package uk.ed.inf.graph.impl;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import sun.nio.cs.Surrogate;
import uk.ed.inf.graph.basic.IBasicPair;

@RunWith(JMock.class)
public class SubgraphTest {
	
	private Mockery mockery = new JUnit4Mockery() {{
		setImposteriser(ClassImposteriser.INSTANCE);
	}};
	
	private Subgraph testSubGraph ;
	
	private Graph superGraph ;
	
	private Node aNode ;
	private Edge anEdge ;
	
	private SubgraphFactory subgraphFactory ;
	private EdgeFactory edgeFactory ;
	private NodeFactory nodeFactory ;
	
	private final static int NUMERIC [] = {0,1,2,3,4,5} ; 

	@Before
	public void setUp() throws Exception {
		superGraph = new Graph () ;
		subgraphFactory = superGraph.subgraphFactory() ;
		edgeFactory = superGraph.edgeFactory() ;
		nodeFactory = superGraph.nodeFactory() ;
		
		testSubGraph = subgraphFactory.createSubgraph() ;
		
		aNode = nodeFactory.createNode() ;
		
		testSubGraph.addNode(aNode) ;
		
		edgeFactory.setPair(aNode, aNode) ;
		anEdge = edgeFactory.createEdge() ;
		testSubGraph.addDanglingEdge(anEdge) ;
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testSubgraph() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testIsInducedSubgraph() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetSuperGraph() {
		assertEquals ( "same supergraph" , superGraph , testSubGraph.getSuperGraph()) ;
	}

	@Test
	public final void testContainsNodeNode() {
		assertTrue ( "has node in" , testSubGraph.containsNode(aNode)) ;
	}
	
	
	@Test
	public final void testContainsConnectionNodeNode() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testContainsEdgeEdge() {
		assertTrue ( "contains edge" , testSubGraph.containsEdge(anEdge)) ;
	}

	@Test
	public final void testBasicEdgeFactory() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testBasicNodeFactory() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testBasicSubgraphFactory() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testEdgeIterator() {
		Edge [] edgeArray = { anEdge } ;
		
		Iterator<Edge> edgeIterator = testSubGraph.edgeIterator() ;
		int counter = 0 ;
		
		while ( edgeIterator.hasNext())
		{
			assertEquals ( "same object" , edgeArray[counter] , edgeIterator.next()) ;
			counter ++ ;
		}
		
		assertEquals ( "one edge" , NUMERIC[1]  , counter ) ;
	}

	@Test
	public final void testNodeIterator() {
		Node [] nodeArray = { aNode } ;
		
		Iterator<Node> nodeIterator = testSubGraph.nodeIterator() ;
		int counter = 0 ;
		
		while ( nodeIterator.hasNext())
		{
			assertEquals ( "same object" , nodeArray[counter] , nodeIterator.next()) ;
			counter ++ ;
		}
		
		assertEquals ( "one edge" , NUMERIC[1]  , counter ) ;
	}

	@Test
	public final void testGetNumEdges() {
		assertEquals ( "one edge" , NUMERIC[1] , testSubGraph.getNumEdges() ) ;
	}

	@Test
	public final void testGetNumNodes() {
		assertEquals ( "one node" , NUMERIC[1] , testSubGraph.getNumNodes() ) ;
	}

	@Test
	public final void testRemoveSubgraph() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testAddNode() {
		Node anotherNode = nodeFactory.createNode() ;
		
		testSubGraph.addNode(anotherNode) ;
		
		assertEquals ( "two nodes" , NUMERIC[2] , testSubGraph.getNumNodes()) ;
	}

	@Test
	public final void testAddConnectedEdge() {
		
		edgeFactory.setPair(nodeFactory.createNode(),nodeFactory.createNode()) ;
		
		Edge anotherEdge = edgeFactory.createEdge() ;
		
		testSubGraph.addConnectedEdge(anotherEdge) ;
		
		assertEquals ( "two edges" , NUMERIC[2] , testSubGraph.getNumEdges()) ;
	}

	@Test
	public final void testAddDanglingEdge() {
		Edge anotherEdge = edgeFactory.createEdge() ;
		
		testSubGraph.addDanglingEdge(anotherEdge) ;
		
		assertEquals ( "two edges" , NUMERIC[2] , testSubGraph.getNumEdges()) ;
	}

	@Test
	public final void testContainsEdgeInt() {
		assertTrue ( "contains edge" , testSubGraph.containsEdge(0)) ;
	}

	@Test
	public final void testContainsNodeInt() {
		assertTrue ( "contains node" , testSubGraph.containsNode(0)) ;
	}

	@Test
	public final void testGetEdge() {
		assertEquals ( "get edge" , anEdge , testSubGraph.getEdge(0)) ;
	}

	@Test
	public final void testGetNode() {
		assertEquals ( "get node" , aNode , testSubGraph.getNode(0)) ;
	}

	@Test
	public final void testIsConsistentSnapShot() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testContainsConnectionIBasicPairOfNodeEdge() {
		fail("Not yet implemented"); // TODO
	}

}
