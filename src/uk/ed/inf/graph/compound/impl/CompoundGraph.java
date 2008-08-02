package uk.ed.inf.graph.compound.impl;

import java.util.Iterator;

import uk.ed.inf.tree.GeneralTree;
import uk.ed.inf.tree.ITree;

public class CompoundGraph extends ArchetypalCompoundGraph {
	private final ITree<ArchetypalCompoundNode> nodeTree;
	private final SubCompoundGraphFactory subgraphFactory;
	private final CompoundNodeFactory nodeFactory;
	private final CompoundEdgeFactory edgeFactory;
	
	public CompoundGraph(){
		super();
		CompoundNode rootNode = new CompoundNode(this, this.getNodeCounter().getLastIndex());
		this.nodeTree = new GeneralTree<ArchetypalCompoundNode>(rootNode);
		this.subgraphFactory = new SubCompoundGraphFactory(this);
		this.edgeFactory = new CompoundEdgeFactory(this);
		this.nodeFactory = new CompoundNodeFactory(rootNode);
	}

	public CompoundGraph(CompoundGraph otherGraph){
		this();
		SubCompoundGraphFactory fact = otherGraph.subgraphFactory();
		Iterator<ArchetypalCompoundNode> iter = otherGraph.nodeTree.getRootNode().getChildCigraph().nodeIterator();
		while(iter.hasNext()){
			ArchetypalCompoundNode level1Node = iter.next();
			fact.addNode(level1Node);
		}
		SubCompoundGraph subgraph = fact.createInducedSubgraph();
		this.copyHere(subgraph);
	}
	
	@Override
	protected final ITree<ArchetypalCompoundNode> getNodeTree(){
		return this.nodeTree;
	}
	
	@Override
	public CompoundNode getRoot() {
		return (CompoundNode)this.nodeTree.getRootNode();
	}

//	public boolean containsDirectedEdge(CompoundNode iInNode, CompoundNode iOutNode) {
//		boolean retVal = false;
//		if(iInNode != null && iOutNode != null){
//			CompoundNode inNode = (CompoundNode)iInNode;
//			CompoundNode outNode = (CompoundNode)iOutNode;
//			CompoundNode node = this.nodeTree.getLowestCommonAncestor(inNode, outNode);
//			if(node != null){
//				retVal = node.getChildCigraph().containsDirectedEdge(inNode, outNode);
//			}
//		}
//		return retVal;
//	}
//
//	public boolean canCreateEdges() {
//		return true;
//	}
//
//	public boolean canCreateNodes() {
//		return false;
//	}
//
//	public boolean canCreateSubgraphs() {
//		return true;
//	}
//
//	public boolean canRemoveSubgraphs() {
//		return true;
//	}
//
//	public boolean containsConnection(CompoundNode iThisNode, CompoundNode iThatNode) {
//		boolean retVal = false;
//		if(iThisNode != null && iThatNode != null){
//			CompoundNode thisNode = (CompoundNode)iThisNode;
//			CompoundNode thatNode = (CompoundNode)iThatNode;
//			CompoundNode node = this.nodeTree.getLowestCommonAncestor(thisNode, thatNode);
//			if(node != null){
//				retVal = node.getChildCigraph().containsConnection(thisNode, thatNode);
//			}
//		}
//		return retVal;
//	}
//
//	public boolean containsEdge(CompoundEdge edge) {
//		boolean retVal = false;
//		if(edge != null){
//			IDirectedPair<CompoundNode, CompoundEdge> ends = edge.getConnectedNodes();
//			retVal = this.containsConnection(ends.getOutNode(), ends.getInNode());
//		}
//		return retVal;
//	}
//
//	public boolean containsEdge(int edgeIdx) {
//		boolean retVal = false;
//		Iterator<CompoundNode> iter = this.nodeTree.levelOrderIterator();
//		while(iter.hasNext()){
//			CompoundNode node = iter.next();
//			retVal = node.getChildCigraph().containsEdge(edgeIdx);
//			if(retVal == true){
//				break;
//			}
//		}
//		return retVal;
//	}
//
//	public boolean containsNode(int nodeIdx) {
//		return this.nodeTree.containsNode(nodeIdx);
//	}
//
//	public boolean containsNode(CompoundNode node) {
//		boolean retVal = false;
//		if(node != null){
//			retVal = this.containsNode(node.getIndex());
//		}
//		return retVal;
//	}

