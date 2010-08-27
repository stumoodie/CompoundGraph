package uk.ac.ed.inf.graph.compound.testfixture;

import uk.ac.ed.inf.graph.compound.IElementAttribute;
import uk.ac.ed.inf.graph.compound.IElementAttributeFactory;

public class ElementAttributeFactory implements	IElementAttributeFactory {
	private String name;
	private IElementAttribute destinationAttribute;
	
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

	@Override
	public void setDestinationAttribute(IElementAttribute attribute) {
		this.destinationAttribute = attribute;
	}

	@Override
	public IElementAttribute getDestinationAttribute() {
		return this.destinationAttribute;
	}
}
