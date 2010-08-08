package uk.ac.ed.inf.graph.compound.newimpl;

import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.ICompoundNodeFactory;

public class CompoundNodeFactory implements ICompoundNodeFactory {
	private final ICompoundGraphElement parent;

	public CompoundNodeFactory(ICompoundGraphElement parent){
		this.parent = parent;
	}
	
	@Override
	public ICompoundNode createNode() {
		int nodeIndex = CompoundGraph.getIndexCounter(this.getGraph()).nextIndex();
		CompoundNode retVal = new CompoundNode(parent, nodeIndex);
		parent.getChildCompoundGraph().addNode(retVal);
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
