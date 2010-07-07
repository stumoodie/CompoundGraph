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


public interface ISubCompoundGraph {

	ICompoundGraph getSuperGraph();
	
	ICompoundNode getNode(int nodeIdx);

	ICompoundEdge getEdge(int edgeIdx);

	ICompoundGraphElement getElement(int idx);
	
	int getNumTopNodes();
	
	Iterator<ICompoundNode> topNodeIterator();
	
	Iterator<ICompoundEdge> topEdgeIterator();
	
	Iterator<ICompoundGraphElement> topElementIterator();
	
	int numTopElements();

	boolean containsRoot() ;
	
	boolean isInducedSubgraph();
	
	boolean isConsistentSnapShot();

	/**
	 * Get the number of nodes in the graph.
	 * @return The number of nodes.
	 */
	int getNumNodes();
	
	/**
	 * Get the number of edges in the graph.
	 * @return The number of edges.
	 */
	int getNumEdges();
	
	/**
	 * Gets the total number of elements in the graph.
	 * @return the number of elements.
	 */
	int numElements();
	

	boolean containsElement(ICompoundGraphElement element);

	boolean containsElement(int idx);

	/**
	 * Tests if the graph contains the node with the given index number.
	 * @param nodeIdx The index number of the node.
	 * @return True if the graph contains the node, false otherwise.
	 */
	boolean containsNode(int nodeIdx);
	
	/**
	 * Tests if this node exists in the graph. 
	 * @param node to be tested.
	 * @return True if the graph contains the node, false otherwise.
	 */
	boolean containsNode(ICompoundNode node);

	/**
	 * Tests if the graph contains an edge connecting these nodes. Ignores
	 * the direction of the edge.
	 * @param thisNode a node to test.
	 * @param thatNode the other node to test.
	 * @return true if the edge exists, false otherwise.
	 */
	boolean containsConnection(ICompoundNode thisNode, ICompoundNode thatNode);

	/**
	 * Tests if the graph contains an edge connecting these nodes. Ignores
	 * the direction of the edge.
	 * @param pair the pair of nodes to test
	 * @return true if a connection involving these nodes exists.
	 */
	boolean containsConnection(CompoundNodePair pair);

	/**
	 * Tests if the graph contains the edge. 
	 * @param edge the edge to test.
	 * @return true if the edge exists in the graph, false otherwise.
	 */
	boolean containsEdge(ICompoundEdge edge);

	boolean containsDirectedEdge(ICompoundNode outNode, ICompoundNode inNode);

	boolean containsDirectedEdge(CompoundNodePair ends);

		/**
	 * Tests if the graph contains the edge of the given index. 
	 * @param edgeIdx the index to test.
	 * @return true if matching edge can be found, false otherwise.
	 */
	boolean containsEdge(int edgeIdx);
	
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
	 * Provides an iterator for all the elements in the subgraph
	 * @return the element iterator, which cannot be null.
	 */
	Iterator<ICompoundGraphElement> elementIterator();

	Iterator<ICompoundGraphElement> edgeLastElementIterator();

	int getNumTopEdges();
}
