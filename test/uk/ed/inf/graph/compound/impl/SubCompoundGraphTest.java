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
package uk.ed.inf.graph.compound.impl;

import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ed.inf.graph.basic.IBasicEdge;
import uk.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ed.inf.graph.compound.archetypal.ArchetypalChildCompoundGraph;
import uk.ed.inf.graph.compound.archetypal.ArchetypalCompoundEdge;
import uk.ed.inf.graph.compound.archetypal.ArchetypalCompoundGraph;
import uk.ed.inf.graph.compound.archetypal.ArchetypalCompoundNode;
import uk.ed.inf.graph.compound.base.BaseCompoundEdge;
import uk.ed.inf.graph.compound.base.BaseCompoundNode;
import uk.ed.inf.graph.directed.IDirectedPair;

@RunWith(JMock.class)
public class SubCompoundGraphTest {
	private static final int EXPECTED_EMPTY_NODES = 0;
	private static final int EXPECTED_EMPTY_EDGES = 0;
	private static final int EXPECTED_NODE1_IDX = 1;
	private static final int EXPECTED_NODE2_IDX = 2;
	private static final int EXPECTED_NODE3_IDX = 3;
	private static final int EXPECTED_NODE4_IDX = 4;
	private static final int EXPECTED_NODE7_IDX = 7;
	private static final int EXPECTED_EDGE1_IDX = 1;
	private static final int EXPECTED_EDGE2_IDX = 2;
	private static final int EXPECTED_EDGE4_IDX = 4;
	private static final int EXPECTED_EDGE7_IDX = 7;
	private static final int EXPECTED_EDGE8_IDX = 8;
	private static final int EXPECTED_MISSING_IDX = 99;
	private static final int EXPECTED_NUM_EDGES = 4;
	private static final int EXPECTED_NUM_NODES = 2;
//	private static final int EXPECTED_LAST_NODE_IDX = 7;
//	private static final int EXPECTED_LAST_EDGE_IDX = 8;
	private static final int EXPECTED_SUB_EDGE1_IDX = 1;
	private static final int EXPECTED_SUB_EDGE2_IDX = 2;
	private static final int EXPECTED_SUB_EDGE3_IDX = 3;
	private static final int EXPECTED_SUB_EDGE4_IDX = 4;
	private static final int EXPECTED_POST_COPY_NUM_NODES = 4;
	private static final int EXPECTED_POST_COPY_NUM_EDGES = 5;
	

	private Mockery mockery = new JUnit4Mockery(){{
		setImposteriser(ClassImposteriser.INSTANCE);
	}};

	private ChildCompoundGraph testInstance;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		this.testInstance = null;
	}

	@Ignore @Test(expected=IllegalArgumentException.class)
	public final void testSubCigraphNull() {
		new ChildCompoundGraph(null);
	}

	@Ignore @Test
	public final void testEmptySubCigraph() {
		final CompoundNode mockRootNode = this.mockery.mock(CompoundNode.class, "mockRootNode");
		final ArchetypalCompoundGraph mockCigraph = this.mockery.mock(ArchetypalCompoundGraph.class, "mockCigraph");
		this.mockery.checking(new Expectations(){{
			atLeast(1).of(mockRootNode).getGraph(); will(returnValue(mockCigraph));
			allowing(mockRootNode).getRoot(); will(returnValue(mockRootNode));
		}});
		this.testInstance = new ChildCompoundGraph(mockRootNode);
		assertEquals("root node", mockRootNode, this.testInstance.getRootNode());
		assertEquals("empty nodes", EXPECTED_EMPTY_NODES, this.testInstance.getNumNodes());
		assertEquals("empty edges", EXPECTED_EMPTY_EDGES, this.testInstance.getNumEdges());
		assertEquals("super graph", mockCigraph, this.testInstance.getSuperGraph());
		assertFalse("empty edge iterator", this.testInstance.edgeIterator().hasNext());
		assertFalse("empty node iterator", this.testInstance.nodeIterator().hasNext());
		this.mockery.assertIsSatisfied();
	}

	@SuppressWarnings("unchecked")
	@Ignore @Test
	public final void testContainsDirectedEdge() {
		final CompoundNode mockRootNode = this.mockery.mock(CompoundNode.class, "mockRootNode");
		final ArchetypalCompoundNode mockNode1 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode1");
		final ArchetypalCompoundNode mockNode2 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode2");
		final ArchetypalCompoundNode mockNode3 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode3");
		final ArchetypalCompoundNode mockNode4 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode4");
		final ArchetypalCompoundNode mockNode7 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode7");
		final ArchetypalCompoundEdge mockEdge1 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge1");
		final ArchetypalCompoundEdge mockEdge2 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge2");
		final ArchetypalCompoundEdge mockEdge4 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge4");
		final ArchetypalCompoundEdge mockEdge7 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge7");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge1Pair = this.mockery.mock(IDirectedPair.class, "mockEdge1Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge2Pair = this.mockery.mock(IDirectedPair.class, "mockEdge2Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge4Pair = this.mockery.mock(IDirectedPair.class, "mockEdge4Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge7Pair = this.mockery.mock(IDirectedPair.class, "mockEdge7Pair");
		final ArchetypalCompoundGraph mockCigraph = this.mockery.mock(ArchetypalCompoundGraph.class, "mockCigraph");
		this.mockery.checking(new Expectations(){{
			allowing(mockRootNode).getGraph(); will(returnValue(mockCigraph));
			allowing(mockRootNode).getRoot(); will(returnValue(mockRootNode));
			
			allowing(mockNode1).compareTo(mockNode1); will(returnValue(0));
			allowing(mockNode1).compareTo(mockNode2); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode3); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode2).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode2).compareTo(mockNode2); will(returnValue(0));
			allowing(mockNode2).compareTo(mockNode3); will(returnValue(-1));
			allowing(mockNode2).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode2).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode3).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode3).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode3).compareTo(mockNode3); will(returnValue(0));
			allowing(mockNode3).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode3).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode4).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode3); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode4); will(returnValue(0));
			allowing(mockNode4).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode7).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode3); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode4); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode7); will(returnValue(0));
			
			allowing(mockEdge1).getConnectedNodes(); will(returnValue(mockEdge1Pair));
			allowing(mockEdge1).compareTo(mockEdge1); will(returnValue(0));
			allowing(mockEdge1).compareTo(mockEdge2); will(returnValue(-1));
			allowing(mockEdge1).compareTo(mockEdge4); will(returnValue(-1));
			allowing(mockEdge1).compareTo(mockEdge7); will(returnValue(-1));
			allowing(mockEdge1).isRemoved(); will(returnValue(false));
			allowing(mockEdge1).getIndex(); will(returnValue(EXPECTED_EDGE1_IDX));
			
			allowing(mockEdge2).getConnectedNodes(); will(returnValue(mockEdge2Pair));
			allowing(mockEdge2).compareTo(mockEdge1); will(returnValue(1));
			allowing(mockEdge2).compareTo(mockEdge2); will(returnValue(0));
			allowing(mockEdge2).compareTo(mockEdge4); will(returnValue(-1));
			allowing(mockEdge2).compareTo(mockEdge7); will(returnValue(-1));
			allowing(mockEdge2).isRemoved(); will(returnValue(false));
			allowing(mockEdge2).getIndex(); will(returnValue(EXPECTED_EDGE2_IDX));

			allowing(mockEdge4).getConnectedNodes(); will(returnValue(mockEdge4Pair));
			allowing(mockEdge4).compareTo(mockEdge1); will(returnValue(1));
			allowing(mockEdge4).compareTo(mockEdge2); will(returnValue(1));
			allowing(mockEdge4).compareTo(mockEdge4); will(returnValue(0));
			allowing(mockEdge4).compareTo(mockEdge7); will(returnValue(-1));
			allowing(mockEdge4).isRemoved(); will(returnValue(false));
			allowing(mockEdge4).getIndex(); will(returnValue(EXPECTED_EDGE4_IDX));

			allowing(mockEdge7).getConnectedNodes(); will(returnValue(mockEdge7Pair));
			allowing(mockEdge7).compareTo(mockEdge1); will(returnValue(1));
			allowing(mockEdge7).compareTo(mockEdge2); will(returnValue(1));
			allowing(mockEdge7).compareTo(mockEdge4); will(returnValue(1));
			allowing(mockEdge7).compareTo(mockEdge7); will(returnValue(0));
			allowing(mockEdge7).isRemoved(); will(returnValue(false));
			allowing(mockEdge7).getIndex(); will(returnValue(EXPECTED_EDGE7_IDX));
			
			allowing(mockEdge1Pair).hasDirectedEnds(with(same(mockNode1)), with(same(mockNode2))); will(returnValue(true));
			allowing(mockEdge1Pair).hasDirectedEnds(with(any(ArchetypalCompoundNode.class)), with(not(same(mockNode2)))); will(returnValue(false));
			allowing(mockEdge1Pair).hasDirectedEnds(with(not(same(mockNode1))), with(any(ArchetypalCompoundNode.class))); will(returnValue(true));
			
			allowing(mockEdge2Pair).hasDirectedEnds(with(same(mockNode2)), with(same(mockNode1))); will(returnValue(true));
			allowing(mockEdge2Pair).hasDirectedEnds(with(any(ArchetypalCompoundNode.class)), with(not(same(mockNode1)))); will(returnValue(false));
			allowing(mockEdge2Pair).hasDirectedEnds(with(not(same(mockNode2))), with(any(ArchetypalCompoundNode.class))); will(returnValue(true));
			
			allowing(mockEdge4Pair).hasDirectedEnds(with(same(mockNode2)), with(same(mockNode4))); will(returnValue(true));
			allowing(mockEdge4Pair).hasDirectedEnds(with(not(same(mockNode2))), with(not(same(mockNode4)))); will(returnValue(false));
			
			allowing(mockEdge7Pair).hasDirectedEnds(with(same(mockNode7)), with(same(mockNode3))); will(returnValue(true));
			allowing(mockEdge7Pair).hasDirectedEnds(with(not(same(mockNode7))), with(not(same(mockNode3)))); will(returnValue(false));
		}});
		this.testInstance = new ChildCompoundGraph(mockRootNode);
