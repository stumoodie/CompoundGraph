/*
  Licensed to the Court of the University of Edinburgh (UofE) under one
  or more contributor license agreements.  See the NOTICE file
  distributed with this work for additional information
  regarding copyright ownership.  The UofE licenses this file
  to you under the Apache License, Version 2.0 (the
  "License"); you may not use this file except in compliance
  with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing,
  software distributed under the License is distributed on an
  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
  KIND, either express or implied.  See the License for the
  specific language governing permissions and limitations
  under the License.
*/

package uk.ac.ed.inf.graph.util;

import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundNode;


/**
 * A set designed to provide convenient access to directed edges. This interface will typically be implemented and used
 * as a data structure with the graph library classes themselves rather than by clients of the library. The
 * set does not ensure that the edges all belong to the same graph (although this is recommended and the client should ensure this),
 * it only requires that the edge indexes are unique, based on <code>equals()</code> and their comparitor.   
 * 
 * @author smoodie
 *
 * @param <N> The node class to be used. It must implement <code>INode</code>.
 * @param <E> The edge class to be used. It must implement <code>IEdge</code>.
 */
public interface IDirectedEdgeSet<
		N extends ICompoundNode,
		E extends ICompoundEdge
> extends IEdgeSet<N, E> {

//	/**
//	 * Does the edge set contain at least one edge with these nodes.
//	 * This assumes a direction to the nodes so if the edges are undirected
//	 * the method must be called for both orientations of the edge.
//	 * @param outNode The node from which the edge is leading out from.
//	 * @param inNode The node from which the edge is leading into.
//	 * @return True if one or more edges has this connectivity, false otherwise. 
//	 */
//	boolean contains(N outNode, N inNode);
	
//	/**
//	 * Get the edges that match the given node connectivity.
//	 * The connectivity is directed so to find an undirected edge the reciprocal
//	 * node connectivity may need to be used. 
//	 * @param outNode The node that the edge is leading out from. Cannot be null.
//	 * @param inNode The node that the edge is leading into. Cannot be null.
//	 * @return The set of edges containing this connectivity. Note that the set is guaranteed to
//	 *  contain at least one edge.
//	 *  @throws NullPointerException if the parameters are null.
//	 *  @throws IllegalArgumentException if <code>contains(N outNode, N inNode) == false</code>
//	 */
//	SortedSet<E> get(N inNode, N outNode);

}
