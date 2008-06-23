package uk.ed.inf.graph.directed;

import uk.ed.inf.graph.basic.IBasicEdge;


public interface IDirectedEdge<
		N extends IDirectedNode<N, ? extends IDirectedEdge<N, ?>>,
		E extends IDirectedEdge<N, E>
> extends IBasicEdge<N, E> {
	
	IDirectedPair<N, E> getConnectedNodes();

}
