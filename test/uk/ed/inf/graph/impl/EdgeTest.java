package uk.ed.inf.graph.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ed.inf.graph.basic.IBasicPair;

public class EdgeTest {

	
	private Edge testEdge ;
	private Edge otherEdge ;
	private static Graph mockGraph ;
	private static Node mockOneNode ;
	private static Node mockTwoNode ;
	
	private static IBasicPair<Node, Edge> testNodePair ; 
	
	private static final int NODE_INDEX_ONE = 1 ;
	private static final int NODE_INDEX_TWO = 2 ;
	private static final int EDGE_INDEX_ONE = 1 ;
	private static final int EDGE_INDEX_TWO = 2 ;
	
	private static final int COMPARE_GREATER_INDEX = 1 ;
	private static final int COMPARE_LESSER_INDEX = -1 ;
	private static final int COMPARE_SAME_INDEX = 0 ;
	
	private static final String TO_STRING_TESTEDGE = "[uk.ed.inf.graph.impl.Edge: index=1, removed=false]" ;
	private static final String TO_STRING_OTHEREDGE = "[uk.ed.inf.graph.impl.Edge: index=2, removed=false]" ;
	
	

	@Before
	public void setUp() throws Exception {
		
		mockGraph = new Graph () ;
		mockOneNode = new Node ( mockGraph , NODE_INDEX_ONE ) ;
		mockTwoNode = new Node ( mockGraph , NODE_INDEX_TWO ) ;
		
		testNodePair = new NodePair ( mockOneNode , mockTwoNode) ;
		
		testEdge = new Edge (mockGraph, EDGE_INDEX_ONE, mockOneNode, mockTwoNode) ;
		otherEdge = new Edge ( mockGraph , EDGE_INDEX_TWO , mockOneNode , mockTwoNode ) ;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testHashCode() {
		assertEquals ( "hash code" , testEdge.hashCode() , testEdge.hashCode() ) ;
	}

	@Test
	public final void testGetConnectedNodes() {
		assertEquals ( "node one " , testNodePair , testEdge.getConnectedNodes()) ;
	}

	@Test
	public final void testGetGraph() {
		assertEquals ( "graph " , mockGraph , testEdge.getGraph()) ;
	}

	@Test
	public final void testGetIndex() {
		assertEquals ( "index" , EDGE_INDEX_ONE , testEdge.getIndex() );
	}

	@Test
	public final void testIsSelfEdge() {
		assertFalse ( "no self edge" , testEdge.isSelfEdge() );
	}

	@Test
	public final void testCompareTo() {
		assertEquals ("compare to self " , COMPARE_SAME_INDEX ,testEdge.compareTo(testEdge)) ;
		assertEquals ("compare to other greater " , COMPARE_GREATER_INDEX ,otherEdge.compareTo(testEdge)) ;
		assertEquals ("compare to other lesser " , COMPARE_LESSER_INDEX ,testEdge.compareTo(otherEdge)) ;
	}

	@Test
	public final void testEqualsObject() {
		assertTrue ( "equals self" , testEdge.equals(testEdge ) );
		assertFalse ( "not Equals other" , testEdge.equals(otherEdge)) ;
	}

	@Test
	public final void testIsRemoved() {
		assertFalse ( "not removed" , testEdge.isRemoved() );
	}

	@Test
	public final void testMarkRemoved() {
		testEdge.markRemoved(true) ;
		assertTrue ( "removed" , testEdge.isRemoved() );
	}

	@Test
	public final void testHasEnds() {
		assertTrue ( "has Ends" , testEdge.hasEnds(testNodePair ) );
	}

	@Test
	public final void testToString() {
		assertEquals ("same edge" , TO_STRING_TESTEDGE , testEdge.toString() );
		assertNotSame("not same edge" , TO_STRING_OTHEREDGE , testEdge.toString() );
		
	}

}
