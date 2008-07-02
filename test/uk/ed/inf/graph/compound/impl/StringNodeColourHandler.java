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
	
	public String copyColour(CompoundNode newNode) {
		return this.colour;
	}

	public INodeColourHandler<CompoundNode, CompoundEdge> createCopy() {
		return new StringNodeColourHandler(this);
	}

	public String getColour() {
		return this.colour;
	}

	public CompoundNode getNode() {
		return this.node;
	}

	public void setColour(Object colour) {
		if(!(colour instanceof String)) throw new IllegalArgumentException("Expected colour to be of class String");
		this.colour = (String)colour;
	}

	public void setNode(CompoundNode node) {
		this.node = node;
	}


}
