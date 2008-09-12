package uk.ed.inf.graph.compound;

import uk.ed.inf.graph.basic.IBasicNodeFactory;
import uk.ed.inf.graph.basic.IBasicSubgraph;

public interface IModifiableChildCompoundGraph <
		N extends ICompoundNode<N, ? extends ICompoundEdge<N, ?>>,
		E extends ICompoundEdge<N, E>
> {


	/**
	 * Gets the nodeFactory for this class. This factory is a singleton so this method must always
	 *  return the same instance of the factory. The factory is the only way that new nodes can be added to
	 *  this graph.
	 * @return The node factory.
	 */
	IBasicNodeFactory<N, E> nodeFactory();
	
	/**
	 * Gets the edgeFactory for this class. This factory is a singleton so this method must always
	 *  return the same instance of the factory. The factory is the only way that new edges can be added to
	 *  this graph.
	 * @return The edge factory.
	 */
	ICompoundChildEdgeFactory<N, E> edgeFactory();
	
	/**
	 * Tests whether the subGraph can be copied to this graph. To be true the subgraph must be an induced subgraph
	 *  that is a consistent of the super graph. It must also be not null.
	 * @param subGraph the subgraph to test, can be null. 
	 * @return true if the subgraph is valid to copy from, false otherwise.
	 */
	boolean canCopyHere(IBasicSubgraph<? extends N, ? extends E> subGraph);

	/**
	 * Copies a subgraph into this graph. Note that the subgraph can be from a different graph or subgraph
	 * of this graph, since the structure of the graph is copied not the nodes and edges instances
	 * themselves. Note that the subgraph must be valid to be copied.
	 * @param subGraph the subgraph to copy
	 */
	void copyHere(IBasicSubgraph<? extends N, ? extends E> subGraph);
}
