package uk.ed.inf.graph.compound.base;

import uk.ed.inf.graph.directed.IDirectedPair;

public abstract class BaseCompoundNodePair implements IDirectedPair<BaseCompoundNode, BaseCompoundEdge> {

	public boolean containsNode(BaseCompoundNode node) {
		return node.equals(getInNode()) || node.equals(getOutNode());
	}

	public BaseCompoundNode getOneNode() {
		return getInNode();
	}

	public BaseCompoundNode getOtherNode(BaseCompoundNode node) {
		BaseCompoundNode retVal = getInNode();
		if (node.equals(getInNode())) {
			retVal = getOutNode();
		}
		return retVal;
	}

	public BaseCompoundNode getTwoNode() {
		return getOutNode();
	}

	public abstract BaseCompoundNode getInNode();

	public abstract BaseCompoundNode getOutNode();

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
			retVal = this.getInNode().equals(inNode) && this.getOutNode().equals(outNode);
		}
		return retVal;
	}

}
