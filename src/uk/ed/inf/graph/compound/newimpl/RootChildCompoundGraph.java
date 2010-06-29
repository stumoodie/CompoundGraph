package uk.ed.inf.graph.compound.newimpl;

import uk.ed.inf.graph.compound.ICompoundGraph;
import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.IRootChildCompoundGraph;
import uk.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ed.inf.tree.ITree;

public class RootChildCompoundGraph extends CommonChildCompoundGraph implements IRootChildCompoundGraph {
	private RootCompoundNode rootNode;

	public RootChildCompoundGraph(RootCompoundNode rootCompoundNode, ICompoundGraphServices services) {
		super(services);
		this.rootNode = rootCompoundNode;
	}

	@Override
	public RootCompoundNode getRoot() {
		return this.rootNode;
	}

	@Override
	public ICompoundGraph getSuperGraph() {
		return this.rootNode.getGraph();
	}

	@Override
	protected boolean hasPassedAdditionalValidation() {
		return true;
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
		return rootNode.getGraph().getElementTree();
	}
}