//		this.testInstance.addNewNode(mockNode1);
//		this.testInstance.addNewNode(mockNode2);
//		this.testInstance.addNewEdge(mockEdge1);
//		this.testInstance.addNewEdge(mockEdge2);
//		this.testInstance.addNewEdge(mockEdge4);
//		this.testInstance.addNewEdge(mockEdge7);
		assertTrue("has directed edge", this.testInstance.containsDirectedEdge(mockNode7, mockNode3));
		assertTrue("has directed edge", this.testInstance.containsDirectedEdge(mockNode2, mockNode4));
		assertFalse("has directed edge", this.testInstance.containsDirectedEdge(mockNode3, mockNode7));
		this.mockery.assertIsSatisfied();
	}

	@SuppressWarnings("unchecked")
	@Ignore @Test
	public final void testContainsEdgeINodeINode() {
		final CompoundNode mockRootNode = this.mockery.mock(CompoundNode.class, "mockRootNode");
		final ArchetypalCompoundNode mockNode1 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode1");
		final ArchetypalCompoundNode mockNode2 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode2");
		final ArchetypalCompoundNode mockNode3 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode3");
		final ArchetypalCompoundNode mockNode4 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode4");
		final ArchetypalCompoundNode mockNode7 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode7");
		final ArchetypalCompoundEdge mockEdge1 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge1");
		final ArchetypalCompoundEdge mockEdge2 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge2");
		final ArchetypalCompoundEdge mockEdge4 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge4");
		final ArchetypalCompoundEdge mockEdge7 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge7");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge1Pair = this.mockery.mock(IDirectedPair.class, "mockEdge1Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge2Pair = this.mockery.mock(IDirectedPair.class, "mockEdge2Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge4Pair = this.mockery.mock(IDirectedPair.class, "mockEdge4Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge7Pair = this.mockery.mock(IDirectedPair.class, "mockEdge7Pair");
		final ArchetypalCompoundGraph mockCigraph = this.mockery.mock(ArchetypalCompoundGraph.class, "mockCigraph");
		this.mockery.checking(new Expectations(){{
			allowing(mockRootNode).getGraph(); will(returnValue(mockCigraph));
			allowing(mockRootNode).getRoot(); will(returnValue(mockRootNode));
			
			allowing(mockNode1).compareTo(mockNode1); will(returnValue(0));
			allowing(mockNode1).compareTo(mockNode2); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode3); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode2).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode2).compareTo(mockNode2); will(returnValue(0));
			allowing(mockNode2).compareTo(mockNode3); will(returnValue(-1));
			allowing(mockNode2).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode2).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode3).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode3).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode3).compareTo(mockNode3); will(returnValue(0));
			allowing(mockNode3).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode3).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode4).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode3); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode4); will(returnValue(0));
			allowing(mockNode4).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode7).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode3); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode4); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode7); will(returnValue(0));
			
			allowing(mockEdge1).getConnectedNodes(); will(returnValue(mockEdge1Pair));
			allowing(mockEdge1).compareTo(mockEdge1); will(returnValue(0));
			allowing(mockEdge1).compareTo(mockEdge2); will(returnValue(-1));
			allowing(mockEdge1).compareTo(mockEdge4); will(returnValue(-1));
			allowing(mockEdge1).compareTo(mockEdge7); will(returnValue(-1));
			allowing(mockEdge1).isRemoved(); will(returnValue(false));

			allowing(mockEdge2).getConnectedNodes(); will(returnValue(mockEdge2Pair));
			allowing(mockEdge2).compareTo(mockEdge1); will(returnValue(1));
			allowing(mockEdge2).compareTo(mockEdge2); will(returnValue(0));
			allowing(mockEdge2).compareTo(mockEdge4); will(returnValue(-1));
			allowing(mockEdge2).compareTo(mockEdge7); will(returnValue(-1));
			allowing(mockEdge2).isRemoved(); will(returnValue(false));

			allowing(mockEdge4).getConnectedNodes(); will(returnValue(mockEdge4Pair));
			allowing(mockEdge4).compareTo(mockEdge1); will(returnValue(1));
			allowing(mockEdge4).compareTo(mockEdge2); will(returnValue(1));
			allowing(mockEdge4).compareTo(mockEdge4); will(returnValue(0));
			allowing(mockEdge4).compareTo(mockEdge7); will(returnValue(-1));
			allowing(mockEdge4).isRemoved(); will(returnValue(false));

			allowing(mockEdge7).getConnectedNodes(); will(returnValue(mockEdge7Pair));
			allowing(mockEdge7).compareTo(mockEdge1); will(returnValue(1));
			allowing(mockEdge7).compareTo(mockEdge2); will(returnValue(1));
			allowing(mockEdge7).compareTo(mockEdge4); will(returnValue(1));
			allowing(mockEdge7).compareTo(mockEdge7); will(returnValue(0));
			allowing(mockEdge7).isRemoved(); will(returnValue(false));
			
			allowing(mockEdge1Pair).hasDirectedEnds(with(same(mockNode1)), with(same(mockNode2))); will(returnValue(true));
			allowing(mockEdge1Pair).hasDirectedEnds(with(any(ArchetypalCompoundNode.class)), with(not(same(mockNode2)))); will(returnValue(false));
			allowing(mockEdge1Pair).hasDirectedEnds(with(not(same(mockNode1))), with(any(ArchetypalCompoundNode.class))); will(returnValue(true));
			
			allowing(mockEdge2Pair).hasDirectedEnds(with(same(mockNode2)), with(same(mockNode1))); will(returnValue(true));
			allowing(mockEdge2Pair).hasDirectedEnds(with(any(ArchetypalCompoundNode.class)), with(not(same(mockNode1)))); will(returnValue(false));
			allowing(mockEdge2Pair).hasDirectedEnds(with(not(same(mockNode2))), with(any(ArchetypalCompoundNode.class))); will(returnValue(true));
			
			allowing(mockEdge4Pair).hasDirectedEnds(with(same(mockNode2)), with(same(mockNode4))); will(returnValue(true));
			allowing(mockEdge4Pair).hasDirectedEnds(with(not(same(mockNode2))), with(not(same(mockNode4)))); will(returnValue(false));
			
			allowing(mockEdge7Pair).hasDirectedEnds(with(same(mockNode7)), with(same(mockNode3))); will(returnValue(true));
			allowing(mockEdge7Pair).hasDirectedEnds(with(not(same(mockNode7))), with(not(same(mockNode3)))); will(returnValue(false));
		}});
		this.testInstance = new ChildCompoundGraph(mockRootNode);
//		this.testInstance.addNewNode(mockNode1);
//		this.testInstance.addNewNode(mockNode2);
//		this.testInstance.addNewEdge(mockEdge1);
//		this.testInstance.addNewEdge(mockEdge2);
//		this.testInstance.addNewEdge(mockEdge4);
//		this.testInstance.addNewEdge(mockEdge7);
		assertTrue("has edge", this.testInstance.containsConnection(mockNode7, mockNode3));
		assertTrue("has edge", this.testInstance.containsConnection(mockNode2, mockNode4));
		assertTrue("has edge", this.testInstance.containsConnection(mockNode3, mockNode7));
		assertFalse("has no edge", this.testInstance.containsConnection(mockNode3, null));
		assertFalse("has no edge", this.testInstance.containsConnection(null, mockNode7));
		assertFalse("has no edge", this.testInstance.containsConnection(null, null));
		this.mockery.assertIsSatisfied();
	}

	@SuppressWarnings("unchecked")
	@Ignore @Test
	public final void testContainsEdgeIEdge() {
		final CompoundNode mockRootNode = this.mockery.mock(CompoundNode.class, "mockRootNode");
		final ArchetypalCompoundNode mockNode1 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode1");
		final ArchetypalCompoundNode mockNode2 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode2");
		final ArchetypalCompoundNode mockNode3 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode3");
		final ArchetypalCompoundNode mockNode4 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode4");
		final ArchetypalCompoundNode mockNode7 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode7");
		final ArchetypalCompoundEdge mockEdge1 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge1");
		final ArchetypalCompoundEdge mockEdge2 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge2");
		final ArchetypalCompoundEdge mockEdge4 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge4");
		final ArchetypalCompoundEdge mockEdge7 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge7");
		final ArchetypalCompoundEdge mockEdge8 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge8");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge1Pair = this.mockery.mock(IDirectedPair.class, "mockEdge1Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge2Pair = this.mockery.mock(IDirectedPair.class, "mockEdge2Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge4Pair = this.mockery.mock(IDirectedPair.class, "mockEdge4Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge7Pair = this.mockery.mock(IDirectedPair.class, "mockEdge7Pair");
//		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge8Pair = this.mockery.mock(IDirectedPair.class, "mockEdge8Pair");
		final ArchetypalCompoundGraph mockCigraph = this.mockery.mock(ArchetypalCompoundGraph.class, "mockCigraph");
		this.mockery.checking(new Expectations(){{
			allowing(mockRootNode).getGraph(); will(returnValue(mockCigraph));
			allowing(mockRootNode).getRoot(); will(returnValue(mockRootNode));
			
			allowing(mockNode1).compareTo(mockNode1); will(returnValue(0));
			allowing(mockNode1).compareTo(mockNode2); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode3); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode2).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode2).compareTo(mockNode2); will(returnValue(0));
			allowing(mockNode2).compareTo(mockNode3); will(returnValue(-1));
			allowing(mockNode2).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode2).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode3).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode3).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode3).compareTo(mockNode3); will(returnValue(0));
			allowing(mockNode3).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode3).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode4).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode3); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode4); will(returnValue(0));
			allowing(mockNode4).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode7).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode3); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode4); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode7); will(returnValue(0));
			
			allowing(mockEdge1).getConnectedNodes(); will(returnValue(mockEdge1Pair));
			allowing(mockEdge1).getIndex(); will(returnValue(EXPECTED_EDGE1_IDX));
			allowing(mockEdge1).compareTo(mockEdge1); will(returnValue(0));
			allowing(mockEdge1).compareTo(mockEdge2); will(returnValue(-1));
			allowing(mockEdge1).compareTo(mockEdge4); will(returnValue(-1));
			allowing(mockEdge1).compareTo(mockEdge7); will(returnValue(-1));
			allowing(mockEdge1).isRemoved(); will(returnValue(false));

			allowing(mockEdge2).getConnectedNodes(); will(returnValue(mockEdge2Pair));
			allowing(mockEdge2).getIndex(); will(returnValue(EXPECTED_EDGE2_IDX));
			allowing(mockEdge2).compareTo(mockEdge1); will(returnValue(1));
			allowing(mockEdge2).compareTo(mockEdge2); will(returnValue(0));
			allowing(mockEdge2).compareTo(mockEdge4); will(returnValue(-1));
			allowing(mockEdge2).compareTo(mockEdge7); will(returnValue(-1));
			allowing(mockEdge2).isRemoved(); will(returnValue(false));

			allowing(mockEdge4).getConnectedNodes(); will(returnValue(mockEdge4Pair));
			allowing(mockEdge4).getIndex(); will(returnValue(EXPECTED_EDGE4_IDX));
			allowing(mockEdge4).compareTo(mockEdge1); will(returnValue(1));
			allowing(mockEdge4).compareTo(mockEdge2); will(returnValue(1));
			allowing(mockEdge4).compareTo(mockEdge4); will(returnValue(0));
			allowing(mockEdge4).compareTo(mockEdge7); will(returnValue(-1));
			allowing(mockEdge4).isRemoved(); will(returnValue(false));

			allowing(mockEdge7).getConnectedNodes(); will(returnValue(mockEdge7Pair));
			allowing(mockEdge7).getIndex(); will(returnValue(EXPECTED_EDGE7_IDX));
			allowing(mockEdge7).compareTo(mockEdge2); will(returnValue(1));
			allowing(mockEdge7).compareTo(mockEdge4); will(returnValue(1));
			allowing(mockEdge7).compareTo(mockEdge7); will(returnValue(0));
			allowing(mockEdge7).isRemoved(); will(returnValue(false));
			
			allowing(mockEdge8).getIndex(); will(returnValue(EXPECTED_EDGE8_IDX));

			allowing(mockEdge1Pair).hasDirectedEnds(with(same(mockNode1)), with(same(mockNode2))); will(returnValue(true));
			allowing(mockEdge1Pair).hasDirectedEnds(with(any(ArchetypalCompoundNode.class)), with(not(same(mockNode2)))); will(returnValue(false));
			allowing(mockEdge1Pair).hasDirectedEnds(with(not(same(mockNode1))), with(any(ArchetypalCompoundNode.class))); will(returnValue(true));
			
			allowing(mockEdge2Pair).hasDirectedEnds(with(same(mockNode2)), with(same(mockNode1))); will(returnValue(true));
			allowing(mockEdge2Pair).hasDirectedEnds(with(any(ArchetypalCompoundNode.class)), with(not(same(mockNode1)))); will(returnValue(false));
			allowing(mockEdge2Pair).hasDirectedEnds(with(not(same(mockNode2))), with(any(ArchetypalCompoundNode.class))); will(returnValue(true));
			
			allowing(mockEdge4Pair).hasDirectedEnds(with(same(mockNode2)), with(same(mockNode4))); will(returnValue(true));
			allowing(mockEdge4Pair).hasDirectedEnds(with(not(same(mockNode2))), with(not(same(mockNode4)))); will(returnValue(false));
			
			allowing(mockEdge7Pair).hasDirectedEnds(with(same(mockNode7)), with(same(mockNode3))); will(returnValue(true));
			allowing(mockEdge7Pair).hasDirectedEnds(with(not(same(mockNode7))), with(not(same(mockNode3)))); will(returnValue(false));
		}});
		this.testInstance = new ChildCompoundGraph(mockRootNode);
