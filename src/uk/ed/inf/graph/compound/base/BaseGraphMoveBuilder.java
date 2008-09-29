package uk.ed.inf.graph.compound.base;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import uk.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ed.inf.graph.compound.ICompoundGraphCopyBuilder;
import uk.ed.inf.graph.compound.ICompoundGraphMoveBuilder;
import uk.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ed.inf.graph.directed.IDirectedPair;

public abstract class BaseGraphMoveBuilder implements ICompoundGraphMoveBuilder<BaseCompoundNode, BaseCompoundEdge> {
	private BaseSubCompoundGraph sourceSubCigraph;
	private BaseChildCompoundGraph destSubCigraph;
	private BaseSubCompoundGraphFactory subGraphFactory;
	private final Map<BaseCompoundNode, BaseCompoundNode> oldNewEquivList;
	private Set <Integer> visited = new HashSet<Integer> () ;
	
	public BaseGraphMoveBuilder(){
		this.oldNewEquivList = new HashMap<BaseCompoundNode, BaseCompoundNode>();
	}
	
	/**
	 * @see uk.ed.inf.graph.compound.base.ICompoundGraphCopyBuilder#setSourceSubgraph(uk.ed.inf.graph.compound.ISubCompoundGraph)
	 * 
	 * @param sourceSubCompoundGraph source subgraph to be moved
	 * @throws IllegalArgumentException if <code>sourceSubCompoundGraph</code> is not of type <code>BaseSubCompoundGraph</code>. 
	 */
	public void setSourceSubgraph(ISubCompoundGraph<? extends BaseCompoundNode, ? extends BaseCompoundEdge> sourceSubCompoundGraph){
		if(!(sourceSubCompoundGraph instanceof BaseSubCompoundGraph)){
			throw new IllegalArgumentException("sourceSubCompoundGraph must be of type BaseSubCompondGraph");
		}
		this.sourceSubCigraph = (BaseSubCompoundGraph)sourceSubCompoundGraph;
	}
	
	/**
	 * @see uk.ed.inf.graph.compound.base.ICompoundGraphCopyBuilder#setDestinatChildCompoundGraph(uk.ed.inf.graph.compound.base.BaseChildCompoundGraph)
	 * @param sourceSubCompoundGraph target child graph to be moved to
	 * @throws IllegalArgumentException if <code>childCompoundGraph</code> is not of type <code>BaseChildCompoundGraph</code>. 
	 */
	public void setDestinatChildCompoundGraph(IChildCompoundGraph<? extends BaseCompoundNode, ? extends BaseCompoundEdge> childCompoundGraph){
		if(!(childCompoundGraph instanceof BaseChildCompoundGraph)){
			throw new IllegalArgumentException("childCompoundGraph must be of type BaseChildCompondGraph");
		}
		this.destSubCigraph = (BaseChildCompoundGraph)childCompoundGraph;
	}
	
	public void makeMove(){
		this.oldNewEquivList.clear();
		this.subGraphFactory = this.destSubCigraph.getSuperGraph().subgraphFactory();
		moveNodes();
		moveEquivalentEdges();
	}
	
	/* (non-Javadoc)
	 * @see uk.ed.inf.graph.compound.base.ICompoundGraphCopyBuilder#copyNodes()
	 */
	private void moveNodes(){
		Iterator<BaseCompoundNode> sourceNodeIter = this.sourceSubCigraph.nodeIterator();
		while(sourceNodeIter.hasNext()){
			BaseCompoundNode srcNode = sourceNodeIter.next(); 
			if(!visited.contains(srcNode.getIndex())){
				moveNode(srcNode, this.destSubCigraph.getRootNode());
			}
		}
	}

	private void moveNode(BaseCompoundNode srcNode, BaseCompoundNode destParentNode){
		BaseCompoundNode newNode = createMoveOfNode(srcNode, destParentNode);
		this.visited.add(srcNode.getIndex()) ;
		this.oldNewEquivList.put(srcNode, newNode);
		this.subGraphFactory.addNode(newNode);
		Iterator<? extends BaseCompoundNode> childIter = srcNode.childIterator();
		while(childIter.hasNext()){
			BaseCompoundNode childNode = childIter.next();
			moveNode(childNode, newNode);
		}
	}
	
	public final BaseSubCompoundGraph getSourceSubgraph(){
		return this.sourceSubCigraph;
	}
	
	public final BaseChildCompoundGraph getDestinationChildGraph(){
		return this.destSubCigraph;
	}
	
	/**
	 * Create a compound node.  This node MUST be added to the child graph of the <code>destParentNode</code>. 
	 * @param srcNode
	 * @param destParentNode
	 * @param newNodeIndex
	 * @return
	 */
	protected abstract BaseCompoundNode createMoveOfNode(BaseCompoundNode srcNode, BaseCompoundNode destParentNode);
	
	/* (non-Javadoc)
	 * @see uk.ed.inf.graph.compound.base.ICompoundGraphCopyBuilder#copyEquivalentEdges()
	 */
	private void moveEquivalentEdges(){
		Iterator<BaseCompoundEdge> edgeIter = this.sourceSubCigraph.edgeIterator();
		while(edgeIter.hasNext()){
			BaseCompoundEdge srcEdge = edgeIter.next();
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
					throw new IllegalStateException("The graph and subgraph are inconsisten: an lca for a moved edge could not be found");
				}
				linkOwner = lca;
			}
			BaseCompoundEdge newEdge = createMoveOfEdge(srcEdge, linkOwner.getChildCompoundGraph(),	newOutNode, newInNode);
			this.subGraphFactory.addEdge(newEdge);
		}
	}
	
	/**
	 * Create a new edge that us a copy of another, which may be in a different graph this this one. This edge MUST be added to
	 * the destination child graph. 
	 * @param srcEdge The edge to copy.
	 * @param edgeOwner The compound node that will "own" this edge. 
	 * @param newEdgeIndex The index to use for the newly created edge.
	 * @return The newly created edge.
	 */
	protected abstract BaseCompoundEdge createMoveOfEdge(BaseCompoundEdge srcEdge, BaseChildCompoundGraph edgeOwner,
												BaseCompoundNode outNode, BaseCompoundNode inNode);	


	public BaseSubCompoundGraph getMovedComponents() {
		return this.subGraphFactory.createSubgraph();
	}
}
