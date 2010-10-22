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
package uk.ac.ed.inf.graph.compound.base;

import java.util.Iterator;

import org.apache.log4j.Logger;

import uk.ac.ed.inf.graph.basic.IBasicPair;
import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.IModifiableCompoundGraph;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ac.ed.inf.graph.directed.IDirectedPair;
import uk.ac.ed.inf.graph.state.IGraphState;
import uk.ac.ed.inf.graph.state.IRestorableGraph;
import uk.ac.ed.inf.graph.util.IndexCounter;
import uk.ac.ed.inf.graph.util.impl.EdgeFromNodeIterator;
import uk.ac.ed.inf.tree.ITree;

public abstract class BaseCompoundGraph implements ICompoundGraph<BaseCompoundNode, BaseCompoundEdge>,
		IRestorableGraph<BaseCompoundNode, BaseCompoundEdge>,
		IModifiableCompoundGraph<BaseCompoundNode, BaseCompoundEdge> {
	private static final String DEBUG_PROP_NAME = "uk.ac.ed.inf.graph.compound.base.debugging";
	// added debug checks to graph
	private final boolean debuggingEnabled;
    private final Logger logger = Logger.getLogger(this.getClass());
	private final BaseCompoundGraphStateHandler stateHandler;
	private BaseGraphCopyBuilder copyBuilder;
	
	protected BaseCompoundGraph(BaseGraphCopyBuilder copyBuilder){
		this.debuggingEnabled = Boolean.getBoolean(DEBUG_PROP_NAME);
		this.stateHandler = new BaseCompoundGraphStateHandler(this);
		this.copyBuilder = copyBuilder;
	}

	protected BaseCompoundGraph(BaseGraphCopyBuilder copyBuilder, BaseCompoundGraph otherGraph){
		this(copyBuilder);
		createCopyOfRootNode(getNodeCounter().getLastIndex(), otherGraph.getRootNode());
		this.performCopy(otherGraph);
	}
	
	protected abstract IndexCounter getNodeCounter();
	
	protected abstract IndexCounter getEdgeCounter();

	protected abstract void createCopyOfRootNode(int newIndexValue, BaseCompoundNode otherRootNode);

	protected abstract ITree<BaseCompoundNode> getNodeTree();

	/**
	 * To be used by copy constructor. After this constructor has been called extending classes should ensure that 
	 *  the root node is copied, and a new Tree is created. The calling constructor can then call
	 *  <code>perfromCopy()</code> to  actually copy the graph;
	 * @param copyBuilder
	 * @param otherGraph
	 */
	protected void performCopy(BaseCompoundGraph otherGraph){
		BaseSubCompoundGraphFactory fact = otherGraph.subgraphFactory();
		Iterator<BaseCompoundNode> iter = otherGraph.getNodeTree().getRootNode().getChildCompoundGraph().nodeIterator();
		while(iter.hasNext()){
			BaseCompoundNode level1Node = iter.next();
			fact.addNode(level1Node);
		}
		BaseSubCompoundGraph subgraph = fact.createInducedSubgraph();
		this.copyHere(subgraph);
	}
	
	protected abstract void notifyCopyOperationComplete(ISubCompoundGraph<? extends BaseCompoundNode, ? extends BaseCompoundEdge> originalSubgraph,
			ISubCompoundGraph<? extends BaseCompoundNode, ? extends BaseCompoundEdge> copiedNodes);
	
	protected abstract void notifyRemovalOperationComplete(ISubCompoundGraph<? extends BaseCompoundNode, ? extends BaseCompoundEdge> subgraph);

	public abstract BaseCompoundNode getRootNode();

	public final boolean containsDirectedEdge(BaseCompoundNode outNode, BaseCompoundNode inNode) {
		boolean retVal = false;
		if(inNode != null && outNode != null){
			BaseCompoundNode node = this.getNodeTree().getLowestCommonAncestor(inNode, outNode);
			if(node != null){
				retVal = node.getChildCompoundGraph().containsDirectedEdge(outNode, inNode);
			}
		}
		return retVal;
	}

	public final boolean containsConnection(BaseCompoundNode thisNode, BaseCompoundNode thatNode) {
		boolean retVal = false;
		if(thisNode != null && thatNode != null){
			BaseCompoundNode node = this.getNodeTree().getLowestCommonAncestor(thisNode, thatNode);
			if(node != null){
				retVal = node.getChildCompoundGraph().containsConnection(thisNode, thatNode);
			}
		}
		return retVal;
	}

	public final boolean containsEdge(BaseCompoundEdge edge) {
		boolean retVal = false;
		if(edge != null && edge.getGraph().equals(this)){
			retVal = edge.getOwningChildGraph().containsEdge(edge);
		}
		return retVal;
	}

	public final boolean containsEdge(int edgeIdx) {
		boolean retVal = false;
		Iterator<BaseCompoundNode> iter = this.getNodeTree().levelOrderIterator();
		while(iter.hasNext()){
			BaseCompoundNode node = iter.next();
			retVal = node.getChildCompoundGraph().containsEdge(edgeIdx);
			if(retVal == true){
				break;
			}
		}
		return retVal;
	}

	public final boolean containsNode(int nodeIdx) {
		return this.getNodeTree().containsNode(nodeIdx);
	}

	public final boolean containsNode(BaseCompoundNode node) {
		boolean retVal = false;
		if(node != null){
			retVal = this.containsNode(node.getIndex());
		}
		return retVal;
	}

	public BaseCompoundEdge getEdge(int edgeIdx) {
		Iterator<BaseCompoundEdge> iter = this.edgeIterator();
		BaseCompoundEdge retVal = null;
		while(iter.hasNext() && retVal == null){
			BaseCompoundEdge edge = iter.next();
			if(edge.getIndex() == edgeIdx){
				retVal = edge;
			}
		}
		return retVal;
	}

	/**
	 * Returns all edges in tree level-node iteration order. For each node the edges are returned in the same
	 * order as the CiNode edge iterator. Returns both undirected and directed nodes.
	 */
	public Iterator<BaseCompoundEdge> edgeIterator() {
		return new EdgeFromNodeIterator<BaseCompoundNode, BaseCompoundEdge>(this.getNodeTree().levelOrderIterator());
	}

	public BaseCompoundNode getNode(int nodeIdx) {
		BaseCompoundNode retVal = this.getNodeTree().get(nodeIdx);
		if(retVal == null) throw new IllegalArgumentException("nodeIdx does not refer toa  node contained in this graph");
		return retVal;
	}

	public Iterator<BaseCompoundNode> nodeIterator() {
		return this.getNodeTree().levelOrderIterator();
	}

	public final int getNumEdges() {
		Iterator<BaseCompoundEdge> iter = this.edgeIterator();
		int cntr = 0;
		while(iter.hasNext()){
			iter.next();
			cntr++;
		}
		return cntr;
	}

	public final int getNumNodes() {
		return this.getNodeTree().size();
	}

	
	public boolean canRemoveSubgraph(ISubCompoundGraph<? extends BaseCompoundNode, ? extends BaseCompoundEdge> subgraph){
		return subgraph != null && subgraph.getSuperGraph().equals(this) && subgraph.isConsistentSnapShot();
	}
	
	public final void removeSubgraph(ISubCompoundGraph<? extends BaseCompoundNode, ? extends BaseCompoundEdge> subgraph) {
		if(this.debuggingEnabled && !this.canRemoveSubgraph(subgraph)) throw new IllegalArgumentException("subgraph does not satify canRemoveSubgraph()");
		BaseSubCompoundGraph removedGraph = internalRemoveSubgraph(subgraph);
		notifyRemovalOperationComplete(removedGraph);
	}

	private void removeEdges(BaseSubCompoundGraphFactory selnFactory, Iterator<? extends BaseCompoundEdge> edgeIterator){
		while(edgeIterator.hasNext()){
			BaseCompoundEdge edge = (BaseCompoundEdge)edgeIterator.next();
			edge.markRemoved(true);
			selnFactory.addEdge(edge);
		}
	}
	
	private void removeNodes(BaseSubCompoundGraphFactory selnFactory, Iterator<? extends BaseCompoundNode> nodeIterator){
		while(nodeIterator.hasNext()){
			BaseCompoundNode node = (BaseCompoundNode)nodeIterator.next();
			if(node.equals(this.getRootNode())){
				throw new IllegalStateException("Cannot remove the root node from a compound graph");
			}
			node.markRemoved(true);
			selnFactory.addNode(node);
			// remove edges associated with node
			Iterator<BaseCompoundEdge> edgeIter = node.edgeIterator();
			while(edgeIter.hasNext()){
				BaseCompoundEdge edge = edgeIter.next();
				edge.markRemoved(true);
				selnFactory.addEdge(edge);
			}
		}
	}
	
	final BaseCompoundNode getLcaNode(BaseCompoundNode inNode, BaseCompoundNode outNode){
		if(inNode == null || outNode == null) throw new IllegalArgumentException("parameters cannot be null");
		
		return this.getNodeTree().getLowestCommonAncestor(inNode, outNode);
	}
	
	/**
	 * Tests if the ends define one or more directed edges.
	 */
	public final boolean containsDirectedEdge(IDirectedPair<? extends BaseCompoundNode, ? extends BaseCompoundEdge> ends) {
		boolean retVal = false;
		if(ends != null){
			BaseCompoundNode outNode = (BaseCompoundNode)ends.getOutNode();
			BaseCompoundNode inNode = (BaseCompoundNode)ends.getInNode();
			// check that at least one node belongs to this graph, if so then we
			// can be sure that the other node and edge will.
			if(outNode.getGraph().equals(this)){
				retVal = outNode.hasOutEdgeTo(inNode);
			}
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
	@SuppressWarnings("unchecked")
	public final boolean containsConnection(IBasicPair<? extends BaseCompoundNode, ? extends BaseCompoundEdge> ends) {
		boolean retVal = false;
		if(ends != null && ends instanceof IDirectedPair){
			// since this is a directed graph a valid edge pair must be an IDirectedPair
			IDirectedPair<? extends BaseCompoundNode, ? extends BaseCompoundEdge> ciEnds = (IDirectedPair<? extends BaseCompoundNode, ? extends BaseCompoundEdge>)ends;
			BaseCompoundNode oneNode = (BaseCompoundNode)ciEnds.getOutNode();
			BaseCompoundNode twoNode = (BaseCompoundNode)ciEnds.getInNode();
			// check that at least one node belongs to this graph, if so then we
			// can be sure that the other node and edge will.
			if(oneNode.getGraph().equals(this)){
				retVal = this.containsConnection(oneNode, twoNode);
			}
		}
		return retVal;
	}

	public IGraphState<BaseCompoundNode, BaseCompoundEdge> getCurrentState() {
		return stateHandler.createGraphState();
	}

	public final void restoreState(IGraphState<BaseCompoundNode, BaseCompoundEdge> previousState) {
		this.stateHandler.restoreState(previousState);
	}

	public final boolean canCopyHere(ISubCompoundGraph<? extends BaseCompoundNode, ? extends BaseCompoundEdge> subGraph) {
		return subGraph != null && subGraph.isInducedSubgraph()
			&& subGraph.isConsistentSnapShot();
	}
	
	public final void copyHere(ISubCompoundGraph<? extends BaseCompoundNode, ? extends BaseCompoundEdge> subGraph) {
		if(this.debuggingEnabled && !canCopyHere(subGraph)) throw new IllegalArgumentException("Cannot copy graph here");
		
		BaseChildCompoundGraph rootCiGraph = this.getNodeTree().getRootNode().getChildCompoundGraph();
		copyBuilder.setDestinatChildCompoundGraph(rootCiGraph);
		copyBuilder.setSourceSubgraph(subGraph);
		copyBuilder.makeCopy();
		notifyCopyOperationComplete(copyBuilder.getSourceSubgraph(), copyBuilder.getCopiedComponents());
	}
	
	public final ISubCompoundGraph<BaseCompoundNode, BaseCompoundEdge> getCopiedComponents() {
		return copyBuilder.getCopiedComponents();
	}

	public abstract BaseCompoundNodeFactory nodeFactory();

	public abstract BaseCompoundEdgeFactory edgeFactory();

	public abstract BaseSubCompoundGraphFactory subgraphFactory();
	
	   /**
     * A hook method that should be used to provide addition validation for the class inheriting from 
     * this one.
     * @return
     */
    protected abstract boolean hasPassedAdditionalValidation();
	
	public boolean isValid() {
	    boolean retVal = true;
	    retVal = this.getRootNode().getEdgeInList().getUnfilteredEdgeSet().isEmpty()
	        && this.getRootNode().getEdgeOutList().getUnfilteredEdgeSet().isEmpty()
	        && this.getRootNode().getParent() == this.getRootNode()
	        && this.getRootNode().isRemoved() == false;
	    if(retVal) {
	        retVal = this.getRootNode().getChildCompoundGraph().isValid();
	    }
	    else {
	        logger.error("Graph Invalid: root node is inconsistent: " + this.getRootNode());
	    }
	    if(retVal) {
	        retVal = this.hasPassedAdditionalValidation();
	    }
	    else {
	        logger.error("Graph Invalid: addition validation from super class has failed");
	    }
	    return retVal;
	}

	public abstract void notifyNewNode(BaseCompoundNode newNode);

	public abstract void notifyNewEdge(BaseCompoundEdge newEdge);

	BaseSubCompoundGraph internalRemoveSubgraph(ISubCompoundGraph<? extends BaseCompoundNode, ? extends BaseCompoundEdge> subgraph) {
		BaseSubCompoundGraphFactory selnFactory = this.subgraphFactory();
		removeEdges(selnFactory, subgraph.edgeIterator());
		removeNodes(selnFactory, subgraph.nodeIterator());
		return selnFactory.createPermissiveInducedSubgraph();
	}

}