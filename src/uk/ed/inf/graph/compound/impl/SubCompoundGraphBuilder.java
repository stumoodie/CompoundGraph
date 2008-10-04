package uk.ed.inf.graph.compound.impl;

import uk.ed.inf.graph.compound.base.BaseSubCompoundGraphBuilder;


public class SubCompoundGraphBuilder extends BaseSubCompoundGraphBuilder {
	private SubCompoundGraph subgraph;
	private final CompoundGraph graph;
	
	/**
	 * Construct the builder, providing it with a list of nodes and edges with which to populate
	 * the subgraph that will be constructed.
	 * @param graph the graph to which the subgraph will refer, cannot be null.
	 * @throws NullPointerException if any of the the parameters are null. 
	 */
	SubCompoundGraphBuilder(CompoundGraph graph){
		super();
		this.graph = graph;
	}
	
	@Override
	protected void newSubgraph() {
		this.subgraph = new SubCompoundGraph(this.getGraph());
	}

	@Override
	public SubCompoundGraph getSubgraph() {
		return this.subgraph;
	}
	
	@Override
	public CompoundGraph getGraph(){
		return this.graph;
	}
	
}
