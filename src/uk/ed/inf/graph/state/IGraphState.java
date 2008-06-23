package uk.ed.inf.graph.state;

import uk.ed.inf.bitstring.IBitString;
import uk.ed.inf.graph.basic.IBasicEdge;
import uk.ed.inf.graph.basic.IBasicGraph;
import uk.ed.inf.graph.basic.IBasicNode;


public interface IGraphState<
		N extends IBasicNode<N, ? extends IBasicEdge<N, ?>>,
		E extends IBasicEdge<N, E>
> {

	/**
	 * Get the graph that this momento belong to.
	 * @return The graph, cannot be null.
	 */
	IBasicGraph<N, E> getGraph();

	/**
	 * Get the states of the nodes as a bit string: [true => exists, false => removed]. Not that the
	 * bit string may be shorter than the number of nodes in the graph as the graph may have expanded
	 * since it was created. 
	 * @return The bit string representing the state of the nodes in graph.
	 */
	IBitString getNodeStates();
	
	/**
	 * Get the states of the edges as a bit string: [true => exists, false => removed]. Not that the
	 * bit string may be shorter than the number of edges in the graph as the graph may have expanded
	 * since it was created. 
	 * @return The bit string representing the state of the edges in the graph.
	 */
	IBitString getEdgeStates();
}
