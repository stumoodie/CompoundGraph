package uk.ed.inf.graph.compound.impl;

import java.util.Iterator;

import uk.ed.inf.graph.compound.base.BaseCompoundEdge;
import uk.ed.inf.graph.compound.base.BaseCompoundNode;
import uk.ed.inf.graph.compound.base.BaseSubCompoundGraph;
import uk.ed.inf.graph.util.impl.DirectedEdgeSet;
import uk.ed.inf.graph.util.impl.NodeSet;


public class SubCompoundGraph extends BaseSubCompoundGraph {
	private final CompoundGraph superGraph;
	
	SubCompoundGraph(CompoundGraph superGraph){
		super();
		this.superGraph = superGraph;
		createEdgeSet(new DirectedEdgeSet<BaseCompoundNode, BaseCompoundEdge>());
		createNodeSet(new NodeSet<BaseCompoundNode, BaseCompoundEdge>());
	}

	@Override
	public CompoundGraph getSuperGraph() {
		return this.superGraph;
	}

	
}
