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

import java.util.List;

import uk.ac.ed.inf.graph.util.IndexCounter;



public interface IModifiableCompoundGraph {

	/**
	 * Gets the node factory to add new nodes to the root child graph of this compound graph.
	 * @return A new instance of the node factory, which cannot be null.
	 */
	ICompoundNodeFactory nodeFactory();

	/**
	 * Gets an edge factory that works out the LCA of two nodes making up the edge
	 * and assigns it to the appropriate compound graph. This is more permissive than the
	 * edge factory from a ChildCompoundGraph, which requires to to know that the edge
	 * belongs in that child graph (i.e. its root node is the LCA of the out and in nodes 
	 * of the edge).  
	 * @return A new instance of the edge factory, which cannot be null.
	 */
	ICompoundEdgeFactory edgeFactory();


	/**
	 * Gets a subgraph factory used to create a subgraph of this graph.
	 * @return a new instance of the subgraph factory.
	 */
	ISubCompoundGraphFactory subgraphFactory();

//	/**
//	 * Gets a new instance of a subgraph factory used to create a subgraph of this graph.
//	 * @return a new instance of the subgraph factory.
//	 */
//	ISubGraphFactory newSimpleSubgraphFactory();

	/**
	 * Returns a new instance of the removal builder.
	 * @return the removal builder, which cannot be null.
	 */
	ISubgraphRemovalBuilder newSubgraphRemovalBuilder();

	/**
	 * Add a listener to listen for structural changes to the graph. 
	 * @param listener the listener, which should not be null
	 */
	void addGraphStructureChangeListener(IGraphStructureChangeListener listener);
	
	/**
	 * Removes listener to listen for structural changes to the graph. If the listener is not held by this graph then it does nothing. 
	 * @param listener the listener to remove, which should not be null
	 */
	void removeGraphStructureChangeListener(IGraphStructureChangeListener listener);
	
	/**
	 * Lists the listeners in use by this graph.
	 * @return the list of listeners, which can be empty if none exist.
	 */
	List<IGraphStructureChangeListener> getGraphStructureChangeListeners();

	/**
	 * Notify the graph that the graphs structure has changed. Clients of the Compound Graph library
	 * should not use this method, nor have any need to. 
	 * @param action the action describing what has changed. 
	 */
	void notifyGraphStructureChange(IGraphStructureChangeAction action);

	IndexCounter getIndexCounter();
}
