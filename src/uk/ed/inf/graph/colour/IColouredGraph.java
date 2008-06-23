package uk.ed.inf.graph.colour;


public interface IColouredGraph<C> {
	
	void setColourFactory(IColourFactory<C> factory);
	
	IColourFactory<C> getColourFactory();

}