//		this.testInstance.addNewNode(mockNode1);
//		this.testInstance.addNewNode(mockNode2);
//		this.testInstance.addNewEdge(mockEdge1);
//		this.testInstance.addNewEdge(mockEdge2);
//		this.testInstance.addNewEdge(mockEdge4);
//		this.testInstance.addNewEdge(mockEdge7);
		assertTrue("has edge", this.testInstance.containsEdge(mockEdge1));
		assertTrue("has edge", this.testInstance.containsEdge(mockEdge2));
		assertFalse("has no edge", this.testInstance.containsEdge(mockEdge8));
		assertFalse("has no edge", this.testInstance.containsConnection(null));
		this.mockery.assertIsSatisfied();
	}

	@SuppressWarnings("unchecked")
	@Ignore @Test
	public final void testContainsEdgeInt() {
		final CompoundNode mockRootNode = this.mockery.mock(CompoundNode.class, "mockRootNode");
		final ArchetypalCompoundNode mockNode1 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode1");
		final ArchetypalCompoundNode mockNode2 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode2");
		final ArchetypalCompoundNode mockNode3 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode3");
		final ArchetypalCompoundNode mockNode4 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode4");
		final ArchetypalCompoundNode mockNode7 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode7");
		final ArchetypalCompoundEdge mockEdge1 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge1");
		final ArchetypalCompoundEdge mockEdge2 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge2");
		final ArchetypalCompoundEdge mockEdge4 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge4");
		final ArchetypalCompoundEdge mockEdge7 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge7");
		final ArchetypalCompoundEdge mockEdge8 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge8");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge1Pair = this.mockery.mock(IDirectedPair.class, "mockEdge1Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge2Pair = this.mockery.mock(IDirectedPair.class, "mockEdge2Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge4Pair = this.mockery.mock(IDirectedPair.class, "mockEdge4Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge7Pair = this.mockery.mock(IDirectedPair.class, "mockEdge7Pair");
//		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge8Pair = this.mockery.mock(IDirectedPair.class, "mockEdge8Pair");
		final ArchetypalCompoundGraph mockCigraph = this.mockery.mock(ArchetypalCompoundGraph.class, "mockCigraph");
		this.mockery.checking(new Expectations(){{
			allowing(mockRootNode).getGraph(); will(returnValue(mockCigraph));
			allowing(mockRootNode).getRoot(); will(returnValue(mockRootNode));
			
			allowing(mockNode1).compareTo(mockNode1); will(returnValue(0));
			allowing(mockNode1).compareTo(mockNode2); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode3); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode2).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode2).compareTo(mockNode2); will(returnValue(0));
			allowing(mockNode2).compareTo(mockNode3); will(returnValue(-1));
			allowing(mockNode2).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode2).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode3).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode3).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode3).compareTo(mockNode3); will(returnValue(0));
			allowing(mockNode3).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode3).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode4).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode3); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode4); will(returnValue(0));
			allowing(mockNode4).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode7).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode3); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode4); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode7); will(returnValue(0));
			
			allowing(mockEdge1).getConnectedNodes(); will(returnValue(mockEdge1Pair));
			allowing(mockEdge1).compareTo(mockEdge1); will(returnValue(0));
			allowing(mockEdge1).compareTo(mockEdge2); will(returnValue(-1));
			allowing(mockEdge1).compareTo(mockEdge4); will(returnValue(-1));
			allowing(mockEdge1).compareTo(mockEdge7); will(returnValue(-1));
			allowing(mockEdge1).isRemoved(); will(returnValue(false));
			allowing(mockEdge1).getIndex(); will(returnValue(EXPECTED_EDGE1_IDX));
			
			allowing(mockEdge2).getConnectedNodes(); will(returnValue(mockEdge2Pair));
			allowing(mockEdge2).compareTo(mockEdge1); will(returnValue(1));
			allowing(mockEdge2).compareTo(mockEdge2); will(returnValue(0));
			allowing(mockEdge2).compareTo(mockEdge4); will(returnValue(-1));
			allowing(mockEdge2).compareTo(mockEdge7); will(returnValue(-1));
			allowing(mockEdge2).isRemoved(); will(returnValue(false));
			allowing(mockEdge2).getIndex(); will(returnValue(EXPECTED_EDGE2_IDX));

			allowing(mockEdge4).getConnectedNodes(); will(returnValue(mockEdge4Pair));
			allowing(mockEdge4).compareTo(mockEdge1); will(returnValue(1));
			allowing(mockEdge4).compareTo(mockEdge2); will(returnValue(1));
			allowing(mockEdge4).compareTo(mockEdge4); will(returnValue(0));
			allowing(mockEdge4).compareTo(mockEdge7); will(returnValue(-1));
			allowing(mockEdge4).isRemoved(); will(returnValue(false));
			allowing(mockEdge4).getIndex(); will(returnValue(EXPECTED_EDGE4_IDX));

			allowing(mockEdge7).getConnectedNodes(); will(returnValue(mockEdge7Pair));
			allowing(mockEdge7).compareTo(mockEdge1); will(returnValue(1));
			allowing(mockEdge7).compareTo(mockEdge2); will(returnValue(1));
			allowing(mockEdge7).compareTo(mockEdge4); will(returnValue(1));
			allowing(mockEdge7).compareTo(mockEdge7); will(returnValue(0));
			allowing(mockEdge7).isRemoved(); will(returnValue(false));
			allowing(mockEdge7).getIndex(); will(returnValue(EXPECTED_EDGE7_IDX));
			
			allowing(mockEdge8).getIndex(); will(returnValue(EXPECTED_EDGE8_IDX));

			allowing(mockEdge1Pair).hasDirectedEnds(with(same(mockNode1)), with(same(mockNode2))); will(returnValue(true));
			allowing(mockEdge1Pair).hasDirectedEnds(with(any(ArchetypalCompoundNode.class)), with(not(same(mockNode2)))); will(returnValue(false));
			allowing(mockEdge1Pair).hasDirectedEnds(with(not(same(mockNode1))), with(any(ArchetypalCompoundNode.class))); will(returnValue(true));
			
			allowing(mockEdge2Pair).hasDirectedEnds(with(same(mockNode2)), with(same(mockNode1))); will(returnValue(true));
			allowing(mockEdge2Pair).hasDirectedEnds(with(any(ArchetypalCompoundNode.class)), with(not(same(mockNode1)))); will(returnValue(false));
			allowing(mockEdge2Pair).hasDirectedEnds(with(not(same(mockNode2))), with(any(ArchetypalCompoundNode.class))); will(returnValue(true));
			
			allowing(mockEdge4Pair).hasDirectedEnds(with(same(mockNode2)), with(same(mockNode4))); will(returnValue(true));
			allowing(mockEdge4Pair).hasDirectedEnds(with(not(same(mockNode2))), with(not(same(mockNode4)))); will(returnValue(false));
			
			allowing(mockEdge7Pair).hasDirectedEnds(with(same(mockNode7)), with(same(mockNode3))); will(returnValue(true));
			allowing(mockEdge7Pair).hasDirectedEnds(with(not(same(mockNode7))), with(not(same(mockNode3)))); will(returnValue(false));
		}});
		this.testInstance = new ChildCompoundGraph(mockRootNode);
//		this.testInstance.addNewNode(mockNode1);
//		this.testInstance.addNewNode(mockNode2);
//		this.testInstance.addNewEdge(mockEdge1);
//		this.testInstance.addNewEdge(mockEdge2);
//		this.testInstance.addNewEdge(mockEdge4);
//		this.testInstance.addNewEdge(mockEdge7);
		assertTrue("has edge", this.testInstance.containsEdge(mockEdge1.getIndex()));
		assertTrue("has edge", this.testInstance.containsEdge(mockEdge2.getIndex()));
		assertFalse("has edge", this.testInstance.containsEdge(mockEdge8.getIndex()));
		this.mockery.assertIsSatisfied();
	}

	@Ignore @Test
	public final void testContainsNodeInt() {
		final CompoundNode mockRootNode = this.mockery.mock(CompoundNode.class, "mockRootNode");
		final ArchetypalCompoundNode mockNode1 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode1");
		final ArchetypalCompoundNode mockNode2 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode2");
		final ArchetypalCompoundNode mockNode3 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode3");
		final ArchetypalCompoundNode mockNode4 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode4");
		final ArchetypalCompoundNode mockNode7 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode7");
		final ArchetypalCompoundGraph mockCigraph = this.mockery.mock(ArchetypalCompoundGraph.class, "mockCigraph");
		this.mockery.checking(new Expectations(){{
			allowing(mockRootNode).getGraph(); will(returnValue(mockCigraph));
			allowing(mockRootNode).getRoot(); will(returnValue(mockRootNode));
			
			allowing(mockNode1).compareTo(mockNode1); will(returnValue(0));
			allowing(mockNode1).compareTo(mockNode2); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode3); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode7); will(returnValue(-1));
			allowing(mockNode1).getIndex(); will(returnValue(EXPECTED_NODE1_IDX));
			allowing(mockNode1).isRemoved(); will(returnValue(false));
			
			allowing(mockNode2).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode2).compareTo(mockNode2); will(returnValue(0));
			allowing(mockNode2).compareTo(mockNode3); will(returnValue(-1));
			allowing(mockNode2).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode2).compareTo(mockNode7); will(returnValue(-1));
			allowing(mockNode2).getIndex(); will(returnValue(EXPECTED_NODE2_IDX));
			allowing(mockNode2).isRemoved(); will(returnValue(false));
			
			allowing(mockNode3).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode3).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode3).compareTo(mockNode3); will(returnValue(0));
			allowing(mockNode3).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode3).compareTo(mockNode7); will(returnValue(-1));
			allowing(mockNode3).getIndex(); will(returnValue(EXPECTED_NODE3_IDX));
			
			allowing(mockNode4).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode3); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode4); will(returnValue(0));
			allowing(mockNode4).compareTo(mockNode7); will(returnValue(-1));
			allowing(mockNode4).getIndex(); will(returnValue(EXPECTED_NODE4_IDX));
			
			allowing(mockNode7).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode3); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode4); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode7); will(returnValue(0));
			allowing(mockNode7).getIndex(); will(returnValue(EXPECTED_NODE7_IDX));
			
		}});
		this.testInstance = new ChildCompoundGraph(mockRootNode);
//		this.testInstance.addNewNode(mockNode1);
//		this.testInstance.addNewNode(mockNode2);
		assertTrue("has node", this.testInstance.containsNode(mockNode1.getIndex()));
		assertTrue("has node", this.testInstance.containsNode(mockNode2.getIndex()));
		assertFalse("no node", this.testInstance.containsNode(EXPECTED_MISSING_IDX));
		this.mockery.assertIsSatisfied();
	}

	@Ignore @Test
	public final void testContainsNodeINode() {
		final CompoundNode mockRootNode = this.mockery.mock(CompoundNode.class, "mockRootNode");
		final ArchetypalCompoundNode mockNode1 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode1");
		final ArchetypalCompoundNode mockNode2 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode2");
		final ArchetypalCompoundNode mockNode3 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode3");
		final ArchetypalCompoundNode mockNode4 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode4");
		final ArchetypalCompoundNode mockNode7 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode7");
		final ArchetypalCompoundGraph mockCigraph = this.mockery.mock(ArchetypalCompoundGraph.class, "mockCigraph");
		this.mockery.checking(new Expectations(){{
			allowing(mockRootNode).getGraph(); will(returnValue(mockCigraph));
			allowing(mockRootNode).getRoot(); will(returnValue(mockRootNode));
			
			allowing(mockNode1).compareTo(mockNode1); will(returnValue(0));
			allowing(mockNode1).compareTo(mockNode2); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode3); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode7); will(returnValue(-1));
			allowing(mockNode1).getIndex(); will(returnValue(EXPECTED_NODE1_IDX));
			allowing(mockNode1).isRemoved(); will(returnValue(false));
			
			allowing(mockNode2).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode2).compareTo(mockNode2); will(returnValue(0));
			allowing(mockNode2).compareTo(mockNode3); will(returnValue(-1));
			allowing(mockNode2).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode2).compareTo(mockNode7); will(returnValue(-1));
			allowing(mockNode2).getIndex(); will(returnValue(EXPECTED_NODE2_IDX));
			allowing(mockNode2).isRemoved(); will(returnValue(false));
			
			allowing(mockNode3).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode3).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode3).compareTo(mockNode3); will(returnValue(0));
			allowing(mockNode3).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode3).compareTo(mockNode7); will(returnValue(-1));
			allowing(mockNode3).getIndex(); will(returnValue(EXPECTED_NODE3_IDX));
			
			allowing(mockNode4).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode3); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode4); will(returnValue(0));
			allowing(mockNode4).compareTo(mockNode7); will(returnValue(-1));
			allowing(mockNode4).getIndex(); will(returnValue(EXPECTED_NODE4_IDX));
			
			allowing(mockNode7).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode3); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode4); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode7); will(returnValue(0));
			allowing(mockNode7).getIndex(); will(returnValue(EXPECTED_NODE7_IDX));
			
		}});
		this.testInstance = new ChildCompoundGraph(mockRootNode);
