package uk.ed.inf.graph.directed;

import uk.ed.inf.graph.basic.IBasicPair;

public interface IDirectedPair<
		N extends IDirectedNode<N, ? extends IDirectedEdge<N, E>>,
		E extends IDirectedEdge<N, E>
> extends IBasicPair<N, E>{

	/**
	 * Tests if the ends exist in this directed pair. Takes into account the direction of the ends so the
	 * reciprocal operations will not be equivalent, i.e.: <code>hasDirectedEnds(outNode, inNode) != hasDirectedEnds(inNode, outNode)</code>.
	 * @param outNode the out node (from which the edge leaves).
	 * @param inNode the in node (to which the edge goes to).
	 * @return
	 */
	boolean hasDirectedEnds(N outNode, N inNode);

	/**
	 * Get the node to which the end goes into. 
	 * @return The node, which will not be null.
	 */
	N getInNode();
	
	/**
	 * Get the node to which the end comes from. 
	 * @return The node, which will not be null.
	 */
	N getOutNode();
	
}
