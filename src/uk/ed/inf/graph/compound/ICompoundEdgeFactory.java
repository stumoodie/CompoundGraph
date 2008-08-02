package uk.ed.inf.graph.compound;

import uk.ed.inf.graph.directed.IDirectedEdgeFactory;

public interface ICompoundEdgeFactory<
		N extends ICompoundNode<N, ? extends ICompoundEdge<N, ?>>,
		E extends ICompoundEdge<N, E>
> extends IDirectedEdgeFactory<N, E> {
	
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
	 * @param outNode the node the edge comes from. 
	 * @param inNode the node the edge goes to.
	 * @return The newly created edge.
	 * @throw IllegalArgumentException if <code>isValidNodePair(outNode, inNode) == false</code>.  
	 */
	E createEdge(N outNode, N inNode);
}
