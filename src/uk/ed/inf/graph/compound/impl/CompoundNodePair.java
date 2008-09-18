package uk.ed.inf.graph.compound.impl;

import uk.ed.inf.graph.compound.base.BaseCompoundNode;
import uk.ed.inf.graph.compound.base.BaseCompoundNodePair;

public class CompoundNodePair extends BaseCompoundNodePair {
	private final CompoundNode outNode;
	private final CompoundNode inNode;
	
	public CompoundNodePair(CompoundNode outNode, CompoundNode inNode) {
		this.outNode = outNode;
		this.inNode = inNode;
	}

	@Override
	public BaseCompoundNode getInNode() {
		return this.inNode;
	}

	@Override
	public BaseCompoundNode getOutNode() {
		return this.outNode;
	}

}
