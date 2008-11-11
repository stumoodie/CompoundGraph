package uk.ed.inf.graph.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import uk.ed.inf.graph.undirected.IUndirectedPair;
import uk.ed.inf.graph.undirected.IUndirectedSubgraphFactory;

public final class SubgraphFactory implements IUndirectedSubgraphFactory<Node, Edge> {
	private final Graph graph;
	private final Set<Node> nodeList = new HashSet<Node>();
	private final Set<Edge>	edgeList = new HashSet<Edge>(); 
	
	SubgraphFactory(Graph graph){
		this.graph = graph;	
	}
	
	public void addNode(Node node){
		this.nodeList.add(node);
	}
	
	public void addEdge(Edge edge){
		this.edgeList.add(edge);
	}

	/**
	 * Creates a subgraph, that includes dangling edges.
	 */
	public Subgraph createSubgraph() {
		Subgraph retVal = createInducedSubgraph();
		for(Edge edge : this.edgeList){
			if(!retVal.containsEdge(edge)){
				retVal.addDanglingEdge(edge);
			}
		}
		return retVal;
	}

	public Subgraph createInducedSubgraph(){
		Subgraph retVal = new Subgraph(this.graph);
		Set<Edge> incidentEdges = new HashSet<Edge>();
		for(Node node : this.nodeList){
			Iterator<Edge> edgeIter = node.edgeIterator();
			while(edgeIter.hasNext()){
				Edge edge = edgeIter.next();
				IUndirectedPair<Node, Edge> ends = edge.getConnectedNodes();
				if(this.nodeList.contains(ends.getOneNode())
						&&  this.nodeList.contains(ends.getTwoNode())){
					incidentEdges.add(edge);
				}
			}
			retVal.addNode(node);
		}
		for(Edge edge : incidentEdges){
			retVal.addConnectedEdge(edge);
		}
		return retVal;
	}

	public Iterator<Edge> edgeIterator() {
		return this.edgeList.iterator();
	}

	public Iterator<Node> nodeIterator() {
		return this.nodeList.iterator();
	}

	public Graph getGraph() {
		return this.graph;
	}

	public int numEdges() {
		return this.edgeList.size();
	}

	public int numNodes() {
		return this.nodeList.size();
	}
}
