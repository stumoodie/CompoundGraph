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

import uk.ed.inf.graph.compound.archetypal.ArchetypalChildCompoundGraph;


public class ChildCompoundGraph extends ArchetypalChildCompoundGraph {
	ChildCompoundGraph(CompoundNode root){
		super(root, new CompoundGraphCopyBuilder() , new CompoundGraphMoveBuilder());
	}
	
	@Override
	public CompoundNode getRootNode() {
		return (CompoundNode)super.getRootNode();
	}

	@Override
	public ChildCompoundEdgeFactory edgeFactory() {
		return new ChildCompoundEdgeFactory(this.getRootNode());
	}

	@Override
	public CompoundNodeFactory nodeFactory() {
		return new CompoundNodeFactory(this.getRootNode());
	}

    @Override
    protected boolean hasPassedAdditionalValidation() {
        return true;
    }
}
