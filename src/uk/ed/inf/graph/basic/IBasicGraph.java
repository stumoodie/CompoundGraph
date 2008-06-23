package uk.ed.inf.graph.basic;

import java.util.Iterator;


public interface IBasicGraph<
		N extends IBasicNode<N, ? extends IBasicEdge<N, ?>>,
		E extends IBasicEdge<N, E>
> {

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
	N getNode(int nodeIdx);

	/**
	 * Tests if this node exists in the graph. 
	 * @param node to be tested.
	 * @return True if the graph contains the node, false otherwise.
	 */
	boolean containsNode(N node);

	/**
	 * Tests if the graph contains an edge connecting these nodes. Ignores
	 * the direction of the edge.
	 * @param thisNode a node to test.
	 * @param thatNode the other node to test.
	 * @return true if the edge exists, false otherwise.
	 */
	boolean containsConnection(N thisNode, N thatNode);
	
	/**
	 * Tests if the graph contains the edge. 
	 * @param edge the edge to test.
	 * @return true if the edge exists in the graph, false otherwise.
	 */
	boolean containsEdge(E edge);

	/**
	 * Tests if the graph has an edge between the defined ends. 
	 * @param ends the ends of the edge to test.
	 * @return true if the edge exists in the graph, false otherwise.
	 */
	boolean containsConnection(IBasicPair<N, E> ends);

	/**
	 * Tests if the graph contains the edge of the given index. 
	 * @param edgeIdx the index to test.
	 * @return true if matching edge can be found, false otherwise.
	 */
	boolean containsEdge(int edgeIdx);
	
	/**
	 * Get the edge with this index.
	 * @param edgeIdx The index of the edge.
	 * @return The edge matching the index.
	 * @throws IllegalArgumentException if an edge matching this index does not exist, i.e. <code>containsEdge(edgeIdx) == false</code>.  
	 */
	E getEdge(int edgeIdx);

	/**
	 * Get an iterator that traverses all the nodes in this graph. The traversal order
	 * is not guaranteed, but all nodes will be traversed. 
	 * @return The node iterator, guaranteed to be non-null.
	 */
	Iterator<N> nodeIterator();
	
	/**
	 * Get an iterator that traverses all the edges in this graph. No traversal order
	 * is guaranteed.
	 * @return The edge iterator, guaranteed to be non-null.
	 */
	Iterator<E> edgeIterator();
	
	/**
	 * Removed all nodes and edges from the graph.
	 */
	void clear();
}
