/*
Copyright 2009, Court of the University of Edinburgh

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License. 
*/
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

	@Override
	protected void addAdditionalEdges() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void addAdditionalNodes() {
		// TODO Auto-generated method stub
		
	}
	
}
