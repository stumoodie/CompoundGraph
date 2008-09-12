package uk.ed.inf.graph.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ed.inf.graph.util.impl.ConnectedNodeIterator;

public class NodeTest {
	
	private Node testNode ;
	private Node otherNode ;
	
	private static Graph mockGraph ;
	private static Edge mockEdge ;
	private static Edge mockEdgeTwo ;
	private static Node mockOneNode ;
	private static Node mockTwoNode ;
	
	private static NodeFactory nodeFactory ;
	private static EdgeFactory edgeFactory ;
	
	private static final int NODE_INDEX_ONE = 1 ;
	private static final int NODE_INDEX_TWO = 2 ;
	private static final int NODE_INDEX_THREE = 3 ;
	private static final int EDGE_INDEX_ONE = 1 ;
	private static final int EDGE_INDEX_TWO = 2 ;
	
	private static final int COMPARE_GREATER_INDEX = 1 ;
	private static final int COMPARE_LESSER_INDEX = -1 ;
	private static final int COMPARE_SAME_INDEX = 0 ;
	
	private static final int NUMERIC_VALUE_ONE = 1 ;
	
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
		
		testNode = nodeFactory.createNode() ;
		otherNode = nodeFactory.createNode() ;
		
		mockOneNode.addEdge(mockEdge) ;
		testNode.addEdge(mockEdge) ;
		
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
		assertEquals ( "degree" ,NUMERIC_VALUE_ONE , testNode.getDegree() );
	}

	@Test
	public final void testEdgeIterator() {
		Edge edgeArray [] = {mockEdge} ;
		
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
		assertEquals ( "get Edges" , NUMERIC_VALUE_ONE , testNode.getEdgesWith(mockOneNode ).size() );
		assertTrue ( "contains edge" , testNode.getEdgesWith(mockOneNode).contains(mockEdge) );
	}

	@Test
	public final void testGetGraph() {
		assertEquals ( "graph" , mockGraph , testNode.getGraph()) ;
	}

	@Test
	public final void testGetIndex() {
		assertEquals ( "index" , NODE_INDEX_TWO , testNode.getIndex()) ;
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
		assertEquals ( "same Node" , COMPARE_SAME_INDEX , testNode.compareTo(testNode) ) ;
		assertEquals ( "greater index" , COMPARE_GREATER_INDEX , otherNode.compareTo(testNode) ) ;
		assertEquals ( "lesser index" , COMPARE_LESSER_INDEX , testNode.compareTo(otherNode) ) ;
	}

	@Test
	public final void testEqualsObject() {
		assertTrue ( "same" , testNode.equals(testNode)) ;
		assertFalse ( "not same" , testNode.equals(otherNode)) ;
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

	@Test
	public final void testToString() {
		assertEquals ( "test node" , "[uk.ed.inf.graph.impl.Node: index=2, removed=false]",  testNode.toString()) ;
		assertNotSame ( "other node" , "[uk.ed.inf.graph.impl.Node: index=3, removed=false]",  testNode.toString()) ;
	}

}
