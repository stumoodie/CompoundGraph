package uk.ac.ed.inf.graph.compound;

import uk.ac.ed.inf.designbycontract.ClassInvariant;
import uk.ac.ed.inf.designbycontract.Postcondition;
import uk.ac.ed.inf.designbycontract.Precondition;

public abstract aspect ICompoundGraphCopyBuilderDBC {
	
	public pointcut makeCopy(ICompoundGraphCopyBuilder cb) : 
		execution(public void ICompoundGraphCopyBuilder.makeCopy())
		&& target(cb);
	
	before(final ICompoundGraphCopyBuilder cb) : makeCopy(cb) {
		new Precondition(){{
			assertion(cb.canCopyHere(), "can copy");
		}};
	}
	
	after(final ICompoundGraphCopyBuilder cb) returning : makeCopy(cb) {
		new Postcondition(){{
			assertion(cb.getCopiedComponents() != null, "copied components exists");
			assertion(cb.getCopiedComponents().getSuperGraph().equals(cb.getDestinationChildGraph().getSuperGraph()), "copied components have same desnt graph");
		}};
	}
	
	public pointcut canCopyHere(ICompoundGraphCopyBuilder cb) :
		execution(public boolean ICompoundGraphCopyBuilder.canCopyHere())
		&& target(cb);
	
	
	after(final ICompoundGraphCopyBuilder cb) returning(final boolean retVal) : canCopyHere(cb) {
		new Postcondition(){{
			assertion(implies(retVal, cb.getSourceSubgraph() != null
					&& cb.getSourceSubgraph().isConsistentSnapShot()
					&& !cb.getSourceSubgraph().hasOrphanedEdges()), "can copy implies valid source");
			assertion(implies(retVal, cb.getDestinationChildGraph() != null), "can copy implies destn set");
		}};
	}
	
	public abstract pointcut allMethods(ICompoundGraphCopyBuilder obj);
	
	after(final ICompoundGraphCopyBuilder cn) : allMethods(cn) {
		new ClassInvariant(){{
			assertion(implies(cn.canCopyHere(), cn.getSourceSubgraph() != null), "can copy implies non-void subgraph");
		}};
	}
	
}
