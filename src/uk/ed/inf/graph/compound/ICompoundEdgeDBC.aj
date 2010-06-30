package uk.ed.inf.graph.compound;

import uk.ac.ed.inf.designbycontract.ClassInvariant;

public aspect ICompoundEdgeDBC {

	pointcut allMethods(ICompoundEdge cn) :
		execution(public void ICompoundEdge.*(..))
		&& target(cn);
	
	after(final ICompoundEdge cn) : allMethods(cn) {
		new ClassInvariant(){{
			assertion(cn.getGraph() != null, "graph not null");
			assertion(cn.getIndex() >= 0, "index is a whole number");
			assertion(cn.getLevel() >= 0, "level is whole number");
			assertion(cn.getParent() != null, "parent is not null");
			assertion(cn.getChildCompoundGraph()!= null, "child graph is not null");
			assertion(cn.getChildCompoundGraph().getRoot().equals(cn), "this is root of its child graph");
			assertion(cn.getRoot() != null, "root is not null");
			assertion(cn.getConnectedNodes() != null, "connected nodes is not null");
			assertion(implies(cn.isSelfEdge(), cn.getConnectedNodes().isSelfEdge()), "consistent self edges");
			assertion(implies(cn.isSelfEdge(), cn.getConnectedNodes().getOutNode().equals(cn.getConnectedNodes().getInNode())), "self edge has same nodes");
			assertion(!cn.isNode(), "is not node");
			assertion(cn.isLink(), "is edge");
		}};
	}
}
