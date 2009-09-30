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
package uk.ed.inf.graph.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.log4j.BasicConfigurator;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ed.inf.graph.basic.IBasicEdge;
import uk.ed.inf.graph.basic.IBasicEdgeFactory;
import uk.ed.inf.graph.basic.IBasicNode;
import uk.ed.inf.graph.basic.IBasicNodeFactory;
import uk.ed.inf.graph.basic.IBasicPair;
import uk.ed.inf.graph.basic.IBasicSubgraph;
import uk.ed.inf.graph.basic.IBasicSubgraphFactory;
import uk.ed.inf.graph.undirected.IUndirectedEdgeFactory;

@RunWith(JMock.class)
public class GraphTest {
	private Mockery mockery = new JUnit4Mockery();

	private static int EXPECTED_NODE1_IDX = 1;
	private static int EXPECTED_NODE4_IDX = 4;
	private static int EXPECTED_NODE7_IDX = 7;
	private static int EXPECTED_NUM_NODES = 6;
	private static int EXPECTED_NUM_EDGES = 7;
	private static int EXPECTED_NODE_DEGREES[] = { 2, 4, 3, 1, 0, 4 };  	
	private static int EXPECTED_NODE_ITER_IDX[] = { 0, 1, 2, 3, 4, 5 };  	
	private static int EXPECTED_EDGE_ITER_IDX[] = { 0, 1, 2, 4, 3, 5, 6 };  	

	private static final int EXPECTED_EMPTY_NODES = 0;
	private static final int EXPECTED_EMPTY_EDGES = 0;
	private static final int EXPECTED_NUM_NODE_POST_REMOVAL = 4;
	private static final int EXPECTED_NUM_EDGES_POST_REMOVAL = 3;
	private static final int EXPECTED_NODE0_DEGREE_POST_REMOVAL = 1;
	private static final int EXPECTED_NODE2_DEGREE_POST_REMOVAL = 2;
	private static final int EXPECTED_NODE3_DEGREE_POST_REMOVAL = 1;
	private static final int EXPECTED_NODE5_DEGREE_POST_REMOVAL = 2;

	private Graph testInstance;
	private Graph testEmptyInstance;
	private Node node0;
	private Node node1;
	private Node node2;
	private Node node3;
	private Node node4;
	private Node node5;
	private Edge edge0;
	private Edge edge1;
	private Edge edge2;
	private Edge edge3;
	private Edge edge4;
	private Edge edge5;
	private Edge edge6;
	
//	@BeforeClass
//	public void setupTest(){
//		BasicConfigurator.configure();
//	}
//	
	@Before
	public void setUp() throws Exception {
		BasicConfigurator.configure();

		this.testEmptyInstance = new Graph();
		this.testInstance = new Graph();
		IBasicNodeFactory<Node, Edge> nodeFactory = this.testInstance.nodeFactory();
		node0 = nodeFactory.createNode();
		node1 = nodeFactory.createNode();
		node2 = nodeFactory.createNode();
		node3 = nodeFactory.createNode();
		node4 = nodeFactory.createNode();
		node5 = nodeFactory.createNode();
		
		IUndirectedEdgeFactory<Node, Edge> edgeFactory = this.testInstance.edgeFactory();
		edgeFactory.setPair(node0, node1);
		edge0 = edgeFactory.createEdge();
		edgeFactory.setPair(node0, node2);
		edge1 = edgeFactory.createEdge();
		edgeFactory.setPair(node1, node2);
		edge2 = edgeFactory.createEdge();
		edgeFactory.setPair(node2, node3);
		edge3 = edgeFactory.createEdge();
		edgeFactory.setPair(node1, node1);
		edge4 = edgeFactory.createEdge();
		edgeFactory.setPair(node5, node5);
		edge5 = edgeFactory.createEdge();
		edge6 = edgeFactory.createEdge();
	}

	@After
	public void tearDown() throws Exception {
		this.testInstance = null;
		this.testEmptyInstance = null;
	}
	
