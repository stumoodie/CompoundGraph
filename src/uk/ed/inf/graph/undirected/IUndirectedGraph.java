package uk.ed.inf.graph.undirected;

import uk.ed.inf.graph.basic.IBasicGraph;

public interface IUndirectedGraph<
		N extends IUndirectedNode<N, ? extends IUndirectedEdge<N, E>>,
		E extends IUndirectedEdge<N, E>
>
extends IBasicGraph<N, E> {

	IUndirectedEdgeFactory<N, E> edgeFactory();
	
}