	public CompoundEdgeFactory edgeFactory() {
		return this.edgeFactory;
	}

	public CompoundNodeFactory nodeFactory() {
		return this.nodeFactory;
	}

	public SubCompoundGraphFactory subgraphFactory() {
		return this.subgraphFactory;
	}

//	public CompoundEdge getEdge(int edgeIdx) {
//		Iterator<CompoundEdge> iter = this.edgeIterator();
//		CompoundEdge retVal = null;
//		while(iter.hasNext() && retVal == null){
//			CompoundEdge edge = iter.next();
//			if(edge.getIndex() == edgeIdx){
//				retVal = edge;
//			}
//		}
//		return retVal;
//	}
//
//	/**
//	 * Returns all edges in tree level-node iteration order. For each node the edges are returned in the same
//	 * order as the CiNode edge iterator. Returns both undirected and directed nodes.
//	 */
//	public Iterator<CompoundEdge> edgeIterator() {
//		return new EdgeFromNodeIterator<CompoundNode, CompoundEdge>(this.nodeTree.levelOrderIterator());
//	}
//
//	public CompoundNode getNode(int nodeIdx) {
//		return this.nodeTree.get(nodeIdx);
//	}
//
//	public Iterator<CompoundNode> nodeIterator() {
//		return this.nodeTree.levelOrderIterator();
//	}
//
//	public int getNumEdges() {
//		Iterator<CompoundEdge> iter = this.edgeIterator();
//		int cntr = 0;
//		while(iter.hasNext()){
//			iter.next();
//			cntr++;
//		}
//		return cntr;
//	}
//
//	public int getNumNodes() {
//		return this.nodeTree.size();
//	}
//
//	public void removeSubgraph(IBasicSubgraph<CompoundNode, CompoundEdge> subgraph) {
//		if(subgraph == null) throw new IllegalArgumentException("subgraph cannot be null");
//		if(subgraph.getSuperGraph() != this) throw new IllegalArgumentException("The subgraph must belong to this graph");
//		removeEdges(subgraph.edgeIterator());
//		removeNodes(subgraph.nodeIterator());
//	}
//
//
//	private void removeEdges(Iterator<CompoundEdge> edgeIterator){
//		while(edgeIterator.hasNext()){
//			CompoundEdge edge = (CompoundEdge)edgeIterator.next();
//			edge.markRemoved(true);
//		}
//	}
//	
//	private void removeNodes(Iterator<CompoundNode> nodeIterator){
//		while(nodeIterator.hasNext()){
//			CompoundNode node = (CompoundNode)nodeIterator.next();
//			node.markRemoved(true);
//		}
//	}
//	
//	CompoundNode getLcaNode(CompoundNode iInNode, CompoundNode iOutNode){
//		if(iInNode == null || iOutNode == null) throw new IllegalArgumentException("parameters cannot be null");
//		
//		CompoundNode inNode = (CompoundNode)iInNode;
//		CompoundNode outNode = (CompoundNode)iOutNode;
//		return this.nodeTree.getLowestCommonAncestor(inNode, outNode);
//	}
//	
//	IndexCounter getNodeCounter(){
//		return this.nodeCounter;
//	}
//
//	IndexCounter getEdgeCounter(){
//		return this.edgeCounter;
//	}
//
//	/**
//	 * Tests if the ends define one or more directed edges.
//	 */
//	public boolean containsDirectedEdge(IDirectedPair<CompoundNode, CompoundEdge> ends) {
//		boolean retVal = false;
//		if(ends != null){
//			CompoundNode outNode = ends.getOutNode();
//			CompoundNode inNode = ends.getInNode();
//			// check that at least one node belongs to this graph, if so then we
//			// can be sure that the other node and edge will.
//			if(outNode.getGraph().equals(this)){
//				retVal = outNode.hasOutEdgeTo(inNode);
//			}
//		}
//		return retVal;
//	}
//
//	/**
//	 * Tests if the ends define any edge in this graph. Note that the node pair must
//	 * be created by this graph as the method expects <code>ends</code> to be of type
//	 * <code>IDirectedPair</code> and will return false if it is not.
//	 * @param ends the pair of nodes that may define the edges of an edge.
//	 * @return true if it does, false otherwise.  
//	 */
//	public boolean containsConnection(IBasicPair<CompoundNode, CompoundEdge> ends) {
//		boolean retVal = false;
//		if(ends != null && ends instanceof IDirectedPair){
//			// since this is a directed graph a valid edge pair must be an IDirectedPair
//			IDirectedPair<CompoundNode, CompoundEdge> ciEnds = (IDirectedPair<CompoundNode, CompoundEdge>)ends;
//			CompoundNode oneNode = ciEnds.getOutNode();
//			CompoundNode twoNode = ciEnds.getInNode();
//			// check that at least one node belongs to this graph, if so then we
//			// can be sure that the other node and edge will.
//			if(oneNode.getGraph().equals(this)){
//				retVal = this.containsConnection(oneNode, twoNode);
//			}
//		}
//		return retVal;
//	}
//
//	public IGraphState<CompoundNode, CompoundEdge> getCurrentState() {
//		return stateHandler.createGraphState();
//	}
//
//	public void restoreState(IGraphState<CompoundNode, CompoundEdge> previousState) {
//		this.stateHandler.restoreState(previousState);
//	}
//
//	public boolean canCopyHere(IBasicSubgraph<CompoundNode, CompoundEdge> subGraph) {
//		return subGraph != null && subGraph instanceof ISubCompoundGraph && subGraph.isInducedSubgraph()
//			&& subGraph.isConsistentSnapShot();
//	}
//	
//	public void copyHere(IBasicSubgraph<CompoundNode, CompoundEdge> iSubGraph) {
//		if(!canCopyHere(iSubGraph)) throw new IllegalArgumentException("Cannot copy graph here");
//		
//		ISubCompoundGraph<CompoundNode, CompoundEdge> subGraph = (ISubCompoundGraph<CompoundNode, CompoundEdge>)iSubGraph;
//		
//		ChildCompoundGraph rootCiGraph = this.nodeTree.getRootNode().getChildCigraph();
//		ChildCompoundGraphBuilder copyBuilder = new ChildCompoundGraphBuilder(rootCiGraph, subGraph);
//		copyBuilder.copyNodes();
//		copyBuilder.copyEquivalentEdges();
//	}

	public CompoundGraph createCopy() {
		return new CompoundGraph(this);
	}

//	private static class RootNodeColourHandler implements INodeColourHandler<CompoundNode, CompoundEdge> {
//		private Integer colour;
//		private CompoundNode node;
//		
//		public RootNodeColourHandler(Integer initialColour){
//			this.colour = initialColour;
//		}
//		
//		public RootNodeColourHandler(RootNodeColourHandler other){
//			this.colour = null;
//			this.node = null;
//		}
//		
//		
//		public Integer copyColour(CompoundNode newNode) {
//			return this.colour;
//		}
//
//		public INodeColourHandler<CompoundNode, CompoundEdge> createCopy() {
//			return new RootNodeColourHandler(this);
//		}
//
//		public Object getColour() {
//			return this.colour;
//		}
//
//		public CompoundNode getNode() {
//			return this.node;
//		}
//
//		public void setColour(Object colour) {
//			if(!(colour instanceof Integer)) new IllegalArgumentException("Expected a colour of class Integer");
//			
//			Integer intColour = (Integer)colour;
//			this.colour = intColour;
//		}
//		
//		public void setNode(CompoundNode node){
//			this.node = node;
//		}
//	}
}
