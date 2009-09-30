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
/**
 * 
 */
package uk.ed.inf.graph.basic.listeners;

import java.util.Iterator;
import java.util.Set;

import uk.ed.inf.graph.basic.IBasicEdge;
import uk.ed.inf.graph.basic.IBasicNode;



/**
 * Reports an event that has changed the node structure of a Model.
 * @author smoodie
 *
 */
public interface IGraphEdgeChangeEvent<
		N extends IBasicNode<N, ? extends IBasicEdge<N, ?>>,
		E extends IBasicEdge<N, E>
> {
	/**
	 * Determines what the change to the model's content was. 
	 * @return the change type.
	 */
	GraphStructureChangeType getChangeType();
	
	/**
	 * Returns the edges that were changed.
	 * @return the edges that were changed.
	 */
	Set<E> getChangedEdges();
	
	/**
	 * Gets the number of edges that have changed.
	 * @return the number of changed edges.
	 */
	int numChangedEdges();
	
	/**
	 * Returns an iterator to the changed edges. This is potentially a more efficient
	 * way of accessing the edges. 
	 * @return an iterator of the collection of changed edges.
	 */
	Iterator<E> changedEdgeIterator();
}