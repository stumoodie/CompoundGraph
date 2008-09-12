package uk.ed.inf.graph.compound;

import uk.ed.inf.graph.directed.IDirectedEdge;


public interface ICompoundEdge<
		N extends ICompoundNode<N, ? extends ICompoundEdge<N, ?>>,
		E extends ICompoundEdge<N, E>
> extends IDirectedEdge<N, E> {

	ICompoundGraph<N, E> getGraph();

	IChildCompoundGraph<N, E> getOwningChildGraph();
	
}
