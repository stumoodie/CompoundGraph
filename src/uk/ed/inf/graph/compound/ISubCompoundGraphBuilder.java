package uk.ed.inf.graph.compound;

import java.util.Set;


public interface ISubCompoundGraphBuilder<
		N extends ICompoundNode<N, ? extends ICompoundEdge<N, ?>>,
		E extends ICompoundEdge<N, E>
> {
	ICompoundGraph<N, E> getGraph();
	
	void setNodeList(Set<? extends N> nodeList);
	
	void setEdgeList(Set<? extends E> edgeList);
	
	void expandChildNodes();

	void addIncidentEdges();
	
	void buildSubgraph();
	
	/**
	 * Retrieve the created subgraph. If the subgraph has not been build then this method will fail.
	 * @return The created subgraph, cannot be null.
	 * @throws IllegalStateException if the subgraph has not been created by a call to <code>buildSubgraph</code>.
	 */
	ISubCompoundGraph<N, E> getSubgraph();
}
