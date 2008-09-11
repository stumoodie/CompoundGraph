package uk.ed.inf.graph.compound.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import uk.ed.inf.graph.basic.IBasicPair;
import uk.ed.inf.graph.basic.IBasicSubgraph;
import uk.ed.inf.graph.basic.IBasicSubgraphFactory;
import uk.ed.inf.graph.compound.ICompoundGraph;
import uk.ed.inf.graph.compound.ICompoundGraphCopyBuilder;
import uk.ed.inf.graph.compound.IModifiableCompoundGraph;
import uk.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ed.inf.graph.compound.impl.CompoundEdge;
import uk.ed.inf.graph.compound.impl.CompoundNode;
import uk.ed.inf.graph.directed.IDirectedPair;
import uk.ed.inf.graph.state.GraphStateHandler;
import uk.ed.inf.graph.state.IGraphState;
import uk.ed.inf.graph.state.IRestorableGraph;
import uk.ed.inf.graph.util.IndexCounter;
import uk.ed.inf.graph.util.impl.EdgeFromNodeIterator;
import uk.ed.inf.tree.ITree;

public abstract class BaseCompoundGraph implements ICompoundGraph<BaseCompoundNode, BaseCompoundEdge>,
		IRestorableGraph<BaseCompoundNode, BaseCompoundEdge>,
		IModifiableCompoundGraph<BaseCompoundNode, BaseCompoundEdge> {
	private final GraphStateHandler<BaseCompoundNode, BaseCompoundEdge> stateHandler;
	private ICompoundGraphCopyBuilder copyBuilder;
	
	protected BaseCompoundGraph(ICompoundGraphCopyBuilder copyBuilder){
		this.stateHandler = new GraphStateHandler<BaseCompoundNode, BaseCompoundEdge>(this);
		this.copyBuilder = copyBuilder;
	}

	protected abstract IndexCounter getNodeCounter();

	protected abstract int getRootNodeIndex();
	
	protected abstract IndexCounter getEdgeCounter();

//	protected abstract void createCopyOfRootNode(int newIndexValue, BaseCompoundNode otherRootNode);

	protected abstract ITree<BaseCompoundNode> getNodeTree();

	/**
	 * To be used by copy constructor. After this constructor has been called extending classes should ensure that 
	 *  the root node is copied, and a new Tree is created. The calling constructor can then call
	 *  <code>perfromCopy()</code> to  actually copy the graph;
	 * @param copyBuilder
	 * @param otherGraph
	 */
	protected void performCopy(BaseCompoundGraph otherGraph){
		IBasicSubgraphFactory<BaseCompoundNode, BaseCompoundEdge> fact = otherGraph.subgraphFactory();
		Iterator<BaseCompoundNode> iter = otherGraph.getNodeTree().getRootNode().getChildCompoundGraph().nodeIterator();
		while(iter.hasNext()){
			BaseCompoundNode level1Node = iter.next();
			fact.addNode(level1Node);
		}
		IBasicSubgraph<BaseCompoundNode, BaseCompoundEdge> subgraph = fact.createInducedSubgraph();
		this.copyHere(subgraph);
	}
	
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
	public final Iterator<BaseCompoundEdge> edgeIterator() {
		return new EdgeFromNodeIterator<BaseCompoundNode, BaseCompoundEdge>(this.getNodeTree().levelOrderIterator());
	}

	public BaseCompoundNode getNode(int nodeIdx) {
		return this.getNodeTree().get(nodeIdx);
	}

	public final Iterator<BaseCompoundNode> nodeIterator() {
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

	public final void removeSubgraph(IBasicSubgraph<BaseCompoundNode, BaseCompoundEdge> subgraph) {
		if(subgraph == null) throw new IllegalArgumentException("subgraph cannot be null");
		if(subgraph.getSuperGraph() != this) throw new IllegalArgumentException("The subgraph must belong to this graph");
		removeEdges(subgraph.edgeIterator());
		removeNodes(subgraph.nodeIterator());
	}


	private void removeEdges(Iterator<BaseCompoundEdge> edgeIterator){
		while(edgeIterator.hasNext()){
			BaseCompoundEdge edge = (BaseCompoundEdge)edgeIterator.next();
			edge.markRemoved(true);
		}
	}
	
	private void removeNodes(Iterator<BaseCompoundNode> nodeIterator){
		while(nodeIterator.hasNext()){
			BaseCompoundNode node = (BaseCompoundNode)nodeIterator.next();
			if(node.equals(this.getRootNode())){
				throw new IllegalStateException("Cannot remove the root node from a compound graph");
			}
			node.markRemoved(true);
			// remove edges associated with node
			Iterator<BaseCompoundEdge> edgeIter = node.edgeIterator();
			while(edgeIter.hasNext()){
				BaseCompoundEdge edge = edgeIter.next();
				edge.markRemoved(true);
			}
		}
	}
	
	final BaseCompoundNode getLcaNode(BaseCompoundNode iInNode, BaseCompoundNode iOutNode){
		if(iInNode == null || iOutNode == null) throw new IllegalArgumentException("parameters cannot be null");
		
		BaseCompoundNode inNode = (BaseCompoundNode)iInNode;
		BaseCompoundNode outNode = (BaseCompoundNode)iOutNode;
		return this.getNodeTree().getLowestCommonAncestor(inNode, outNode);
	}
	
	/**
	 * Tests if the ends define one or more directed edges.
	 */
	public final boolean containsDirectedEdge(IDirectedPair<BaseCompoundNode, BaseCompoundEdge> ends) {
		boolean retVal = false;
		if(ends != null){
			BaseCompoundNode outNode = ends.getOutNode();
			BaseCompoundNode inNode = ends.getInNode();
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
	public final boolean containsConnection(IBasicPair<BaseCompoundNode, BaseCompoundEdge> ends) {
		boolean retVal = false;
		if(ends != null && ends instanceof IDirectedPair){
			// since this is a directed graph a valid edge pair must be an IDirectedPair
			IDirectedPair<BaseCompoundNode, BaseCompoundEdge> ciEnds = (IDirectedPair<BaseCompoundNode, BaseCompoundEdge>)ends;
			BaseCompoundNode oneNode = ciEnds.getOutNode();
			BaseCompoundNode twoNode = ciEnds.getInNode();
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

	public final boolean canCopyHere(IBasicSubgraph<BaseCompoundNode, BaseCompoundEdge> subGraph) {
		return subGraph != null && subGraph instanceof ISubCompoundGraph && subGraph.isInducedSubgraph()
			&& subGraph.isConsistentSnapShot();
	}
	
	public final void copyHere(IBasicSubgraph<BaseCompoundNode, BaseCompoundEdge> iSubGraph) {
		if(!canCopyHere(iSubGraph)) throw new IllegalArgumentException("Cannot copy graph here");
		
		ISubCompoundGraph<BaseCompoundNode, BaseCompoundEdge> subGraph = (ISubCompoundGraph<BaseCompoundNode, BaseCompoundEdge>)iSubGraph;
		
		BaseChildCompoundGraph rootCiGraph = this.getNodeTree().getRootNode().getChildCompoundGraph();
//		ChildCompoundGraphBuilder copyBuilder = new ChildCompoundGraphBuilder(rootCiGraph, subGraph);
		copyBuilder.setDestinatChildCompoundGraph(rootCiGraph);
		copyBuilder.setSourceSubgraph(subGraph);
		copyBuilder.copyNodes();
		copyBuilder.copyEquivalentEdges();
	}
	
	public final IBasicSubgraph<BaseCompoundNode, BaseCompoundEdge> getCopiedComponents() {
		// TODO Auto-generated method stub
		return null;
	}


	public final void clear() {

		this.removeEdges(this.edgeIterator()) ;
		Iterator<BaseCompoundNode> nodeIterator = this.nodeIterator() ;
		
		List<BaseCompoundNode> allNodesButRootNode = new ArrayList<BaseCompoundNode> () ;
		
		while ( nodeIterator.hasNext())
		{
			BaseCompoundNode aNode = nodeIterator.next() ;
			if ( aNode != this.getRootNode())
			{
				allNodesButRootNode.add(aNode) ;
			}
		}
		
		this.removeNodes(allNodesButRootNode.iterator()) ;
	}
	
	public abstract BaseCompoundNodeFactory nodeFactory();

	public abstract BaseCompoundEdgeFactory edgeFactory();

	public abstract BaseSubCompoundGraphFactory subgraphFactory();
}
