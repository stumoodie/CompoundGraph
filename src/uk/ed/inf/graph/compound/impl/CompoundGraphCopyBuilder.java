package uk.ed.inf.graph.compound.impl;

import uk.ed.inf.graph.compound.archetypal.ArchetypalCompoundEdge;
import uk.ed.inf.graph.compound.base.BaseChildCompoundGraph;
import uk.ed.inf.graph.compound.base.BaseCompoundEdge;
import uk.ed.inf.graph.compound.base.BaseCompoundNode;
import uk.ed.inf.graph.compound.base.BaseGraphCopyBuilder;


public class CompoundGraphCopyBuilder extends BaseGraphCopyBuilder {
	
	public CompoundGraphCopyBuilder(){
		super();
	}

	@Override
	protected CompoundNode createCopyOfNode(BaseCompoundNode srcNode, BaseCompoundNode destParentNode, int newIndex) {
		CompoundNode retVal = new CompoundNode((CompoundNode)destParentNode, newIndex);
//		ChildCompoundGraph destParentCompoundNode = (ChildCompoundGraph)destParentNode.getChildCigraph(); 
//		CompoundNodeFactory fact = destParentCompoundNode.nodeFactory();
//		CompoundNode retVal = fact.createNode();
		return retVal;
	}

	@Override
	protected ArchetypalCompoundEdge createCopyOfEdge(BaseCompoundEdge srcEdge, BaseChildCompoundGraph edgeOwner,
			int newEdgeIndex, BaseCompoundNode outNode, BaseCompoundNode inNode) {
		return new CompoundEdge((ChildCompoundGraph)edgeOwner, newEdgeIndex, (CompoundNode)outNode, (CompoundNode)inNode);
	}

}
