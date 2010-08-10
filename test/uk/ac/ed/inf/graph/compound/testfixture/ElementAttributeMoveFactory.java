package uk.ac.ed.inf.graph.compound.testfixture;

import uk.ac.ed.inf.graph.compound.IElementAttribute;
import uk.ac.ed.inf.graph.compound.IElementAttributeMoveFactory;

public class ElementAttributeMoveFactory implements	IElementAttributeMoveFactory {
	private IElementAttribute attribToMove;
	private IElementAttribute destinationAttribute;
	private boolean canCreateFlag = true;
	
	@Override
	public boolean canCreateAttribute() {
		return this.attribToMove != null && destinationAttribute != null && canCreateFlag;
	}

	@Override
	public IElementAttribute createAttribute() {
		return this.attribToMove;
	}

	@Override
	public void setElementToMove(IElementAttribute attributeToMove) {
		this.attribToMove = attributeToMove;
	}

	@Override
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
