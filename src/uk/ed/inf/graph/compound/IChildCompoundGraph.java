package uk.ed.inf.graph.compound;



public interface IChildCompoundGraph<
		N extends ICompoundNode<N, ? extends ICompoundEdge<N, ?>>,
		E extends ICompoundEdge<N, E>
> extends ICompoundGraph<N, E> {
	
	/**
	 * Gets the compound graph that owns this child graph. 
	 * @return the owning compound graph, which cannot be null.
	 */
	ICompoundGraph<N, E> getSuperGraph();
	
//	/**
//	 * Tests if the subgraph contains nodes or edges that are contained by this
//	 * child graph or its children.
//	 * @param subgraph the subgraph to test, which can be null.
//	 * @return true if the subgraph intersects with this child graph, false otherwise.
//	 */
//	boolean intersects(ISubCompoundGraph<? extends N, ? extends E> subgraph);
}
