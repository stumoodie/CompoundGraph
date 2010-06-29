package uk.ed.inf.graph.compound.newimpl;

import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.ICompoundNodePair;

public class CompoundNodePair implements ICompoundNodePair {
	private final ICompoundNode outNode;
	private final ICompoundNode inNode;
	
	public CompoundNodePair(ICompoundNode outNode, ICompoundNode inNode) {
		this.outNode = outNode;
		this.inNode = inNode;
	}
	
	@Override
	public boolean containsNode(ICompoundNode node) {
		return this.outNode.equals(node) || this.inNode.equals(node);
	}

	@Override
	public ICompoundNode getInNode() {
		return this.inNode;
	}

	@Override
	public ICompoundNode getOtherNode(ICompoundNode node) {
		return this.inNode.equals(node) ? this.outNode : this.inNode;
	}

	@Override
	public ICompoundNode getOutNode() {
		return this.outNode;
	}

	@Override
	public boolean hasDirectedEnds(ICompoundNode outNode, ICompoundNode inNode) {
		return this.outNode.equals(outNode) && this.inNode.equals(inNode);
	}

	@Override
	public boolean hasEnds(ICompoundNode endOne, ICompoundNode endTwo) {
		return this.hasDirectedEnds(endOne, endTwo) || this.hasDirectedEnds(endTwo, endOne);
	}

	@Override
	public ICompoundNodePair reversedNodes() {
		return new CompoundNodePair(inNode, outNode);
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
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof CompoundNodePair)) {
			return false;
		}
		CompoundNodePair other = (CompoundNodePair) obj;
		if (inNode == null) {
			if (other.inNode != null) {
				return false;
			}
		} else if (!inNode.equals(other.inNode)) {
			return false;
		}
		if (outNode == null) {
			if (other.outNode != null) {
				return false;
			}
		} else if (!outNode.equals(other.outNode)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
		builder.append("(inNode=");
		builder.append(inNode);
		builder.append(", outNode=");
		builder.append(outNode);
		builder.append(")");
		return builder.toString();
	}

	@Override
	public boolean isSelfEdge() {
		return this.outNode.equals(inNode);
	}

}
