/*
Copyright 2009, Court of the University of Edinburgh

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License. 
*/
package uk.ed.inf.graph.compound.base;

import java.util.Iterator;

import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.ICompoundNodePair;
import uk.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ed.inf.graph.compound.ISubgraphAlgorithms;
import uk.ed.inf.graph.util.IDirectedEdgeSet;
import uk.ed.inf.graph.util.IEdgeSet;
import uk.ed.inf.graph.util.IFilterCriteria;
import uk.ed.inf.graph.util.INodeSet;
import uk.ed.inf.graph.util.SubgraphAlgorithms;
import uk.ed.inf.graph.util.impl.FilteredEdgeSet;
import uk.ed.inf.graph.util.impl.FilteredNodeSet;

public abstract class BaseSubCompoundGraph implements ISubCompoundGraph {
	private INodeSet<ICompoundNode, ICompoundEdge> topNodeSet;
	private INodeSet<ICompoundNode, ICompoundEdge> nodeSet;
	private IEdgeSet<ICompoundNode, ICompoundEdge> edgeSet;
	private Boolean isInducedSubgraphFlag = null;
	
	protected BaseSubCompoundGraph(){
	}
	
	protected final void createTopNodeSet(INodeSet<ICompoundNode, ICompoundEdge> topNodeSet){
		this.topNodeSet = new FilteredNodeSet<ICompoundNode, ICompoundEdge>(topNodeSet, new IFilterCriteria<ICompoundNode>(){

			public boolean matched(ICompoundNode testObj) {
//				return !testObj.isRemoved();
				return true;
			}
	
		});
	}
	
	protected final void createNodeSet(INodeSet<ICompoundNode, ICompoundEdge> nodeSet){
		this.nodeSet = new FilteredNodeSet<ICompoundNode, ICompoundEdge>(nodeSet, new IFilterCriteria<ICompoundNode>(){
			public boolean matched(ICompoundNode testObj) {
				return true;
			}
	
		});
	}
	
	protected final void createEdgeSet(IDirectedEdgeSet<ICompoundNode, ICompoundEdge> edgeSet){
		this.edgeSet = new FilteredEdgeSet<ICompoundNode, ICompoundEdge>(edgeSet,
				new IFilterCriteria<ICompoundEdge>(){

					public boolean matched(ICompoundEdge testObj) {
//						return !testObj.isRemoved();
						return true;
					}
			
		});
	}
	
	@Override
	public abstract BaseCompoundGraph getSuperGraph();

	@Override
	public boolean isInducedSubgraph() {
		if(isInducedSubgraphFlag  == null){
			ISubgraphAlgorithms alg = new SubgraphAlgorithms(this);
			this.isInducedSubgraphFlag = new Boolean(alg.isInducedSubgraph());
		}
		return this.isInducedSubgraphFlag;
	}

	@Override
	public boolean containsConnection(ICompoundNode thisNode, ICompoundNode thatNode) {
		boolean retVal = false;
		if(thisNode != null && thatNode != null){
			if(this.containsNode(thisNode) && this.containsNode(thatNode)){
				retVal = thisNode.hasEdgeWith(thatNode);
			}
		}
		return retVal;
	}

	@Override
	public boolean containsEdge(ICompoundEdge edge) {
		return this.edgeSet.contains(edge);
	}

	@Override
	public boolean containsEdge(int edgeIdx) {
		return this.edgeSet.contains(edgeIdx);
	}

	@Override
	public boolean containsNode(int nodeIdx) {
		return this.nodeSet.contains(nodeIdx);
	}

	@Override
	public boolean containsNode(ICompoundNode node) {
		return this.nodeSet.contains(node);
	}

	@Override
	public ICompoundEdge getEdge(int edgeIdx) {
		return this.edgeSet.get(edgeIdx);
	}

	@Override
	public Iterator<ICompoundEdge> edgeIterator() {
		return this.edgeSet.iterator();
	}

	@Override
	public ICompoundNode getNode(int nodeIdx) {
		return this.nodeSet.get(nodeIdx);
	}

	@Override
	public Iterator<ICompoundNode> nodeIterator() {
		return this.nodeSet.iterator();
	}

	@Override
	public int getNumEdges() {
		return this.edgeSet.size();
	}

	@Override
	public int getNumNodes() {
		return this.nodeSet.size();
	}

	protected final void addTopNode(ICompoundNode newNode){
		this.topNodeSet.add(newNode);
	}
	
	protected final void addEdge(ICompoundEdge newEdge){
		this.edgeSet.add(newEdge);
	}
	
	void buildComplete(){
	}

	@Override
	public boolean containsDirectedEdge(ICompoundNode outNode, ICompoundNode inNode) {
		boolean retVal = false;
		if(outNode != null && inNode != null){
			retVal = this.edgeSet.contains(outNode, inNode);
		}
		return retVal;
	}

	@Override
	public boolean isConsistentSnapShot() {
		boolean retVal = true;
		Iterator <ICompoundNode> nodeIter = this.nodeIterator() ;
		while ( nodeIter.hasNext() && retVal){
			retVal = !nodeIter.next().isRemoved();
		}
		Iterator<ICompoundEdge> edgeIter = this.edgeIterator();
		while ( edgeIter.hasNext() && retVal){
			retVal = !edgeIter.next().isRemoved();
		}		
		
		return retVal;
	}

	/**
	 * Tests if the ends define one or more directed edges.
	 */
	@Override
	public boolean containsDirectedEdge(ICompoundNodePair ends) {
		boolean retVal = false;
		if(ends != null){
			ICompoundNode outNode = ends.getOutNode();
			ICompoundNode inNode = ends.getInNode();
			// check that both nodes exist in this subgraph
			retVal = outNode.hasOutEdgeTo(inNode);
		}
		return retVal;
	}

	/**
	 * Tests if the ends define any edge in this graph. Note that the node pair must
	 * be created by this graph as the method expects <code>ends</code> to be of type
	 * <code>IDirectedPair</code> and will return false if it is not.
	 * @param ends the pair of nodes that may define the edges of an edge.
	 * @return true if it does, false otherwise.  
	 */
	@Override
	public boolean containsConnection(ICompoundNodePair ends) {
		boolean retVal = false;
		if(ends != null){
			ICompoundNode oneNode = ends.getOutNode();
			ICompoundNode twoNode = ends.getInNode();
			// check that the nodes belong to this subgraph.
			retVal = this.containsConnection(oneNode, twoNode);
		}
		return retVal;
	}

	@Override
	public Iterator<ICompoundNode> topNodeIterator(){
		return this.topNodeSet.iterator();
	}
	
	@Override
	public int getNumTopNodes(){
		return this.topNodeSet.size();
	}
	
	/**
	 * Checks if this SubGraph contains the RootNode of the CompoundGraph.
	 * @return true if the rootNode is contained in this SubGraph.
	 */
	@Override
	public boolean containsRoot (){
		 return this.containsNode(this.getSuperGraph().getRoot()) ;
	}

	protected final void addNode(ICompoundNode node) {
		this.nodeSet.add(node);
	}

	@Override
	public boolean containsElement(ICompoundGraphElement element) {
		return this.nodeSet.contains(element) || this.edgeSet.contains(element);
	}
	
}
