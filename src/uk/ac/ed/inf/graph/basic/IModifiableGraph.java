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
package uk.ac.ed.inf.graph.basic;

import uk.ac.ed.inf.graph.basic.listeners.IGraphChangeListenee;

public interface IModifiableGraph<
		N extends IBasicNode<N, ? extends IBasicEdge<N, ?>>,
		E extends IBasicEdge<N, E>
> extends IGraphChangeListenee<N, E> {

	/**
	 * Gets the nodeFactory for this class. This factory is a singleton so this method must always
	 *  return the same instance of the factory. The factory is the only way that new nodes can be added to
	 *  this graph.
	 * @return The node factory.
	 * @throws UnsupportedOperationException if this graph implementation does support new node creation and so a factory
	 *  is not provided, i.e. <code>canCreateNodes() == false</code>.
	 */
	IBasicNodeFactory<N, E> nodeFactory();
	
	/**
	 * Gets the edgeFactory for this class. The factory is the only way that new edges can be added to
	 *  this graph.
	 * @return The edge factory.
	 * @throws UnsupportedOperationException if this graph implementation does support new edge creation and so a factory
	 *  is not provided, i.e. <code>canCreateEdges() == false</code>.
	 */
	IBasicEdgeFactory<N, E> edgeFactory();
	
	/**
	 * Gets the subgraphFactory for this class. 
	 * @return The subgraph factory.
	 * @throws UnsupportedOperationException if this graph implementation does support new subgraph creation and so a factory
	 *  is not provided, i.e. <code>canCreateSubgraphs() == false</code>.
	 */
	IBasicSubgraphFactory<N,E> subgraphFactory();
	
	/**
	 * Removes the nodes and edges defined in the subgraph from this graph. The subgraph must be consistent with
	 *  this graph and be a subgraph of this graph.
	 * @param subgraph The subgraph to remove, cannot be null.
	 * @throws NullPointerException if <code>subgraph</code> is null.
	 * @throws UnsupportedOperationException if removal is not supported, i.e. when <code>canRemoveSubgraph() == false</code>.
	 * @throws IllegalArgumentException if the subgraph does not belong to this graph: <code>subgraph.getOwningGraph() != this</code>.
	 * @throws IllegalArgumentException if the subgraph is not consistent with this graph: <code>subgraph.isConsistentSnapshot() == false</code>. 
	 */
	void removeSubgraph(IBasicSubgraph<? extends N, ? extends E> subgraph);

	/**
	 * Tests whether the subGraph can be copied to this graph. To be true the subgraph must be an induced subgraph
	 *  that is a consistent of the super graph. It must also be not null.
	 * @param subGraph the subgraph to test, can be null. 
	 * @return true if the subgraph is valid to copy from, false otherwise.
	 */
	boolean canCopyHere(IBasicSubgraph<? extends N, ? extends E> subGraph);

	/**
	 * Copies a subgraph into this graph. Note that the subgraph can be from a different graph or subgraph
	 * of this graph, since the structure of the graph is copied not the nodes and edges instances
	 * themselves. Note that the subgraph must be valid to be copied.
	 * @param subGraph the subgraph to copy
	 */
	void copyHere(IBasicSubgraph<? extends N, ? extends E> subGraph);
	
	/**
	 * Retrieves the nodes and edges created in this graph by the last copy operation. The subgraph
	 * is <b>not</b> guaranteed to be a consistent snapshot of this graph.   If not copy operation has
	 * been performed then an empty subset will be returned.
	 * @return the subgraph of copied components, or an empty subset of not copy operation has been perfromed. 
	 */
	IBasicSubgraph<N, E> getCopiedComponents();
	
}