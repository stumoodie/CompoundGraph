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



public interface IModifiableCompoundGraph {

	/**
	 * Gets the node factory to add new nodes to the root child graph of this compound graph.
	 * @return A new instance of the node factory, which cannot be null.
	 */
	ICompoundNodeFactory nodeFactory();

	/**
	 * Gets an edge factory that works out the LCA of two nodes making up the edge
	 * and assigns it to the appropriate compound graph. This is more permissive than the
	 * edge factory from a ChildCompoundGraph, which requires to to know that the edge
	 * belongs in that child graph (i.e. its root node is the LCA of the out and in nodes 
	 * of the edge).  
	 * @return A new instance of the edge factory, which cannot be null.
	 */
	ICompoundEdgeFactory edgeFactory();


	/**
	 * Gets a subgraph factory used to create a subgraph of this graph.
	 * @return a new instance of the subgraph factory.
	 */
	ISubCompoundGraphFactory subgraphFactory();

	ISubgraphRemovalBuilder newSubgraphRemovalBuilder();
}
