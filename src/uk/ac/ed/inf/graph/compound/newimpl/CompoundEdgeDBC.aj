package uk.ac.ed.inf.graph.compound.newimpl;

import uk.ac.ed.inf.designbycontract.Precondition;
import uk.ac.ed.inf.graph.compound.ICompoundEdgeDBC;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.IElementAttribute;

public aspect CompoundEdgeDBC extends ICompoundEdgeDBC {

	@Override
	public pointcut allMethods(ICompoundGraphElement edge) :
		execution(public void CompoundEdge.*(*))
		&& target(edge);

	pointcut constructor(ICompoundGraphElement parent, int idx, IElementAttribute attribute, ICompoundNode outNode, ICompoundNode inNode) :
		execution(public CompoundEdge.new(ICompoundGraphElement, int, IElementAttribute, ICompoundNode, ICompoundNode))
		&& args(parent, idx, attribute, outNode, inNode);
	
	before(final ICompoundGraphElement parent, final int idx, final IElementAttribute attribute, final ICompoundNode outNode, final ICompoundNode inNode) : constructor(parent, idx, attribute, outNode, inNode) {
		new Precondition(){{
			assertion(parent != null && outNode != null && inNode != null, "parameters not null");
			assertion(idx >= 0, "index a positive number");
			assertion(parent.getGraph().equals(outNode.getGraph()) && parent.getGraph().equals(inNode.getGraph()), "same graph");
			assertion(parent.isLowestCommonAncestor(outNode, inNode), "parent is lca node");
			assertion(!outNode.isAncestor(inNode) && !inNode.isAncestor(outNode), "no edges with child nodes");
		}};
	}
}
