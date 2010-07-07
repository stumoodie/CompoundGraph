package uk.ed.inf.graph.compound.newimpl;

import uk.ed.inf.graph.compound.CompoundNodePair;
import uk.ed.inf.graph.compound.ICompoundChildEdgeFactory;
import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundGraph;
import uk.ed.inf.graph.compound.ICompoundGraphElement;

public class CompoundChildEdgeFactory implements ICompoundChildEdgeFactory {
	private final ICompoundGraphElement parent;
	private CompoundNodePair pair;
	
	public CompoundChildEdgeFactory(ICompoundGraphElement parent){
		this.parent = parent;
	}
	
	@Override
	public boolean canCreateEdge() {
		return this.isValidNodePair(this.pair);
	}

	@Override
	public ICompoundEdge createEdge() {
		ICompoundEdge retVal = new CompoundEdge(this.parent, CompoundGraph.getIndexCounter(this.getGraph()).nextIndex(), this.pair.getOutNode(), this.pair.getInNode());
		parent.getChildCompoundGraph().addEdge(retVal);
		return retVal;
	}

	@Override
	public CompoundNodePair getCurrentNodePair() {
		return this.pair;
	}

	@Override
	public ICompoundGraph getGraph() {
		return parent.getGraph();
	}

	@Override
	public ICompoundGraphElement getParent() {
		return parent;
	}

	@Override
	public boolean isValidNodePair(CompoundNodePair nodePair) {
		boolean retVal = false;
		if(nodePair != null){
			retVal = this.parent.getChildCompoundGraph().getElementTree().getLowestCommonAncestor(nodePair.getOutNode(), nodePair.getInNode()).equals(parent);
		}
		return retVal;
	}

	@Override
	public void setPair(CompoundNodePair nodePair) {
		this.pair = nodePair;
	}
}
