package uk.ed.inf.graph.compound.impl;

import uk.ed.inf.graph.basic.IBasicPair;
import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.directed.IDirectedPair;
import uk.ed.inf.graph.state.IRestorableGraphElement;

public abstract class ArchetypalCompoundEdge implements ICompoundEdge<ArchetypalCompoundNode, ArchetypalCompoundEdge>, IRestorableGraphElement {
	private final int index;
	private final ArchetypalCompoundNode inNode;
	private final ArchetypalCompoundNode outNode;
	private final ArchetypalChildCompoundGraph owningSubgraph;
	private boolean removed;
	
	protected ArchetypalCompoundEdge(ArchetypalChildCompoundGraph owningSubgraph, int index, ArchetypalCompoundNode outNode, ArchetypalCompoundNode inNode){
		this.index = index;
		this.owningSubgraph = owningSubgraph;
		this.inNode = inNode;
		this.outNode = outNode;
		inNode.addInEdge(this);
		outNode.addOutEdge(this);
		this.removed = false;
	}
	
	public IDirectedPair<ArchetypalCompoundNode, ArchetypalCompoundEdge> getConnectedNodes() {
		return new CiNodePair();
	}

	public ArchetypalChildCompoundGraph getOwningChildGraph() {
		return this.owningSubgraph;
	}

	public ArchetypalCompoundGraph getGraph() {
		return this.owningSubgraph.getSuperGraph();
	}

	public int getIndex() {
		return this.index;
	}

	public int compareTo(ArchetypalCompoundEdge o) {
		return this.index < o.getIndex() ? -1 : (this.index == o.getIndex() ? 0 : 1);
	}

	public boolean isRemoved() {
		return this.removed;
	}
	
	public void markRemoved(boolean removed){
		this.removed = removed;
	}

	public boolean hasEnds(IBasicPair<ArchetypalCompoundNode, ArchetypalCompoundEdge> ends) {
		return ends.hasEnds(inNode, outNode);
	}


	public boolean isSelfEdge() {
		return this.inNode.equals(this.outNode);
	}

	private class CiNodePair implements IDirectedPair<ArchetypalCompoundNode, ArchetypalCompoundEdge> {
		
		public boolean containsNode(ArchetypalCompoundNode node) {
			return node.equals(inNode) || node.equals(outNode);
		}

		public ArchetypalCompoundNode getOneNode() {
			return inNode;
		}

		public ArchetypalCompoundNode getOtherNode(ArchetypalCompoundNode node) {
			ArchetypalCompoundNode retVal = inNode;
			if(node.equals(inNode)){
				retVal = outNode;
			}
			return retVal;
		}

		public ArchetypalCompoundNode getTwoNode() {
			return outNode;
		}

		public ArchetypalCompoundNode getInNode() {
			return inNode;
		}

		public ArchetypalCompoundNode getOutNode() {
			return outNode;
		}

		public boolean hasEnds(ArchetypalCompoundNode endOne, ArchetypalCompoundNode endTwo) {
			boolean retVal = false;
			if(endOne != null && endTwo != null){
				retVal = hasDirectedEnds(endOne, endTwo) || hasDirectedEnds(endTwo, endOne);
			}
			return retVal;
		}
		
		public boolean hasDirectedEnds(ArchetypalCompoundNode outNode, ArchetypalCompoundNode inNode){
			boolean retVal = false;
			if(outNode != null && inNode != null){
				retVal = ArchetypalCompoundEdge.this.inNode.equals(inNode) && ArchetypalCompoundEdge.this.outNode.equals(outNode);
			}
			return retVal;
		}
		
	}
}
