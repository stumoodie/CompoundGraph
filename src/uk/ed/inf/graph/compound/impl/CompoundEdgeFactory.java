package uk.ed.inf.graph.compound.impl;

import uk.ed.inf.graph.colour.IColouredEdgeFactory;
import uk.ed.inf.graph.colour.IEdgeColourHandler;
import uk.ed.inf.graph.colour.IEdgeColourHandlerFactory;
import uk.ed.inf.graph.directed.IDirectedEdgeFactory;

public class CompoundEdgeFactory implements IDirectedEdgeFactory<CompoundNode, CompoundEdge>,
	IColouredEdgeFactory<CompoundNode, CompoundEdge> {
	private final CompoundGraph graph;
	private ChildCompoundGraph childCompoundGraph;
	private CompoundNode inNode;
	private CompoundNode outNode;
	private IEdgeColourHandlerFactory<CompoundNode, CompoundEdge> colourHandlerFactory;
	
	public CompoundEdgeFactory(CompoundGraph graph){
		this.graph = graph;
	}
	
	
	public void setPair(CompoundNode inNode, CompoundNode outNode){
		this.inNode = (CompoundNode)inNode;
		this.outNode = (CompoundNode)outNode;
	}
	
	public CompoundEdge createEdge() {
		if(colourHandlerFactory == null) throw new IllegalStateException("Colour handler factor must be set");
		CompoundNode lcmNode = this.graph.getLcaNode(this.inNode, this.outNode);
		int cntr = this.graph.getEdgeCounter().nextIndex();
		IEdgeColourHandler<CompoundNode, CompoundEdge> edgeColourHandler = colourHandlerFactory.createColourHandler();
		CompoundEdge newEdge = new CompoundEdge(this.childCompoundGraph, cntr, edgeColourHandler, inNode, outNode); 
		lcmNode.getChildCigraph().addNewEdge(newEdge);
		return newEdge;
	}


	public CompoundGraph getGraph() {
		return this.graph;
	}


	public void setColourHandlerFactory(IEdgeColourHandlerFactory<CompoundNode, CompoundEdge> colourHandlerFactory) {
		this.colourHandlerFactory = colourHandlerFactory;
	}
}
