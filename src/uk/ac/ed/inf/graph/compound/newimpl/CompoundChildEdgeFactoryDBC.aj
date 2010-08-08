package uk.ac.ed.inf.graph.compound.newimpl;

import uk.ac.ed.inf.designbycontract.Precondition;
import uk.ac.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundEdgeFactoryDBC;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;

public aspect CompoundChildEdgeFactoryDBC extends ICompoundEdgeFactoryDBC {

	public pointcut theClass(ICompoundEdgeFactory object) :
		target(object)
		&& within(CompoundChildEdgeFactory);

	pointcut constructor(ICompoundGraphElement parent) :
		execution(public CompoundChildEdgeFactory.new(ICompoundGraphElement))
		&& args(parent);
	
	before(final ICompoundGraphElement parent) : constructor(parent) {
		new Precondition(){{
			assertion(parent != null, "parameters not null");
		}};
	}
	
}
