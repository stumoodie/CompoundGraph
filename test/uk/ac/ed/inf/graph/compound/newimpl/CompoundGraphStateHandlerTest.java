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
import static org.junit.Assert.assertNotNull;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.ed.inf.bitstring.BitStringBuffer;
import uk.ac.ed.inf.bitstring.IBitString;
import uk.ac.ed.inf.graph.compound.IGraphRestoreStateAction;
import uk.ac.ed.inf.graph.compound.testfixture.ComplexGraphFixture;
import uk.ac.ed.inf.graph.compound.testfixture.IGraphTestFixture;
import uk.ac.ed.inf.graph.state.IGraphState;
import uk.ac.ed.inf.graph.state.IGraphStateHandler;

@RunWith(JMock.class)
public class CompoundGraphStateHandlerTest {
	private Mockery mockery;
	private IGraphStateHandler testInstance;
	private IGraphTestFixture testFixture;
	private final String expectedBitString = "{0, 1, 2, 3, 4, 5, 8, 9, 10}";
	private IGraphState mockState;
	private IBitString restoreBitString;
	
	
	@Before
	public void setUp() throws Exception {
		this.mockery = new JUnit4Mockery();
		this.testFixture = new ComplexGraphFixture(mockery, "");
		this.testFixture.setElementRemoved(ComplexGraphFixture.NODE6_ID, true);
		this.testFixture.setElementRemoved(ComplexGraphFixture.EDGE1_ID, true);
		this.testFixture.buildFixture();
		
		this.testInstance = new CompoundGraphStateHandler(this.testFixture.getGraph());
		this.mockState = this.mockery.mock(IGraphState.class, "mockState");
		int bitStringSize = this.testFixture.getGraph().numElements();
		BitStringBuffer buf = new BitStringBuffer(bitStringSize);
		buf.set(0, bitStringSize, true);
		buf.set(4, false);
		this.restoreBitString = buf.toBitString();
		this.mockery.checking(new Expectations(){{
			allowing(mockState).getGraph(); will(returnValue(testFixture.getGraph()));
			allowing(mockState).getElementStates(); will(returnValue(restoreBitString));
		}});
	}

	@After
	public void tearDown() throws Exception {
		this.mockery = null;
		this.testFixture = null;
		this.testInstance = null;
		this.mockState = null;
	}

	@Test
	public void testCompoundGraphStateHandler() {
		new CompoundGraphStateHandler(null);
	}

	@Test
	public void testCreateGraphState() {
		IGraphState actualResult = this.testInstance.createGraphState();
		assertNotNull("expected not null", actualResult);
		assertEquals("expected bit string", expectedBitString , actualResult.getElementStates().toString());
	}

	@Test
	public void testGetGraph() {
		assertEquals("expected graph", this.testFixture.getGraph(), this.testInstance.getGraph());
	}

	@Test
	public void testRestoreState() {
		final Sequence rootNodeSequence = this.mockery.sequence("rootNodeSequence"); 
		final Sequence node1Sequence = this.mockery.sequence("node1Sequence"); 
		final Sequence node2Sequence = this.mockery.sequence("node2Sequence"); 
		final Sequence node3Sequence = this.mockery.sequence("node3Sequence"); 
		final Sequence node4Sequence = this.mockery.sequence("node4Sequence"); 
		final Sequence node5Sequence = this.mockery.sequence("node5Sequence"); 
		final Sequence node6Sequence = this.mockery.sequence("node6Sequence"); 
		final Sequence edge1Sequence = this.mockery.sequence("edge1Sequence"); 
		final Sequence edge2Sequence = this.mockery.sequence("edge2Sequence"); 
		final Sequence edge3Sequence = this.mockery.sequence("edge3Sequence"); 
		final Sequence edge4Sequence = this.mockery.sequence("edge4Sequence"); 
		this.mockery.checking(new Expectations(){{
			one(testFixture.getGraph()).notifyGraphRestoreChange(with(any(IGraphRestoreStateAction.class)));
			
//			one(testFixture.getRootNode()).markRemoved(true); inSequence(rootNodeSequence);
			one(testFixture.getRootNode()).markRemoved(false); inSequence(rootNodeSequence);
//			one(testFixture.getNode(ComplexGraphFixture.NODE1_ID)).markRemoved(true); inSequence(node1Sequence);
			one(testFixture.getNode(ComplexGraphFixture.NODE1_ID)).markRemoved(false); inSequence(node1Sequence);
//			one(testFixture.getNode(ComplexGraphFixture.NODE2_ID)).markRemoved(true); inSequence(node2Sequence);
			one(testFixture.getNode(ComplexGraphFixture.NODE2_ID)).markRemoved(false); inSequence(node2Sequence);
//			one(testFixture.getNode(ComplexGraphFixture.NODE3_ID)).markRemoved(true); inSequence(node3Sequence);
			one(testFixture.getNode(ComplexGraphFixture.NODE3_ID)).markRemoved(false); inSequence(node3Sequence);
//			one(testFixture.getNode(ComplexGraphFixture.NODE4_ID)).markRemoved(true); inSequence(node4Sequence);
			one(testFixture.getNode(ComplexGraphFixture.NODE4_ID)).markRemoved(true); inSequence(node4Sequence);
//			one(testFixture.getNode(ComplexGraphFixture.NODE5_ID)).markRemoved(true); inSequence(node5Sequence);
			one(testFixture.getNode(ComplexGraphFixture.NODE5_ID)).markRemoved(false); inSequence(node5Sequence);
//			one(testFixture.getNode(ComplexGraphFixture.NODE6_ID)).markRemoved(true); inSequence(node6Sequence);
			one(testFixture.getNode(ComplexGraphFixture.NODE6_ID)).markRemoved(false); inSequence(node6Sequence);
//			one(testFixture.getEdge(ComplexGraphFixture.EDGE1_ID)).markRemoved(true); inSequence(edge1Sequence);
			one(testFixture.getEdge(ComplexGraphFixture.EDGE1_ID)).markRemoved(false); inSequence(edge1Sequence);
//			one(testFixture.getEdge(ComplexGraphFixture.EDGE2_ID)).markRemoved(true); inSequence(edge2Sequence);
			one(testFixture.getEdge(ComplexGraphFixture.EDGE2_ID)).markRemoved(false); inSequence(edge2Sequence);
//			one(testFixture.getEdge(ComplexGraphFixture.EDGE3_ID)).markRemoved(true); inSequence(edge3Sequence);
			one(testFixture.getEdge(ComplexGraphFixture.EDGE3_ID)).markRemoved(false); inSequence(edge3Sequence);
//			one(testFixture.getEdge(ComplexGraphFixture.EDGE4_ID)).markRemoved(true); inSequence(edge4Sequence);
			one(testFixture.getEdge(ComplexGraphFixture.EDGE4_ID)).markRemoved(false); inSequence(edge4Sequence);
			
			ignoring(testFixture.getGraph().subgraphFactory());
		}});
		this.testInstance.restoreState(mockState);
		this.mockery.assertIsSatisfied();
	}

	@Test(expected=NullPointerException.class)
	public void testRestoreStateIsNull() {
		this.testInstance.restoreState(null);
	}

}
