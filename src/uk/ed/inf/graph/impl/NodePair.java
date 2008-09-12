package uk.ed.inf.graph.impl;

import uk.ed.inf.graph.undirected.IUndirectedPair;


public final class NodePair implements IUndirectedPair<Node, Edge> {
	private final Node oneNode;
	private final Node twoNode;
	
	NodePair(Node oneNode, Node twoNode){
		this.oneNode = oneNode;
		this.twoNode = twoNode;
	}
	
	public Node getOneNode() {
		return this.oneNode;
	}

	public Node getTwoNode() {
		return this.twoNode;
	}

	public boolean containsNode(Node node) {
		return this.oneNode.equals(node) || this.twoNode.equals(node);
	}
	
	
	/**
	 * Get the other node of the pair to this one. 
	 * @param node
	 * @return
	 */	
	public Node getOtherNode(Node node){
		if(containsNode(node) == false) throw new IllegalArgumentException("This node must be contained by this pair");
		Node retVal = this.oneNode.equals(node) ? this.getTwoNode() : this.getOneNode();
		return retVal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((oneNode == null) ? 0 : oneNode.hashCode());
		result = prime * result + ((twoNode == null) ? 0 : twoNode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final NodePair other = (NodePair) obj;
		if (oneNode == null) {
			if (other.oneNode != null)
				return false;
		} else if (!oneNode.equals(other.oneNode))
			return false;
		if (twoNode == null) {
			if (other.twoNode != null)
				return false;
		} else if (!twoNode.equals(other.twoNode))
			return false;
		return true;
	}

	public boolean hasEnds(Node endOne, Node endTwo) {
		boolean retVal = false;
		if(oneNode != null && twoNode != null){
			retVal = (this.oneNode.equals(endOne) && this.twoNode.equals(endTwo))
				|| (this.oneNode.equals(endTwo) && this.twoNode.equals(endOne));
		}
		return retVal;
	}

}
