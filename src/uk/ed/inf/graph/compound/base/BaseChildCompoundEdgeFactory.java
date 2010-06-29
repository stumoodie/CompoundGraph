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

import uk.ed.inf.graph.compound.ICompoundChildEdgeFactory;
import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.archetypal.ArchetypalCompoundEdge;
import uk.ed.inf.graph.compound.archetypal.ArchetypalCompoundNode;
import uk.ed.inf.graph.compound.impl.ChildCompoundGraph;
import uk.ed.inf.graph.compound.impl.CompoundEdge;
import uk.ed.inf.graph.compound.impl.CompoundNode;
import uk.ed.inf.graph.compound.impl.CompoundNodePair;

public class BaseChildCompoundEdgeFactory implements ICompoundChildEdgeFactory {
	
	@Override
	public abstract BaseChildCompoundGraph getOwningChildGraph();

	/**
	 * Methods implementing the <code>isValidNodePair()</code> should call this method
	 * in addition to carrying out it's own checks.
	 * @param outNode
	 * @param inNode
	 * @return
	 */
	protected final boolean isValidBaseNodePair(ICompoundNode outNode, ICompoundNode inNode) {
		boolean retVal = false;
		if(outNode != null  && inNode != null){
			BaseCompoundGraphElement parentNode = this.getOwningChildGraph().getRoot();
			retVal = this.getOwningChildGraph().getSuperGraph().getLcaNode(outNode, inNode).equals(parentNode);
		}
		return retVal;
	}

	protected BaseCompoundEdge newEdge(BaseChildCompoundGraph owningChildGraph, int edgeIndex, BaseCompoundNode outNode, BaseCompoundNode inNode) {
		return new BaseCompoundEdge((ChildCompoundGraph)owningChildGraph, edgeIndex, outNode, inNode);
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
	protected BaseCompoundNode getInNode() {
		return this.inNode;
	}

	@Override
	protected BaseCompoundNode getOutNode() {
		return this.outNode;
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
		
	@Override
	public ArchetypalCompoundNode getRoot() {
		return this.root;
	}
	
	@Override
	protected void addNewNode(ICompoundNode node) {
	    super.addNewNode(node);
	}

	@Override
	protected void addNewEdge(ICompoundEdge edge) {
	    super.addNewEdge(edge);
	}
	
	@Override
	public BaseCompoundEdge createEdge() {
		int idx = this.getGraph().getEdgeCounter().nextIndex();
		BaseCompoundEdge newCompoundEdge = newEdge(this.getOwningChildGraph(), idx, this.getOutNode(), this.getInNode());
//		this.getOwningChildGraph().addNewEdge(newCompoundEdge) ;
		this.getGraph().notifyNewEdge(newCompoundEdge);
		this.getOwningChildGraph().notifyNewEdge(newCompoundEdge);
		return newCompoundEdge ;
	}

}
