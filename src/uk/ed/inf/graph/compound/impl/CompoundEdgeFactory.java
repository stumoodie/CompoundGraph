package uk.ed.inf.graph.compound.impl;

import uk.ed.inf.graph.compound.archetypal.ArchetypalChildCompoundGraph;
import uk.ed.inf.graph.compound.archetypal.ArchetypalCompoundEdge;
import uk.ed.inf.graph.compound.archetypal.ArchetypalCompoundEdgeFactory;
import uk.ed.inf.graph.compound.archetypal.ArchetypalCompoundNode;


public class CompoundEdgeFactory extends ArchetypalCompoundEdgeFactory {
	public CompoundEdgeFactory(CompoundGraph graph){
		super(graph);
	}
	
	
	protected ArchetypalCompoundEdge newEdge(ArchetypalChildCompoundGraph owningGraph, int nodeIndex,
			ArchetypalCompoundNode outNode,	ArchetypalCompoundNode inNode) {
		return new CompoundEdge((ChildCompoundGraph)owningGraph, nodeIndex, (CompoundNode)outNode, (CompoundNode)inNode);
	}
	
	@Override
	public CompoundEdge createEdge(){
		return (CompoundEdge)super.createEdge();
	}
}
