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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ed.inf.graph.basic.IBasicEdge;
import uk.ed.inf.graph.basic.IBasicNode;

@RunWith(JMock.class)
public class NodeSetTest {
	
	private Mockery mockery = new JUnit4Mockery() {{
		setImposteriser(ClassImposteriser.INSTANCE);
	}};
	
	private NodeSet<TestNode, TestEdge> testNodeSet ;
	
	private TestNode mockBasicNode2 ;
	
	private Set<TestNode> nodeCollection = new HashSet<TestNode> () ;
	
	private static final int[] NUMERIC = {0,1,2,3,4,5} ;

	@Before
	public void setUp() throws Exception {
		
		testNodeSet = new NodeSet<TestNode, TestEdge>() ;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Ignore @Test
	public final void testNodeSet() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testAdd() {
		final TestNode mockBasicNode ;
		mockBasicNode = mockery.mock(TestNode.class ,"mockBasicNode") ;
		testNodeSet.add(mockBasicNode) ;
		assertEquals ( "added node" , NUMERIC[1] , testNodeSet.size() ) ;
	}

	@Ignore @Test
	public final void testAddAll() {
		final TestNode mockBasicNode ;
		mockBasicNode = mockery.mock(TestNode.class ,"mockBasicNode") ;
		mockBasicNode2 = mockery.mock(TestNode.class ,"mockBasicNode2") ;
		
		this.mockery.checking(new Expectations(){{
			atLeast(1).of(mockBasicNode).compareTo(mockBasicNode2) ; returnValue(-1) ;
			atLeast(1).of(mockBasicNode2).compareTo(mockBasicNode) ; returnValue(1) ;
		}});
		
		nodeCollection.add(mockBasicNode) ;
		nodeCollection.add(mockBasicNode2) ;
		
		testNodeSet.addAll(nodeCollection) ;
		assertEquals ( "added node" , NUMERIC[2] , testNodeSet.size() ) ;
	}

	@Test
	public final void testClear() {
		final TestNode mockBasicNode ;
		mockBasicNode = mockery.mock(TestNode.class ,"mockBasicNode") ;
		testNodeSet.add(mockBasicNode) ;
		testNodeSet.clear() ;
		assertEquals ( "cleared" , NUMERIC[0] , testNodeSet.size() ) ;
	}

	@Ignore @Test
	public final void testComparator() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testContainsInt() {
		final TestNode mockBasicNode ;
		mockBasicNode = mockery.mock(TestNode.class ,"mockBasicNode") ;
		testNodeSet.add(mockBasicNode) ;
		
		this.mockery.checking(new Expectations(){{
			atLeast(1).of(mockBasicNode).getIndex() ; returnValue(0) ;
		}});
		
		assertTrue ( "contains at 0" , testNodeSet.contains(0) ) ;
	}

	@Ignore @Test
	public final void testContainsObject() {
		final TestNode mockBasicNode ;
		mockBasicNode = mockery.mock(TestNode.class ,"mockBasicNode") ;
		testNodeSet.add(mockBasicNode) ;
		
		this.mockery.checking(new Expectations(){{
			atLeast(1).of(mockBasicNode).compareTo(mockBasicNode) ; returnValue(0) ;
		}});
		
		assertTrue ( "contains object" , testNodeSet.contains(mockBasicNode)) ;
	}

	@Test
	public final void testGet() {
		final TestNode mockBasicNode ;
		mockBasicNode = mockery.mock(TestNode.class ,"mockBasicNode") ;
		testNodeSet.add(mockBasicNode) ;
		
		this.mockery.checking(new Expectations(){{
			atLeast(1).of(mockBasicNode).getIndex() ; returnValue(0) ;
		}});
		
		assertEquals ( "same with modkBasicNode" , mockBasicNode , testNodeSet.get(0) ) ;
	}

	@Ignore @Test
	public final void testContainsAll() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testIsEmpty() {
		assertTrue ( "is Empty" , testNodeSet.isEmpty()) ;
	}

	@Test
	public final void testIterator() {
		final TestNode mockBasicNode ;
		
		mockBasicNode = mockery.mock(TestNode.class ,"mockBasicNode") ;
		testNodeSet.add(mockBasicNode) ;
		
		TestNode [] nodeArray = { mockBasicNode } ;
		
		Iterator<TestNode> nodeIterator = testNodeSet.iterator() ;
		
		int counter = 0 ;
		
		while(nodeIterator.hasNext())
		{
			assertEquals("sameNode" , nodeArray[counter], nodeIterator.next()) ;
			counter++ ;
		}
		
		assertEquals( "same number" , NUMERIC[1] , counter) ;
		
	}

	@Ignore @Test
	public final void testRemove() {
		final TestNode mockBasicNode ;
		mockBasicNode = mockery.mock(TestNode.class ,"mockBasicNode") ;
		this.mockery.checking(new Expectations(){{
			atLeast(1).of(mockBasicNode).compareTo(mockBasicNode) ; returnValue(0) ;
		}});
		
		testNodeSet.add(mockBasicNode) ;
		
		assertFalse ( "not empty" , testNodeSet.isEmpty()) ;
		
		testNodeSet.remove(mockBasicNode) ;
		
		assertTrue ( "empty" , testNodeSet.isEmpty()) ;
	}

	@Ignore @Test
	public final void testRemoveAll() {
		fail("Not yet implemented"); // TODO
	}

	@Ignore @Test
	public final void testRetainAll() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSize() {
		final TestNode mockBasicNode ;
		mockBasicNode = mockery.mock(TestNode.class ,"mockBasicNode") ;
		testNodeSet.add(mockBasicNode) ;
		
		assertEquals ( "size is 1 " , NUMERIC[1] , testNodeSet.size() );
	}

	@Test
	public final void testToArray() {
		final TestNode mockBasicNode ;
		mockBasicNode = mockery.mock(TestNode.class ,"mockBasicNode") ;
		
		testNodeSet.add(mockBasicNode) ;
		
		Object[] nodeArray = testNodeSet.toArray() ;
		
		assertEquals ( "length" , NUMERIC[1] , Array.getLength(nodeArray) ) ;
		assertTrue ( "is TestNode" , nodeArray[0] instanceof TestNode) ;
		assertEquals ( "mockNode" , mockBasicNode , nodeArray[0]) ;
	}

	@Test
	public final void testToArrayTArray() {
		final TestNode mockBasicNode ;
		mockBasicNode = mockery.mock(TestNode.class ,"mockBasicNode") ;
		
		testNodeSet.add(mockBasicNode) ;
		
		TestNode nodeArray [] = new TestNode [0] ;
		
		assertEquals ( "array empty" , NUMERIC[0] , Array.getLength(nodeArray)) ;
		
		nodeArray = testNodeSet.toArray(nodeArray ) ;
		
		assertEquals ( "length" , NUMERIC[1] , Array.getLength(nodeArray) ) ;
		assertTrue ( "is TestNode" , nodeArray[0] instanceof TestNode) ;
		assertEquals ( "mockNode" , mockBasicNode , nodeArray[0]) ;
		
	}
	
	private static abstract class TestNode implements IBasicNode<TestNode, TestEdge> { }
	
	private static abstract class TestEdge implements IBasicEdge<TestNode, TestEdge> {}

}
