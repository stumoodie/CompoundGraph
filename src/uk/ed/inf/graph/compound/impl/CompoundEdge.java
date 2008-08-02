package uk.ed.inf.graph.compound.impl;


public class CompoundEdge extends ArchetypalCompoundEdge {
	
	public CompoundEdge(ChildCompoundGraph owningSubgraph, int index, CompoundNode outNode, CompoundNode inNode){
		super(owningSubgraph, index, outNode, inNode);
	}
	
}
