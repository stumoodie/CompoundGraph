package uk.ed.inf.graph.compound;

import uk.ed.inf.graph.basic.IBasicGraph;
import uk.ed.inf.graph.basic.IBasicSubgraph;


public interface IChildCompoundGraph<
		N extends ICompoundNode<N, ? extends ICompoundEdge<N, E>>,
		E extends ICompoundEdge<N, E>
> extends ICompoundGraph<N, E> {
	
	IBasicGraph<N, E> getSuperGraph();
	
	boolean canCopyHere(IBasicSubgraph<N, E> subGraph);

	void copyHere(IBasicSubgraph<N, E> subGraph);
}
