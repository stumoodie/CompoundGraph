package uk.ed.inf.graph.compound.impl;

import uk.ed.inf.graph.compound.archetypal.ArchetypalCompoundEdge;
import uk.ed.inf.graph.compound.archetypal.ArchetypalCompoundNode;
import uk.ed.inf.graph.compound.base.BaseChildCompoundEdgeFactory;
import uk.ed.inf.graph.compound.base.BaseChildCompoundGraph;
import uk.ed.inf.graph.compound.base.BaseCompoundNode;


public class ChildCompoundEdgeFactory extends BaseChildCompoundEdgeFactory {
	
	public ChildCompoundEdgeFactory(ArchetypalCompoundNode parentNode){
		super(parentNode);
	}

	@Override
	protected ArchetypalCompoundEdge newEdge(
			BaseChildCompoundGraph owningChildGraph, int edgeIndex,
			BaseCompoundNode outNode, BaseCompoundNode inNode) {
		return new CompoundEdge((ChildCompoundGraph)owningChildGraph, edgeIndex,
				(CompoundNode)outNode, (CompoundNode)inNode);
	}
	
	@Override
	public CompoundEdge createEdge(){
		return (CompoundEdge)super.createEdge();
	}
}
