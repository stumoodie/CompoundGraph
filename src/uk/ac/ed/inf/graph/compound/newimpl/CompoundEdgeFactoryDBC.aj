package uk.ac.ed.inf.graph.compound.newimpl;

import uk.ac.ed.inf.designbycontract.Precondition;
import uk.ac.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundEdgeFactoryDBC;
import uk.ac.ed.inf.graph.compound.ICompoundGraph;

public aspect CompoundEdgeFactoryDBC extends ICompoundEdgeFactoryDBC {

	@Override
	public pointcut allMethods(ICompoundEdgeFactory object) :
		target(object)
		&& (execution(public void CompoundEdgeFactory.*(..)) ||
				execution(public void CommonEdgeFactory.*(..)));

	pointcut constructor(ICompoundGraph graph) :
		execution(public CompoundEdgeFactory.new(ICompoundGraph))
		&& args(graph);
	
	before(final ICompoundGraph graph) : constructor(graph) {
		new Precondition(){{
			assertion(graph != null, "parameters not null");
		}};
	}
	
}