//		this.testInstance.addNewNode(mockNode1);
//		this.testInstance.addNewNode(mockNode2);
		assertTrue("has node", this.testInstance.containsNode(mockNode1));
		assertTrue("has node", this.testInstance.containsNode(mockNode2));
		assertFalse("no node", this.testInstance.containsNode(null));
		this.mockery.assertIsSatisfied();
	}

	@SuppressWarnings("unchecked")
	@Ignore @Test
	public final void testGetEdge() {
		final CompoundNode mockRootNode = this.mockery.mock(CompoundNode.class, "mockRootNode");
		final ArchetypalCompoundNode mockNode1 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode1");
		final ArchetypalCompoundNode mockNode2 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode2");
		final ArchetypalCompoundNode mockNode3 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode3");
		final ArchetypalCompoundNode mockNode4 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode4");
		final ArchetypalCompoundNode mockNode7 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode7");
		final ArchetypalCompoundEdge mockEdge1 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge1");
		final ArchetypalCompoundEdge mockEdge2 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge2");
		final ArchetypalCompoundEdge mockEdge4 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge4");
		final ArchetypalCompoundEdge mockEdge7 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge7");
		final ArchetypalCompoundEdge mockEdge8 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge8");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge1Pair = this.mockery.mock(IDirectedPair.class, "mockEdge1Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge2Pair = this.mockery.mock(IDirectedPair.class, "mockEdge2Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge4Pair = this.mockery.mock(IDirectedPair.class, "mockEdge4Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge7Pair = this.mockery.mock(IDirectedPair.class, "mockEdge7Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge8Pair = this.mockery.mock(IDirectedPair.class, "mockEdge8Pair");
		final ArchetypalCompoundGraph mockCigraph = this.mockery.mock(ArchetypalCompoundGraph.class, "mockCigraph");
		this.mockery.checking(new Expectations(){{
			allowing(mockRootNode).getGraph(); will(returnValue(mockCigraph));
			allowing(mockRootNode).getRoot(); will(returnValue(mockRootNode));
			
			allowing(mockNode1).compareTo(mockNode1); will(returnValue(0));
			allowing(mockNode1).compareTo(mockNode2); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode3); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode2).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode2).compareTo(mockNode2); will(returnValue(0));
			allowing(mockNode2).compareTo(mockNode3); will(returnValue(-1));
			allowing(mockNode2).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode2).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode3).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode3).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode3).compareTo(mockNode3); will(returnValue(0));
			allowing(mockNode3).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode3).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode4).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode3); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode4); will(returnValue(0));
			allowing(mockNode4).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode7).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode3); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode4); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode7); will(returnValue(0));
			
			allowing(mockEdge1).getConnectedNodes(); will(returnValue(mockEdge1Pair));
			allowing(mockEdge1).getIndex(); will(returnValue(EXPECTED_EDGE1_IDX));
			allowing(mockEdge1).isRemoved(); will(returnValue(false));
			
			allowing(mockEdge2).getConnectedNodes(); will(returnValue(mockEdge2Pair));
			allowing(mockEdge2).getIndex(); will(returnValue(EXPECTED_EDGE2_IDX));
			allowing(mockEdge2).compareTo(mockEdge1); will(returnValue(1));
			allowing(mockEdge2).isRemoved(); will(returnValue(false));

			allowing(mockEdge4).getConnectedNodes(); will(returnValue(mockEdge4Pair));
			allowing(mockEdge4).getIndex(); will(returnValue(EXPECTED_EDGE4_IDX));
			allowing(mockEdge4).compareTo(mockEdge1); will(returnValue(1));
			allowing(mockEdge4).compareTo(mockEdge2); will(returnValue(1));

			allowing(mockEdge7).getConnectedNodes(); will(returnValue(mockEdge7Pair));
			allowing(mockEdge7).getIndex(); will(returnValue(EXPECTED_EDGE7_IDX));
			allowing(mockEdge7).compareTo(mockEdge2); will(returnValue(1));
			allowing(mockEdge7).compareTo(mockEdge4); will(returnValue(1));
			
			allowing(mockEdge8).getConnectedNodes(); will(returnValue(mockEdge8Pair));
			allowing(mockEdge8).getIndex(); will(returnValue(EXPECTED_EDGE8_IDX));

//			allowing(mockEdge1Pair).getOneNode(); will(returnValue(mockNode1));
//			allowing(mockEdge1Pair).getTwoNode(); will(returnValue(mockNode2));
//
//			allowing(mockEdge2Pair).getOneNode(); will(returnValue(mockNode2));
//			allowing(mockEdge2Pair).getTwoNode(); will(returnValue(mockNode1));
//
//			allowing(mockEdge4Pair).getOneNode(); will(returnValue(mockNode2));
//			allowing(mockEdge4Pair).getTwoNode(); will(returnValue(mockNode4));
//
//			allowing(mockEdge7Pair).getOneNode(); will(returnValue(mockNode7));
//			allowing(mockEdge7Pair).getTwoNode(); will(returnValue(mockNode3));
//
//			allowing(mockEdge8Pair).getOneNode(); will(returnValue(mockNode1));
//			allowing(mockEdge8Pair).getTwoNode(); will(returnValue(mockNode1));

		}});
		this.testInstance = new ChildCompoundGraph(mockRootNode);
//		this.testInstance.addNewNode(mockNode1);
//		this.testInstance.addNewNode(mockNode2);
//		this.testInstance.addNewEdge(mockEdge1);
//		this.testInstance.addNewEdge(mockEdge2);
//		this.testInstance.addNewEdge(mockEdge4);
//		this.testInstance.addNewEdge(mockEdge7);
		assertEquals("has edge", mockEdge1, this.testInstance.getEdge(mockEdge1.getIndex()));
		assertEquals("has edge", mockEdge2, this.testInstance.getEdge(mockEdge2.getIndex()));
		assertTrue("has no edge", this.testInstance.getEdge(mockEdge8.getIndex()) == null);
		this.mockery.assertIsSatisfied();
	}

	@SuppressWarnings("unchecked")
	@Ignore @Test
	public final void testGetEdgeIterator() {
		final CompoundNode mockRootNode = this.mockery.mock(CompoundNode.class, "mockRootNode");
		final ArchetypalCompoundNode mockNode1 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode1");
		final ArchetypalCompoundNode mockNode2 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode2");
		final ArchetypalCompoundNode mockNode3 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode3");
		final ArchetypalCompoundNode mockNode4 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode4");
		final ArchetypalCompoundNode mockNode7 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode7");
		final ArchetypalCompoundEdge mockEdge1 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge1");
		final ArchetypalCompoundEdge mockEdge2 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge2");
		final ArchetypalCompoundEdge mockEdge4 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge4");
		final ArchetypalCompoundEdge mockEdge7 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge7");
//		final ArchetypalCompoundEdge mockEdge8 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge8");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge1Pair = this.mockery.mock(IDirectedPair.class, "mockEdge1Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge2Pair = this.mockery.mock(IDirectedPair.class, "mockEdge2Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge4Pair = this.mockery.mock(IDirectedPair.class, "mockEdge4Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge7Pair = this.mockery.mock(IDirectedPair.class, "mockEdge7Pair");
//		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge8Pair = this.mockery.mock(IDirectedPair.class, "mockEdge8Pair");
		final ArchetypalCompoundGraph mockCigraph = this.mockery.mock(ArchetypalCompoundGraph.class, "mockCigraph");
		this.mockery.checking(new Expectations(){{
			allowing(mockRootNode).getGraph(); will(returnValue(mockCigraph));
			allowing(mockRootNode).getRoot(); will(returnValue(mockRootNode));
			
			allowing(mockNode1).compareTo(mockNode1); will(returnValue(0));
			allowing(mockNode1).compareTo(mockNode2); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode3); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode2).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode2).compareTo(mockNode2); will(returnValue(0));
			allowing(mockNode2).compareTo(mockNode3); will(returnValue(-1));
			allowing(mockNode2).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode2).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode3).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode3).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode3).compareTo(mockNode3); will(returnValue(0));
			allowing(mockNode3).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode3).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode4).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode3); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode4); will(returnValue(0));
			allowing(mockNode4).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode7).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode3); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode4); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode7); will(returnValue(0));
			
			allowing(mockEdge1).getConnectedNodes(); will(returnValue(mockEdge1Pair));
			allowing(mockEdge1).compareTo(mockEdge1); will(returnValue(0));
			allowing(mockEdge1).compareTo(mockEdge2); will(returnValue(-1));
			allowing(mockEdge1).compareTo(mockEdge4); will(returnValue(-1));
			allowing(mockEdge1).compareTo(mockEdge7); will(returnValue(-1));
			allowing(mockEdge1).isRemoved(); will(returnValue(false));

			allowing(mockEdge2).getConnectedNodes(); will(returnValue(mockEdge2Pair));
			allowing(mockEdge2).compareTo(mockEdge1); will(returnValue(1));
			allowing(mockEdge2).compareTo(mockEdge2); will(returnValue(0));
			allowing(mockEdge2).compareTo(mockEdge4); will(returnValue(-1));
			allowing(mockEdge2).compareTo(mockEdge7); will(returnValue(-1));
			allowing(mockEdge2).isRemoved(); will(returnValue(false));

			allowing(mockEdge4).getConnectedNodes(); will(returnValue(mockEdge4Pair));
			allowing(mockEdge4).compareTo(mockEdge1); will(returnValue(1));
			allowing(mockEdge4).compareTo(mockEdge2); will(returnValue(1));
			allowing(mockEdge4).compareTo(mockEdge4); will(returnValue(0));
			allowing(mockEdge4).compareTo(mockEdge7); will(returnValue(-1));
			allowing(mockEdge4).isRemoved(); will(returnValue(false));

			allowing(mockEdge7).getConnectedNodes(); will(returnValue(mockEdge7Pair));
			allowing(mockEdge7).compareTo(mockEdge1); will(returnValue(1));
			allowing(mockEdge7).compareTo(mockEdge2); will(returnValue(1));
			allowing(mockEdge7).compareTo(mockEdge4); will(returnValue(1));
			allowing(mockEdge7).compareTo(mockEdge7); will(returnValue(0));
			allowing(mockEdge7).isRemoved(); will(returnValue(false));
			
			allowing(mockEdge1Pair).hasDirectedEnds(with(same(mockNode1)), with(same(mockNode2))); will(returnValue(true));
			allowing(mockEdge1Pair).hasDirectedEnds(with(any(ArchetypalCompoundNode.class)), with(not(same(mockNode2)))); will(returnValue(false));
			allowing(mockEdge1Pair).hasDirectedEnds(with(not(same(mockNode1))), with(any(ArchetypalCompoundNode.class))); will(returnValue(true));
			
			allowing(mockEdge2Pair).hasDirectedEnds(with(same(mockNode2)), with(same(mockNode1))); will(returnValue(true));
			allowing(mockEdge2Pair).hasDirectedEnds(with(any(ArchetypalCompoundNode.class)), with(not(same(mockNode1)))); will(returnValue(false));
			allowing(mockEdge2Pair).hasDirectedEnds(with(not(same(mockNode2))), with(any(ArchetypalCompoundNode.class))); will(returnValue(true));
			
			allowing(mockEdge4Pair).hasDirectedEnds(with(same(mockNode2)), with(same(mockNode4))); will(returnValue(true));
			allowing(mockEdge4Pair).hasDirectedEnds(with(not(same(mockNode2))), with(not(same(mockNode4)))); will(returnValue(false));
			
			allowing(mockEdge7Pair).hasDirectedEnds(with(same(mockNode7)), with(same(mockNode3))); will(returnValue(true));
			allowing(mockEdge7Pair).hasDirectedEnds(with(not(same(mockNode7))), with(not(same(mockNode3)))); will(returnValue(false));
		}});
		this.testInstance = new ChildCompoundGraph(mockRootNode);
//		this.testInstance.addNewNode(mockNode1);
//		this.testInstance.addNewNode(mockNode2);
//		this.testInstance.addNewEdge(mockEdge1);
//		this.testInstance.addNewEdge(mockEdge2);
//		this.testInstance.addNewEdge(mockEdge4);
//		this.testInstance.addNewEdge(mockEdge7);
		Iterator<BaseCompoundEdge> iter = this.testInstance.edgeIterator();
		IBasicEdge expectedIterationOrder[] = { mockEdge1, mockEdge2, mockEdge4, mockEdge7 };
		List<IBasicEdge> expectedEdgeList = Arrays.asList(expectedIterationOrder);
		for(IBasicEdge expectedEdge : expectedEdgeList){
			assertTrue("edge available", iter.hasNext());
			assertEquals("expected next edge", expectedEdge, iter.next());
		}
		assertFalse("iterator is exhausted as expected", iter.hasNext());
		try{
			iter.next();
			fail("Expected a NoSuchElementException");
		}
		catch(NoSuchElementException e){
			// exception is expected here
		}
		this.mockery.assertIsSatisfied();
	}

	@Ignore @Test
	public final void testGetNode() {
		final CompoundNode mockRootNode = this.mockery.mock(CompoundNode.class, "mockRootNode");
		final ArchetypalCompoundNode mockNode1 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode1");
		final ArchetypalCompoundNode mockNode2 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode2");
		final ArchetypalCompoundNode mockNode3 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode3");
		final ArchetypalCompoundNode mockNode4 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode4");
		final ArchetypalCompoundNode mockNode7 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode7");
		final ArchetypalCompoundGraph mockCigraph = this.mockery.mock(ArchetypalCompoundGraph.class, "mockCigraph");
		this.mockery.checking(new Expectations(){{
			allowing(mockRootNode).getGraph(); will(returnValue(mockCigraph));
			allowing(mockRootNode).getRoot(); will(returnValue(mockRootNode));
			
			allowing(mockNode1).compareTo(mockNode1); will(returnValue(0));
			allowing(mockNode1).compareTo(mockNode2); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode3); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode7); will(returnValue(-1));
			allowing(mockNode1).getIndex(); will(returnValue(EXPECTED_NODE1_IDX));
			
			allowing(mockNode2).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode2).compareTo(mockNode2); will(returnValue(0));
			allowing(mockNode2).compareTo(mockNode3); will(returnValue(-1));
			allowing(mockNode2).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode2).compareTo(mockNode7); will(returnValue(-1));
			allowing(mockNode2).getIndex(); will(returnValue(EXPECTED_NODE2_IDX));
			
			allowing(mockNode3).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode3).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode3).compareTo(mockNode3); will(returnValue(0));
			allowing(mockNode3).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode3).compareTo(mockNode7); will(returnValue(-1));
			allowing(mockNode3).getIndex(); will(returnValue(EXPECTED_NODE3_IDX));
			
			allowing(mockNode4).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode3); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode4); will(returnValue(0));
			allowing(mockNode4).compareTo(mockNode7); will(returnValue(-1));
			allowing(mockNode4).getIndex(); will(returnValue(EXPECTED_NODE4_IDX));
			
			allowing(mockNode7).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode3); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode4); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode7); will(returnValue(0));
			allowing(mockNode7).getIndex(); will(returnValue(EXPECTED_NODE7_IDX));
			
		}});
		this.testInstance = new ChildCompoundGraph(mockRootNode);
