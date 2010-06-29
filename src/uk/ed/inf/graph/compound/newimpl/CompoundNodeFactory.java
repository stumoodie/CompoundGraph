package uk.ed.inf.graph.compound.newimpl;

import uk.ed.inf.graph.compound.ICompoundGraph;
import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.ICompoundNodeFactory;

public class CompoundNodeFactory implements ICompoundNodeFactory {
	private final ICompoundGraphElement parent;
	private final ICompoundElementRegistration registration;

	public CompoundNodeFactory(ICompoundGraphElement parent, ICompoundElementRegistration registration){
		this.parent = parent;
		this.registration = registration;
	}
	
	@Override
	public ICompoundNode createNode() {
		int nodeIndex = CompoundGraph.getIndexCounter(this.getGraph()).nextIndex();
		CompoundNode retVal = new CompoundNode(parent, nodeIndex);
		this.registration.registerNode(retVal);
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
