package uk.ed.inf.graph.compound.impl;

import uk.ed.inf.graph.compound.archetypal.ArchetypalCompoundEdge;
import uk.ed.inf.graph.compound.base.BaseChildCompoundGraph;
import uk.ed.inf.graph.compound.base.BaseCompoundEdgeFactory;
import uk.ed.inf.graph.compound.base.BaseCompoundNode;


public class CompoundEdgeFactory extends BaseCompoundEdgeFactory {
	public CompoundEdgeFactory(CompoundGraph graph){
		super(graph);
	}
	
	
	protected ArchetypalCompoundEdge newEdge(BaseChildCompoundGraph owningGraph, int nodeIndex,
			BaseCompoundNode outNode,	BaseCompoundNode inNode) {
		return new CompoundEdge((ChildCompoundGraph)owningGraph, nodeIndex, (CompoundNode)outNode, (CompoundNode)inNode);
	}
	
	@Override
	public CompoundEdge createEdge(){
		return (CompoundEdge)super.createEdge();
	}
}
