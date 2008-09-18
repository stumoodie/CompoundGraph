package uk.ed.inf.graph.compound.impl;

import uk.ed.inf.graph.compound.base.BaseCompoundNode;
import uk.ed.inf.graph.compound.base.BaseCompoundNodeFactory;


public class CompoundNodeFactory extends BaseCompoundNodeFactory {
	private final CompoundNode parent;
	
	CompoundNodeFactory(CompoundNode parent){
		super();
		this.parent = parent;
	}
	
	@Override
	public CompoundNode newNode(BaseCompoundNode parent, int nodeIndex) {
		return new CompoundNode((CompoundNode)parent, nodeIndex);
	}
	
	@Override
	public CompoundNode createNode(){
		return (CompoundNode)super.createNode();
	}

	@Override
	public CompoundGraph getGraph() {
		return (CompoundGraph)this.parent.getGraph();
	}

	@Override
	public BaseCompoundNode getParentNode() {
		return this.parent;
	}
}
