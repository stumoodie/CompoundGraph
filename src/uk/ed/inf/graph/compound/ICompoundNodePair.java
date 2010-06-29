package uk.ed.inf.graph.compound;


public interface ICompoundNodePair {

	/**
	 * Is this node contained in this end pair.
	 * @param node The node to test, can be null.
	 * @return true is node is contained, else false.
	 */
	boolean containsNode(ICompoundNode node);

	/**
	 * Tests if this end pair is made up of these two nodes. This is ignores the direction of
	 * the edge these edges make up. In all cases, even for directed edges the following holds:
	 * <p>
	 * <code>hasEnds(endOne, endTwo) == hasEnds(endTwo, endOne)</code>
	 * @param endOne The first node end.
	 * @param endTwo The second node end.
	 * @return true if both nodes make up the end pair, false otherwise.
	 */
	boolean hasEnds(ICompoundNode endOne, ICompoundNode endTwo);
	
	/**
	 * Get the other node to this one in the pair.
	 * @param node The node to test, cannot be null.
	 * @return The other node of the pair. If the nodes in the pair are identical then
	 *  will return the same instance as <code>node</code>.
	 */
	ICompoundNode getOtherNode(ICompoundNode node);

	/**
	 * Tests if the ends exist in this directed pair. Takes into account the direction of the ends so the
	 * reciprocal operations will not be equivalent, i.e.: <code>hasDirectedEnds(outNode, inNode) != hasDirectedEnds(inNode, outNode)</code>.
	 * @param outNode the out node (from which the edge leaves).
	 * @param inNode the in node (to which the edge goes to).
	 * @return
	 */
	boolean hasDirectedEnds(ICompoundNode outNode, ICompoundNode inNode);

	/**
	 * Get the node to which the end goes into. 
	 * @return The node, which will not be null.
	 */
	ICompoundNode getInNode();
	
	/**
	 * Get the node to which the end comes from. 
	 * @return The node, which will not be null.
	 */
	ICompoundNode getOutNode();

	/**
	 * Create a new node pair, with the nodes reversed as if this edge was pointing in the other direction.
	 * @return the new directed edge with the in and out nodes switched relative to this one. Cannot be null.
	 */
	ICompoundNodePair reversedNodes();

	boolean isSelfEdge();
	
}
