package uk.ac.ed.inf.graph.compound;

import uk.ac.ed.inf.designbycontract.ClassInvariant;
import uk.ac.ed.inf.designbycontract.Precondition;

public abstract aspect ISubCompoundGraphDBC {
	
	public abstract pointcut allMethods(ISubCompoundGraph obj);
	
	pointcut getEdge(ISubCompoundGraph cn, int idx) :
		execution(public ICompoundEdge ISubCompoundGraph.getEdge(int))
		&& target(cn) && args(idx) ;

	before(final ISubCompoundGraph cn, final int idx) : getEdge(cn, idx) {
		new Precondition(){{
			assertion(cn.containsEdge(idx), "contains edge");
		}};
	}
		
	pointcut getNode(ISubCompoundGraph cn, int idx) :
		execution(public ICompoundNode ISubCompoundGraph.getNode(int))
		&& target(cn) && args(idx) ;

	before(final ISubCompoundGraph cn, final int idx) : getNode(cn, idx) {
		new Precondition(){{
			assertion(cn.containsNode(idx), "contains node");
		}};
	}
		
	pointcut getElement(ISubCompoundGraph cn, int idx) :
		execution(public ICompoundGraphElement ISubCompoundGraph.getElement(int))
		&& target(cn) && args(idx) ;

	before(final ISubCompoundGraph cn, final int idx) : getElement(cn, idx) {
		new Precondition(){{
			assertion(cn.containsElement(idx), "contains element");
		}};
	}
		
	after(final ISubCompoundGraph cn) : allMethods(cn) {
		new ClassInvariant(){{
			assertion(cn.getSuperGraph() != null, "super graph not null");
			assertion(cn.getNumTopEdges() >= 0, "num top edges is a whole number");
			assertion(cn.getNumTopNodes() >= 0, "num top nodes is a whole number");
			assertion(cn.numTopElements() >= 0, "num top elements is a whole number");
			assertion(cn.numTopElements() == cn.getNumTopNodes() + cn.getNumTopEdges(), "num top nodes is a whole number");
			assertion(cn.getNumEdges() >= 0, "num edges is a whole number");
			assertion(cn.getNumNodes() >= 0, "num nodes is a whole number");
			assertion(cn.numElements() >= 0, "num elements is a whole number");
			assertion(cn.numElements() == cn.getNumEdges() + cn.getNumNodes(), "element count consistent with nodes and edges");
		}};
	}
	
}
