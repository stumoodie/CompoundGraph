package uk.ac.ed.inf.graph.compound.newimpl;

import uk.ac.ed.inf.designbycontract.Precondition;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.ICompoundNodeDBC;

public aspect CompoundNodeDBC extends ICompoundNodeDBC {

	public pointcut allMethods(ICompoundNode cn) :
		execution(public void CompoundNode.*(..))
		&& target(cn);

	pointcut constructor(ICompoundGraphElement parent, int idx) :
		execution(public CompoundNode.new(ICompoundGraphElement, int))
		&& args(parent, idx);
	
	before(final ICompoundGraphElement parent, final int idx) : constructor(parent, idx) {
		new Precondition(){{
			assertion(parent != null, "parameters not null");
			assertion(idx >= 0, "index a positive number");
		}};
	}
	
}
