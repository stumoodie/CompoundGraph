package uk.ed.inf.graph.compound.archetypal;

import uk.ed.inf.graph.basic.IBasicNodeFactory;

public abstract class ArchetypalCompoundNodeFactory implements IBasicNodeFactory<ArchetypalCompoundNode, ArchetypalCompoundEdge> {
	private final ArchetypalCompoundNode parent;
	
	public ArchetypalCompoundNodeFactory(ArchetypalCompoundNode parent){
		if(parent == null) throw new IllegalArgumentException("parent cannot be null");
		
		this.parent = parent;
	}
	
	public ArchetypalCompoundNode createNode() {
		int cntr = this.parent.getGraph().getNodeCounter().nextIndex();
//		CompoundNode retVal = new CompoundNode(this.parent, cntr);
		ArchetypalCompoundNode retVal = newNode(this.parent, cntr);
		this.parent.getChildCigraph().addNewNode(retVal);
		return retVal;
	}
	
	protected abstract ArchetypalCompoundNode newNode(ArchetypalCompoundNode parent, int nodeIndex);

	public ArchetypalCompoundGraph getGraph() {
		return parent.getGraph();
	}
}
