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

import uk.ed.inf.graph.directed.IDirectedEdgeFactory;

/**
 * Factory that creates an edge within a particular child graph. It may work out the LCA node that the
 * edge should be added to. the newly created edge will be added to the child graoh returned by <code>getOwningChildGraph()</code>. 
 *  
 * @author smoodie
 *
 * @param <N> the node
 * @param <E> the edge
 */
public interface ICompoundEdgeFactory<
		N extends ICompoundNode<N, ? extends ICompoundEdge<N, ?>>,
		E extends ICompoundEdge<N, E>
> extends IDirectedEdgeFactory<N, E> {

	/**
	 * Get the compound graph that owns this factory.
	 * @return the graph, which cannot be null.
	 */
	ICompoundGraph<N, E> getGraph();
	
	/**
	 * Tests if the edge can be created based on the node pair.
	 * @return true if this is the case, false otherwise. 
	 */
	boolean canCreateEdge();
	
	/**
	 * The child graph that will contain any edges created by this factory. 
	 * @return The child graph, cannot be null.
	 * @throws IllegalStateException if <code>getPair() == null</code>. 
	 */
	IChildCompoundGraph<N, E> getOwningChildGraph();
	
	/**
	 * Creates a new directed edge from <code>outNode</code> to <code>inNode</code> which is
	 *  owned by the childCompoundGraph returned by <code>getOwningChildGraph()</code>.
	 * @return The newly created edge.
	 * @throw IllegalArgumentException if <code>canCreateEdge() == false</code>.  
	 */
	E createEdge();
}
