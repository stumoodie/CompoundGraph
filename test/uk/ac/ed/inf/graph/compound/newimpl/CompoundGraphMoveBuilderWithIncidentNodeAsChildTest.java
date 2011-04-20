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

import static org.hamcrest.Matchers.isOneOf;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.ed.inf.designbycontract.PreConditionException;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElementFactory;
import uk.ac.ed.inf.graph.compound.ICompoundGraphMoveBuilder;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ac.ed.inf.graph.compound.testfixture.ComplexGraphFixture;
import uk.ac.ed.inf.graph.compound.testfixture.IGraphTestFixture;

@RunWith(JMock.class)
public class CompoundGraphMoveBuilderWithIncidentNodeAsChildTest {
	private static final int NUM_TOP_ELEMENTS = 1;
	private Mockery mockery;
	private ICompoundGraphMoveBuilder testInstance;
	private IGraphTestFixture testFixture;
	private ISubCompoundGraph mockSrcSubgraph;
	private ICompoundGraphElementFactory mockElementFactory;
	
	@Before
	public void setUp() throws Exception {
		this.mockery = new JUnit4Mockery();
		this.testFixture = new ComplexGraphFixture(mockery, "");
		this.testFixture.buildFixture();
		this.mockElementFactory = this.mockery.mock(ICompoundGraphElementFactory.class, "mockElementFactory");
		final ISubCompoundGraph mockDestnSubgraph = this.mockery.mock(ISubCompoundGraph.class, "mockDestnSubgraph");
		this.mockery.checking(new Expectations(){{
			allowing(testFixture.getGraph().subgraphFactory()).createSubgraph(); will(returnValue(mockDestnSubgraph));
		}});
		this.testInstance = new CompoundGraphMoveBuilder(this.testFixture.getNode(ComplexGraphFixture.NODE6_ID).getChildCompoundGraph(),
				this.mockElementFactory);
		this.mockSrcSubgraph = this.mockery.mock(ISubCompoundGraph.class, "mockSrcSubgraph");
		this.mockery.checking(new Expectations(){{
			allowing(mockSrcSubgraph).getSuperGraph(); will(returnValue(testFixture.getGraph()));
			allowing(mockSrcSubgraph).topElementIterator(); will(returnIterator(testFixture.getNode(ComplexGraphFixture.NODE1_ID)));
			allowing(mockSrcSubgraph).numTopElements(); will(returnValue(NUM_TOP_ELEMENTS));
			allowing(mockSrcSubgraph).elementIterator(); will(returnIterator(testFixture.getNode(ComplexGraphFixture.NODE1_ID), testFixture.getNode(ComplexGraphFixture.NODE2_ID), testFixture.getEdge(ComplexGraphFixture.EDGE3_ID)));
			allowing(mockSrcSubgraph).edgeLastElementIterator(); will(returnIterator(testFixture.getNode(ComplexGraphFixture.NODE1_ID), testFixture.getNode(ComplexGraphFixture.NODE2_ID), testFixture.getEdge(ComplexGraphFixture.EDGE3_ID)));
			allowing(mockSrcSubgraph).hasOrphanedEdges(); will(returnValue(false));
			allowing(mockSrcSubgraph).isConsistentSnapShot(); will(returnValue(true));
			allowing(mockSrcSubgraph).containsRoot(); will(returnValue(false));
			allowing(mockSrcSubgraph).containsNode(with(isOneOf(testFixture.getNode(ComplexGraphFixture.NODE1_ID), testFixture.getNode(ComplexGraphFixture.NODE2_ID)))); will(returnValue(true));
			allowing(mockSrcSubgraph).containsNode(with(not(isOneOf(testFixture.getNode(ComplexGraphFixture.NODE1_ID), testFixture.getNode(ComplexGraphFixture.NODE2_ID))))); will(returnValue(false));
		}});
		this.testInstance.setSourceSubgraph(mockSrcSubgraph);
	}

	@After
	public void tearDown() throws Exception {
		this.mockery = null;
		this.testFixture = null;
		this.testInstance = null;
	}

	@Test(expected=PreConditionException.class)
	public void testCompoundGraphMoveBuilder() {
		new CompoundGraphMoveBuilder(null, null);
	}

	@Test
	public void testGetMovedComponents() {
//		final ISubCompoundGraphFactory destn_subgraphFactory = this.testFixture.getGraph().subgraphFactory();
//		final ISubCompoundGraph mockMovedSubgraph = this.mockery.mock(ISubCompoundGraph.class, "mockMovedSubgraph");
//		this.mockery.checking(new Expectations(){{
//			exactly(1).of(destn_subgraphFactory).createSubgraph(); will(returnValue(mockMovedSubgraph));
//		}});
		ISubCompoundGraph actualResult = this.testInstance.getMovedComponents();
		assertNotNull("moved components", actualResult);
//		assertEquals("expected subgraph", mockMovedSubgraph, actualResult);
	}

	@Test
	public void testGetDestinationChildGraph() {
		assertEquals("destn child graph", this.testFixture.getNode(ComplexGraphFixture.NODE6_ID).getChildCompoundGraph(), this.testInstance.getDestinationChildGraph());
	}

	@Test
	public void testGetSourceSubgraph() {
		assertEquals("expected src subgraph", this.mockSrcSubgraph, this.testInstance.getSourceSubgraph());
	}

	@Test
	public void testCanMoveHere() {
		assertFalse("cannot move", this.testInstance.canMoveHere());
	}

	@Test(expected=PreConditionException.class)
	public void testMakeMove() {
		this.testInstance.makeMove();
	}

	@Test
	public void testSetSourceSubgraph() {
		this.testInstance.setSourceSubgraph(mockSrcSubgraph);
		assertEquals("expected subgraph", this.mockSrcSubgraph, this.testInstance.getSourceSubgraph());
	}

	@Test
	public void testGetRemovedComponents() {
//		final ISubCompoundGraphFactory subgraphFactory = this.testFixture.getGraph().subgraphFactory();
//		final ISubCompoundGraph mockRemovedSubgraph = this.mockery.mock(ISubCompoundGraph.class, "mockRemovedSubgraph");
//		this.mockery.checking(new Expectations(){{
//			exactly(1).of(subgraphFactory).createSubgraph(); will(returnValue(mockRemovedSubgraph));
//		}});
		ISubCompoundGraph actualResult = this.testInstance.getRemovedComponents();
		assertNotNull("removed components", actualResult);
//		assertEquals("expected subgraph", mockRemovedSubgraph, actualResult);
	}

}
