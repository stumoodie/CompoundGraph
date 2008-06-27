package uk.ed.inf.graph.compound.impl;

import uk.ed.inf.graph.colour.INodeColourHandler;

public class StringNodeColourHandler implements INodeColourHandler<CompoundNode, CompoundEdge> {
	private String colour;
	private CompoundNode node;
	
	public StringNodeColourHandler(){
		this((String)null);
	}
	
	public StringNodeColourHandler(String initialValue){
		this.colour = initialValue;
		this.node = null;
	}
	
	private StringNodeColourHandler(StringNodeColourHandler other){
		this.colour = other.colour;
		this.node = null;
	}
	
	@Override
	public String copyColour(CompoundNode newNode) {
		return this.colour;
	}

	@Override
	public INodeColourHandler<CompoundNode, CompoundEdge> createCopy() {
		return new StringNodeColourHandler(this);
	}

	@Override
	public String getColour() {
		return this.colour;
	}

	@Override
	public CompoundNode getNode() {
		return this.node;
	}

	@Override
	public void setColour(Object colour) {
		if(!(colour instanceof String)) throw new IllegalArgumentException("Expected colour to be of class String");
		this.colour = (String)colour;
	}

	@Override
	public void setNode(CompoundNode node) {
		this.node = node;
	}


}
