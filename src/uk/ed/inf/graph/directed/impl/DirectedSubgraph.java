package uk.ed.inf.graph.directed.impl;

import java.util.Iterator;

import uk.ed.inf.graph.basic.IBasicGraph;
import uk.ed.inf.graph.basic.IBasicPair;
import uk.ed.inf.graph.directed.IDirectedPair;
import uk.ed.inf.graph.directed.IDirectedSubgraph;
import uk.ed.inf.graph.util.IEdgeSet;
import uk.ed.inf.graph.util.INodeSet;
import uk.ed.inf.graph.util.impl.DirectedEdgeSet;
import uk.ed.inf.graph.util.impl.NodeSet;

public class DirectedSubgraph implements IDirectedSubgraph<DirectedNode, DirectedEdge> {
	private final DirectedGraph superGraph;
	private final INodeSet<DirectedNode, DirectedEdge> nodes;
	private final IEdgeSet<DirectedNode, DirectedEdge> edges;
	
	public DirectedSubgraph(DirectedGraph graph){
		this.superGraph = graph;
		this.nodes = new NodeSet<DirectedNode, DirectedEdge>();
		this.edges = new DirectedEdgeSet<DirectedNode, DirectedEdge>();
	}
	
	public IBasicGraph<DirectedNode, DirectedEdge> getSuperGraph() {
		return this.superGraph;
	}

	public boolean isConsistentSnapShot() {
		boolean retVal = true;
		Iterator<DirectedNode> nodeIter = this.nodes.iterator();
		while(nodeIter.hasNext() && retVal){
			DirectedNode node = nodeIter.next();
			if(node.isRemoved()){
				retVal = false;
			}
		}
		Iterator<DirectedEdge> edgeIter = this.edges.iterator();
		while(edgeIter.hasNext() && retVal){
			DirectedEdge edge = edgeIter.next();
			if(edge.isRemoved()){
				retVal = false;
			}
		}
		return retVal;
	}

	public boolean isInducedSubgraph() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean containsConnection(DirectedNode thisNode, DirectedNode thatNode) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean containsConnection(IBasicPair<? extends DirectedNode, ? extends DirectedEdge> ends) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean containsEdge(DirectedEdge edge) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean containsEdge(int edgeIdx) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean containsNode(int nodeIdx) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean containsNode(DirectedNode node) {
		// TODO Auto-generated method stub
		return false;
	}

	public Iterator<DirectedEdge> edgeIterator() {
		return this.edges.iterator();
	}

	public DirectedEdge getEdge(int edgeIdx) {
		// TODO Auto-generated method stub
		return null;
	}

	public DirectedNode getNode(int nodeIdx) {
		// TODO Auto-generated method stub
		return null;
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

	public boolean containsDirectedEdge(DirectedNode outNode, DirectedNode inNode) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean containsDirectedEdge(IDirectedPair<? extends DirectedNode, ? extends DirectedEdge> ends) {
		// TODO Auto-generated method stub
		return false;
	}

}
