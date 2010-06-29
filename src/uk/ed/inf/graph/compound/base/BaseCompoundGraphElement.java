package uk.ed.inf.graph.compound.base;

import java.util.Iterator;

import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.tree.AncestorTreeIterator;
import uk.ed.inf.tree.LevelOrderTreeIterator;
import uk.ed.inf.tree.PreOrderTreeIterator;

public abstract class BaseCompoundGraphElement implements ICompoundGraphElement {
	public static final int ROOT_LEVEL = 0;
	private final BaseCompoundGraphElement parent;
	private final BaseChildCompoundGraph childCompoundGraph;
	private final int treeLevel;

	protected BaseCompoundGraphElement(BaseCompoundGraphElement localParent){
		if(localParent == null){
			this.parent = this;
		}
		else{
			this.parent = localParent;
		}
		this.childCompoundGraph = createChildCompoundGraph(this.parent);
		this.treeLevel = calcTreeLevel();
	}
	
	protected int calcTreeLevel(){
		BaseCompoundGraphElement p = this;
		int level = ROOT_LEVEL;
		while(p != p.getParent()){
			p = p.getParent();
			level++;
		}
		return level;
	}

	@Override
	public int getLevel(){
		return this.treeLevel;
	}
	
	@Override
	public BaseChildCompoundGraph getChildCompoundGraph(){
		return this.childGraph;
	}

	protected abstract void createChildCompoundGraph(BaseCompoundGraphElement rootNode);
	
	public abstract BaseCompoundGraph getGraph();
	
	/**
	 * The parent node cannot be null and should be the root node if the current node is the root
	 * node. This follows the standard conversion for tree data structures.
	 */
	@Override
	public BaseCompoundGraphElement getParent(){
		return this.parent;
	}
	
	public final Iterator<ICompoundGraphElement> childIterator() {
		return this.getChildCompoundGraph().elementIterator();
	}

	@Override
	public boolean isAncestor(ICompoundGraphElement testNode) {
	    boolean retVal = false;
	    if(testNode != null) {
	        retVal = this.getGraph().getNodeTree().isAncestor(this, testNode);
	    }
	    return retVal;
	}
	
	@Override
	public boolean isDescendent(ICompoundGraphElement testNode) {
        boolean retVal = false;
        if(testNode != null) {
            retVal = this.getGraph().getNodeTree().isDescendant(this, testNode);
        }
        return retVal;
	}
	
	@Override
	public final boolean isChild(ICompoundGraphElement childNode) {
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
	public final boolean isParent(ICompoundGraphElement parentNode) {
		boolean retVal = false;
		if(parentNode != null){
			retVal = this.getParent().equals(parentNode);
		}
		return retVal;
	}

	@Override
	public final Iterator<ICompoundGraphElement> ancestorIterator() {
		return new AncestorTreeIterator<ICompoundGraphElement>(this);
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
	public int compareTo(ICompoundGraphElement o) {
		return Integer.valueOf(this.getIndex()).compareTo(o.getIndex());
	}

}
