package uk.ac.ed.inf.graph.compound;

public interface IElementAttributeCopyFactory extends IElementAttributeFactory {

	void setDestinationAttribute(IElementAttribute destinationAttribute);
	
	IElementAttribute getDestinationAttribute();
	
	void setElementToCopy(IElementAttribute attributeToCopy);
	
	IElementAttribute getElementToCopy();
}
