package uk.ac.ed.inf.graph.compound.newimpl;

import uk.ac.ed.inf.designbycontract.Precondition;
import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.ISubgraphRemovalBuilder;
import uk.ac.ed.inf.graph.compound.ISubgraphRemovalBuilderDBC;

public aspect CompoundSubgraphRemovalBuilderDBC extends ISubgraphRemovalBuilderDBC {

	@Override
	public pointcut allMethods(ISubgraphRemovalBuilder object) :
		execution(public * CompoundSubgraphRemovalBuilder.*(*))
		&& target(object);

	pointcut constructor(ICompoundGraph root) :
		execution(protected CompoundSubgraphRemovalBuilder.new(ICompoundGraph))
		&& args(root);
	
	before(final ICompoundGraph root) : constructor(root) {
		new Precondition(){{
			assertion(root != null, "parameters not null");
		}};
	}
	
}
