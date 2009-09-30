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
package uk.ed.inf.graph.compound.archetypal;

import uk.ed.inf.graph.compound.base.BaseCompoundEdge;
import uk.ed.inf.graph.compound.base.BaseCompoundGraph;

public abstract class ArchetypalCompoundEdge extends BaseCompoundEdge {
	private final int index;
	private final ArchetypalChildCompoundGraph owningSubgraph;
	private final ArchetypalCompoundNode inNode; 
	private final ArchetypalCompoundNode outNode; 
	
	protected ArchetypalCompoundEdge(ArchetypalChildCompoundGraph owningSubgraph, int index, ArchetypalCompoundNode outNode, ArchetypalCompoundNode inNode){
		super();
		this.index = index;
		this.owningSubgraph = owningSubgraph;
		this.inNode = inNode;
		this.changeInEdge();
		this.outNode = outNode;
		this.changeOutNode();
		this.owningSubgraph.addNewEdge(this);
	}
	
	@Override
	protected final ArchetypalCompoundNode getInNode(){
		return this.inNode;
	}
	
	@Override
	protected final ArchetypalCompoundNode getOutNode(){
		return this.outNode;
	}
	
	
	@Override
	public ArchetypalChildCompoundGraph getOwningChildGraph() {
		return this.owningSubgraph;
	}

	@Override
	public BaseCompoundGraph getGraph() {
		return this.owningSubgraph.getSuperGraph();
	}

	@Override
	public int getIndex() {
		return this.index;
	}

	public int compareTo(ArchetypalCompoundEdge o) {
		return this.index < o.getIndex() ? -1 : (this.index == o.getIndex() ? 0 : 1);
	}
}
