package uk.ed.inf.graph.colour;

import uk.ed.inf.graph.basic.IBasicEdge;
import uk.ed.inf.graph.basic.IBasicNode;

public interface IColouredEdge<
N extends IBasicNode<N, ? extends IBasicEdge<N, ?>>,
E extends IBasicEdge<N, E>
> {

	IEdgeColourHandler<N, E> getColourHandler();

}