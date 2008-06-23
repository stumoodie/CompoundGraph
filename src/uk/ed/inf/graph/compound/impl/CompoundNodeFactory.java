package uk.ed.inf.graph.compound.impl;

import uk.ed.inf.graph.basic.IBasicNodeFactory;

public class CompoundNodeFactory implements IBasicNodeFactory<CompoundNode, CompoundEdge> {
	private final CompoundNode parent;
	
	public CompoundNodeFactory(CompoundNode parent){
		if(parent == null) throw new IllegalArgumentException("parent cannot be null");
		
		this.parent = parent;
	}
	
	public CompoundNode createNode() {
		int indexCntr = this.parent.getGraph().getNodeCounter().nextIndex();
		CompoundNode retVal = new CompoundNode(this.parent, indexCntr);
		this.parent.getChildCigraph().addNewNode(retVal);
		return retVal;
	}
}
