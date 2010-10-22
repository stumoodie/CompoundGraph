package uk.ac.ed.inf.graph.compound;

import java.util.Iterator;

import uk.ac.ed.inf.designbycontract.ClassInvariant;
import uk.ac.ed.inf.designbycontract.Postcondition;
import uk.ac.ed.inf.designbycontract.Precondition;


public abstract aspect ICompoundNodeDBC extends ICompoundGraphElementDBC {
	
	pointcut addInEdge(ICompoundNode cn, ICompoundEdge inEdge) :
		execution(public void ICompoundNode.addInEdge(ICompoundEdge))
		&& target(cn)
		&& args(inEdge);
	

	private int previousInDegree;
	private int previousDegree;
	
	before(final ICompoundNode cn, final ICompoundEdge inEdge) : addInEdge(cn, inEdge) {
		new Precondition(){{
			assertion(inEdge != null, "inEdge cannot be null");
			assertion(inEdge.getGraph().equals(cn.getGraph()), "same graph");
			assertion(inEdge.getConnectedNodes().getInNode().equals(cn), "consistent in node");
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

	before(final ICompoundNode cn, final ICompoundEdge outEdge) : addOutEdge(cn, outEdge) {
		new Precondition(){{
			assertion(outEdge != null, "outEdge cannot be null");
			assertion(outEdge.getGraph().equals(cn.getGraph()), "same graph");
			assertion(outEdge.getConnectedNodes().getOutNode().equals(cn), "consistent out node");
		}};
		previousOutDegree = cn.getOutDegree();
		previousDegree = cn.getDegree();
	}

	after(final ICompoundNode cn, final ICompoundEdge outEdge) returning : addOutEdge(cn, outEdge) {
		new Postcondition(){{
			assertion(implies(!outEdge.isRemoved(), cn.getOutDegree() == previousOutDegree + 1), "Out degree incremented");
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

	
	after(final ICompoundGraphElement ce) : allMethods(ce) {
		final ICompoundNode cn = (ICompoundNode)ce;
		new ClassInvariant(){{
			assertion(cn.getOutDegree() >= 0, "out degree is a whole number");
			assertion(cn.getInDegree() >= 0, "in degree is a whole number");
			assertion(cn.getDegree() >= 0, "degree is a whole number");
			assertion(cn.isNode(), "is a node");
			assertion(!cn.isLink(), "is not an edge");
		}};
	}
}
