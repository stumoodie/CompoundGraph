package uk.ed.inf.graph.colour;

import uk.ed.inf.graph.basic.IBasicEdge;
import uk.ed.inf.graph.basic.IBasicNode;

public interface IEdgeColourHandler<
	N extends IBasicNode<N, ? extends IBasicEdge<N, ?>>,
	E extends IBasicEdge<N, E>
> {
	
	void setColour(Object colour);
	
	Object getColour();
	
	IEdgeColourHandler<N, E> createCopy();
	
	Object copyColour(E newEdge);
	
	E getEdge();
	
	void setEdge(E edge);
}
