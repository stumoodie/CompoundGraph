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
package uk.ed.inf.graph.compound.newimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ed.inf.graph.compound.ICompoundGraph;
import uk.ed.inf.graph.compound.ICompoundNode;

@RunWith(JMock.class)
public class CompoundEdgeFactoryTest {
	private Mockery mockery = new JUnit4Mockery();
	
	private ICompoundEdgeFactory testEdgeFactory ;

	private static ICompoundGraph mockCompoundGraph ;
	private static ICompoundNode mockOneNode ;
	private static ICompoundNode mockTwoNode ;
	private static ICompoundNode otherOneNode ;
	private static ICompoundNode otherTwoNode ;
	
	private static final int COMPOUND_NODE_INDEX_ONE = 1 ;
	private static final int COMPOUND_NODE_INDEX_TWO = 2 ;
	private static final int COMPOUND_NODE_INDEX_THREE = 3 ;
	private static final int COMPOUND_NODE_INDEX_FOUR = 2 ;
	private static final int COMPOUND_NODE_INDEX_FIVE = 3 ;

	@Before
	public void setUp() throws Exception {
		mockCompoundGraph = this.mockery.mock(ICompoundGraph.class, "mockCompoundGraph");
		mockOneNode = this.mockery.mock(ICompoundNode.class, "mockOneNode");
		mockTwoNode = this.mockery.mock(ICompoundNode.class, "mockTwoNode");
		otherOneNode = this.mockery.mock(ICompoundNode.class, "otherOneNode");
		otherTwoNode = this.mockery.mock(ICompoundNode.class, "otherTwoNode");

		this.mockery.checking(new Expectations(){{
			allowing(mockOneNode).getIndex(); will(returnValue(COMPOUND_NODE_INDEX_TWO));
			allowing(mockTwoNode).getIndex(); will(returnValue(COMPOUND_NODE_INDEX_THREE));

			allowing(otherOneNode).getIndex(); will(returnValue(COMPOUND_NODE_INDEX_FOUR));
			allowing(otherTwoNode).getIndex(); will(returnValue(COMPOUND_NODE_INDEX_FIVE));
		}});
		
		
		testEdgeFactory = new CompoundEdgeFactory (mockCompoundGraph) ;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Ignore @Test
	public final void testCiEdgeFactory() {
		fail("Not yet implemented"); // TODO
	}

	@Ignore @Test
	public final void testSetPair() {
		testEdgeFactory.setPair(otherOneNode, otherTwoNode) ;
		fail("Not yet implemented"); // TODO
	}

	@Ignore @Test
	public final void testCreateEdge() {
		this.testEdgeFactory.setPair(mockOneNode, mockTwoNode);
		ICompoundEdge generatedEdge = testEdgeFactory.createEdge() ;//newEdge(mockChildCompoundGraph, COMPOUND_NODE_INDEX_ONE, mockOneNode, mockTwoNode) ;
		assertEquals ( "get graph" , mockCompoundGraph , generatedEdge.getGraph() ) ;
		assertEquals ( "get index" , COMPOUND_NODE_INDEX_ONE , generatedEdge.getIndex() ) ;
	}

	@Ignore @Test
	public final void testGetGraph() {
		assertEquals ( "get graph" , mockCompoundGraph , testEdgeFactory.getGraph()) ;
	}

}
