/*
  Licensed to the Court of the University of Edinburgh (UofE) under one
  or more contributor license agreements.  See the NOTICE file
  distributed with this work for additional information
  regarding copyright ownership.  The UofE licenses this file
  to you under the Apache License, Version 2.0 (the
  "License"); you may not use this file except in compliance
  with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing,
  software distributed under the License is distributed on an
  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
  KIND, either express or implied.  See the License for the
  specific language governing permissions and limitations
  under the License.
*/
package uk.ac.ed.inf.graph.compound.newimpl;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import uk.ac.ed.inf.graph.compound.CompoundNodePair;
import uk.ac.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElementFactory;
import uk.ac.ed.inf.graph.compound.ICompoundGraphMoveBuilder;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.IElementAttribute;
import uk.ac.ed.inf.graph.compound.IElementAttributeFactory;
import uk.ac.ed.inf.graph.compound.IGraphStructureChangeAction;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraphFactory;

public class CompoundGraphMoveBuilder implements ICompoundGraphMoveBuilder {
	private final Logger logger = Logger.getLogger(this.getClass());
	private ISubCompoundGraph sourceSubCigraph;
	private final IChildCompoundGraph destChildGraph;
	private ISubCompoundGraphFactory movedDestnElementsSubgraphFactory;
	private final Map<ICompoundGraphElement, ICompoundGraphElement> oldNewEquivList;
	private ISubCompoundGraphFactory removalSubGraphFactory;
	private final ICompoundGraphElementFactory elementFactory;
	private ISubCompoundGraph movedComponentsSubgraph;

	public CompoundGraphMoveBuilder(IChildCompoundGraph destn, ICompoundGraphElementFactory elementFactory){
		this.oldNewEquivList = new HashMap<ICompoundGraphElement, ICompoundGraphElement>();
		this.destChildGraph = destn;
		this.movedDestnElementsSubgraphFactory = this.destChildGraph.getSuperGraph().subgraphFactory();
		this.movedComponentsSubgraph = this.movedDestnElementsSubgraphFactory.createSubgraph();
		this.removalSubGraphFactory = this.destChildGraph.getSuperGraph().subgraphFactory();
		this.elementFactory = elementFactory;
	}
	
	@Override
	public ISubCompoundGraph getMovedComponents() {
		return this.movedComponentsSubgraph;
	}

	@Override
	public IChildCompoundGraph getDestinationChildGraph() {
		return this.destChildGraph;
	}

	@Override
	public ISubCompoundGraph getSourceSubgraph() {
		return this.sourceSubCigraph;
	}

	@Override
	public boolean canMoveHere() {
		ISubCompoundGraph subGraph = this.sourceSubCigraph;
		boolean retVal = subGraph != null
//			&& this.elementAttributeFactory != null
			&& subGraph.getSuperGraph().equals(this.destChildGraph.getSuperGraph())
			&& !subGraph.hasOrphanedEdges()
			&& subGraph.isConsistentSnapShot()
			&& !subGraph.containsRoot();
		if(retVal){
			// check is destination is a child of any of the top nodes
			// and that at least one of the top nodes will be moved to a new child graph
			// and that the top nodes attributes are permitted to be moved.
//			this.elementAttributeFactory.setDestinationAttribute(this.destChildGraph.getRoot().getAttribute());
			boolean isDestnChildOfTopNode = false;
			int numTopElsWithDifferntParents = subGraph.numTopElements();
			Iterator<ICompoundGraphElement> topNodeIter = subGraph.topElementIterator();
			while(topNodeIter.hasNext() && !isDestnChildOfTopNode){
				ICompoundGraphElement topNode = topNodeIter.next();
				ICompoundGraphElement destn = this.destChildGraph.getRoot();
				if(topNode.getParent().equals(destn)){
					numTopElsWithDifferntParents--;
				}
				isDestnChildOfTopNode = topNode.isDescendent(destn);
				IElementAttributeFactory elementAttributeFactory = topNode.getAttribute().elementAttributeMoveFactory();
//				this.elementAttributeFactory.setElementToMove(topNode.getAttribute());
				elementAttributeFactory.setDestinationAttribute(this.destChildGraph.getRoot().getAttribute());
				retVal = elementAttributeFactory.canCreateAttribute();
			}
			retVal = !isDestnChildOfTopNode && numTopElsWithDifferntParents > 0;
		}
		if(retVal){
			// now check if has any incident nodes in the subgraph as these will violate the compound graph structure
			Iterator<ICompoundNode> incidentNodeIterator = ((ICompoundNode)this.destChildGraph.getRoot()).connectedNodeIterator();
			while(incidentNodeIterator.hasNext() && retVal){
				ICompoundNode incidentNode = incidentNodeIterator.next();
				retVal = !subGraph.containsNode(incidentNode);
			}
		}
		return retVal;
	}

