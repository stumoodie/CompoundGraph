package uk.ed.inf.graph.compound.impl;

import uk.ed.inf.graph.compound.archetypal.ArchetypalChildCompoundGraph;


public class ChildCompoundGraph extends ArchetypalChildCompoundGraph {
	ChildCompoundGraph(CompoundNode root){
		super(root, new CompoundGraphCopyBuilder() , new CompoundGraphMoveBuilder());
	}
	
	@Override
	public CompoundNode getRootNode() {
		return (CompoundNode)super.getRootNode();
	}

	@Override
	public ChildCompoundEdgeFactory edgeFactory() {
		return new ChildCompoundEdgeFactory(this.getRootNode());
	}

	@Override
	public CompoundNodeFactory nodeFactory() {
		return new CompoundNodeFactory(this.getRootNode());
	}

    @Override
    protected boolean hasPassedAdditionalValidation() {
        return true;
    }
}
