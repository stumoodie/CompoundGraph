package uk.ed.inf.graph.compound.newimpl;

import uk.ac.ed.inf.designbycontract.Precondition;
import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundEdgeDBC;
import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.ICompoundNode;

public aspect CompoundEdgeDBC extends ICompoundEdgeDBC {

	public pointcut allMethods(ICompoundEdge edge) :
		execution(public void CompoundEdge.*(*))
		&& target(edge);

	pointcut constructor(ICompoundGraphElement parent, int idx, ICompoundNode outNode, ICompoundNode inNode) :
		execution(public CompoundEdge.new(ICompoundGraphElement, int, ICompoundNode, ICompoundNode))
		&& args(parent, idx, outNode, inNode);
	
	before(final ICompoundGraphElement parent, final int idx, final ICompoundNode outNode, final ICompoundNode inNode) : constructor(parent, idx, outNode, inNode) {
		new Precondition(){{
			assertion(parent != null && outNode != null && inNode != null, "parameters not null");
			assertion(idx >= 0, "index a positive number");
			assertion(parent.getGraph().equals(outNode.getGraph()) && parent.getGraph().equals(inNode.getGraph()), "same graph");
			assertion(parent.getGraph().getElementTree().getLowestCommonAncestor(outNode, inNode).equals(parent), "parent is lca node");
			assertion(!outNode.isAncestor(inNode) && !inNode.isAncestor(outNode), "no edges with child nodes");
		}};
	}
}
