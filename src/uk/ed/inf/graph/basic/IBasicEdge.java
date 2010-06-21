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
package uk.ed.inf.graph.basic;


/**
 * An edge in a graph. By default this is undirected.
 * @author smoodie
 *
 * @param <N> The node class that must implement the INode class.
 * @param <E> The edge class that must implement this interface.
 */
public interface IBasicEdge extends IEdge, Comparable<IBasicEdge> {

	/**
	 * Get the owning graph of this edge.
	 * @return the owning graph.
	 */
	IBasicGraph getGraph();
	
	/**
	 * Get the index that uniquely identifies the edge within its owning graph.  
	 * @return The index, which is a While number (>-0).
	 */
	int getIndex();
	
	/**
	 * Get the ends of  
	 * @return
	 */
	IBasicPair getConnectedNodes();
	
	/**
	 * Is this a self edge, i.e. both ends are the same node. 
	 * @return <code>true</code> if removed.
	 */
	boolean isSelfEdge();
	
	/**
	 * Tests if this edge has these ends. Ignores the directions of the ends.
	 * @param ends The ends to be tested.
	 * @return true if the edge contains the ends, false otherwise. 
	 */
	boolean hasEnds(IBasicPair ends);
	
	/**
	 * Test if the edge has been removed from the graph.
	 * @return <code>true</code> if removed, false otherwise.
	 */
	boolean isRemoved();
}
