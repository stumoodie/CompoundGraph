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
import static org.junit.Assert.assertNotNull;
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
import uk.ac.ed.inf.graph.compound.ICompoundGraphElementFactory;
import uk.ac.ed.inf.graph.compound.ICompoundGraphMoveBuilder;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ac.ed.inf.graph.compound.testfixture.ComplexGraphFixture;
import uk.ac.ed.inf.graph.compound.testfixture.IGraphTestFixture;

@RunWith(JMock.class)
public class CompoundGraphMoveBuilderTest {
	private Mockery mockery;
	private ICompoundGraphMoveBuilder testInstance;
	private IGraphTestFixture testFixture;
	private IGraphTestFixture destnFixture;
	private ISubCompoundGraph mockSubgraph;
	private ICompoundGraphElementFactory mockElementFactory;
	
	@Before
	public void setUp() throws Exception {
		this.mockery = new JUnit4Mockery();
		this.testFixture = new ComplexGraphFixture(mockery, "");
		this.testFixture.buildFixture();
		this.destnFixture = new ComplexGraphFixture(mockery, "destn_");
		this.destnFixture.buildFixture();
		this.mockElementFactory = this.mockery.mock(ICompoundGraphElementFactory.class, "mockElementFactory");
		this.mockSubgraph = this.mockery.mock(ISubCompoundGraph.class, "mockSubgraph");
		this.mockery.checking(new Expectations(){{
			allowing(destnFixture.getGraph().subgraphFactory()).createSubgraph(); will(returnValue(mockSubgraph));
		}});
		this.testInstance = new CompoundGraphMoveBuilder(this.destnFixture.getGraph().getRoot().getChildCompoundGraph(),
				this.mockElementFactory);
		this.mockery.checking(new Expectations(){{
			allowing(mockSubgraph).getSuperGraph(); will(returnValue(testFixture.getGraph()));
			allowing(mockSubgraph).isConsistentSnapShot(); will(returnValue(true));
			allowing(mockSubgraph).hasOrphanedEdges(); will(returnValue(false));
		}});
	}

	@After
	public void tearDown() throws Exception {
		this.mockery = null;
		this.testFixture = null;
		this.destnFixture = null;
		this.testInstance = null;
	}

	@Test(expected=PreConditionException.class)
	public void testCompoundGraphMoveBuilder() {
		new CompoundGraphMoveBuilder(null, this.mockElementFactory);
	}

	@Test
	public void testGetMovedComponents() {
		assertNotNull("moved components", this.testInstance.getMovedComponents());
	}

	@Test
	public void testGetDestinationChildGraph() {
		assertEquals("destn child graph", this.destnFixture.getRootNode().getChildCompoundGraph(), this.testInstance.getDestinationChildGraph());
	}

	@Test
	public void testGetSourceSubgraph() {
		assertNull("no src subgraph", this.testInstance.getSourceSubgraph());
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
		this.testInstance.setSourceSubgraph(mockSubgraph);
		assertEquals("expected subgraph", this.mockSubgraph, this.testInstance.getSourceSubgraph());
	}

	@Test
	public void testGetRemovedComponents() {
		assertNotNull("removed components", this.testInstance.getRemovedComponents());
	}

}
