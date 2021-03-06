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



/**
 * Factory that creates an edge within a particular child graph. It may work out the LCA node that the
 * edge should be added to. the newly created edge will be added to the child graoh returned by <code>getOwningChildGraph()</code>. 
 *  
 * @author smoodie
 *
  */
public interface ICompoundEdgeFactory {

	void setAttributeFactory(IElementAttributeFactory attributeFactory);
	
	IElementAttributeFactory getAttributeFactory();
	
	/**
	 * Get the compound graph that owns this factory.
	 * @return the graph, which cannot be null.
	 */
	ICompoundGraph getGraph();
	
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
	ICompoundGraphElement getParent();
	
	/**
	 * Creates a new directed edge from <code>outNode</code> to <code>inNode</code> which is
	 *  owned by the childCompoundGraph returned by <code>getOwningChildGraph()</code>.
	 * @return The newly created edge.
	 * @throw IllegalArgumentException if <code>canCreateEdge() == false</code>.  
	 */
	ICompoundEdge createEdge();
	
	boolean isValidNodePair(CompoundNodePair pair);

	/**
	 * Sets the nodes to be used to create the edge.
	 * @param outNode outNode, cannot be null.
	 * @param inNode inNode, cannot be null.
	 * @throws IllegalArgumentException if <code>isValidNodePair(outNode, inNode) == false</code>.
	 */
	void setPair(CompoundNodePair pair);

	CompoundNodePair getCurrentNodePair();
}
