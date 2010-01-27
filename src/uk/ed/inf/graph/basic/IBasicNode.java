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
import java.util.SortedSet;


/**
 * A basic graph node useable by all graphs.
 * @author smoodie
 *
 * @param <N> The node type that must implement this interface.
 * @param <E> The edge type that must implement the <code>IEdge</code> class. 
 */
public interface IBasicNode<
	N extends INode,
	E extends IEdge
> extends INode, Comparable<N> {

	/**
	 * Get the graph that owns this node.  
	 * @return The graph instance which cannot be null.
	 */
	IBasicGraph<N, E> getGraph();
	
	/**
	 * Get the index of this node. 
	 * @return Returns a whole number (>=0).
	 */
	int getIndex();
	
	/**
	 * Tests whether this node shares one or more edges with another node, irrespective of the 
	 * direction of that edge. 
	 * @param other The other node to test. Can be null.
	 * @return True if and edge is shared, false otherwise.
	 */
	boolean hasEdgeWith(N other);
	
	/**
	 * Gets the edges shared with the other node. 
	 * @param other The other node to test. Cannot be null.
	 * @return A set of edges sorted by edge index, which 
	 * 
	 */
	SortedSet<E> getEdgesWith(N other);
	
	/**
	 * Get the degree of the this node. That is the number of edges associated with it. Note that in graph theory
	 * self edges (edges that start and finish on the same node) add 2 to the degree of a node.  
	 * @return The degree of the node.
	 */
	int getDegree();
	
	/**
	 * Provides an iterator that lists all edges associated with this node. In a directed graph
	 * this iterator ignores the direction of the edge.
	 * @return the edge iterator.
	 */
	Iterator<E> edgeIterator();

	/**
	 * Provides an iterator that lists all nodes connected to this node via another edge. When self-edges
	 * are encountered a reference to this node will be returned. The iterator may return the same node more than once
	 * if this node has multiple edges to it.
	 * @return the node iterator.
	 */
	Iterator<N> connectedNodeIterator();
	
	/**
	 * Has the node been removed from the graph? Nodes are not removed from the graph's data structures,
	 * but flagged as deleted.
	 * @return true if removed, false otherwise. 
	 */
	boolean isRemoved();
	
	/**
	 * Is the other node equal to this one. Should obey the standard contract for <code>equals</code>, and in addition 
	 * a node should be regarded as equal if its owning graph and index are identical. 
	 * @param other the other object to be tested.
	 * @return true if equals by the standard contract and if the owning graph and index are identical. false otherwise.
	 */
	boolean equals(Object other);
	
	/**
	 * Get the hash code for this node. Should be consistent with <code>equals</code>. 
	 * @return The hash code for this node.
	 */
	int hashCode();

	/**
	 * Compare this node to another node. Comparison should be based on the comparison order of the node's index
	 * only. The owning graph should be ignored. It is the job of the graph library to ensure that only
	 * nodes belonging to the same graph are compared.   
	 * @param other the other node to compare to.
	 */
	int compareTo(N other);
}
