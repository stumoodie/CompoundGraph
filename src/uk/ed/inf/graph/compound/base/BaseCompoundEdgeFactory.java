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
package uk.ed.inf.graph.compound.base;

import uk.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.ICompoundNode;

public class BaseCompoundEdgeFactory implements ICompoundEdgeFactory {
	
	@Override
	public abstract void setPair(ICompoundNode outNode, ICompoundNode inNode);

	protected abstract BaseCompoundNode getOutNode();
	
	protected abstract BaseCompoundNode getInNode(); 
		
	public abstract BaseCompoundNodePair getCurrentNodePair();
	
	@Override
	public BaseCompoundGraphElement getParent(){
		if(this.getCurrentNodePair() == null) throw new IllegalArgumentException("No node pair has been set");
		
		ICompoundGraphElement lcmNode = this.getGraph().getLcaNode(this.getOutNode(), this.getInNode());
		return (BaseCompoundGraphElement)lcmNode;
	}


	@Override
	public BaseCompoundEdge createEdge() {
		if(this.getOutNode() == null || this.getInNode() == null) throw new IllegalStateException("One or more nodes not set");
		BaseCompoundNode lcmNode = (BaseCompoundNode)this.getGraph().getLcaNode(this.getOutNode(), this.getInNode());
		int cntr = this.getGraph().getEdgeCounter().nextIndex();
		BaseCompoundEdge newEdge = newEdge(lcmNode.getChildCompoundGraph(), cntr, this.getOutNode(), this.getInNode()); 
//		lcmNode.getChildCompoundGraph().addNewEdge(newEdge);
		this.getGraph().notifyNewEdge(newEdge);
		this.getParent().getChildCompoundGraph().notifyNewEdge(newEdge);
		return newEdge;
	}

	protected abstract BaseCompoundEdge newEdge(BaseChildCompoundGraph owningGraph, int nodeIndex,
			BaseCompoundNode outNode,	BaseCompoundNode inNode);

	@Override
	public abstract BaseCompoundGraph getGraph();
	
	@Override
	public abstract boolean canCreateEdge();
}
