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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import uk.ac.ed.inf.graph.compound.ISubCompoundGraphBuilder;
import uk.ac.ed.inf.graph.directed.IDirectedPair;

public abstract class BaseSubCompoundGraphBuilder implements ISubCompoundGraphBuilder<BaseCompoundNode, BaseCompoundEdge> {
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private final Set<BaseCompoundNode> topNodeList;
	private final Set<BaseCompoundNode> allNodeList;
	private final Set<BaseCompoundEdge> edgeList;

	/**
	 * Construct the builder, providing it with a list of nodes and edges with which to populate
	 * the subgraph that will be constructed.
	 * @param graph the graph to which the subgraph will refer, cannot be null.
	 * @param topNodeList the list of nodes to be added to the subgraph, cannot be null.
	 * @param edgeList the list of edges to be added to the subgraph, cannot be null.
	 * @throws NullPointerException if any of the the parameters are null. 
	 */
	protected BaseSubCompoundGraphBuilder(){
		this.topNodeList = new HashSet<BaseCompoundNode>();
		this.allNodeList = new HashSet<BaseCompoundNode>();
		this.edgeList = new HashSet<BaseCompoundEdge>();
	}
	
	public void setNodeList(Set<? extends BaseCompoundNode> nodeList){
		this.topNodeList.addAll(nodeList);
		this.allNodeList.addAll(nodeList);
	}
	
	public void setEdgeList(Set<? extends BaseCompoundEdge> edgeList){
		this.edgeList.addAll(edgeList);
	}
	
	protected Set<BaseCompoundNode> getTopNodeList(){
	    return this.topNodeList;
	}
	
	protected Set<BaseCompoundNode> getNodeList(){
	    return this.allNodeList;
	}
	
    protected Set<BaseCompoundEdge> getEdgeList(){
        return this.edgeList;
    }
    
	/**
	 * Expand the nodes provided to the builder so that all the contents of their compound graphs
	 * are included in the new subgraph. This is done recursively so that the branches from each of
	 * the original nodes are fully expanded. Nodes that are already children are merged into their
	 * respective branches.
	 */
	public void expandChildNodes(){
		Set<BaseCompoundNode> initialNodes = new HashSet<BaseCompoundNode>(this.topNodeList);
		for(BaseCompoundNode compoundNode : initialNodes){
			Iterator<? extends BaseCompoundNode> iter = compoundNode.levelOrderIterator();
			addEdges(iter.next()); // get edges of the the current node's child graph
			while(iter.hasNext()){
				BaseCompoundNode childNode = iter.next();
				// prune children from top node list
				this.topNodeList.remove(childNode);
				// add node to allNode list
				this.allNodeList.add(childNode);
				// now add edges in this node's compound graph.
				addEdges(childNode);
			}
		}
	}

	private void addEdges(BaseCompoundNode node){
		Iterator<? extends BaseCompoundEdge> edgeIter = node.getChildCompoundGraph().edgeIterator();
		while(edgeIter.hasNext()){
			BaseCompoundEdge childEdge = edgeIter.next();
			this.edgeList.add(childEdge);
		}
	}
	
	/**
	 * Add the incident edges between the nodes already added to the subgraph. This is required to
	 * produce an induced subgraph. 
	 */
	public void addIncidentEdges(){
		for(BaseCompoundNode node : this.allNodeList){
//		final Set<BaseCompoundNode> expandedNodes = createExpandedNodes();
//		Iterator<BaseCompoundNode> nodeTreeIter = new NodeTreeIterator<BaseCompoundNode, BaseCompoundEdge>(this.nodeList.iterator());
//		while(nodeTreeIter.hasNext()){
//			BaseCompoundNode node = nodeTreeIter.next();
			if(logger.isDebugEnabled()){
				logger.debug("Investigating node: " + node);
			}
			// we only consider out edges as this will reduce the number edges we have
			// to consider twice. If an edge is directed and incident to the nodes in the
			// subgraph then we are guaranteed to traverse it once.
			// 
			Iterator<BaseCompoundEdge> edgeIter = node.getOutEdgeIterator();
			while(edgeIter.hasNext()){
				BaseCompoundEdge edge = edgeIter.next();
				if(logger.isDebugEnabled()){
					logger.debug("Testing edge: " + edge);
				}
				if(!this.edgeList.contains(edge)){
					logger.debug("Edge not observed before");
					// only do this if the edge is not already in the set of edges
					IDirectedPair<BaseCompoundNode, BaseCompoundEdge> ends = edge.getConnectedNodes();
					if(logger.isDebugEnabled()){
						logger.debug("Testing other node: " + ends.getInNode());
					}
					if(this.allNodeList.contains(ends.getInNode())){
//					if(expandedNodes.contains(ends.getInNode())){
						if(logger.isDebugEnabled()){
							logger.debug("Storing edge: " + edge + ", iNode= " + ends.getInNode());
						}
						// the edge links two nodes that will be in the subgraph so it is
						// incident and so we add it.
						this.edgeList.add(edge);
					}
					else{
						logger.debug("Node not present in subgraph");
					}
				}
				else{
					logger.debug("Edge already in subgraph");
				}
			}
		}
	}
	
//	private Set<BaseCompoundNode> createExpandedNodes(){
//		Set<BaseCompoundNode> retVal = new HashSet<BaseCompoundNode>();
//		Iterator<BaseCompoundNode> iter = new NodeTreeIterator<BaseCompoundNode, BaseCompoundEdge>(this.nodeList.iterator());
//		while(iter.hasNext()){
//			retVal.add(iter.next());
//		}
//		return retVal;
//	}

	protected abstract void addAdditionalNodes();
	
	protected abstract void addAdditionalEdges();
	
	/**
	 * Build the new subgraph, based on the previous processing of the initial nodes and edges.
	 */
	public void buildSubgraph() {
		newSubgraph();
		for(BaseCompoundNode node : this.topNodeList){
			getSubgraph().addTopNode(node);
		}
		for(BaseCompoundNode node : this.allNodeList){
			getSubgraph().addNode(node);
		}
		for(BaseCompoundEdge edge : this.edgeList){
			getSubgraph().addEdge(edge);
		}
		
		getSubgraph().buildComplete() ;
	}
	
	protected abstract void newSubgraph();
	
	/**
	 * Retrieve the created subgraph. If the subgraph has not been build then this method will fail.
	 * @return The created subgraph, cannot be null.
	 * @throws IllegalStateException if the subgraph has not been created by a call to <code>buildSubgraph</code>.
	 */
	public abstract BaseSubCompoundGraph getSubgraph();
	
	public abstract BaseCompoundGraph getGraph();

	public boolean hasAdditionalNodes() {
		return false;
	}
}
