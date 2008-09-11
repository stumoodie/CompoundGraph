package uk.ed.inf.graph.impl;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EdgeFactoryTest {
	
	private EdgeFactory testEdgeFactory ;
	private static Graph mockGraph ;
	
	private Node oneNode ;
	private Node twoNode ;
	
	private static final int [] INDEX = { 1 , 2 , 3 , 4 , 5 } ;
	private static final int [] NUMERIC = {0,1,2,3,4,5} ;

	@Before
	public void setUp() throws Exception {
		
		mockGraph = new Graph () ;
		testEdgeFactory = new EdgeFactory (mockGraph) ;
		
		oneNode = new Node (mockGraph , INDEX[0]) ;
		twoNode = new Node (mockGraph , INDEX[1]) ;
		
		testEdgeFactory.setPair(oneNode, twoNode) ;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testSetPair() {
		NodePair anEdgePair = testEdgeFactory.createEdge().getConnectedNodes() ;
		assertEquals ( "oneNode" , oneNode , anEdgePair.getOneNode()) ;
		assertEquals ( "oneNode" , twoNode , anEdgePair.getTwoNode()) ;
	}

	@Test
	public final void testCreateEdge() {
		Edge createdEdge = testEdgeFactory.createEdge() ;
		assertEquals ( "same graph" , mockGraph , createdEdge.getGraph()) ;
	}

	@Test
	public final void testGetGraph() {
		assertEquals ( "get graph" , mockGraph , testEdgeFactory.getGraph()) ;
	}

}
