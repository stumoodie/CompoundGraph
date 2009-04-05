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
package uk.ed.inf.graph.state;

import uk.ed.inf.bitstring.IBitString;
import uk.ed.inf.graph.basic.IBasicEdge;
import uk.ed.inf.graph.basic.IBasicGraph;
import uk.ed.inf.graph.basic.IBasicNode;


public interface IGraphState<
		N extends IBasicNode<N, ? extends IBasicEdge<N, ?>>,
		E extends IBasicEdge<N, E>
> {

	/**
	 * Get the graph that this momento belong to.
	 * @return The graph, cannot be null.
	 */
	IBasicGraph<N, E> getGraph();

	/**
	 * Get the states of the nodes as a bit string: [true => exists, false => removed]. Not that the
	 * bit string may be shorter than the number of nodes in the graph as the graph may have expanded
	 * since it was created. 
	 * @return The bit string representing the state of the nodes in graph.
	 */
	IBitString getNodeStates();
	
	/**
	 * Get the states of the edges as a bit string: [true => exists, false => removed]. Not that the
	 * bit string may be shorter than the number of edges in the graph as the graph may have expanded
	 * since it was created. 
	 * @return The bit string representing the state of the edges in the graph.
	 */
	IBitString getEdgeStates();
}
