package uk.ed.inf.graph.compound.base;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import uk.ed.inf.graph.compound.ISubCompoundGraphFactory;

public abstract class BaseSubCompoundGraphFactory implements ISubCompoundGraphFactory<BaseCompoundNode, BaseCompoundEdge> {
	private final BaseSubCompoundGraphBuilder builder;
	private final Set<BaseCompoundNode> nodeList = new HashSet<BaseCompoundNode>();
	private final Set<BaseCompoundEdge> edgeList = new HashSet<BaseCompoundEdge>();

	protected BaseSubCompoundGraphFactory(BaseSubCompoundGraphBuilder builder){
		this.builder = builder;
	}
	
	public final void addNode(BaseCompoundNode node){
		this.nodeList.add(node);
	}
	
	public final void addEdge(BaseCompoundEdge edge){
		this.edgeList.add(edge);
	}

	/**
	 * Get an iterator for the set of nodes added to this factory. It will node contain
	 * any addition nodes in child compound graphs of these nodes. You should create the
	 * subgraph to get this.
	 * @return iterator of <code>CiNode</code>s.
	 */
	public Iterator<BaseCompoundNode> nodeIterator(){
		return this.nodeList.iterator();
	}
	
	/**
	 * Get an iterator of the set of edges added to this factory. It will not return any incident
	 * edges derived from the nodes selected here or between nodes in any child compound graphs of
	 * the selected nodes. You should create the subgraph to get this. 
	 */
	public Iterator<BaseCompoundEdge> edgeIterator(){
		return this.edgeList.iterator();
	}
	
	/**
	 * Creates a subgraph that includes dangling edges and contains nodes from the compound graphs of
	 *  of each subgraph.
	 *  @return the newly created subgraph, cannot be null. 
	 */
	public BaseSubCompoundGraph createSubgraph() {
		builder.setNodeList(this.nodeList);
		builder.setEdgeList(this.edgeList);
		builder.expandChildNodes();
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
	public BaseSubCompoundGraph createInducedSubgraph(){
		builder.setNodeList(this.nodeList);
		builder.setEdgeList(this.edgeList);
		builder.expandChildNodes();
		builder.addIncidentEdges();
		builder.buildSubgraph();
		return builder.getSubgraph();
	}
	
	public final BaseCompoundGraph getGraph(){
		return this.builder.getGraph();
	}
	
}
