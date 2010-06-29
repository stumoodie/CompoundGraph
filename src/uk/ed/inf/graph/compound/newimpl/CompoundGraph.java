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
package uk.ed.inf.graph.compound.newimpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import uk.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ed.inf.graph.compound.ICompoundGraph;
import uk.ed.inf.graph.compound.ICompoundGraphCopyBuilder;
import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.ICompoundNodeFactory;
import uk.ed.inf.graph.compound.ICompoundNodePair;
import uk.ed.inf.graph.compound.IRootCompoundNode;
import uk.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ed.inf.graph.compound.ISubCompoundGraphFactory;
import uk.ed.inf.graph.state.IGraphState;
import uk.ed.inf.graph.state.IGraphStateHandler;
import uk.ed.inf.graph.state.IRestorableGraph;
import uk.ed.inf.graph.state.IRestorableGraphElement;
import uk.ed.inf.graph.util.IndexCounter;
import uk.ed.inf.graph.util.impl.EdgeTreeIterator;
import uk.ed.inf.graph.util.impl.NodeTreeIterator;
import uk.ed.inf.tree.ITree;

public class CompoundGraph implements ICompoundGraph, IRestorableGraph {
	private final static int ROOT_NODE_IDX = 0;

	private static final String DEBUG_PROP_NAME = "uk.ed.inf.graph.compound.base.debugging";
	// added debug checks to graph
	private final boolean debuggingEnabled;
    private final Logger logger = Logger.getLogger(this.getClass());
//	private final IndexCounter elementCounter;
	private final IRootCompoundNode rootNode;
	private ICompoundGraphCopyBuilder copyBuilder;
	private final IGraphStateHandler stateHandler;
	private static Map<ICompoundGraph, IndexCounter> counterLookup = new HashMap<ICompoundGraph, IndexCounter>();

//	private ICompoundGraphServices services;

	public CompoundGraph(){
		this(new CompoundGraphCopyBuilder());
	}
		
	public CompoundGraph(ICompoundGraphCopyBuilder copyBuilder){
		this.debuggingEnabled = Boolean.getBoolean(DEBUG_PROP_NAME);
		this.stateHandler = new CompoundGraphStateHandler(this);
//		this.elementCounter = new IndexCounter(ROOT_NODE_IDX);
//		this.services =  new ICompoundGraphServices() {
//			
//			@Override
//			public ICompoundGraphMoveBuilder newMoveBuilder() {
//				return new CompoundGraphMoveBuilder();
//			}
//			
//			@Override
//			public ICompoundGraphCopyBuilder newCopyBuilder() {
//				return new CompoundGraphCopyBuilder();
//			}
//			
//			@Override
//			public IndexCounter getIndexCounter() {
//				return elementCounter;
//			}
//		};
		this.rootNode = new RootCompoundNode(this, ROOT_NODE_IDX);
//		this.copyBuilder = this.services.newCopyBuilder();
		this.copyBuilder = copyBuilder;
		counterLookup.put(this, new IndexCounter(ROOT_NODE_IDX));
	}
	
	public static IndexCounter getIndexCounter(ICompoundGraph graph){
		return counterLookup.get(graph);
	}
	
	
	@Override
	public IRootCompoundNode getRoot() {
		return this.rootNode;
	}

	public ITree<ICompoundGraphElement> getElementTree(){
		return this.getRoot().getChildCompoundGraph().getElementTree();
	}
	
	@Override
	public boolean containsConnection(ICompoundNode thisNode, ICompoundNode thatNode) {
		boolean retVal = false;
		if(thisNode != null && thatNode != null){
			ICompoundGraphElement node = this.getElementTree().getLowestCommonAncestor(thisNode, thatNode);
			if(node != null){
				retVal = node.getChildCompoundGraph().containsConnection(thisNode, thatNode);
			}
		}
		return retVal;
	}

	@Override
	public boolean containsConnection(ICompoundNodePair ends) {
		boolean retVal = false;
		if(ends != null){
			ICompoundNode oneNode = ends.getOutNode();
			ICompoundNode twoNode = ends.getInNode();
			// check that at least one node belongs to this graph, if so then we
			// can be sure that the other node and edge will.
			if(oneNode.getGraph().equals(this)){
				retVal = this.containsConnection(oneNode, twoNode);
			}
		}
		return retVal;
	}

	@Override
	public boolean containsDirectedEdge(ICompoundNode outNode, ICompoundNode inNode) {
		boolean retVal = false;
		if(inNode != null && outNode != null){
			ICompoundGraphElement node = this.getElementTree().getLowestCommonAncestor(inNode, outNode);
			if(node != null){
				retVal = node.getChildCompoundGraph().containsDirectedEdge(outNode, inNode);
			}
		}
		return retVal;
	}

	@Override
	public boolean containsEdge(ICompoundEdge edge) {
		return edge != null && !edge.isRemoved() && edge.getGraph().equals(this);
	}

	@Override
	public boolean containsEdge(int edgeIdx) {
		boolean retVal = false;
		Iterator<ICompoundGraphElement> iter = this.getElementTree().levelOrderIterator();
		while(iter.hasNext()){
			ICompoundGraphElement node = iter.next();
			retVal = node.getChildCompoundGraph().containsEdge(edgeIdx);
			if(retVal == true){
				break;
			}
		}
		return retVal;
	}

	@Override
	public boolean containsNode(int nodeIdx) {
		return this.getElementTree().containsNode(nodeIdx);
	}

	@Override
	public boolean containsNode(ICompoundNode node) {
		return node != null && !node.isRemoved() && node.getGraph().equals(this);
	}

	@Override
	public Iterator<ICompoundEdge> edgeIterator() {
		return new EdgeTreeIterator(this.getRoot());
	}

