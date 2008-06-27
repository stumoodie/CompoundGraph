package uk.ed.inf.graph.colour;

import uk.ed.inf.graph.basic.IBasicEdge;
import uk.ed.inf.graph.basic.IBasicEdgeFactory;
import uk.ed.inf.graph.basic.IBasicNode;


public interface IColouredEdgeFactory<
		N extends IBasicNode<N, ? extends IBasicEdge<N, ?>>,
		E extends IBasicEdge<N, E>
> extends IBasicEdgeFactory<N, E> {

	/**
	 * Get the graph to which this factory acts upon.
	 * @return The owning graph, cannot be null.
	 */
	IColouredGraph<N, E> getGraph();
	
	void setColourHandler(IEdgeColourHandler<N, E> colour);
	
	/**
	 * Create a new edge and add it to the graph.
	 * @return
	 */
	E createEdge();
	
}
