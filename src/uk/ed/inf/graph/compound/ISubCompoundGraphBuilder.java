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

import java.util.Set;


public interface ISubCompoundGraphBuilder<
		N extends ICompoundNode<N, ? extends ICompoundEdge<N, ?>>,
		E extends ICompoundEdge<N, E>
> {
	ICompoundGraph<N, E> getGraph();
	
	void setNodeList(Set<? extends N> nodeList);
	
	void setEdgeList(Set<? extends E> edgeList);
	
	void expandChildNodes();

	void addIncidentEdges();
	
	void buildSubgraph();
	
	/**
	 * Retrieve the created subgraph. If the subgraph has not been build then this method will fail.
	 * @return The created subgraph, cannot be null.
	 * @throws IllegalStateException if the subgraph has not been created by a call to <code>buildSubgraph</code>.
	 */
	ISubCompoundGraph<N, E> getSubgraph();
}
