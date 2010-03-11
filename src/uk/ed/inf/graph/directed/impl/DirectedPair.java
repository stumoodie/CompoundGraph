package uk.ed.inf.graph.directed.impl;

import uk.ed.inf.graph.directed.IDirectedPair;

public class DirectedPair implements IDirectedPair<DirectedNode, DirectedEdge> {
	private final DirectedNode outNode;
	private final DirectedNode inNode;

	public DirectedPair(DirectedNode outNode, DirectedNode inNode){
		this.outNode = outNode;
		this.inNode = inNode;
	}
	
	public DirectedNode getInNode() {
		return this.inNode;
	}

	public DirectedNode getOutNode() {
		return this.outNode;
	}

	public boolean hasDirectedEnds(DirectedNode outNode, DirectedNode inNode) {
		return this.outNode.equals(outNode) && this.inNode.equals(inNode);
	}

	public IDirectedPair<DirectedNode, DirectedEdge> reversedNodes() {
		return new DirectedPair(inNode, outNode);
	}

	public boolean containsNode(DirectedNode node) {
		return this.outNode.equals(node) || this.inNode.equals(node);
	}

	public DirectedNode getOtherNode(DirectedNode node) {
		DirectedNode retVal = this.outNode;
		if(this.outNode.equals(node)){
			retVal = this.inNode;
		}
		return retVal;
	}

	public boolean hasEnds(DirectedNode endOne, DirectedNode endTwo) {
		return this.outNode.equals(endOne) && this.inNode.equals(endTwo)
			|| this.outNode.equals(endTwo) && this.inNode.equals(endOne);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((inNode == null) ? 0 : inNode.hashCode());
		result = prime * result + ((outNode == null) ? 0 : outNode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof DirectedPair))
			return false;
		DirectedPair other = (DirectedPair) obj;
		if (inNode == null) {
			if (other.inNode != null)
				return false;
		} else if (!inNode.equals(other.inNode))
			return false;
		if (outNode == null) {
			if (other.outNode != null)
				return false;
		} else if (!outNode.equals(other.outNode))
			return false;
		return true;
	}

}
