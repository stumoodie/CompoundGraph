package uk.ed.inf.graph.compound.base;

import java.util.Iterator;

import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.IRootChildCompoundGraph;
import uk.ed.inf.graph.compound.ISubCompoundGraph;

public class BaseRootChildCompoundGraph extends BaseChildCompoundGraph implements IRootChildCompoundGraph {

	protected BaseRootChildCompoundGraph(BaseRootCompoundNode rootNode){
		super();
		
	}
	
	@Override
	public abstract BaseRootCompoundNode getRoot();

	@Override
	public BaseChildCompoundEdgeFactory edgeFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean hasPassedAdditionalValidation() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BaseCompoundNodeFactory nodeFactory() {
		// TODO Auto-generated method stub
		return null;
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
	protected void notifyNewEdge(ICompoundEdge newEdge) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void notifyNewNode(ICompoundNode newNode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Iterator<ICompoundGraphElement> elementIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int numElements() {
		// TODO Auto-generated method stub
		return 0;
	}

}
