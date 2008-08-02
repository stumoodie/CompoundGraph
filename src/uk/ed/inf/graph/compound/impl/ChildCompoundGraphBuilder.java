package uk.ed.inf.graph.compound.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import uk.ed.inf.graph.basic.IBasicNodeFactory;
import uk.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ed.inf.graph.directed.IDirectedPair;

public class ChildCompoundGraphBuilder {
	private final ISubCompoundGraph<ArchetypalCompoundNode, ArchetypalCompoundEdge> sourceSubCigraph;
	private final ArchetypalChildCompoundGraph destSubCigraph;
	private final Map<ArchetypalCompoundNode, ArchetypalCompoundNode> oldNewEquivList;
	
	public ChildCompoundGraphBuilder(ArchetypalChildCompoundGraph destSubCigraph, ISubCompoundGraph<ArchetypalCompoundNode, ArchetypalCompoundEdge> sourceSubCigraph){
		this.destSubCigraph = destSubCigraph;
		this.sourceSubCigraph = sourceSubCigraph;
		this.oldNewEquivList = new HashMap<ArchetypalCompoundNode, ArchetypalCompoundNode>();
	}
	
	public void copyNodes(){
		Iterator<ArchetypalCompoundNode> sourceNodeIter = this.sourceSubCigraph.nodeIterator();
		while(sourceNodeIter.hasNext()){
			ArchetypalCompoundNode srcNode = (ArchetypalCompoundNode)sourceNodeIter.next(); 
			copyNode(srcNode, this.destSubCigraph.getRoot());
		}
	}

	private void copyNode(ArchetypalCompoundNode srcNode, ArchetypalCompoundNode destParentNode){
		IBasicNodeFactory<ArchetypalCompoundNode, ArchetypalCompoundEdge> nodeFactory = this.destSubCigraph.nodeFactory();
		ArchetypalCompoundNode newNode = nodeFactory.createNode();
		this.oldNewEquivList.put(srcNode, newNode);
		Iterator<? extends ArchetypalCompoundNode> childIter = srcNode.childIterator();
		while(childIter.hasNext()){
			ArchetypalCompoundNode childNode = childIter.next();
			copyNode(childNode, newNode);
		}
	}
	
	
	public void copyEquivalentEdges(){
		Iterator<ArchetypalCompoundEdge> edgeIter = this.sourceSubCigraph.edgeIterator();
		while(edgeIter.hasNext()){
			ArchetypalCompoundEdge srcEdge = (ArchetypalCompoundEdge)edgeIter.next();
			IDirectedPair<ArchetypalCompoundNode, ArchetypalCompoundEdge> ends = srcEdge.getConnectedNodes();
			ArchetypalCompoundNode newInNode = this.oldNewEquivList.get(ends.getInNode());
			ArchetypalCompoundNode newOutNode = this.oldNewEquivList.get(ends.getOutNode());
			ArchetypalCompoundNode oldOwner = srcEdge.getOwningChildGraph().getRoot();
			// find the owning node if it is part of the subgraph.
			ArchetypalCompoundNode linkOwner = this.oldNewEquivList.get(oldOwner);
			if(linkOwner == null){
				// the submap does not contain the lca of this edge so it must be calculated from
				// scratch
				ArchetypalCompoundGraph ciGraph = this.destSubCigraph.getSuperGraph();
				ArchetypalCompoundNode lca = ciGraph.getLcaNode(newInNode, newOutNode);
				if(lca == null){
					throw new IllegalStateException("The graph and subgraph are inconsisten: an lca for a copied edge could not be found");
				}
				linkOwner = lca;
			}
//			int edgeIdx =  this.destSubCigraph.getSuperGraph().getEdgeCounter().nextIndex();
//			IEdgeColourHandler<ArchetypalCompoundNode, ArchetypalCompoundEdge> newColourHandler = srcEdge.getColourHandler().createCopy();
//			ArchetypalCompoundEdge newEdge = new ArchetypalCompoundEdge(linkOwner.getChildCigraph(), edgeIdx, newColourHandler, newInNode, newOutNode);
			ICompoundEdgeFactory<ArchetypalCompoundNode, ArchetypalCompoundEdge> edgeFact = this.destSubCigraph.edgeFactory();
			edgeFact.setPair(newOutNode, newInNode);
			ArchetypalCompoundEdge newEdge = edgeFact.createEdge();
			this.destSubCigraph.addNewEdge(newEdge);
		}
	}
}
