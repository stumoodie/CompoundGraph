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

import org.jmock.Expectations;
import org.jmock.Mockery;

import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.IRootChildCompoundGraph;
import uk.ac.ed.inf.graph.compound.IRootCompoundNode;

public class EmptyGraphFixture {
	public static final int EXPECTED_ROOT_IDX = 0;

	private final Mockery mockery;
	
	private IRootCompoundNode mockRootNode;
	private IRootChildCompoundGraph mockChildgraphRoot;
	private ICompoundGraph mockCompoundGraph;
	
	public EmptyGraphFixture(Mockery mockery){
		this.mockery = mockery;
	}
	
	public Mockery getMockery(){
		return this.mockery;
	}
	
	
	public void initialiseFixture(){
		mockCompoundGraph = this.mockery.mock(ICompoundGraph.class, "mockCompoundGraph");
		mockRootNode = this.mockery.mock(IRootCompoundNode.class, "mockRootNode");
		this.mockChildgraphRoot = this.mockery.mock(IRootChildCompoundGraph.class, "mockChildgraphRoot");

		this.mockery.checking(new Expectations(){{
			allowing(mockCompoundGraph).getRoot(); will(returnValue(mockRootNode));
			
			allowing(mockRootNode).getGraph(); will(returnValue(mockCompoundGraph));
			allowing(mockRootNode).getParent(); will(returnValue(mockRootNode));
			allowing(mockRootNode).getRoot(); will(returnValue(mockRootNode));
			allowing(mockRootNode).getIndex(); will(returnValue(EXPECTED_ROOT_IDX));
			allowing(mockRootNode).childIterator(); will(returnIterator(new ICompoundGraphElement[0]));
			allowing(mockRootNode).ancestorIterator(); will(returnIterator(mockRootNode));
			allowing(mockRootNode).preOrderIterator(); will(returnIterator(mockRootNode));
			allowing(mockRootNode).getOutDegree(); will(returnValue(0));
			allowing(mockRootNode).getInDegree(); will(returnValue(0));
			allowing(mockRootNode).getDegree(); will(returnValue(0));
			allowing(mockRootNode).getLevel(); will(returnValue(0));
		
			allowing(mockChildgraphRoot).getRoot(); will(returnValue(mockRootNode));
			allowing(mockChildgraphRoot).numElements(); will(returnValue(0));
			allowing(mockChildgraphRoot).numEdges(); will(returnValue(0));
			allowing(mockChildgraphRoot).numNodes(); will(returnValue(0));
			allowing(mockChildgraphRoot).getSuperGraph(); will(returnValue(mockCompoundGraph));
		
		}});
	}
	
	public ICompoundGraph getCompoundGraph(){
		return this.mockCompoundGraph;
	}
}
