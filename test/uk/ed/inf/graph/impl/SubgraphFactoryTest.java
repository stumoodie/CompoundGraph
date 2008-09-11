package uk.ed.inf.graph.impl;

import static org.junit.Assert.*;

import java.util.Iterator;

import javax.swing.text.EditorKit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SubgraphFactoryTest {
	
	private Graph graph;
	
	private SubgraphFactory testSubGraphFactory;
	
	private Node aNode ;
	
	private Edge anEdge ;
	
	private static final int NUMMERIC [] = {0,1,2,3,4,5} ;

	@Before
	public void setUp() throws Exception {
		graph = new Graph () ;
		testSubGraphFactory = graph.subgraphFactory() ;
		
		aNode = graph.nodeFactory().createNode() ;
		
		graph.edgeFactory().setPair(aNode, aNode) ;
		anEdge = graph.edgeFactory().createEdge() ;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testSubgraphFactory() {
		assertTrue ( "factory exists" , testSubGraphFactory!= null ) ;
		assertTrue ( "same instance" , testSubGraphFactory == graph.subgraphFactory()) ;
	}

	@Test
	public final void testAddNode() {
		assertFalse ( "nodes empty" , testSubGraphFactory.nodeIterator().hasNext()) ;
		graph.subgraphFactory().addNode(aNode) ;
		assertTrue ( "node added" , testSubGraphFactory.nodeIterator().hasNext()) ;
	}

	@Test
	public final void testAddEdge() {
		assertFalse ( "edges empty" , testSubGraphFactory.edgeIterator().hasNext()) ;
		graph.subgraphFactory().addEdge(anEdge) ;
		assertTrue ( "edge added" , testSubGraphFactory.edgeIterator().hasNext()) ;
	}

	@Test
	public final void testCreateSubgraph() {
		Subgraph aSubgraph = testSubGraphFactory.createSubgraph() ;
		graph.subgraphFactory().addEdge(anEdge) ;
		graph.subgraphFactory().addNode(aNode) ;
		
		assertTrue ( "subgraph exists" , aSubgraph != null ) ;
		assertEquals ( "subgraph have correct parent" , graph , aSubgraph.getSuperGraph() ) ;
		
		Node [] nodeArray = {aNode } ;
		Iterator<Node> nodeIterator = testSubGraphFactory.nodeIterator() ;
		int counter = 0 ;
		
		while ( nodeIterator.hasNext()) 
		{
			assertEquals ( "same node" , nodeArray[counter] , nodeIterator.next() );
			counter ++ ;
		}
		
		assertEquals ( "one node" , NUMMERIC[1] , counter) ;
		
		Edge [] edgeArray = {anEdge } ;
		Iterator<Edge> edgeIterator = testSubGraphFactory.edgeIterator() ;
		counter = 0 ;
		
		while ( edgeIterator.hasNext()) 
		{
			assertEquals ( "same edge" , edgeArray[counter] , edgeIterator.next() );
			counter ++ ;
		}
		
		assertEquals ( "one edge" , NUMMERIC[1] , counter) ;
	}

	@Test
	public final void testCreateInducedSubgraph() {
		Subgraph aSubgraph = testSubGraphFactory.createInducedSubgraph() ;
		graph.subgraphFactory().addEdge(anEdge) ;
		graph.subgraphFactory().addNode(aNode) ;
		
		assertTrue ( "subgraph exists" , aSubgraph != null ) ;
		assertEquals ( "subgraph have correct parent" , graph , aSubgraph.getSuperGraph() ) ;
		
		Node [] nodeArray = {aNode } ;
		Iterator<Node> nodeIterator = testSubGraphFactory.nodeIterator() ;
		int counter = 0 ;
		
		while ( nodeIterator.hasNext()) 
		{
			assertEquals ( "same node" , nodeArray[counter] , nodeIterator.next() );
			counter ++ ;
		}
		
		assertEquals ( "one node" , NUMMERIC[1] , counter) ;
		
		Edge [] edgeArray = {anEdge } ;
		Iterator<Edge> edgeIterator = testSubGraphFactory.edgeIterator() ;
		counter = 0 ;
		
		while ( edgeIterator.hasNext()) 
		{
			assertEquals ( "same edge" , edgeArray[counter] , edgeIterator.next() );
			counter ++ ;
		}
		
		assertEquals ( "one edge" , NUMMERIC[1] , counter) ;
	}

	@Test
	public final void testEdgeIterator() {
		testSubGraphFactory.addEdge(anEdge) ;
		Edge [] edgeArray = {anEdge } ;
		Iterator<Edge> edgeIterator = testSubGraphFactory.edgeIterator() ;
		int counter = 0 ;
		
		while ( edgeIterator.hasNext()) 
		{
			assertEquals ( "same edge" , edgeArray[counter] , edgeIterator.next() );
			counter ++ ;
		}
		
		assertEquals ( "one edge" , NUMMERIC[1] , counter) ;
	}

	@Test
	public final void testNodeIterator() {
		testSubGraphFactory.addNode(aNode) ;
 		Node [] nodeArray = {aNode } ;
		Iterator<Node> nodeIterator = testSubGraphFactory.nodeIterator() ;
		int counter = 0 ;
		
		while ( nodeIterator.hasNext()) 
		{
			assertEquals ( "same node" , nodeArray[counter] , nodeIterator.next() );
			counter ++ ;
		}
		
		assertEquals ( "one node" , NUMMERIC[1] , counter) ;
	}

}
