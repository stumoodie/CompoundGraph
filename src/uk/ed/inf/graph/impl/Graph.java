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
package uk.ed.inf.graph.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import uk.ed.inf.graph.basic.IBasicEdge;
import uk.ed.inf.graph.basic.IBasicPair;
import uk.ed.inf.graph.basic.IBasicSubgraph;
import uk.ed.inf.graph.basic.IModifiableGraph;
import uk.ed.inf.graph.basic.listeners.GraphStructureChangeListenee;
import uk.ed.inf.graph.basic.listeners.GraphStructureChangeType;
import uk.ed.inf.graph.basic.listeners.IGraphChangeListener;
import uk.ed.inf.graph.state.GraphStateHandler;
import uk.ed.inf.graph.state.IGraphState;
import uk.ed.inf.graph.state.IRestorableGraph;
import uk.ed.inf.graph.undirected.IUndirectedGraph;
import uk.ed.inf.graph.undirected.IUndirectedPair;
import uk.ed.inf.graph.util.IFilterCriteria;
import uk.ed.inf.graph.util.INodeEdgeFilterCriteria;
import uk.ed.inf.graph.util.IndexCounter;
import uk.ed.inf.graph.util.impl.EdgeFromNodeIterator;
import uk.ed.inf.graph.util.impl.FilteredIterator;


