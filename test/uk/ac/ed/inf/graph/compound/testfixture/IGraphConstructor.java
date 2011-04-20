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

import uk.ac.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundNodeFactory;
import uk.ac.ed.inf.graph.compound.IRootChildCompoundGraph;
import uk.ac.ed.inf.graph.compound.IRootCompoundNode;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraphFactory;

public interface IGraphConstructor {

	IRootChildCompoundGraph createChildGraph(IRootCompoundNode node);
	
	ICompoundNodeFactory createNodeFactory(ICompoundGraph graph);
	
	ICompoundEdgeFactory createEdgeFactory(ICompoundGraph graph);

	ISubCompoundGraphFactory createSubgraphFactory(ICompoundGraph graph);

	IRootCompoundNode createRootNode(ICompoundGraph graph);
	
	ICompoundGraph createGraph();
	
	boolean buildGraph(ICompoundGraph graph);
	
	boolean buildRootNode(IRootCompoundNode node);
	
	boolean buildChildGraph(IRootChildCompoundGraph childGraph);
	
	boolean buildNodeFactory(ICompoundNodeFactory nodeFactory);
	
	boolean buildEdgeFactory(ICompoundEdgeFactory edgeFactory);

	boolean buildSubgraphFactory(ISubCompoundGraphFactory subgraphFactory);
}
