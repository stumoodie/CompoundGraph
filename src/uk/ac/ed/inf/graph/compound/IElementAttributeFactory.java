package uk.ac.ed.inf.graph.compound;

public interface IElementAttributeFactory {

	boolean canCreateAttribute();
	
	IElementAttribute createAttribute();

}
