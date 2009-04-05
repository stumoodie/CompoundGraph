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

public interface ICompoundGraphMoveBuilder<
		N extends ICompoundNode<N, ? extends ICompoundEdge<N, ?>>,
		E extends ICompoundEdge<N, E>
> {

	ISubCompoundGraph<N, E> getSourceSubgraph();
	
	IChildCompoundGraph<N, E> getDestinationChildGraph();
	
	/**
	 * Sets the subgraph which is to be moved.
	 * @param sourceSubCompoundGraph
	 */
	void setSourceSubgraph(ISubCompoundGraph<? extends N, ? extends E> sourceSubCompoundGraph);

	/**
	 * Sets the child compound graph that is to be moved to.
	 * @param childCompoundGraph
	 */
	void setDestinatChildCompoundGraph(IChildCompoundGraph<? extends N, ? extends E> childCompoundGraph);

	/**
	 * Make a move of subgraph into the destination graph
	 */
	void makeMove();

	/**
	 * Gets the moved nodes and edges that were created in the destination graph as a
	 * subgraph of the destination graph.
	 * @return The subgraph of moved nodes, which will be empty of no nodes are moved.
	 */
	ISubCompoundGraph<N, E> getMovedComponents();
}