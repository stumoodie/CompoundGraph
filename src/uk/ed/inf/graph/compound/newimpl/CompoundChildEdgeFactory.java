package uk.ed.inf.graph.compound.newimpl;

import uk.ed.inf.graph.compound.ICompoundChildEdgeFactory;
import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundGraph;
import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.ICompoundNodePair;

public class CompoundChildEdgeFactory implements ICompoundChildEdgeFactory {
	private final ICompoundGraphElement parent;
	private ICompoundNode outNode;
	private ICompoundNode inNode;
	
	public CompoundChildEdgeFactory(ICompoundGraphElement parent){
		this.parent = parent;
	}
	
	@Override
	public boolean canCreateEdge() {
		return this.isValidNodePair(outNode, inNode);
	}

	@Override
	public ICompoundEdge createEdge() {
		ICompoundEdge retVal = new CompoundEdge(this.parent, CompoundGraph.getIndexCounter(this.getGraph()).nextIndex(), this.outNode, this.inNode);
		parent.getChildCompoundGraph().addEdge(retVal);
		return retVal;
	}

	@Override
	public ICompoundNodePair getCurrentNodePair() {
		return new CompoundNodePair(outNode, inNode);
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
	public boolean isValidNodePair(ICompoundNode outNode, ICompoundNode inNode) {
		boolean retVal = false;
		if(outNode != null  && inNode != null){
			retVal = this.parent.getChildCompoundGraph().getElementTree().getLowestCommonAncestor(outNode, inNode).equals(parent);
		}
		return retVal;
	}

	@Override
	public void setPair(ICompoundNode outNode, ICompoundNode inNode) {
		this.outNode = outNode;
		this.inNode = inNode;
	}

}