	@Test
	public final void testGraphSetupAsExpected(){
		assertEquals("empty has no nodes", this.testEmptyInstance.getNumNodes(), EXPECTED_EMPTY_NODES);
		assertEquals("empty has no edges", this.testEmptyInstance.getNumEdges(), EXPECTED_EMPTY_EDGES);
		assertEquals("expected test nodes", this.testInstance.getNumNodes(), EXPECTED_NUM_NODES);
		assertEquals("expected test edges", this.testInstance.getNumEdges(), EXPECTED_NUM_EDGES);
		{
		Iterator<Node> nodeIter = this.testInstance.nodeIterator();
		while(nodeIter.hasNext()){
			Node node = nodeIter.next();
			assertEquals("expected degree", EXPECTED_NODE_DEGREES[node.getIndex()], node.getDegree());
		}
		}
		{
			int i = 0;
			Iterator<Node> nodeIter = this.testInstance.nodeIterator();
			while(nodeIter.hasNext()){
				Node node = nodeIter.next();
				assertEquals("expected degree", EXPECTED_NODE_ITER_IDX[i], node.getIndex());
				i++;
			}
		}
		{
			Iterator<Edge> edgeIter = this.testInstance.edgeIterator();
			int i = 0;
			while(edgeIter.hasNext()){
				Edge edge = edgeIter.next();
				assertEquals("expected edge idx", EXPECTED_EDGE_ITER_IDX[i], edge.getIndex());
				i++;
			}
		}
		Iterator<Edge> edgeIter = this.testInstance.edgeIterator();
		{
			IBasicPair<Node, Edge> expectedPair = edgeIter.next().getConnectedNodes();
			assertTrue("expected edge incident node", expectedPair.containsNode(node0));
			assertTrue("expected edge incident node", expectedPair.containsNode(node1));
		}
		{
			IBasicPair<Node, Edge> expectedPair = edgeIter.next().getConnectedNodes();
			assertTrue("expected edge incident node", expectedPair.containsNode(node0));
			assertTrue("expected edge incident node", expectedPair.containsNode(node2));
		}
		{
			IBasicPair<Node, Edge> expectedPair = edgeIter.next().getConnectedNodes();
			assertTrue("expected edge incident node", expectedPair.containsNode(node2));
			assertTrue("expected edge incident node", expectedPair.containsNode(node1));
		}
		{
			IBasicPair<Node, Edge> expectedPair = edgeIter.next().getConnectedNodes();
			assertTrue("expected edge incident node", expectedPair.containsNode(node1));
			assertTrue("expected edge incident node", expectedPair.containsNode(node1));
		}
		{
			IBasicPair<Node, Edge> expectedPair = edgeIter.next().getConnectedNodes();
			assertTrue("expected edge incident node", expectedPair.containsNode(node2));
			assertTrue("expected edge incident node", expectedPair.containsNode(node3));
		}
		{
			IBasicPair<Node, Edge> expectedPair = edgeIter.next().getConnectedNodes();
			assertTrue("expected edge incident node", expectedPair.containsNode(node5));
			assertTrue("expected edge incident node", expectedPair.containsNode(node5));
		}
		{
			IBasicPair<Node, Edge> expectedPair = edgeIter.next().getConnectedNodes();
			assertTrue("expected edge incident node", expectedPair.containsNode(node5));
			assertTrue("expected edge incident node", expectedPair.containsNode(node5));
		}
	}
	
	@Test(expected=IllegalArgumentException.class)
	public final void testGetNodeFromEmpty() {
		this.testEmptyInstance.getNode(EXPECTED_NODE1_IDX);
	}

	@Test
	public final void testGetNode() {
		Node node = this.testInstance.getNode(EXPECTED_NODE1_IDX);
		int expectedNodeIdx = EXPECTED_NODE1_IDX;
		int actualNodeIdx = node.getIndex();
		assertEquals("correct node", expectedNodeIdx, actualNodeIdx);
	}

	@Test(expected=IllegalArgumentException.class)
	public final void testGetNodeNoNode() {
		Node node = this.testInstance.getNode(EXPECTED_NODE7_IDX);
		int expectedNodeIdx = EXPECTED_NODE7_IDX;
		int actualNodeIdx = node.getIndex();
		assertEquals("correct node", expectedNodeIdx, actualNodeIdx);
	}

	@Test
	public final void testCanCreateEdges() {
		assertTrue("can create edge", this.testInstance.canCreateEdges());
	}

	@Test
	public final void testCanCreateNodes() {
		assertTrue("can create node", this.testInstance.canCreateNodes());
	}

