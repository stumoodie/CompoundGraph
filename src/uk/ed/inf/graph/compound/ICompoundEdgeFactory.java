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

	ICompoundGraph<N, E> getGraph();

}
