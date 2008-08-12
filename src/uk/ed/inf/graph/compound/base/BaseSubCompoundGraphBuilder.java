package uk.ed.inf.graph.compound.base;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import uk.ed.inf.graph.directed.IDirectedPair;

public abstract class BaseSubCompoundGraphBuilder {
	private final BaseCompoundGraph graph;
	private final Set<BaseCompoundNode> nodeList;
	private final Set<BaseCompoundEdge> edgeList;
//	private ArchetypalSubCompoundGraph subGraph;

	/**
	 * Construct the builder, providing it with a list of nodes and edges with which to populate
	 * the subgraph that will be constructed.
	 * @param graph the graph to which the subgraph will refer, cannot be null.
	 * @param nodeList the list of nodes to be added to the subgraph, cannot be null.
	 * @param edgeList the list of edges to be added to the subgraph, cannot be null.
	 * @throws NullPointerException if any of the the parameters are null. 
	 */
	public BaseSubCompoundGraphBuilder(BaseCompoundGraph graph){
		this.graph = graph;
		this.nodeList = new HashSet<BaseCompoundNode>();
		this.edgeList = new HashSet<BaseCompoundEdge>();
	}
	
	public void setNodeList(Set<? extends BaseCompoundNode> nodeList){
		this.nodeList.addAll(nodeList);
	}
	
	public void setEdgeList(Set<? extends BaseCompoundEdge> edgeList){
		this.edgeList.addAll(edgeList);
	}
	
	/**
	 * Expand the nodes provided to the builder so that all the contents of their compound graphs
	 * are included in the new subgraph. This is done recursively so that the branches from each of
	 * the original nodes are fully expanded. Nodes that are already children are merged into their
	 * respective branches.
	 */
	public void expandChildCigraphs(){
		Set<BaseCompoundNode> initialNodes = new HashSet<BaseCompoundNode>(this.nodeList); 
		for(BaseCompoundNode compoundNode : initialNodes){
			Iterator<? extends BaseCompoundNode> iter = compoundNode.levelOrderIterator();
			iter.next(); // skip the current node
			while(iter.hasNext()){
				BaseCompoundNode childNode = iter.next();
				this.nodeList.add(childNode);
				// now add edges in this node's compound graph.
				Iterator<? extends BaseCompoundEdge> edgeIter = childNode.getChildCompoundGraph().edgeIterator();
				while(edgeIter.hasNext()){
					BaseCompoundEdge childEdge = edgeIter.next();
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
		for(BaseCompoundNode node : this.nodeList){
			// we only consider out edges as this will reduce the number edges we have
			// to consider twice. If an edge is directed and incident to the nodes in the
			// subgraph then we are guaranteed to traverse it once.
			// 
			Iterator<BaseCompoundEdge> edgeIter = node.getOutEdgeIterator();
			while(edgeIter.hasNext()){
				BaseCompoundEdge edge = edgeIter.next();
				if(!this.edgeList.contains(edge)){
					// only do this if the edge is not already in the set of edges
					IDirectedPair<BaseCompoundNode, BaseCompoundEdge> ends = edge.getConnectedNodes();
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
		newSubgraph(this.graph);
		for(BaseCompoundNode node : this.nodeList){
			getSubgraph().addNode(node);
		}
		for(BaseCompoundEdge edge : this.edgeList){
			getSubgraph().addEdge(edge);
		}
	}
	
	protected abstract void newSubgraph(BaseCompoundGraph compoundGraph);
	
	/**
	 * Retrieve the created subgraph. If the subgraph has not been build then this method will fail.
	 * @return The created subgraph, cannot be null.
	 * @throws IllegalStateException if the subgraph has not been created by a call to <code>buildSubgraph</code>.
	 */
	public abstract BaseSubCompoundGraph getSubgraph();
}
