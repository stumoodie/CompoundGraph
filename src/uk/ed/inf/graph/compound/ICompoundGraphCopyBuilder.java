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

public interface ICompoundGraphCopyBuilder {

	ISubCompoundGraph getSourceSubgraph();
	
	IChildCompoundGraph getDestinationChildGraph();
	
	/**
	 * Sets the subgraph which is to be copied.
	 * @param sourceSubCompoundGraph
	 */
	void setSourceSubgraph(ISubCompoundGraph sourceSubCompoundGraph);

	/**
	 * Sets the child compound graph that is to be copied to.
	 * @param childCompoundGraph
	 */
	void setDestinatChildCompoundGraph(IChildCompoundGraph childCompoundGraph);

	/**
	 * Make a copy of subgraph into the destination graph
	 */
	void makeCopy();

	/**
	 * Gets the copied nodes and edges that were created in the destination graph as a
	 * subgraph of the destination graph.
	 * @return The subgraph of copied nodes, which will be empty of no nodes are copied.
	 */
	ISubCompoundGraph getCopiedComponents();
}