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
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ed.inf.graph.util.impl.ConnectedNodeIterator;

public class NodeTest {
	
	private Node testNode ;
//	private Node otherNode ;
	
	private static Graph mockGraph ;
	private static Edge mockEdge ;
	private static Edge mockEdgeTwo ;
	private static Node mockOneNode ;
	private static Node mockTwoNode ;
	
	private static NodeFactory nodeFactory ;
	private static EdgeFactory edgeFactory ;
	
//	private static final int NODE_INDEX_ONE = 1 ;
//	private static final int NODE_INDEX_TWO = 2 ;
//	private static final int NODE_INDEX_THREE = 3 ;
//	private static final int EDGE_INDEX_ONE = 1 ;
//	private static final int EDGE_INDEX_TWO = 2 ;
	
	private static final int COMPARE_GREATER_INDEX = 1 ;
	private static final int COMPARE_LESSER_INDEX = -1 ;
	private static final int COMPARE_SAME_INDEX = 0 ;
	
	private static final int EXPECTED_NUM_NODE1_EDGES = 2;
	private static final int EXPECTED_NODE1_DEGREE = 2;
	private static final int EXPECTED_NODE2_IDX = 1;
	
	@Before
	public void setUp() throws Exception {
		
		mockGraph = new Graph () ;
		
		nodeFactory = mockGraph.nodeFactory() ;
		edgeFactory = mockGraph.edgeFactory() ;
		
		mockOneNode = nodeFactory.createNode() ;
		mockTwoNode = nodeFactory.createNode() ;
		

		
		edgeFactory.setPair(mockOneNode, mockTwoNode ) ;
		mockEdge = edgeFactory.createEdge() ;
		mockEdgeTwo = edgeFactory.createEdge() ;
		
//		testNode = nodeFactory.createNode() ;
//		otherNode = nodeFactory.createNode() ;
		testNode = mockOneNode;
//		mockOneNode.addEdge(mockEdge) ;
//		testNode.addEdge(mockEdge) ;
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testHashCode() {
		assertEquals ("same hash code" , testNode , testNode ) ;
	}

	@Test
	public final void testConnectedNodeIterator() {
		ConnectedNodeIterator<Node, Edge> validation = new ConnectedNodeIterator<Node, Edge> ( mockOneNode , mockOneNode.edgeIterator()) ;
		
		Iterator<Node> toCheck = mockOneNode.connectedNodeIterator() ;
		
		while ( validation.hasNext())
		{
			assertEquals ( "same object" , validation.next() , toCheck.next()) ;
		}
		
		assertFalse ( "no more" , toCheck.hasNext()) ;
		
	}

	@Test
	public final void testGetDegree() {
		assertEquals ( "degree" ,EXPECTED_NODE1_DEGREE , testNode.getDegree() );
	}

	@Test
	public final void testEdgeIterator() {
		Edge edgeArray [] = {mockEdge, mockEdgeTwo} ;
		
		Iterator<Edge> edgeIterator = testNode.edgeIterator() ;
		
		int counter = 0 ;
		
		while ( edgeIterator.hasNext() )
		{
			assertEquals ( "egde iterator item" , edgeArray[counter] , edgeIterator.next() ) ;
			counter ++ ;
		}
	}

	@Test
	public final void testGetEdgesWith() {
		assertEquals ( "get Edges" , EXPECTED_NUM_NODE1_EDGES , mockOneNode.getEdgesWith(mockTwoNode ).size() );
		assertTrue ( "contains edge" , mockOneNode.getEdgesWith(mockTwoNode).contains(mockEdge) );
	}

	@Test
	public final void testGetGraph() {
		assertEquals ( "graph" , mockGraph , testNode.getGraph()) ;
	}

	@Test
	public final void testGetIndex() {
		assertEquals ( "index" , EXPECTED_NODE2_IDX , mockTwoNode.getIndex()) ;
	}

	@Test
	public final void testHasEdgeWith() {
		assertTrue ( mockOneNode.hasEdgeWith(mockTwoNode) );
	}

	@Test
	public final void testAddEdge() {  
		testNode.addEdge(mockEdgeTwo) ;
		
		Edge edgeArray [] = {mockEdge , mockEdgeTwo} ;
		
		Iterator<Edge> edgeIterator = testNode.edgeIterator() ;
		
		int counter = 0 ;
		
		while ( edgeIterator.hasNext() )
		{
			assertEquals ( "egde iterator item" , edgeArray[counter] , edgeIterator.next() ) ;
			counter ++ ;
		}
	}

	@Test
	public final void testCompareTo() {
		assertEquals ( "same Node" , COMPARE_SAME_INDEX , mockOneNode.compareTo(mockOneNode) ) ;
		assertEquals ( "greater index" , COMPARE_GREATER_INDEX , mockTwoNode.compareTo(mockOneNode) ) ;
		assertEquals ( "lesser index" , COMPARE_LESSER_INDEX , mockOneNode.compareTo(mockTwoNode) ) ;
	}

	@Test
	public final void testEqualsObject() {
		assertTrue ( "same" , testNode.equals(testNode)) ;
		assertFalse ( "not same" , testNode.equals(mockTwoNode)) ;
	}

	@Test
	public final void testIsRemoved() {
		assertFalse ( "not removed" , testNode.isRemoved() ) ;
	}

	@Test
	public final void testMarkRemoved() {
		testNode.markRemoved(true) ;
		assertTrue ( "removed" , testNode.isRemoved()) ;
	}

}
