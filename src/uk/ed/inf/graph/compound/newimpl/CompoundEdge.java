package uk.ed.inf.graph.compound.newimpl;

import java.util.Iterator;

import uk.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundGraph;
import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.ICompoundNodePair;
import uk.ed.inf.tree.AncestorTreeIterator;
import uk.ed.inf.tree.LevelOrderTreeIterator;
import uk.ed.inf.tree.PreOrderTreeIterator;

public class CompoundEdge implements ICompoundEdge {
	private final int level;
	private final ICompoundGraphElement parentElement;
	private final IChildCompoundGraph childGraph;
	private final ICompoundNodePair nodePair;
	private final int index;
	private boolean removed;

	public CompoundEdge(ICompoundGraphElement parent, int index, ICompoundNode outNode, ICompoundNode inNode){
		this.index = index;
		this.parentElement = parent;
		this.childGraph = new ChildCompoundGraph(this);
		this.nodePair = new CompoundNodePair(outNode, inNode);
		this.level = calcTreeLevel();
		this.removed = false;
	}
	
	
	private int calcTreeLevel(){
		ICompoundGraphElement p = this;
		int level = ROOT_LEVEL;
		while(p != p.getParent()){
			p = p.getParent();
			level++;
		}
		return level;
	}

	@Override
	public ICompoundNodePair getConnectedNodes() {
		return new CompoundNodePair(this.nodePair.getOutNode(), this.nodePair.getInNode());
	}

	@Override
	public int getIndex() {
		return this.index;
	}

	@Override
	public boolean hasDirectedEnds(ICompoundNode outNode, ICompoundNode inNode) {
		return this.hasDirectedEnds(new CompoundNodePair(outNode, inNode));
	}

	@Override
	public boolean hasDirectedEnds(ICompoundNodePair ends) {
		return this.nodePair.equals(ends);
	}

	@Override
	public boolean hasEnds(ICompoundNode thisNode, ICompoundNode thatNode) {
		return this.nodePair.hasEnds(thisNode, thatNode);
	}

	@Override
	public boolean hasEnds(ICompoundNodePair ends) {
		return this.nodePair.equals(ends);
	}

	@Override
	public boolean isSelfEdge() {
		return this.nodePair.isSelfEdge();
	}

	@Override
	public IChildCompoundGraph getChildCompoundGraph() {
		return this.childGraph;
	}

	@Override
	public ICompoundGraph getGraph() {
		return this.parentElement.getGraph();
	}

	@Override
	public boolean isAncestor(ICompoundGraphElement testNode) {
	    boolean retVal = false;
	    if(testNode != null) {
	        retVal = this.getGraph().getElementTree().isAncestor(this, testNode);
	    }
	    return retVal;
	}

	@Override
	public boolean isDescendent(ICompoundGraphElement testNode) {
        boolean retVal = false;
        if(testNode != null) {
            retVal = this.getGraph().getElementTree().isDescendant(this, testNode);
        }
        return retVal;
	}

	@Override
	public boolean isLink() {
		return true;
	}

	@Override
	public boolean isNode() {
		return false;
	}

	@Override
	public boolean isRemoved() {
		return this.removed;
	}

	@Override
	public void markRemoved(boolean setRemoved) {
		this.removed = setRemoved;
	}

	@Override
	public int compareTo(ICompoundGraphElement o) {
		return Integer.valueOf(this.index).compareTo(o.getIndex());
	}

	@Override
	public Iterator<ICompoundGraphElement> ancestorIterator() {
		return new AncestorTreeIterator<ICompoundGraphElement>(this);
	}

	@Override
	public Iterator<ICompoundGraphElement> childIterator() {
		return this.getChildCompoundGraph().elementIterator();
	}

	@Override
	public int getLevel() {
		return this.level;
	}

	@Override
	public ICompoundGraphElement getParent() {
		return this.parentElement;
	}

	@Override
	public ICompoundGraphElement getRoot() {
		return this.parentElement.getRoot();
	}

	@Override
	public boolean isChild(ICompoundGraphElement childNode) {
		boolean retVal = false;
		if(childNode != null){
			Iterator<ICompoundNode> childIter = this.getChildCompoundGraph().nodeIterator();
			while(childIter.hasNext() && retVal == false){
				ICompoundNode possChild = childIter.next();
				if(possChild.equals(childNode)){
					retVal = true;
				}
			}
		}
		return retVal;
	}

	@Override
	public boolean isParent(ICompoundGraphElement parentNode) {
		boolean retVal = false;
		if(parentNode != null){
			retVal = this.getParent().equals(parentNode);
		}
		return retVal;
	}

	@Override
	public Iterator<ICompoundGraphElement> levelOrderIterator() {
		return new LevelOrderTreeIterator<ICompoundGraphElement>(this);
	}

	@Override
	public Iterator<ICompoundGraphElement> preOrderIterator() {
		return new PreOrderTreeIterator<ICompoundGraphElement>(this);
	}

}
