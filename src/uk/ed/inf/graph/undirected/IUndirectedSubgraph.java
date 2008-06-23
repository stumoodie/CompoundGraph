package uk.ed.inf.graph.undirected;

import uk.ed.inf.graph.basic.IBasicSubgraph;

public interface IUndirectedSubgraph<
		N extends IUndirectedNode<N, ? extends IUndirectedEdge<N, E>>,
		E extends IUndirectedEdge<N, E>
> extends IBasicSubgraph<N, E> {

}
