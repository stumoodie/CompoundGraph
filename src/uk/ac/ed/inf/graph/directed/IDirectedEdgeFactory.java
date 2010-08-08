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

import uk.ac.ed.inf.graph.basic.IBasicEdgeFactory;

public interface IDirectedEdgeFactory<
		N extends IDirectedNode<N, ? extends IDirectedEdge<N, ?>>,
		E extends IDirectedEdge<N, E>
> extends IBasicEdgeFactory<N, E> {

	/**
	 * Sets the nodes to be used to create the edge.
	 * @param outNode outNode, cannot be null.
	 * @param inNode inNode, cannot be null.
	 * @throws IllegalArgumentException if <code>isValidNodePair(outNode, inNode) == false</code>.
	 */
	void setPair(N outNode, N inNode);

	/**
	 * Gets the current node pair.
	 * @return the current node pair, or null if no node pair has been set yet.
	 */
	IDirectedPair<N, E> getCurrentNodePair();
}
