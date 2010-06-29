package uk.ed.inf.graph.compound.newimpl;

import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ed.inf.graph.compound.ICompoundGraph;
import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.ICompoundNodePair;

public class CompoundEdgeFactory implements ICompoundEdgeFactory {
	private final ICompoundGraph graph;
	private final ICompoundGraphServices services;
	private ICompoundNode outNode;
	private ICompoundNode inNode;
	
	public CompoundEdgeFactory(ICompoundGraph graph, ICompoundGraphServices services){
		this.graph = graph;
		this.services = services;
	}
	
	@Override
	public boolean canCreateEdge() {
		return this.inNode != null && this.outNode != null && isValidNodePair(this.outNode, this.inNode);
	}

	@Override
	public ICompoundEdge createEdge() {
		
		ICompoundGraphElement lcmNode = this.getParent();
		int cntr = this.services.getIndexCounter().nextIndex();
		ICompoundEdge newEdge = new CompoundEdge(lcmNode, cntr, this.outNode, this.inNode, this.services); 
//		this.getGraph().notifyNewEdge(newEdge);	//TODO:
//		this.getParent().getChildCompoundGraph().notifyNewEdge(newEdge);
		return newEdge;
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
