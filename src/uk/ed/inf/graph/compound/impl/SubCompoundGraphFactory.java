package uk.ed.inf.graph.compound.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import uk.ed.inf.graph.basic.IBasicSubgraphFactory;

public class SubCompoundGraphFactory implements IBasicSubgraphFactory<ArchetypalCompoundNode, ArchetypalCompoundEdge> {
	private final CompoundGraph graph;
	private final Set<CompoundNode> nodeList = new HashSet<CompoundNode>();
	private final Set<CompoundEdge> edgeList = new HashSet<CompoundEdge>();

	public SubCompoundGraphFactory(CompoundGraph graph){
		this.graph = graph;
	}
	
	public void addNode(ArchetypalCompoundNode iNode){
		CompoundNode node = (CompoundNode)iNode;
		this.nodeList.add(node);
	}
	
	public void addEdge(ArchetypalCompoundEdge iEdge){
		CompoundEdge edge = (CompoundEdge)iEdge;
		this.edgeList.add(edge);
	}

	/**
	 * Get an iterator for the set of nodes added to this factory. It will node contain
	 * any addition nodes in child compound graphs of these nodes. You should create the
	 * subgraph to get this.
	 * @return iterator of <code>CiNode</code>s.
	 */
	public Iterator<CompoundNode> nodeIterator(){
		return this.nodeList.iterator();
	}
	
	/**
	 * Get an iterator of the set of edges added to this factory. It will not return any incident
	 * edges derived from the nodes selected here or between nodes in any child compound graphs of
	 * the selected nodes. You should create the subgraph to get this. 
	 */
	public Iterator<CompoundEdge> edgeIterator(){
		return this.edgeList.iterator();
	}
	
	/**
	 * Creates a subgraph that includes dangling edges and contains nodes from the compound graphs of
	 *  of each subgraph.
	 *  @return the newly created subgraph, cannot be null. 
	 */
	public SubCompoundGraph createSubgraph() {
		SubCompoundGraphBuilder builder = new SubCompoundGraphBuilder(this.graph, this.nodeList, this.edgeList);
		builder.expandChildCigraphs();
		builder.buildSubgraph();
		return builder.getSubgraph();
	}

	/**
	 * Creates an induced subgraph that contains all incident edges between the nodes in this graph.
	 * Child nodes in the compound-graph of each node are also included. 
	 * Unattached (dangling) edges defined in the factory are omitted from this sub-graph.
	 * <p>
	 * Postcondition:
	 * <code>retVal.isInducedSubgraph() == true</code>
	 * @return the newly created induced subgraph.    
	 */
	public SubCompoundGraph createInducedSubgraph(){
		SubCompoundGraphBuilder builder = new SubCompoundGraphBuilder(this.graph, this.nodeList, this.edgeList);
		builder.expandChildCigraphs();
		builder.addIncidentEdges();
		builder.buildSubgraph();
		return builder.getSubgraph();
	}
}