public final class Graph implements IUndirectedGraph<Node, Edge>, IRestorableGraph<Node, Edge>,
			IModifiableGraph<Node, Edge> {
	private final Logger logger = Logger.getLogger(this.getClass()); 
	private final ArrayList<Node> nodeList;
	private final GraphStateHandler<Node, Edge> stateHandler;
	private SubgraphFactory copiedComponentsFact;
	private final GraphStructureChangeListenee<Node, Edge> listeneeHelper;
	private final IndexCounter nodeCounter;
	private final IndexCounter edgeCounter;
	
	public Graph(){
		this.nodeList = new ArrayList<Node>();
		this.stateHandler = new GraphStateHandler<Node, Edge>(this);
		this.copiedComponentsFact = this.subgraphFactory();
		this.listeneeHelper = new GraphStructureChangeListenee<Node, Edge>();
		this.nodeCounter = new IndexCounter(-1);
		this.edgeCounter = new IndexCounter(-1);
	}
	
	public Graph(Graph other){
		this();
		
		SubgraphFactory fact = other.subgraphFactory();
		for(Node node : this.nodeList){
			fact.addNode(node);
		}
		// since graph cannot contain dangling edges it is safe to only add the nodes
		// and then add in the incident edges by creating an induced subgraph.
		Subgraph copySubgraph = fact.createInducedSubgraph();
		this.copyHere(copySubgraph);
	}
	
	
	IndexCounter getNodeCounter(){
		return this.nodeCounter;
	}
	
	IndexCounter getEdgeCounter(){
		return this.edgeCounter;
	}
	
	public Node getNode(int index){
		if(this.containsNode(index) == false) throw new IllegalArgumentException("A node with this index must exist");
		return this.nodeList.get(index); 
	}
	
	public boolean canCreateEdges() {
		return true;
	}

	public boolean canCreateNodes() {
		return true;
	}

	public boolean canCreateSubgraphs() {
		return true;
	}

	public boolean canRemoveSubgraphs() {
		return true;
	}

	public boolean containsNode(Node node) {
		boolean retVal = false;
		if(node != null && !node.isRemoved()){
			retVal = this.containsNode(node.getIndex());
		}
		return retVal;
	}

	public boolean containsConnection(Node thisNode, Node thatNode) {
		boolean retVal = false;
		if(thisNode != null && !thisNode.isRemoved()
				&& thatNode != null && !thatNode.isRemoved()){
			retVal = thisNode.hasEdgeWith(thatNode);
		}
		return retVal;
	}

	public EdgeFactory edgeFactory() {
		return new EdgeFactory(this);
	}

	public NodeFactory nodeFactory() {
		return new NodeFactory(this);
	}

	public SubgraphFactory subgraphFactory() {
		return new SubgraphFactory(this);
	}

	public Iterator<Edge> edgeIterator() {
		return new EdgeFromNodeIterator<Node, Edge>(this.nodeIterator(), new EdgeFromNodeCriteria());
	}

	public Iterator<Node> nodeIterator() {
		return new FilteredIterator<Node>(this.nodeList.iterator(), new NodeFilter());
	}

	public int getNumEdges() {
		int cntr = 0;
		EdgeFromNodeIterator<Node, Edge> iter = new EdgeFromNodeIterator<Node, Edge>(this.nodeIterator(),
				new EdgeFromNodeCriteria());  
		while(iter.hasNext()){
			iter.next();
			cntr++;
		}
		return cntr;
	}

	public int getNumNodes() {
		int count = 0;
		for(Node node : this.nodeList){
			if(!node.isRemoved()){
				count++;
			}
		}
		return count;
	}

	public void removeSubgraph(IBasicSubgraph<? extends Node, ? extends Edge> subgraph) {
		this.logger.debug("entering method removeSubgraph");
		if(subgraph == null) throw new IllegalArgumentException("subgraph cannot be null");
		if(subgraph.getSuperGraph() != this) throw new IllegalArgumentException("The subgraph must belong to this graph");
		Set<Node> removedNodes = new HashSet<Node>();
		Set<Edge> removedEdges = new HashSet<Edge>();
		findEdgesToRemove(subgraph.edgeIterator(), removedEdges);
		findNodesToRemove(subgraph.nodeIterator(), removedNodes, removedEdges);
		doRemoval(removedNodes, removedEdges);
		this.logger.debug("exiting method removeSubgraph");
	}
	
	private void doRemoval(Set<Node> removedNodes, Set<Edge> removedEdges){
		for(Node node : removedNodes){
			node.markRemoved(true);
		}
		for(Edge edge : removedEdges){
			edge.markRemoved(true);
		}
		this.listeneeHelper.notifyNodeStructureChange(GraphStructureChangeType.DELETED, removedNodes);
		this.listeneeHelper.notifyEdgeStructureChange(GraphStructureChangeType.DELETED, removedEdges);
	}

	private void findEdgesToRemove(Iterator<? extends Edge> edgeIterator, Set<Edge> removedEdges){
		this.logger.debug("entering method removeEdges");
		while(edgeIterator.hasNext()){
			Edge edge = (Edge)edgeIterator.next();
//			edge.markRemoved(true);
			removedEdges.add(edge);
			this.logger.debug("removed edge: " + edge);
		}
		this.logger.debug("exiting method removeEdges");
	}
	
	private void findNodesToRemove(Iterator<? extends Node> nodeIterator, Set<Node> removedNodes, Set<Edge> removedEdges){
		this.logger.debug("entering method removeNodes");
		while(nodeIterator.hasNext()){
			Node node = (Node)nodeIterator.next();
			if(!this.containsNode(node)) throw new IllegalStateException("Node does not exist in super graph");
			// remove edges attached to node
			Iterator<Edge> edgeIterator = node.edgeIterator();
			final Set<Edge> edgeDeletionSet = new HashSet<Edge>();
			// first find edges then delete them. Cannot delete while
			// iterating as this modified the set being iterated upon
			// and cause a concurrency exception
			while(edgeIterator.hasNext()){
				edgeDeletionSet.add(edgeIterator.next());
			}
			for(Edge edge : edgeDeletionSet){
//				edge.markRemoved(true);
				removedEdges.add(edge);
				this.logger.debug("removed edge: " + edge);
			}
			// remove node
//			node.markRemoved(true);
			removedNodes.add(node);
			this.logger.debug("removed node: " + node);
		}
		this.logger.debug("exiting method removeNodes");
	}
	
	public boolean containsEdge(Edge edge) {
		return containsEdge(edge.getIndex());
	}

	public boolean containsEdge(int edgeIdx) {
		// set up iterator that filters out removed nodes
		EdgeFromNodeIterator<Node, Edge> iter = new EdgeFromNodeIterator<Node, Edge>(this.nodeIterator(), new EdgeFromNodeCriteria());
		boolean retVal = false;
		while(iter.hasNext()){
			IBasicEdge<Node, Edge> searchEdge = iter.next();
			if(searchEdge.getIndex() == edgeIdx){
				retVal = true;
				break;
			}
		}
		return retVal;
	}

	public boolean containsNode(int nodeIdx) {
		return nodeIdx < this.nodeList.size() && !this.nodeList.get(nodeIdx).isRemoved();
	}

	public Edge getEdge(int edgeIdx) {
		// set up iterator that filters out removed nodes
		EdgeFromNodeIterator<Node, Edge> iter = new EdgeFromNodeIterator<Node, Edge>(this.nodeIterator(), new EdgeFromNodeCriteria());
		Edge retVal = null;
		while(iter.hasNext()){
			IBasicEdge<Node, Edge> searchEdge = iter.next();
			if(searchEdge.getIndex() == edgeIdx){
				retVal = (Edge)searchEdge;
				break;
			}
		}
		if(retVal == null) throw new IllegalArgumentException("The index must refer to a node that exists in the graph");
		
		return retVal;
	}
	
	void addNewNode(Node newNode){
		if(newNode.getIndex() != this.nodeList.size()) throw new IllegalArgumentException("The index of the node must equal to its order of addition to the graph");
		if(newNode.isRemoved()) throw new IllegalArgumentException("The node cannot be marked as removed when added to the graph");
		this.nodeList.add(newNode);
		final Set<Node> changeSet = new HashSet<Node>();
		changeSet.add(newNode);
		this.listeneeHelper.notifyNodeStructureChange(GraphStructureChangeType.ADDED, changeSet);
	}

	void addNewEdge(Edge newEdge) {
		// added are not stored here, so this just registers the new node being added
		final Set<Edge> changeSet = new HashSet<Edge>();
		changeSet.add(newEdge);
		this.listeneeHelper.notifyEdgeStructureChange(GraphStructureChangeType.ADDED, changeSet);
	}

	@SuppressWarnings("unchecked")
	public boolean containsConnection(IBasicPair<? extends Node, ? extends Edge> ends) {
		boolean retVal = false;
		if(ends != null && ends instanceof IUndirectedPair){
			IUndirectedPair<? extends Node, ? extends Edge> undirectedEnds = (IUndirectedPair<? extends Node, ? extends Edge>)ends;
			Node thisNode = (Node)undirectedEnds.getOneNode();
			Node thatNode = (Node)undirectedEnds.getTwoNode();
			if(!thisNode.isRemoved() && !thatNode.isRemoved()){
				retVal = thisNode.hasEdgeWith(thatNode);
			}
		}
		return retVal;
	}
	
	public IGraphState<Node, Edge> getCurrentState() {
		return this.stateHandler.createGraphState();
	}

	public void restoreState(IGraphState<Node, Edge> previousState) {
		this.stateHandler.restoreState(previousState);
	}

	public boolean canCopyHere(IBasicSubgraph<? extends Node, ? extends Edge> subGraph) {
		return subGraph != null && subGraph.isInducedSubgraph();
	}

	
	public void copyHere(IBasicSubgraph<? extends Node, ? extends Edge> subGraph) {
		if(canCopyHere(subGraph)) throw new IllegalArgumentException("Cannot copy graph here");
		
		Map<Node, Node> nodeEquivalence = new HashMap<Node, Node>();
		NodeFactory nodeFactory = this.nodeFactory();
		Iterator<? extends Node> nodeIter = subGraph.nodeIterator();
		while(nodeIter.hasNext()){
			Node oldNode = nodeIter.next();
			Node newNode = nodeFactory.createNode();
			this.nodeList.add(newNode);
			nodeEquivalence.put(oldNode, newNode);
			this.copiedComponentsFact.addNode(newNode);
		}
		EdgeFactory edgeFactory = this.edgeFactory();
		Iterator<? extends Edge> edgeIter = subGraph.edgeIterator();
		while(edgeIter.hasNext()){
			Edge oldEdge = edgeIter.next();
			Node newNodeOne = nodeEquivalence.get(oldEdge.getConnectedNodes().getOneNode());
			Node newNodeTwo = nodeEquivalence.get(oldEdge.getConnectedNodes().getTwoNode());
			edgeFactory.setPair(newNodeOne, newNodeTwo);
			Edge newEdge = edgeFactory.createEdge();
			this.copiedComponentsFact.addEdge(newEdge);
		}
		this.listeneeHelper.notifyNodeStructureChange(GraphStructureChangeType.ADDED, this.copiedComponentsFact.getNodes());
		this.listeneeHelper.notifyEdgeStructureChange(GraphStructureChangeType.ADDED, this.copiedComponentsFact.getEdges());
	}

	private class NodeFilter implements IFilterCriteria<Node> {

		public boolean matched(Node testObj) {
			return !testObj.isRemoved();
		}
	}
	
	private class EdgeFromNodeCriteria implements INodeEdgeFilterCriteria<Node, Edge> {

		public boolean matchedEdge(Edge testEdge) {
			return !testEdge.isRemoved();
		}

		public boolean matchedNode(Node testNode) {
			return !testNode.isRemoved();
		}
		
	}

	public Subgraph getCopiedComponents() {
		return this.copiedComponentsFact.createSubgraph();
	}

	public void addGraphChangeListener(IGraphChangeListener<Node, Edge> listener) {
		listeneeHelper.addGraphChangeListener(listener);
	}

	public Iterator<IGraphChangeListener<Node, Edge>> modelChangeListenerIterator() {
		return listeneeHelper.modelChangeListenerIterator();
	}

	public void removeGraphChangeListener(IGraphChangeListener<Node, Edge> listener) {
		listeneeHelper.removeGraphChangeListener(listener);
	}
}
