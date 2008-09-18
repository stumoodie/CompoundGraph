package uk.ed.inf.graph.compound;


public interface IModifiableCompoundGraph <
		N extends ICompoundNode<N, ? extends ICompoundEdge<N, ?>>,
		E extends ICompoundEdge<N, E>
> {

	ICompoundNodeFactory<N, E> nodeFactory();

	/**
	 * Gets an edge factory that works out the LCA of two nodes making up the edge
	 * and assigns it to the appropriate compound graph. This is more permissive than the
	 * edge factory from a ChildCompoundGraph, which requires to to know that the edge
	 * belongs in that child graph (i.e. its root node is the LCA of the out and in nodes 
	 * of the edge).  
	 * @return The edge factory, which cannot be null.
	 */
	ICompoundEdgeFactory<N, E> edgeFactory();


	ISubCompoundGraphFactory<N,E> subgraphFactory();


	/**
	 * Removes the nodes and edges defined in the subgraph from this graph. The subgraph must be consistent with
	 *  this graph and be a subgraph of this graph.
	 * @param subgraph The subgraph to remove, cannot be null.
	 * @throws NullPointerException if <code>subgraph</code> is null.
	 * @throws UnsupportedOperationException if removal is not supported, i.e. when <code>canRemoveSubgraph() == false</code>.
	 * @throws IllegalArgumentException if the subgraph does not belong to this graph: <code>subgraph.getOwningGraph() != this</code>.
	 * @throws IllegalArgumentException if the subgraph is not consistent with this graph: <code>subgraph.isConsistentSnapshot() == false</code>. 
	 */
	void removeSubgraph(ISubCompoundGraph<? extends N, ? extends E> subgraph);

	/**
	 * Tests whether the subGraph can be copied to this graph. To be true the subgraph must be an induced subgraph
	 *  that is a consistent of the super graph. It must also be not null.
	 * @param subGraph the subgraph to test, can be null. 
	 * @return true if the subgraph is valid to copy from, false otherwise.
	 */
	boolean canCopyHere(ISubCompoundGraph<? extends N, ? extends E> subgraph);

	/**
	 * Copies a subgraph into this graph. Note that the subgraph can be from a different graph or subgraph
	 * of this graph, since the structure of the graph is copied not the nodes and edges instances
	 * themselves. Note that the subgraph must be valid to be copied.
	 * @param subGraph the subgraph to copy
	 */
	void copyHere(ISubCompoundGraph<? extends N, ? extends E> subgraph);
	
	/**
	 * Retrieves the nodes and edges created in this graph by the last copy operation. The subgraph
	 * is <b>not</b> guaranteed to be a consistent snapshot of this graph.   If not copy operation has
	 * been performed then an empty subset will be returned.
	 * @return the subgraph of copied components, or an empty subset of not copy operation has been perfromed. 
	 */
	ISubCompoundGraph<N, E> getCopiedComponents();
}
