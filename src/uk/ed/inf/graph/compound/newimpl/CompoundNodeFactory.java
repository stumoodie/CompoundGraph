package uk.ed.inf.graph.compound.newimpl;

import uk.ed.inf.graph.compound.ICompoundGraph;
import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.ICompoundNodeFactory;

public class CompoundNodeFactory implements ICompoundNodeFactory {
	private final ICompoundGraphElement parent;
	private final ICompoundGraphServices services;

	public CompoundNodeFactory(ICompoundGraphElement parent, ICompoundGraphServices services){
		this.parent = parent;
		this.services = services;
	}
	
	@Override
	public ICompoundNode createNode() {
		int nodeIndex = this.services.getIndexCounter().nextIndex();
		CompoundNode retVal = new CompoundNode(parent, nodeIndex, this.services);
		return retVal;
	}

	@Override
	public ICompoundGraph getGraph() {
		return this.parent.getGraph();
	}

	@Override
	public ICompoundGraphElement getParentNode() {
		return this.parent;
	}

}
