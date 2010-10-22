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

import uk.ac.ed.inf.graph.basic.IBasicGraph;
import uk.ac.ed.inf.graph.undirected.IUndirectedNodeFactory;

public final class NodeFactory implements IUndirectedNodeFactory<Node, Edge> {
	private final Graph graph;
	
	NodeFactory(Graph graph){
		this.graph = graph;
	}
	
	public Node createNode() {
		Node retVal = new Node(this.graph, this.graph.getNodeCounter().nextIndex());
		this.graph.addNewNode(retVal);
		return retVal;
	}

	public IBasicGraph<Node, Edge> getGraph() {
		return this.graph;
	}

}