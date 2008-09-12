package uk.ed.inf.graph.compound.base;

import uk.ed.inf.graph.compound.ICompoundChildEdgeFactory;

public abstract class BaseChildCompoundEdgeFactory implements ICompoundChildEdgeFactory<BaseCompoundNode, BaseCompoundEdge> {
	private final BaseCompoundNode parentNode;
	private BaseCompoundNode inNode;
	private BaseCompoundNode outNode;
	
	protected BaseChildCompoundEdgeFactory(BaseCompoundNode parentNode){
		this.parentNode = parentNode;
	}
	
	public BaseChildCompoundGraph getOwningChildGraph() {
		return this.parentNode.getChildCompoundGraph();
	}

	public boolean isValidNodePair(BaseCompoundNode outNode, BaseCompoundNode inNode) {
		boolean retVal = false;
		if(outNode != null  && inNode != null){
			retVal = parentNode.getGraph().getLcaNode(outNode, inNode).equals(this.parentNode);
		}
		return retVal;
	}

	public void setPair(BaseCompoundNode outNode, BaseCompoundNode inNode) {
		this.inNode = inNode;
		this.outNode = outNode;
	}

	public BaseCompoundEdge createEdge() {
		int idx = this.getGraph().getEdgeCounter().nextIndex();
		BaseCompoundEdge newCompoundEdge = newEdge(this.getOwningChildGraph(), idx, this.outNode, this.inNode);
		this.getOwningChildGraph().addNewEdge(newCompoundEdge) ;
		return newCompoundEdge ;
	}

	protected abstract BaseCompoundEdge newEdge(BaseChildCompoundGraph owningChildGraph,
					int edgeIndex, BaseCompoundNode outNode, BaseCompoundNode inNode);
	
	public BaseCompoundGraph getGraph() {
		return this.parentNode.getGraph();
	}

}
