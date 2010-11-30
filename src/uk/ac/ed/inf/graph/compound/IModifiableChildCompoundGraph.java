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
package uk.ac.ed.inf.graph.compound;

import java.util.Iterator;


public interface IModifiableChildCompoundGraph {

	/**
	 * Gets a new instance of a node factory used to add new nodes to this child graph.
	 * @return a new instance of the node factory.
	 */
	ICompoundNodeFactory nodeFactory();

	/**
	 * Gets a new edgeFactory. The factory is the only way that new edges can be added to
	 *  this graph.
	 * @return A new instance of the edge factory.
	 */
	ICompoundChildEdgeFactory edgeFactory();
	
	/**
	 * Gets a new copy builder to manipulate copying to this child graph
	 * @return a new instance of the copy builder.
	 */
	ICompoundGraphCopyBuilder newCopyBuilder();

	/**
	 * Gets a new move builder to manipulate moving subgraphs to this child graph. 
	 * @return a new instance of the move builder
	 */
	ICompoundGraphMoveBuilder newMoveBuilder();
	
	void addNode(ICompoundNode node);

	void addEdge(ICompoundEdge edge);

	Iterator<ICompoundGraphElement> unfilteredElementIterator();
}
