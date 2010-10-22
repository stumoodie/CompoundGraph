package uk.ac.ed.inf.graph.compound;

import uk.ac.ed.inf.designbycontract.ClassInvariant;
import uk.ac.ed.inf.designbycontract.Postcondition;
import uk.ac.ed.inf.designbycontract.Precondition;

public abstract aspect ICompoundEdgeFactoryDBC {
	
	pointcut setPair(ICompoundEdgeFactory cn, CompoundNodePair pair) :
		execution(public void ICompoundEdgeFactory.setPair(CompoundNodePair))
		&& target(cn) && args(pair) ;

	before(final ICompoundEdgeFactory cn, final CompoundNodePair pair) : setPair(cn, pair) {
		new Precondition(){{
			assertion(cn.isValidNodePair(pair), "valid node pair");
		}};
	}
	
	after(final ICompoundEdgeFactory cn, final CompoundNodePair pair) returning : setPair(cn, pair) {
		new Postcondition(){{
			assertion(cn.getParent() != null, "valid parent");
		}};
	}
	
	pointcut createEdge(ICompoundEdgeFactory cf) :
		execution(public ICompoundEdge ICompoundEdgeFactory.createEdge()) &&
		target(cf);
	
	before(final ICompoundEdgeFactory cf) : createEdge(cf) {
		new Precondition(){{
			assertion(cf.canCreateEdge(), "can create the edge");
		}};
	}
	
	public abstract pointcut allMethods(ICompoundEdgeFactory cn);
	
	after(final ICompoundEdgeFactory cn) : allMethods(cn) {
		new ClassInvariant(){{
			assertion(cn.getGraph() != null, "root is not null");
		}};
	}
	
}
