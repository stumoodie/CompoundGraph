package uk.ed.inf.graph.compound.base;

import uk.ed.inf.graph.compound.IRootCompoundNode;

public class BaseRootCompoundNode extends BaseCompoundNode implements IRootCompoundNode {
	private BaseRootChildCompoundGraph childCompoundGraph;

	public BaseRootCompoundNode(BaseCompoundGraph superGraph, int index) {
		super(superGraph, index);
	}

	public BaseRootCompoundNode(BaseCompoundGraphElement parent, int index){
		super(parent, index);
	}
	@Override
	public BaseRootChildCompoundGraph getChildCompoundGraph(){
		return this.childCompoundGraph;
	}

	@Override
	protected void createChildCompoundGraph(BaseCompoundNode rootNode){
		this.childCompoundGraph = new BaseRootChildCompoundGraph(this);
	}
}
