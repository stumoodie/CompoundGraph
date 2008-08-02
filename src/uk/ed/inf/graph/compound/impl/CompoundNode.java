package uk.ed.inf.graph.compound.impl;


public class CompoundNode extends ArchetypalCompoundNode {
//	private final CompoundNode parent;
//	private final IEdgeSet<CompoundNode, CompoundEdge> edgeInList;
//	private final IEdgeSet<CompoundNode, CompoundEdge> edgeOutList;
	private final ChildCompoundGraph childCompoundGraph;
//	private final CompoundGraph superGraph; 
//	private final int index;
//	private boolean removed;
//	private final INodeColourHandler<CompoundNode, CompoundEdge> colour;
	
	public CompoundNode(CompoundGraph superGraph, int index){
		super(superGraph, index);
		this.childCompoundGraph = new ChildCompoundGraph(this);
	}
	
	public CompoundNode(CompoundNode parent, int index){
		super(parent, index);
		this.childCompoundGraph = new ChildCompoundGraph(this);
	}

	@Override
	public ChildCompoundGraph getChildCigraph() {
		return this.childCompoundGraph;
	}
	
}
