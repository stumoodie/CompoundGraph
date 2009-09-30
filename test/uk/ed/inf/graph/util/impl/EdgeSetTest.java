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
package uk.ed.inf.graph.util.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ed.inf.graph.impl.Edge;
import uk.ed.inf.graph.impl.EdgeFactory;
import uk.ed.inf.graph.impl.Graph;
import uk.ed.inf.graph.impl.Node;

public class EdgeSetTest {
	
	private EdgeSet<Node, Edge> testEdgeSet ;
	
	private Graph aGraph ;
	private EdgeFactory edgeFactory ;
	
	private Node aNode ;
	
	private Edge anEdge ;
	
	private static final int NUMERIC [] = {0,1,2,3,4,5} ;
	
	@Before
	public void setUp() throws Exception {
		
		aGraph = new Graph () ;
		
		aNode = aGraph.nodeFactory().createNode() ;
		
		edgeFactory = aGraph.edgeFactory() ;
		edgeFactory.setPair(aNode, aNode ) ;
		
		testEdgeSet = new EdgeSet<Node, Edge> () ;
		
		anEdge = edgeFactory.createEdge() ;
		
		testEdgeSet.add( anEdge) ;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testEdgeSet() {
		assertNotNull ( "created" , testEdgeSet) ;
		assertFalse ( "is not empty" , testEdgeSet.isEmpty()) ;
		assertTrue ( "has anEdge in" , testEdgeSet.contains(anEdge)) ;
	}

	@Test
	public final void testIterator() {
		Edge [] edgeArray = { anEdge } ;
		
		Iterator <Edge> edgeIterator = testEdgeSet.iterator() ;
		
		int counter =  0 ;
		
		while ( edgeIterator.hasNext())
		{
			assertEquals ( "same edge" , edgeArray[counter] , edgeIterator.next() ) ;
			counter ++ ;
		}
		
		assertEquals ( "one edge" , NUMERIC[1] , counter) ;
	}

	@Test
	public final void testGetEdgesWith() {
		SortedSet<Edge> egdeSortedSet = testEdgeSet.getEdgesWith(aNode, aNode) ;
		
		assertEquals ( "one edge" , NUMERIC[1] , egdeSortedSet.size() ) ;
		assertTrue ( "contains anEdge" , egdeSortedSet.contains(anEdge)) ;
	}

	@Test
	public final void testHasEdgesWith() {
		assertTrue ( "has with aNode" , testEdgeSet.hasEdgesWith(aNode, aNode)) ;
	}

	@Test
	public final void testContainsInt() {
		assertTrue ( "has in this index" , testEdgeSet.contains(0)) ;
	}

	@Test
	public final void testContainsE() {
		assertTrue ( "has this edge" , testEdgeSet.contains(anEdge )) ;
	}

	@Test
	public final void testContainsNN() {
		assertTrue ( "has edge of aNode" , testEdgeSet.contains(aNode, aNode) );
	}

	@Test
	public final void testGetNN() {
		SortedSet<Edge> result = testEdgeSet.get(aNode, aNode) ;
		
		assertEquals ( "oneEdge" ,NUMERIC[1] , result.size()) ;
		assertTrue ( "contains anEdge" , result.contains(anEdge) ) ;
	}

	@Test
	public final void testGetInt() {
		assertEquals ( "same edge" , anEdge , testEdgeSet.get(0)) ;
	}

	@Test
	public final void testRemoveE() {
		testEdgeSet.remove(anEdge) ;
		assertTrue ("is empty" , testEdgeSet.isEmpty()) ;
	}

	@Test
	public final void testAdd() {
		Edge anotherEdge = edgeFactory.createEdge() ;
 		testEdgeSet.add(anotherEdge) ;
 		assertEquals ( "two Edges" , NUMERIC[2] , testEdgeSet.size()) ;
	}

	@Test
	public final void testSize() {
		assertEquals ( "one edge" , NUMERIC[1] , testEdgeSet.size() ) ;
	}

	@Test
	public final void testAddAll() {
		List<Edge> edgeList = new ArrayList<Edge> () ;
		
		edgeList.add(edgeFactory.createEdge()) ;
		edgeList.add(edgeFactory.createEdge()) ;
		
		testEdgeSet.addAll(edgeList) ;
		
		assertEquals ( "three edges" , NUMERIC[3] , testEdgeSet.size()) ;
		assertTrue ("contains from edgeList" , testEdgeSet.containsAll(edgeList)) ;
	}

	@Test
	public final void testClear() {
		testEdgeSet.clear() ;
		assertTrue ( "empty" , testEdgeSet.isEmpty() ) ;
	}

	@Test
	public final void testContainsObject() {
		assertTrue ( "contains anEdge" , testEdgeSet.contains(anEdge)) ;
	}

	@Test
	public final void testContainsAll() {
		Edge anotherEdge = edgeFactory.createEdge() ;
		
		testEdgeSet.add(anotherEdge ) ;
		
		List<Edge> allEdges = new ArrayList <Edge> () ; 
		allEdges.add(anotherEdge) ;
		allEdges.add(anEdge) ;
		
		assertTrue ( "has all in" , testEdgeSet.containsAll(allEdges) );
		
	}

	@Test
	public final void testIsEmpty() {
		assertFalse ( "not empty" , testEdgeSet.isEmpty() ) ;
	}

	@Test
	public final void testRemoveObject() {
		testEdgeSet.remove(anEdge) ;
		assertTrue ( "is empty" , testEdgeSet.isEmpty() ) ;
	}

	@Test
	public final void testRemoveAll() {
		Edge anotherEdge = edgeFactory.createEdge() ;
		
		testEdgeSet.add(anotherEdge ) ;
		
		List<Edge> allEdges = new ArrayList <Edge> () ; 
		allEdges.add(anotherEdge) ;
		allEdges.add(anEdge) ;
		
		testEdgeSet.removeAll(allEdges) ;
		assertTrue ( "is empty" , testEdgeSet.isEmpty()) ;
	}

	@Test
	public final void testRetainAll() {
		Edge anotherEdge = edgeFactory.createEdge() ;
		
		testEdgeSet.add(anotherEdge ) ;
		
		List<Edge> edgeList = new ArrayList <Edge> () ; 
		edgeList.add(anotherEdge) ;
		
		testEdgeSet.retainAll(edgeList) ;
		
		assertEquals ( "one edge" , NUMERIC[1] , testEdgeSet.size()) ;
		assertTrue ( "anotherEdge" , testEdgeSet.contains(anotherEdge)) ;
	}

	@Test
	public final void testToArray() {
		Object [] edgeArray = testEdgeSet.toArray() ;
		
		assertEquals ( "contains one" , NUMERIC[1] , Array.getLength(edgeArray)) ;
		assertEquals ( "contained is AnEdge" , anEdge , edgeArray[0]) ;
	}

	@Test
	public final void testToArrayTArray() {
		Edge [] edgeArray = new Edge[0] ;
		edgeArray = testEdgeSet.toArray(edgeArray) ;
		
		assertEquals ( "contains one" , NUMERIC[1] , Array.getLength(edgeArray)) ;
		assertEquals ( "contained is AnEdge" , anEdge , edgeArray[0]) ;
	}

}
