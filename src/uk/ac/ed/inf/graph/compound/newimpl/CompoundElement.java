package uk.ac.ed.inf.graph.compound.newimpl;

import java.util.Iterator;

import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.IElementAttribute;
import uk.ac.ed.inf.tree.AncestorTreeIterator;
import uk.ac.ed.inf.tree.LevelOrderTreeIterator;
import uk.ac.ed.inf.tree.PreOrderTreeIterator;

public abstract class CompoundElement implements ICompoundGraphElement {
	private final int index;
	private boolean removed;
	private final IElementAttribute attribute;

	protected CompoundElement(int index, IElementAttribute attribute){
		this.index = index;
		this.removed = false;
		this.attribute = attribute;
		this.attribute.setCurrentElement(this);
	}
	
	@Override
	public final void markRemoved(boolean setRemoved) {
		this.removed = setRemoved;
		if(!setRemoved){
			this.getAttribute().setCurrentElement(this);
		}
	}

	@Override
	public final boolean isRemoved() {
		return this.removed;
	}

	@Override
	public final int getIndex() {
		return this.index;
	}

	@Override
	public final int compareTo(ICompoundGraphElement o) {
		int otherIdx = o.getIndex();
		return this.getIndex() < otherIdx ? -1 : (this.getIndex() > otherIdx ? 1 : 0);
	}


	@Override
	public final boolean isAncestor(ICompoundGraphElement testNode) {
	    boolean retVal = false;
	    if(testNode != null) {
	        retVal = this.getGraph().getElementTree().isAncestor(this, testNode);
	    }
	    return retVal;
	}

	@Override
	public final boolean isDescendent(ICompoundGraphElement testNode) {
        boolean retVal = false;
        if(testNode != null) {
            retVal = this.getGraph().getElementTree().isDescendant(this, testNode);
        }
        return retVal;
	}

	@Override
	public final Iterator<ICompoundGraphElement> ancestorIterator() {
		return new AncestorTreeIterator<ICompoundGraphElement>(this);
	}

	@Override
	public final Iterator<ICompoundGraphElement> childIterator() {
		return this.getChildCompoundGraph().elementIterator();
	}

	@Override
	public final boolean isChild(ICompoundGraphElement childElement) {
		boolean retVal = false;
		if(childElement != null){
			Iterator<ICompoundGraphElement> childIter = this.getChildCompoundGraph().elementIterator();
			while(childIter.hasNext() && !retVal){
				ICompoundGraphElement possChild = childIter.next();
				if(possChild.equals(childElement)){
					retVal = true;
				}
			}
		}
		return retVal;
	}

	@Override
	public final boolean isLowestCommonAncestor(ICompoundGraphElement thisNode, ICompoundGraphElement thatNode){
		return thisNode != null && thatNode != null && thisNode.getGraph().equals(this.getGraph()) && thatNode.getGraph().equals(this.getGraph())
			&& this.getRoot().getGraph().getElementTree().getLowestCommonAncestor(thisNode, thatNode).equals(this);
	}
	
	@Override
	public final Iterator<ICompoundGraphElement> levelOrderIterator() {
		return new LevelOrderTreeIterator<ICompoundGraphElement>(this);
	}

	@Override
	public final Iterator<ICompoundGraphElement> preOrderIterator() {
		return new PreOrderTreeIterator<ICompoundGraphElement>(this);
	}

	@Override
	public final IElementAttribute getAttribute() {
		return this.attribute;
	}

}
