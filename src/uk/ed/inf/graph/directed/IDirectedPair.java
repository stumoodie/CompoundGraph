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
package uk.ed.inf.graph.directed;

import uk.ed.inf.graph.basic.IBasicPair;

public interface IDirectedPair<
		N extends IDirectedNode<N, ? extends IDirectedEdge<N, ?>>,
		E extends IDirectedEdge<N, E>
> extends IBasicPair<N, E>{

	/**
	 * Tests if the ends exist in this directed pair. Takes into account the direction of the ends so the
	 * reciprocal operations will not be equivalent, i.e.: <code>hasDirectedEnds(outNode, inNode) != hasDirectedEnds(inNode, outNode)</code>.
	 * @param outNode the out node (from which the edge leaves).
	 * @param inNode the in node (to which the edge goes to).
	 * @return
	 */
	boolean hasDirectedEnds(N outNode, N inNode);

	/**
	 * Get the node to which the end goes into. 
	 * @return The node, which will not be null.
	 */
	N getInNode();
	
	/**
	 * Get the node to which the end comes from. 
	 * @return The node, which will not be null.
	 */
	N getOutNode();

	/**
	 * Create a new node pair, with the nodes reversed as if this edge was pointing in the other direction.
	 * @return the new directed edge with the in and out nodes switched relative to this one. Cannot be null.
	 */
	IDirectedPair<N, E> reversedNodes();
	
}
