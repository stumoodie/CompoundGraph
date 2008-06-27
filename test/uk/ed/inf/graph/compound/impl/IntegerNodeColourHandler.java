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
	
	@Override
	public Integer copyColour(CompoundNode newNode) {
		return this.colour;
	}
	@Override
	public INodeColourHandler<CompoundNode, CompoundEdge> createCopy() {
		return new IntegerNodeColourHandler(this);
	}
	@Override
	public Object getColour() {
		return this.colour;
	}
	@Override
	public CompoundNode getNode() {
		return this.owner;
	}
	@Override
	public void setColour(Object colour) {
		if(!(colour instanceof Integer)) throw new IllegalArgumentException("colour must be of class Integer");
		this.colour = (Integer)colour;
	}
	@Override
	public void setNode(CompoundNode node) {
		this.owner = node;
	}
	

}
