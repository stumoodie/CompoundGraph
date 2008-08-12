package uk.ed.inf.graph.compound.base;

import uk.ed.inf.graph.directed.IDirectedEdgeFactory;

public abstract class BaseCompoundEdgeFactory implements IDirectedEdgeFactory<BaseCompoundNode, BaseCompoundEdge> {
	private final BaseCompoundGraph graph;
	private BaseCompoundNode inNode;
	private BaseCompoundNode outNode;
	
	public BaseCompoundEdgeFactory(BaseCompoundGraph graph){
		this.graph = graph;
	}
	
	
	public void setPair(BaseCompoundNode outNode, BaseCompoundNode inNode){
		this.inNode = inNode;
		this.outNode = outNode;
	}
	
	public BaseCompoundEdge createEdge() {
		if(inNode == null || outNode == null) throw new IllegalStateException("One or more nodes not set");
		BaseCompoundNode lcmNode = this.graph.getLcaNode(this.inNode, this.outNode);
		int cntr = this.graph.getEdgeCounter().nextIndex();
		BaseCompoundEdge newEdge = newEdge(lcmNode.getChildCigraph(), cntr, outNode, inNode); 
		lcmNode.getChildCigraph().addNewEdge(newEdge);
		return newEdge;
	}

	protected abstract BaseCompoundEdge newEdge(BaseChildCompoundGraph owningGraph, int nodeIndex,
			BaseCompoundNode outNode,	BaseCompoundNode inNode);

	public BaseCompoundGraph getGraph() {
		return this.graph;
	}
}
