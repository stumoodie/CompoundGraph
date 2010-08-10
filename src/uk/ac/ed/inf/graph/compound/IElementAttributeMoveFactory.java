package uk.ac.ed.inf.graph.compound;

public interface IElementAttributeMoveFactory extends IElementAttributeFactory {

	void setElementToMove(IElementAttribute attributeToMove);
	
	IElementAttribute getElementToMove();

	void setDestinationAttribute(IElementAttribute attribute);

	IElementAttribute getDestinationAttribute();
	
}