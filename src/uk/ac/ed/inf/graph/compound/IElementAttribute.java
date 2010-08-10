package uk.ac.ed.inf.graph.compound;

public interface IElementAttribute {

	boolean isValidChild(IElementAttribute potentialChild);

	ICompoundGraphElement getCurrentElement();
	
	void setCurrentElement(ICompoundGraphElement newOwner);
	
}
