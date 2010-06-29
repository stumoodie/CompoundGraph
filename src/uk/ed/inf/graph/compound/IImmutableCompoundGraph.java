package uk.ed.inf.graph.compound;

import java.util.Iterator;

import uk.ed.inf.tree.ITree;

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

	/**
	 * Test if there is one or more directed edge defined by the end pair <code>ends</code>.
	 * @param ends the pair of nodes to be tested, can be null.
	 * @return true if there is at least one edge between then, false otherwise.
	 */
	boolean containsConnection(ICompoundNodePair nodePair);
	
	boolean containsConnection(ICompoundNode thisNode, ICompoundNode thatNode);

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
	 * @return The edge matching the index.
	 * @throws IllegalArgumentException if an edge matching this index does not exist, i.IBasicEdge. <code>containsEdge(edgeIdx) == false</code>.  
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

	int numElements();
	
	Iterator<ICompoundGraphElement> elementIterator();

}
