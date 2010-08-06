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

public interface ICompoundGraphMoveBuilder {

	/**
	 * Tests whether the subGraph can be moved to this graph. To be true the subgraph must be an induced subgraph
	 *  that is a consistent of the super graph. It must also be not null and belong to the
	 *  same graph as this one. The subgraph must also be valid.
	 *  Also no nodes in the induced sub-graph of <code>subGraph</code> can be children
	 *  of this child compound graph. 
	 * @param subGraph the subgraph to test, can be null. 
	 * @return true if the subgraph is valid to copy from, false otherwise.
	 * @deprecated use setSourceSubgraph() and canMoveHere() instead
	 */
	@Deprecated
	boolean canMoveHere(ISubCompoundGraph subGraph);

	boolean canMoveHere();
	
	/**
	 * Moves a subgraph into this graph. It does this by creating a new set of
	 * nodes in this subgraph and removing the nodes defined in <code>subGraph</code>.
	 * The new nodes from the move can be found in <code>getMovedComponents()</code> 
	 * and the removed nodes will be found in <code>subGraph</code>.
	 * @param subGraph the subgraph to move.
	 * @throws IllegalArgumentException if <code>canMoveHere(subGraph) == false</code>.
	 * @deprecated Use setSourceSubgraph() and makeMove() instead.
	 */
	@Deprecated
	void moveHere(ISubCompoundGraph subGraph);
	
	/**
	 * Retrieves the nodes and edges created in this graph by the last copy operation. The subgraph
	 * is <b>not</b> guaranteed to be a consistent snapshot of this graph.   If not copy operation has
	 * been performed then an empty subset will be returned.
	 * @return the subgraph of copied components, or an empty subset of not copy operation has been perfromed. 
	 */
	ISubCompoundGraph getMovedComponents();
	
	ISubCompoundGraph getSourceSubgraph();
	
	IChildCompoundGraph getDestinationChildGraph();
	
	/**
	 * Sets the subgraph which is to be moved.
	 * @param sourceSubCompoundGraph
	 */
	void setSourceSubgraph(ISubCompoundGraph sourceSubCompoundGraph);

	/**
	 * Make a move of subgraph into the destination graph
	 */
	void makeMove();

	
	ISubCompoundGraph getRemovedComponents();
}