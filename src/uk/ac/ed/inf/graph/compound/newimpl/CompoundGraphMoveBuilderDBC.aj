package uk.ac.ed.inf.graph.compound.newimpl;

import uk.ac.ed.inf.designbycontract.Precondition;
import uk.ac.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundGraphMoveBuilder;
import uk.ac.ed.inf.graph.compound.ICompoundGraphMoveBuilderDBC;

public aspect CompoundGraphMoveBuilderDBC extends ICompoundGraphMoveBuilderDBC {

	public pointcut allMethods(ICompoundGraphMoveBuilder obj) :
		execution(public void CompoundGraphMoveBuilder.*(*))
		&& target(obj);

	pointcut constructor(IChildCompoundGraph root) :
		execution(public CompoundGraphMoveBuilder.new(IChildCompoundGraph))
		&& args(root);
	
	before(final IChildCompoundGraph root) : constructor(root) {
		new Precondition(){{
			assertion(root != null, "parameters not null");
		}};
	}
}
