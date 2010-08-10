package uk.ac.ed.inf.graph.compound.newimpl;

import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.IElementAttribute;
import uk.ac.ed.inf.graph.compound.IRootCompoundNode;

public class RootCompoundNode extends CommonCompoundNode implements IRootCompoundNode {
	private final RootChildCompoundGraph childGraph;
	private final CompoundGraph graph;
	
	public RootCompoundNode(CompoundGraph graph, int index, IElementAttribute attribute){
		super(index, attribute);
		this.childGraph = new RootChildCompoundGraph(this);
		this.graph = graph;
	}
	
	@Override
	public RootChildCompoundGraph getChildCompoundGraph() {
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
