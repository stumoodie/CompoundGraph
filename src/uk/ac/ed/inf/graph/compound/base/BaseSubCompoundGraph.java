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

import java.util.Iterator;

import uk.ac.ed.inf.graph.basic.IBasicPair;
import uk.ac.ed.inf.graph.basic.ISubgraphAlgorithms;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ac.ed.inf.graph.directed.IDirectedPair;
import uk.ac.ed.inf.graph.util.IDirectedEdgeSet;
import uk.ac.ed.inf.graph.util.IEdgeSet;
import uk.ac.ed.inf.graph.util.IFilterCriteria;
import uk.ac.ed.inf.graph.util.INodeSet;
import uk.ac.ed.inf.graph.util.SubgraphAlgorithms;
import uk.ac.ed.inf.graph.util.impl.FilteredEdgeSet;
import uk.ac.ed.inf.graph.util.impl.FilteredNodeSet;

public abstract class BaseSubCompoundGraph implements ISubCompoundGraph<BaseCompoundNode, BaseCompoundEdge> {
	private INodeSet<BaseCompoundNode, BaseCompoundEdge> topNodeSet;
	private INodeSet<BaseCompoundNode, BaseCompoundEdge> nodeSet;
	private IEdgeSet<BaseCompoundNode, BaseCompoundEdge> edgeSet;
	private Boolean isInducedSubgraphFlag = null;
	
	protected BaseSubCompoundGraph(){
	}
	
	protected final void createTopNodeSet(INodeSet<BaseCompoundNode, BaseCompoundEdge> topNodeSet){
		this.topNodeSet = new FilteredNodeSet<BaseCompoundNode, BaseCompoundEdge>(topNodeSet, new IFilterCriteria<BaseCompoundNode>(){

			public boolean matched(BaseCompoundNode testObj) {
//				return !testObj.isRemoved();
				return true;
			}
	
		});
	}
	
	protected final void createNodeSet(INodeSet<BaseCompoundNode, BaseCompoundEdge> nodeSet){
		this.nodeSet = new FilteredNodeSet<BaseCompoundNode, BaseCompoundEdge>(nodeSet, new IFilterCriteria<BaseCompoundNode>(){
			public boolean matched(BaseCompoundNode testObj) {
				return true;
			}
	
		});
	}
	
	protected final void createEdgeSet(IDirectedEdgeSet<BaseCompoundNode, BaseCompoundEdge> edgeSet){
		this.edgeSet = new FilteredEdgeSet<BaseCompoundNode, BaseCompoundEdge>(edgeSet,
				new IFilterCriteria<BaseCompoundEdge>(){

					public boolean matched(BaseCompoundEdge testObj) {
//						return !testObj.isRemoved();
						return true;
					}
			
		});
	}
	
	public abstract BaseCompoundGraph getSuperGraph();

	public boolean isInducedSubgraph() {
		if(isInducedSubgraphFlag  == null){
			ISubgraphAlgorithms<BaseCompoundNode, BaseCompoundEdge> alg = new SubgraphAlgorithms<BaseCompoundNode, BaseCompoundEdge>(this);
			this.isInducedSubgraphFlag = new Boolean(alg.isInducedSubgraph());
		}
		return this.isInducedSubgraphFlag;
	}

	public boolean containsConnection(BaseCompoundNode thisNode, BaseCompoundNode thatNode) {
		boolean retVal = false;
		if(thisNode != null && thatNode != null){
			if(this.containsNode(thisNode) && this.containsNode(thatNode)){
				retVal = thisNode.hasEdgeWith(thatNode);
			}
		}
		return retVal;
	}

	public boolean containsEdge(BaseCompoundEdge edge) {
		return this.edgeSet.contains(edge);
	}

	public boolean containsEdge(int edgeIdx) {
		return this.edgeSet.contains(edgeIdx);
	}

	public boolean containsNode(int nodeIdx) {
		return this.nodeSet.contains(nodeIdx);
	}

	public boolean containsNode(BaseCompoundNode node) {
		return this.nodeSet.contains(node);
	}