	@Test
	public final void testCanCreateSubgraphs() {
		assertTrue("can create subgraphs", this.testInstance.canCreateSubgraphs());
	}

	@Test
	public final void testCanRemoveSubgraphs() {
		assertTrue("can remove subgraphs", this.testInstance.canRemoveSubgraphs());
	}

	@Test
	public final void testContainsNodeINode() {
		assertFalse("expected no node", this.testEmptyInstance.containsNode(this.node0));
		assertTrue("expected node 0", this.testInstance.containsNode(this.node0));
		assertTrue("expected node 4", this.testInstance.containsNode(this.node4));
		assertFalse("expected no null node", this.testInstance.containsNode(null));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public final void testContainsAfterNodeRemoval(){
		final IBasicSubgraph<Node, Edge> mockSubgraph = mockery.mock(IBasicSubgraph.class, "mockSubgraph");
		mockery.checking(new Expectations() {{
			atLeast(1).of(mockSubgraph).nodeIterator(); will(returnIterator(node1, node4));
			atLeast(1).of(mockSubgraph).edgeIterator(); will(returnIterator(edge5));
			atLeast(1).of(mockSubgraph).getSuperGraph(); will(returnValue(testInstance));
		}});
		this.testInstance.removeSubgraph(mockSubgraph);
		int actualNumNodes = this.testInstance.getNumNodes();
		int actualNumEdges = this.testInstance.getNumEdges();
		int actualNode0Degree = this.node0.getDegree();
		int actualNode2Degree = this.node2.getDegree();
		int actualNode3Degree = this.node3.getDegree();
		int actualNode5Degree = this.node5.getDegree();
		boolean actualNode1Exists = this.testInstance.containsNode(node1);
		boolean actualNode1IdxExists = this.testInstance.containsNode(EXPECTED_NODE1_IDX);
		boolean actualNode4Exists = this.testInstance.containsNode(node4);
		boolean actualNode4IdxExists = this.testInstance.containsNode(EXPECTED_NODE4_IDX);
		this.mockery.assertIsSatisfied();
		assertEquals("expected num nodes", EXPECTED_NUM_NODE_POST_REMOVAL, actualNumNodes);
		assertEquals("expected num edges", EXPECTED_NUM_EDGES_POST_REMOVAL, actualNumEdges);
		assertEquals("expected node degree", EXPECTED_NODE0_DEGREE_POST_REMOVAL, actualNode0Degree);
		assertEquals("expected node degree", EXPECTED_NODE2_DEGREE_POST_REMOVAL, actualNode2Degree);
		assertEquals("expected node degree", EXPECTED_NODE3_DEGREE_POST_REMOVAL, actualNode3Degree);
		assertEquals("expected node degree", EXPECTED_NODE5_DEGREE_POST_REMOVAL, actualNode5Degree);
		assertFalse("expected node missing", actualNode1Exists);
		assertFalse("expected node missing", actualNode1IdxExists);
		assertFalse("expected node missing", actualNode4Exists);
		assertFalse("expected node missing", actualNode4IdxExists);
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected=IllegalArgumentException.class)
	public final void testContainsAfterNodeRemovalWithDifferentSupergraph(){
		final IBasicSubgraph<Node, Edge> mockSubgraph = mockery.mock(IBasicSubgraph.class, "mockSubgraph");
		mockery.checking(new Expectations() {{
			allowing(mockSubgraph).nodeIterator(); will(returnIterator(node1, node4));
			allowing(mockSubgraph).edgeIterator(); will(returnIterator(edge5));
			allowing(mockSubgraph).getSuperGraph(); will(returnValue(testInstance));
		}});
		this.testEmptyInstance.removeSubgraph(mockSubgraph);
	}

	@SuppressWarnings("unchecked")
	@Test(expected=IllegalStateException.class)
	public final void testContainsAfterNodeRemovalWithMissingNodes(){
		final IBasicSubgraph<Node, Edge> mockSubgraph = mockery.mock(IBasicSubgraph.class, "mockSubgraph");
		mockery.checking(new Expectations() {{
			atLeast(1).of(mockSubgraph).nodeIterator(); will(returnIterator(node1, node4));
			atLeast(1).of(mockSubgraph).edgeIterator(); will(returnIterator(edge5));
			atLeast(1).of(mockSubgraph).getSuperGraph(); will(returnValue(testEmptyInstance));
		}});
		this.testEmptyInstance.removeSubgraph(mockSubgraph);
	}

	@Test
	public final void testContainsEdgeINodeINode() {
		assertTrue("expected edge", this.testInstance.containsConnection(this.node1, this.node2));
		assertTrue("expected edge", this.testInstance.containsConnection(this.node2, this.node1));
		assertTrue("expected edge", this.testInstance.containsConnection(this.node5, this.node5));
		assertFalse("expected no edge", this.testInstance.containsConnection(this.node3, this.node1));
		assertFalse("expected no edge", this.testInstance.containsConnection(null, null));
		assertFalse("expected no edge", this.testInstance.containsConnection(this.node2, null));
		assertFalse("expected no edge", this.testInstance.containsConnection(null, this.node5));
		assertTrue("expected no edge", this.testEmptyInstance.containsConnection(this.node1, this.node2));
	}

	@Test
	public final void testCreateEdgeFactory() {
		IBasicEdgeFactory<Node, Edge> fact1 = this.testInstance.edgeFactory();
		IBasicEdgeFactory<Node, Edge> fact2 = this.testInstance.edgeFactory();
		assertNotNull("factory created", fact1);
		assertTrue("factory not the same", fact1 != fact2);
	}

	@Test
	public final void testCreateNodeFactory() {
		IBasicNodeFactory<Node, Edge> fact1 = this.testInstance.nodeFactory();
		IBasicNodeFactory<Node, Edge> fact2 = this.testInstance.nodeFactory();
		assertNotNull("factory created", fact1);
		assertTrue("factory not the same", fact1 != fact2);
	}

	@Test
	public final void testCreateSubgraphFactory() {
		IBasicSubgraphFactory<Node, Edge> fact1 = this.testInstance.subgraphFactory();
		IBasicSubgraphFactory<Node, Edge> fact2 = this.testInstance.subgraphFactory();
		assertNotNull("factory created", fact1);
		assertTrue("factory not the same", fact1 != fact2);
	}

	@Test
	public final void testGetEdgeIterator() {
		Iterator<Edge> iter = this.testInstance.edgeIterator();
		assertNotNull("iterator exits", iter);
		Edge expectedIterationOrder[] = { edge0, edge1, edge2, edge4, edge3, edge5, edge6 };
		List<Edge> expectedEdgeList = Arrays.asList(expectedIterationOrder);
		for(Edge expectedEdge : expectedEdgeList){
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
	}

	@Test
	public final void testGetNodeIterator() {
		Iterator<Node> iter = this.testInstance.nodeIterator();
		Node expectedIterationOrder[] = { node0, node1, node2, node3, node4, node5 };
		List<Node> expectedNodeList = Arrays.asList(expectedIterationOrder);
		for(Node expectedNode : expectedNodeList){
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
	}

	@Test
	public final void testGetNumEdges() {
		assertEquals("expected num edges", this.testInstance.getNumEdges(), EXPECTED_NUM_EDGES); 
		assertEquals("expected empty num edges", this.testEmptyInstance.getNumEdges(), EXPECTED_EMPTY_EDGES); 
	}

	@Test
	public final void testGetNumNodes() {
		assertEquals("expected num nodes", this.testInstance.getNumNodes(), EXPECTED_NUM_NODES); 
		assertEquals("expected empty num nodes", this.testEmptyInstance.getNumNodes(), EXPECTED_EMPTY_NODES); 
	}

	@Test
	public final void testContainsEdgeIEdge() {
		assertTrue("expected edge", this.testInstance.containsEdge(this.edge0));
	}

	@Test
	public final void testContainsEdgeInt() {
		assertTrue("expected edge", this.testInstance.containsEdge(this.edge0.getIndex()));
	}

	@Test
	public final void testContainsNodeInt() {
		assertTrue("expected node", this.testInstance.containsNode(this.node0.getIndex()));
	}

	@Test
	public final void testGetEdge() {
		assertEquals("expected edge", this.edge0, this.testInstance.getEdge(this.edge0.getIndex()));
		assertFalse("expected edge", this.edge0 == this.testInstance.getEdge(this.edge1.getIndex()));
	}

	private interface TestEdge extends IBasicEdge<TestNode, TestEdge> {
		
	}
	
	private interface TestNode extends IBasicNode<TestNode, TestEdge> {
		
	}
}
