package uk.ed.inf.graph.compound.impl;

import uk.ed.inf.graph.compound.archetypal.ArchetypalChildCompoundGraph;
import uk.ed.inf.graph.compound.archetypal.ArchetypalGraphCopyBuilder;
import uk.ed.inf.graph.compound.archetypal.ArchetypalCompoundEdge;
import uk.ed.inf.graph.compound.archetypal.ArchetypalCompoundNode;


public class CompoundGraphCopyBuilder extends ArchetypalGraphCopyBuilder {
	
	public CompoundGraphCopyBuilder(){
		super();
	}

	@Override
	protected CompoundNode createCopyOfNode(ArchetypalCompoundNode srcNode, ArchetypalCompoundNode destParentNode, int newIndex) {
		CompoundNode retVal = new CompoundNode((CompoundNode)destParentNode, newIndex);
//		ChildCompoundGraph destParentCompoundNode = (ChildCompoundGraph)destParentNode.getChildCigraph(); 
//		CompoundNodeFactory fact = destParentCompoundNode.nodeFactory();
//		CompoundNode retVal = fact.createNode();
		return retVal;
	}

	@Override
	protected ArchetypalCompoundEdge createCopyOfEdge(ArchetypalCompoundEdge srcEdge, ArchetypalChildCompoundGraph edgeOwner,
			int newEdgeIndex, ArchetypalCompoundNode outNode, ArchetypalCompoundNode inNode) {
		return new CompoundEdge((ChildCompoundGraph)edgeOwner, newEdgeIndex, (CompoundNode)outNode, (CompoundNode)inNode);
	}

}
