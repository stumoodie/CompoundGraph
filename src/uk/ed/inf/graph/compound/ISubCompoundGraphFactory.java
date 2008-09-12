package uk.ed.inf.graph.compound;

import uk.ed.inf.graph.basic.IBasicSubgraphFactory;

public interface ISubCompoundGraphFactory<
		N extends ICompoundNode<N, ? extends ICompoundEdge<N, ?>>,
		E extends ICompoundEdge<N, E>
> extends IBasicSubgraphFactory<N, E> {

	ICompoundGraph<N, E> getGraph();

	ISubCompoundGraph<N, E> createSubgraph();
	
	ISubCompoundGraph<N, E> createInducedSubgraph();

}
