package uk.ed.inf.graph.compound.archetypal;

import java.util.Iterator;

import uk.ed.inf.graph.basic.IBasicPair;
import uk.ed.inf.graph.basic.IBasicSubgraph;
import uk.ed.inf.graph.basic.IBasicSubgraphFactory;
import uk.ed.inf.graph.compound.ICompoundGraph;
import uk.ed.inf.graph.compound.IModifiableCompoundGraph;
import uk.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ed.inf.graph.directed.IDirectedPair;
import uk.ed.inf.graph.state.GraphStateHandler;
import uk.ed.inf.graph.state.IGraphState;
import uk.ed.inf.graph.state.IRestorableGraph;
import uk.ed.inf.graph.util.IndexCounter;
import uk.ed.inf.graph.util.impl.EdgeFromNodeIterator;
import uk.ed.inf.tree.GeneralTree;
import uk.ed.inf.tree.ITree;

public abstract class ArchetypalCompoundGraph implements ICompoundGraph<ArchetypalCompoundNode, ArchetypalCompoundEdge>,
		IRestorableGraph<ArchetypalCompoundNode, ArchetypalCompoundEdge>,
		IModifiableCompoundGraph<ArchetypalCompoundNode, ArchetypalCompoundEdge> {
	private final static int ROOT_NODE_IDX = 0;
	private final static int INIT_EDGE_IDX = 0;
	private final IndexCounter nodeCounter;
	private final IndexCounter edgeCounter;
	private final GeneralTree<ArchetypalCompoundNode> nodeTree;
	private final GraphStateHandler<ArchetypalCompoundNode, ArchetypalCompoundEdge> stateHandler;
	private ArchetypalGraphCopyBuilder copyBuilder;
	
	protected ArchetypalCompoundGraph(ArchetypalGraphCopyBuilder copyBuilder){
		this.nodeCounter = new IndexCounter(ROOT_NODE_IDX);
		this.edgeCounter = new IndexCounter(INIT_EDGE_IDX);
		createNewRootNode(nodeCounter.getLastIndex());
		this.nodeTree = new GeneralTree<ArchetypalCompoundNode>(getRootNode());
		this.stateHandler = new GraphStateHandler<ArchetypalCompoundNode, ArchetypalCompoundEdge>(this);
		this.copyBuilder = copyBuilder;
	}

	protected abstract void createNewRootNode(int indexValue);
	
	protected abstract void createCopyOfRootNode(int newIndexValue, ArchetypalCompoundNode otherRootNode);

	private ITree<ArchetypalCompoundNode> getNodeTree(){
		return this.nodeTree;
	}
	
	protected ArchetypalCompoundGraph(ArchetypalGraphCopyBuilder copyBuilder, ArchetypalCompoundGraph otherGraph){
		this.nodeCounter = new IndexCounter(ROOT_NODE_IDX);
		this.edgeCounter = new IndexCounter(INIT_EDGE_IDX);
		createCopyOfRootNode(nodeCounter.getLastIndex(), otherGraph.getRootNode());
		this.nodeTree = new GeneralTree<ArchetypalCompoundNode>(getRootNode());
		this.stateHandler = new GraphStateHandler<ArchetypalCompoundNode, ArchetypalCompoundEdge>(this);
		IBasicSubgraphFactory<ArchetypalCompoundNode, ArchetypalCompoundEdge> fact = otherGraph.subgraphFactory();
		Iterator<ArchetypalCompoundNode> iter = otherGraph.getNodeTree().getRootNode().getChildCigraph().nodeIterator();
		while(iter.hasNext()){
			ArchetypalCompoundNode level1Node = iter.next();
			fact.addNode(level1Node);
		}
		IBasicSubgraph<ArchetypalCompoundNode, ArchetypalCompoundEdge> subgraph = fact.createInducedSubgraph();
		this.copyHere(subgraph);
		this.copyBuilder = copyBuilder;
	}
	
	public abstract ArchetypalCompoundNode getRootNode();

	public boolean containsDirectedEdge(ArchetypalCompoundNode outNode, ArchetypalCompoundNode inNode) {
		boolean retVal = false;
		if(inNode != null && outNode != null){
			ArchetypalCompoundNode node = this.getNodeTree().getLowestCommonAncestor(inNode, outNode);
			if(node != null){
				retVal = node.getChildCigraph().containsDirectedEdge(outNode, inNode);
			}
		}
		return retVal;
	}
	
	public boolean canCreateEdges() {
		return true;
	}

	public boolean canCreateNodes() {
		return false;
	}

	public boolean canCreateSubgraphs() {
		return true;
	}

	public boolean canRemoveSubgraphs() {
		return true;
	}

	public boolean containsConnection(ArchetypalCompoundNode thisNode, ArchetypalCompoundNode thatNode) {
		boolean retVal = false;
		if(thisNode != null && thatNode != null){
			ArchetypalCompoundNode node = this.getNodeTree().getLowestCommonAncestor(thisNode, thatNode);
			if(node != null){
				retVal = node.getChildCigraph().containsConnection(thisNode, thatNode);
			}
		}
		return retVal;
	}

	public boolean containsEdge(ArchetypalCompoundEdge edge) {
		boolean retVal = false;
		if(edge != null && edge.getGraph().equals(this)){
			retVal = edge.getOwningChildGraph().containsEdge(edge);
		}
		return retVal;
	}

	public boolean containsEdge(int edgeIdx) {
		boolean retVal = false;
		Iterator<ArchetypalCompoundNode> iter = this.getNodeTree().levelOrderIterator();
		while(iter.hasNext()){
			ArchetypalCompoundNode node = iter.next();
			retVal = node.getChildCigraph().containsEdge(edgeIdx);
			if(retVal == true){
				break;
			}
		}
		return retVal;
	}

	public boolean containsNode(int nodeIdx) {
		return this.getNodeTree().containsNode(nodeIdx);
	}

	public boolean containsNode(ArchetypalCompoundNode node) {
		boolean retVal = false;
		if(node != null){
			retVal = this.containsNode(node.getIndex());
		}
		return retVal;
	}

	public ArchetypalCompoundEdge getEdge(int edgeIdx) {
		Iterator<ArchetypalCompoundEdge> iter = this.edgeIterator();
		ArchetypalCompoundEdge retVal = null;
		while(iter.hasNext() && retVal == null){
			ArchetypalCompoundEdge edge = iter.next();
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
	public Iterator<ArchetypalCompoundEdge> edgeIterator() {
		return new EdgeFromNodeIterator<ArchetypalCompoundNode, ArchetypalCompoundEdge>(this.getNodeTree().levelOrderIterator());
	}

	public ArchetypalCompoundNode getNode(int nodeIdx) {
		return this.getNodeTree().get(nodeIdx);
	}

	public Iterator<ArchetypalCompoundNode> nodeIterator() {
		return this.getNodeTree().levelOrderIterator();
	}

	public int getNumEdges() {
		Iterator<ArchetypalCompoundEdge> iter = this.edgeIterator();
		int cntr = 0;
		while(iter.hasNext()){
			iter.next();
			cntr++;
		}
		return cntr;
	}

	public int getNumNodes() {
		return this.getNodeTree().size();
	}

	public void removeSubgraph(IBasicSubgraph<ArchetypalCompoundNode, ArchetypalCompoundEdge> subgraph) {
		if(subgraph == null) throw new IllegalArgumentException("subgraph cannot be null");
		if(subgraph.getSuperGraph() != this) throw new IllegalArgumentException("The subgraph must belong to this graph");
		removeEdges(subgraph.edgeIterator());
		removeNodes(subgraph.nodeIterator());
	}


	private void removeEdges(Iterator<ArchetypalCompoundEdge> edgeIterator){
		while(edgeIterator.hasNext()){
			ArchetypalCompoundEdge edge = (ArchetypalCompoundEdge)edgeIterator.next();
			edge.markRemoved(true);
		}
	}
	
	private void removeNodes(Iterator<ArchetypalCompoundNode> nodeIterator){
		while(nodeIterator.hasNext()){
			ArchetypalCompoundNode node = (ArchetypalCompoundNode)nodeIterator.next();
			if(node.equals(this.getRootNode())){
				throw new IllegalStateException("Cannot remove the root node from a compound graph");
			}
			node.markRemoved(true);
			// remove edges associated with node
			Iterator<ArchetypalCompoundEdge> edgeIter = node.edgeIterator();
			while(edgeIter.hasNext()){
				ArchetypalCompoundEdge edge = edgeIter.next();
				edge.markRemoved(true);
			}
		}
	}
	
	ArchetypalCompoundNode getLcaNode(ArchetypalCompoundNode iInNode, ArchetypalCompoundNode iOutNode){
		if(iInNode == null || iOutNode == null) throw new IllegalArgumentException("parameters cannot be null");
		
		ArchetypalCompoundNode inNode = (ArchetypalCompoundNode)iInNode;
		ArchetypalCompoundNode outNode = (ArchetypalCompoundNode)iOutNode;
		return this.getNodeTree().getLowestCommonAncestor(inNode, outNode);
	}
	
//	IndexCounter getNodeCounter(){
//		return this.nodeCounter;
//	}
//
//	IndexCounter getEdgeCounter(){
//		return this.edgeCounter;
//	}

	/**
	 * Tests if the ends define one or more directed edges.
	 */
	public boolean containsDirectedEdge(IDirectedPair<ArchetypalCompoundNode, ArchetypalCompoundEdge> ends) {
		boolean retVal = false;
		if(ends != null){
			ArchetypalCompoundNode outNode = ends.getOutNode();
			ArchetypalCompoundNode inNode = ends.getInNode();
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
	public boolean containsConnection(IBasicPair<ArchetypalCompoundNode, ArchetypalCompoundEdge> ends) {
		boolean retVal = false;
		if(ends != null && ends instanceof IDirectedPair){
			// since this is a directed graph a valid edge pair must be an IDirectedPair
			IDirectedPair<ArchetypalCompoundNode, ArchetypalCompoundEdge> ciEnds = (IDirectedPair<ArchetypalCompoundNode, ArchetypalCompoundEdge>)ends;
			ArchetypalCompoundNode oneNode = ciEnds.getOutNode();
			ArchetypalCompoundNode twoNode = ciEnds.getInNode();
			// check that at least one node belongs to this graph, if so then we
			// can be sure that the other node and edge will.
			if(oneNode.getGraph().equals(this)){
				retVal = this.containsConnection(oneNode, twoNode);
			}
		}
		return retVal;
	}

	public IGraphState<ArchetypalCompoundNode, ArchetypalCompoundEdge> getCurrentState() {
		return stateHandler.createGraphState();
	}

	public void restoreState(IGraphState<ArchetypalCompoundNode, ArchetypalCompoundEdge> previousState) {
		this.stateHandler.restoreState(previousState);
	}

	public boolean canCopyHere(IBasicSubgraph<ArchetypalCompoundNode, ArchetypalCompoundEdge> subGraph) {
		return subGraph != null && subGraph instanceof ISubCompoundGraph && subGraph.isInducedSubgraph()
			&& subGraph.isConsistentSnapShot();
	}
	
	public void copyHere(IBasicSubgraph<ArchetypalCompoundNode, ArchetypalCompoundEdge> iSubGraph) {
		if(!canCopyHere(iSubGraph)) throw new IllegalArgumentException("Cannot copy graph here");
		
		ISubCompoundGraph<ArchetypalCompoundNode, ArchetypalCompoundEdge> subGraph = (ISubCompoundGraph<ArchetypalCompoundNode, ArchetypalCompoundEdge>)iSubGraph;
		
		ArchetypalChildCompoundGraph rootCiGraph = this.getNodeTree().getRootNode().getChildCigraph();
//		ChildCompoundGraphBuilder copyBuilder = new ChildCompoundGraphBuilder(rootCiGraph, subGraph);
		copyBuilder.setDestinatChildCompoundGraph(rootCiGraph);
		copyBuilder.setSourceSubgraph(subGraph);
		copyBuilder.copyNodes();
		copyBuilder.copyEquivalentEdges();
	}
	
	public IBasicSubgraph<ArchetypalCompoundNode, ArchetypalCompoundEdge> getCopiedComponents() {
		// TODO Auto-generated method stub
		return null;
	}


	protected final IndexCounter getNodeCounter(){
		return this.nodeCounter;
	}
	
	
	protected final IndexCounter getEdgeCounter(){
		return this.edgeCounter;
	}

	public void clear() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Implement this method!");
	}
	
	public abstract ArchetypalCompoundNodeFactory nodeFactory();

	public abstract ArchetypalCompoundEdgeFactory edgeFactory();
}
