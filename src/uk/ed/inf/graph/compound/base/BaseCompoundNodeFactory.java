package uk.ed.inf.graph.compound.base;

import uk.ed.inf.graph.compound.ICompoundNodeFactory;

public abstract class BaseCompoundNodeFactory implements ICompoundNodeFactory<BaseCompoundNode, BaseCompoundEdge> {
	private final BaseCompoundNode parent;
	
	public BaseCompoundNodeFactory(BaseCompoundNode parent){
		if(parent == null) throw new IllegalArgumentException("parent cannot be null");
		
		this.parent = parent;
	}
	
	public BaseCompoundNode createNode() {
		int cntr = this.parent.getGraph().getNodeCounter().nextIndex();
		BaseCompoundNode retVal = newNode(this.parent, cntr);
		this.parent.getChildCompoundGraph().addNewNode(retVal);
		return retVal;
	}
	
	protected abstract BaseCompoundNode newNode(BaseCompoundNode parent, int nodeIndex);

	public BaseCompoundGraph getGraph() {
		return parent.getGraph();
	}
	
	public BaseChildCompoundGraph getOwningChildGraph() {
		return this.parent.getChildCompoundGraph();
	}
}
