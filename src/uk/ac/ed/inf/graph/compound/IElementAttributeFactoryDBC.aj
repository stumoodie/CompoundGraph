package uk.ac.ed.inf.graph.compound;

import uk.ac.ed.inf.designbycontract.Postcondition;
import uk.ac.ed.inf.designbycontract.Precondition;

public abstract aspect IElementAttributeFactoryDBC {

	public pointcut createAttribute(IElementAttributeFactory cnf) :
		execution(public IElementAttribute IElementAttributeFactory.createNode())
		&& target(cnf);
	
	
	before(final IElementAttributeFactory cnf) : createAttribute(cnf) {
		new Precondition(){{
			assertion(cnf.canCreateAttribute(), "can create attribute");
		}};
	}
	
	after(final IElementAttributeFactory cnf) returning (final IElementAttribute retVal) : createAttribute(cnf) {
		new Postcondition(){{
			assertion(retVal != null, "created node exists");
		}};
	}
}
