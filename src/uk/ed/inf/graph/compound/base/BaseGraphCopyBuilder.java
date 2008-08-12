package uk.ed.inf.graph.compound.base;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import uk.ed.inf.graph.compound.ICompoundGraphCopyBuilder;
import uk.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ed.inf.graph.directed.IDirectedPair;

public abstract class BaseGraphCopyBuilder implements ICompoundGraphCopyBuilder {
	private ISubCompoundGraph<BaseCompoundNode, BaseCompoundEdge> sourceSubCigraph;
	private BaseChildCompoundGraph destSubCigraph;
	private final Map<BaseCompoundNode, BaseCompoundNode> oldNewEquivList;
	
	public BaseGraphCopyBuilder(){
		this.oldNewEquivList = new HashMap<BaseCompoundNode, BaseCompoundNode>();
	}
	
	/* (non-Javadoc)
	 * @see uk.ed.inf.graph.compound.base.ICompoundGraphCopyBuilder#setSourceSubgraph(uk.ed.inf.graph.compound.ISubCompoundGraph)
	 */
	public void setSourceSubgraph(ISubCompoundGraph<BaseCompoundNode, BaseCompoundEdge> sourceSubCompoundGraph){
		this.sourceSubCigraph = sourceSubCompoundGraph;
	}
	
	/* (non-Javadoc)
	 * @see uk.ed.inf.graph.compound.base.ICompoundGraphCopyBuilder#setDestinatChildCompoundGraph(uk.ed.inf.graph.compound.base.BaseChildCompoundGraph)
	 */
	public void setDestinatChildCompoundGraph(BaseChildCompoundGraph childCompoundGraph){
		this.destSubCigraph = childCompoundGraph;
	}
	
	/* (non-Javadoc)
	 * @see uk.ed.inf.graph.compound.base.ICompoundGraphCopyBuilder#copyNodes()
	 */
	public void copyNodes(){
		Iterator<BaseCompoundNode> sourceNodeIter = this.sourceSubCigraph.nodeIterator();
		while(sourceNodeIter.hasNext()){
			BaseCompoundNode srcNode = (BaseCompoundNode)sourceNodeIter.next(); 
			copyNode(srcNode, this.destSubCigraph.getRootNode());
		}
	}

	private void copyNode(BaseCompoundNode srcNode, BaseCompoundNode destParentNode){
//		IBasicNodeFactory<BaseCompoundNode, BaseCompoundEdge> nodeFactory = this.destSubCigraph.nodeFactory();
//		BaseCompoundNode newNode = nodeFactory.createNode();
		BaseCompoundNode newNode = createCopyOfNode(srcNode, destParentNode, destParentNode.getGraph().getNodeCounter().nextIndex());
		this.oldNewEquivList.put(srcNode, newNode);
		Iterator<? extends BaseCompoundNode> childIter = srcNode.childIterator();
		while(childIter.hasNext()){
			BaseCompoundNode childNode = childIter.next();
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
	protected abstract BaseCompoundNode createCopyOfNode(BaseCompoundNode srcNode,
																BaseCompoundNode destParentNode, int newNodeIndex);
	
	/* (non-Javadoc)
	 * @see uk.ed.inf.graph.compound.base.ICompoundGraphCopyBuilder#copyEquivalentEdges()
	 */
	public void copyEquivalentEdges(){
		Iterator<BaseCompoundEdge> edgeIter = this.sourceSubCigraph.edgeIterator();
		while(edgeIter.hasNext()){
			BaseCompoundEdge srcEdge = (BaseCompoundEdge)edgeIter.next();
			IDirectedPair<BaseCompoundNode, BaseCompoundEdge> ends = srcEdge.getConnectedNodes();
			BaseCompoundNode newInNode = this.oldNewEquivList.get(ends.getInNode());
			BaseCompoundNode newOutNode = this.oldNewEquivList.get(ends.getOutNode());
			BaseCompoundNode oldOwner = srcEdge.getOwningChildGraph().getRootNode();
			// find the owning node if it is part of the subgraph.
			BaseCompoundNode linkOwner = this.oldNewEquivList.get(oldOwner);
			if(linkOwner == null){
				// the submap does not contain the lca of this edge so it must be calculated from
				// scratch
				BaseCompoundGraph ciGraph = this.destSubCigraph.getSuperGraph();
				BaseCompoundNode lca = ciGraph.getLcaNode(newInNode, newOutNode);
				if(lca == null){
					throw new IllegalStateException("The graph and subgraph are inconsisten: an lca for a copied edge could not be found");
				}
				linkOwner = lca;
			}
//			int edgeIdx =  this.destSubCigraph.getSuperGraph().getEdgeCounter().nextIndex();
//			IEdgeColourHandler<BaseCompoundNode, BaseCompoundEdge> newColourHandler = srcEdge.getColourHandler().createCopy();
//			BaseCompoundEdge newEdge = new BaseCompoundEdge(linkOwner.getChildCigraph(), edgeIdx, newColourHandler, newInNode, newOutNode);
//			ICompoundEdgeFactory<BaseCompoundNode, BaseCompoundEdge> edgeFact = this.destSubCigraph.edgeFactory();
//			edgeFact.setPair(newOutNode, newInNode);
//			BaseCompoundEdge newEdge = edgeFact.createEdge();
			BaseCompoundEdge newEdge = createCopyOfEdge(srcEdge, linkOwner.getChildCompoundGraph(), linkOwner.getGraph().getEdgeCounter().nextIndex(),
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
	protected abstract BaseCompoundEdge createCopyOfEdge(BaseCompoundEdge srcEdge, BaseChildCompoundGraph edgeOwner,
												int newEdgeIndex, BaseCompoundNode outNode, BaseCompoundNode inNode);	
}
