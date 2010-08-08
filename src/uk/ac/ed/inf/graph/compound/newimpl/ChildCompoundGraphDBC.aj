package uk.ac.ed.inf.graph.compound.newimpl;

import uk.ac.ed.inf.designbycontract.Precondition;
import uk.ac.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ac.ed.inf.graph.compound.IChildCompoundGraphDBC;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;

public aspect ChildCompoundGraphDBC extends IChildCompoundGraphDBC {

	public pointcut theClass(IChildCompoundGraph object) :
		target(object)
		&& within(ChildCompoundGraph);

	pointcut constructor(ICompoundGraphElement root) :
		execution(public ChildCompoundGraph.new(ICompoundGraphElement))
		&& args(root);
	
	before(final ICompoundGraphElement root) : constructor(root) {
		new Precondition(){{
			assertion(root != null, "parameters not null");
		}};
	}
}
