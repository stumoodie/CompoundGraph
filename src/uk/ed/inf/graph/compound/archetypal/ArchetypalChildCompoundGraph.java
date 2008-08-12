package uk.ed.inf.graph.compound.archetypal;

import uk.ed.inf.graph.compound.base.BaseChildCompoundGraph;
import uk.ed.inf.graph.compound.base.BaseCompoundEdge;
import uk.ed.inf.graph.compound.base.BaseCompoundNode;
import uk.ed.inf.graph.compound.base.BaseGraphCopyBuilder;
import uk.ed.inf.graph.util.impl.DirectedEdgeSet;
import uk.ed.inf.graph.util.impl.NodeSet;

public abstract class ArchetypalChildCompoundGraph extends BaseChildCompoundGraph {
	private final ArchetypalCompoundNode root;
	
	protected ArchetypalChildCompoundGraph(ArchetypalCompoundNode root, BaseGraphCopyBuilder builder){
		super(builder);
		if(root == null) throw new IllegalArgumentException("root cannot be null");
		
		this.root = root;
		createEdgeSet(new DirectedEdgeSet<BaseCompoundNode, BaseCompoundEdge>());
		createNodeSet(new NodeSet<BaseCompoundNode, BaseCompoundEdge>());
	}
	
	public ArchetypalCompoundNode getRootNode() {
		return this.root;
	}

}
