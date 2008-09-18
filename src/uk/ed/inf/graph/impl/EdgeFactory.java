package uk.ed.inf.graph.impl;

import org.apache.log4j.Logger;

import uk.ed.inf.graph.basic.IBasicGraph;
import uk.ed.inf.graph.basic.IBasicPair;
import uk.ed.inf.graph.undirected.IUndirectedEdgeFactory;

public class EdgeFactory implements IUndirectedEdgeFactory<Node, Edge> {
	private final Logger logger = Logger.getLogger(this.getClass());
	private int indexCntr;
	private final Graph graph;
	private Node oneNode;
	private Node twoNode;
	
	EdgeFactory(Graph graph){
		this.graph = graph;
	}
	
	public void setPair(Node oneNode, Node twoNode){
		this.oneNode = oneNode;
		this.twoNode = twoNode;
	}
	
	public Edge createEdge() {
		logger.debug("Creating edge between nodes(" + oneNode + ", " + twoNode + ")");
		return new Edge(this.graph, indexCntr++, oneNode, twoNode);
	}

	public IBasicGraph<Node, Edge> getGraph() {
		return this.graph;
	}

	public IBasicPair<Node, Edge> getCurrentNodePair() {
		return new NodePair(this.oneNode, this.twoNode);
	}

	public boolean canCreateEdge() {
		return this.getCurrentNodePair() != null;
	}

	public boolean isValidNodePair(Node thisNode, Node thatNode) {
		return thisNode != null && thatNode != null;
	}
}
