package uk.ed.inf.graph.compound.newimpl;

import uk.ed.inf.graph.compound.ICompoundChildEdgeFactory;
import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ed.inf.graph.compound.ICompoundGraph;
import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.ICompoundNodePair;

public class CompoundEdgeFactory implements ICompoundEdgeFactory {
	private final ICompoundGraph graph;
	private ICompoundNode outNode;
	private ICompoundNode inNode;
	
	public CompoundEdgeFactory(ICompoundGraph graph){
		this.graph = graph;
	}
	
	@Override
	public boolean canCreateEdge() {
		return this.inNode != null && this.outNode != null && isValidNodePair(this.outNode, this.inNode);
	}

	@Override
	public ICompoundEdge createEdge() {
		
		ICompoundGraphElement lcmNode = this.getParent();
		ICompoundChildEdgeFactory childEdgeFact = lcmNode.getChildCompoundGraph().edgeFactory();
		childEdgeFact.setPair(outNode, inNode);
		return childEdgeFact.createEdge();
	}

	@Override
	public ICompoundNodePair getCurrentNodePair() {
		return new CompoundNodePair(outNode, inNode);
	}

	@Override
	public ICompoundGraph getGraph() {
		return this.graph;
	}

	@Override
	public ICompoundGraphElement getParent() {
		ICompoundGraphElement lcmNode = this.getGraph().getElementTree().getLowestCommonAncestor(this.outNode, this.inNode);
		return lcmNode;
	}

	@Override
	public boolean isValidNodePair(ICompoundNode outNode, ICompoundNode inNode) {
		return this.outNode != null && this.inNode != null && this.outNode.getGraph().equals(this.graph)
				&& this.outNode.getGraph().equals(this.graph);
	}

	@Override
	public void setPair(ICompoundNode outNode, ICompoundNode inNode) {
		this.outNode = outNode;
		this.inNode = inNode;
	}

}
