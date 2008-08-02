package uk.ed.inf.graph.compound.impl;

import uk.ed.inf.graph.compound.ICompoundEdgeFactory;

public class ChildCompoundEdgeFactory implements ICompoundEdgeFactory<ArchetypalCompoundNode, ArchetypalCompoundEdge> {
	private final CompoundNode parentNode;
	private CompoundNode inNode;
	private CompoundNode outNode;
	
	public ChildCompoundEdgeFactory(CompoundNode parentNode){
		this.parentNode = parentNode;
	}
	
	public CompoundEdge createEdge(ArchetypalCompoundNode outNode, ArchetypalCompoundNode inNode) {
		// TODO Auto-generated method stub
		return null;
	}

	public ChildCompoundGraph getOwningChildGraph() {
		return (ChildCompoundGraph)this.parentNode.getChildCigraph();
	}

	public boolean isValidNodePair(ArchetypalCompoundNode outNode, ArchetypalCompoundNode inNode) {
		boolean retVal = false;
		if(outNode != null && outNode instanceof CompoundNode && inNode != null && outNode instanceof CompoundNode){
			retVal = parentNode.getGraph().getLcaNode(outNode, inNode).equals(this.parentNode);
		}
		return retVal;
	}

	public void setPair(ArchetypalCompoundNode outNode, ArchetypalCompoundNode inNode) {
		this.inNode = (CompoundNode)inNode;
		this.outNode = (CompoundNode)outNode;
	}

	public CompoundEdge createEdge() {
		int idx = this.getGraph().getEdgeCounter().nextIndex();
		return new CompoundEdge(this.getOwningChildGraph(), idx, this.outNode, this.inNode);
	}

	public CompoundGraph getGraph() {
		return (CompoundGraph)this.parentNode.getGraph();
	}

}
