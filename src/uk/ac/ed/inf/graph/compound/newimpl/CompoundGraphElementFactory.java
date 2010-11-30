package uk.ac.ed.inf.graph.compound.newimpl;

import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElementFactory;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.IElementAttribute;

public class CompoundGraphElementFactory implements ICompoundGraphElementFactory {
	private int index;
	private ICompoundGraphElement parent;
	private IElementAttribute attribute;
	
	public CompoundGraphElementFactory(){
	}
	
	@Override
	public void setParent(ICompoundGraphElement parent) {
		this.parent = parent;
	}

	@Override
	public void setIndex(int newIndex) {
		this.index = newIndex;
	}

	@Override
	public void setAttribute(IElementAttribute newAttribute) {
		this.attribute = newAttribute;
	}

	@Override
	public ICompoundEdge createEdge(ICompoundNode outNode, ICompoundNode inNode) {
		return new CompoundEdge(this.parent, this.index, this.attribute, outNode, inNode);
	}

	@Override
	public ICompoundNode createNode() {
		return new CompoundNode(this.parent, this.index, this.attribute);
	}

}
