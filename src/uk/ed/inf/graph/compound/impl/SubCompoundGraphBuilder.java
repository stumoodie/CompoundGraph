package uk.ed.inf.graph.compound.impl;

import uk.ed.inf.graph.compound.base.BaseCompoundGraph;
import uk.ed.inf.graph.compound.base.BaseSubCompoundGraphBuilder;


public class SubCompoundGraphBuilder extends BaseSubCompoundGraphBuilder {
	private SubCompoundGraph subgraph;
	
	/**
	 * Construct the builder, providing it with a list of nodes and edges with which to populate
	 * the subgraph that will be constructed.
	 * @param graph the graph to which the subgraph will refer, cannot be null.
	 * @param nodeList the list of nodes to be added to the subgraph, cannot be null.
	 * @param edgeList the list of edges to be added to the subgraph, cannot be null.
	 * @throws NullPointerException if any of the the parameters are null. 
	 */
	public SubCompoundGraphBuilder(CompoundGraph graph){
		super(graph);
	}
	
	@Override
	protected void newSubgraph(BaseCompoundGraph compoundGraph) {
		this.subgraph = new SubCompoundGraph(compoundGraph);
	}

	@Override
	public SubCompoundGraph getSubgraph() {
		return this.subgraph;
	}
	
}
