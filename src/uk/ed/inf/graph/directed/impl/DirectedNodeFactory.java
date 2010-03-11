package uk.ed.inf.graph.directed.impl;

import uk.ed.inf.graph.basic.IBasicNodeFactory;
import uk.ed.inf.graph.util.IndexCounter;

public class DirectedNodeFactory implements IBasicNodeFactory<DirectedNode, DirectedEdge> {
	private final DirectedGraph graph;

	DirectedNodeFactory(DirectedGraph graph){
		this.graph = graph;
	}
	
	public DirectedNode createNode() {
		IndexCounter counter = this.graph.getIndexCounter();
		return new DirectedNode(this.graph, counter.nextIndex());
	}

	public DirectedGraph getGraph() {
		return this.graph;
	}

}
