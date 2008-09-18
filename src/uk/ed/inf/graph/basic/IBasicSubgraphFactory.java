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

	/**
	 * Add a node to the factory that will be used to compose the subgraph.
	 * @param node The node to be added. Cannot be null.
	 * @throws NullPointerException if <code>node</code> is null.
	 * @throws IllegalArgumentException if <code>node</code> does not belong
	 * the the same graph as the factory.
	 */
	void addNode(N node);
	
	/**
	 * Add an edge to the factory to be used to compose the subgraph. 
	 * @param edge
	 * @throws NullPointerException if <code>edge</code> is null.
	 * @throws IllegalArgumentException if <code>edge</code> does not belong
	 * the the same graph as the factory.
	 */
	void addEdge(E edge);
	
	/**
	 * Iterate over the nodes that have been added to this factory.
	 * @return the node iterator, which cannot be null.
	 */
	Iterator<N> nodeIterator();
	
	/**
	 * Iterate over the edges that have been added to the factory. 
	 * @return the edge iterator that cannot be null.
	 */
	Iterator<E> edgeIterator();
	
	/**
	 * Create a subgraph composed of the nodes and edges added to this factory.
	 * @return the new subgraph, which cannot be null, but can be empty if no nodes and edges were added.
	 */
	IBasicSubgraph<N, E> createSubgraph();
	
	/**
	 * Create an induced subgraph of the nodes and edges added to this factory. This subgraph also
	 * contains edges that exist between all the nodes added to the factory, even if they are not
	 * explicitly added to the factory. 
	 * @return the new subgraph, which cannot be null, but can be empty if no nodes and edges held
	 *  by the factory.
	 */
	IBasicSubgraph<N, E> createInducedSubgraph();
}
