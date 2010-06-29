/*
Copyright 2009, Court of the University of Edinburgh

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License. 
*/
package uk.ed.inf.graph.compound.base;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import uk.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundGraphMoveBuilder;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.ICompoundNodePair;
import uk.ed.inf.graph.compound.ISubCompoundGraph;

public class BaseGraphMoveBuilder implements ICompoundGraphMoveBuilder {
	private BaseSubCompoundGraph sourceSubCigraph;
	private BaseChildCompoundGraph destSubCigraph;
	private BaseSubCompoundGraphFactory subGraphFactory;
	private BaseSubCompoundGraphFactory removalSubGraphFactory;
	private final Map<BaseCompoundNode, BaseCompoundNode> oldNewEquivList;
	private final Set<Integer> visited = new HashSet<Integer>(); 
	
	public BaseGraphMoveBuilder(){
		this.oldNewEquivList = new HashMap<BaseCompoundNode, BaseCompoundNode>();
	}
	
	/**
	 * @see uk.ed.inf.graph.compound.base.ICompoundGraphMoveBuilder#setSourceSubgraph(uk.ed.inf.graph.compound.ISubCompoundGraph)
	 * 
	 * @param sourceSubCompoundGraph source subgraph to be moved
	 * @throws IllegalArgumentException if <code>sourceSubCompoundGraph</code> is not of type <code>BaseSubCompoundGraph</code>. 
	 */
	public void setSourceSubgraph(ISubCompoundGraph sourceSubCompoundGraph){
		if(!(sourceSubCompoundGraph instanceof BaseSubCompoundGraph)){
			throw new IllegalArgumentException("sourceSubCompoundGraph must be of type BaseSubCompondGraph");
		}
		this.sourceSubCigraph = (BaseSubCompoundGraph)sourceSubCompoundGraph;
	}
	
	/**
	 * @see uk.ed.inf.graph.compound.base.ICompoundGraphMoveBuilder#setDestinatChildCompoundGraph(uk.ed.inf.graph.compound.base.BaseChildCompoundGraph)
	 * @param sourceSubCompoundGraph target child graph to be moved to
	 * @throws IllegalArgumentException if <code>childCompoundGraph</code> is not of type <code>BaseChildCompoundGraph</code>. 
	 */
	public void setDestinatChildCompoundGraph(IChildCompoundGraph childCompoundGraph){
		if(!(childCompoundGraph instanceof BaseChildCompoundGraph)){
			throw new IllegalArgumentException("childCompoundGraph must be of type BaseChildCompondGraph");
		}
		this.destSubCigraph = (BaseChildCompoundGraph)childCompoundGraph;
	}
	
	public void makeMove(){
		this.oldNewEquivList.clear();
		this.visited.clear();
		this.subGraphFactory = this.destSubCigraph.getSuperGraph().subgraphFactory();
		this.removalSubGraphFactory = this.destSubCigraph.getSuperGraph().subgraphFactory();
		this.additionalInitialisation();
		moveNodes();
		moveEquivalentEdges();
		moveLinkedEdges();
		this.additionalMoveTasks();
		this.sourceSubCigraph.getSuperGraph().internalRemoveSubgraph(this.removalSubGraphFactory.createSubgraph());
        // avoid holding onto additional unneeded memory
        this.oldNewEquivList.clear();
        this.visited.clear();
        this.removalSubGraphFactory = null;
	}
	
    /**
     * Provides a hook for super classes to initialise its data-structures before
     * copying begins, but after this classes move data-structures have been initialised.         
     */
    protected abstract void additionalInitialisation(); 
    
    /**
     * Provide a hook for super classes to perform additional operations after this class has
     * completed moving the compound graph.
     */
    protected abstract void additionalMoveTasks();

    /* (non-Javadoc)
	 * @see uk.ed.inf.graph.compound.base.ICompoundGraphCopyBuilder#copyNodes()
	 */
	private void moveNodes(){
		Iterator<ICompoundNode> sourceNodeIter = this.sourceSubCigraph.topNodeIterator();
		while(sourceNodeIter.hasNext()){
			BaseCompoundNode srcNode = (BaseCompoundNode)sourceNodeIter.next();
			if(!visited.contains(srcNode.getIndex())){
				if(srcNode.getParent().equals(this.destSubCigraph.getRoot())){
					//the source and destination are the same so don't move the top node
					markUnmovedNode(srcNode, this.destSubCigraph.getRoot());
				}
				else{
					moveNode(srcNode, this.destSubCigraph.getRoot());
				}
			}
		}
	}
	
	
	/**
	 * Marks nodes that are not to be moved to the same location. Since no move 
	 * is actually required the equivalence and visit lists are updated so that the
	 * rest of the move operation can perform as if these nodes were actually moved.
	 * @param srcNode the node to be moved
	 * @param destParentNode the destination for th move, which should in fact be the <code>srcNode</code> parent.
	 */
	private void markUnmovedNode(BaseCompoundNode srcNode, BaseCompoundNode destParentNode){
		this.visited.add(srcNode.getIndex()) ;
		this.oldNewEquivList.put(srcNode, srcNode);
		Iterator<ICompoundNode> childIter = srcNode.childIterator();
		while(childIter.hasNext()){
			BaseCompoundNode childNode = (BaseCompoundNode)childIter.next();
			markUnmovedNode(childNode, srcNode);
		}
	}

