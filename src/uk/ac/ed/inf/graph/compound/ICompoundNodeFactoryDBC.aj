package uk.ac.ed.inf.graph.compound;

import uk.ac.ed.inf.designbycontract.Postcondition;
import uk.ac.ed.inf.designbycontract.Precondition;

public abstract aspect ICompoundNodeFactoryDBC {

	public pointcut createNode(ICompoundNodeFactory cnf) :
		execution(public ICompoundNode ICompoundNodeFactory.createNode())
		&& target(cnf);
	
	
	before(final ICompoundNodeFactory cnf) : createNode(cnf) {
		new Precondition(){{
			assertion(cnf.canCreateNode(), "can create node");
		}};
	}
	
	after(final ICompoundNodeFactory cnf) returning (final ICompoundNode retVal) : createNode(cnf) {
		new Postcondition(){{
			assertion(retVal != null, "created node exists");
			assertion(retVal.getAttribute() != null, "attribute assigned");
		}};
	}
}
