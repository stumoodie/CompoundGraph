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

public class SubgraphFactoryTest {
	
	private Graph graph;
	
	private SubgraphFactory testSubGraphFactory;
	
	private NodeFactory nodeFactory ;
	private EdgeFactory edgeFactory ;
	
	private Node aNode ;
	private Edge anEdge ;
	
	private static final int NUMMERIC [] = {0,1,2,3,4,5} ;

	@Before
	public void setUp() throws Exception {
		graph = new Graph () ;
		testSubGraphFactory = graph.subgraphFactory() ;
		nodeFactory = graph.nodeFactory() ;
		edgeFactory = graph.edgeFactory() ;
		
		aNode = nodeFactory.createNode() ;
		
		edgeFactory.setPair(aNode, aNode) ;
		anEdge = edgeFactory.createEdge() ;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testSubgraphFactory() {
		assertTrue ( "factory exists" , testSubGraphFactory!= null ) ;
		assertTrue ( "same instance" , testSubGraphFactory != graph.subgraphFactory()) ;
	}

	@Test
	public final void testAddNode() {
		assertFalse ( "nodes empty" , testSubGraphFactory.nodeIterator().hasNext()) ;
		testSubGraphFactory.addNode(aNode) ;
		assertTrue ( "node added" , testSubGraphFactory.nodeIterator().hasNext()) ;
	}

	@Test
	public final void testAddEdge() {
		assertFalse ( "edges empty" , testSubGraphFactory.edgeIterator().hasNext()) ;
		testSubGraphFactory.addEdge(anEdge) ;
		assertTrue ( "edge added" , testSubGraphFactory.edgeIterator().hasNext()) ;
	}

	@Test
	public final void testCreateSubgraph() {
		Subgraph aSubgraph = testSubGraphFactory.createSubgraph() ;
		testSubGraphFactory.addEdge(anEdge) ;
		testSubGraphFactory.addNode(aNode) ;
		
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
		testSubGraphFactory.addEdge(anEdge) ;
		testSubGraphFactory.addNode(aNode) ;
		
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