	public BaseCompoundEdge getEdge(int edgeIdx) {
		return this.edgeSet.get(edgeIdx);
	}

	public Iterator<BaseCompoundEdge> edgeIterator() {
		return this.edgeSet.iterator();
	}

	public BaseCompoundNode getNode(int nodeIdx) {
		return this.nodeSet.get(nodeIdx);
	}

	public Iterator<BaseCompoundNode> nodeIterator() {
		return this.nodeSet.iterator();
	}

	public int getNumEdges() {
		return this.edgeSet.size();
	}

	public int getNumNodes() {
		return this.nodeSet.size();
	}

	void addTopNode(BaseCompoundNode newNode){
		this.topNodeSet.add(newNode);
	}
	
	void addEdge(BaseCompoundEdge newEdge){
		this.edgeSet.add(newEdge);
	}
	
	void buildComplete(){
	}

	public boolean containsDirectedEdge(BaseCompoundNode outNode, BaseCompoundNode inNode) {
		boolean retVal = false;
		if(outNode != null && inNode != null){
			retVal = this.edgeSet.contains(outNode, inNode);
		}
		return retVal;
	}

	public boolean isConsistentSnapShot() {
		boolean retVal = true;
		Iterator <BaseCompoundNode> nodeIter = this.nodeIterator() ;
		while ( nodeIter.hasNext() && retVal){
			retVal = !nodeIter.next().isRemoved();
		}
		Iterator<BaseCompoundEdge> edgeIter = this.edgeIterator();
		while ( edgeIter.hasNext() && retVal){
			retVal = !edgeIter.next().isRemoved();
		}		
		
		return retVal;
	}

	/**
	 * Tests if the ends define one or more directed edges.
	 */
	public boolean containsDirectedEdge(IDirectedPair<? extends BaseCompoundNode, ? extends BaseCompoundEdge> ends) {
		boolean retVal = false;
		if(ends != null){
			BaseCompoundNode outNode = (BaseCompoundNode)ends.getOutNode();
			BaseCompoundNode inNode = (BaseCompoundNode)ends.getInNode();
			// check that both nodes exist in this subgraph
			retVal = outNode.hasOutEdgeTo(inNode);
		}
		return retVal;
	}

	/**
	 * Tests if the ends define any edge in this graph. Note that the node pair must
	 * be created by this graph as the method expects <code>ends</code> to be of type
	 * <code>IDirectedPair</code> and will return false if it is not.
	 * @param ends the pair of nodes that may define the edges of an edge.
	 * @return true if it does, false otherwise.  
	 */
	@SuppressWarnings("unchecked")
	public boolean containsConnection(IBasicPair<? extends BaseCompoundNode, ? extends BaseCompoundEdge> ends) {
		boolean retVal = false;
		if(ends != null && ends instanceof IDirectedPair){
			// since this is a directed graph a valid edge pair must be an IDirectedPair
			IDirectedPair<? extends BaseCompoundNode, ? extends BaseCompoundEdge> ciEnds = (IDirectedPair<? extends BaseCompoundNode, ? extends BaseCompoundEdge>)ends;
			BaseCompoundNode oneNode = (BaseCompoundNode)ciEnds.getOutNode();
			BaseCompoundNode twoNode = (BaseCompoundNode)ciEnds.getInNode();
			// check that the nodes belong to this subgraph.
			retVal = this.containsConnection(oneNode, twoNode);
		}
		return retVal;
	}

	public Iterator<BaseCompoundNode> topNodeIterator(){
		return this.topNodeSet.iterator();
	}
	
	public int getNumTopNodes(){
		return this.topNodeSet.size();
	}
	
	/**
	 * Checks if this SubGraph contains the RootNode of the CompoundGraph.
	 * @return true if the rootNode is contained in this SubGraph.
	 */
	public boolean containsRoot () 
	{
		 return this.containsNode(this.getSuperGraph().getRootNode()) ;
	}

	public final void addNode(BaseCompoundNode node) {
		this.nodeSet.add(node);
	}
	
}
