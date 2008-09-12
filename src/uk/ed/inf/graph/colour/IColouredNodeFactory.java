package uk.ed.inf.graph.colour;

import uk.ed.inf.graph.basic.IBasicEdge;
import uk.ed.inf.graph.basic.IBasicNode;

public interface IColouredNodeFactory<
		N extends IBasicNode<N, ? extends IBasicEdge<N, ?>>,
		E extends IBasicEdge<N, E>
> {

	/**
	 * Get the graph to which this factory acts upon.
	 * @return The owning graph, cannot be null.
	 */
	IColouredGraph<N, E> getGraph();

	void setColourHandlerFactory(INodeColourHandlerFactory<? extends N, ? extends E> handlerFactory);
	
	N createNode();
	
}
