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
package uk.ed.inf.graph.compound;


public interface IModifiableCompoundGraph <
		N extends ICompoundNode<N, ? extends ICompoundEdge<N, ?>>,
		E extends ICompoundEdge<N, E>
> {

	/**
	 * Gets the node factory to add new nodes to the root child graph of this compound graph.
	 * @return the edge factory, which cannot be null.
	 */
	ICompoundNodeFactory<N, E> nodeFactory();

	/**
	 * Gets an edge factory that works out the LCA of two nodes making up the edge
	 * and assigns it to the appropriate compound graph. This is more permissive than the
	 * edge factory from a ChildCompoundGraph, which requires to to know that the edge
	 * belongs in that child graph (i.e. its root node is the LCA of the out and in nodes 
	 * of the edge).  
	 * @return The edge factory, which cannot be null.
	 */
	ICompoundEdgeFactory<N, E> edgeFactory();


	/**
	 * Gets a subgraph factory used to create a subgraph of this graph.
	 * @return a new instance of the subgraph factory.
	 */
	ISubCompoundGraphFactory<N,E> subgraphFactory();


	/**
	 * Tests if subgraph removal will succeed. To succeed the subgraph must belong to this graph,
	 *  it must be a consistent snapshot and cannot be null.
	 * @param subgraph the subgraph to be removed, which can be null.
	 * @return true if the subgraph will succeed, false otherwise. 
	 */
	boolean canRemoveSubgraph(ISubCompoundGraph<? extends N, ? extends E> subgraph);
	
	/**
	 * Removes the nodes and edges defined in the subgraph from this graph. The subgraph must be consistent with
	 *  this graph and be a subgraph of this graph.
	 * @param subgraph The subgraph to remove, cannot be null.
	 * @throws IllegalArgumentException if <code>canRemoveSubgraph(subgraph) == false</code>.
	 * 
	void removeSubgraph(ISubCompoundGraph<? extends N, ? extends E> subgraph);

	/**
	 * Tests whether the subGraph can be copied to this graph. To be true the subgraph must be an induced subgraph
	 *  that is a consistent of the super graph. It must also be not null.
	 * @param subGraph the subgraph to test, can be null. 
	 * @return true if the subgraph is valid to copy from, false otherwise.
	 */
	boolean canCopyHere(ISubCompoundGraph<? extends N, ? extends E> subgraph);

	/**
	 * Copies a subgraph into this graph. Note that the subgraph can be from a different graph or subgraph
	 * of this graph, since the structure of the graph is copied not the nodes and edges instances
	 * themselves. Note that the subgraph must be valid to be copied.
	 * @param subGraph the subgraph to copy
	 * @throws IllegalArgumentException if <code>canCopyHere(subGraph) == false</code>.
	 */
	void copyHere(ISubCompoundGraph<? extends N, ? extends E> subgraph);
	
	/**
	 * Retrieves the nodes and edges created in this graph by the last copy operation. The subgraph
	 * is <b>not</b> guaranteed to be a consistent snapshot of this graph.   If not copy operation has
	 * been performed then an empty subset will be returned.
	 * @return the subgraph of copied components, or an empty subset of not copy operation has been perfromed. 
	 */
	ISubCompoundGraph<N, E> getCopiedComponents();
}
