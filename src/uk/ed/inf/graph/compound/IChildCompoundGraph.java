package uk.ed.inf.graph.compound;

import uk.ed.inf.graph.basic.IBasicGraph;


public interface IChildCompoundGraph<
		N extends ICompoundNode<N, ? extends ICompoundEdge<N, ?>>,
		E extends ICompoundEdge<N, E>
> extends ICompoundGraph<N, E> {
	
	IBasicGraph<N, E> getSuperGraph();
}
