package uk.ed.inf.graph.compound.impl;

import uk.ed.inf.graph.directed.IDirectedEdgeFactory;

public class CompoundEdgeFactory implements IDirectedEdgeFactory<ArchetypalCompoundNode, ArchetypalCompoundEdge> {
	private final CompoundGraph graph;
	private CompoundNode inNode;
	private CompoundNode outNode;
	
	public CompoundEdgeFactory(CompoundGraph graph){
		this.graph = graph;
	}
	
	
	public void setPair(ArchetypalCompoundNode outNode, ArchetypalCompoundNode inNode){
		this.inNode = (CompoundNode)inNode;
		this.outNode = (CompoundNode)outNode;
	}
	
	public CompoundEdge createEdge() {
		if(inNode == null || outNode == null) throw new IllegalStateException("One or more nodes not set");
		ArchetypalCompoundNode lcmNode = this.graph.getLcaNode(this.inNode, this.outNode);
		int cntr = this.graph.getEdgeCounter().nextIndex();
		CompoundEdge newEdge = new CompoundEdge((ChildCompoundGraph)lcmNode.getChildCigraph(), cntr, outNode, inNode); 
		lcmNode.getChildCigraph().addNewEdge(newEdge);
		return newEdge;
	}


	public CompoundGraph getGraph() {
		return this.graph;
	}
}
