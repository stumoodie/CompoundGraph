package uk.ed.inf.graph.compound.newimpl;

import uk.ac.ed.inf.designbycontract.Precondition;
import uk.ed.inf.graph.compound.ICompoundGraph;
import uk.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ed.inf.graph.compound.ISubCompoundGraphDBC;

public aspect SubCompoundGraphDBC extends ISubCompoundGraphDBC {

	public pointcut theClass(ISubCompoundGraph object) :
		target(object)
		&& within(ChildCompoundGraph);

	pointcut constructor(ICompoundGraph root) :
		execution(public SubCompoundGraph.new(ICompoundGraph))
		&& args(root);
	
	before(final ICompoundGraph root) : constructor(root) {
		new Precondition(){{
			assertion(root != null, "parameters not null");
		}};
	}
}
