package uk.ed.inf.graph.compound.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import uk.ed.inf.graph.directed.IDirectedPair;

public class SubCompoundGraphBuilder {
	private final ArchetypalCompoundGraph graph;
	private final Set<ArchetypalCompoundNode> nodeList;
	private final Set<ArchetypalCompoundEdge> edgeList;
	private SubCompoundGraph subGraph;

	/**
	 * Construct the builder, providing it with a list of nodes and edges with which to populate
	 * the subgraph that will be constructed.
	 * @param graph the graph to which the subgraph will refer, cannot be null.
	 * @param nodeList the list of nodes to be added to the subgraph, cannot be null.
	 * @param edgeList the list of edges to be added to the subgraph, cannot be null.
	 * @throws NullPointerException if any of the the parameters are null. 
	 */
	public SubCompoundGraphBuilder(ArchetypalCompoundGraph graph, Set<? extends ArchetypalCompoundNode> nodeList,
			Set<? extends ArchetypalCompoundEdge> edgeList){
		this.graph = graph;
		this.nodeList = new HashSet<ArchetypalCompoundNode>(nodeList);
		this.edgeList = new HashSet<ArchetypalCompoundEdge>(edgeList);
	}
	
	/**
	 * Constructs the builder, provide it with just a list of nodes with which to populate the graph.
	 * @param graph the graph to which the subgraph will refer, cannot be null.
	 * @param nodeList the list of nodes to be added to the subgraph, cannot be null.
	 * @throws NullPointerException if any of the the parameters are null. 
	 */
	public SubCompoundGraphBuilder(ArchetypalCompoundGraph graph, Set<? extends ArchetypalCompoundNode> nodeList){
		this.graph = graph;
		this.nodeList = new HashSet<ArchetypalCompoundNode>(nodeList);
		this.edgeList = new HashSet<ArchetypalCompoundEdge>();
	}
	
	
	/**
	 * Expand the nodes provided to the builder so that all the contents of their compound graphs
	 * are included in the new subgraph. This is done recursively so that the branches from each of
	 * the original nodes are fully expanded. Nodes that are already children are merged into their
	 * respective branches.
	 */
	public void expandChildCigraphs(){
		Set<ArchetypalCompoundNode> initialNodes = new HashSet<ArchetypalCompoundNode>(this.nodeList); 
		for(ArchetypalCompoundNode compoundNode : initialNodes){
			Iterator<? extends ArchetypalCompoundNode> iter = compoundNode.levelOrderIterator();
			iter.next(); // skip the current node
			while(iter.hasNext()){
				ArchetypalCompoundNode childNode = iter.next();
				this.nodeList.add(childNode);
				// now add edges in this node's compound graph.
				Iterator<? extends ArchetypalCompoundEdge> edgeIter = childNode.getChildCigraph().edgeIterator();
				while(edgeIter.hasNext()){
					ArchetypalCompoundEdge childEdge = edgeIter.next();
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
		for(ArchetypalCompoundNode node : this.nodeList){
			// we only consider out edges as this will reduce the number edges we have
			// to consider twice. If an edge is directed and incident to the nodes in the
			// subgraph then we are guaranteed to traverse it once.
			// 
			Iterator<ArchetypalCompoundEdge> edgeIter = node.getOutEdgeIterator();
			while(edgeIter.hasNext()){
				ArchetypalCompoundEdge edge = edgeIter.next();
				if(!this.edgeList.contains(edge)){
					// only do this if the edge is not already in the set of edges
					IDirectedPair<ArchetypalCompoundNode, ArchetypalCompoundEdge> ends = edge.getConnectedNodes();
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
		
		for(ArchetypalCompoundNode node : this.nodeList){
			subGraph.addNode(node);
		}
		for(ArchetypalCompoundEdge edge : this.edgeList){
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
