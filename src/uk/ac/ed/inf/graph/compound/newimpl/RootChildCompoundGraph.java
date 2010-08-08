package uk.ac.ed.inf.graph.compound.newimpl;

import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.IRootChildCompoundGraph;
import uk.ac.ed.inf.graph.compound.IRootCompoundNode;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ac.ed.inf.tree.GeneralTree;
import uk.ac.ed.inf.tree.ITree;

public class RootChildCompoundGraph extends CommonChildCompoundGraph implements IRootChildCompoundGraph {
	private final ITree<ICompoundGraphElement> elementTree;

	public RootChildCompoundGraph(IRootCompoundNode rootCompoundNode) {
		super();
		this.elementTree = new GeneralTree<ICompoundGraphElement>(rootCompoundNode);
	}

	@Override
	public IRootCompoundNode getRoot() {
		return (IRootCompoundNode)this.elementTree.getRootNode();
	}

	@Override
	public ICompoundGraph getSuperGraph() {
		return this.getRoot().getGraph();
	}

	@Override
	protected void notifyCopyOperationComplete(ISubCompoundGraph originalSubgraph, ISubCompoundGraph copiedSubgraph) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void notifyMoveOperationComplete(ISubCompoundGraph originalSubgraph, ISubCompoundGraph movedSubgraph) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ITree<ICompoundGraphElement> getElementTree() {
		return this.elementTree;
	}
}
