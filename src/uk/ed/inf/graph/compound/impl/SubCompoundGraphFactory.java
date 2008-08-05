package uk.ed.inf.graph.compound.impl;

import uk.ed.inf.graph.compound.archetypal.ArchetypalSubCompoundGraphFactory;


public class SubCompoundGraphFactory extends ArchetypalSubCompoundGraphFactory {

	public SubCompoundGraphFactory(CompoundGraph graph){
		super(new SubCompoundGraphBuilder(graph));
	}

	public SubCompoundGraph createInducedSubgraph(){
		return (SubCompoundGraph)super.createInducedSubgraph();
	}

	@Override
	public SubCompoundGraph createSubgraph(){
		return (SubCompoundGraph)super.createSubgraph();
	}
}
