package uk.ed.inf.graph.compound.newimpl;

import uk.ac.ed.inf.designbycontract.Precondition;
import uk.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ed.inf.graph.compound.ICompoundGraph;
import uk.ed.inf.graph.compound.ICompoundGraphCopyBuilder;
import uk.ed.inf.graph.compound.ICompoundGraphCopyBuilderDBC;

public aspect CompoundGraphCopyBuilderDBC extends ICompoundGraphCopyBuilderDBC {

	public pointcut theClass(ICompoundGraphCopyBuilder object) :
		target(object)
		&& within(ChildCompoundGraph);

	pointcut constructor(IChildCompoundGraph root) :
		execution(public SubCompoundGraph.new(ICompoundGraph))
		&& args(root);
	
	before(final IChildCompoundGraph root) : constructor(root) {
		new Precondition(){{
			assertion(root != null, "parameters not null");
		}};
	}
}
