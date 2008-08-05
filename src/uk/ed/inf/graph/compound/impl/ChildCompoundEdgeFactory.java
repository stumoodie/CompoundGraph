package uk.ed.inf.graph.compound.impl;

import uk.ed.inf.graph.compound.archetypal.ArchetypalChildCompoundEdgeFactory;
import uk.ed.inf.graph.compound.archetypal.ArchetypalChildCompoundGraph;
import uk.ed.inf.graph.compound.archetypal.ArchetypalCompoundEdge;
import uk.ed.inf.graph.compound.archetypal.ArchetypalCompoundNode;


public class ChildCompoundEdgeFactory extends ArchetypalChildCompoundEdgeFactory {
	
	public ChildCompoundEdgeFactory(ArchetypalCompoundNode parentNode){
		super(parentNode);
	}

	@Override
	protected ArchetypalCompoundEdge newEdge(
			ArchetypalChildCompoundGraph owningChildGraph, int edgeIndex,
			ArchetypalCompoundNode outNode, ArchetypalCompoundNode inNode) {
		return new CompoundEdge((ChildCompoundGraph)owningChildGraph, edgeIndex,
				(CompoundNode)outNode, (CompoundNode)inNode);
	}
	
	@Override
	public CompoundEdge createEdge(){
		return (CompoundEdge)super.createEdge();
	}
}
