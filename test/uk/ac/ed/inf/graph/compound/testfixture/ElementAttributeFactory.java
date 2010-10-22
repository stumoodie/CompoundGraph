package uk.ac.ed.inf.graph.compound.testfixture;

import uk.ac.ed.inf.graph.compound.IElementAttribute;
import uk.ac.ed.inf.graph.compound.IElementAttributeFactory;

public class ElementAttributeFactory implements	IElementAttributeFactory {
	private String name;
	private IElementAttribute destinationAttribute;
	private IElementAttribute outAttribute;
	private IElementAttribute inAttribute;
	
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


	@Override
	public void setOutAttribute(IElementAttribute attribute) {
		this.outAttribute = attribute;
	}

	@Override
	public IElementAttribute getOutAttribute() {
		return this.outAttribute;
	}

	@Override
	public void setInAttribute(IElementAttribute attribute) {
		this.inAttribute = attribute;
	}

	@Override
	public IElementAttribute getInAttribute() {
		return this.inAttribute;
	}

}
