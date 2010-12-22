package uk.ac.ed.inf.graph.compound;

import uk.ac.ed.inf.designbycontract.ClassInvariant;
import uk.ac.ed.inf.designbycontract.Precondition;

public abstract aspect ISubGraphDBC {
	
	public abstract pointcut allMethods(ISubGraph obj);
	
	pointcut getEdge(ISubCompoundGraph cn, int idx) :
		execution(public ICompoundEdge ISubGraph.getEdge(int))
		&& target(cn) && args(idx) ;

	before(final ISubGraph cn, final int idx) : getEdge(cn, idx) {
		new Precondition(){{
			assertion(cn.containsEdge(idx), "contains edge");
		}};
	}
		
	pointcut getNode(ISubGraph cn, int idx) :
		execution(public ICompoundNode ISubCompoundGraph.getNode(int))
		&& target(cn) && args(idx) ;

	before(final ISubGraph cn, final int idx) : getNode(cn, idx) {
		new Precondition(){{
			assertion(cn.containsNode(idx), "contains node");
		}};
	}
		
	pointcut getElement(ISubGraph cn, int idx) :
		execution(public ICompoundGraphElement ISubGraph.getElement(int))
		&& target(cn) && args(idx) ;

	before(final ISubGraph cn, final int idx) : getElement(cn, idx) {
		new Precondition(){{
			assertion(cn.containsElement(idx), "contains element");
		}};
	}
		
	after(final ISubGraph cn) : allMethods(cn) {
		new ClassInvariant(){{
			assertion(cn.getSuperGraph() != null, "super graph not null");
			assertion(cn.getNumEdges() >= 0, "num edges is a whole number");
			assertion(cn.getNumNodes() >= 0, "num nodes is a whole number");
			assertion(cn.numElements() >= 0, "num elements is a whole number");
			assertion(cn.numElements() == cn.getNumEdges() + cn.getNumNodes(), "element count consistent with nodes and edges");
		}};
	}
	
}
