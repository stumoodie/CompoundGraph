package uk.ac.ed.inf.graph.compound;

import uk.ac.ed.inf.designbycontract.ClassInvariant;
import uk.ac.ed.inf.designbycontract.Postcondition;
import uk.ac.ed.inf.designbycontract.Precondition;

public abstract aspect ICompoundEdgeFactoryDBC {
	
	public abstract pointcut theClass(ICompoundEdgeFactory obj);
	
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
	
	pointcut allMethods(ICompoundEdgeFactory cn) :
		execution(public void ICompoundEdgeFactory.*(..))
		&& target(cn);
	
	after(final ICompoundEdgeFactory cn) : allMethods(cn) {
		new ClassInvariant(){{
			assertion(cn.getGraph() != null, "root is not null");
		}};
	}
	
}
