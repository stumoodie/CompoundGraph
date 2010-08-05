package uk.ed.inf.graph.compound;


public interface ISubgraphRemovalBuilder {

	/**
	 * Sets the subgraph to be removed.
	 * @param subgraph the subgraph to be removed, whoch cannot be null
	 */
	void setRemovalSubgraph(ISubCompoundGraph subgraph);
	
	/**
	 * Get subgraph to be removed
	 * @return the subgraph to be removed.
	 */
	ISubCompoundGraph getRemovalSubgraph();
	
	/**
	 * Tests if subgraph removal will succeed. To succeed the subgraph must belong to this graph,
	 *  it must be a consistent snapshot and cannot be null.
	 * @param subgraph the subgraph to be removed, which can be null.
	 * @return true if the subgraph will succeed, false otherwise. 
	 */
	boolean canRemoveSubgraph();
	
	/**
	 * Removes the nodes and edges defined in the subgraph from this graph. The subgraph must be consistent with
	 *  this graph and be a subgraph of this graph.
	 * @param subgraph The subgraph to remove, cannot be null.
	 * @throws IllegalArgumentException if <code>canRemoveSubgraph(subgraph) == false</code>.
	 * @deprecated use setRemovalSubgraph() then removeSubgraph()
	 */ 
	void removeSubgraph(ISubCompoundGraph subgraph);

	void removeSubgraph();
	
	/**
	 * Gets the graph that owns this removal builder
	 * @return the graph, which cannot be null.
	 */
	ICompoundGraph getGraph();

}
