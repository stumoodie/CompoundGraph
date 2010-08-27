package uk.ac.ed.inf.graph.compound;

public interface IElementAttributeCopyFactory extends IElementAttributeFactory {

	void setElementToCopy(IElementAttribute attributeToCopy);
	
	IElementAttribute getElementToCopy();
}
