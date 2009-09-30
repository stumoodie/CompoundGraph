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

import uk.ed.inf.graph.basic.IBasicPair;
import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.directed.IDirectedPair;
import uk.ed.inf.graph.state.IRestorableGraphElement;

public abstract class BaseCompoundEdge implements ICompoundEdge<BaseCompoundNode, BaseCompoundEdge>, IRestorableGraphElement {
	
	protected BaseCompoundEdge(){
		this.setRemoved(false);
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
	
	public final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> getConnectedNodes() {
		return new CiNodePair(this.getOutNode(), this.getInNode());
	}

	public abstract BaseChildCompoundGraph getOwningChildGraph();

	public abstract BaseCompoundGraph getGraph();

	public abstract int getIndex();

	public final int compareTo(BaseCompoundEdge o) {
		return this.getIndex() < o.getIndex() ? -1 : (this.getIndex() == o.getIndex() ? 0 : 1);
	}

	public abstract boolean isRemoved();
	
	public final void markRemoved(boolean removed){
		this.setRemoved(removed);
		removalAction(removed);
	}

	/**
	 * additional actions to be executed upon this node being
	 * marked as removed.
	 */
	protected abstract void removalAction(boolean removed);
	
	public final boolean hasEnds(IBasicPair<? super BaseCompoundNode, ? super BaseCompoundEdge> ends) {
		return ends.hasEnds(getInNode(), getOutNode());
	}


	public final boolean hasDirectedEnds(IDirectedPair<? super BaseCompoundNode, ? super BaseCompoundEdge> ends) {
		return ends.hasEnds(getInNode(), getOutNode());
	}


	public final boolean isSelfEdge() {
		return this.getInNode().equals(this.getOutNode());
	}
	
	private static class CiNodePair implements IDirectedPair<BaseCompoundNode, BaseCompoundEdge> {
		private final BaseCompoundNode outNode;
		private final BaseCompoundNode inNode;
		
		public CiNodePair(BaseCompoundNode outNode, BaseCompoundNode inNode){
			this.outNode = outNode;
			this.inNode = inNode;
		}
		
		public boolean containsNode(BaseCompoundNode node) {
			return node.equals(inNode) || node.equals(outNode);
		}

		public BaseCompoundNode getOneNode() {
			return inNode;
		}

		public BaseCompoundNode getOtherNode(BaseCompoundNode node) {
			BaseCompoundNode retVal = inNode;
			if(node.equals(inNode)){
				retVal = outNode;
			}
			return retVal;
		}

		public BaseCompoundNode getTwoNode() {
			return outNode;
		}

		public BaseCompoundNode getInNode() {
			return inNode;
		}

		public BaseCompoundNode getOutNode() {
			return outNode;
		}

		public boolean hasEnds(BaseCompoundNode endOne, BaseCompoundNode endTwo) {
			boolean retVal = false;
			if(endOne != null && endTwo != null){
				retVal = hasDirectedEnds(endOne, endTwo) || hasDirectedEnds(endTwo, endOne);
			}
			return retVal;
		}
		
		public boolean hasDirectedEnds(BaseCompoundNode outNode, BaseCompoundNode inNode){
			boolean retVal = false;
			if(outNode != null && inNode != null){
				retVal = this.inNode.equals(inNode) && this.outNode.equals(outNode);
			}
			return retVal;
		}

		public IDirectedPair<BaseCompoundNode, BaseCompoundEdge> reversedNodes() {
			return new CiNodePair(this.inNode, this.outNode);
		}
		
	}
}
