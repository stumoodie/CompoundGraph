package uk.ed.inf.graph.compound.newimpl;

import uk.ac.ed.inf.designbycontract.Precondition;
import uk.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ed.inf.graph.compound.IChildCompoundGraphDBC;
import uk.ed.inf.graph.compound.ICompoundGraphElement;

public aspect ChildCompoundGraphDBC extends IChildCompoundGraphDBC {

	public pointcut theClass(IChildCompoundGraph object) :
		target(object)
		&& within(ChildCompoundGraph);

	pointcut constructor(ICompoundGraphElement root) :
		call(public ChildCompoundGraph.new(ICompoundGraphElement))
		&& args(root);
	
	before(final ICompoundGraphElement root) : constructor(root) {
		new Precondition(){{
			assertion(root != null, "parameters not null");
		}};
	}
}
