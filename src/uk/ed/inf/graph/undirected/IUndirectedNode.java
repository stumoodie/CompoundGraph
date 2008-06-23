package uk.ed.inf.graph.undirected;

import uk.ed.inf.graph.basic.IBasicNode;

public interface IUndirectedNode<
		N extends IUndirectedNode<N, ? extends IUndirectedEdge<N, ?>>,
		E extends IUndirectedEdge<N, E>
> extends IBasicNode<N, E> {

}
