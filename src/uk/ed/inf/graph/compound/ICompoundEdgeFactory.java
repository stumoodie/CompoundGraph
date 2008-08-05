package uk.ed.inf.graph.compound;

import uk.ed.inf.graph.directed.IDirectedEdgeFactory;

/**
 * Factory that creates an edge within a particular child graph. It does not work out the LCA node that the
 * edge should be added to it and assumes that the edge should be added to the child graph associated with this 
 * factory.
 *  
 * @author smoodie
 *
 * @param <N>
 * @param <E>
 */
public interface ICompoundEdgeFactory<
		N extends ICompoundNode<N, ? extends ICompoundEdge<N, ?>>,
		E extends ICompoundEdge<N, E>
> extends IDirectedEdgeFactory<N, E> {

	/**
	 * Sets the nodes to be used to create the edge.
	 * @param outNode
	 * @param inNode
	 * @throws IllegalArgumentException if the nodes are not valid, ie. <code>isValidNodePair(outNode, inNode) == true</code>.
	 */
	void setPair(N outNode, N inNode);
	
	/**
	 * Tests if the LCA of <code>outNode</code> and <code>inNode</code> is the root node of the owning child graph.
	 * @param outNode out node of edge, can be null.
	 * @param inNode in node of edge, can be null.
	 * @return true if this is the case, false otherwise. 
	 */
	boolean isValidNodePair(N outNode, N inNode);
	
	/**
	 * The child graph that will contain any edges created by this factory. 
	 * @return The child graph, cannot be null.
	 */
	IChildCompoundGraph<N, E> getOwningChildGraph();
	
	/**
	 * Creates a new directed edge from <code>outNode</code> to <code>inNode</code> which is
	 *  owned by the childCompoundGraph returned by <code>getOwningChildGraph()</code>.
	 * @return The newly created edge.
	 * @throw IllegalArgumentException if <code>isValidNodePair(outNode, inNode) == false</code>.  
	 */
	E createEdge();
}
