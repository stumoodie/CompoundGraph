package uk.ac.ed.inf.graph.compound;

import uk.ac.ed.inf.designbycontract.ClassInvariant;
import uk.ac.ed.inf.designbycontract.Postcondition;
import uk.ac.ed.inf.designbycontract.Precondition;

public abstract aspect ICompoundGraphMoveBuilderDBC {
	
	public pointcut makeMove(ICompoundGraphMoveBuilder cb) : 
		execution(public void ICompoundGraphMoveBuilder.makeMove())
		&& target(cb);
	
	before(final ICompoundGraphMoveBuilder cb) : makeMove(cb) {
		new Precondition(){{
			assertion(cb.canMoveHere(), "can move");
		}};
	}
	
	after(final ICompoundGraphMoveBuilder cb) returning : makeMove(cb) {
		new Postcondition(){{
			assertion(cb.getMovedComponents() != null, "moved components exists");
			assertion(cb.getMovedComponents().getSuperGraph().equals(cb.getDestinationChildGraph().getSuperGraph()), "moved components have same desnt graph");
		}};
	}
	
	public pointcut canMoveHere(ICompoundGraphMoveBuilder cb) :
		execution(public boolean ICompoundGraphMoveBuilder.canMoveHere())
		&& target(cb);
	
	
	after(final ICompoundGraphMoveBuilder cb) returning(final boolean retVal) : canMoveHere(cb) {
		new Postcondition(){{
			assertion(implies(retVal, cb.getSourceSubgraph() != null
					&& cb.getSourceSubgraph().isConsistentSnapShot()
					&& cb.getSourceSubgraph().isInducedSubgraph()), "can move implies valid source");
			assertion(implies(retVal, cb.getDestinationChildGraph() != null), "can move implies destn set");
		}};
	}
	
	public abstract pointcut allMethods(ICompoundGraphMoveBuilder obj);
	
	after(final ICompoundGraphMoveBuilder cn) : allMethods(cn) {
		new ClassInvariant(){{
			assertion(implies(cn.canMoveHere(), cn.getSourceSubgraph() != null), "can move implies non-void subgraph");
		}};
	}
	
}
