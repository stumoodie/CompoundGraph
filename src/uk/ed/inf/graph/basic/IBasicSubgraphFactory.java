package uk.ed.inf.graph.basic;

import java.util.Iterator;

public interface IBasicSubgraphFactory<
		N extends IBasicNode<N, ? extends IBasicEdge<N, ?>>,
		E extends IBasicEdge<N, E>
> {

	void addNode(N node);
	
	void addEdge(E edge);
	
	Iterator<N> nodeIterator();
	
	Iterator<E> edgeIterator();
	
	IBasicSubgraph<N, E> createSubgraph();
	
//	IBasicSubgraph<N, E> createEmptySubgraph();
	
	IBasicSubgraph<N, E> createInducedSubgraph();
}
