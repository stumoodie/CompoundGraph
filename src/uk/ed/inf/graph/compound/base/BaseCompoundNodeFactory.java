package uk.ed.inf.graph.compound.base;

import uk.ed.inf.graph.compound.ICompoundNodeFactory;

public abstract class BaseCompoundNodeFactory implements ICompoundNodeFactory<BaseCompoundNode, BaseCompoundEdge> {
	
	public BaseCompoundNodeFactory(){
	}
	
	public BaseCompoundNode createNode() {
		int cntr = this.getGraph().getNodeCounter().nextIndex();
		BaseCompoundNode retVal = newNode(this.getParentNode(), cntr);
//		this.getParentNode().getChildCompoundGraph().addNewNode(retVal);
		return retVal;
	}
	
	/**
	 * Creates the new node. The node must be correctly created and owned by the 
	 * ChildCompoundGraph of the parent node.
	 * @param parent the parent node the child graph of which this node will be added. 
	 * @param nodeIndex the new index to use when creating the node.
	 * @return the newly created node which must be valid and correctly formed.
	 */
	protected abstract BaseCompoundNode newNode(BaseCompoundNode parent, int nodeIndex);

	public abstract BaseCompoundGraph getGraph();
	
	public abstract BaseCompoundNode getParentNode();
}
