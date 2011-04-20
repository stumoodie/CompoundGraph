/*
  Licensed to the Court of the University of Edinburgh (UofE) under one
  or more contributor license agreements.  See the NOTICE file
  distributed with this work for additional information
  regarding copyright ownership.  The UofE licenses this file
  to you under the Apache License, Version 2.0 (the
  "License"); you may not use this file except in compliance
  with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing,
  software distributed under the License is distributed on an
  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
  KIND, either express or implied.  See the License for the
  specific language governing permissions and limitations
  under the License.
*/
package uk.ac.ed.inf.graph.compound;


public interface ISubgraphRemovalBuilder {

	/**
	 * Sets the subgraph to be removed.
	 * @param subgraph the subgraph to be removed, whoch cannot be null
	 */
	void setRemovalSubgraph(ISubCompoundGraph subgraph);
	
	/**
	 * Get subgraph to be removed
	 * @return the subgraph to be removed.
	 */
	ISubCompoundGraph getRemovalSubgraph();
	
	/**
	 * Tests if subgraph removal will succeed. To succeed the subgraph must belong to this graph,
	 *  it must be a consistent snapshot and cannot be null.
	 * @param subgraph the subgraph to be removed, which can be null.
	 * @return true if the subgraph will succeed, false otherwise. 
	 */
	boolean canRemoveSubgraph();
	
	/**
	 * Removes the nodes and edges defined in the subgraph from this graph. The subgraph must be consistent with
	 *  this graph and be a subgraph of this graph.
	 */ 
	void removeSubgraph();
	
	/**
	 * Gets the graph that owns this removal builder
	 * @return the graph, which cannot be null.
	 */
	ICompoundGraph getGraph();

}
