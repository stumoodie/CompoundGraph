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

import java.util.Iterator;
import java.util.SortedSet;

import uk.ed.inf.graph.state.IRestorableNode;


public interface ICompoundNode extends ICompoundGraphElement, IRestorableNode {
	
	int getIndex();
	
	int getInDegree();
	
	int getOutDegree();
	
	/**
	 * Has at least one edge coming into this one from outNode 
	 * @param outNode
	 * @return
	 */
	boolean hasInEdgeFrom(ICompoundNode outNode);
	
	/**
	 * Gets the set of out edges to which this node is the in node and
	 * <code>outNode</code> is the out node.
	 * @param outNode the out node to the edge
	 * @return a set of edges with this node as in node and <code>outNode</code> as out node.
	 */
	SortedSet<ICompoundEdge> getInEdgesFrom(ICompoundNode outNode);  
	
	/**
	 * Has at least one edge going out from this node to inNode.
	 * @param inNode the in node to search for, which can be null.
	 * @return true if this node is the out node and <code>inNode</code> is the in node to an directed edge incident to this node. 
	 */
	boolean hasOutEdgeTo(ICompoundNode inNode);
	
	/**
	 * Gets the set of out edges to which this node is the out node and
	 * <code>inNode</code> is the in node.
	 * @param inNode the in node to the edge
	 * @return a set of edges with this node as out node and <code>inNode</code> as in node.
	 */
	SortedSet<ICompoundEdge> getOutEdgesTo(ICompoundNode inNode);  

	/**
	 * Gets all edges connecting this node
	 * @return
	 */
	Iterator<ICompoundEdge> getInEdgeIterator();
	
	/**
	 * Gets all edges going out from this node. 
	 * @return
	 */
	Iterator<ICompoundEdge> getOutEdgeIterator();

	/**
	 * Gets the incident nodes which are the in nodes to the incident edge.
	 * This means that this node is the out node to the given edge.
	 * @return an iterator over all the resulting in nodes.
	 */
	Iterator<ICompoundNode> getInNodeIterator();
	
	/**
	 * Gets the incident nodes which are out nodes to the incident directed edge.
	 * This means that this node is the in node to given edge.  
	 * @return an iterator for the results.
	 */
	Iterator<ICompoundNode> getOutNodeIterator();

	
	/**
	 * Tests whether this node shares one or more edges with another node, irrespective of the 
	 * direction of that edge. 
	 * @param other The other node to test. Can be null.
	 * @return True if and edge is shared, false otherwise.
	 */
	boolean hasEdgeWith(ICompoundNode other);
	
	/**
	 * Gets the edges shared with the other node. 
	 * @param other The other node to test. Cannot be null.
	 * @return A set of edges sorted by edge index, which 
	 * 
	 */
	SortedSet<ICompoundEdge> getEdgesWith(ICompoundNode other);
	
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
	Iterator<ICompoundEdge> edgeIterator();

	/**
	 * Provides an iterator that lists all nodes connected to this node via another edge. When self-edges
	 * are encountered a reference to this node will be returned. The iterator may return the same node more than once
	 * if this node has multiple edges to it.
	 * @return the node iterator.
	 */
	Iterator<ICompoundNode> connectedNodeIterator();

	boolean containsEdge(ICompoundEdge edge);

	boolean containsInEdge(ICompoundEdge edge);

	boolean containsOutEdge(ICompoundEdge edge);

	void addOutEdge(ICompoundEdge compoundEdge);

	void addInEdge(ICompoundEdge compoundEdge);
}
