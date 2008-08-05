package uk.ed.inf.graph.compound.archetypal;

import uk.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ed.inf.graph.compound.impl.ChildCompoundGraph;
import uk.ed.inf.graph.compound.impl.CompoundGraph;
import uk.ed.inf.graph.compound.impl.CompoundNode;

public abstract class ArchetypalChildCompoundEdgeFactory implements ICompoundEdgeFactory<ArchetypalCompoundNode, ArchetypalCompoundEdge> {
	private final ArchetypalCompoundNode parentNode;
	private ArchetypalCompoundNode inNode;
	private ArchetypalCompoundNode outNode;
	
	protected ArchetypalChildCompoundEdgeFactory(ArchetypalCompoundNode parentNode){
		this.parentNode = parentNode;
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

	public ArchetypalCompoundEdge createEdge() {
		int idx = this.getGraph().getEdgeCounter().nextIndex();
		return newEdge(this.getOwningChildGraph(), idx, this.outNode, this.inNode);
	}

	protected abstract ArchetypalCompoundEdge newEdge(ArchetypalChildCompoundGraph owningChildGraph,
					int edgeIndex, ArchetypalCompoundNode outNode, ArchetypalCompoundNode inNode);
	
	public CompoundGraph getGraph() {
		return (CompoundGraph)this.parentNode.getGraph();
	}

}
