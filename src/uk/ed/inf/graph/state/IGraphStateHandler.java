package uk.ed.inf.graph.state;

import uk.ed.inf.graph.basic.IBasicEdge;
import uk.ed.inf.graph.basic.IBasicGraph;
import uk.ed.inf.graph.basic.IBasicNode;

public interface IGraphStateHandler<N extends IRestorableGraphElement & IBasicNode<N, ? extends IBasicEdge<N, ?>>, E extends IRestorableGraphElement & IBasicEdge<N, E>> {

	IBasicGraph<N, E> getGraph();

	IGraphState<N, E> createGraphState();

	void restoreState(IGraphState<N, E> previousState);

}