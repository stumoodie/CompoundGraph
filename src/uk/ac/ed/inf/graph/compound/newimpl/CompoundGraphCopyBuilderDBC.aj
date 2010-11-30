package uk.ac.ed.inf.graph.compound.newimpl;

import uk.ac.ed.inf.designbycontract.Precondition;
import uk.ac.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundGraphCopyBuilder;
import uk.ac.ed.inf.graph.compound.ICompoundGraphCopyBuilderDBC;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElementFactory;

public aspect CompoundGraphCopyBuilderDBC extends ICompoundGraphCopyBuilderDBC {

	public pointcut allMethods(ICompoundGraphCopyBuilder obj) :
		execution(public void CompoundGraphCopyBuilder.*(*))
		&& target(obj);

	pointcut constructor(IChildCompoundGraph root, ICompoundGraphElementFactory elementFactory) :
		execution(public CompoundGraphCopyBuilder.new(IChildCompoundGraph, ICompoundGraphElementFactory))
		&& args(root, elementFactory);
	
	before(final IChildCompoundGraph root, final ICompoundGraphElementFactory elementFactory) : constructor(root, elementFactory) {
		new Precondition(){{
			assertion(root != null && elementFactory != null, "parameters not null");
		}};
	}
}
