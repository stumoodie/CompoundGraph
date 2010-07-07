package uk.ed.inf.graph.compound;

import java.util.Iterator;

import uk.ac.ed.inf.designbycontract.ClassInvariant;
import uk.ac.ed.inf.designbycontract.Postcondition;
import uk.ac.ed.inf.designbycontract.Precondition;


public aspect ICompoundNodeDBC {
	
	pointcut addInEdge(ICompoundNode cn, ICompoundEdge inEdge) :
		execution(public void ICompoundNode.addInEdge(ICompoundEdge))
		&& target(cn)
		&& args(inEdge);
	

	private int previousInDegree;
	private int previousDegree;
	
	before(ICompoundNode cn, final ICompoundEdge inEdge) : addInEdge(cn, inEdge) {
		new Precondition(){{
			assertion(inEdge != null, "inEdge cannot be null");
		}};
		previousInDegree = cn.getInDegree();
		previousDegree = cn.getDegree();
	}
	
	after(final ICompoundNode cn, final ICompoundEdge inEdge) returning : addInEdge(cn, inEdge) {
		new Postcondition(){{
			assertion(implies(!inEdge.isRemoved(), cn.getInDegree() == previousInDegree + 1), "In degree incremented");
			assertion(implies(inEdge.isRemoved(), cn.getDegree() == previousDegree), "Degree not incremented if removed");
		}};
	}
		
	pointcut addOutEdge(ICompoundNode cn, ICompoundEdge inEdge) :
		execution(public void ICompoundNode.addOutEdge(ICompoundEdge))
		&& target(cn)
		&& args(inEdge);
	
	private int previousOutDegree;

	before(ICompoundNode cn, final ICompoundEdge outEdge) : addOutEdge(cn, outEdge) {
		new Precondition(){{
			assertion(outEdge != null, "outEdge cannot be null");
		}};
		previousOutDegree = cn.getOutDegree();
		previousDegree = cn.getDegree();
	}

	after(final ICompoundNode cn, final ICompoundEdge outEdge) returning : addOutEdge(cn, outEdge) {
		new Postcondition(){{
			assertion(implies(!outEdge.isRemoved(), cn.getInDegree() == previousInDegree + 1), "Out degree incremented");
			assertion(implies(outEdge.isRemoved(), cn.getDegree() == previousDegree), "Degree not incremented if removed");
		}};
	}
		
//	SortedSet<ICompoundEdge> getInEdgesFrom(ICompoundNode outNode);  
	pointcut getInEdgesFrom(ICompoundNode cn, ICompoundNode outNode) :
		execution(public Iterator<ICompoundEdge> getInEdgesFrom(ICompoundNode))
		&& target(cn)
		&& args(outNode);
	
	before(final ICompoundNode cn, final ICompoundNode outNode) : getInEdgesFrom(cn, outNode) {
		new Precondition(){{
			assertion(cn.hasInEdgeFrom(outNode), "has outNode");
		}};
	}

	after(final ICompoundNode cn, ICompoundNode outEdge) returning (final Iterator<ICompoundEdge> retVal) : getInEdgesFrom(cn, outEdge) {
		new Postcondition(){{
			assertion(retVal != null, "retVal is not null");
			assertion(retVal.hasNext(), "At least one edge found");
		}};
	}

//	SortedSet<ICompoundEdge> getOutEdgesTo(ICompoundNode inNode);  
	pointcut getOutEdgesTo(ICompoundNode cn, ICompoundNode inNode) :
		execution(public Iterator<ICompoundEdge> getOutEdgesTo(ICompoundNode))
		&& target(cn)
		&& args(inNode);
	
	before(final ICompoundNode cn, final ICompoundNode inNode) : getOutEdgesTo(cn, inNode) {
		new Precondition(){{
			assertion(cn.hasOutEdgeTo(inNode), "has inNode");
		}};
	}

	after(final ICompoundNode cn, ICompoundNode inNode) returning (final Iterator<ICompoundEdge> retVal) : getOutEdgesTo(cn, inNode) {
		new Postcondition(){{
			assertion(retVal != null, "retVal is not null");
			assertion(retVal.hasNext(), "At least one edge found");
		}};
	}

	
//	SortedSet<ICompoundEdge> getEdgesWith(ICompoundNode other);
	pointcut getEdgesWith(ICompoundNode cn, ICompoundNode other) :
		execution(public Iterator<ICompoundEdge> getEdgesWith(ICompoundNode))
		&& target(cn)
		&& args(other);
	
	before(final ICompoundNode cn, final ICompoundNode other) : getEdgesWith(cn, other) {
		new Precondition(){{
			assertion(cn.hasEdgeWith(other), "has other node");
		}};
	}

	after(final ICompoundNode cn, ICompoundNode other) returning (final Iterator<ICompoundEdge> retVal) : getEdgesWith(cn, other) {
		new Postcondition(){{
			assertion(retVal != null, "retVal is not null");
			assertion(retVal.hasNext(), "At least one edge found");
		}};
	}

	
	pointcut allMethods(ICompoundNode cn) :
		execution(public void ICompoundNode.*(..))
		&& target(cn);
	
	after(final ICompoundNode cn) : allMethods(cn) {
		new ClassInvariant(){{
			assertion(cn.getGraph() != null, "graph not null");
			assertion(cn.getIndex() >= 0, "index is a whole number");
			assertion(cn.getLevel() >= 0, "level is whole number");
			assertion(cn.getOutDegree() >= 0, "out degree is a whole number");
			assertion(cn.getInDegree() >= 0, "in degree is a whole number");
			assertion(cn.getDegree() >= 0, "degree is a whole number");
			assertion(cn.getParent() != null, "parent is not null");
			assertion(cn.getChildCompoundGraph()!= null, "child graph is not null");
			assertion(cn.getChildCompoundGraph().getRoot().equals(cn), "this is root of its child graph");
			assertion(cn.getRoot() != null, "root is not null");
			assertion(cn.isNode(), "is node");
			assertion(!cn.isLink(), "is not edge");
		}};
	}
}
