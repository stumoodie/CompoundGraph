package uk.ed.inf.graph.compound.impl;

import uk.ed.inf.graph.compound.archetypal.ArchetypalCompoundNode;
import uk.ed.inf.graph.compound.archetypal.ArchetypalCompoundNodeFactory;


public class CompoundNodeFactory extends ArchetypalCompoundNodeFactory {
	
	public CompoundNodeFactory(CompoundNode parent){
		super(parent);
	}
	
	@Override
	public CompoundNode newNode(ArchetypalCompoundNode parent, int nodeIndex) {
		return new CompoundNode((CompoundNode)parent, nodeIndex);
	}
	
	@Override
	public CompoundNode createNode(){
		return (CompoundNode)super.createNode();
	}
}
