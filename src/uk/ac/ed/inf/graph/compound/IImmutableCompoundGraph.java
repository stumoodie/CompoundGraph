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
package uk.ac.ed.inf.graph.compound;

import java.util.Iterator;

import uk.ac.ed.inf.tree.ITree;

public interface IImmutableCompoundGraph {

	/**
	 * Get the root element of this graph.
	 * @return The root node, which cannot be null.
	 */
	ICompoundGraphElement getRoot();

	ITree<ICompoundGraphElement> getElementTree();
	
	/**
	 * Tests if there is one or more directed edges from the <code>outNode</code> to the <code>inNode</code>.
	 * @param outNode the node the edge comes out from, can be null.
	 * @param inNode the node the edge goes into, can be null.
	 * @return true if there is such and edge, false otherwise. 
	 */
	boolean containsDirectedEdge(ICompoundNode outNode, ICompoundNode inNode);

	boolean containsConnection(ICompoundNode thisNode, ICompoundNode thatNode);

	/**
	 * Get the number of nodes in the graph.
	 * @return The number of nodes.
	 */
	int numNodes();
	
	/**
	 * Get the number of edges in the graph.
	 * @return The number of edges.
	 */
	int numEdges();
	
	/**
	 * Tests if the graph contains the node with the given index number.
	 * @param nodeIdx The index number of the node.
	 * @return True if the graph contains the node, false otherwise.
	 */
	boolean containsNode(int nodeIdx);
	
	/**
	 * Get the node matching the nodeIdx.
	 * @param nodeIdx the node index to lookup. 
	 * @return the node matching the index. Cannot be null.
	 * @throws IllegalArgumentException if node does not exists, i.e. <code>containsNode(nodeIdx) == false</code>
	 */
	ICompoundNode getNode(int nodeIdx);

	/**
	 * Tests if this node exists in the graph. 
	 * @param node to be tested.
	 * @return True if the graph contains the node, false otherwise.
	 */
	boolean containsNode(ICompoundNode node);

	/**
	 * Tests if the graph contains the edge. 
	 * @param edge the edge to test.
	 * @return true if the edge exists in the graph, false otherwise.
	 */
	boolean containsEdge(ICompoundEdge edge);

	/**
	 * Tests if the graph contains the edge of the given index. 
	 * @param edgeIdx the index to test.
	 * @return true if matching edge can be found, false otherwise.
	 */
	boolean containsEdge(int edgeIdx);
	
	/**
	 * Get the edge with this index.
	 * @param edgeIdx The index of the edge.
	 * @return The edge matching the index or null if it does not exist.
	 */
	ICompoundEdge getEdge(int edgeIdx);

	/**
	 * Get an iterator that traverses all the nodes in this graph. The traversal order
	 * is not guaranteed, but all nodes will be traversed. 
	 * @return The node iterator, guaranteed to be non-null.
	 */
	Iterator<ICompoundNode> nodeIterator();
	
	/**
	 * Get an iterator that traverses all the edges in this graph. No traversal order
	 * is guaranteed.
	 * @return The edge iterator, guaranteed to be non-null.
	 */
	Iterator<ICompoundEdge> edgeIterator();

	/**
	 * get the number elements in the graph
	 * @return the number of elements
	 */
	int numElements();
	
	/**
	 * Iterates over all the elements in the graph.
	 * @return the iterator, which will not be null.
	 */
	Iterator<ICompoundGraphElement> elementIterator();

}