//		this.testInstance.addNewNode(mockNode1);
//		this.testInstance.addNewNode(mockNode2);
		assertEquals("has node", mockNode1, this.testInstance.getNode(mockNode1.getIndex()));
		assertEquals("has node", mockNode2, this.testInstance.getNode(mockNode2.getIndex()));
		try{
			this.testInstance.getNode(EXPECTED_MISSING_IDX);
			fail("IllegalArgumentException should be shown");
		}
		catch(IllegalArgumentException e){
			// expect this to be thrown
		}
		this.mockery.assertIsSatisfied();
	}

	@Ignore @Test
	public final void testGetNodeIterator() {
		final CompoundNode mockRootNode = this.mockery.mock(CompoundNode.class, "mockRootNode");
		final ArchetypalCompoundNode mockNode1 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode1");
		final ArchetypalCompoundNode mockNode2 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode2");
		final ArchetypalCompoundNode mockNode3 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode3");
		final ArchetypalCompoundNode mockNode4 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode4");
		final ArchetypalCompoundNode mockNode7 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode7");
		final ArchetypalCompoundGraph mockCigraph = this.mockery.mock(ArchetypalCompoundGraph.class, "mockCigraph");
		this.mockery.checking(new Expectations(){{
			allowing(mockRootNode).getGraph(); will(returnValue(mockCigraph));
			allowing(mockRootNode).getRoot(); will(returnValue(mockRootNode));
			
			allowing(mockNode1).compareTo(mockNode1); will(returnValue(0));
			allowing(mockNode1).compareTo(mockNode2); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode3); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode7); will(returnValue(-1));
			allowing(mockNode1).getIndex(); will(returnValue(EXPECTED_NODE1_IDX));
			
			allowing(mockNode2).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode2).compareTo(mockNode2); will(returnValue(0));
			allowing(mockNode2).compareTo(mockNode3); will(returnValue(-1));
			allowing(mockNode2).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode2).compareTo(mockNode7); will(returnValue(-1));
			allowing(mockNode2).getIndex(); will(returnValue(EXPECTED_NODE2_IDX));
			
			allowing(mockNode3).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode3).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode3).compareTo(mockNode3); will(returnValue(0));
			allowing(mockNode3).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode3).compareTo(mockNode7); will(returnValue(-1));
			allowing(mockNode3).getIndex(); will(returnValue(EXPECTED_NODE3_IDX));
			
			allowing(mockNode4).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode3); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode4); will(returnValue(0));
			allowing(mockNode4).compareTo(mockNode7); will(returnValue(-1));
			allowing(mockNode4).getIndex(); will(returnValue(EXPECTED_NODE4_IDX));
			
			allowing(mockNode7).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode3); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode4); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode7); will(returnValue(0));
			allowing(mockNode7).getIndex(); will(returnValue(EXPECTED_NODE7_IDX));
			
		}});
		this.testInstance = new ChildCompoundGraph(mockRootNode);
//		this.testInstance.addNewNode(mockNode1);
//		this.testInstance.addNewNode(mockNode2);
		Iterator<BaseCompoundNode> iter = this.testInstance.nodeIterator();
		ArchetypalCompoundNode expectedIterationOrder[] = { mockNode1, mockNode2 };
		List<ArchetypalCompoundNode> expectedNodeList = Arrays.asList(expectedIterationOrder);
		for(ArchetypalCompoundNode expectedNode : expectedNodeList){
			assertTrue("node available", iter.hasNext());
			assertEquals("expected next node", expectedNode, iter.next());
		}
		assertFalse("iterator is exhausted as expected", iter.hasNext());
		try{
			iter.next();
			fail("Expected a NoSuchElementException");
		}
		catch(NoSuchElementException e){
			// exception is expected here
		}
		this.mockery.assertIsSatisfied();
	}

	@SuppressWarnings("unchecked")
	@Ignore @Test
	public final void testGetNumEdges() {
		final CompoundNode mockRootNode = this.mockery.mock(CompoundNode.class, "mockRootNode");
		final ArchetypalCompoundNode mockNode1 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode1");
		final ArchetypalCompoundNode mockNode2 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode2");
		final ArchetypalCompoundNode mockNode3 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode3");
		final ArchetypalCompoundNode mockNode4 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode4");
		final ArchetypalCompoundNode mockNode7 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode7");
		final ArchetypalCompoundEdge mockEdge1 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge1");
		final ArchetypalCompoundEdge mockEdge2 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge2");
		final ArchetypalCompoundEdge mockEdge4 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge4");
		final ArchetypalCompoundEdge mockEdge7 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge7");
//		final ArchetypalCompoundEdge mockEdge8 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge8");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge1Pair = this.mockery.mock(IDirectedPair.class, "mockEdge1Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge2Pair = this.mockery.mock(IDirectedPair.class, "mockEdge2Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge4Pair = this.mockery.mock(IDirectedPair.class, "mockEdge4Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge7Pair = this.mockery.mock(IDirectedPair.class, "mockEdge7Pair");
//		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge8Pair = this.mockery.mock(IDirectedPair.class, "mockEdge8Pair");
		final ArchetypalCompoundGraph mockCigraph = this.mockery.mock(ArchetypalCompoundGraph.class, "mockCigraph");
		this.mockery.checking(new Expectations(){{
			allowing(mockRootNode).getGraph(); will(returnValue(mockCigraph));
			allowing(mockRootNode).getRoot(); will(returnValue(mockRootNode));
			
			allowing(mockNode1).compareTo(mockNode1); will(returnValue(0));
			allowing(mockNode1).compareTo(mockNode2); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode3); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode2).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode2).compareTo(mockNode2); will(returnValue(0));
			allowing(mockNode2).compareTo(mockNode3); will(returnValue(-1));
			allowing(mockNode2).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode2).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode3).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode3).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode3).compareTo(mockNode3); will(returnValue(0));
			allowing(mockNode3).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode3).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode4).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode3); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode4); will(returnValue(0));
			allowing(mockNode4).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode7).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode3); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode4); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode7); will(returnValue(0));
			
			allowing(mockEdge1).getConnectedNodes(); will(returnValue(mockEdge1Pair));
			allowing(mockEdge1).compareTo(mockEdge1); will(returnValue(0));
			allowing(mockEdge1).compareTo(mockEdge2); will(returnValue(-1));
			allowing(mockEdge1).compareTo(mockEdge4); will(returnValue(-1));
			allowing(mockEdge1).compareTo(mockEdge7); will(returnValue(-1));
			allowing(mockEdge1).isRemoved(); will(returnValue(false));

			allowing(mockEdge2).getConnectedNodes(); will(returnValue(mockEdge2Pair));
			allowing(mockEdge2).compareTo(mockEdge1); will(returnValue(1));
			allowing(mockEdge2).compareTo(mockEdge2); will(returnValue(0));
			allowing(mockEdge2).compareTo(mockEdge4); will(returnValue(-1));
			allowing(mockEdge2).compareTo(mockEdge7); will(returnValue(-1));
			allowing(mockEdge2).isRemoved(); will(returnValue(false));

			allowing(mockEdge4).getConnectedNodes(); will(returnValue(mockEdge4Pair));
			allowing(mockEdge4).compareTo(mockEdge1); will(returnValue(1));
			allowing(mockEdge4).compareTo(mockEdge2); will(returnValue(1));
			allowing(mockEdge4).compareTo(mockEdge4); will(returnValue(0));
			allowing(mockEdge4).compareTo(mockEdge7); will(returnValue(-1));
			allowing(mockEdge4).isRemoved(); will(returnValue(false));

			allowing(mockEdge7).getConnectedNodes(); will(returnValue(mockEdge7Pair));
			allowing(mockEdge7).compareTo(mockEdge1); will(returnValue(1));
			allowing(mockEdge7).compareTo(mockEdge2); will(returnValue(1));
			allowing(mockEdge7).compareTo(mockEdge4); will(returnValue(1));
			allowing(mockEdge7).compareTo(mockEdge7); will(returnValue(0));
			allowing(mockEdge7).isRemoved(); will(returnValue(false));
			
			allowing(mockEdge1Pair).hasDirectedEnds(with(same(mockNode1)), with(same(mockNode2))); will(returnValue(true));
			allowing(mockEdge1Pair).hasDirectedEnds(with(any(ArchetypalCompoundNode.class)), with(not(same(mockNode2)))); will(returnValue(false));
			allowing(mockEdge1Pair).hasDirectedEnds(with(not(same(mockNode1))), with(any(ArchetypalCompoundNode.class))); will(returnValue(true));
			
			allowing(mockEdge2Pair).hasDirectedEnds(with(same(mockNode2)), with(same(mockNode1))); will(returnValue(true));
			allowing(mockEdge2Pair).hasDirectedEnds(with(any(ArchetypalCompoundNode.class)), with(not(same(mockNode1)))); will(returnValue(false));
			allowing(mockEdge2Pair).hasDirectedEnds(with(not(same(mockNode2))), with(any(ArchetypalCompoundNode.class))); will(returnValue(true));
			
			allowing(mockEdge4Pair).hasDirectedEnds(with(same(mockNode2)), with(same(mockNode4))); will(returnValue(true));
			allowing(mockEdge4Pair).hasDirectedEnds(with(not(same(mockNode2))), with(not(same(mockNode4)))); will(returnValue(false));
			
			allowing(mockEdge7Pair).hasDirectedEnds(with(same(mockNode7)), with(same(mockNode3))); will(returnValue(true));
			allowing(mockEdge7Pair).hasDirectedEnds(with(not(same(mockNode7))), with(not(same(mockNode3)))); will(returnValue(false));
		}});
		this.testInstance = new ChildCompoundGraph(mockRootNode);
