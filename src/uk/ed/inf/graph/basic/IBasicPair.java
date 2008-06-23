package uk.ed.inf.graph.basic;


public interface IBasicPair<
	N extends IBasicNode<N, ? extends IBasicEdge<N, E>>,
	E extends IBasicEdge<N, E>
> {
	
	/**
	 * Is this node contained in this end pair.
	 * @param node The node to test, can be null.
	 * @return true is node is contained, else false.
	 */
	boolean containsNode(N node);

	/**
	 * Tests if this end pair is made up of these two nodes. This is ignores the direction of
	 * the edge these edges make up. In all cases, even for directed edges the following holds:
	 * <p>
	 * <code>hasEnds(endOne, endTwo) == hasEnds(endTwo, endOne)</code>
	 * @param endOne The first node end.
	 * @param endTwo The second node end.
	 * @return true if both nodes make up the end pair, false otherwise.
	 */
	boolean hasEnds(N endOne, N endTwo);
	
	/**
	 * Get the other node to this one in the pair.
	 * @param node The node to test, cannot be null.
	 * @return The other node of the pair. If the nodes in the pair are identical then
	 *  will return the same instance as <code>node</code>.
	 */
	N getOtherNode(N node);
	
	/**
	 * Test whether a pair is equal, that is do they include the same pair of nodes.
	 * The ordering is important and reciprocal pairs are not regarded as equivalent.
	 * This is in addition to the standard contact for equals. 
	 * @param other the other object to compare for equality.
	 * @return true if they are equals by the equals contract, false otherwise.
	 */
	boolean equals(Object other);
	
	/**
	 * The hashCode that should given identical behaviour to equals. That is is equals returns
	 * true both object should have the same hash code value.
	 * @return
	 */
	int hashCode();
}
