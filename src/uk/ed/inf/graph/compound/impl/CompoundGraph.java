package uk.ed.inf.graph.compound.impl;

import uk.ed.inf.graph.compound.archetypal.ArchetypalCompoundGraph;
import uk.ed.inf.graph.compound.base.BaseCompoundNode;


public class CompoundGraph extends ArchetypalCompoundGraph {
	private CompoundNode rootNode;
	
	public CompoundGraph(){
		super(new CompoundGraphCopyBuilder());
	}

	public CompoundGraph(CompoundGraph otherGraph){
		super(new CompoundGraphCopyBuilder(), otherGraph);
	}
	
	@Override
	protected void createNewRootNode(int newIndex){
		this.rootNode = new CompoundNode(this, newIndex);
	}

	@Override
	public CompoundNode getRootNode(){
		return this.rootNode;
	}
	
	@Override
	public CompoundEdgeFactory edgeFactory() {
		return new CompoundEdgeFactory(this);
	}

	@Override
	public CompoundNodeFactory nodeFactory() {
		return new CompoundNodeFactory(rootNode);
	}

	@Override
	public SubCompoundGraphFactory subgraphFactory() {
		return new SubCompoundGraphFactory(this);
	}

	@Override
	protected void createCopyOfRootNode(int newIndexValue, BaseCompoundNode otherRootNode) {
		this.rootNode = new CompoundNode(this, newIndexValue);
	}

    @Override
    protected boolean hasPassedAdditionalValidation() {
        return true;
    }

}
