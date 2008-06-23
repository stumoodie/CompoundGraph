package uk.ed.inf.graph.state;

import uk.ed.inf.graph.basic.IBasicEdge;
import uk.ed.inf.graph.basic.IBasicNode;

public interface IRestorableGraph<
		N extends IBasicNode<N, ? extends IBasicEdge<N, E>>,
		E extends IBasicEdge<N, E>
> {
	
	/**
	 * Get the current state of the graph as a momento.
	 * @return the current graph state, will not be null. 
	 */
	IGraphState<N, E> getCurrentState();
	
	/**
	 * Restores the graph state to the state stored by the <code>previousState</code>
	 * object.
	 * @param previousState The previous state to be restored.
	 */
	void restoreState(IGraphState<N, E> previousState);
}
