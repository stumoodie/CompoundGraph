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
package uk.ac.ed.inf.graph.impl;

import java.util.Iterator;
import java.util.SortedSet;

import org.apache.log4j.Logger;

import uk.ac.ed.inf.graph.basic.listeners.GraphStructureChangeType;
import uk.ac.ed.inf.graph.basic.listeners.INodeChangeListener;
import uk.ac.ed.inf.graph.basic.listeners.NodeStructureChangeListenee;
import uk.ac.ed.inf.graph.state.IRestorableGraphElement;
import uk.ac.ed.inf.graph.undirected.IUndirectedNode;
import uk.ac.ed.inf.graph.util.IFilterCriteria;
import uk.ac.ed.inf.graph.util.impl.ConnectedNodeIterator;
import uk.ac.ed.inf.graph.util.impl.EdgeSet;
import uk.ac.ed.inf.graph.util.impl.FilteredEdgeSet;

public final class Node implements IUndirectedNode<Node, Edge>, IRestorableGraphElement {
	private final Logger logger = Logger.getLogger(this.getClass());
	private final int index;
	private final Graph owningGraph;
	private final FilteredEdgeSet<Node, Edge> edgeSet;
	private boolean removed;
	private final NodeStructureChangeListenee<Node, Edge> listenableHelper;
	
	/**
	 * Creates a new node belonging to a graph. Note the node is not added to the
	 * graph in this constructor, this must be done elsewhere. Typically usign a node
	 * factory. 
	 * @param owningGraph The graph to which this node belongs.
	 * @param index The identifying index for the node, that
	 *  can be used to lookup this node from the graph.
	 *  @throws IllegalArgumentException if owningGraph is null.
	 *  @throws IllegalArgumentException if index is negative.
	 */
	Node(Graph owningGraph, int index){
		if(owningGraph == null) throw new IllegalArgumentException("owning graph cannot be null");
		if(index < 0) throw new IllegalArgumentException("index must be a positive integer");
		
		this.index = index;
		this.owningGraph = owningGraph;
		this.edgeSet = new FilteredEdgeSet<Node, Edge>(new EdgeSet<Node, Edge>(), new IFilterCriteria<Edge>(){
			public boolean matched(Edge edge){
				return !edge.isRemoved();
			}
		});
		this.removed = false;
		this.listenableHelper = new NodeStructureChangeListenee<Node, Edge>(this);
		if(logger.isDebugEnabled()){
			this.logger.debug("Created new node idx=" + index);
		}
	}
	
	
	public Iterator<Node> connectedNodeIterator() {
		return new ConnectedNodeIterator<Node, Edge>(this, this.edgeSet.iterator());
	}

	public int getDegree() {
		int degree = 0;
		for(Edge e : this.edgeSet){
			if(e.isSelfEdge()){
				degree += 2;
			}
			else{
				degree++;
			}
		}
		return degree;
	}

	public Iterator<Edge> edgeIterator() {
		return this.edgeSet.iterator();
	}

	public SortedSet<Edge> getEdgesWith(Node other) {
		return this.edgeSet.getEdgesWith(this, other);
	}

	public Graph getGraph() {
		return this.owningGraph;
	}

	public int getIndex() {
		return this.index;
	}

	public boolean hasEdgeWith(Node other) {
		return this.edgeSet.contains(this, other) || this.edgeSet.contains(other, this);
	}

	void addEdge(Edge edge){
		this.edgeSet.add(edge);
		this.listenableHelper.notifyNodeStructureChange(GraphStructureChangeType.ADDED, edge);
		if(logger.isDebugEnabled()){
			this.logger.debug("node(" + index + "): added edge=" + edge);
		}
	}
	
	void removedEdge(Edge edge){
		this.listenableHelper.notifyNodeStructureChange(GraphStructureChangeType.DELETED, edge);
	}
	
	public int compareTo(Node o) {
		return this.index < o.getIndex() ? -1 : (this.index == o.getIndex() ? 0 : 1);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + index;
		result = prime * result
				+ ((owningGraph == null) ? 0 : owningGraph.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Node other = (Node) obj;
		if (index != other.index)
			return false;
		if (owningGraph == null) {
			if (other.owningGraph != null)
				return false;
		} else if (!owningGraph.equals(other.owningGraph))
			return false;
		return true;
	}


	public boolean isRemoved() {
		return removed;
	}


	public void markRemoved(boolean removeFlag) {
		if(logger.isDebugEnabled()){
			this.logger.debug("node id(" + this.index + "), changing removal status to: " + removeFlag);
		}
		this.removed = removeFlag;
		this.listenableHelper.setListenersEnabled(!removeFlag);
	}
	
	@Override
	public String toString(){
		StringBuilder buf = new StringBuilder(this.getClass().getSimpleName());
		buf.append("(idx=");
		buf.append(this.index);
		buf.append(",removed=");
		buf.append(this.removed);
		buf.append(")");
		return buf.toString();
	}


	public void addNodeChangeListener(INodeChangeListener<Node, Edge> listener) {
		this.listenableHelper.addNodeChangeListener(listener);
	}


	public Iterator<INodeChangeListener<Node, Edge>> nodeChangeListenerIterator() {
		return this.listenableHelper.nodeChangeListenerIterator();
	}


	public void removeNodeChangeListener(INodeChangeListener<Node, Edge> listener) {
		this.listenableHelper.removeNodeChangeListener(listener);
	}
}
