package uk.ed.inf.graph.compound.impl;

import uk.ed.inf.graph.basic.IBasicNodeFactory;
import uk.ed.inf.graph.colour.IColouredNodeFactory;
import uk.ed.inf.graph.colour.INodeColourHandler;
import uk.ed.inf.graph.colour.INodeColourHandlerFactory;

public class CompoundNodeFactory implements IBasicNodeFactory<CompoundNode, CompoundEdge>,
		IColouredNodeFactory<CompoundNode, CompoundEdge> {
	private final CompoundNode parent;
	private INodeColourHandlerFactory<CompoundNode, CompoundEdge> colourFactory;
	
	public CompoundNodeFactory(CompoundNode parent){
		if(parent == null) throw new IllegalArgumentException("parent cannot be null");
		
		this.parent = parent;
	}
	
	public CompoundNode createNode() {
		if(this.colourFactory == null) throw new IllegalStateException("Node colour handler factory must be set");
		
		int indexCntr = this.parent.getGraph().getNodeCounter().nextIndex();
		INodeColourHandler<CompoundNode, CompoundEdge> colourHandler = this.colourFactory.createColourHandler();
		CompoundNode retVal = new CompoundNode(this.parent, colourHandler, indexCntr);
		this.parent.getChildCigraph().addNewNode(retVal);
		return retVal;
	}

	public CompoundGraph getGraph() {
		return parent.getGraph();
	}

	public void setColourHandlerFactory(INodeColourHandlerFactory<CompoundNode, CompoundEdge> colourHandlerFactory) {
		this.colourFactory = colourHandlerFactory;
	}

}
