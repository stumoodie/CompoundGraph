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

import uk.ed.inf.graph.compound.archetypal.ArchetypalCompoundEdge;
import uk.ed.inf.graph.compound.base.BaseChildCompoundGraph;
import uk.ed.inf.graph.compound.base.BaseCompoundEdgeFactory;
import uk.ed.inf.graph.compound.base.BaseCompoundNode;


public class CompoundEdgeFactory extends BaseCompoundEdgeFactory {
	private final CompoundGraph graph;
	private CompoundNode outNode;
	private CompoundNode inNode;
	
	CompoundEdgeFactory(CompoundGraph graph){
		this.graph = graph;
	}
	
	@Override
	protected ArchetypalCompoundEdge newEdge(BaseChildCompoundGraph owningGraph, int nodeIndex,
			BaseCompoundNode outNode,	BaseCompoundNode inNode) {
		return new CompoundEdge((ChildCompoundGraph)owningGraph, nodeIndex, (CompoundNode)outNode, (CompoundNode)inNode);
	}
	
	@Override
	public CompoundEdge createEdge(){
		return (CompoundEdge)super.createEdge();
	}

	@Override
	public CompoundNodePair getCurrentNodePair() {
		return new CompoundNodePair(this.outNode, this.inNode);
	}

	@Override
	public CompoundGraph getGraph() {
		return this.graph;
	}

	@Override
	protected BaseCompoundNode getInNode() {
		return this.inNode;
	}

	@Override
	protected BaseCompoundNode getOutNode() {
		return this.outNode;
	}

	@Override
	public void setPair(BaseCompoundNode outNode, BaseCompoundNode inNode) {
		this.outNode = (CompoundNode)outNode;
		this.inNode = (CompoundNode)inNode;		
	}

	@Override
	public boolean canCreateEdge() {
		return this.inNode != null && this.outNode != null;
	}

	@Override
	public ChildCompoundGraph getOwningChildGraph() {
		return (ChildCompoundGraph)super.getOwningChildGraph();
	}

	public boolean isValidNodePair(BaseCompoundNode outNode, BaseCompoundNode inNode) {
		return this.outNode != null && this.inNode != null && this.outNode.getGraph().equals(this.graph)
			&& this.outNode.equals(this.inNode) && this.outNode instanceof CompoundNode
			&& this.inNode instanceof CompoundNode;
	}
}
