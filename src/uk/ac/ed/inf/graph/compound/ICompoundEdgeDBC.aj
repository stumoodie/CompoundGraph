package uk.ac.ed.inf.graph.compound;

import uk.ac.ed.inf.designbycontract.ClassInvariant;

public abstract aspect ICompoundEdgeDBC extends ICompoundGraphElementDBC {

//	public abstract pointcut allMethodsICompoundEdge(ICompoundEdge cn);
	
	@Override
	after(final ICompoundGraphElement ce) : allMethods(ce) {
		final ICompoundEdge cn = (ICompoundEdge)ce;
		new ClassInvariant(){{
			assertion(cn.getConnectedNodes() != null, "connected nodes is not null");
			assertion(implies(cn.isSelfEdge(), cn.getConnectedNodes().isSelfEdge()), "consistent self edges");
			assertion(implies(cn.isSelfEdge(), cn.getConnectedNodes().getOutNode().equals(cn.getConnectedNodes().getInNode())), "self edge has same nodes");
			assertion(!cn.isNode(), "is not a node");
			assertion(cn.isEdge(), "is edge");
			assertion(implies(!cn.isRemoved(), !cn.getConnectedNodes().getOutNode().isRemoved() && !cn.getConnectedNodes().getInNode().isRemoved()), "incident nodes not removed");
		}};
	}
}
