package uk.ed.inf.graph.compound.newimpl;

import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.IRootChildCompoundGraph;
import uk.ed.inf.graph.compound.IRootCompoundNode;

public class RootCompoundNode extends CommonCompoundNode implements IRootCompoundNode {
	private final RootChildCompoundGraph childGraph;
	private CompoundGraph graph;
	
	public RootCompoundNode(CompoundGraph graph, int index, ICompoundGraphServices services){
		super(index);
		this.childGraph = new RootChildCompoundGraph(this, services);
	}
	
	@Override
	public IRootChildCompoundGraph getChildCompoundGraph() {
		return this.childGraph;
	}

	@Override
	public int getLevel() {
		return ROOT_LEVEL;
	}

	@Override
	public ICompoundGraphElement getParent() {
		return this;
	}

	@Override
	public boolean isParent(ICompoundGraphElement parentNode) {
		return true;
	}

	@Override
	public CompoundGraph getGraph() {
		return this.graph;
	}

	@Override
	public ICompoundGraphElement getRoot() {
		return this;
	}

}