	@Override
	public Iterator<ICompoundGraphElement> elementIterator() {
		return this.getElementTree().levelOrderIterator();
	}

	@Override
	public ICompoundEdge getEdge(int edgeIdx) {
		ICompoundGraphElement retVal = this.getElementTree().get(edgeIdx);
		if(retVal == null || !(retVal instanceof ICompoundEdge)) throw new IllegalArgumentException("edgeIdx does not refer toa  node contained in this graph");
		return (ICompoundEdge)retVal;
	}

	@Override
	public ICompoundNode getNode(int nodeIdx) {
		ICompoundGraphElement retVal = this.getElementTree().get(nodeIdx);
		if(retVal == null || !(retVal instanceof ICompoundNode)) throw new IllegalArgumentException("nodeIdx does not refer toa  node contained in this graph");
		return (ICompoundNode)retVal;
	}

	@Override
	public int getNumEdges() {
		Iterator<ICompoundEdge> iter = this.edgeIterator();
		int cntr = 0;
		while(iter.hasNext()){
			iter.next();
			cntr++;
		}
		return cntr;
	}

	@Override
	public int getNumNodes() {
		Iterator<ICompoundNode> iter = this.nodeIterator();
		int cntr = 0;
		while(iter.hasNext()){
			iter.next();
			cntr++;
		}
		return cntr;
	}

	@Override
	public Iterator<ICompoundNode> nodeIterator() {
		return new NodeTreeIterator(this.getRoot());
	}

	@Override
	public int numElements() {
		return this.getElementTree().size();
	}

	@Override
	public boolean canCopyHere(ISubCompoundGraph subGraph) {
		return subGraph != null && subGraph.isInducedSubgraph() && subGraph.isConsistentSnapShot();
	}

	protected void notifyCopyOperationComplete(ISubCompoundGraph sourceSubgraph, ISubCompoundGraph copiedComponents) {
		//TODO: Add listener that is notified
	}

	@Override
	public boolean canRemoveSubgraph(ISubCompoundGraph subgraph) {
		return subgraph != null && subgraph.getSuperGraph().equals(this) && !subgraph.containsRoot() && subgraph.isConsistentSnapShot();
	}

	@Override
	public void copyHere(ISubCompoundGraph subGraph) {
		if(this.debuggingEnabled && !canCopyHere(subGraph)) throw new IllegalArgumentException("Cannot copy graph here");
		
		IChildCompoundGraph rootCiGraph = this.getRoot().getChildCompoundGraph();
		copyBuilder.setDestinatChildCompoundGraph(rootCiGraph);
		copyBuilder.setSourceSubgraph(subGraph);
		copyBuilder.makeCopy();
		notifyCopyOperationComplete(copyBuilder.getSourceSubgraph(), copyBuilder.getCopiedComponents());
	}

	@Override
	public ICompoundEdgeFactory edgeFactory() {
		return new CompoundEdgeFactory(this);
	}

	@Override
	public ISubCompoundGraph getCopiedComponents() {
		return this.copyBuilder.getCopiedComponents();
	}

	@Override
	public boolean isValid() {
	    boolean retVal = true;
	    retVal = this.getRoot().getChildCompoundGraph().getNumEdges() == 0
	        && this.getRoot().getParent() == this.getRoot()
	        && this.getRoot().isRemoved() == false;
	    if(retVal) {
	        retVal = this.getRoot().getChildCompoundGraph().isValid();
	    }
	    else {
	        logger.error("Graph Invalid: root node is inconsistent: " + this.getRoot());
	    }
	    if(retVal) {
	        retVal = this.hasPassedAdditionalValidation();
	    }
	    else {
	        logger.error("Graph Invalid: addition validation from super class has failed");
	    }
	    return retVal;
	}

	protected boolean hasPassedAdditionalValidation() {
		// TODO : do something more clever than this for validation
		return true;
	}

	@Override
	public ICompoundNodeFactory nodeFactory() {
		return this.getRoot().getChildCompoundGraph().nodeFactory();
	}

	@Override
	public void removeSubgraph(ISubCompoundGraph subgraph) {
		if(this.debuggingEnabled && !this.canRemoveSubgraph(subgraph)) throw new IllegalArgumentException("subgraph does not satify canRemoveSubgraph()");
		
		ISubCompoundGraphFactory selnFactory = this.subgraphFactory();
		removeElements(selnFactory, subgraph.elementIterator());
	}

	private void removeElements(ISubCompoundGraphFactory selnFactory, Iterator<ICompoundGraphElement> elementIterator) {
		while(elementIterator.hasNext()){
			ICompoundGraphElement node = elementIterator.next();
			node.markRemoved(true);
			selnFactory.addElement(node);
		}
	}

	protected void notifyRemovalOperationComplete(ISubCompoundGraph removedGraph) {
		// TODO : add a listener!
		
	}

	@Override
	public ISubCompoundGraphFactory subgraphFactory() {
		return new SubCompoundGraphFactory(this);
	}

	@Override
	public IGraphState getCurrentState() {
		return stateHandler.createGraphState();
	}

	@Override
	public void restoreState(IGraphState previousState) {
		this.stateHandler.restoreState(previousState);
	}

	@Override
	public Iterator<IRestorableGraphElement> restorableElementIterator() {
		final Iterator<ICompoundGraphElement> iter = this.getRoot().levelOrderIterator();
		return new Iterator<IRestorableGraphElement>(){

			@Override
			public boolean hasNext() {
				return iter.hasNext();
			}

			@Override
			public IRestorableGraphElement next() {
				return (IRestorableGraphElement)iter.next();
			}

			@Override
			public void remove() {
				iter.remove();
			}
			
		};
	}
}
