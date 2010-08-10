package uk.ac.ed.inf.graph.compound.newimpl;

import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ac.ed.inf.tree.GeneralTree;
import uk.ac.ed.inf.tree.ITree;

public class ChildCompoundGraph extends CommonChildCompoundGraph {
	private ITree<ICompoundGraphElement> elementTree;

	public ChildCompoundGraph(ICompoundGraphElement rootElement) {
		super();
		this.elementTree = new GeneralTree<ICompoundGraphElement>(rootElement);
	}

	@Override
	protected void notifyCopyOperationComplete(ISubCompoundGraph originalSubgraph, ISubCompoundGraph copiedSubgraph) {

	}

	@Override
	protected void notifyMoveOperationComplete(ISubCompoundGraph originalSubgraph, ISubCompoundGraph movedSubgraph) {

	}

	@Override
	public ICompoundGraphElement getRoot() {
		return this.getElementTree().getRootNode();
	}

	@Override
	public ITree<ICompoundGraphElement> getElementTree() {
		return this.elementTree;
	}

}