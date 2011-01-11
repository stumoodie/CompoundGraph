package uk.ac.ed.inf.graph.compound.testfixture;

import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.IElementAttribute;
import uk.ac.ed.inf.graph.compound.IElementAttributeFactory;

public class ElementAttribute implements IElementAttribute {
	private final String name;
	private ICompoundGraphElement currentElement;
	private boolean canCreateFlag = true;
	
	public ElementAttribute(String name){
		this.name = name;
	}
	
	public ElementAttribute(ElementAttribute otherEl){
		this.name = otherEl.getName();
	}
	
	public String getName(){
		return this.name;
	}
	
	@Override
	public String toString(){
		StringBuilder buf = new StringBuilder(this.getClass().getSimpleName());
		buf.append("(");
		buf.append(this.name);
		buf.append(")");
		return buf.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ElementAttribute other = (ElementAttribute) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public ICompoundGraphElement getCurrentElement() {
		return this.currentElement;
	}

	@Override
	public void setCurrentElement(ICompoundGraphElement newOwner) {
		this.currentElement = newOwner;
	}

	@Override
	public IElementAttributeFactory elementAttributeCopyFactory() {
		ElementAttributeCopyFactory retVal = new ElementAttributeCopyFactory(this);
		retVal.setCanCreateFlag(canCreateFlag);
		return retVal;
	}

	@Override
	public IElementAttributeFactory elementAttributeMoveFactory() {
		ElementAttributeMoveFactory retVal = new ElementAttributeMoveFactory(this);
		retVal.setCanCreateFlag(canCreateFlag);
		return retVal;
	}

	public void setCanCreateFlag(boolean canCreate) {
		this.canCreateFlag = canCreate;
	}
}
