package uk.ed.inf.graph.compound.base;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import uk.ed.inf.graph.compound.ISubCompoundGraphBuilder;
import uk.ed.inf.graph.directed.IDirectedPair;
import uk.ed.inf.graph.util.impl.NodeTreeIterator;

public abstract class BaseSubCompoundGraphBuilder implements ISubCompoundGraphBuilder<BaseCompoundNode, BaseCompoundEdge> {
	private Set<BaseCompoundNode> nodeList;
	private Set<BaseCompoundEdge> edgeList;

	/**
	 * Construct the builder, providing it with a list of nodes and edges with which to populate
	 * the subgraph that will be constructed.
	 * @param graph the graph to which the subgraph will refer, cannot be null.
	 * @param nodeList the list of nodes to be added to the subgraph, cannot be null.
	 * @param edgeList the list of edges to be added to the subgraph, cannot be null.
	 * @throws NullPointerException if any of the the parameters are null. 
	 */
	protected BaseSubCompoundGraphBuilder(){
		this.nodeList = Collections.emptySet();
		this.edgeList = Collections.emptySet();
	}
	
	public void setNodeList(Set<? extends BaseCompoundNode> nodeList){
		this.nodeList = new HashSet<BaseCompoundNode>(nodeList);
	}
	
	public void setEdgeList(Set<? extends BaseCompoundEdge> edgeList){
		this.edgeList = new HashSet<BaseCompoundEdge>(edgeList);
	}
	
	/**
	 * Expand the nodes provided to the builder so that all the contents of their compound graphs
	 * are included in the new subgraph. This is done recursively so that the branches from each of
	 * the original nodes are fully expanded. Nodes that are already children are merged into their
	 * respective branches.
	 */
	public void expandChildNodes(){
		Set<BaseCompoundNode> initialNodes = new HashSet<BaseCompoundNode>(this.nodeList); 
		for(BaseCompoundNode compoundNode : initialNodes){
			Iterator<? extends BaseCompoundNode> iter = compoundNode.levelOrderIterator();
			addEdges(iter.next()); // get edges of the the current node's child graph
			while(iter.hasNext()){
				BaseCompoundNode childNode = iter.next();
				this.nodeList.remove(childNode);
				// now add edges in this node's compound graph.
				addEdges(childNode);
			}
		}
	}

	private void addEdges(BaseCompoundNode node){
		Iterator<? extends BaseCompoundEdge> edgeIter = node.getChildCompoundGraph().edgeIterator();
		while(edgeIter.hasNext()){
			BaseCompoundEdge childEdge = edgeIter.next();
			this.edgeList.add(childEdge);
		}
	}
	
	/**
	 * Add the incident edges between the nodes already added to the subgraph. This is required to
	 * produce an induced subgraph. 
	 */
	public void addIncidentEdges(){
//		for(BaseCompoundNode node : this.nodeList){
		final Set<BaseCompoundNode> expandedNodes = createExpandedNodes();
		Iterator<BaseCompoundNode> nodeTreeIter = new NodeTreeIterator<BaseCompoundNode, BaseCompoundEdge>(this.nodeList.iterator());
		while(nodeTreeIter.hasNext()){
			BaseCompoundNode node = nodeTreeIter.next();
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
					if(expandedNodes.contains(ends.getInNode())){
						// the edge links two nodes that will be in the subgraph so it is
						// incident and so we add it.
						this.edgeList.add(edge);
					}
				}
			}
		}
	}
	
	//TODO: replace with a tree search !
	private Set<BaseCompoundNode> createExpandedNodes(){
		Set<BaseCompoundNode> retVal = new HashSet<BaseCompoundNode>();
		Iterator<BaseCompoundNode> iter = new NodeTreeIterator<BaseCompoundNode, BaseCompoundEdge>(this.nodeList.iterator());
		while(iter.hasNext()){
			retVal.add(iter.next());
		}
		return retVal;
	}

	/**
	 * Build the new subgraph, based on the previous processing of the initial nodes and edges.
	 */
	public void buildSubgraph() {
		newSubgraph();
		for(BaseCompoundNode node : this.nodeList){
			getSubgraph().addTopNode(node);
		}
		for(BaseCompoundEdge edge : this.edgeList){
			getSubgraph().addEdge(edge);
		}
		
		getSubgraph().buildComplete() ;
	}
	
	protected abstract void newSubgraph();
	
	/**
	 * Retrieve the created subgraph. If the subgraph has not been build then this method will fail.
	 * @return The created subgraph, cannot be null.
	 * @throws IllegalStateException if the subgraph has not been created by a call to <code>buildSubgraph</code>.
	 */
	public abstract BaseSubCompoundGraph getSubgraph();
	
	public abstract BaseCompoundGraph getGraph();
}
