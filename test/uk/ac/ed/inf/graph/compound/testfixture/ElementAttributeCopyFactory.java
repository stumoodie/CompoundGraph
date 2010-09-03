package uk.ac.ed.inf.graph.compound.testfixture;

import uk.ac.ed.inf.graph.compound.IElementAttribute;
import uk.ac.ed.inf.graph.compound.IElementAttributeFactory;

public class ElementAttributeCopyFactory implements	IElementAttributeFactory {
	private final IElementAttribute attribToCopy;
	private IElementAttribute destinationAttribute;
	private boolean canCreateFlag = true;
	
	public ElementAttributeCopyFactory(ElementAttribute attrbToCopy){
		this.attribToCopy = attrbToCopy;
	}
	
	@Override
	public boolean canCreateAttribute() {
		return this.attribToCopy != null && destinationAttribute != null && canCreateFlag;
	}

	@Override
	public IElementAttribute createAttribute() {
		return new ElementAttribute(((ElementAttribute)this.attribToCopy));
	}

	public IElementAttribute getElementToCopy() {
		return this.attribToCopy;
	}

	@Override
	public void setDestinationAttribute(IElementAttribute destinationAttribute) {
		this.destinationAttribute = destinationAttribute;
	}

	@Override
	public IElementAttribute getDestinationAttribute() {
		return this.destinationAttribute;
	}

	public boolean canCreate() {
		return canCreateFlag;
	}

	public void setCanCreateFlag(boolean canCreateFlag) {
		this.canCreateFlag = canCreateFlag;
	}

}
