package uk.ac.ed.inf.graph.compound.newimpl;

import uk.ac.ed.inf.designbycontract.Precondition;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ICompoundNodeDBC;
import uk.ac.ed.inf.graph.compound.IElementAttribute;

public aspect CompoundNodeDBC extends ICompoundNodeDBC {

	@Override
	public pointcut allMethods(ICompoundGraphElement cn) :
		(execution(public void CompoundNode.*(..))
		|| execution(public void CommonCompoundNode.*(..)))
		&& target(cn);

	pointcut constructor(ICompoundGraphElement parent, int idx, IElementAttribute attrib) :
		execution(public CompoundNode.new(ICompoundGraphElement, int, IElementAttribute))
		&& args(parent, idx, attrib);
	
	before(final ICompoundGraphElement parent, final int idx, final IElementAttribute attrib) : constructor(parent, idx, attrib) {
		new Precondition(){{
			assertion(parent != null, "parameters not null");
			assertion(idx >= 0, "index a positive number");
			assertion(attrib != null, "attribute not null");
		}};
	}
	
}
