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
package uk.ac.ed.inf.graph.directed;

import uk.ac.ed.inf.graph.basic.IBasicGraph;


public interface IDirectedGraph<
		N extends IDirectedNode<N, ? extends IDirectedEdge<N, ?>>,
		E extends IDirectedEdge<N, E>
> extends IBasicGraph<N, E> {

	/**
	 * Tests if there is one or more directed edges from the <code>outNode</code> to the <code>inNode</code>.
	 * @param outNode the node the edge comes out from, can be null.
	 * @param inNode the node the edge goes into, can be null.
	 * @return true if there is such and edge, false otherwise. 
	 */
	boolean containsDirectedEdge(N outNode, N inNode);

	/**
	 * Test if there is one or more directed edge defined by the end pair <code>ends</code>.
	 * @param ends the pair of nodes to be tested, can be null.
	 * @return true if there is at least one edge between then, false otherwise.
	 */
	boolean containsDirectedEdge(IDirectedPair<? extends N, ? extends E> ends);
	
}
