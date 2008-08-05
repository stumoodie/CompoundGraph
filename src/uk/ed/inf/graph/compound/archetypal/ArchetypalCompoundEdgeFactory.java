package uk.ed.inf.graph.compound.archetypal;

import uk.ed.inf.graph.compound.impl.CompoundNode;
import uk.ed.inf.graph.directed.IDirectedEdgeFactory;

public abstract class ArchetypalCompoundEdgeFactory implements IDirectedEdgeFactory<ArchetypalCompoundNode, ArchetypalCompoundEdge> {
	private final ArchetypalCompoundGraph graph;
	private ArchetypalCompoundNode inNode;
	private ArchetypalCompoundNode outNode;
	
	public ArchetypalCompoundEdgeFactory(ArchetypalCompoundGraph graph){
		this.graph = graph;
	}
	
	
	public void setPair(ArchetypalCompoundNode outNode, ArchetypalCompoundNode inNode){
		this.inNode = (CompoundNode)inNode;
		this.outNode = (CompoundNode)outNode;
	}
	
	public ArchetypalCompoundEdge createEdge() {
		if(inNode == null || outNode == null) throw new IllegalStateException("One or more nodes not set");
		ArchetypalCompoundNode lcmNode = this.graph.getLcaNode(this.inNode, this.outNode);
		int cntr = this.graph.getEdgeCounter().nextIndex();
		ArchetypalCompoundEdge newEdge = newEdge(lcmNode.getChildCigraph(), cntr, outNode, inNode); 
		lcmNode.getChildCigraph().addNewEdge(newEdge);
		return newEdge;
	}

	protected abstract ArchetypalCompoundEdge newEdge(ArchetypalChildCompoundGraph owningGraph, int nodeIndex,
			ArchetypalCompoundNode outNode,	ArchetypalCompoundNode inNode);

	public ArchetypalCompoundGraph getGraph() {
		return this.graph;
	}
}
