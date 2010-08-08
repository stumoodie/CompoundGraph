package uk.ac.ed.inf.graph.compound;

import uk.ac.ed.inf.designbycontract.ClassInvariant;
import uk.ac.ed.inf.designbycontract.Precondition;

public abstract aspect IChildCompoundGraphDBC {
	
	public abstract pointcut theClass(IChildCompoundGraph obj);
	
	pointcut getEdge(IChildCompoundGraph cn, int idx) :
		execution(public ICompoundEdge IChildCompoundGraph.getEdge(int))
		&& target(cn) && args(idx) ;

	before(final IChildCompoundGraph cn, final int idx) : getEdge(cn, idx) {
		new Precondition(){{
			assertion(cn.containsEdge(idx), "contains edge");
		}};
	}
		
	pointcut getNode(IChildCompoundGraph cn, int idx) :
		execution(public ICompoundNode IChildCompoundGraph.getNode(int))
		&& target(cn) && args(idx) ;

	before(final IChildCompoundGraph cn, final int idx) : getNode(cn, idx) {
		new Precondition(){{
			assertion(cn.containsNode(idx), "contains node");
		}};
	}
		
	
	pointcut allMethods(IChildCompoundGraph cn) :
		execution(public void IChildCompoundGraph.*(..))
		&& target(cn);
	
	after(final IChildCompoundGraph cn) : allMethods(cn) {
		new ClassInvariant(){{
			assertion(cn.getSuperGraph() != null, "super graph not null");
			assertion(cn.getRoot() != null, "root is not null");
			assertion(cn.getRoot().getChildCompoundGraph().equals(cn), "root node has this as child graph");
			assertion(cn.numEdges() >= 0, "num edges is a whole number");
			assertion(cn.numNodes() >= 0, "num nodes is a whole number");
			assertion(cn.numElements() >= 0, "num elements is a whole number");
			assertion(cn.numElements() == cn.numEdges() + cn.numNodes(), "element count consistent with nodes and edges");
		}};
	}
	
}
