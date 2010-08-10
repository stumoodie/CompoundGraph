package uk.ac.ed.inf.graph.compound.testfixture;

import uk.ac.ed.inf.graph.compound.IElementAttribute;
import uk.ac.ed.inf.graph.compound.IElementAttributeFactory;

public class ElementAttributeFactory implements	IElementAttributeFactory {
	private String name;
	
	public ElementAttributeFactory(){
		name = null;
	}
	
	@Override
	public boolean canCreateAttribute() {
		return this.name != null;
	}

	@Override
	public IElementAttribute createAttribute() {
		return new ElementAttribute(name);
	}

	public void setName(String name) {
		this.name = name;
	}
}
