package uk.ac.ed.inf.graph.compound;

import uk.ac.ed.inf.designbycontract.ClassInvariant;
import uk.ac.ed.inf.designbycontract.Postcondition;
import uk.ac.ed.inf.designbycontract.Precondition;

public abstract aspect ISubgraphRemovalBuilderDBC {
	
	public abstract pointcut allMethods(ISubgraphRemovalBuilder obj);
	
	pointcut setRemovalSubgraph(ISubgraphRemovalBuilder rb, ISubCompoundGraph subgraph) :
		execution(public void ISubgraphRemovalBuilder.setRemovalSubgraph(ISubCompoundGraph)) &&
		target(rb) &&
		args(subgraph);
	
	after(final ISubgraphRemovalBuilder rb, final ISubCompoundGraph subgraph) returning : setRemovalSubgraph(rb, subgraph) {
		new Postcondition(){{
			assertion(subgraph.equals(rb.getRemovalSubgraph()), "subgraph is set");
		}};
	}
	
	pointcut removeSubgraph(ISubgraphRemovalBuilder rb) :
		execution(public void removeSubgraph())
		&& target(rb);

	before(final ISubgraphRemovalBuilder rb) : removeSubgraph(rb) {
		new Precondition(){{
			assertion(rb.canRemoveSubgraph(), "can remove subgraph");
		}};
	}

	after(final ISubgraphRemovalBuilder rb) returning : removeSubgraph(rb) {
		new Precondition(){{
			assertion(rb.canRemoveSubgraph(), "can remove subgraph");
			assertion(rb.getRemovalSubgraph() != null, "has removal subgraph");
		}};
	}
	
	after(final ISubgraphRemovalBuilder rb) : allMethods(rb){
		new ClassInvariant(){{
			assertion(rb.getGraph() != null, "graph always defined");
			assertion(implies(rb.canRemoveSubgraph(), rb.getRemovalSubgraph() != null), "can remove implied subgraph defined");
			assertion(implies(rb.canRemoveSubgraph(), rb.getRemovalSubgraph().isConsistentSnapShot()), "can remove implied subgraph is consistent");
			assertion(implies(rb.getRemovalSubgraph() == null, !rb.canRemoveSubgraph()), "no subgraph implies cannot remove");
		}};
	}
}