	@Override
	public void makeMove() {
		this.oldNewEquivList.clear();
		this.movedDestnElementsSubgraphFactory = this.destChildGraph.getSuperGraph().subgraphFactory();
		moveElements();
		addDanglingIncidentEdges();
//		moveLinkedEdges();
		// avoid holding onto additional unneeded memory
		this.oldNewEquivList.clear();
		ICompoundGraph graph = this.destChildGraph.getSuperGraph();
		final ISubCompoundGraph removalSubgraph = removalSubGraphFactory.createSubgraph();
		Iterator<ICompoundGraphElement> iter = removalSubgraph.elementIterator();
		while(iter.hasNext()){
			ICompoundGraphElement element = iter.next();
			element.markRemoved(true);
		}
		this.movedComponentsSubgraph = this.movedDestnElementsSubgraphFactory.createSubgraph();
		graph.notifyGraphStructureChange(new IGraphStructureChangeAction(){

			@Override
			public GraphStructureChangeType getChangeType() {
				return GraphStructureChangeType.SUBGRAPH_MOVED;
			}

			@Override
			public ISubCompoundGraph originalSubgraph() {
				return removalSubgraph;
			}

			@Override
			public ISubCompoundGraph changedSubgraph() {
				return movedComponentsSubgraph;
			}
		});
	}

	private void addDanglingIncidentEdges() {
		ElementTreeStructure incidentDanglingEdges = new ElementTreeStructure();
		// algorithm is to do BFS traversal starting at top nodes of subgraph and then adding
		// in any incident edges, which are also descended until all nodes and edges incident to nodes that
		// are children of the incident edges to the subgraph are traveresed.
		// All such edges and their children are stored for later processing.
		Deque<ICompoundGraphElement> stack = new LinkedList<ICompoundGraphElement>();
		Iterator<ICompoundGraphElement> nodeIter = this.sourceSubCigraph.topElementIterator();
		while(nodeIter.hasNext()){
			stack.push(nodeIter.next());
		}
		Set<Integer> visited = new HashSet<Integer>();
		while (!stack.isEmpty()) {
			ICompoundGraphElement srcElement = stack.poll();
			if(srcElement instanceof ICompoundNode){
				ICompoundNode srcNode = (ICompoundNode)srcElement;
				Iterator<ICompoundEdge> edgeIter = srcNode.edgeIterator();
				while (edgeIter.hasNext()) {
					ICompoundEdge srcEdge = edgeIter.next();
					if (!visited.contains(srcEdge.getIndex())) {
						visited.add(srcEdge.getIndex());
						CompoundNodePair ends = srcEdge.getConnectedNodes();
						if(!this.oldNewEquivList.containsKey(ends.getOutNode()) || !this.oldNewEquivList.containsKey(ends.getInNode())){
							incidentDanglingEdges.addTopElement(srcEdge);
							stack.push(srcEdge);
						}
					}
				}
			}
			Iterator<ICompoundGraphElement> childIter = srcElement.childIterator();
			while(childIter.hasNext()){
				ICompoundGraphElement child = childIter.next();
				if(!visited.contains(child)){
					stack.push(child);
					visited.add(child.getIndex());
				}
			}
		}
		processIncidentEdges(incidentDanglingEdges);
	}
	
	
	private void processIncidentEdges(ElementTreeStructure incidentDanglingEdges){
		Iterator<ICompoundGraphElement> elIter = incidentDanglingEdges.edgeLastElementIterator();
		while (elIter.hasNext()) {
			ICompoundGraphElement srcElement = elIter.next();
			ICompoundGraphElement newElement = null;
			ICompoundGraphElement equivParent = this.oldNewEquivList.get(srcElement.getParent());
			if(srcElement instanceof ICompoundNode){
				if(logger.isTraceEnabled()){
					logger.trace("Creating node src=" + srcElement + ", parent=" + equivParent);
				}
				if(!srcElement.getParent().equals(equivParent)){
					newElement = moveNode((ICompoundNode)srcElement, equivParent);
				}
				else{
					newElement = srcElement;
				}
			}
			else{
				ICompoundEdge srcEdge = (ICompoundEdge)srcElement;
				CompoundNodePair ends = srcEdge.getConnectedNodes();
				ICompoundNode newInNode = (ICompoundNode)this.oldNewEquivList.get(ends.getInNode());
				ICompoundNode newOutNode = (ICompoundNode)this.oldNewEquivList.get(ends.getOutNode());
				if(newInNode == null){
					newInNode = ends.getInNode();
				}
				else if(newOutNode == null){
					newOutNode = ends.getOutNode();
				}
				ICompoundGraph ciGraph = this.destChildGraph.getSuperGraph();
				ICompoundGraphElement lca = ciGraph.getElementTree().getLowestCommonAncestor(newInNode, newOutNode);
				if (lca == null) {
					throw new IllegalStateException("The graph and subgraph are inconsistent: an lca for a copied edge could not be found");
				}
				if(!(newInNode.equals(ends.getInNode()) && newOutNode.equals(ends.getOutNode()) && equivParent.equals(srcElement.getParent()))){
					newElement = moveEdge((ICompoundEdge)srcElement, lca, newOutNode, newInNode);
				}
				else{
					newElement = srcElement;
				}
			}
			this.oldNewEquivList.put(srcElement, newElement);
			this.movedDestnElementsSubgraphFactory.addElement(newElement);
		}
	}

