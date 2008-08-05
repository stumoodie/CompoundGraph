package uk.ed.inf.graph.compound.archetypal;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import uk.ed.inf.graph.basic.IBasicSubgraphFactory;
import uk.ed.inf.graph.compound.impl.CompoundEdge;
import uk.ed.inf.graph.compound.impl.CompoundNode;

public abstract class ArchetypalSubCompoundGraphFactory implements IBasicSubgraphFactory<ArchetypalCompoundNode, ArchetypalCompoundEdge> {
	private final ArchetypalSubCompoundGraphBuilder builder;
	private final Set<ArchetypalCompoundNode> nodeList = new HashSet<ArchetypalCompoundNode>();
	private final Set<ArchetypalCompoundEdge> edgeList = new HashSet<ArchetypalCompoundEdge>();

	public ArchetypalSubCompoundGraphFactory(ArchetypalSubCompoundGraphBuilder builder){
		this.builder = builder;
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
	public Iterator<ArchetypalCompoundNode> nodeIterator(){
		return this.nodeList.iterator();
	}
	
	/**
	 * Get an iterator of the set of edges added to this factory. It will not return any incident
	 * edges derived from the nodes selected here or between nodes in any child compound graphs of
	 * the selected nodes. You should create the subgraph to get this. 
	 */
	public Iterator<ArchetypalCompoundEdge> edgeIterator(){
		return this.edgeList.iterator();
	}
	
	/**
	 * Creates a subgraph that includes dangling edges and contains nodes from the compound graphs of
	 *  of each subgraph.
	 *  @return the newly created subgraph, cannot be null. 
	 */
	public ArchetypalSubCompoundGraph createSubgraph() {
		builder.setNodeList(this.nodeList);
		builder.setEdgeList(this.edgeList);
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
	public ArchetypalSubCompoundGraph createInducedSubgraph(){
		builder.setNodeList(this.nodeList);
		builder.setEdgeList(this.edgeList);
		builder.expandChildCigraphs();
		builder.addIncidentEdges();
		builder.buildSubgraph();
		return builder.getSubgraph();
	}
}
