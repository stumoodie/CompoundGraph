package uk.ac.ed.inf.graph.compound.newimpl;

import uk.ac.ed.inf.designbycontract.Precondition;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ICompoundNodeFactoryDBC;

public aspect CompoundNodeFactoryDBC extends ICompoundNodeFactoryDBC {

	public pointcut constructor(ICompoundGraphElement parent) :
		execution(public CompoundNodeFactory.new(ICompoundGraphElement))
		&& args(parent);
	
	before(final ICompoundGraphElement parent) : constructor(parent) {
		new Precondition(){{
			assertion(parent != null, "parent not null");
		}};
	}
	
}