	private void moveElements(){
		initRootNodeEquivalent(); 
		Iterator<ICompoundGraphElement> sourceNodeIter = this.sourceSubCigraph.edgeLastElementIterator();
		while(sourceNodeIter.hasNext()){
			ICompoundGraphElement srcElement = sourceNodeIter.next();
			ICompoundGraphElement newElement = null;
			ICompoundGraphElement equivParent = this.oldNewEquivList.get(srcElement.getParent());
			if(srcElement instanceof ICompoundNode){
				if(logger.isTraceEnabled()){
					logger.trace("Creating node src=" + srcElement + ", parent=" + equivParent);
				}
				if(!srcElement.getParent().equals(equivParent)){
					newElement = moveNode((ICompoundNode)srcElement, equivParent);
				}
				else{
					newElement = srcElement;
				}
			}
			else if(srcElement instanceof ICompoundEdge){
				ICompoundEdge srcEdge = (ICompoundEdge)srcElement;
				CompoundNodePair srcNodePair = srcEdge.getConnectedNodes();
				ICompoundNode equivOutNode = (ICompoundNode)this.oldNewEquivList.get(srcNodePair.getOutNode());
				ICompoundNode equivInNode = (ICompoundNode)this.oldNewEquivList.get(srcNodePair.getInNode());
				if(logger.isTraceEnabled()){
					logger.trace("Creating edge src=" + srcElement + ", parent=" + equivParent +", outNode=" + equivOutNode + ", inNode=" + equivInNode);
				}
				// test if edge connections and parent are unchanged. If so leave edge alone. otherwise create a new edge
				if(!(equivInNode.equals(srcNodePair.getInNode()) && equivOutNode.equals(srcNodePair.getOutNode()) && equivParent.equals(srcElement.getParent()))){
					newElement = moveEdge((ICompoundEdge)srcElement, equivParent, equivOutNode, equivInNode);
				}
				else{
					newElement = srcElement;
				}
			}
			else{
				throw new RuntimeException("Unrecognised element type");
			}
			if(logger.isTraceEnabled()){
				logger.trace("Moving src=" + srcElement + ", tgt=" + newElement);
			}
			this.oldNewEquivList.put(srcElement, newElement);
			this.movedDestnElementsSubgraphFactory.addElement(newElement);
		}
	}

//	private void moveLinkedEdges(){
//		Iterator<ICompoundNode> nodeIter = this.sourceSubCigraph.nodeIterator();
//		Set<Integer> visited = new HashSet<Integer>();
//		while (nodeIter.hasNext()) {
//			ICompoundNode srcNode = nodeIter.next();
//			Iterator<ICompoundEdge> edgeIter = srcNode.edgeIterator();
//			while (edgeIter.hasNext()) {
//				boolean foundLinkedNode = true;
//				ICompoundEdge srcEdge = edgeIter.next();
//				if (!visited.contains(srcEdge.getIndex())) {
//					visited.add(srcEdge.getIndex());
//					CompoundNodePair ends = srcEdge.getConnectedNodes();
//					ICompoundNode newInNode = (ICompoundNode)this.oldNewEquivList.get(ends.getInNode());
//					ICompoundNode newOutNode = (ICompoundNode)this.oldNewEquivList.get(ends.getOutNode());
//					if(newInNode == null){
//						newInNode = ends.getInNode();
//					}
//					else if(newOutNode == null){
//						newOutNode = ends.getOutNode();
//					}
//					else{
//						foundLinkedNode = false;
//					}
//					if(foundLinkedNode){
//						ICompoundGraph ciGraph = this.destChildGraph.getSuperGraph();
//						ICompoundGraphElement lca = ciGraph.getElementTree().getLowestCommonAncestor(newInNode, newOutNode);
//						if (lca == null) {
//							throw new IllegalStateException("The graph and subgraph are inconsistent: an lca for a copied edge could not be found");
//						}
//						ICompoundEdge newEdge = moveEdge(srcEdge, lca, newOutNode, newInNode);
//						this.removalSubGraphFactory.addElement(srcEdge);
//						this.movedDestnElementsSubgraphFactory.addElement(newEdge);
//					}
//				}
//			}
//		}
//	}

