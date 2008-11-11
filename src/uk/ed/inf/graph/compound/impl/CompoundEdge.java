package uk.ed.inf.graph.compound.impl;

import uk.ed.inf.graph.compound.archetypal.ArchetypalCompoundEdge;


public class CompoundEdge extends ArchetypalCompoundEdge {
	
	CompoundEdge(ChildCompoundGraph owningSubgraph, int index, CompoundNode outNode, CompoundNode inNode){
		super(owningSubgraph, index, outNode, inNode);
	}
	
	@Override
	public String toString(){
		return this.getClass().getSimpleName() + "[index=" + this.getIndex() + "]";
	}

	@Override
	protected void removalAction(boolean removed) {
	}
}
