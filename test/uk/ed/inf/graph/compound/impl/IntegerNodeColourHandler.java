package uk.ed.inf.graph.compound.impl;

import uk.ed.inf.graph.colour.INodeColourHandler;

public class IntegerNodeColourHandler implements INodeColourHandler<CompoundNode, CompoundEdge> {
	private Integer colour;
	private CompoundNode owner;
	
	public IntegerNodeColourHandler(Integer initialValue){
		this.colour = initialValue;
	}
	
	public IntegerNodeColourHandler(IntegerNodeColourHandler other){
		this.colour = other.colour;
		this.owner = null;
	}
	
	public Integer copyColour(CompoundNode newNode) {
		return this.colour;
	}

	public INodeColourHandler<CompoundNode, CompoundEdge> createCopy() {
		return new IntegerNodeColourHandler(this);
	}

	public Object getColour() {
		return this.colour;
	}

	public CompoundNode getNode() {
		return this.owner;
	}

	public void setColour(Object colour) {
		if(!(colour instanceof Integer)) throw new IllegalArgumentException("colour must be of class Integer");
		this.colour = (Integer)colour;
	}

	public void setNode(CompoundNode node) {
		this.owner = node;
	}
	

}
