package uk.ac.ed.inf.graph.compound;

import uk.ac.ed.inf.designbycontract.ClassInvariant;

public abstract aspect ICompoundGraphElementDBC {

	public abstract pointcut allMethods(ICompoundGraphElement cn);
	
	after(final ICompoundGraphElement cn) : allMethods(cn) {
		new ClassInvariant(){{
			assertion(cn.getGraph() != null, "graph not null");
			assertion(cn.getIndex() >= 0, "index is a whole number");
			assertion(cn.getLevel() >= 0, "level is whole number");
			assertion(cn.getParent() != null, "parent is not null");
			assertion(cn.getAttribute() != null, "attribute is not null");
			assertion(cn.getChildCompoundGraph()!= null, "child graph is not null");
			assertion(cn.getChildCompoundGraph().getRoot().equals(cn), "this is root of its child graph");
			assertion(cn.getRoot() != null, "root is not null");
			assertion(cn.getAttribute().getCurrentElement() != null, "attribute curr element set");
			assertion(implies(!cn.isRemoved(), cn.getAttribute().getCurrentElement().equals(cn)), "attribute consistent");
		}};
	}
}
