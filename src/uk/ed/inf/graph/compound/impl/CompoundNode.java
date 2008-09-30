package uk.ed.inf.graph.compound.impl;

import uk.ed.inf.graph.compound.archetypal.ArchetypalCompoundNode;


public class CompoundNode extends ArchetypalCompoundNode {
//	private final CompoundNode parent;
//	private final IEdgeSet<CompoundNode, CompoundEdge> edgeInList;
//	private final IEdgeSet<CompoundNode, CompoundEdge> edgeOutList;
	private ChildCompoundGraph childCompoundGraph;
//	private final CompoundGraph superGraph; 
//	private final int index;
//	private boolean removed;
//	private final INodeColourHandler<CompoundNode, CompoundEdge> colour;
	
	CompoundNode(CompoundGraph superGraph, int index){
		super(superGraph, index);
	}
	
	CompoundNode(CompoundNode parent, int index){
		super(parent, index);
	}

	@Override
	protected void createChildCompoundGraph(ArchetypalCompoundNode rootNode){
		this.childCompoundGraph = new ChildCompoundGraph(this);
	}
	
	@Override
	public ChildCompoundGraph getChildCompoundGraph() {
		return this.childCompoundGraph;
	}

	@Override
	public String toString(){
		return this.getClass().getSimpleName() + "[index=" + this.getIndex() + "]";
	}
}
