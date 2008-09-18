package uk.ed.inf.graph.directed;

import uk.ed.inf.graph.basic.IBasicEdgeFactory;

public interface IDirectedEdgeFactory<
		N extends IDirectedNode<N, ? extends IDirectedEdge<N, ?>>,
		E extends IDirectedEdge<N, E>
> extends IBasicEdgeFactory<N, E> {

	/**
	 * Sets the nodes to be used to create the edge.
	 * @param outNode outNode, cannot be null.
	 * @param inNode inNode, cannot be null.
	 * @throws IllegalArgumentException if <code>isValidNodePair(outNode, inNode) == false</code>.
	 */
	void setPair(N outNode, N inNode);

	/**
	 * Gets the current node pair.
	 * @return the current node pair, or null if no node pair has been set yet.
	 */
	IDirectedPair<N, E> getCurrentNodePair();
}
