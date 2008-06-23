package uk.ed.inf.graph.colour;

public interface IColouredComponent {
	
	<C> void setColour(IComponentColour<C> colour);
	
	<C> IComponentColour<C> getColour();
	
}
