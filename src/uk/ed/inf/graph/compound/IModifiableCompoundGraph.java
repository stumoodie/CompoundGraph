package uk.ed.inf.graph.compound;

import uk.ed.inf.graph.basic.IBasicSubgraph;
import uk.ed.inf.graph.basic.IModifiableGraph;

public interface IModifiableCompoundGraph <
		N extends ICompoundNode<N, ? extends ICompoundEdge<N, ?>>,
		E extends ICompoundEdge<N, E>
> extends IModifiableGraph<N, E> {

	ICompoundNodeFactory<N, E> nodeFactory();

	/**
	 * Gets an edge factory that works out the LCA of two nodes making up the edge
	 * and assigns it to the appropriate compound graph. This is more permissive than the
	 * edge factory from a ChildCompoundGraph, which requires to to know that the edge
	 * belongs in that child graph (i.e. its root node is the LCA of the out and in nodes 
	 * of the edge).  
	 * @return The edge factory, which cannot be null.
	 */
	
	ICompoundEdgeFactory<N, E> edgeFactory();


	ISubCompoundGraphFactory<N,E> subgraphFactory();


	ISubCompoundGraph<N, E> getCopiedComponents();
}
