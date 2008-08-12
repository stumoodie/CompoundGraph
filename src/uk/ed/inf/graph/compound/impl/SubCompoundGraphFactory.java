package uk.ed.inf.graph.compound.impl;

import uk.ed.inf.graph.compound.base.BaseSubCompoundGraphFactory;



public class SubCompoundGraphFactory extends BaseSubCompoundGraphFactory {

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
