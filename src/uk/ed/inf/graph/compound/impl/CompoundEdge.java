package uk.ed.inf.graph.compound.impl;

import uk.ed.inf.graph.compound.archetypal.ArchetypalCompoundEdge;


public class CompoundEdge extends ArchetypalCompoundEdge {
	private boolean removed;
    
    
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

    @Override
    protected void setRemoved(boolean removed) {
        this.removed = removed;
    }
    
    @Override
    public boolean isRemoved() {
        return this.removed;
    }
}
