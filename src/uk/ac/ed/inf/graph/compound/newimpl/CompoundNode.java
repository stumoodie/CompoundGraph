package uk.ac.ed.inf.graph.compound.newimpl;

import uk.ac.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;

public class CompoundNode extends CommonCompoundNode {
	private final ICompoundGraphElement parentElement;
	private final int level;
	private final IChildCompoundGraph childGraph;
	
	public CompoundNode(ICompoundGraphElement parentElement, int index){
		super(index);
		this.parentElement = parentElement;
		this.level = calcTreeLevel();
		this.childGraph = new ChildCompoundGraph(this);
	}
	
	@Override
	public IChildCompoundGraph getChildCompoundGraph() {
		return this.childGraph;
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
	public int getLevel() {
		return this.level;
	}

	@Override
	public ICompoundGraphElement getParent() {
		return this.parentElement;
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
	public ICompoundGraph getGraph() {
		return this.parentElement.getGraph();
	}

	@Override
	public ICompoundGraphElement getRoot() {
		return this.parentElement.getRoot();
	}
	
	@Override
	public String toString(){
		StringBuilder buf = new StringBuilder(this.getClass().getSimpleName());
		buf.append("(");
		buf.append("index=");
		buf.append(this.getIndex());
		buf.append(",parent=");
		buf.append(this.getParent().getIndex());
		buf.append(")");
		return buf.toString();
	}

}
