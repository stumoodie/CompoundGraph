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
package uk.ed.inf.graph.util;

import java.util.Set;
import java.util.SortedSet;

import uk.ed.inf.graph.basic.IEdge;
import uk.ed.inf.graph.basic.INode;

/**
 * A set designed to provide convenient access to edges. This interface will typically be implemented and used
 * as a data structure with the graph library classes themselves rather than by clients of the library. The
 * set does not ensure that the edges all belong to the same graph (although this is recommended and the client should ensure this),
 * it only requires that the edge indexes are unique, based on <code>equals()</code> and their comparitor.   
 * 
 * @author smoodie
 *
 * @param <N> The node class to be used. It must implement <code>INode</code>.
 * @param <E> The edge class to be used. It must implement <code>IEdge</code>.
 */
public interface IEdgeSet<
		N extends INode,
		E extends IEdge
> extends Set<E> {

	/**
	 * Does the edge set contain at least one edge with these nodes.
	 * This assumes a direction to the nodes so if the edges are undirected
	 * the method must be called for both orientations of the edge.
	 * @param thisNode a node the edge may be connected to.
	 * @param thatNode another node the edge may be connected to.
	 * @return True if one or more edges has this connectivity, false otherwise. 
	 */
	boolean contains(N thisNode, N thatNode);
	
	/**
	 * Get the edges that match the given node connectivity.
	 * The connectivity is undirected. 
	 * @param thisNode a node the edge may be connected to. Cannot be null.
	 * @param thatNode another node the edge may be connected to. Cannot be null.
	 * @return The set of edges containing this connectivity. Note that the set is guaranteed to
	 *  contain at least one edge.
	 *  @throws NullPointerException if the parameters are null.
	 *  @throws IllegalArgumentException if <code>contains(N thisNode, N thatNode) == false</code>
	 */
	SortedSet<E> get(N thisNode, N thatNode);

	/**
	 * Does this edge set contain one or more edges associated with this
	 * node?
	 * @param thisNode The node that is the point of reference.
	 * @param otherNode The node to be tested. Can be null.
	 * @return True if one or more edges conatin the node, false otherwise.
	 */
	boolean hasEdgesWith(N thisNode, N otherNode);

	/**
	 * Gets the edges that contain this node as one of its ends.
	 * @param thisNode the node that is the point of reference.
	 * @param otherNode The node associated with the edges. Cannot be null.
	 * @return The set of edges containing the matched nodes. The set will contain at least one node.
	 * @throws NullPointerException of node is null.
	 * @throws IllegalArgumentException if <code>hasEdgesWith(node) == false</code>.  
	 */
	SortedSet<E> getEdgesWith(N thisNode, N otherNode);
	
	/**
	 * Does an edge with this index exist in the set. 
	 * @param edgeIdx The edge index.
	 * @return True if an edge matching this index was found, false otherwise.
	 */
	boolean contains(int edgeIdx);
	
	/**
	 * Get the edge with the given index.
	 * @param edgeIdx The index of the edge to be obtained.
	 * @return The edge with this index. Will not be null.
	 * @throws IllegalArgumentException if <code>contains(edgeIdx) == false</code> 
	 */
	E get(int edgeIdx);
	
}
