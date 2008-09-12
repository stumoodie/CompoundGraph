package uk.ed.inf.graph.compound;

import uk.ed.inf.graph.basic.IBasicNodeFactory;

public interface ICompoundNodeFactory<
		N extends ICompoundNode<N, ? extends ICompoundEdge<N, ?>>,
		E extends ICompoundEdge<N, E>
> extends IBasicNodeFactory<N, E> {

	ICompoundGraph<N, E> getGraph();

	IChildCompoundGraph<N, E> getOwningChildGraph();
	
}
