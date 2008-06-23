package uk.ed.inf.graph.undirected;

import uk.ed.inf.graph.basic.IBasicNodeFactory;

public interface IUndirectedNodeFactory<
		N extends IUndirectedNode<N, ? extends IUndirectedEdge<N, E>>,
		E extends IUndirectedEdge<N, E>
> extends IBasicNodeFactory<N, E> {

}
