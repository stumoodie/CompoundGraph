package uk.ed.inf.graph.directed;

import uk.ed.inf.graph.basic.IBasicGraph;


public interface IDirectedGraph<
		N extends IDirectedNode<N, ? extends IDirectedEdge<N, ?>>,
		E extends IDirectedEdge<N, E>
> extends IBasicGraph<N, E> {

	/**
	 * Tests if there is one or more directed edges from the <code>outNode</code> to the <code>inNode</code>.
	 * @param outNode the node the edge comes out from, can be null.
	 * @param inNode the node the edge goes into, can be null.
	 * @return true if there is such and edge, false otherwise. 
	 */
	boolean containsDirectedEdge(N outNode, N inNode);

	/**
	 * Test if there is one or more directed edge defined by the end pair <code>ends</code>.
	 * @param ends the pair of nodes to be tested, can be null.
	 * @return true if there is at least one edge between then, false otherwise.
	 */
	boolean containsDirectedEdge(IDirectedPair<N, E> ends);
	
}
