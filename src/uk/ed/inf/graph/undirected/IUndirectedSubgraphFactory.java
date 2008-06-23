package uk.ed.inf.graph.undirected;

import uk.ed.inf.graph.basic.IBasicSubgraphFactory;

public interface IUndirectedSubgraphFactory<
		N extends IUndirectedNode<N, ? extends IUndirectedEdge<N, E>>,
		E extends IUndirectedEdge<N, E>
> extends IBasicSubgraphFactory<N, E> {

}
