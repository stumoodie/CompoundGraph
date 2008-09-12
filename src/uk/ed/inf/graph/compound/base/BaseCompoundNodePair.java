package uk.ed.inf.graph.compound.base;

import uk.ed.inf.graph.directed.IDirectedPair;

public class BaseCompoundNodePair implements IDirectedPair<BaseCompoundNode, BaseCompoundEdge> {
	private final BaseCompoundNode outNode;
	private final BaseCompoundNode inNode;

	BaseCompoundNodePair(BaseCompoundNode outNode, BaseCompoundNode inNode) {
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
		if (node.equals(inNode)) {
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
		if (endOne != null && endTwo != null) {
			retVal = hasDirectedEnds(endOne, endTwo)
					|| hasDirectedEnds(endTwo, endOne);
		}
		return retVal;
	}

	public boolean hasDirectedEnds(BaseCompoundNode outNode, BaseCompoundNode inNode) {
		boolean retVal = false;
		if (outNode != null && inNode != null) {
			retVal = this.inNode.equals(inNode) && this.outNode.equals(outNode);
		}
		return retVal;
	}

}
