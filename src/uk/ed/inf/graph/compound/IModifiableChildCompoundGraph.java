package uk.ed.inf.graph.compound;

import uk.ed.inf.graph.basic.IBasicEdgeFactory;
import uk.ed.inf.graph.basic.IBasicNodeFactory;
import uk.ed.inf.graph.basic.IBasicSubgraph;

public interface IModifiableChildCompoundGraph <
		N extends ICompoundNode<N, ? extends ICompoundEdge<N, E>>,
		E extends ICompoundEdge<N, E>
> {


	/**
	 * Test whether the implementation of this graph can create new nodes that are added to the graph.
	 * @return true if it can, false otherwise.
	 */
	boolean canCreateNodes();
	
	/**
	 * Gets the nodeFactory for this class. This factory is a singleton so this method must always
	 *  return the same instance of the factory. The factory is the only way that new nodes can be added to
	 *  this graph.
	 * @return The node factory.
	 * @throws UnsupportedOperationException if this graph implementation does support new node creation and so a factory
	 *  is not provided, i.e. <code>canCreateNodes() == false</code>.
	 */
	IBasicNodeFactory<N, E> nodeFactory();
	
	/**
	 * Test whether the implementation of this graph can create new edges that are added to the graph.
	 * @return true if it can, false otherwise.
	 */
	boolean canCreateEdges();
	
	/**
	 * Gets the edgeFactory for this class. This factory is a singleton so this method must always
	 *  return the same instance of the factory. The factory is the only way that new edges can be added to
	 *  this graph.
	 * @return The edge factory.
	 * @throws UnsupportedOperationException if this graph implementation does support new edge creation and so a factory
	 *  is not provided, i.e. <code>canCreateEdges() == false</code>.
	 */
	IBasicEdgeFactory<N, E> edgeFactory();
	
	/**
	 * Tests whether the subGraph can be copied to this graph. To be true the subgraph must be an induced subgraph
	 *  that is a consistent of the super graph. It must also be not null.
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
