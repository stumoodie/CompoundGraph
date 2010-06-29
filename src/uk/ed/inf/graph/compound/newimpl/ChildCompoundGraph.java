package uk.ed.inf.graph.compound.newimpl;

import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ed.inf.tree.GeneralTree;
import uk.ed.inf.tree.ITree;

public class ChildCompoundGraph extends CommonChildCompoundGraph {
	private ITree<ICompoundGraphElement> elementTree;

	public ChildCompoundGraph(ICompoundGraphElement rootElement) {
		super();
		this.elementTree = new GeneralTree<ICompoundGraphElement>(rootElement);
	}

	@Override
	protected boolean hasPassedAdditionalValidation() {
		return true;
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
