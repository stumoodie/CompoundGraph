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

import uk.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ed.inf.graph.compound.archetypal.ArchetypalCompoundGraph;
import uk.ed.inf.graph.compound.base.BaseCompoundEdge;
import uk.ed.inf.graph.compound.base.BaseCompoundNode;

public class CompoundGraph extends ArchetypalCompoundGraph {
	private CompoundNode rootNode;
	
	public CompoundGraph(){
		super(new CompoundGraphCopyBuilder());
	}

	public CompoundGraph(CompoundGraph otherGraph){
		super(new CompoundGraphCopyBuilder(), otherGraph);
	}
	
	@Override
	protected void createNewRootNode(int newIndex){
		this.rootNode = new CompoundNode(this, newIndex);
	}

	@Override
	public CompoundNode getRootNode(){
		return this.rootNode;
	}
	
	@Override
	public CompoundEdgeFactory edgeFactory() {
		return new CompoundEdgeFactory(this);
	}

	@Override
	public CompoundNodeFactory nodeFactory() {
		return new CompoundNodeFactory(rootNode);
	}

	@Override
	public SubCompoundGraphFactory subgraphFactory() {
		return new SubCompoundGraphFactory(this);
	}

	@Override
	protected void createCopyOfRootNode(int newIndexValue, BaseCompoundNode otherRootNode) {
		this.rootNode = new CompoundNode(this, newIndexValue);
	}

    @Override
    protected boolean hasPassedAdditionalValidation() {
        return true;
    }
	@Override
	protected void notifyCopyOperationComplete(ISubCompoundGraph<? extends BaseCompoundNode, ? extends BaseCompoundEdge> originalSubgraph,
			ISubCompoundGraph<? extends BaseCompoundNode, ? extends BaseCompoundEdge> copiedNodes) {
		
	}

	@Override
	protected void notifyRemovalOperationComplete(ISubCompoundGraph<? extends BaseCompoundNode, ? extends BaseCompoundEdge> subgraph) {
		
	}

	@Override
	public void notifyNewEdge(BaseCompoundEdge newEdge) {
	}

	@Override
	public void notifyNewNode(BaseCompoundNode newNode) {
	}

}
