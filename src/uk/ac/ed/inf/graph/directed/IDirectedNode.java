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

import java.util.Iterator;
import java.util.SortedSet;

import uk.ac.ed.inf.graph.basic.IBasicNode;


public interface IDirectedNode<
		N extends IDirectedNode<N, ? extends IDirectedEdge<N, ?>>,
		E extends IDirectedEdge<N, E>
> extends IBasicNode<N, E> {
	int getInDegree();
	
	int getOutDegree();
	
	/**
	 * Has at least one edge coming into this one from outNode 
	 * @param outNode
	 * @return
	 */
	boolean hasInEdgeFrom(N outNode);
	
	SortedSet<E> getInEdgesFrom(N outNode);  
	
	/**
	 * Has at least one edge going out from this node to inNode.
	 * @param inNode
	 * @return
	 */
	boolean hasOutEdgeTo(N inNode);
	
	SortedSet<E> getOutEdgesTo(N inNode);  

	/**
	 * Gets all edges connecting this node
	 * @return
	 */
	Iterator<E> getInEdgeIterator();
	
	/**
	 * Gets all edges going out from this node. 
	 * @return
	 */
	Iterator<E> getOutEdgeIterator();

	Iterator<N> getInNodeIterator();
	
	Iterator<N> getOutNodeIterator();
}
