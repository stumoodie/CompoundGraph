package uk.ac.ed.inf.graph.compound.newimpl;

import uk.ac.ed.inf.graph.compound.CompoundNodePair;
import uk.ac.ed.inf.graph.compound.ICompoundChildEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;

public class CompoundEdgeFactory implements ICompoundEdgeFactory {
	private final ICompoundGraph graph;
	private CompoundNodePair pair;
	
	public CompoundEdgeFactory(ICompoundGraph graph){
		this.graph = graph;
	}
	
	@Override
	public boolean canCreateEdge() {
		return isValidNodePair(this.pair);
	}

	@Override
	public ICompoundEdge createEdge() {
		ICompoundGraphElement lcmNode = this.getParent();
		ICompoundChildEdgeFactory childEdgeFact = lcmNode.getChildCompoundGraph().edgeFactory();
		childEdgeFact.setPair(this.pair);
		return childEdgeFact.createEdge();
	}

	@Override
	public CompoundNodePair getCurrentNodePair() {
		return this.pair;
	}

	@Override
	public ICompoundGraph getGraph() {
		return this.graph;
	}

	@Override
	public ICompoundGraphElement getParent() {
		ICompoundGraphElement lcmNode = null;
		if(this.pair != null){
			lcmNode = this.getGraph().getElementTree().getLowestCommonAncestor(this.pair.getOutNode(), this.pair.getInNode());
		}
		return lcmNode;
	}

	@Override
	public boolean isValidNodePair(CompoundNodePair nodePair) {
		return nodePair != null && nodePair.getGraph().equals(this.graph);
	}

	@Override
	public void setPair(CompoundNodePair pair) {
		this.pair = pair;
	}

}
