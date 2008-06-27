package uk.ed.inf.graph.colour;

import uk.ed.inf.graph.basic.IBasicEdge;
import uk.ed.inf.graph.basic.IBasicGraph;
import uk.ed.inf.graph.basic.IBasicNode;


public interface IColouredGraph<
	N extends IBasicNode<N, ? extends IBasicEdge<N, ?>>,
	E extends IBasicEdge<N, E>
> extends IBasicGraph<N, E> {
	
	IColouredEdgeFactory<N, E> edgeFactory();

	IColouredNodeFactory<N, E> nodeFactory();
}
