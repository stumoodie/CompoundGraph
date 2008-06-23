package uk.ed.inf.graph.undirected;

import uk.ed.inf.graph.basic.IBasicEdge;

public interface IUndirectedEdge<
		N extends IUndirectedNode<N, ? extends IUndirectedEdge<N, E>>,
		E extends IUndirectedEdge<N, E>
> extends IBasicEdge<N, E> {

	IUndirectedGraph<N, E> getGraph();

	IUndirectedPair<N, E> getConnectedNodes();

}
