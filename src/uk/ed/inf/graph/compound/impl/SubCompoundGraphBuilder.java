package uk.ed.inf.graph.compound.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import uk.ed.inf.graph.directed.IDirectedPair;

public class SubCompoundGraphBuilder {
	private final CompoundGraph graph;
	private final Set<CompoundNode> nodeList;
	private final Set<CompoundEdge> edgeList;
	private SubCompoundGraph subGraph;

	/**
	 * Construct the builder, providing it with a list of nodes and edges with which to populate
	 * the subgraph that will be constructed.
	 * @param graph the graph to which the subgraph will refer, cannot be null.
	 * @param nodeList the list of nodes to be added to the subgraph, cannot be null.
	 * @param edgeList the list of edges to be added to the subgraph, cannot be null.
	 * @throws NullPointerException if any of the the parameters are null. 
	 */
	public SubCompoundGraphBuilder(CompoundGraph graph, Set<CompoundNode> nodeList, Set<CompoundEdge> edgeList){
		this.graph = graph;
		this.nodeList = new HashSet<CompoundNode>(nodeList);
		this.edgeList = new HashSet<CompoundEdge>(edgeList);
	}
	
	/**
	 * Constructs the builder, provide it with just a list of nodes with which to populate the graph.
	 * @param graph the graph to which the subgraph will refer, cannot be null.
	 * @param nodeList the list of nodes to be added to the subgraph, cannot be null.
	 * @throws NullPointerException if any of the the parameters are null. 
	 */
	public SubCompoundGraphBuilder(CompoundGraph graph, Set<CompoundNode> nodeList){
		this.graph = graph;
		this.nodeList = new HashSet<CompoundNode>(nodeList);
		this.edgeList = new HashSet<CompoundEdge>();
	}
	
	
	/**
	 * Expand the nodes provided to the builder so that all the contents of their compound graphs
	 * are included in the new subgraph. This is done recursively so that the branches from each of
	 * the original nodes are fully expanded. Nodes that are already children are merged into their
	 * respective branches.
	 */
	public void expandChildCigraphs(){
		Set<CompoundNode> initialNodes = new HashSet<CompoundNode>(this.nodeList); 
		for(CompoundNode compoundNode : initialNodes){
			Iterator<CompoundNode> iter = compoundNode.levelOrderIterator();
			iter.next(); // skip the current node
			while(iter.hasNext()){
				CompoundNode childNode = iter.next();
				this.nodeList.add(childNode);
				// now add edges in this node's compound graph.
				Iterator<CompoundEdge> edgeIter = childNode.getChildCigraph().edgeIterator();
				while(edgeIter.hasNext()){
					CompoundEdge childEdge = edgeIter.next();
					this.edgeList.add(childEdge);
				}
			}
		}
	}
	
	/**
	 * Add the incident edges between the nodes already added to the subgraph. This is required to
	 * produce an induced subgraph. 
	 */
	public void addIncidentEdges(){
		for(CompoundNode node : this.nodeList){
			// we only consider out edges as this will reduce the number edges we have
			// to consider twice. If an edge is directed and incident to the nodes in the
			// subgraph then we are guaranteed to traverse it once.
			// 
			Iterator<CompoundEdge> edgeIter = node.getOutEdgeIterator();
			while(edgeIter.hasNext()){
				CompoundEdge edge = edgeIter.next();
				if(!this.edgeList.contains(edge)){
					// only do this if the edge is not already in the set of edges
					IDirectedPair<CompoundNode, CompoundEdge> ends = edge.getConnectedNodes();
					if(nodeList.contains(ends.getInNode())){
						// the edge links two nodes that will be in the subgraph so it is
						// incident and so we add it.
						this.edgeList.add(edge);
					}
				}
			}
		}
	}

	/**
	 * Build the new subgraph, based on the previous processing of the initial nodes and edges.
	 */
	public void buildSubgraph() {
		this.subGraph = new SubCompoundGraph(this.graph);
		
		for(CompoundNode node : this.nodeList){
			subGraph.addNode(node);
		}
		for(CompoundEdge edge : this.edgeList){
			subGraph.addEdge(edge);
		}
	}
	
	/**
	 * Retrieve the created subgraph. If the subgraph has not been build then this method will fail.
	 * @return The created subgraph, cannot be null.
	 * @throws IllegalStateException if the subgraph has not been created by a call to <code>buildSubgraph</code>.
	 */
	public SubCompoundGraph getSubgraph() {
		return this.subGraph;
	}
}
