package uk.ac.ed.inf.graph.compound.newimpl;

import uk.ac.ed.inf.designbycontract.Precondition;
import uk.ac.ed.inf.graph.compound.ICompoundChildEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundChildEdgeFactoryDBC;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;

public aspect CompoundChildEdgeFactoryDBC extends ICompoundChildEdgeFactoryDBC {

	@Override
	public pointcut allMethods(ICompoundChildEdgeFactory object) :
		(execution(public void CompoundChildEdgeFactory.*(..)) ||
				execution(public void CommonEdgeFactory.*(..))) &&
		target(object);

	pointcut constructor(ICompoundGraphElement parent) :
		execution(public CompoundChildEdgeFactory.new(ICompoundGraphElement))
		&& args(parent);
	
	before(final ICompoundGraphElement parent) : constructor(parent) {
		new Precondition(){{
			assertion(parent != null, "parameters not null");
		}};
	}
	
}