//		this.testInstance.addNewNode(mockNode1);
//		this.testInstance.addNewNode(mockNode2);
//		this.testInstance.addNewEdge(mockEdge1);
//		this.testInstance.addNewEdge(mockEdge2);
//		this.testInstance.addNewEdge(mockEdge4);
//		this.testInstance.addNewEdge(mockEdge7);
		assertEquals("expected num edges", EXPECTED_NUM_EDGES, this.testInstance.getNumEdges());
		this.mockery.assertIsSatisfied();
	}

	@SuppressWarnings("unchecked")
	@Ignore @Test
	public final void testGetNumNodes() {
		final CompoundNode mockRootNode = this.mockery.mock(CompoundNode.class, "mockRootNode");
		final ArchetypalCompoundNode mockNode1 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode1");
		final ArchetypalCompoundNode mockNode2 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode2");
		final ArchetypalCompoundNode mockNode3 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode3");
		final ArchetypalCompoundNode mockNode4 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode4");
		final ArchetypalCompoundNode mockNode7 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode7");
		final ArchetypalCompoundEdge mockEdge1 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge1");
		final ArchetypalCompoundEdge mockEdge2 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge2");
		final ArchetypalCompoundEdge mockEdge4 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge4");
		final ArchetypalCompoundEdge mockEdge7 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge7");
//		final ArchetypalCompoundEdge mockEdge8 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge8");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge1Pair = this.mockery.mock(IDirectedPair.class, "mockEdge1Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge2Pair = this.mockery.mock(IDirectedPair.class, "mockEdge2Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge4Pair = this.mockery.mock(IDirectedPair.class, "mockEdge4Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge7Pair = this.mockery.mock(IDirectedPair.class, "mockEdge7Pair");
//		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge8Pair = this.mockery.mock(IDirectedPair.class, "mockEdge8Pair");
		final ArchetypalCompoundGraph mockCigraph = this.mockery.mock(ArchetypalCompoundGraph.class, "mockCigraph");
		this.mockery.checking(new Expectations(){{
			allowing(mockRootNode).getGraph(); will(returnValue(mockCigraph));
			allowing(mockRootNode).getRoot(); will(returnValue(mockRootNode));
			
			allowing(mockNode1).compareTo(mockNode1); will(returnValue(0));
			allowing(mockNode1).compareTo(mockNode2); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode3); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode2).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode2).compareTo(mockNode2); will(returnValue(0));
			allowing(mockNode2).compareTo(mockNode3); will(returnValue(-1));
			allowing(mockNode2).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode2).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode3).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode3).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode3).compareTo(mockNode3); will(returnValue(0));
			allowing(mockNode3).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode3).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode4).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode3); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode4); will(returnValue(0));
			allowing(mockNode4).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode7).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode3); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode4); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode7); will(returnValue(0));
			
			allowing(mockEdge1).getConnectedNodes(); will(returnValue(mockEdge1Pair));
			allowing(mockEdge1).compareTo(mockEdge1); will(returnValue(0));
			allowing(mockEdge1).compareTo(mockEdge2); will(returnValue(-1));
			allowing(mockEdge1).compareTo(mockEdge4); will(returnValue(-1));
			allowing(mockEdge1).compareTo(mockEdge7); will(returnValue(-1));
			allowing(mockEdge1).isRemoved(); will(returnValue(false));

			allowing(mockEdge2).getConnectedNodes(); will(returnValue(mockEdge2Pair));
			allowing(mockEdge2).compareTo(mockEdge1); will(returnValue(1));
			allowing(mockEdge2).compareTo(mockEdge2); will(returnValue(0));
			allowing(mockEdge2).compareTo(mockEdge4); will(returnValue(-1));
			allowing(mockEdge2).compareTo(mockEdge7); will(returnValue(-1));
			allowing(mockEdge2).isRemoved(); will(returnValue(false));

			allowing(mockEdge4).getConnectedNodes(); will(returnValue(mockEdge4Pair));
			allowing(mockEdge4).compareTo(mockEdge1); will(returnValue(1));
			allowing(mockEdge4).compareTo(mockEdge2); will(returnValue(1));
			allowing(mockEdge4).compareTo(mockEdge4); will(returnValue(0));
			allowing(mockEdge4).compareTo(mockEdge7); will(returnValue(-1));
			allowing(mockEdge4).isRemoved(); will(returnValue(false));

			allowing(mockEdge7).getConnectedNodes(); will(returnValue(mockEdge7Pair));
			allowing(mockEdge7).compareTo(mockEdge1); will(returnValue(1));
			allowing(mockEdge7).compareTo(mockEdge2); will(returnValue(1));
			allowing(mockEdge7).compareTo(mockEdge4); will(returnValue(1));
			allowing(mockEdge7).compareTo(mockEdge7); will(returnValue(0));
			allowing(mockEdge7).isRemoved(); will(returnValue(false));
			
			allowing(mockEdge1Pair).hasDirectedEnds(with(same(mockNode1)), with(same(mockNode2))); will(returnValue(true));
			allowing(mockEdge1Pair).hasDirectedEnds(with(any(ArchetypalCompoundNode.class)), with(not(same(mockNode2)))); will(returnValue(false));
			allowing(mockEdge1Pair).hasDirectedEnds(with(not(same(mockNode1))), with(any(ArchetypalCompoundNode.class))); will(returnValue(true));
			
			allowing(mockEdge2Pair).hasDirectedEnds(with(same(mockNode2)), with(same(mockNode1))); will(returnValue(true));
			allowing(mockEdge2Pair).hasDirectedEnds(with(any(ArchetypalCompoundNode.class)), with(not(same(mockNode1)))); will(returnValue(false));
			allowing(mockEdge2Pair).hasDirectedEnds(with(not(same(mockNode2))), with(any(ArchetypalCompoundNode.class))); will(returnValue(true));
			
			allowing(mockEdge4Pair).hasDirectedEnds(with(same(mockNode2)), with(same(mockNode4))); will(returnValue(true));
			allowing(mockEdge4Pair).hasDirectedEnds(with(not(same(mockNode2))), with(not(same(mockNode4)))); will(returnValue(false));
			
			allowing(mockEdge7Pair).hasDirectedEnds(with(same(mockNode7)), with(same(mockNode3))); will(returnValue(true));
			allowing(mockEdge7Pair).hasDirectedEnds(with(not(same(mockNode7))), with(not(same(mockNode3)))); will(returnValue(false));
		}});
		this.testInstance = new ChildCompoundGraph(mockRootNode);
//		this.testInstance.addNewNode(mockNode1);
//		this.testInstance.addNewNode(mockNode2);
//		this.testInstance.addNewEdge(mockEdge1);
//		this.testInstance.addNewEdge(mockEdge2);
//		this.testInstance.addNewEdge(mockEdge4);
//		this.testInstance.addNewEdge(mockEdge7);
		assertEquals("num nodes", EXPECTED_NUM_NODES, this.testInstance.getNumNodes());
		this.mockery.assertIsSatisfied();
	}

	@SuppressWarnings("unchecked")
	@Ignore @Test
	public final void testCanCopyHere() {
		final CompoundNode mockRootNode = this.mockery.mock(CompoundNode.class, "mockRootNode");
		final ArchetypalCompoundNode mockNode1 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode1");
		final ArchetypalCompoundNode mockNode2 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode2");
		final ArchetypalCompoundNode mockNode3 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode3");
		final ArchetypalCompoundNode mockNode4 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode4");
		final ArchetypalCompoundNode mockNode7 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode7");
		final ArchetypalCompoundEdge mockEdge1 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge1");
		final ArchetypalCompoundEdge mockEdge2 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge2");
		final ArchetypalCompoundEdge mockEdge4 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge4");
		final ArchetypalCompoundEdge mockEdge7 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge7");
		final ArchetypalCompoundEdge mockEdge8 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge8");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge1Pair = this.mockery.mock(IDirectedPair.class, "mockEdge1Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge2Pair = this.mockery.mock(IDirectedPair.class, "mockEdge2Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge4Pair = this.mockery.mock(IDirectedPair.class, "mockEdge4Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge7Pair = this.mockery.mock(IDirectedPair.class, "mockEdge7Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge8Pair = this.mockery.mock(IDirectedPair.class, "mockEdge8Pair");
		final ArchetypalCompoundGraph mockCigraph = this.mockery.mock(ArchetypalCompoundGraph.class, "mockCigraph");
		final ISubCompoundGraph<BaseCompoundNode, BaseCompoundEdge> mockSubgraph = this.mockery.mock(ISubCompoundGraph.class, "mockSubgraph");
		this.mockery.checking(new Expectations(){{
			allowing(mockRootNode).getGraph(); will(returnValue(mockCigraph));
			allowing(mockRootNode).getRoot(); will(returnValue(mockRootNode));
			
			allowing(mockNode1).compareTo(mockNode1); will(returnValue(0));
			allowing(mockNode1).compareTo(mockNode2); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode3); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode2).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode2).compareTo(mockNode2); will(returnValue(0));
			allowing(mockNode2).compareTo(mockNode3); will(returnValue(-1));
			allowing(mockNode2).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode2).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode3).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode3).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode3).compareTo(mockNode3); will(returnValue(0));
			allowing(mockNode3).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode3).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode4).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode3); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode4); will(returnValue(0));
			allowing(mockNode4).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode7).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode3); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode4); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode7); will(returnValue(0));
			
			allowing(mockEdge1).getConnectedNodes(); will(returnValue(mockEdge1Pair));
			allowing(mockEdge1).getIndex(); will(returnValue(EXPECTED_EDGE1_IDX));

			allowing(mockEdge2).getConnectedNodes(); will(returnValue(mockEdge2Pair));
			allowing(mockEdge2).getIndex(); will(returnValue(EXPECTED_EDGE2_IDX));

			allowing(mockEdge4).getConnectedNodes(); will(returnValue(mockEdge4Pair));
			allowing(mockEdge4).getIndex(); will(returnValue(EXPECTED_EDGE4_IDX));

			allowing(mockEdge7).getConnectedNodes(); will(returnValue(mockEdge7Pair));
			allowing(mockEdge7).getIndex(); will(returnValue(EXPECTED_EDGE7_IDX));
			
			allowing(mockEdge8).getConnectedNodes(); will(returnValue(mockEdge8Pair));
			allowing(mockEdge8).getIndex(); will(returnValue(EXPECTED_EDGE8_IDX));

//			allowing(mockEdge1Pair).getOneNode(); will(returnValue(mockNode1));
//			allowing(mockEdge1Pair).getTwoNode(); will(returnValue(mockNode2));
//
//			allowing(mockEdge2Pair).getOneNode(); will(returnValue(mockNode2));
//			allowing(mockEdge2Pair).getTwoNode(); will(returnValue(mockNode1));
//
//			allowing(mockEdge4Pair).getOneNode(); will(returnValue(mockNode2));
//			allowing(mockEdge4Pair).getTwoNode(); will(returnValue(mockNode4));
//
//			allowing(mockEdge7Pair).getOneNode(); will(returnValue(mockNode7));
//			allowing(mockEdge7Pair).getTwoNode(); will(returnValue(mockNode3));
//
//			allowing(mockEdge8Pair).getOneNode(); will(returnValue(mockNode1));
//			allowing(mockEdge8Pair).getTwoNode(); will(returnValue(mockNode1));

			atLeast(1).of(mockSubgraph).isInducedSubgraph(); will(returnValue(true));
			
		}});
		this.testInstance = new ChildCompoundGraph(mockRootNode);
//		this.testInstance.addNewNode(mockNode1);
//		this.testInstance.addNewNode(mockNode2);
//		this.testInstance.addNewEdge(mockEdge1);
//		this.testInstance.addNewEdge(mockEdge2);
//		this.testInstance.addNewEdge(mockEdge4);
//		this.testInstance.addNewEdge(mockEdge7);
		assertTrue("can copy", this.testInstance.canCopyHere(mockSubgraph));
		assertFalse("can copy", this.testInstance.canCopyHere(null));
		this.mockery.assertIsSatisfied();
	}

	@SuppressWarnings("unchecked")
	@Ignore @Test
	public final void testCanCopyHereNotInducedSubGraph() {
		final CompoundNode mockRootNode = this.mockery.mock(CompoundNode.class, "mockRootNode");
		final ArchetypalCompoundNode mockNode1 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode1");
		final ArchetypalCompoundNode mockNode2 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode2");
		final ArchetypalCompoundNode mockNode3 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode3");
		final ArchetypalCompoundNode mockNode4 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode4");
		final ArchetypalCompoundNode mockNode7 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode7");
		final ArchetypalCompoundEdge mockEdge1 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge1");
		final ArchetypalCompoundEdge mockEdge2 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge2");
		final ArchetypalCompoundEdge mockEdge4 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge4");
		final ArchetypalCompoundEdge mockEdge7 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge7");
		final ArchetypalCompoundEdge mockEdge8 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge8");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge1Pair = this.mockery.mock(IDirectedPair.class, "mockEdge1Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge2Pair = this.mockery.mock(IDirectedPair.class, "mockEdge2Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge4Pair = this.mockery.mock(IDirectedPair.class, "mockEdge4Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge7Pair = this.mockery.mock(IDirectedPair.class, "mockEdge7Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge8Pair = this.mockery.mock(IDirectedPair.class, "mockEdge8Pair");
		final ArchetypalCompoundGraph mockCigraph = this.mockery.mock(ArchetypalCompoundGraph.class, "mockCigraph");
		final ISubCompoundGraph<BaseCompoundNode, BaseCompoundEdge> mockSubgraph = this.mockery.mock(ISubCompoundGraph.class, "mockSubgraph");
		this.mockery.checking(new Expectations(){{
			allowing(mockRootNode).getGraph(); will(returnValue(mockCigraph));
			allowing(mockRootNode).getRoot(); will(returnValue(mockRootNode));
			
			allowing(mockNode1).compareTo(mockNode1); will(returnValue(0));
			allowing(mockNode1).compareTo(mockNode2); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode3); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode2).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode2).compareTo(mockNode2); will(returnValue(0));
			allowing(mockNode2).compareTo(mockNode3); will(returnValue(-1));
			allowing(mockNode2).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode2).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode3).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode3).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode3).compareTo(mockNode3); will(returnValue(0));
			allowing(mockNode3).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode3).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode4).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode3); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode4); will(returnValue(0));
			allowing(mockNode4).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode7).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode3); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode4); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode7); will(returnValue(0));
			
			allowing(mockEdge1).getConnectedNodes(); will(returnValue(mockEdge1Pair));
			allowing(mockEdge1).getIndex(); will(returnValue(EXPECTED_EDGE1_IDX));

			allowing(mockEdge2).getConnectedNodes(); will(returnValue(mockEdge2Pair));
			allowing(mockEdge2).getIndex(); will(returnValue(EXPECTED_EDGE2_IDX));

			allowing(mockEdge4).getConnectedNodes(); will(returnValue(mockEdge4Pair));
			allowing(mockEdge4).getIndex(); will(returnValue(EXPECTED_EDGE4_IDX));

			allowing(mockEdge7).getConnectedNodes(); will(returnValue(mockEdge7Pair));
			allowing(mockEdge7).getIndex(); will(returnValue(EXPECTED_EDGE7_IDX));
			
			allowing(mockEdge8).getConnectedNodes(); will(returnValue(mockEdge8Pair));
			allowing(mockEdge8).getIndex(); will(returnValue(EXPECTED_EDGE8_IDX));

//			allowing(mockEdge1Pair).getOneNode(); will(returnValue(mockNode1));
//			allowing(mockEdge1Pair).getTwoNode(); will(returnValue(mockNode2));
//
//			allowing(mockEdge2Pair).getOneNode(); will(returnValue(mockNode2));
//			allowing(mockEdge2Pair).getTwoNode(); will(returnValue(mockNode1));
//
//			allowing(mockEdge4Pair).getOneNode(); will(returnValue(mockNode2));
//			allowing(mockEdge4Pair).getTwoNode(); will(returnValue(mockNode4));
//
//			allowing(mockEdge7Pair).getOneNode(); will(returnValue(mockNode7));
//			allowing(mockEdge7Pair).getTwoNode(); will(returnValue(mockNode3));
//
//			allowing(mockEdge8Pair).getOneNode(); will(returnValue(mockNode1));
//			allowing(mockEdge8Pair).getTwoNode(); will(returnValue(mockNode1));

			atLeast(1).of(mockSubgraph).isInducedSubgraph(); will(returnValue(false));
			
		}});
		this.testInstance = new ChildCompoundGraph(mockRootNode);
