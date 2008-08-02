package uk.ed.inf.graph.compound;

import uk.ed.inf.graph.directed.IDirectedGraph;


public interface ICompoundGraph<
		N extends ICompoundNode<N, ? extends ICompoundEdge<N, ?>>,
		E extends ICompoundEdge<N, E>
> extends IDirectedGraph<N, E> {

	/**
	 * Get the root node of this graph.
	 * @return The root node, which cannot be null.
	 */
	N getRoot();

}
