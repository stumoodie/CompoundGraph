package uk.ed.inf.graph.colour;

import uk.ed.inf.graph.basic.IBasicEdge;
import uk.ed.inf.graph.basic.IBasicNode;

public interface INodeColourHandler<
	N extends IBasicNode<N, ? extends IBasicEdge<N, ?>>,
	E extends IBasicEdge<N, E>
> {
	void setColour(Object colour);
	
	Object getColour();
	
	INodeColourHandler<N, E> createCopy();
	
	
	Object copyColour(N newNode);
	
	void setNode(N node);
	
	N getNode();
}
