package uk.ed.inf.graph.impl;

import uk.ed.inf.graph.basic.IBasicGraph;
import uk.ed.inf.graph.undirected.IUndirectedNodeFactory;

public final class NodeFactory implements IUndirectedNodeFactory<Node, Edge> {
	private final Graph graph;
	private int indexCntr = 0;
	
	public NodeFactory(Graph graph){
		this.graph = graph;
	}
	
	public Node createNode() {
		Node retVal = new Node(this.graph, indexCntr++);
		this.graph.addNewNode(retVal);
		return retVal;
	}

	@Override
	public IBasicGraph<Node, Edge> getGraph() {
		return this.graph;
	}

}
