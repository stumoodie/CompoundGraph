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
package uk.ac.ed.inf.graph.compound.newimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.ed.inf.designbycontract.PreConditionException;
import uk.ac.ed.inf.graph.compound.ICompoundGraphCopyBuilder;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElementFactory;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ac.ed.inf.graph.compound.testfixture.ComplexGraphFixture;

@RunWith(JMock.class)
public class CompoundGraphCopyBuilderWithInconsistentSrcTest {

	private Mockery mockery;

	private ICompoundGraphCopyBuilder testInstance;
	private ComplexGraphFixture testFixture;
	private ComplexGraphFixture destnFixture;
	private ISubCompoundGraph mockSrcSubgraph;
	private ICompoundGraphElementFactory mockElementFactory;
	
	@Before
	public void setUp() throws Exception {
		this.mockery = new JUnit4Mockery();

		this.testFixture = new ComplexGraphFixture(mockery, "");
		this.testFixture.buildFixture();
		// src graph
		this.mockSrcSubgraph = this.mockery.mock(ISubCompoundGraph.class, "mockSrcSubgraph");
		this.mockElementFactory = this.mockery.mock(ICompoundGraphElementFactory.class, "mockElementFactory");

		// dstn graph
		this.destnFixture = new ComplexGraphFixture(mockery, "destn");
		this.destnFixture.buildFixture();
		
		this.mockery.checking(new Expectations(){{
			allowing(mockSrcSubgraph).getSuperGraph(); will(returnValue(testFixture.getGraph()));
			allowing(mockSrcSubgraph).elementIterator(); will(returnIterator(testFixture.getNode1(), testFixture.getNode2(), testFixture.getEdge2(), testFixture.getNode3(), testFixture.getNode4(), testFixture.getNode5()));
			allowing(mockSrcSubgraph).edgeLastElementIterator(); will(returnIterator(testFixture.getNode1(), testFixture.getNode2(), testFixture.getNode3(), testFixture.getNode4(), testFixture.getNode5(), testFixture.getEdge2()));
			allowing(mockSrcSubgraph).hasOrphanedEdges(); will(returnValue(false));
			allowing(mockSrcSubgraph).isConsistentSnapShot(); will(returnValue(false));
			allowing(mockSrcSubgraph).containsRoot(); will(returnValue(false));
		}});
		
		this.testInstance = new CompoundGraphCopyBuilder(this.destnFixture.getGraph().getRoot().getChildCompoundGraph(), this.mockElementFactory);
		this.testInstance.setSourceSubgraph(mockSrcSubgraph);
	}

	@After
	public void tearDown() throws Exception {
		this.mockery = null;
		this.testInstance = null;
	}

	@Test
	public void testGetCopiedComponents() {
		assertNull("not copied contents", this.testInstance.getCopiedComponents());
	}

	@Test
	public void testGetDestinationChildGraph() {
		assertEquals("expected childGraph", this.destnFixture.getGraph().getRoot().getChildCompoundGraph(), this.testInstance.getDestinationChildGraph());
	}

	@Test
	public void testGetSourceSubgraph() {
		assertEquals("expected subgraph", this.mockSrcSubgraph, this.testInstance.getSourceSubgraph());
	}

	@Test(expected=PreConditionException.class)
	public void testMakeCopy(){
		this.testInstance.makeCopy();
		
	}
	
	@Test
	public void testCanCopyHere(){
		assertFalse("cannot copy", this.testInstance.canCopyHere());
	}
}
