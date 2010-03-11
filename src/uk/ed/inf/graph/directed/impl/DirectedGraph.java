package uk.ed.inf.graph.directed.impl;

import java.util.Iterator;

import uk.ed.inf.graph.basic.IBasicEdgeFactory;
import uk.ed.inf.graph.basic.IBasicNodeFactory;
import uk.ed.inf.graph.basic.IBasicPair;
import uk.ed.inf.graph.basic.IBasicSubgraph;
import uk.ed.inf.graph.basic.IBasicSubgraphFactory;
import uk.ed.inf.graph.basic.IModifiableGraph;
import uk.ed.inf.graph.basic.listeners.IGraphChangeListener;
import uk.ed.inf.graph.directed.IDirectedGraph;
import uk.ed.inf.graph.directed.IDirectedPair;
import uk.ed.inf.graph.util.IDirectedEdgeSet;
import uk.ed.inf.graph.util.INodeSet;
import uk.ed.inf.graph.util.IndexCounter;
import uk.ed.inf.graph.util.impl.DirectedEdgeSet;
import uk.ed.inf.graph.util.impl.NodeSet;

public class DirectedGraph implements IDirectedGraph<DirectedNode, DirectedEdge>, IModifiableGraph<DirectedNode, DirectedEdge> {
	private final INodeSet<DirectedNode, DirectedEdge> nodes;
	private final IDirectedEdgeSet<DirectedNode, DirectedEdge> edges;
	private final IndexCounter indexCounter;
	private final DirectedNodeFactory nodeFactory;
	
	public DirectedGraph(){
		this.indexCounter = new IndexCounter();
		this.nodes = new NodeSet<DirectedNode, DirectedEdge>();
		this.edges = new DirectedEdgeSet<DirectedNode, DirectedEdge>();
		this.nodeFactory = new DirectedNodeFactory(this);
	}
	
	public boolean containsDirectedEdge(DirectedNode outNode, DirectedNode inNode) {
		return this.edges.containsDirectedEdge(outNode, inNode);
	}

	public boolean containsDirectedEdge(IDirectedPair<? extends DirectedNode, ? extends DirectedEdge> ends) {
		boolean retVal = false;
		if(ends != null){
			DirectedNode outNode = ends.getOutNode();
			DirectedNode inNode = ends.getInNode();
			// check that both nodes exist in this subgraph
			retVal = outNode.hasOutEdgeTo(inNode);
		}
		return retVal;
	}

	public boolean containsConnection(DirectedNode thisNode, DirectedNode thatNode) {
		return this.edges.contains(thisNode, thatNode);
	}

	public boolean containsConnection(IBasicPair<? extends DirectedNode, ? extends DirectedEdge> ends) {
		boolean retVal = false;
		if(ends != null && ends instanceof DirectedPair){
			// since this is a directed graph a valid edge pair must be an IDirectedPair
			DirectedPair ciEnds = (DirectedPair)ends;
			DirectedNode oneNode = ciEnds.getOutNode();
			DirectedNode twoNode = ciEnds.getInNode();
			// check that the nodes belong to this subgraph.
			retVal = this.containsConnection(oneNode, twoNode);
		}
		return retVal;
	}

	public boolean containsEdge(DirectedEdge edge) {
		return this.edges.contains(edge);
	}

	public boolean containsEdge(int edgeIdx) {
		return this.edges.contains(edgeIdx);
	}

	public boolean containsNode(int nodeIdx) {
		return this.nodes.contains(nodeIdx);
	}

	public boolean containsNode(DirectedNode node) {
		return this.nodes.contains(node);
	}

	public Iterator<DirectedEdge> edgeIterator() {
		return this.edges.iterator();
	}

	public DirectedEdge getEdge(int edgeIdx) {
		return this.edges.get(edgeIdx);
	}

	public DirectedNode getNode(int nodeIdx) {
		return this.nodes.get(nodeIdx);
	}

	public int getNumEdges() {
		return this.edges.size();
	}

	public int getNumNodes() {
		return this.nodes.size();
	}

	public Iterator<DirectedNode> nodeIterator() {
		return this.nodes.iterator();
	}

	public boolean canCopyHere(IBasicSubgraph<? extends DirectedNode, ? extends DirectedEdge> subGraph) {
		return false;
	}

	public void copyHere(IBasicSubgraph<? extends DirectedNode, ? extends DirectedEdge> subGraph) {
		throw new UnsupportedOperationException("Not implemented!");
	}

	public IBasicEdgeFactory<DirectedNode, DirectedEdge> edgeFactory() {
		return new DirectedEdgeFactory(this);
	}

	public IBasicSubgraph<DirectedNode, DirectedEdge> getCopiedComponents() {
		throw new UnsupportedOperationException("Not implemented!");
	}

	public IBasicNodeFactory<DirectedNode, DirectedEdge> nodeFactory() {
		return this.nodeFactory;
	}

	public void removeSubgraph(IBasicSubgraph<? extends DirectedNode, ? extends DirectedEdge> subgraph) {
		throw new UnsupportedOperationException("Not implemented!");
	}

	public IBasicSubgraphFactory<DirectedNode, DirectedEdge> subgraphFactory() {
		throw new UnsupportedOperationException("Not implemented!");
	}

	public void addGraphChangeListener(IGraphChangeListener<DirectedNode, DirectedEdge> listener) {
		// TODO Auto-generated method stub
		
	}

	public Iterator<IGraphChangeListener<DirectedNode, DirectedEdge>> modelChangeListenerIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeGraphChangeListener(IGraphChangeListener<DirectedNode, DirectedEdge> listener) {
		// TODO Auto-generated method stub
		
	}

	IndexCounter getIndexCounter() {
		return this.indexCounter;
	}


}
