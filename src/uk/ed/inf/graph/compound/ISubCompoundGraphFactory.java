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
package uk.ed.inf.graph.compound;

import java.util.Iterator;

public interface ISubCompoundGraphFactory {

	ICompoundGraph getGraph();

	ISubCompoundGraph createSubgraph();
	
	ISubCompoundGraph createInducedSubgraph();

	ISubCompoundGraph createPermissiveInducedSubgraph();

	/**
	 * Add a node to the factory that will be used to compose the subgraph.
	 * @param node The node to be added. Cannot be null.
	 * @throws NullPointerException if <code>node</code> is null.
	 * @throws IllegalArgumentException if <code>node</code> does not belong
	 * the the same graph as the factory.
	 */
	void addNode(ICompoundNode node);
	
	/**
	 * Get the number of nodes added to the factory;
	 * @return the number of nodes.
	 */
	int numNodes();
	
	/**
	 * Add an edge to the factory to be used to compose the subgraph. 
	 * @param edge
	 * @throws NullPointerException if <code>edge</code> is null.
	 * @throws IllegalArgumentException if <code>edge</code> does not belong
	 * the the same graph as the factory.
	 */
	void addEdge(ICompoundEdge edge);
	
	/**
	 * The number of edges added to the factory.
	 * @return the number of edges.
	 */
	int numEdges();
	
	/**
	 * Iterate over the nodes that have been added to this factory.
	 * @return the node iterator, which cannot be null.
	 */
	Iterator<ICompoundNode> nodeIterator();
	
	/**
	 * Iterate over the edges that have been added to the factory. 
	 * @return the edge iterator that cannot be null.
	 */
	Iterator<ICompoundEdge> edgeIterator();
	
}
