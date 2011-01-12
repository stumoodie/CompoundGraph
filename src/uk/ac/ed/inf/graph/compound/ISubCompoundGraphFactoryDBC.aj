package uk.ac.ed.inf.graph.compound;

import uk.ac.ed.inf.designbycontract.Postcondition;
import uk.ac.ed.inf.designbycontract.Precondition;

public abstract aspect ISubCompoundGraphFactoryDBC {

	public abstract pointcut allMethods(ISubCompoundGraphFactory obj);
	
	pointcut createSubgraph(ISubCompoundGraphFactory fact) :
		execution(public ISubCompoundGraph ISubCompoundGraphFactory.createSubgraph()) &&
		target(fact) &&
		args();
	
	after(final ISubCompoundGraphFactory fact) returning(final ISubCompoundGraph subgraph) : createSubgraph(fact) {
		new Postcondition(){{
			assertion(subgraph != null, "subgraph exists");
		}};
	}

	
	pointcut createInducedSubgraph(ISubCompoundGraphFactory fact) :
		execution(public ISubCompoundGraph ISubCompoundGraphFactory.createInducedSubgraph()) &&
		target(fact) &&
		args();
	
	after(final ISubCompoundGraphFactory fact) returning(final ISubCompoundGraph subgraph) : createInducedSubgraph(fact) {
		new Postcondition(){{
			assertion(subgraph != null, "subgraph exists");
			assertion(subgraph.isInducedSubgraph(), "subgraph is induced");
			assertion(!subgraph.hasOrphanedEdges(), "no dangling edges");
		}};
	}

	
	pointcut createPermissiveInducedSubgraph(ISubCompoundGraphFactory fact) :
		execution(public ISubCompoundGraph ISubCompoundGraphFactory.createPermissiveInducedSubgraph()) &&
		target(fact) &&
		args();
	
	after(final ISubCompoundGraphFactory fact) returning(final ISubCompoundGraph subgraph) : createPermissiveInducedSubgraph(fact) {
		new Postcondition(){{
			assertion(subgraph != null, "subgraph exists");
			assertion(subgraph.isInducedSubgraph(), "subgraph is induced");
		}};
	}

	pointcut addElement(ISubCompoundGraphFactory fact, ICompoundGraphElement element) :
		execution(public void ISubCompoundGraphFactory.addElement(ICompoundGraphElement)) &&
		target(fact) &&
		args(element);
	
	before(final ISubCompoundGraphFactory fact, final ICompoundGraphElement element) : addElement(fact, element) {
		new Precondition(){{
			assertion(element != null, "element not null");
		}};
	}

	after(final ISubCompoundGraphFactory obj) : allMethods(obj) {
		new Postcondition(){{
			assertion(obj.numElements() >= 0, "positive num elements");
			assertion(obj.getGraph() != null, "graph set");
		}};
	}
	
}
