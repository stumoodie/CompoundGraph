package uk.ed.inf.graph.compound.impl;

import uk.ed.inf.graph.compound.archetypal.ArchetypalCompoundGraph;
import uk.ed.inf.graph.compound.archetypal.ArchetypalCompoundNode;


public class CompoundGraph extends ArchetypalCompoundGraph {
	private CompoundNode rootNode;
	private final SubCompoundGraphFactory subgraphFactory;
	private final CompoundNodeFactory nodeFactory;
	private final CompoundEdgeFactory edgeFactory;
	
	public CompoundGraph(){
		super(new CompoundGraphCopyBuilder());
		this.subgraphFactory = new SubCompoundGraphFactory(this);
		this.edgeFactory = new CompoundEdgeFactory(this);
		this.nodeFactory = new CompoundNodeFactory(rootNode);
	}

	public CompoundGraph(CompoundGraph otherGraph){
		super(new CompoundGraphCopyBuilder(), otherGraph);
		this.subgraphFactory = otherGraph.subgraphFactory();
		this.nodeFactory = otherGraph.nodeFactory;
		this.edgeFactory = otherGraph.edgeFactory;
	}
	
	@Override
	protected void createNewRootNode(int newIndex){
		this.rootNode = new CompoundNode(this, newIndex);
	}

	@Override
	public CompoundNode getRootNode(){
		return this.rootNode;
	}
	

	public CompoundEdgeFactory edgeFactory() {
		return this.edgeFactory;
	}

	public CompoundNodeFactory nodeFactory() {
		return this.nodeFactory;
	}

	public SubCompoundGraphFactory subgraphFactory() {
		return this.subgraphFactory;
	}


	@Override
	protected void createCopyOfRootNode(int newIndexValue, ArchetypalCompoundNode otherRootNode) {
		this.rootNode = new CompoundNode(this, newIndexValue);
	}

}
