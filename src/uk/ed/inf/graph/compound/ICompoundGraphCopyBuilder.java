package uk.ed.inf.graph.compound;

public interface ICompoundGraphCopyBuilder<
		N extends ICompoundNode<N, ? extends ICompoundEdge<N, ?>>,
		E extends ICompoundEdge<N, E>
> {

	ISubCompoundGraph<N, E> getSourceSubgraph();
	
	IChildCompoundGraph<N, E> getDestinationChildGraph();
	
	/**
	 * Sets the subgraph which is to be copied.
	 * @param sourceSubCompoundGraph
	 */
	void setSourceSubgraph(ISubCompoundGraph<? extends N, ? extends E> sourceSubCompoundGraph);

	/**
	 * Sets the child compound graph that is to be copied to.
	 * @param childCompoundGraph
	 */
	void setDestinatChildCompoundGraph(IChildCompoundGraph<? extends N, ? extends E> childCompoundGraph);

	/**
	 * Make a copy of subgraph into the destination graph
	 */
	void makeCopy();

	/**
	 * Gets the copied nodes and edges that were created in the destination graph as a
	 * subgraph of the destination graph.
	 * @return The subgraph of copied nodes, which will be empty of no nodes are copied.
	 */
	ISubCompoundGraph<N, E> getCopiedComponents();
}