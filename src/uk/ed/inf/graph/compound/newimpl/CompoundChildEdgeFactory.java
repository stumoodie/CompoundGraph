package uk.ed.inf.graph.compound.newimpl;

import uk.ed.inf.graph.compound.ICompoundChildEdgeFactory;
import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundGraph;
import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.ICompoundNodePair;

public class CompoundChildEdgeFactory implements ICompoundChildEdgeFactory {
	private final ICompoundGraphElement parent;
	private final ICompoundGraphServices services;
	private ICompoundNode outNode;
	private ICompoundNode inNode;
	
	public CompoundChildEdgeFactory(ICompoundGraphElement parent, ICompoundGraphServices services){
		this.parent = parent;
		this.services = services;
	}
	
	@Override
	public boolean canCreateEdge() {
		return this.isValidNodePair(outNode, inNode);
	}

	@Override
	public ICompoundEdge createEdge() {
		return new CompoundEdge(this.parent, this.services.getIndexCounter().nextIndex(), this.outNode, this.inNode, this.services);
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
