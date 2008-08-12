package uk.ed.inf.graph.compound.impl;

import uk.ed.inf.graph.compound.base.BaseCompoundNode;
import uk.ed.inf.graph.compound.base.BaseCompoundNodeFactory;


public class CompoundNodeFactory extends BaseCompoundNodeFactory {
	
	public CompoundNodeFactory(CompoundNode parent){
		super(parent);
	}
	
	@Override
	public CompoundNode newNode(BaseCompoundNode parent, int nodeIndex) {
		return new CompoundNode((CompoundNode)parent, nodeIndex);
	}
	
	@Override
	public CompoundNode createNode(){
		return (CompoundNode)super.createNode();
	}
}
