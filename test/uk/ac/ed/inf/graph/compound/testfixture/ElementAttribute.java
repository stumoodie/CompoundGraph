package uk.ac.ed.inf.graph.compound.testfixture;

import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.IElementAttribute;

public class ElementAttribute implements IElementAttribute {
	private final String name;
	private ICompoundGraphElement currentElement;
	private boolean enableChildren;
	
	public ElementAttribute(String name){
		this.name = name;
		this.enableChildren = true;
	}
	
	public ElementAttribute(ElementAttribute otherEl){
		this.name = otherEl.getName();
		this.enableChildren = otherEl.enableChildren;
	}
	
	@Override
	public boolean isValidChild(IElementAttribute potentialChild) {
		return this.enableChildren;
	}

	public String getName(){
		return this.name;
	}
	
	@Override
	public String toString(){
		StringBuilder buf = new StringBuilder(this.getClass().getSimpleName());
		buf.append("(");
		buf.append("rootNode");
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

	public void setEnableChildren(boolean isValidFlag) {
		this.enableChildren = isValidFlag;
	}
}
