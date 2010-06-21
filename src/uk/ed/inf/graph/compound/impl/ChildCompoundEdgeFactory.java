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

import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.archetypal.ArchetypalCompoundEdge;
import uk.ed.inf.graph.compound.base.BaseChildCompoundEdgeFactory;
import uk.ed.inf.graph.compound.base.BaseChildCompoundGraph;
import uk.ed.inf.graph.compound.base.BaseCompoundGraph;
import uk.ed.inf.graph.compound.base.BaseCompoundNode;


public class ChildCompoundEdgeFactory extends BaseChildCompoundEdgeFactory {
	private final CompoundNode parentNode;
	private CompoundNode outNode;
	private CompoundNode inNode;
	
	public ChildCompoundEdgeFactory(CompoundNode parentNode){
		super();
		this.parentNode = parentNode;
	}

	@Override
	protected ArchetypalCompoundEdge newEdge(
			BaseChildCompoundGraph owningChildGraph, int edgeIndex,
			BaseCompoundNode outNode, BaseCompoundNode inNode) {
		return new CompoundEdge((ChildCompoundGraph)owningChildGraph, edgeIndex,
				(CompoundNode)outNode, (CompoundNode)inNode);
	}
	
	@Override
	public CompoundEdge createEdge(){
		return (CompoundEdge)super.createEdge();
	}

	@Override
	public CompoundNodePair getCurrentNodePair() {
		return new CompoundNodePair(this.getOutNode(), this.getInNode());
	}

	@Override
	public BaseCompoundGraph getGraph() {
		return this.parentNode.getGraph();
	}

	@Override
	protected CompoundNode getInNode() {
		return this.inNode;
	}

	@Override
	protected CompoundNode getOutNode() {
		return this.outNode;
	}

	@Override
	public BaseChildCompoundGraph getOwningChildGraph() {
		return this.parentNode.getChildCompoundGraph();
	}

	@Override
	public void setPair(ICompoundNode outNode, ICompoundNode inNode) {
		if(outNode instanceof CompoundNode && inNode instanceof CompoundNode){
			this.outNode = (CompoundNode)outNode;
			this.inNode = (CompoundNode)inNode;
		}
		else{
			throw new ClassCastException("outNode and inNode must be of type CompoundNode");
		}
	}

	@Override
	public boolean canCreateEdge() {
		return this.isValidNodePair(this.outNode, this.inNode);
	}

	@Override
	public boolean isValidNodePair(ICompoundNode outNode, ICompoundNode inNode) {
		boolean retVal = false;
		if(super.isValidBaseNodePair(outNode, inNode)){
			retVal = outNode instanceof CompoundNode && inNode instanceof CompoundNode;
		}
		return retVal;
	}
}
