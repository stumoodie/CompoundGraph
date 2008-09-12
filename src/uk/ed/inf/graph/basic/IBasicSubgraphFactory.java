package uk.ed.inf.graph.basic;

import java.util.Iterator;

public interface IBasicSubgraphFactory<
		N extends IBasicNode<N, ? extends IBasicEdge<N, ?>>,
		E extends IBasicEdge<N, E>
> {

	/** The graph that this factory will create a subgraph for.
	 * @return the owning graph, which cannot be null.
	 */
	IBasicGraph<N, E> getGraph();

	void addNode(N node);
	
	void addEdge(E edge);
	
	Iterator<N> nodeIterator();
	
	Iterator<E> edgeIterator();
	
	IBasicSubgraph<N, E> createSubgraph();
	
	IBasicSubgraph<N, E> createInducedSubgraph();
}
