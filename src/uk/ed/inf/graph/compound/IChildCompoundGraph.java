package uk.ed.inf.graph.compound;



public interface IChildCompoundGraph<
		N extends ICompoundNode<N, ? extends ICompoundEdge<N, ?>>,
		E extends ICompoundEdge<N, E>
> extends ICompoundGraph<N, E> {
	
	ICompoundGraph<N, E> getSuperGraph();
}
