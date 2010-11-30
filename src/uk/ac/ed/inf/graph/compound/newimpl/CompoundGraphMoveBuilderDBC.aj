package uk.ac.ed.inf.graph.compound.newimpl;

import uk.ac.ed.inf.designbycontract.Precondition;
import uk.ac.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElementFactory;
import uk.ac.ed.inf.graph.compound.ICompoundGraphMoveBuilder;
import uk.ac.ed.inf.graph.compound.ICompoundGraphMoveBuilderDBC;

public aspect CompoundGraphMoveBuilderDBC extends ICompoundGraphMoveBuilderDBC {

	public pointcut allMethods(ICompoundGraphMoveBuilder obj) :
		execution(public void CompoundGraphMoveBuilder.*(*))
		&& target(obj);

	pointcut constructor(IChildCompoundGraph root, ICompoundGraphElementFactory elementFactory) :
		execution(public CompoundGraphMoveBuilder.new(IChildCompoundGraph, ICompoundGraphElementFactory))
		&& args(root, elementFactory);
	
	before(final IChildCompoundGraph root, final ICompoundGraphElementFactory elementFactory) : constructor(root, elementFactory) {
		new Precondition(){{
			assertion(root != null && elementFactory != null, "parameters not null");
		}};
	}
}
