package uk.ed.inf.graph.compound.impl;

import uk.ed.inf.graph.compound.base.BaseChildCompoundEdgeFactory;
import uk.ed.inf.graph.compound.base.BaseChildCompoundGraph;
import uk.ed.inf.graph.compound.base.BaseCompoundEdge;
import uk.ed.inf.graph.compound.base.BaseCompoundNode;
import uk.ed.inf.graph.compound.base.BaseCompoundNodeFactory;
import uk.ed.inf.graph.compound.base.BaseGraphCopyBuilder;


public class CompoundGraphCopyBuilder extends BaseGraphCopyBuilder {
	CompoundGraphCopyBuilder(){
		super();
	}

	@Override
	protected BaseCompoundNode createCopyOfNode(BaseCompoundNode srcNode, BaseCompoundNode destParentNode) {
		BaseCompoundNodeFactory edgefact = destParentNode.getChildCompoundGraph().nodeFactory();
		return edgefact.createNode();
	}

	@Override
	protected BaseCompoundEdge createCopyOfEdge(BaseCompoundEdge srcEdge, BaseChildCompoundGraph edgeOwner,
			BaseCompoundNode outNode, BaseCompoundNode inNode) {
		BaseChildCompoundEdgeFactory edgefact = edgeOwner.edgeFactory();
		edgefact.setPair(outNode, inNode);
		return edgefact.createEdge();
	}

    @Override
    protected void additionalCopyTasks() {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void additionalInitialisation() {
        // TODO Auto-generated method stub
        
    }
}
