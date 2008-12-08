package uk.ed.inf.graph.compound.base;

import uk.ed.inf.graph.compound.ICompoundEdgeFactory;

public abstract class BaseCompoundEdgeFactory implements ICompoundEdgeFactory<BaseCompoundNode, BaseCompoundEdge> {
	
	public abstract void setPair(BaseCompoundNode outNode, BaseCompoundNode inNode);

	protected abstract BaseCompoundNode getOutNode();
	
	protected abstract BaseCompoundNode getInNode(); 
		
	public abstract BaseCompoundNodePair getCurrentNodePair();
	
	public BaseChildCompoundGraph getOwningChildGraph(){
		if(this.getCurrentNodePair() == null) throw new IllegalArgumentException("No node pair has been set");
		BaseCompoundNode lcmNode = this.getGraph().getLcaNode(this.getOutNode(), this.getInNode());
		return lcmNode.getChildCompoundGraph();
	}

	
	public BaseCompoundEdge createEdge() {
		if(this.getOutNode() == null || this.getInNode() == null) throw new IllegalStateException("One or more nodes not set");
		BaseCompoundNode lcmNode = this.getGraph().getLcaNode(this.getOutNode(), this.getInNode());
		int cntr = this.getGraph().getEdgeCounter().nextIndex();
		BaseCompoundEdge newEdge = newEdge(lcmNode.getChildCompoundGraph(), cntr, this.getOutNode(), this.getInNode()); 
//		lcmNode.getChildCompoundGraph().addNewEdge(newEdge);
		return newEdge;
	}

	protected abstract BaseCompoundEdge newEdge(BaseChildCompoundGraph owningGraph, int nodeIndex,
			BaseCompoundNode outNode,	BaseCompoundNode inNode);

	public abstract BaseCompoundGraph getGraph();
	
	public abstract boolean canCreateEdge();
}
