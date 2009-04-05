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

public interface IBasicEdgeFactory<
		N extends IBasicNode<N, ? extends IBasicEdge<N, ?>>,
		E extends IBasicEdge<N, E>
> {

	/**
	 * Get the graph to which this factory acts upon.
	 * @return The owning graph, cannot be null.
	 */
	IBasicGraph<N, E> getGraph();
	
	/**
	 * Sets a pair of nodes with which to create a new edge.
	 * @param thisNode a node in the edge pair, cannot be null.
	 * @param thatNode another node in the edge pair, cannot be null.
	 * @throws IllegalArgumentException if <code>isValidNodePair(thisNode, thatNode) == false</code>.
	 */
	void setPair(N thisNode, N thatNode);
	
	
	/**
	 * Test if the node pair is valid. Tests include, does the node pair belong to the 
	 * same graph as that return by <code>getGraph()</code>, do both nodes belong to the same
	 * graph and are both nodes not null.
	 * @param thisNode a node that may be the end of an edge.
	 * @param thatNode another node that may be the end of an edge.
	 * @return true if the above conditions are met, false otherwise.
	 */
	boolean isValidNodePair(N thisNode, N thatNode);
	
	/**
	 * Returns the current node pair, or false if no nodes have been set. 
	 * @return the current node pair or false if <code>setPair(thisNode, thatNode)</code> has not
	 * been called.
	 */
	IBasicPair<N, E> getCurrentNodePair();

	/**
	 * Tests is all the conditions have been met in the factory so that
	 * edge creation will succeed. AT the very least <code>isValidNodePair(thisNode, thatNode)</code>
	 * should be true.
	 * @return true if this is the case, false otherwise.
	 */
	boolean canCreateEdge();
	
	/**
	 * Create a new edge and add it to the graph.
	 * @return the newly created edge that is added to the graph 
	 */
	E createEdge();
	
}
