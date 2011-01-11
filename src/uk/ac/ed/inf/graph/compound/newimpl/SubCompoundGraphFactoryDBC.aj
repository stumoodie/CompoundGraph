package uk.ac.ed.inf.graph.compound.newimpl;

import uk.ac.ed.inf.designbycontract.Precondition;
import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraphFactory;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraphFactoryDBC;

public aspect SubCompoundGraphFactoryDBC extends ISubCompoundGraphFactoryDBC {

	public pointcut allMethods(ISubCompoundGraphFactory object) :
		execution(public void SubCompoundGraphFactory.*(*))
		&& target(object);

	pointcut constructor(ICompoundGraph root) :
		execution(SubCompoundGraphFactory.new(ICompoundGraph))
		&& args(root);
	
	before(final ICompoundGraph root) : constructor(root) {
		new Precondition(){{
			assertion(root != null, "parameters not null");
		}};
	}
}
