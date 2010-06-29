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


public interface ISubCompoundGraphBuilder {
	ICompoundGraph getGraph();
	
	void setElementList(Set<? extends ICompoundGraphElement> elementList);
	
	void expandChildNodes();

	void addIncidentEdges();
	
	void buildSubgraph();
	
	void removeNonIncidentEdges();
	
	/**
	 * Retrieve the created subgraph. If the subgraph has not been build then this method will fail.
	 * @return The created subgraph, cannot be null.
	 * @throws IllegalStateException if the subgraph has not been created by a call to <code>buildSubgraph</code>.
	 */
	ISubCompoundGraph getSubgraph();
}
