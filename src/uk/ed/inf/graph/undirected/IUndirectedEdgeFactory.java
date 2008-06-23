package uk.ed.inf.graph.undirected;

import uk.ed.inf.graph.basic.IBasicEdgeFactory;

public interface IUndirectedEdgeFactory<
		N extends IUndirectedNode<N, ? extends IUndirectedEdge<N, ?>>,
		E extends IUndirectedEdge<N, E>
> extends IBasicEdgeFactory<N, E> {

	void setPair(N oneNode, N twoNode);

}
