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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.ed.inf.designbycontract.PreConditionException;
import uk.ac.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElementFactory;
import uk.ac.ed.inf.graph.compound.ICompoundGraphMoveBuilder;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.IElementAttribute;
import uk.ac.ed.inf.graph.compound.IGraphStructureChangeAction;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraphFactory;
import uk.ac.ed.inf.graph.compound.testfixture.ComplexGraphFixture;
import uk.ac.ed.inf.graph.compound.testfixture.NodeElementAttribute;
import uk.ac.ed.inf.graph.compound.testfixture.IGraphTestFixture;

@RunWith(JMock.class)
public class CompoundGraphMoveBuilderWithNonDestnSharingSubgraphTest {
	private static final int NUM_TOP_ELEMENTS = 4;
	private Mockery mockery;
	private ICompoundGraphMoveBuilder testInstance;
	private IGraphTestFixture testFixture;
	private ISubCompoundGraph mockSrcSubgraph;
	private ICompoundGraphElementFactory mockElementFactory;
	private ISubCompoundGraph mockDestnSubgraph;
	
	@Before
	public void setUp() throws Exception {
		this.mockery = new JUnit4Mockery();
		this.testFixture = new ComplexGraphFixture(mockery, "");
		this.testFixture.buildFixture();
		this.mockElementFactory = this.mockery.mock(ICompoundGraphElementFactory.class, "mockElementFactory");
		mockDestnSubgraph = this.mockery.mock(ISubCompoundGraph.class, "mockDestnSubgraph");
		this.mockery.checking(new Expectations(){{
			allowing(testFixture.getGraph().subgraphFactory()).createSubgraph(); will(returnValue(mockDestnSubgraph));
		}});
		this.testInstance = new CompoundGraphMoveBuilder(this.testFixture.getNode(ComplexGraphFixture.NODE6_ID).getChildCompoundGraph(), mockElementFactory);
		this.mockSrcSubgraph = this.mockery.mock(ISubCompoundGraph.class, "mockSrcSubgraph");
		this.mockery.checking(new Expectations(){{
			allowing(mockSrcSubgraph).getSuperGraph(); will(returnValue(testFixture.getGraph()));
			allowing(mockSrcSubgraph).topElementIterator(); will(returnIterator(testFixture.getNode(ComplexGraphFixture.NODE2_ID),
					testFixture.getEdge(ComplexGraphFixture.EDGE4_ID), testFixture.getEdge(ComplexGraphFixture.EDGE2_ID),
					testFixture.getNode(ComplexGraphFixture.NODE3_ID), testFixture.getNode(ComplexGraphFixture.NODE5_ID)));
			allowing(mockSrcSubgraph).numTopElements(); will(returnValue(NUM_TOP_ELEMENTS));
			allowing(mockSrcSubgraph).elementIterator(); will(returnIterator(testFixture.getNode(ComplexGraphFixture.NODE2_ID),
					testFixture.getEdge(ComplexGraphFixture.EDGE4_ID), testFixture.getEdge(ComplexGraphFixture.EDGE3_ID),
					testFixture.getEdge(ComplexGraphFixture.EDGE2_ID),
					testFixture.getNode(ComplexGraphFixture.NODE3_ID), testFixture.getNode(ComplexGraphFixture.NODE4_ID),
					testFixture.getNode(ComplexGraphFixture.NODE5_ID), testFixture.getEdge(ComplexGraphFixture.EDGE4_ID)));
			allowing(mockSrcSubgraph).nodeIterator(); will(returnIterator(testFixture.getNode(ComplexGraphFixture.NODE2_ID),
					testFixture.getNode(ComplexGraphFixture.NODE3_ID), testFixture.getNode(ComplexGraphFixture.NODE4_ID),
					testFixture.getNode(ComplexGraphFixture.NODE5_ID)));
			allowing(mockSrcSubgraph).edgeLastElementIterator(); will(returnIterator(testFixture.getNode(ComplexGraphFixture.NODE2_ID),
					testFixture.getEdge(ComplexGraphFixture.EDGE3_ID), testFixture.getNode(ComplexGraphFixture.NODE3_ID),
					testFixture.getNode(ComplexGraphFixture.NODE5_ID), testFixture.getEdge(ComplexGraphFixture.EDGE2_ID),
					testFixture.getNode(ComplexGraphFixture.NODE4_ID), testFixture.getEdge(ComplexGraphFixture.EDGE4_ID)));
			allowing(mockSrcSubgraph).hasOrphanedEdges(); will(returnValue(false));
			allowing(mockSrcSubgraph).isConsistentSnapShot(); will(returnValue(true));
			allowing(mockSrcSubgraph).containsRoot(); will(returnValue(false));
			allowing(mockSrcSubgraph).containsNode(with(isOneOf(testFixture.getNode(ComplexGraphFixture.NODE2_ID), testFixture.getNode(ComplexGraphFixture.NODE3_ID),
					testFixture.getNode(ComplexGraphFixture.NODE4_ID), testFixture.getNode(ComplexGraphFixture.NODE5_ID)))); will(returnValue(true));
			allowing(mockSrcSubgraph).containsNode(with(not(isOneOf(testFixture.getNode(ComplexGraphFixture.NODE2_ID), testFixture.getNode(ComplexGraphFixture.NODE3_ID),
					testFixture.getNode(ComplexGraphFixture.NODE4_ID), testFixture.getNode(ComplexGraphFixture.NODE5_ID))))); will(returnValue(false));
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
		assertTrue("can move", this.testInstance.canMoveHere());
	}

	@Test
	public void testMakeMove() {
		final ICompoundNode destnNode = this.testFixture.getNode(ComplexGraphFixture.NODE6_ID);
		final IChildCompoundGraph destnChildNode = destnNode.getChildCompoundGraph();
		
		final ISubCompoundGraphFactory movedNodesSubgraphFactory = this.testFixture.getGraph().subgraphFactory();
		
		final ICompoundNode mockNode = this.mockery.mock(ICompoundNode.class, "mockNode");
		final IChildCompoundGraph mockNodeChildGraph = this.mockery.mock(IChildCompoundGraph.class, "mockNodeChildGraph");
		
		final ICompoundEdge mockEdge = this.mockery.mock(ICompoundEdge.class, "mockEdge");
		final IChildCompoundGraph mockEdgeChildGraph = this.mockery.mock(IChildCompoundGraph.class, "mockEdgeChildGraph");

//		mockDestnSubgraph = this.mockery.mock(ISubCompoundGraph.class, "mockDestnSubgraph");
		final ICompoundNode node2 = testFixture.getNode(ComplexGraphFixture.NODE2_ID);
		final ICompoundNode node3 = testFixture.getNode(ComplexGraphFixture.NODE3_ID);
		final ICompoundNode node4 = testFixture.getNode(ComplexGraphFixture.NODE4_ID);
		final ICompoundNode node5 = testFixture.getNode(ComplexGraphFixture.NODE5_ID);
		final ICompoundEdge edge2 = testFixture.getEdge(ComplexGraphFixture.EDGE2_ID);
		final ICompoundEdge edge3 = testFixture.getEdge(ComplexGraphFixture.EDGE3_ID);
		final ICompoundEdge edge4 = testFixture.getEdge(ComplexGraphFixture.EDGE4_ID);
		this.mockery.checking(new Expectations(){{
			allowing(mockNode).getChildCompoundGraph(); will(returnValue(mockNodeChildGraph));
			allowing(mockNode).getGraph(); will(returnValue(testFixture.getGraph()));
			allowing(mockNode).getAttribute(); will(returnValue(new NodeElementAttribute("mockNodeAtt")));
			
			allowing(mockEdge).getChildCompoundGraph(); will(returnValue(mockEdgeChildGraph));
			allowing(mockEdge).getGraph(); will(returnValue(testFixture.getGraph()));
			allowing(mockEdge).getAttribute(); will(returnValue(new NodeElementAttribute("mockNodeAtt")));
		
			exactly(1).of(node2).markRemoved(true);
			exactly(1).of(node3).markRemoved(true);
			exactly(1).of(node4).markRemoved(true);
			exactly(1).of(node5).markRemoved(true);
			exactly(1).of(edge3).markRemoved(true);
			exactly(1).of(edge4).markRemoved(true);
			exactly(1).of(edge2).markRemoved(true);
			
			exactly(14).of(movedNodesSubgraphFactory).addElement(with(any(ICompoundGraphElement.class)));
//			oneOf(movedNodesSubgraphFactory).createSubgraph(); will(returnValue(mockDestnSubgraph)); inSequence(subgraphSeq);
//			oneOf(movedNodesSubgraphFactory).createSubgraph(); will(returnValue(mockRemovalSubgraph)); inSequence(subgraphSeq);
//			oneOf(movedNodesSubgraphFactory).createSubgraph(); will(returnValue(mockDestnSubgraph)); inSequence(subgraphSeq);
			
//		}});
//		this.mockery.checking(new Expectations(){{
			exactly(5).of(mockElementFactory).setParent(with(destnNode));
			exactly(1).of(mockElementFactory).setParent(with(mockEdge));
			exactly(1).of(mockElementFactory).setParent(with(mockNode));
			exactly(7).of(mockElementFactory).setIndex(with(any(Integer.class)));
			exactly(7).of(mockElementFactory).setAttribute(with(any(IElementAttribute.class)));
			exactly(4).of(mockElementFactory).createNode(); will(returnValue(mockNode));
			exactly(3).of(mockElementFactory).createEdge(with(mockNode), with(mockNode)); will(returnValue(mockEdge));
			
			exactly(3).of(destnChildNode).addNode(with(any(ICompoundNode.class)));
			
			exactly(2).of(destnChildNode).addEdge(with(any(ICompoundEdge.class)));
			
			exactly(1).of(mockNodeChildGraph).addEdge(mockEdge);

			exactly(1).of(mockEdgeChildGraph).addNode(mockNode);
			
			one(testFixture.getGraph()).notifyGraphStructureChange(with(any(IGraphStructureChangeAction.class)));
			
			allowing(mockDestnSubgraph).elementIterator(); will(returnIterator(node2, node3, node4, node5, edge2, edge3, edge4));
			allowing(mockDestnSubgraph).getSuperGraph(); will(returnValue(testFixture.getGraph()));
		}});
		this.testInstance.makeMove();
		this.mockery.assertIsSatisfied();
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
