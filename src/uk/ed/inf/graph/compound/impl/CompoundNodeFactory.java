package uk.ed.inf.graph.compound.impl;

import uk.ed.inf.graph.basic.IBasicNodeFactory;
import uk.ed.inf.graph.colour.IColouredNodeFactory;
import uk.ed.inf.graph.colour.INodeColourHandler;

public class CompoundNodeFactory implements IBasicNodeFactory<CompoundNode, CompoundEdge>,
		IColouredNodeFactory<CompoundNode, CompoundEdge> {
	private final CompoundNode parent;
	private INodeColourHandler<CompoundNode, CompoundEdge> colour;
	
	public CompoundNodeFactory(CompoundNode parent){
		if(parent == null) throw new IllegalArgumentException("parent cannot be null");
		
		this.parent = parent;
	}
	
	public CompoundNode createNode() {
		if(this.colour == null) throw new IllegalStateException("Node colour must be set");
		
		int indexCntr = this.parent.getGraph().getNodeCounter().nextIndex();
		CompoundNode retVal = new CompoundNode(this.parent, this.colour, indexCntr);
		this.parent.getChildCigraph().addNewNode(retVal);
		return retVal;
	}

	@Override
	public CompoundGraph getGraph() {
		return parent.getGraph();
	}

	@Override
	public void setColourHandler(INodeColourHandler<CompoundNode, CompoundEdge> colour) {
		this.colour = colour;
	}

}
