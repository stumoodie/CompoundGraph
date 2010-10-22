package uk.ac.ed.inf.graph.compound;

import uk.ac.ed.inf.designbycontract.ClassInvariant;
import uk.ac.ed.inf.designbycontract.Precondition;

public abstract aspect ICompoundGraphDBC {
	
	public abstract pointcut theClass(ICompoundGraph obj);
	
	pointcut getEdge(ICompoundGraph cn, int idx) :
		execution(public ICompoundEdge ICompoundGraph.getEdge(int))
		&& target(cn) && args(idx) ;

	before(final ICompoundGraph cn, final int idx) : getEdge(cn, idx) {
		new Precondition(){{
			assertion(cn.containsEdge(idx), "contains edge");
		}};
	}
		
	pointcut getNode(ICompoundGraph cn, int idx) :
		execution(public ICompoundNode ICompoundGraph.getNode(int))
		&& target(cn) && args(idx) ;

	before(final ICompoundGraph cn, final int idx) : getNode(cn, idx) {
		new Precondition(){{
			assertion(cn.containsNode(idx), "contains node");
		}};
	}
		
	public pointcut edgeFactory(ICompoundGraph cn, ICompoundNode outNode, ICompoundNode inNode) :
		execution(public ICompoundEdgeFactory ICompoundGraph.edgeFactory(ICompoundNode, ICompoundNode))
		&& target(cn)
		&& args(outNode, inNode);
	
	before(final ICompoundGraph cn, final ICompoundNode outNode, final ICompoundNode inNode) : edgeFactory(cn, outNode, inNode) {
		new Precondition(){{
			assertion(outNode != null, "outNode is not null");
			assertion(inNode != null, "inNode is not null");
			assertion(outNode.getGraph().equals(cn), "outNode belongs to this graph");
			assertion(inNode.getGraph().equals(cn), "inNode belongs to this graph");
		}};
	}
	
	pointcut allMethods(ICompoundGraph cn) :
		execution(public void ICompoundGraph.*(..))
		&& target(cn);
	
	after(final ICompoundGraph cn) : allMethods(cn) {
		new ClassInvariant(){{
			assertion(cn.getRoot() != null, "root is not null");
			assertion(cn.getRoot().getChildCompoundGraph().equals(cn), "root node has this as child graph");
			assertion(cn.numEdges() >= 0, "num edges is a whole number");
			assertion(cn.numNodes() >= 0, "num nodes is a whole number");
			assertion(cn.numElements() >= 0, "num elements is a whole number");
			assertion(cn.numElements() == cn.numEdges() + cn.numNodes(), "element count consistent with nodes and edges");
		}};
	}
	
}
