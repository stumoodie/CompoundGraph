package uk.ed.inf.graph.basic;


public interface IBasicEdgeFactory<
		N extends IBasicNode<N, ? extends IBasicEdge<N, ?>>,
		E extends IBasicEdge<N, E>
> {

	/**
	 * Get the graph to which this factory acts upon.
	 * @return The owning graph, cannot be null.
	 */
	IBasicGraph<N, E> getGraph();
	
	/**
	 * Create a new edge and add it to the graph.
	 * @return
	 */
	E createEdge();
	
}
