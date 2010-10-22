package uk.ac.ed.inf.graph.compound;

import uk.ac.ed.inf.designbycontract.Precondition;

public aspect CompoundNodePairDBC {

	pointcut constructor(ICompoundNode outNode, ICompoundNode inNode) :
		execution(public CompoundNodePair.new(ICompoundNode, ICompoundNode))
		&& args(outNode, inNode);
	
	before(final ICompoundNode outNode, final ICompoundNode inNode) : constructor(outNode, inNode) {
		new Precondition(){{
			assertion(outNode != null && inNode != null, "parameters not null");
			assertion(outNode.getGraph().equals(inNode.getGraph()), "same graph");
		}};
	}
	
	pointcut allMethods(CompoundNodePair pair) :
		execution(public void CompoundNodePair.*(..))
		&& target(pair);
	
}
