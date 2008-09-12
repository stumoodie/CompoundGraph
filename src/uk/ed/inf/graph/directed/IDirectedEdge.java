package uk.ed.inf.graph.directed;

import uk.ed.inf.graph.basic.IBasicEdge;


public interface IDirectedEdge<
		N extends IDirectedNode<N, ? extends IDirectedEdge<N, ?>>,
		E extends IDirectedEdge<N, E>
> extends IBasicEdge<N, E> {
	
	IDirectedPair<N, E> getConnectedNodes();

	/**
	 * Tests if this directed edge has these ends. Ignores the directions of the ends.
	 * @param ends The ends to be tested.
	 * @return true if the edge contains the ends, false otherwise. 
	 */
	boolean hasDirectedEnds(IDirectedPair<? super N, ? super E> ends);
	
}
