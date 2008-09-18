package uk.ed.inf.graph.compound;


public interface IModifiableChildCompoundGraph <
		N extends ICompoundNode<N, ? extends ICompoundEdge<N, ?>>,
		E extends ICompoundEdge<N, E>
> {


	ICompoundNodeFactory<N, E> nodeFactory();

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
	/**
	 * Gets the edgeFactory for this class. This factory is a singleton so this method must always
	 *  return the same instance of the factory. The factory is the only way that new edges can be added to
	 *  this graph.
	 * @return The edge factory.
	 */
	ICompoundChildEdgeFactory<N, E> edgeFactory();
	
	/**
	 * Tests whether the subGraph can be moved to this graph. To be true the subgraph must be an induced subgraph
	 *  that is a consistent of the super graph. It must also be not null and belong to the
	 *  same graph as this one. The subgraph must also be valid.
	 *  Also no nodes in the induced sub-graph of <code>subGraph</code> can be children
	 *  of this child compound graph. 
	 * @param subGraph the subgraph to test, can be null. 
	 * @return true if the subgraph is valid to copy from, false otherwise.
	 */
	boolean canMoveHere(ISubCompoundGraph<? extends N, ? extends E> subGraph);

	/**
	 * Moves a subgraph into this graph. It does this by creating a new set of
	 * nodes in this subgraph and removing the nodes defined in <code>subGraph</code>.
	 * The new nodes from the move can be found in <code>getMovedComponents()</code> 
	 * and the removed nodes will be found in <code>subGraph</code>.
	 * @param subGraph the subgraph to move.
	 * @throws IllegalArgumentException if <code>canMoveHere(subGraph) == false</code>.
	 */
	void moveHere(ISubCompoundGraph<? extends N, ? extends E> subGraph);
	
	/**
	 * Retrieves the nodes and edges created in this graph by the last copy operation. The subgraph
	 * is <b>not</b> guaranteed to be a consistent snapshot of this graph.   If not copy operation has
	 * been performed then an empty subset will be returned.
	 * @return the subgraph of copied components, or an empty subset of not copy operation has been perfromed. 
	 */
	ISubCompoundGraph<N, E> getMovedComponents();
}
