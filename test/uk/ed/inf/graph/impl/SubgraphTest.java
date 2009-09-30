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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class SubgraphTest {
	

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

	@Ignore @Test
	public final void testSubgraph() {
		fail("Not yet implemented"); // TODO
	}

	@Ignore @Test
	public final void testIsInducedSubgraph() {
		fail("Not yet implemented"); // TODO
	}

	@Ignore @Test
	public final void testGetSuperGraph() {
		assertEquals ( "same supergraph" , superGraph , testSubGraph.getSuperGraph()) ;
	}

	@Ignore @Test
	public final void testContainsNodeNode() {
		assertTrue ( "has node in" , testSubGraph.containsNode(aNode)) ;
	}
	
	
	@Ignore @Test
	public final void testContainsConnectionNodeNode() {
		fail("Not yet implemented"); // TODO
	}

	@Ignore @Test
	public final void testContainsEdgeEdge() {
		assertTrue ( "contains edge" , testSubGraph.containsEdge(anEdge)) ;
	}

	@Ignore @Test
	public final void testBasicEdgeFactory() {
		fail("Not yet implemented"); // TODO
	}

	@Ignore @Test
	public final void testBasicNodeFactory() {
		fail("Not yet implemented"); // TODO
	}

	@Ignore @Test
	public final void testBasicSubgraphFactory() {
		fail("Not yet implemented"); // TODO
	}

	@Ignore @Test
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

	@Ignore @Test
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

	@Ignore @Test
	public final void testGetNumEdges() {
		assertEquals ( "one edge" , NUMERIC[1] , testSubGraph.getNumEdges() ) ;
	}

	@Ignore @Test
	public final void testGetNumNodes() {
		assertEquals ( "one node" , NUMERIC[1] , testSubGraph.getNumNodes() ) ;
	}

	@Ignore @Test
	public final void testRemoveSubgraph() {
		fail("Not yet implemented"); // TODO
	}

	@Ignore @Test
	public final void testAddNode() {
		Node anotherNode = nodeFactory.createNode() ;
		
		testSubGraph.addNode(anotherNode) ;
		
		assertEquals ( "two nodes" , NUMERIC[2] , testSubGraph.getNumNodes()) ;
	}

	@Ignore @Test
	public final void testAddConnectedEdge() {
		
		edgeFactory.setPair(nodeFactory.createNode(),nodeFactory.createNode()) ;
		
		Edge anotherEdge = edgeFactory.createEdge() ;
		
		testSubGraph.addConnectedEdge(anotherEdge) ;
		
		assertEquals ( "two edges" , NUMERIC[2] , testSubGraph.getNumEdges()) ;
	}

	@Ignore @Test
	public final void testAddDanglingEdge() {
		Edge anotherEdge = edgeFactory.createEdge() ;
		
		testSubGraph.addDanglingEdge(anotherEdge) ;
		
		assertEquals ( "two edges" , NUMERIC[2] , testSubGraph.getNumEdges()) ;
	}

	@Ignore @Test
	public final void testContainsEdgeInt() {
		assertTrue ( "contains edge" , testSubGraph.containsEdge(0)) ;
	}

	@Ignore @Test
	public final void testContainsNodeInt() {
		assertTrue ( "contains node" , testSubGraph.containsNode(0)) ;
	}

	@Ignore @Test
	public final void testGetEdge() {
		assertEquals ( "get edge" , anEdge , testSubGraph.getEdge(0)) ;
	}

	@Ignore @Test
	public final void testGetNode() {
		assertEquals ( "get node" , aNode , testSubGraph.getNode(0)) ;
	}

	@Ignore @Test
	public final void testIsConsistentSnapShot() {
		fail("Not yet implemented"); // TODO
	}

	@Ignore @Test
	public final void testContainsConnectionIBasicPairOfNodeEdge() {
		fail("Not yet implemented"); // TODO
	}

}
