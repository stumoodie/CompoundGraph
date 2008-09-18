package uk.ed.inf.graph.impl;

import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import uk.ed.inf.graph.basic.IBasicPair;
import uk.ed.inf.graph.basic.ISubgraphAlgorithms;
import uk.ed.inf.graph.undirected.IUndirectedPair;
import uk.ed.inf.graph.undirected.IUndirectedSubgraph;
import uk.ed.inf.graph.util.SubgraphAlgorithms;
import uk.ed.inf.graph.util.impl.AdjList;
import uk.ed.inf.graph.util.impl.EdgeSet;

public final class Subgraph implements IUndirectedSubgraph<Node, Edge> {
	private final Graph superGraph;
	private final SortedSet<Node> nodeList;
	// edges that are attached to zero or one node in the subgraph 
	private final EdgeSet<Node, Edge> edgeSet;
	private final AdjList adjList;
	
	Subgraph(Graph superGraph){
		this.superGraph = superGraph;
		this.nodeList = new TreeSet<Node>(new Comparator<Node>(){
			public int compare(Node o1, Node o2) {
				return o1.getIndex() < o2.getIndex() ? -1 : (o1.getIndex() == o2.getIndex() ? 0 : 1);
			}
		});
		this.edgeSet = new EdgeSet<Node, Edge>();
//		this.edgeList = new TreeSet<Edge>(new Comparator<Edge>(){
//			public int compare(Edge o1, Edge o2) {
//				return o1.getIndex() < o2.getIndex() ? -1 : (o1.getIndex() == o2.getIndex() ? 0 : 1);
//			}
//		});
		this.adjList = new AdjList(this.superGraph.getNumNodes());
	}
	
	public boolean isInducedSubgraph(){
		ISubgraphAlgorithms<Node, Edge> alg = new SubgraphAlgorithms<Node, Edge>(this);
		return alg.isInducedSubgraph();
	}
	
//	public boolean canCopyHere(IBasicSubgraph<Node, Edge> subGraph) {
//		return subGraph != null && subGraph.isInducedSubgraph();
//	}
//
//	
//	public void copyHere(IBasicSubgraph<Node, Edge> subGraph) {
//		if(canCopyHere(subGraph)) throw new IllegalArgumentException("Cannot copy graph here");
//		
//		Map<Node, Node> nodeEquivalence = new HashMap<Node, Node>();
//		NodeFactory nodeFactory = this.superGraph.basicNodeFactory();
//		Iterator<Node> nodeIter = subGraph.nodeIterator();
//		while(nodeIter.hasNext()){
//			Node oldNode = nodeIter.next();
//			Node newNode = nodeFactory.createNode();
//			this.nodeList.add(newNode);
//			nodeEquivalence.put(oldNode, newNode);
//		}
//		EdgeFactory edgeFactory = this.superGraph.basicEdgeFactory();
//		Iterator<Edge> edgeIter = subGraph.edgeIterator();
//		while(edgeIter.hasNext()){
//			Edge oldEdge = edgeIter.next();
//			Node newNodeOne = nodeEquivalence.get(oldEdge.getConnectedNodes().getOneNode());
//			Node newNodeTwo = nodeEquivalence.get(oldEdge.getConnectedNodes().getTwoNode());
//			edgeFactory.setPair(newNodeOne, newNodeTwo);
//			edgeFactory.createEdge();
//		}
//	}

	public Graph getSuperGraph() {
		return this.superGraph;
	}


//	public boolean canCreateEdges() {
//		return false;
//	}
//
//	public boolean canCreateNodes() {
//		return false;
//	}
//
//	public boolean canCreateSubgraphs() {
//		return false;
//	}
//
//	public boolean canRemoveSubgraphs() {
//		return false;
//	}

	public boolean containsNode(Node node) {
		return this.nodeList.contains(node);
	}

	public boolean containsConnection(Node thisNode, Node thatNode) {
		return this.adjList.isConnected(thisNode.getIndex(), thatNode.getIndex());
	}
	
	public boolean containsEdge(Edge edge){
		return this.edgeSet.contains(edge);
	}

//	public IBasicEdgeFactory<Node, Edge> basicEdgeFactory() {
//		throw new UnsupportedOperationException();
//	}
//
//	public IBasicNodeFactory<Node, Edge> basicNodeFactory() {
//		throw new UnsupportedOperationException();
//	}
//
//	public IBasicSubgraphFactory<Node, Edge> basicSubgraphFactory() {
//		throw new UnsupportedOperationException();
//	}

	public Iterator<Edge> edgeIterator() {
		return this.edgeSet.iterator();
	}

	public Iterator<Node> nodeIterator() {
		return this.nodeList.iterator();
	}

	public int getNumEdges() {
		return this.edgeSet.size();
	}

	public int getNumNodes() {
		return this.nodeList.size();
	}

//	public void removeSubgraph(IBasicSubgraph<Node, Edge> node) {
//		throw new UnsupportedOperationException();
//	}

	void addNode(Node node) {
		this.nodeList.add(node);
	}
	
	void addConnectedEdge(Edge edge){
		NodePair pair = edge.getConnectedNodes();
		this.adjList.addEdge(edge.getIndex(), pair.getOneNode().getIndex(), pair.getTwoNode().getIndex());
	}
	
	void addDanglingEdge(Edge edge){
		this.edgeSet.add(edge);
	}

	public boolean containsEdge(int edgeIdx) {
		return getEdge(edgeIdx) != null;
	}

	public boolean containsNode(int nodeIdx) {
		return this.adjList.containsNode(nodeIdx);
	}

	public Edge getEdge(int edgeIdx) {
		return this.edgeSet.get(edgeIdx);
	}

	public Node getNode(int nodeIdx) {
		Node retVal = null;
		// assume is sorted
		for(Node node : this.nodeList){
			if(node.getIndex() > nodeIdx){
				break;
			}
			if(node.getIndex() == nodeIdx){
				retVal = node;
				break;
			}
		}
		return retVal;
	}

	public boolean isConsistentSnapShot() {
		boolean retVal = true;
		for(Node node : this.nodeList){
			if(node.isRemoved()){
				retVal = false;
				break;
			}
		}
		if(retVal){
			for(Edge edge : this.edgeSet){
				if(edge.isRemoved()){
					retVal = false;
					break;
				}
			}
		}
		return retVal;
	}

	public boolean containsConnection(IBasicPair<? extends Node, ? extends Edge> ends) {
		IUndirectedPair<? extends Node, ? extends Edge> undirectedEnds = (IUndirectedPair<? extends Node, ? extends Edge>)ends;
		return this.adjList.isConnected(undirectedEnds.getOneNode().getIndex(),
				undirectedEnds.getTwoNode().getIndex());
	}
}
