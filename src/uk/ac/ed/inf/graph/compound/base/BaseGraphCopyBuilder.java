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
package uk.ac.ed.inf.graph.compound.base;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import uk.ac.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundGraphCopyBuilder;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ac.ed.inf.graph.directed.IDirectedPair;

public abstract class BaseGraphCopyBuilder implements ICompoundGraphCopyBuilder<BaseCompoundNode, BaseCompoundEdge> {
	private BaseSubCompoundGraph sourceSubCigraph;
	private BaseChildCompoundGraph destSubCigraph;
	private BaseSubCompoundGraphFactory subGraphFactory;
	private final Map<BaseCompoundNode, BaseCompoundNode> oldNewEquivList;
	private final Set<Integer> visited = new HashSet<Integer>(); 
	
	public BaseGraphCopyBuilder(){
		this.oldNewEquivList = new HashMap<BaseCompoundNode, BaseCompoundNode>();
	}
	
	/**
	 * @see uk.ac.ed.inf.graph.compound.base.ICompoundGraphCopyBuilder#setSourceSubgraph(uk.ac.ed.inf.graph.compound.ISubCompoundGraph)
	 * 
	 * @param sourceSubCompoundGraph source subgraph to be copied
	 * @throws IllegalArgumentException if <code>sourceSubCompoundGraph</code> is not of type <code>BaseSubCompoundGraph</code>. 
	 */
	public void setSourceSubgraph(ISubCompoundGraph<? extends BaseCompoundNode, ? extends BaseCompoundEdge> sourceSubCompoundGraph){
		if(!(sourceSubCompoundGraph instanceof BaseSubCompoundGraph)){
			throw new IllegalArgumentException("sourceSubCompoundGraph must be of type BaseSubCompondGraph");
		}
		this.sourceSubCigraph = (BaseSubCompoundGraph)sourceSubCompoundGraph;
	}
	
	/**
	 * @see uk.ac.ed.inf.graph.compound.base.ICompoundGraphCopyBuilder#setDestinatChildCompoundGraph(uk.ac.ed.inf.graph.compound.base.BaseChildCompoundGraph)
	 * @param sourceSubCompoundGraph target child graph to be copied to
	 * @throws IllegalArgumentException if <code>childCompoundGraph</code> is not of type <code>BaseChildCompoundGraph</code>. 
	 */
	public void setDestinatChildCompoundGraph(IChildCompoundGraph<? extends BaseCompoundNode, ? extends BaseCompoundEdge> childCompoundGraph){
		if(!(childCompoundGraph instanceof BaseChildCompoundGraph)){
			throw new IllegalArgumentException("childCompoundGraph must be of type BaseChildCompondGraph");
		}
		this.destSubCigraph = (BaseChildCompoundGraph)childCompoundGraph;
	}
	
	/**
	 * Gets the equivalent copied node to the specified node, if it exists. 
	 * @param originalNode the original node that we want to get the copy of.
	 * @return the copied node, or null if none exists.
	 */
	protected final BaseCompoundNode getCopiedNode(BaseCompoundNode originalNode) {
	    return this.oldNewEquivList.get(originalNode);
	}
	
	public void makeCopy(){
		this.oldNewEquivList.clear();
		this.visited.clear();
		this.subGraphFactory = this.destSubCigraph.getSuperGraph().subgraphFactory();
		additionalInitialisation();
		copyNodes();
		copyEquivalentEdges();
		additionalCopyTasks();
		// avoid holding onto additional unneeded memory
		this.oldNewEquivList.clear();
		this.visited.clear();
	}
	
	
	/**
	 * Provides a hook for super classes to initialise its data-structures before
	 * copying begins, but after this classes copy data-structures have been initialised.         
	 */
	protected abstract void additionalInitialisation(); 
	
	/**
	 * Provide a hook for super classes to perform additional operations after this class has
	 * completed copying the compound graph.
	 */
	protected abstract void additionalCopyTasks();
	
	/* (non-Javadoc)
	 * @see uk.ac.ed.inf.graph.compound.base.ICompoundGraphCopyBuilder#copyNodes()
	 */
	private void copyNodes(){
		Iterator<BaseCompoundNode> sourceNodeIter = this.sourceSubCigraph.topNodeIterator();
		while(sourceNodeIter.hasNext()){
			BaseCompoundNode srcNode = sourceNodeIter.next();
			if(!visited.contains(srcNode.getIndex())){
				copyNode(srcNode, this.destSubCigraph.getRootNode());
			}
		}
	}

	private void copyNode(BaseCompoundNode srcNode, BaseCompoundNode destParentNode){
		BaseCompoundNode newNode = createCopyOfNode(srcNode, destParentNode);
		this.visited.add(srcNode.getIndex()) ;
		this.oldNewEquivList.put(srcNode, newNode);
		this.subGraphFactory.addNode(newNode);
		Iterator<? extends BaseCompoundNode> childIter = srcNode.childIterator();
		while(childIter.hasNext()){
			BaseCompoundNode childNode = childIter.next();
			copyNode(childNode, newNode);
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
	protected abstract BaseCompoundNode createCopyOfNode(BaseCompoundNode srcNode, BaseCompoundNode destParentNode);
	
	/* (non-Javadoc)
	 * @see uk.ac.ed.inf.graph.compound.base.ICompoundGraphCopyBuilder#copyEquivalentEdges()
	 */
	private void copyEquivalentEdges(){
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
					throw new IllegalStateException("The graph and subgraph are inconsisten: an lca for a copied edge could not be found");
				}
				linkOwner = lca;
			}
			BaseCompoundEdge newEdge = createCopyOfEdge(srcEdge, linkOwner.getChildCompoundGraph(),	newOutNode, newInNode);
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
	protected abstract BaseCompoundEdge createCopyOfEdge(BaseCompoundEdge srcEdge, BaseChildCompoundGraph edgeOwner,
												BaseCompoundNode outNode, BaseCompoundNode inNode);	


	public BaseSubCompoundGraph getCopiedComponents() {
		return this.subGraphFactory.createSubgraph();
	}
}