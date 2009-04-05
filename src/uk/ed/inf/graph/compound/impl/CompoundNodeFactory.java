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

import uk.ed.inf.graph.compound.base.BaseCompoundNode;
import uk.ed.inf.graph.compound.base.BaseCompoundNodeFactory;


public class CompoundNodeFactory extends BaseCompoundNodeFactory {
	private final CompoundNode parent;
	
	CompoundNodeFactory(CompoundNode parent){
		super();
		this.parent = parent;
	}
	
	@Override
	public CompoundNode newNode(BaseCompoundNode parent, int nodeIndex) {
		return new CompoundNode((CompoundNode)parent, nodeIndex);
	}
	
	@Override
	public CompoundNode createNode(){
		return (CompoundNode)super.createNode();
	}

	@Override
	public CompoundGraph getGraph() {
		return (CompoundGraph)this.parent.getGraph();
	}

	@Override
	public BaseCompoundNode getParentNode() {
		return this.parent;
	}
}
