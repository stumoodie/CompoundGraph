package uk.ed.inf.graph.basic;

public interface IModifiableGraph<
		N extends IBasicNode<N, ? extends IBasicEdge<N, ?>>,
		E extends IBasicEdge<N, E>
> {

	/**
	 * Gets the nodeFactory for this class. This factory is a singleton so this method must always
	 *  return the same instance of the factory. The factory is the only way that new nodes can be added to
	 *  this graph.
	 * @return The node factory.
	 * @throws UnsupportedOperationException if this graph implementation does support new node creation and so a factory
	 *  is not provided, i.e. <code>canCreateNodes() == false</code>.
	 */
	IBasicNodeFactory<N, E> nodeFactory();
	
	/**
	 * Gets the edgeFactory for this class. This factory is a singleton so this method must always
	 *  return the same instance of the factory. The factory is the only way that new edges can be added to
	 *  this graph.
	 * @return The edge factory.
	 * @throws UnsupportedOperationException if this graph implementation does support new edge creation and so a factory
	 *  is not provided, i.e. <code>canCreateEdges() == false</code>.
	 */
	IBasicEdgeFactory<N, E> edgeFactory();
	
	/**
	 * Gets the subgraphFactory for this class. This factory is a singleton so this method must always
	 *  return the same instance of the factory.
	 * @return The subgraph factory.
	 * @throws UnsupportedOperationException if this graph implementation does support new subgraph creation and so a factory
	 *  is not provided, i.e. <code>canCreateSubgraphs() == false</code>.
	 */
	IBasicSubgraphFactory<N, E> subgraphFactory();
	
	/**
	 * Removes the nodes and edges defined in the subgraph from this graph. The subgraph must be consistent with
	 *  this graph and be a subgraph of this graph.
	 * @param subgraph The subgraph to remove, cannot be null.
	 * @throws NullPointerException if <code>subgraph</code> is null.
	 * @throws UnsupportedOperationException if removal is not supported, i.e. when <code>canRemoveSubgraph() == false</code>.
	 * @throws IllegalArgumentException if the subgraph does not belong to this graph: <code>subgraph.getOwningGraph() != this</code>.
	 * @throws IllegalArgumentException if the subgraph is not consistent with this graph: <code>subgraph.isConsistentSnapshot() == false</code>. 
	 */
	void removeSubgraph(IBasicSubgraph<N, E> subgraph);

	/**
	 * Tests whether the subGraph can be copied to this graph. To be true the subgraph must be an induced subgraph
	 *  that is a consistent of the super graph. It must also be not null.
	 * @param subGraph the subgraph to test, can be null. 
	 * @return true if the subgraph is valid to copy from, false otherwise.
	 */
	boolean canCopyHere(IBasicSubgraph<N, E> subGraph);

	/**
	 * Copies a subgraph into this graph. Note that the subgraph can be from a different graph or subgraph
	 * of this graph, since the structure of the graph is copied not the nodes and edges instances
	 * themselves. Note that the subgraph must be valid to be copied.
	 * @param subGraph the subgraph to copy
	 */
	void copyHere(IBasicSubgraph<N, E> subGraph);
	
	/**
	 * Retrieves the nodes and edges created in this graph by the last copy operation. The subgraph
	 * is <b>not</b> guaranteed to be a consistent snapshot of this graph.   If not copy operation has
	 * been performed then an empty subset will be returned.
	 * @return the subgraph of copied components, or an empty subset of not copy operation has been perfromed. 
	 */
	IBasicSubgraph<N, E> getCopiedComponents();
	
//	/**
//	 * Creates a copy of this graph. Note that the graph's structure is copied, but the indexes of
//	 *  the copied graphs nodes and edges are not guaranteed to be the same.
//	 * @return An instance of the copied graph. Cannot be null.
//	 * @throws IllegalArgumentException if <code>canCopyHere()</code> cannot be null.
//	 */
//	IBasicGraph<N, E> createCopy();
}