//		this.testInstance.addNewNode(mockNode1);
//		this.testInstance.addNewNode(mockNode2);
//		this.testInstance.addNewEdge(mockEdge1);
//		this.testInstance.addNewEdge(mockEdge2);
//		this.testInstance.addNewEdge(mockEdge4);
//		this.testInstance.addNewEdge(mockEdge7);
		assertFalse("can copy", this.testInstance.canCopyHere(mockSubgraph));
		assertFalse("can copy", this.testInstance.canCopyHere(null));
		this.mockery.assertIsSatisfied();
	}

	@SuppressWarnings("unchecked")
	@Ignore @Test
	public final void testCopyHere() {
		final CompoundNode mockRootNode = this.mockery.mock(CompoundNode.class, "mockRootNode");
		final ArchetypalCompoundNode mockNode1 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode1");
		final ArchetypalCompoundNode mockNode2 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode2");
		final ArchetypalCompoundNode mockNode3 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode3");
		final ArchetypalCompoundNode mockNode4 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode4");
		final ArchetypalCompoundNode mockNode7 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode7");
		final ArchetypalCompoundEdge mockEdge1 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge1");
		final ArchetypalCompoundEdge mockEdge2 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge2");
		final ArchetypalCompoundEdge mockEdge4 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge4");
		final ArchetypalCompoundEdge mockEdge7 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge7");
		final ArchetypalCompoundEdge mockEdge8 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge8");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge1Pair = this.mockery.mock(IDirectedPair.class, "mockEdge1Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge2Pair = this.mockery.mock(IDirectedPair.class, "mockEdge2Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge4Pair = this.mockery.mock(IDirectedPair.class, "mockEdge4Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge7Pair = this.mockery.mock(IDirectedPair.class, "mockEdge7Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge8Pair = this.mockery.mock(IDirectedPair.class, "mockEdge8Pair");
		final ArchetypalCompoundGraph mockCigraph = this.mockery.mock(ArchetypalCompoundGraph.class, "mockCigraph");
		this.mockery.checking(new Expectations(){{
			allowing(mockRootNode).getGraph(); will(returnValue(mockCigraph));
			allowing(mockRootNode).getRoot(); will(returnValue(mockRootNode));
			
			allowing(mockNode1).compareTo(mockNode1); will(returnValue(0));
			allowing(mockNode1).compareTo(mockNode2); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode3); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode2).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode2).compareTo(mockNode2); will(returnValue(0));
			allowing(mockNode2).compareTo(mockNode3); will(returnValue(-1));
			allowing(mockNode2).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode2).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode3).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode3).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode3).compareTo(mockNode3); will(returnValue(0));
			allowing(mockNode3).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode3).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode4).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode3); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode4); will(returnValue(0));
			allowing(mockNode4).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode7).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode3); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode4); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode7); will(returnValue(0));
			
			allowing(mockEdge1).getConnectedNodes(); will(returnValue(mockEdge1Pair));
			allowing(mockEdge1).getIndex(); will(returnValue(EXPECTED_EDGE1_IDX));

			allowing(mockEdge2).getConnectedNodes(); will(returnValue(mockEdge2Pair));
			allowing(mockEdge2).getIndex(); will(returnValue(EXPECTED_EDGE2_IDX));

			allowing(mockEdge4).getConnectedNodes(); will(returnValue(mockEdge4Pair));
			allowing(mockEdge4).getIndex(); will(returnValue(EXPECTED_EDGE4_IDX));

			allowing(mockEdge7).getConnectedNodes(); will(returnValue(mockEdge7Pair));
			allowing(mockEdge7).getIndex(); will(returnValue(EXPECTED_EDGE7_IDX));
			
			allowing(mockEdge8).getConnectedNodes(); will(returnValue(mockEdge8Pair));
			allowing(mockEdge8).getIndex(); will(returnValue(EXPECTED_EDGE8_IDX));

//			allowing(mockEdge1Pair).getOneNode(); will(returnValue(mockNode1));
//			allowing(mockEdge1Pair).getTwoNode(); will(returnValue(mockNode2));
//
//			allowing(mockEdge2Pair).getOneNode(); will(returnValue(mockNode2));
//			allowing(mockEdge2Pair).getTwoNode(); will(returnValue(mockNode1));
//
//			allowing(mockEdge4Pair).getOneNode(); will(returnValue(mockNode2));
//			allowing(mockEdge4Pair).getTwoNode(); will(returnValue(mockNode4));
//
//			allowing(mockEdge7Pair).getOneNode(); will(returnValue(mockNode7));
//			allowing(mockEdge7Pair).getTwoNode(); will(returnValue(mockNode3));
//
//			allowing(mockEdge8Pair).getOneNode(); will(returnValue(mockNode1));
//			allowing(mockEdge8Pair).getTwoNode(); will(returnValue(mockNode1));

			
		}});
		this.testInstance = new ChildCompoundGraph(mockRootNode);
//		this.testInstance.addNewNode(mockNode1);
//		this.testInstance.addNewNode(mockNode2);
//		this.testInstance.addNewEdge(mockEdge1);
//		this.testInstance.addNewEdge(mockEdge2);
//		this.testInstance.addNewEdge(mockEdge4);
//		this.testInstance.addNewEdge(mockEdge7);
		this.mockery.assertIsSatisfied();
		final ISubCompoundGraph mockSubgraph = this.mockery.mock(ISubCompoundGraph.class, "mockSubgraph");
		final ArchetypalChildCompoundGraph mockChildgraph0 = this.mockery.mock(ArchetypalChildCompoundGraph.class, "mockChildgraph0");
		final ArchetypalChildCompoundGraph mockChildgraph2 = this.mockery.mock(ArchetypalChildCompoundGraph.class, "mockChildgraph2");
		final ArchetypalChildCompoundGraph mockChildgraph3 = this.mockery.mock(ArchetypalChildCompoundGraph.class, "mockChildgraph3");
		final ArchetypalCompoundNode mockSubRootNode = this.mockery.mock(ArchetypalCompoundNode.class, "mockSubRootNode");
		final ArchetypalCompoundNode mockSubNode0 = this.mockery.mock(ArchetypalCompoundNode.class, "mockSubNode0");
		final ArchetypalCompoundNode mockSubNode1 = this.mockery.mock(ArchetypalCompoundNode.class, "mockSubNode1");
		final ArchetypalCompoundNode mockSubNode2 = this.mockery.mock(ArchetypalCompoundNode.class, "mockSubNode2");
		final ArchetypalCompoundNode mockSubNode3 = this.mockery.mock(ArchetypalCompoundNode.class, "mockSubNode3");
		final ArchetypalCompoundNode mockSubNode4 = this.mockery.mock(ArchetypalCompoundNode.class, "mockSubNode4");
		final ArchetypalCompoundEdge mockSubEdge1 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockSubEdge1");
		final ArchetypalCompoundEdge mockSubEdge2 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockSubEdge2");
		final ArchetypalCompoundEdge mockSubEdge3 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockSubEdge3");
		final ArchetypalCompoundEdge mockSubEdge4 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockSubEdge4");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockSubEdge1Pair = this.mockery.mock(IDirectedPair.class, "mockSubEdge1Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockSubEdge2Pair = this.mockery.mock(IDirectedPair.class, "mockSubEdge2Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockSubEdge3Pair = this.mockery.mock(IDirectedPair.class, "mockSubEdge3Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockSubEdge4Pair = this.mockery.mock(IDirectedPair.class, "mockSubEdge4Pair");
//		final IndexCounter nodeCounter = new IndexCounter(EXPECTED_LAST_NODE_IDX); 
//		final IndexCounter edgeCounter = new IndexCounter(EXPECTED_LAST_EDGE_IDX); 
		this.mockery.checking(new Expectations(){{
			atLeast(1).of(mockSubgraph).isInducedSubgraph(); will(returnValue(true));
			atLeast(1).of(mockSubgraph).nodeIterator(); will(returnIterator(mockSubNode0, mockSubNode4));
			atLeast(1).of(mockSubgraph).edgeIterator(); will(returnIterator(mockSubEdge4));
			
//			atLeast(1).of(mockCigraph).getNodeCounter(); will(returnValue(nodeCounter));
//			atLeast(1).of(mockCigraph).getEdgeCounter(); will(returnValue(edgeCounter));
//			one(mockCigraph).getLcaNode(with(aNonNull(ArchetypalCompoundNode.class)), with(aNonNull(ArchetypalCompoundNode.class)));
			
			atLeast(1).of(mockRootNode).getChildCompoundGraph(); will(returnValue(testInstance));
			
			allowing(mockNode1).getIndex(); will(returnValue(EXPECTED_NODE1_IDX));
			
			allowing(mockNode2).getIndex(); will(returnValue(EXPECTED_NODE2_IDX));
			
			allowing(mockNode3).getIndex(); will(returnValue(EXPECTED_NODE3_IDX));
			
			allowing(mockNode4).getIndex(); will(returnValue(EXPECTED_NODE4_IDX));
			
			allowing(mockNode7).getIndex(); will(returnValue(EXPECTED_NODE7_IDX));

			allowing(mockSubEdge1).getConnectedNodes(); will(returnValue(mockSubEdge1Pair));
			allowing(mockSubEdge1).getIndex(); will(returnValue(EXPECTED_SUB_EDGE1_IDX));
			allowing(mockSubEdge1).getOwningChildGraph(); will(returnValue(mockChildgraph0));

//			allowing(mockSubEdge1Pair).getOneNode(); will(returnValue(mockSubNode1));
//			allowing(mockSubEdge1Pair).getTwoNode(); will(returnValue(mockSubNode2));
			
			allowing(mockSubEdge2).getConnectedNodes(); will(returnValue(mockSubEdge2Pair));
			allowing(mockSubEdge2).getIndex(); will(returnValue(EXPECTED_SUB_EDGE2_IDX));
			allowing(mockSubEdge2).getOwningChildGraph(); will(returnValue(mockChildgraph2));

//			allowing(mockSubEdge2Pair).getOneNode(); will(returnValue(mockSubNode3));
//			allowing(mockSubEdge2Pair).getTwoNode(); will(returnValue(mockSubNode2));
			
			allowing(mockSubEdge3).getConnectedNodes(); will(returnValue(mockSubEdge3Pair));
			allowing(mockSubEdge3).getIndex(); will(returnValue(EXPECTED_SUB_EDGE3_IDX));
			allowing(mockSubEdge3).getOwningChildGraph(); will(returnValue(mockChildgraph3));

//			allowing(mockSubEdge3Pair).getOneNode(); will(returnValue(mockSubNode3));
//			allowing(mockSubEdge3Pair).getTwoNode(); will(returnValue(mockSubNode3));
			
			allowing(mockSubEdge4).getConnectedNodes(); will(returnValue(mockSubEdge4Pair));
			allowing(mockSubEdge4).getIndex(); will(returnValue(EXPECTED_SUB_EDGE4_IDX));
			allowing(mockSubEdge4).getOwningChildGraph(); will(returnValue(mockChildgraph0));

			allowing(mockSubEdge4Pair).getInNode(); will(returnValue(mockSubNode4));
			allowing(mockSubEdge4Pair).getOutNode(); will(returnValue(mockSubNode3));
			
			allowing(mockSubNode0).childIterator(); will(returnIterator(mockSubNode1, mockSubNode2));
			allowing(mockSubNode0).getParent(); will(returnValue(mockSubRootNode));
			
			allowing(mockSubNode1).childIterator(); will(returnIterator(mockSubNode3));
			allowing(mockSubNode1).getParent(); will(returnValue(mockSubNode0));
			
			allowing(mockSubNode2).childIterator(); will(returnIterator());
			allowing(mockSubNode2).getParent(); will(returnValue(mockSubNode0));
			
			allowing(mockSubNode3).childIterator(); will(returnIterator());
			allowing(mockSubNode3).getParent(); will(returnValue(mockSubNode1));
			
			allowing(mockSubNode4).childIterator(); will(returnIterator());
			allowing(mockSubNode4).getParent(); will(returnValue(mockSubRootNode));
			
			allowing(mockChildgraph0).getRootNode(); will(returnValue(mockSubRootNode));
			
			allowing(mockChildgraph2).getRootNode(); will(returnValue(mockSubNode2));
			
			allowing(mockChildgraph3).getRootNode(); will(returnValue(mockSubNode3));
		}});
		this.testInstance.copyHere(mockSubgraph);
		assertEquals("num nodes", EXPECTED_POST_COPY_NUM_NODES, this.testInstance.getNumNodes());
		assertEquals("num edges", EXPECTED_POST_COPY_NUM_EDGES, this.testInstance.getNumEdges());
		this.mockery.assertIsSatisfied();
	}

	@SuppressWarnings("unchecked")
	@Ignore @Test
	public final void testIsInducedSubgraph() {
		final CompoundNode mockRootNode = this.mockery.mock(CompoundNode.class, "mockRootNode");
		final ArchetypalCompoundNode mockNode1 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode1");
		final ArchetypalCompoundNode mockNode2 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode2");
		final ArchetypalCompoundNode mockNode3 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode3");
		final ArchetypalCompoundNode mockNode4 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode4");
		final ArchetypalCompoundNode mockNode7 = this.mockery.mock(ArchetypalCompoundNode.class, "mockNode7");
		final ArchetypalCompoundEdge mockEdge1 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge1");
		final ArchetypalCompoundEdge mockEdge2 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge2");
		final ArchetypalCompoundEdge mockEdge4 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge4");
		final ArchetypalCompoundEdge mockEdge7 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge7");
		final ArchetypalCompoundEdge mockEdge8 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge8");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge1Pair = this.mockery.mock(IDirectedPair.class, "mockEdge1Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge2Pair = this.mockery.mock(IDirectedPair.class, "mockEdge2Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge4Pair = this.mockery.mock(IDirectedPair.class, "mockEdge4Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge7Pair = this.mockery.mock(IDirectedPair.class, "mockEdge7Pair");
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockEdge8Pair = this.mockery.mock(IDirectedPair.class, "mockEdge8Pair");
		final ArchetypalCompoundGraph mockCigraph = this.mockery.mock(ArchetypalCompoundGraph.class, "mockCigraph");
		this.mockery.checking(new Expectations(){{
			allowing(mockRootNode).getGraph(); will(returnValue(mockCigraph));
			allowing(mockRootNode).getRoot(); will(returnValue(mockRootNode));
			
			allowing(mockNode1).compareTo(mockNode1); will(returnValue(0));
			allowing(mockNode1).compareTo(mockNode2); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode3); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode1).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode2).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode2).compareTo(mockNode2); will(returnValue(0));
			allowing(mockNode2).compareTo(mockNode3); will(returnValue(-1));
			allowing(mockNode2).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode2).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode3).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode3).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode3).compareTo(mockNode3); will(returnValue(0));
			allowing(mockNode3).compareTo(mockNode4); will(returnValue(-1));
			allowing(mockNode3).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode4).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode3); will(returnValue(1));
			allowing(mockNode4).compareTo(mockNode4); will(returnValue(0));
			allowing(mockNode4).compareTo(mockNode7); will(returnValue(-1));
			
			allowing(mockNode7).compareTo(mockNode1); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode2); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode3); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode4); will(returnValue(1));
			allowing(mockNode7).compareTo(mockNode7); will(returnValue(0));
			
			allowing(mockEdge1).getConnectedNodes(); will(returnValue(mockEdge1Pair));
			allowing(mockEdge1).getIndex(); will(returnValue(EXPECTED_EDGE1_IDX));

			allowing(mockEdge2).getConnectedNodes(); will(returnValue(mockEdge2Pair));
			allowing(mockEdge2).getIndex(); will(returnValue(EXPECTED_EDGE2_IDX));

			allowing(mockEdge4).getConnectedNodes(); will(returnValue(mockEdge4Pair));
			allowing(mockEdge4).getIndex(); will(returnValue(EXPECTED_EDGE4_IDX));

			allowing(mockEdge7).getConnectedNodes(); will(returnValue(mockEdge7Pair));
			allowing(mockEdge7).getIndex(); will(returnValue(EXPECTED_EDGE7_IDX));
			
			allowing(mockEdge8).getConnectedNodes(); will(returnValue(mockEdge8Pair));
			allowing(mockEdge8).getIndex(); will(returnValue(EXPECTED_EDGE8_IDX));

