package uk.ed.inf.graph.directed;

import uk.ed.inf.graph.basic.IBasicSubgraph;

public interface IDirectedSubgraph<
		N extends IDirectedNode<N, ? extends IDirectedEdge<N, ?>>,
		E extends IDirectedEdge<N, E>
> extends IBasicSubgraph<N, E>, IDirectedGraph<N, E> {

}
