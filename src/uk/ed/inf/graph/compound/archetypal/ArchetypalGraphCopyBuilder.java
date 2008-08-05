package uk.ed.inf.graph.compound.archetypal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import uk.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ed.inf.graph.directed.IDirectedPair;

public abstract class ArchetypalGraphCopyBuilder {
	private ISubCompoundGraph<ArchetypalCompoundNode, ArchetypalCompoundEdge> sourceSubCigraph;
	private ArchetypalChildCompoundGraph destSubCigraph;
	private final Map<ArchetypalCompoundNode, ArchetypalCompoundNode> oldNewEquivList;
	
	public ArchetypalGraphCopyBuilder(){
		this.oldNewEquivList = new HashMap<ArchetypalCompoundNode, ArchetypalCompoundNode>();
	}
	
	public void setSourceSubgraph(ISubCompoundGraph<ArchetypalCompoundNode, ArchetypalCompoundEdge> sourceSubCompoundGraph){
		this.sourceSubCigraph = sourceSubCompoundGraph;
	}
	
	public void setDestinatChildCompoundGraph(ArchetypalChildCompoundGraph childCompoundGraph){
		this.destSubCigraph = childCompoundGraph;
	}
	
	public void copyNodes(){
		Iterator<ArchetypalCompoundNode> sourceNodeIter = this.sourceSubCigraph.nodeIterator();
		while(sourceNodeIter.hasNext()){
			ArchetypalCompoundNode srcNode = (ArchetypalCompoundNode)sourceNodeIter.next(); 
			copyNode(srcNode, this.destSubCigraph.getRootNode());
		}
	}

	private void copyNode(ArchetypalCompoundNode srcNode, ArchetypalCompoundNode destParentNode){
//		IBasicNodeFactory<ArchetypalCompoundNode, ArchetypalCompoundEdge> nodeFactory = this.destSubCigraph.nodeFactory();
//		ArchetypalCompoundNode newNode = nodeFactory.createNode();
		ArchetypalCompoundNode newNode = createCopyOfNode(srcNode, destParentNode, destParentNode.getGraph().getNodeCounter().nextIndex());
		this.oldNewEquivList.put(srcNode, newNode);
		Iterator<? extends ArchetypalCompoundNode> childIter = srcNode.childIterator();
		while(childIter.hasNext()){
			ArchetypalCompoundNode childNode = childIter.next();
			copyNode(childNode, newNode);
		}
	}
	
	/**
	 * Create a compound node
	 * @param srcNode
	 * @param destParentNode
	 * @param newNodeIndex
	 * @return
	 */
	protected abstract ArchetypalCompoundNode createCopyOfNode(ArchetypalCompoundNode srcNode,
																ArchetypalCompoundNode destParentNode, int newNodeIndex);
	
	public void copyEquivalentEdges(){
		Iterator<ArchetypalCompoundEdge> edgeIter = this.sourceSubCigraph.edgeIterator();
		while(edgeIter.hasNext()){
			ArchetypalCompoundEdge srcEdge = (ArchetypalCompoundEdge)edgeIter.next();
			IDirectedPair<ArchetypalCompoundNode, ArchetypalCompoundEdge> ends = srcEdge.getConnectedNodes();
			ArchetypalCompoundNode newInNode = this.oldNewEquivList.get(ends.getInNode());
			ArchetypalCompoundNode newOutNode = this.oldNewEquivList.get(ends.getOutNode());
			ArchetypalCompoundNode oldOwner = srcEdge.getOwningChildGraph().getRootNode();
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
//			ICompoundEdgeFactory<ArchetypalCompoundNode, ArchetypalCompoundEdge> edgeFact = this.destSubCigraph.edgeFactory();
//			edgeFact.setPair(newOutNode, newInNode);
//			ArchetypalCompoundEdge newEdge = edgeFact.createEdge();
			ArchetypalCompoundEdge newEdge = createCopyOfEdge(srcEdge, linkOwner.getChildCigraph(), linkOwner.getGraph().getEdgeCounter().nextIndex(),
																newOutNode, newInNode);
			this.destSubCigraph.addNewEdge(newEdge);
		}
	}
	
	/**
	 * Create a new edge that us a copy of another, which may be in a different graph this this one. 
	 * @param srcEdge The edge to copy.
	 * @param edgeOwner The compound node that will "own" this edge. 
	 * @param newEdgeIndex The index to use for the newly created edge.
	 * @return The newly created edge.
	 */
	protected abstract ArchetypalCompoundEdge createCopyOfEdge(ArchetypalCompoundEdge srcEdge, ArchetypalChildCompoundGraph edgeOwner,
												int newEdgeIndex, ArchetypalCompoundNode outNode, ArchetypalCompoundNode inNode);	
}