//			allowing(mockEdge1Pair).getOneNode(); will(returnValue(mockNode1));
//			allowing(mockEdge1Pair).getTwoNode(); will(returnValue(mockNode2));
//
//			allowing(mockEdge2Pair).getOneNode(); will(returnValue(mockNode2));
//			allowing(mockEdge2Pair).getTwoNode(); will(returnValue(mockNode1));
//
//			allowing(mockEdge4Pair).getOneNode(); will(returnValue(mockNode2));
//			allowing(mockEdge4Pair).getTwoNode(); will(returnValue(mockNode4));
//
//			allowing(mockEdge7Pair).getOneNode(); will(returnValue(mockNode7));
//			allowing(mockEdge7Pair).getTwoNode(); will(returnValue(mockNode3));
//
//			allowing(mockEdge8Pair).getOneNode(); will(returnValue(mockNode1));
//			allowing(mockEdge8Pair).getTwoNode(); will(returnValue(mockNode1));

		}});
		this.testInstance = new ChildCompoundGraph(mockRootNode);
//		this.testInstance.addNewNode(mockNode1);
//		this.testInstance.addNewNode(mockNode2);
//		this.testInstance.addNewEdge(mockEdge1);
//		this.testInstance.addNewEdge(mockEdge2);
//		this.testInstance.addNewEdge(mockEdge4);
//		this.testInstance.addNewEdge(mockEdge7);
		assertFalse("induced subgraph", this.testInstance.isInducedSubgraph());
		this.mockery.assertIsSatisfied();
	}

//	@SuppressWarnings("unchecked")
//	@Test
//	public final void testGetLcaNode() {
//		final CiNode mockRootNode = this.mockery.mock(CiNode.class, "mockRootNode");
//		final CiNode mockNode1 = this.mockery.mock(CiNode.class, "mockNode1");
//		final CiNode mockNode2 = this.mockery.mock(CiNode.class, "mockNode2");
//		final CiNode mockNode3 = this.mockery.mock(CiNode.class, "mockNode3");
//		final CiNode mockNode4 = this.mockery.mock(CiNode.class, "mockNode4");
//		final CiNode mockNode6 = this.mockery.mock(CiNode.class, "mockNode6");
//		final CiNode mockNode7 = this.mockery.mock(CiNode.class, "mockNode7");
//		final CiEdge mockEdge1 = this.mockery.mock(CiEdge.class, "mockEdge1");
//		final CiEdge mockEdge2 = this.mockery.mock(CiEdge.class, "mockEdge2");
//		final CiEdge mockEdge4 = this.mockery.mock(CiEdge.class, "mockEdge4");
//		final CiEdge mockEdge7 = this.mockery.mock(CiEdge.class, "mockEdge7");
//		final CiEdge mockEdge8 = this.mockery.mock(CiEdge.class, "mockEdge8");
//		final IDirectedPair<CiNode, CiEdge> mockEdge1Pair = this.mockery.mock(IDirectedPair.class, "mockEdge1Pair");
//		final IDirectedPair<CiNode, CiEdge> mockEdge2Pair = this.mockery.mock(IDirectedPair.class, "mockEdge2Pair");
//		final IDirectedPair<CiNode, CiEdge> mockEdge4Pair = this.mockery.mock(IDirectedPair.class, "mockEdge4Pair");
//		final IDirectedPair<CiNode, CiEdge> mockEdge7Pair = this.mockery.mock(IDirectedPair.class, "mockEdge7Pair");
//		final IDirectedPair<CiNode, CiEdge> mockEdge8Pair = this.mockery.mock(IDirectedPair.class, "mockEdge8Pair");
//		final Cigraph mockCigraph = this.mockery.mock(Cigraph.class, "mockCigraph");
//		this.mockery.checking(new Expectations(){{
//			allowing(mockRootNode).getGraph(); will(returnValue(mockCigraph));
//			allowing(mockRootNode).getParent(); will(returnValue(mockRootNode));
//			allowing(mockRootNode).getRoot(); will(returnValue(mockRootNode));
//			
//			allowing(mockNode1).compareTo(mockNode1); will(returnValue(0));
//			allowing(mockNode1).compareTo(mockNode2); will(returnValue(-1));
//			allowing(mockNode1).compareTo(mockNode3); will(returnValue(-1));
//			allowing(mockNode1).compareTo(mockNode4); will(returnValue(-1));
//			allowing(mockNode1).compareTo(mockNode7); will(returnValue(-1));
//			allowing(mockNode1).getParent(); will(returnValue(mockRootNode));
//			
//			allowing(mockNode2).compareTo(mockNode1); will(returnValue(1));
//			allowing(mockNode2).compareTo(mockNode2); will(returnValue(0));
//			allowing(mockNode2).compareTo(mockNode3); will(returnValue(-1));
//			allowing(mockNode2).compareTo(mockNode4); will(returnValue(-1));
//			allowing(mockNode2).compareTo(mockNode7); will(returnValue(-1));
//			allowing(mockNode2).getParent(); will(returnValue(mockRootNode));
//			
//			allowing(mockNode3).compareTo(mockNode1); will(returnValue(1));
//			allowing(mockNode3).compareTo(mockNode2); will(returnValue(1));
//			allowing(mockNode3).compareTo(mockNode3); will(returnValue(0));
//			allowing(mockNode3).compareTo(mockNode4); will(returnValue(-1));
//			allowing(mockNode3).compareTo(mockNode7); will(returnValue(-1));
//			allowing(mockNode3).getParent(); will(returnValue(mockNode1));
//			
//			allowing(mockNode4).compareTo(mockNode1); will(returnValue(1));
//			allowing(mockNode4).compareTo(mockNode2); will(returnValue(1));
//			allowing(mockNode4).compareTo(mockNode3); will(returnValue(1));
//			allowing(mockNode4).compareTo(mockNode4); will(returnValue(0));
//			allowing(mockNode4).compareTo(mockNode7); will(returnValue(-1));
//			allowing(mockNode4).getParent(); will(returnValue(mockNode1));
//			
//			allowing(mockNode6).getParent(); will(returnValue(mockNode2));
//
//			allowing(mockNode7).compareTo(mockNode1); will(returnValue(1));
//			allowing(mockNode7).compareTo(mockNode2); will(returnValue(1));
//			allowing(mockNode7).compareTo(mockNode3); will(returnValue(1));
//			allowing(mockNode7).compareTo(mockNode4); will(returnValue(1));
//			allowing(mockNode7).compareTo(mockNode7); will(returnValue(0));
//			allowing(mockNode7).getParent(); will(returnValue(mockNode6));
//			
//			allowing(mockEdge1).getConnectedNodes(); will(returnValue(mockEdge1Pair));
//			allowing(mockEdge1).getIndex(); will(returnValue(EXPECTED_EDGE1_IDX));
//
//			allowing(mockEdge2).getConnectedNodes(); will(returnValue(mockEdge2Pair));
//			allowing(mockEdge2).getIndex(); will(returnValue(EXPECTED_EDGE2_IDX));
//
//			allowing(mockEdge4).getConnectedNodes(); will(returnValue(mockEdge4Pair));
//			allowing(mockEdge4).getIndex(); will(returnValue(EXPECTED_EDGE4_IDX));
//
//			allowing(mockEdge7).getConnectedNodes(); will(returnValue(mockEdge7Pair));
//			allowing(mockEdge7).getIndex(); will(returnValue(EXPECTED_EDGE7_IDX));
//			
//			allowing(mockEdge8).getConnectedNodes(); will(returnValue(mockEdge8Pair));
//			allowing(mockEdge8).getIndex(); will(returnValue(EXPECTED_EDGE8_IDX));
//
////			allowing(mockEdge1Pair).getOneNode(); will(returnValue(mockNode1));
////			allowing(mockEdge1Pair).getTwoNode(); will(returnValue(mockNode2));
////
////			allowing(mockEdge2Pair).getOneNode(); will(returnValue(mockNode2));
////			allowing(mockEdge2Pair).getTwoNode(); will(returnValue(mockNode1));
////
////			allowing(mockEdge4Pair).getOneNode(); will(returnValue(mockNode2));
////			allowing(mockEdge4Pair).getTwoNode(); will(returnValue(mockNode4));
////
////			allowing(mockEdge7Pair).getOneNode(); will(returnValue(mockNode7));
////			allowing(mockEdge7Pair).getTwoNode(); will(returnValue(mockNode3));
////
////			allowing(mockEdge8Pair).getOneNode(); will(returnValue(mockNode1));
////			allowing(mockEdge8Pair).getTwoNode(); will(returnValue(mockNode1));
//			
//		}});
//		this.testInstance = new ChildCigraph(mockRootNode);
//		this.testInstance.addNewNode(mockNode1);
//		this.testInstance.addNewNode(mockNode2);
//		this.testInstance.addNewEdge(mockEdge1);
//		this.testInstance.addNewEdge(mockEdge2);
//		this.testInstance.addNewEdge(mockEdge4);
//		this.testInstance.addNewEdge(mockEdge7);
//		assertEquals("expected lca", mockNode1, this.testInstance.getLcaNode(mockNode1, mockNode1));
//		assertEquals("expected lca", mockRootNode, this.testInstance.getLcaNode(mockNode7, mockNode3));
//		this.mockery.assertIsSatisfied();
//	}
	
}
