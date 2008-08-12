package uk.ed.inf.graph.compound.base;

import uk.ed.inf.graph.basic.IBasicPair;
import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.directed.IDirectedPair;
import uk.ed.inf.graph.state.IRestorableGraphElement;

public abstract class BaseCompoundEdge implements ICompoundEdge<BaseCompoundNode, BaseCompoundEdge>, IRestorableGraphElement {
	private boolean removed;
	
	protected BaseCompoundEdge(){
		this.removed = false;
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

	public final boolean isRemoved() {
		return this.removed;
	}
	
	public final void markRemoved(boolean removed){
		this.removed = removed;
	}

	public final boolean hasEnds(IBasicPair<BaseCompoundNode, BaseCompoundEdge> ends) {
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
		
	}
}
