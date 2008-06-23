package uk.ed.inf.graph.compound.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import uk.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ed.inf.graph.directed.IDirectedPair;

public class ChildCompoundGraphBuilder {
	private final ISubCompoundGraph<CompoundNode, CompoundEdge> sourceSubCigraph;
	private final ChildCompoundGraph destSubCigraph;
	private final Map<CompoundNode, CompoundNode> oldNewEquivList;
	
	public ChildCompoundGraphBuilder(ChildCompoundGraph destSubCigraph, ISubCompoundGraph<CompoundNode, CompoundEdge> sourceSubCigraph){
		this.destSubCigraph = destSubCigraph;
		this.sourceSubCigraph = sourceSubCigraph;
		this.oldNewEquivList = new HashMap<CompoundNode, CompoundNode>();
	}
	
	public void copyNodes(){
		Iterator<CompoundNode> sourceNodeIter = this.sourceSubCigraph.nodeIterator();
		while(sourceNodeIter.hasNext()){
			CompoundNode srcNode = (CompoundNode)sourceNodeIter.next(); 
			copyNode(srcNode, this.destSubCigraph.getRoot());
		}
	}

	private void copyNode(CompoundNode srcNode, CompoundNode destParentNode){
		CompoundNodeFactory nodeFactory = destParentNode.getChildCigraph().nodeFactory();
		CompoundNode newNode = nodeFactory.createNode();
		this.oldNewEquivList.put(srcNode, newNode);
		Iterator<CompoundNode> childIter = srcNode.childIterator();
		while(childIter.hasNext()){
			CompoundNode childNode = childIter.next();
			copyNode(childNode, newNode);
		}
	}
	
	public void copyEquivalentEdges(){
		Iterator<CompoundEdge> edgeIter = this.sourceSubCigraph.edgeIterator();
		while(edgeIter.hasNext()){
			CompoundEdge srcEdge = (CompoundEdge)edgeIter.next();
			IDirectedPair<CompoundNode, CompoundEdge> ends = srcEdge.getConnectedNodes();
			CompoundNode newInNode = this.oldNewEquivList.get(ends.getInNode());
			CompoundNode newOutNode = this.oldNewEquivList.get(ends.getOutNode());
			CompoundNode oldOwner = srcEdge.getOwningChildGraph().getRoot();
			// find the owning node if it is part of the subgraph.
			CompoundNode linkOwner = this.oldNewEquivList.get(oldOwner);
			if(linkOwner == null){
				// the submap does not contain the lca of this edge so it must be calculated from
				// scratch
				CompoundGraph ciGraph = this.destSubCigraph.getSuperGraph();
				CompoundNode lca = ciGraph.getLcaNode(newInNode, newOutNode);
				if(lca == null){
					throw new IllegalStateException("The graph and subgraph are inconsisten: an lca for a copied edge could not be found");
				}
				linkOwner = lca;
			}
			int edgeIdx =  this.destSubCigraph.getSuperGraph().getEdgeCounter().nextIndex();
			CompoundEdge newEdge = new CompoundEdge(linkOwner.getChildCigraph(), edgeIdx, newInNode, newOutNode);
			this.destSubCigraph.addNewEdge(newEdge);
		}
	}
}
