package uk.ed.inf.graph.compound.impl;

import uk.ed.inf.graph.basic.IBasicNodeFactory;

public class CompoundNodeFactory implements IBasicNodeFactory<ArchetypalCompoundNode, ArchetypalCompoundEdge> {
	private final CompoundNode parent;
	
	public CompoundNodeFactory(CompoundNode parent){
		if(parent == null) throw new IllegalArgumentException("parent cannot be null");
		
		this.parent = parent;
	}
	
	public CompoundNode createNode() {
		int cntr = this.parent.getGraph().getNodeCounter().nextIndex();
		CompoundNode retVal = new CompoundNode(this.parent, cntr);
		this.parent.getChildCigraph().addNewNode(retVal);
		return retVal;
	}

	public ArchetypalCompoundGraph getGraph() {
		return parent.getGraph();
	}
}
