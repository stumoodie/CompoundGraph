/*
Copyright 2009, Court of the University of Edinburgh

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License. 
*/
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
	 * Get the number of nodes added to the factory;
	 * @return the number of nodes.
	 */
	int numNodes();
	
	/**
	 * Add an edge to the factory to be used to compose the subgraph. 
	 * @param edge
	 * @throws NullPointerException if <code>edge</code> is null.
	 * @throws IllegalArgumentException if <code>edge</code> does not belong
	 * the the same graph as the factory.
	 */
	void addEdge(E edge);
	
	/**
	 * The number of edges added to the factory.
	 * @return the number of edges.
	 */
	int numEdges();
	
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
