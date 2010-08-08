package uk.ac.ed.inf.graph.compound.newimpl;

import uk.ac.ed.inf.designbycontract.Precondition;
import uk.ac.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundGraphCopyBuilder;
import uk.ac.ed.inf.graph.compound.ICompoundGraphCopyBuilderDBC;

public aspect CompoundGraphCopyBuilderDBC extends ICompoundGraphCopyBuilderDBC {

	public pointcut allMethods(ICompoundGraphCopyBuilder obj) :
		execution(public void CompoundGraphCopyBuilder.*(*))
		&& target(obj);

	pointcut constructor(IChildCompoundGraph root) :
		execution(public CompoundGraphCopyBuilder.new(IChildCompoundGraph))
		&& args(root);
	
	before(final IChildCompoundGraph root) : constructor(root) {
		new Precondition(){{
			assertion(root != null, "parameters not null");
		}};
	}
}
