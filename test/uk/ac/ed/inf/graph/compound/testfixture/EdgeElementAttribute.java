package uk.ac.ed.inf.graph.compound.testfixture;

import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.IElementAttribute;
import uk.ac.ed.inf.graph.compound.IElementAttributeFactory;

public class EdgeElementAttribute implements IElementAttribute {
	private String name;
	private ICompoundGraphElement currentElement;
	
	public EdgeElementAttribute(String name){
		this.name = name;
	}
	
	public EdgeElementAttribute(EdgeElementAttribute attribToCopy) {
		this.name = attribToCopy.getName();
	}

	public String getName(){
		return this.name;
	}
	
	
	@Override
	public ICompoundGraphElement getCurrentElement() {
		return this.currentElement;
	}

	@Override
	public void setCurrentElement(ICompoundGraphElement currentElement) {
		this.currentElement = currentElement;
	}

	@Override
	public IElementAttributeFactory elementAttributeCopyFactory() {
		return new EdgeElementAttributeCopyFactory(this);
	}

	@Override
	public IElementAttributeFactory elementAttributeMoveFactory() {
		return new EdgeElementAttributeMoveFactory(this);
	}

}
