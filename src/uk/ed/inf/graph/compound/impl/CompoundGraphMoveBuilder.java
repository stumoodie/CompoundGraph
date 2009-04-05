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

import uk.ed.inf.graph.compound.base.BaseChildCompoundEdgeFactory;
import uk.ed.inf.graph.compound.base.BaseChildCompoundGraph;
import uk.ed.inf.graph.compound.base.BaseCompoundEdge;
import uk.ed.inf.graph.compound.base.BaseCompoundNode;
import uk.ed.inf.graph.compound.base.BaseCompoundNodeFactory;
import uk.ed.inf.graph.compound.base.BaseGraphMoveBuilder;


public class CompoundGraphMoveBuilder extends BaseGraphMoveBuilder {
	CompoundGraphMoveBuilder(){
		super();
	}

	@Override
	protected BaseCompoundNode createMoveOfNode(BaseCompoundNode srcNode, BaseCompoundNode destParentNode) {
		BaseCompoundNodeFactory edgefact = destParentNode.getChildCompoundGraph().nodeFactory();
		return edgefact.createNode();
	}

	@Override
	protected BaseCompoundEdge createMoveOfEdge(BaseCompoundEdge srcEdge, BaseChildCompoundGraph edgeOwner,
			BaseCompoundNode outNode, BaseCompoundNode inNode) {
		BaseChildCompoundEdgeFactory edgefact = edgeOwner.edgeFactory();
		edgefact.setPair(outNode, inNode);
		return edgefact.createEdge();
	}

    @Override
    protected void additionalInitialisation() {
        
    }

    @Override
    protected void additionalMoveTasks() {
        
    }

}