	private void initRootNodeEquivalent() {
		Iterator<ICompoundGraphElement> topElements = this.sourceSubCigraph.topElementIterator();
		while(topElements.hasNext()){
			ICompoundGraphElement topElement = topElements.next();
			ICompoundGraphElement topParent = topElement.getParent();
			ICompoundGraphElement destElement = this.destChildGraph.getRoot(); 
			if(logger.isTraceEnabled()){
				logger.trace("topElement=" + topElement + ", topParent=" + topParent +", destRoot=" + destElement);
			}
			this.oldNewEquivList.put(topParent, destElement);
		}
		
	}

	private ICompoundEdge moveEdge(ICompoundEdge srcEdge, ICompoundGraphElement parent, ICompoundNode outNode, ICompoundNode inNode) {
//		ICompoundEdge retVal = srcEdge;
//		ICompoundChildEdgeFactory edgefact = parent.getChildCompoundGraph().edgeFactory();
//		edgefact.setPair(new CompoundNodePair(outNode, inNode));
		IElementAttributeFactory elementAttributeFactory = srcEdge.getAttribute().elementAttributeMoveFactory();
		elementAttributeFactory.setDestinationAttribute(parent.getAttribute());
		elementAttributeFactory.setInAttribute(inNode.getAttribute());
		elementAttributeFactory.setOutAttribute(outNode.getAttribute());
//		this.elementAttributeFactory.setElementToMove(srcEdge.getAttribute());
		int nodeIndex = parent.getGraph().getIndexCounter().nextIndex();
		IElementAttribute newAttribute = elementAttributeFactory.createAttribute();
		this.elementFactory.setParent(parent);
		this.elementFactory.setIndex(nodeIndex);
		this.elementFactory.setAttribute(newAttribute);
		ICompoundEdge newEdge = this.elementFactory.createEdge(outNode, inNode);
//		CompoundEdge newEdge = new CompoundEdge(parent, nodeIndex, newAttribute, outNode, inNode);
		parent.getChildCompoundGraph().addEdge(newEdge);
//		edgefact.setAttributeFactory(elementAttributeFactory);
//		retVal = edgefact.createEdge();
//		srcEdge.markRemoved(true);
		this.removalSubGraphFactory.addElement(srcEdge);
		return newEdge;
	}

	private ICompoundNode moveNode(ICompoundNode srcNode, ICompoundGraphElement destParentNode){
//		ICompoundNode newNode = srcNode; 
//		ICompoundNodeFactory fact = destParentNode.getChildCompoundGraph().nodeFactory();
		IElementAttributeFactory elementAttributeFactory = srcNode.getAttribute().elementAttributeMoveFactory();
		elementAttributeFactory.setDestinationAttribute(destParentNode.getAttribute());
//		elementAttributeFactory.setElementToMove(srcNode.getAttribute());
		int nodeIndex = destParentNode.getGraph().getIndexCounter().nextIndex();
		IElementAttribute newAttribute = elementAttributeFactory.createAttribute();
		this.elementFactory.setParent(destParentNode);
		this.elementFactory.setIndex(nodeIndex);
		this.elementFactory.setAttribute(newAttribute);
		ICompoundNode newNode = this.elementFactory.createNode();
//		CompoundNode newNode = new CompoundNode(destParentNode, nodeIndex, newAttribute);
		destParentNode.getChildCompoundGraph().addNode(newNode);
//		fact.setAttributeFactory(elementAttributeFactory);
//		newNode = fact.createNode();
//		srcNode.markRemoved(true);
		this.removalSubGraphFactory.addElement(srcNode);
		return newNode;
	}

	@Override
	public void setSourceSubgraph(ISubCompoundGraph sourceSubCompoundGraph) {
		this.sourceSubCigraph = sourceSubCompoundGraph;
		if(sourceSubCompoundGraph != null){
			this.removalSubGraphFactory = this.sourceSubCigraph.getSuperGraph().subgraphFactory();
		}
		else{
			this.removalSubGraphFactory = this.destChildGraph.getSuperGraph().subgraphFactory();
		}
	}

	@Override
	public ISubCompoundGraph getRemovedComponents() {
		return this.removalSubGraphFactory.createSubgraph();
	}

}
