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

	/**
	 * Tests whether the subGraph can be copied to this graph. To be true the subgraph must be an induced subgraph
	 *  that is a consistent of the super graph. It must also be not null.
	 * @param subGraph the subgraph to test, can be null. 
	 * @return true if the subgraph is valid to copy from, false otherwise.
	 * @deprecated use setSourceSubgraph() then canCopyHere()
	 */
	boolean canCopyHere(ISubCompoundGraph subgraph);

	/**
	 * Tests whether the subGraph can be copied to this graph. To be true the subgraph must be an induced subgraph
	 *  that is a consistent of the super graph. It must also be not null.
	 * @return true if the subgraph is valid to copy from, false otherwise.
	 */
	boolean canCopyHere();

	/**
	 * Copies a subgraph into this graph. Note that the subgraph can be from a different graph or subgraph
	 * of this graph, since the structure of the graph is copied not the nodes and edges instances
	 * themselves. Note that the subgraph must be valid to be copied.
	 * @param subGraph the subgraph to copy
	 */
	void copyHere(ISubCompoundGraph subgraph);
	
	/**
	 * Retrieves the nodes and edges created in this graph by the last copy operation. The subgraph
	 * is <b>not</b> guaranteed to be a consistent snapshot of this graph.   If not copy operation has
	 * been performed then an empty subgraph will be returned.
	 * @return the subgraph of copied components, or an empty subgraph if no copy operation has been performed. 
	 */
	ISubCompoundGraph getCopiedComponents();
	
	/**
	 * Gets the subgraph that was the source for the last copy.
	 * @return
	 */
	ISubCompoundGraph getSourceSubgraph();
	
	/**
	 * Gets the child graph that is the destination for the copy  
	 * @return the destination child graph which cannot be null
	 */
	IChildCompoundGraph getDestinationChildGraph();
	
	/**
	 * Sets the subgraph which is to be copied.
	 * @param sourceSubCompoundGraph
	 */
	void setSourceSubgraph(ISubCompoundGraph sourceSubCompoundGraph);

	/**
	 * Make a copy of subgraph into the destination graph
	 */
	void makeCopy();

}