package uk.ac.ed.inf.graph.compound.testfixture;

import uk.ac.ed.inf.graph.compound.IElementAttribute;
import uk.ac.ed.inf.graph.compound.IElementAttributeFactory;

public class ElementAttributeMoveFactory implements	IElementAttributeFactory {
	private final IElementAttribute attribToMove;
	private IElementAttribute destinationAttribute;
	private boolean canCreateFlag = true;
	
	
	public ElementAttributeMoveFactory(IElementAttribute attribToMove){
		this.attribToMove = attribToMove;
	}
	
	@Override
	public boolean canCreateAttribute() {
		return this.attribToMove != null && destinationAttribute != null && canCreateFlag;
	}

	@Override
	public IElementAttribute createAttribute() {
		return this.attribToMove;
	}

	public IElementAttribute getElementToMove() {
		return this.attribToMove;
	}

	@Override
	public void setDestinationAttribute(IElementAttribute attribute) {
		this.destinationAttribute = attribute;
	}

	@Override
	public IElementAttribute getDestinationAttribute() {
		return destinationAttribute;
	}


	public boolean canCreate() {
		return canCreateFlag;
	}

	public void setCanCreateFlag(boolean canCreateFlag) {
		this.canCreateFlag = canCreateFlag;
	}

}
