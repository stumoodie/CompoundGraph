package uk.ed.inf.graph.directed;

import uk.ed.inf.graph.basic.IBasicEdgeFactory;

public interface IDirectedEdgeFactory<
		N extends IDirectedNode<N, ? extends IDirectedEdge<N, ?>>,
		E extends IDirectedEdge<N, E>
> extends IBasicEdgeFactory<N, E> {

	void setPair(N outNode, N inNode);
	
}
