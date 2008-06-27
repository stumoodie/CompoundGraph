package uk.ed.inf.graph.compound.impl;

import uk.ed.inf.graph.basic.IBasicPair;
import uk.ed.inf.graph.colour.IColouredEdge;
import uk.ed.inf.graph.colour.IEdgeColourHandler;
import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.directed.IDirectedPair;
import uk.ed.inf.graph.state.IRestorableGraphElement;

public class CompoundEdge implements ICompoundEdge<CompoundNode, CompoundEdge>, IColouredEdge<CompoundNode, CompoundEdge>,
		IRestorableGraphElement {
	private final int index;
	private final CompoundNode inNode;
	private final CompoundNode outNode;
	private final ChildCompoundGraph owningSubgraph;
	private boolean removed;
	private final IEdgeColourHandler<CompoundNode, CompoundEdge> colour;
	
	public CompoundEdge(ChildCompoundGraph owningSubgraph, int index, IEdgeColourHandler<CompoundNode, CompoundEdge> colour,
						CompoundNode outNode, CompoundNode inNode){
		this.index = index;
		this.owningSubgraph = owningSubgraph;
		this.inNode = inNode;
		this.outNode = outNode;
		inNode.addInEdge(this);
		outNode.addOutEdge(this);
		this.removed = false;
		this.colour = colour;
		this.colour.setEdge(this);
	}
	
	public IDirectedPair<CompoundNode, CompoundEdge> getConnectedNodes() {
		return new CiNodePair();
	}

	public ChildCompoundGraph getOwningChildGraph() {
		return this.owningSubgraph;
	}

	public CompoundGraph getGraph() {
		return this.owningSubgraph.getSuperGraph();
	}

	public int getIndex() {
		return this.index;
	}

	public int compareTo(CompoundEdge o) {
		return this.index < o.getIndex() ? -1 : (this.index == o.getIndex() ? 0 : 1);
	}

	public boolean isRemoved() {
		return this.removed;
	}
	
	public void markRemoved(boolean removed){
		this.removed = removed;
	}

	public boolean hasEnds(IBasicPair<CompoundNode, CompoundEdge> ends) {
		return ends.hasEnds(inNode, outNode);
	}


	public boolean isSelfEdge() {
		return this.inNode.equals(this.outNode);
	}

	private class CiNodePair implements IDirectedPair<CompoundNode, CompoundEdge> {
		
		public boolean containsNode(CompoundNode node) {
			return node.equals(inNode) || node.equals(outNode);
		}

		public CompoundNode getOneNode() {
			return inNode;
		}

		public CompoundNode getOtherNode(CompoundNode node) {
			CompoundNode retVal = inNode;
			if(node.equals(inNode)){
				retVal = outNode;
			}
			return retVal;
		}

		public CompoundNode getTwoNode() {
			return outNode;
		}

		public CompoundNode getInNode() {
			return inNode;
		}

		public CompoundNode getOutNode() {
			return outNode;
		}

		public boolean hasEnds(CompoundNode endOne, CompoundNode endTwo) {
			boolean retVal = false;
			if(endOne != null && endTwo != null){
				retVal = hasDirectedEnds(endOne, endTwo) || hasDirectedEnds(endTwo, endOne);
			}
			return retVal;
		}
		
		public boolean hasDirectedEnds(CompoundNode outNode, CompoundNode inNode){
			boolean retVal = false;
			if(outNode != null && inNode != null){
				retVal = CompoundEdge.this.inNode.equals(inNode) && CompoundEdge.this.outNode.equals(outNode);
			}
			return retVal;
		}
		
	}

	@Override
	public IEdgeColourHandler<CompoundNode, CompoundEdge> getColourHandler() {
		return this.colour;
	}
}
