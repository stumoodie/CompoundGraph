package uk.ed.inf.graph.directed.impl;

import uk.ed.inf.graph.basic.IBasicEdgeFactory;

public class DirectedEdgeFactory implements IBasicEdgeFactory<DirectedNode, DirectedEdge> {
	private final DirectedGraph graph;
	private DirectedPair nodePair;
	
	public DirectedEdgeFactory(DirectedGraph graph) {
		this.graph = graph;
		this.nodePair = null;
	}
	
	public boolean canCreateEdge() {
		return nodePair != null;
	}

	public DirectedEdge createEdge() {
		return new DirectedEdge(this.graph, this.graph.getIndexCounter().nextIndex(), this.nodePair.getOutNode(), this.nodePair.getInNode());
	}

	public DirectedPair getCurrentNodePair() {
		return this.nodePair;
	}

	public DirectedGraph getGraph() {
		return this.getGraph();
	}

	public boolean isValidNodePair(DirectedNode thisNode, DirectedNode thatNode) {
		return thisNode != null && thisNode.getGraph().equals(this.graph)
			&& thatNode != null && thatNode.getGraph().equals(this.graph);
	}

	public void setPair(DirectedNode thisNode, DirectedNode thatNode) {
		this.nodePair = new DirectedPair(thisNode, thatNode);
	}

}
