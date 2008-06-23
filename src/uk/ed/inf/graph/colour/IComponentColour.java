package uk.ed.inf.graph.colour;

public interface IComponentColour<N> {
	void setColour(Object colour);
	
	Object getColour();
	
	N getNode();
}
