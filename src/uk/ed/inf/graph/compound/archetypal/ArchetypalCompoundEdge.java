package uk.ed.inf.graph.compound.archetypal;

import uk.ed.inf.graph.compound.base.BaseCompoundEdge;
import uk.ed.inf.graph.compound.base.BaseCompoundGraph;

public abstract class ArchetypalCompoundEdge extends BaseCompoundEdge {
	private final int index;
	private final ArchetypalChildCompoundGraph owningSubgraph;
	private final ArchetypalCompoundNode inNode; 
	private final ArchetypalCompoundNode outNode; 
	
	protected ArchetypalCompoundEdge(ArchetypalChildCompoundGraph owningSubgraph, int index, ArchetypalCompoundNode outNode, ArchetypalCompoundNode inNode){
		super();
		this.index = index;
		this.owningSubgraph = owningSubgraph;
		this.inNode = inNode;
		this.changeInEdge();
		this.outNode = outNode;
		this.changeOutNode();
	}
	
	@Override
	protected final ArchetypalCompoundNode getInNode(){
		return this.inNode;
	}
	
	@Override
	protected final ArchetypalCompoundNode getOutNode(){
		return this.outNode;
	}
	
	
	@Override
	public ArchetypalChildCompoundGraph getOwningChildGraph() {
		return this.owningSubgraph;
	}

	@Override
	public BaseCompoundGraph getGraph() {
		return this.owningSubgraph.getSuperGraph();
	}

	@Override
	public int getIndex() {
		return this.index;
	}

	public int compareTo(ArchetypalCompoundEdge o) {
		return this.index < o.getIndex() ? -1 : (this.index == o.getIndex() ? 0 : 1);
	}
}
