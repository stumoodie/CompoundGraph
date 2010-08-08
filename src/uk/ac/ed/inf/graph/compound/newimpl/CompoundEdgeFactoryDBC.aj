package uk.ac.ed.inf.graph.compound.newimpl;

import uk.ac.ed.inf.designbycontract.Precondition;
import uk.ac.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundEdgeFactoryDBC;
import uk.ac.ed.inf.graph.compound.ICompoundGraph;

public aspect CompoundEdgeFactoryDBC extends ICompoundEdgeFactoryDBC {

	public pointcut theClass(ICompoundEdgeFactory object) :
		target(object)
		&& within(CompoundEdgeFactory);

	pointcut constructor(ICompoundGraph graph) :
		execution(public CompoundEdgeFactory.new(ICompoundGraph))
		&& args(graph);
	
	before(final ICompoundGraph graph) : constructor(graph) {
		new Precondition(){{
			assertion(graph != null, "parameters not null");
		}};
	}
	
}
