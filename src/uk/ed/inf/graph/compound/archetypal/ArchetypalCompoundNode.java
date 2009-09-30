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
import uk.ed.inf.graph.compound.base.BaseCompoundNode;
import uk.ed.inf.graph.util.impl.DirectedEdgeSet;

public abstract class ArchetypalCompoundNode extends BaseCompoundNode {
	private final ArchetypalCompoundNode parent;
	private final ArchetypalCompoundGraph superGraph; 
	private final int index;
	
	protected ArchetypalCompoundNode(ArchetypalCompoundGraph superGraph, int index){
		this(superGraph, null, index);
	}
	
	protected ArchetypalCompoundNode(ArchetypalCompoundNode parent, int index){
		this(parent.getGraph(), parent, index);
	}
	
	private ArchetypalCompoundNode(ArchetypalCompoundGraph superGraph, ArchetypalCompoundNode parent, int index){
		super();
		this.superGraph = superGraph;
		this.index = index;
		if(parent == null){
			this.parent = this;
		}
		else{
			this.parent = parent;
			this.parent.getChildCompoundGraph().addNewNode(this);
		}
		createInEdgeSet(new DirectedEdgeSet<BaseCompoundNode, BaseCompoundEdge>());
		createOutEdgeSet(new DirectedEdgeSet<BaseCompoundNode, BaseCompoundEdge>());
		createChildCompoundGraph(this);
	}
	
	@Override
	public ArchetypalCompoundNode getParent() {
		return this.parent;
	}

	protected abstract void createChildCompoundGraph(ArchetypalCompoundNode rootNode);

	@Override
	public abstract ArchetypalChildCompoundGraph getChildCompoundGraph();

	@Override
	public ArchetypalCompoundGraph getGraph() {
		return this.superGraph;
	}

	@Override
	public int getIndex() {
		return this.index;
	}
}