	private void moveNode(BaseCompoundNode srcNode, BaseCompoundNode destParentNode){
		BaseCompoundNode newNode = createMoveOfNode(srcNode, destParentNode);
		this.visited.add(srcNode.getIndex()) ;
		this.oldNewEquivList.put(srcNode, newNode);
		this.subGraphFactory.addNode(newNode);
		this.removalSubGraphFactory.addNode(srcNode);
		Iterator<ICompoundNode> childIter = srcNode.childIterator();
		while(childIter.hasNext()){
			BaseCompoundNode childNode = (BaseCompoundNode)childIter.next();
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
		Iterator<ICompoundEdge> edgeIter = this.sourceSubCigraph.edgeIterator();
		while(edgeIter.hasNext()){
			BaseCompoundEdge srcEdge = (BaseCompoundEdge)edgeIter.next();
			ICompoundNodePair ends = srcEdge.getConnectedNodes();
			BaseCompoundNode newInNode = this.oldNewEquivList.get(ends.getInNode());
			BaseCompoundNode newOutNode = this.oldNewEquivList.get(ends.getOutNode());
			BaseCompoundNode oldOwner = srcEdge.getOwningChildGraph().getRoot();
			// find the owning node if it is part of the subgraph.
			BaseCompoundNode newOwner = this.oldNewEquivList.get(oldOwner);
			if(newOwner == null){
				// the submap does not contain the lca of this edge so it must be calculated from
				// scratch
				BaseCompoundGraph ciGraph = this.destSubCigraph.getSuperGraph();
				ICompoundNode lca = ciGraph.getLcaNode(newInNode, newOutNode);
				if(lca == null){
					throw new IllegalStateException("The graph and subgraph are inconsistent: an lca for a copied edge could not be found");
				}
				newOwner = (BaseCompoundNode)lca;
			}
			BaseCompoundEdge newEdge = srcEdge;
			// now we move the edge only if it's connecting nodes and parent have changed
			if(!(newOwner.equals(oldOwner) && newInNode.equals(ends.getInNode()) && newOutNode.equals(ends.getOutNode()))){
				newEdge = createMoveOfEdge(srcEdge, newOwner.getChildCompoundGraph(),	newOutNode, newInNode);
				this.removalSubGraphFactory.addEdge(srcEdge);
			}
			this.subGraphFactory.addEdge(newEdge);
		}
	}
	
	/* (non-Javadoc)
	 * @see uk.ed.inf.graph.compound.base.ICompoundGraphCopyBuilder#copyEquivalentEdges()
	 */
	private void moveLinkedEdges(){
		Iterator<ICompoundNode> nodeIter = this.sourceSubCigraph.nodeIterator();
		Set<Integer> visited = new HashSet<Integer>();
		while (nodeIter.hasNext()) {
			BaseCompoundNode srcNode = (BaseCompoundNode)nodeIter.next();
			Iterator<ICompoundEdge> edgeIter = srcNode.edgeIterator();
			while (edgeIter.hasNext()) {
				boolean foundLinkedNode = true;
				BaseCompoundEdge srcEdge = (BaseCompoundEdge)edgeIter.next();
				if (!visited.contains(srcEdge.getIndex())) {
					visited.add(srcEdge.getIndex());
					ICompoundNodePair ends = srcEdge.getConnectedNodes();
					BaseCompoundNode newInNode = this.oldNewEquivList.get(ends.getInNode());
					BaseCompoundNode newOutNode = this.oldNewEquivList.get(ends.getOutNode());
					if(newInNode == null){
						newInNode = (BaseCompoundNode)ends.getInNode();
					}
					else if(newOutNode == null){
						newOutNode = (BaseCompoundNode)ends.getOutNode();
					}
					else{
						foundLinkedNode = false;
					}
					if(foundLinkedNode){
						BaseCompoundGraph ciGraph = this.destSubCigraph.getSuperGraph();
						ICompoundNode lca = ciGraph.getLcaNode(newInNode, newOutNode);
						if (lca == null) {
							throw new IllegalStateException(
							"The graph and subgraph are inconsistent: an lca for a copied edge could not be found");
						}
						BaseCompoundEdge newEdge = createMoveOfEdge(srcEdge, (BaseChildCompoundGraph)lca.getChildCompoundGraph(), newOutNode, newInNode);
						this.removalSubGraphFactory.addEdge(srcEdge);
						this.subGraphFactory.addEdge(newEdge);
					}
				}
			}
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
