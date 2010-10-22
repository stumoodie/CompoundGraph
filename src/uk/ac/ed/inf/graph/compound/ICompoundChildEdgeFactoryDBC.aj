package uk.ac.ed.inf.graph.compound;

import uk.ac.ed.inf.designbycontract.ClassInvariant;
import uk.ac.ed.inf.designbycontract.Postcondition;
import uk.ac.ed.inf.designbycontract.Precondition;

public abstract aspect ICompoundChildEdgeFactoryDBC {
	
	pointcut setPair(ICompoundChildEdgeFactory cn, CompoundNodePair pair) :
		execution(public void ICompoundEdgeFactory.setPair(CompoundNodePair))
		&& target(cn) && args(pair) ;

	before(final ICompoundChildEdgeFactory cn, final CompoundNodePair pair) : setPair(cn, pair) {
		new Precondition(){{
			assertion(cn.isValidNodePair(pair), "valid node pair");
		}};
	}
	
	after(final ICompoundChildEdgeFactory cn, final CompoundNodePair pair) returning : setPair(cn, pair) {
		new Postcondition(){{
			assertion(cn.getParent() != null, "valid parent");
		}};
	}
	
	pointcut createEdge(ICompoundChildEdgeFactory cf) :
		execution(public ICompoundEdge ICompoundEdgeFactory.createEdge()) &&
		target(cf);
	
	before(final ICompoundChildEdgeFactory cf) : createEdge(cf) {
		new Precondition(){{
			assertion(cf.canCreateEdge(), "can create the edge");
		}};
	}
	
	public abstract pointcut allMethods(ICompoundChildEdgeFactory cn);
	
	after(final ICompoundChildEdgeFactory cn) : allMethods(cn) {
		new ClassInvariant(){{
			assertion(cn.getGraph() != null, "root is not null");
		}};
	}
	
}
