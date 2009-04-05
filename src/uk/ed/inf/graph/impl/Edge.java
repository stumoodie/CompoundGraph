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

import org.apache.log4j.Logger;

import uk.ed.inf.graph.basic.IBasicPair;
import uk.ed.inf.graph.state.IRestorableGraphElement;
import uk.ed.inf.graph.undirected.IUndirectedEdge;

public final class Edge implements IUndirectedEdge<Node, Edge>, IRestorableGraphElement {
	private final Logger logger = Logger.getLogger(this.getClass());
	private final int index;
	private final NodePair connectedNodes;
	private final Graph graph;
	private boolean removed;
	
	Edge(Graph graph, int index, Node oneNode, Node twoNode){
		this.index = index;
		this.graph = graph;
		this.connectedNodes = new NodePair(oneNode, twoNode);
		oneNode.addEdge(this);
		twoNode.addEdge(this);
		this.removed = false;
		this.logger.debug("Created new edge id=" + index + ", nodes(" + oneNode + ", " + twoNode + ")");
	}
	
	public NodePair getConnectedNodes() {
		return this.connectedNodes;
	}

	public Graph getGraph() {
		return this.graph;
	}

	public int getIndex() {
		return this.index;
	}

	public boolean isSelfEdge(){
		final Node oneNode = this.connectedNodes.getOneNode();
		final Node twoNode = this.connectedNodes.getTwoNode();
		return oneNode.equals(twoNode);
	}
	
	public int compareTo(Edge o) {
		return this.index < o.getIndex() ? -1 : (this.index == o.getIndex() ? 0 : 1);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((graph == null) ? 0 : graph.hashCode());
		result = prime * result + index;
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
		final Edge other = (Edge) obj;
		if (graph == null) {
			if (other.graph != null)
				return false;
		} else if (!graph.equals(other.graph))
			return false;
		if (index != other.index)
			return false;
		return true;
	}

	public boolean isRemoved() {
		return removed;
	}

	public void markRemoved(boolean markRemoved) {
		this.logger.debug("edge id(" + this.index + "), changing removal status to: " + markRemoved);
		this.removed = markRemoved;
	}

	public boolean hasEnds(IBasicPair<? super Node, ? super Edge> ends) {
		return this.connectedNodes.equals(ends);
	}

	@Override
	public String toString(){
		return "[" + this.getClass().getName() + ": index=" + this.index + ", removed=" + this.removed + "]";
	}
}
