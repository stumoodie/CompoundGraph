package uk.ed.inf.graph.compound;

import uk.ed.inf.graph.basic.IBasicGraph;
import uk.ed.inf.graph.basic.IBasicSubgraph;


public interface IChildCompoundGraph<
		N extends ICompoundNode<N, ? extends ICompoundEdge<N, ?>>,
		E extends ICompoundEdge<N, E>
> extends ICompoundGraph<N, E> {
	
	IBasicGraph<N, E> getSuperGraph();

	/**
	 * Tests whether the subGraph can be copied to this child compound graph. To be true the subgraph must be
	 *  an induced subgraph that is a consistent of the super graph. It must also be not null.
	 * @param subGraph the subgraph to test, can be null. 
	 * @return true if the subgraph is valid to copy from, false otherwise.
	 */
	boolean canCopyHere(IBasicSubgraph<N, E> subGraph);

	/**
	 * Copies a subgraph into this graph. Note that the subgraph can be from a different graph or subgraph
	 * of this graph, since the structure of the graph is copied not the nodes and edges instances
	 * themselves. Note that the subgraph must be valid to be copied.
	 * @param subGraph the subgraph to copy
	 */
	void copyHere(IBasicSubgraph<N, E> subGraph);
}
