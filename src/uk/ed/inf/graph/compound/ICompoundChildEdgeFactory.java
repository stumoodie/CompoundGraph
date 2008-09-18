package uk.ed.inf.graph.compound;


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
public interface ICompoundChildEdgeFactory<
		N extends ICompoundNode<N, ? extends ICompoundEdge<N, ?>>,
		E extends ICompoundEdge<N, E>
> extends ICompoundEdgeFactory<N, E> {

	/**
	 * @see ICompoundEdgeFactory for definition.
	 * Not that this implementation will always return the child graph regardless of whether
	 * the node pair is set.
	 * @return the owning child graph of the new edge.
	 */
	IChildCompoundGraph<N, E> getOwningChildGraph();
	
}
