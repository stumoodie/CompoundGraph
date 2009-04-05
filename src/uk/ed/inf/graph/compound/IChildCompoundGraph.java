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



public interface IChildCompoundGraph<
		N extends ICompoundNode<N, ? extends ICompoundEdge<N, ?>>,
		E extends ICompoundEdge<N, E>
> extends ICompoundGraph<N, E> {
	
	/**
	 * Gets the compound graph that owns this child graph. 
	 * @return the owning compound graph, which cannot be null.
	 */
	ICompoundGraph<N, E> getSuperGraph();
	
//	/**
//	 * Tests if the subgraph contains nodes or edges that are contained by this
//	 * child graph or its children.
//	 * @param subgraph the subgraph to test, which can be null.
//	 * @return true if the subgraph intersects with this child graph, false otherwise.
//	 */
//	boolean intersects(ISubCompoundGraph<? extends N, ? extends E> subgraph);
}
