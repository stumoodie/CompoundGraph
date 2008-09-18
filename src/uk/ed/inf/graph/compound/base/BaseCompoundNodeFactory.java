package uk.ed.inf.graph.compound.base;

import uk.ed.inf.graph.compound.ICompoundNodeFactory;

public abstract class BaseCompoundNodeFactory implements ICompoundNodeFactory<BaseCompoundNode, BaseCompoundEdge> {
	
	public BaseCompoundNodeFactory(){
	}
	
	public BaseCompoundNode createNode() {
		int cntr = this.getGraph().getNodeCounter().nextIndex();
		BaseCompoundNode retVal = newNode(this.getParentNode(), cntr);
		this.getParentNode().getChildCompoundGraph().addNewNode(retVal);
		return retVal;
	}
	
	protected abstract BaseCompoundNode newNode(BaseCompoundNode parent, int nodeIndex);

	public abstract BaseCompoundGraph getGraph();
	
	public abstract BaseCompoundNode getParentNode();
}
