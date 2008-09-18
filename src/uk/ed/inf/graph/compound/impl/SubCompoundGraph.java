package uk.ed.inf.graph.compound.impl;

import uk.ed.inf.graph.compound.base.BaseSubCompoundGraph;


public class SubCompoundGraph extends BaseSubCompoundGraph {
	private final CompoundGraph superGraph;
	
	SubCompoundGraph(CompoundGraph superGraph){
		super();
		this.superGraph = superGraph;
	}

	@Override
	public CompoundGraph getSuperGraph() {
		return this.superGraph;
	}
	
}
