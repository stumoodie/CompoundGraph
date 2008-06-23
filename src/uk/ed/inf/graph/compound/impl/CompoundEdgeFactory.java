package uk.ed.inf.graph.compound.impl;

import uk.ed.inf.graph.directed.IDirectedEdgeFactory;

public class CompoundEdgeFactory implements IDirectedEdgeFactory<CompoundNode, CompoundEdge> {
	private final CompoundGraph graph;
	private ChildCompoundGraph childCompoundGraph;
	private CompoundNode inNode;
	private CompoundNode outNode;
	
	public CompoundEdgeFactory(CompoundGraph graph){
		this.graph = graph;
	}
	
	
	public void setPair(CompoundNode inNode, CompoundNode outNode){
		this.inNode = (CompoundNode)inNode;
		this.outNode = (CompoundNode)outNode;
	}
	
	public CompoundEdge createEdge() {
		CompoundNode lcmNode = this.graph.getLcaNode(this.inNode, this.outNode);
		int cntr = this.graph.getEdgeCounter().nextIndex();
		CompoundEdge newEdge = new CompoundEdge(this.childCompoundGraph, cntr, inNode, outNode); 
		lcmNode.getChildCigraph().addNewEdge(newEdge);
		return newEdge;
	}


	public CompoundGraph getGraph() {
		return this.graph;
	}
}
