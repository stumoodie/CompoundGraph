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

import org.apache.log4j.Logger;

import uk.ac.ed.inf.graph.basic.IBasicGraph;
import uk.ac.ed.inf.graph.basic.IBasicPair;
import uk.ac.ed.inf.graph.undirected.IUndirectedEdgeFactory;

public class EdgeFactory implements IUndirectedEdgeFactory<Node, Edge> {
	private final Logger logger = Logger.getLogger(this.getClass());
	private final Graph graph;
	private Node oneNode;
	private Node twoNode;
	
	EdgeFactory(Graph graph){
		this.graph = graph;
	}
	
	public void setPair(Node oneNode, Node twoNode){
		this.oneNode = oneNode;
		this.twoNode = twoNode;
	}
	
	public Edge createEdge() {
		if(logger.isDebugEnabled()){
			logger.debug("Creating edge between nodes(" + oneNode + ", " + twoNode + ")");
		}
		Edge retVal = new Edge(this.graph, this.graph.getEdgeCounter().nextIndex(), oneNode, twoNode);
		this.graph.addNewEdge(retVal);
		return retVal;
	}

	public IBasicGraph<Node, Edge> getGraph() {
		return this.graph;
	}

	public IBasicPair<Node, Edge> getCurrentNodePair() {
		return new NodePair(this.oneNode, this.twoNode);
	}

	public boolean canCreateEdge() {
		return this.getCurrentNodePair() != null;
	}

	public boolean isValidNodePair(Node thisNode, Node thatNode) {
		return thisNode != null && thatNode != null;
	}
}
