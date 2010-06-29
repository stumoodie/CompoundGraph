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

import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.ICompoundNodePair;
import uk.ed.inf.graph.compound.archetypal.ArchetypalChildCompoundGraph;
import uk.ed.inf.graph.compound.archetypal.ArchetypalCompoundNode;

public class BaseCompoundEdge extends BaseCompoundGraphElement implements ICompoundEdge {
	private final int index;
	private final ArchetypalChildCompoundGraph owningSubgraph;
	private final ArchetypalCompoundNode inNode; 
	private final ArchetypalCompoundNode outNode;
	private boolean removed;
	
	protected BaseCompoundEdge(BaseCompoundGraphElement parent, int idx, BaseCompoundNode outNode, BaseCompoundNode inNode){
		this.index = idx;
		this.inNode = inNode;
		this.changeInEdge();
		this.outNode = outNode;
		this.changeOutNode();
		this.parent.getChildCompoundGraph().addNewNode(this);
		removed = false;
	}
	
//	protected abstract void setInNode(BaseCompoundNode outNode);
	
	protected abstract BaseCompoundNode getInNode();
	
	/**
	 * Ensures that edge is added to In Node 
	 * @throws IllegalStateException if getInNode() == null  
	 */
	protected final void changeInEdge(){
		if(getInNode() == null) throw new IllegalStateException("In node must be set");
		
		getInNode().addInEdge(this);
	}
	
//	protected abstract void setOutNode(BaseCompoundNode outNode);
	
	protected abstract BaseCompoundNode getOutNode();
	
    /**
     * This should be used to set the removal status variable only. No other actions#
     * should be performed here. To perform an action on removal then use {@link #removalAction(boolean)}. 
     * @param removed the removal status: true means the edge is removed.
     */
    protected abstract void setRemoved(boolean removed);

    /**
	 * Ensures that edge is added to Out Node 
	 * @throws IllegalStateException if getOutNode() == null  
	 */
	protected final void changeOutNode(){
		if(getOutNode() == null) throw new IllegalStateException("Out node must be set");

		getOutNode().addOutEdge(this);
	}
	
	@Override
	public final ICompoundNodePair getConnectedNodes() {
		return new CiNodePair(this.getOutNode(), this.getInNode());
	}

	@Override
	public abstract BaseCompoundGraph getGraph();


	@Override
	public abstract boolean isRemoved();
	
	@Override
	public final void markRemoved(boolean removed){
		this.setRemoved(removed);
		removalAction(removed);
	}

	/**
	 * additional actions to be executed upon this node being
	 * marked as removed.
	 */
	protected abstract void removalAction(boolean removed);
	
	@Override
	public final boolean hasEnds(ICompoundNodePair ends) {
		return ends.hasEnds(getInNode(), getOutNode());
	}


	@Override
	public final boolean hasDirectedEnds(ICompoundNodePair ends) {
		return ends.hasEnds(getInNode(), getOutNode());
	}

	@Override
	public boolean hasEnds(ICompoundNode endOne, ICompoundNode endTwo) {
		boolean retVal = false;
		if(endOne != null && endTwo != null){
			retVal = hasDirectedEnds(endOne, endTwo) || hasDirectedEnds(endTwo, endOne);
		}
		return retVal;
	}
	
	@Override
	public boolean hasDirectedEnds(ICompoundNode outNode, ICompoundNode inNode){
		boolean retVal = false;
		if(outNode != null && inNode != null){
			retVal = this.getInNode().equals(inNode) && this.getOutNode().equals(outNode);
		}
		return retVal;
	}

	@Override
	public final boolean isSelfEdge() {
		return this.getInNode().equals(this.getOutNode());
	}
	
	private static class CiNodePair implements ICompoundNodePair {
		private final BaseCompoundNode outNode;
		private final BaseCompoundNode inNode;
		
		public CiNodePair(BaseCompoundNode outNode, BaseCompoundNode inNode){
			this.outNode = outNode;
			this.inNode = inNode;
		}
		
		@Override
		public boolean containsNode(ICompoundNode node) {
			return node.equals(inNode) || node.equals(outNode);
		}

		@Override
		public BaseCompoundNode getOtherNode(ICompoundNode node) {
			BaseCompoundNode retVal = inNode;
			if(node.equals(inNode)){
				retVal = outNode;
			}
			return retVal;
		}

		@Override
		public BaseCompoundNode getInNode() {
			return inNode;
		}

		@Override
		public BaseCompoundNode getOutNode() {
			return outNode;
		}

		@Override
		public boolean hasEnds(ICompoundNode endOne, ICompoundNode endTwo) {
			boolean retVal = false;
			if(endOne != null && endTwo != null){
				retVal = hasDirectedEnds(endOne, endTwo) || hasDirectedEnds(endTwo, endOne);
			}
			return retVal;
		}
		
		@Override
		public boolean hasDirectedEnds(ICompoundNode outNode, ICompoundNode inNode){
			boolean retVal = false;
			if(outNode != null && inNode != null){
				retVal = this.inNode.equals(inNode) && this.outNode.equals(outNode);
			}
			return retVal;
		}

		@Override
		public ICompoundNodePair reversedNodes() {
			return new CiNodePair(this.inNode, this.outNode);
		}
		
	}
}
