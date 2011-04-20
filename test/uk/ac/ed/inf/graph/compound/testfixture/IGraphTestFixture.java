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
package uk.ac.ed.inf.graph.compound.testfixture;

import org.jmock.api.Action;

import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.IRootCompoundNode;

public interface IGraphTestFixture {

	void redefineNode(String id, INodeConstructor nodeConstructor);
	
	void redefineEdge(String id, IEdgeConstructor edgeConstructor);
	
	void redefineGraph(IGraphConstructor graphConstructor);
	
	ICompoundGraph getGraph();
	
	ICompoundNode getNode(String nodeId);
	
	ICompoundEdge getEdge(String edgeId);
	
	void buildFixture();

	void setElementRemoved(String elementId, boolean markRemoved);

	boolean isRemoved(String elementId);

	IRootCompoundNode getRootNode();

	Action setRemovalState(String elementId);
}
