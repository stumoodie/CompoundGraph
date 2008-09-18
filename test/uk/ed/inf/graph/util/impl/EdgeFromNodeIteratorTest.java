package uk.ed.inf.graph.util.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ed.inf.graph.basic.IBasicEdge;
import uk.ed.inf.graph.basic.IBasicNode;

@RunWith(JMock.class)
public class EdgeFromNodeIteratorTest {
	private Mockery mockery = new JUnit4Mockery();
	private EdgeFromNodeIterator<TestNode, TestEdge> testInstance;
	

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(expected=IllegalArgumentException.class)
	public final void testEdgeIteratorCollectionOfQextendsINodeNullCollection() {
		this.testInstance = new EdgeFromNodeIterator<TestNode, TestEdge>(null);
	}

	@SuppressWarnings("unchecked")
	@Test
	public final void testEdgeIteratorCollectionOfQextendsINode() {
		final Collection<TestNode> mockNodeCollection = this.mockery.mock(Collection.class, "mockNodeCollection");
		final TestNode mockNode0 = this.mockery.mock(TestNode.class, "mockNode0");
		final TestNode mockNode1 = this.mockery.mock(TestNode.class, "mockNode1");
		final TestNode mockNode2 = this.mockery.mock(TestNode.class, "mockNode2");
		final TestNode mockNode3 = this.mockery.mock(TestNode.class, "mockNode3");
		final TestNode mockNode4 = this.mockery.mock(TestNode.class, "mockNode4");
		final TestNode mockNode5 = this.mockery.mock(TestNode.class, "mockNode5");
		final TestEdge mockEdge0 = this.mockery.mock(TestEdge.class, "mockEdge0");
		final TestEdge mockEdge1 = this.mockery.mock(TestEdge.class, "mockEdge1");
		final TestEdge mockEdge2 = this.mockery.mock(TestEdge.class, "mockEdge2");
		final TestEdge mockEdge3 = this.mockery.mock(TestEdge.class, "mockEdge3");
		final TestEdge mockEdge4 = this.mockery.mock(TestEdge.class, "mockEdge4");
		final TestEdge mockEdge5 = this.mockery.mock(TestEdge.class, "mockEdge5");
		final TestEdge mockEdge6 = this.mockery.mock(TestEdge.class, "mockEdge6");
		this.mockery.checking(new Expectations(){{
			allowing(mockNodeCollection).iterator(); will(returnIterator(mockNode0, mockNode1, mockNode2, mockNode3, mockNode4, mockNode5));
			
			allowing(mockNode0).edgeIterator(); will(returnIterator(mockEdge0, mockEdge1));

			allowing(mockNode1).edgeIterator(); will(returnIterator(mockEdge0, mockEdge2, mockEdge4, mockEdge4));

			allowing(mockNode2).edgeIterator(); will(returnIterator(mockEdge1, mockEdge2, mockEdge3));

			allowing(mockNode3).edgeIterator(); will(returnIterator(mockEdge3));

			allowing(mockNode4).edgeIterator(); will(returnIterator());

			allowing(mockNode5).edgeIterator(); will(returnIterator(mockEdge5, mockEdge5, mockEdge6, mockEdge6));
			
			allowing(mockEdge0).getIndex(); will(returnValue(0));
			
			allowing(mockEdge1).getIndex(); will(returnValue(1));
			
			allowing(mockEdge2).getIndex(); will(returnValue(2));
			
			allowing(mockEdge3).getIndex(); will(returnValue(3));
			
			allowing(mockEdge4).getIndex(); will(returnValue(4));
			
			allowing(mockEdge5).getIndex(); will(returnValue(5));
			
			allowing(mockEdge6).getIndex(); will(returnValue(6));
		}});
		this.testInstance = new EdgeFromNodeIterator(mockNodeCollection.iterator());
		TestEdge expectedIterationOrder[] = { mockEdge0, mockEdge1, mockEdge2, mockEdge4,
				mockEdge3, mockEdge5, mockEdge6 };
		List<TestEdge> expectedEdgeList = Arrays.asList(expectedIterationOrder);
		for(TestEdge expectedEdge : expectedEdgeList){
			assertTrue("edge available", this.testInstance.hasNext());
			assertEquals("expected next edge", expectedEdge, this.testInstance.next());
		}
		assertFalse("iterator is exhausted as expected", this.testInstance.hasNext());
		try{
			this.testInstance.next();
			fail("Expected a NoSuchElementException");
		}
		catch(NoSuchElementException e){
			// exception is expected here
		}
		this.mockery.assertIsSatisfied();
	}

	@SuppressWarnings("unchecked")
	@Test
	public final void testEdgeIteratorCollectionOfQextendsINodeNextOnly() {
		final Collection<TestNode> mockNodeCollection = this.mockery.mock(Collection.class, "mockNodeCollection");
		final TestNode mockNode0 = this.mockery.mock(TestNode.class, "mockNode0");
		final TestNode mockNode1 = this.mockery.mock(TestNode.class, "mockNode1");
		final TestNode mockNode2 = this.mockery.mock(TestNode.class, "mockNode2");
		final TestNode mockNode3 = this.mockery.mock(TestNode.class, "mockNode3");
		final TestNode mockNode4 = this.mockery.mock(TestNode.class, "mockNode4");
		final TestNode mockNode5 = this.mockery.mock(TestNode.class, "mockNode5");
		final TestEdge mockEdge0 = this.mockery.mock(TestEdge.class, "mockEdge0");
		final TestEdge mockEdge1 = this.mockery.mock(TestEdge.class, "mockEdge1");
		final TestEdge mockEdge2 = this.mockery.mock(TestEdge.class, "mockEdge2");
		final TestEdge mockEdge3 = this.mockery.mock(TestEdge.class, "mockEdge3");
		final TestEdge mockEdge4 = this.mockery.mock(TestEdge.class, "mockEdge4");
		final TestEdge mockEdge5 = this.mockery.mock(TestEdge.class, "mockEdge5");
		final TestEdge mockEdge6 = this.mockery.mock(TestEdge.class, "mockEdge6");
		this.mockery.checking(new Expectations(){{
			allowing(mockNodeCollection).iterator(); will(returnIterator(mockNode0, mockNode1, mockNode2, mockNode3, mockNode4, mockNode5));
			
			allowing(mockNode0).edgeIterator(); will(returnIterator(mockEdge0, mockEdge1));

			allowing(mockNode1).edgeIterator(); will(returnIterator(mockEdge0, mockEdge2, mockEdge4, mockEdge4));

			allowing(mockNode2).edgeIterator(); will(returnIterator(mockEdge1, mockEdge2, mockEdge3));

			allowing(mockNode3).edgeIterator(); will(returnIterator(mockEdge3));

			allowing(mockNode4).edgeIterator(); will(returnIterator());

			allowing(mockNode5).edgeIterator(); will(returnIterator(mockEdge5, mockEdge5, mockEdge6, mockEdge6));
			
			allowing(mockEdge0).getIndex(); will(returnValue(0));
			
			allowing(mockEdge1).getIndex(); will(returnValue(1));
			
			allowing(mockEdge2).getIndex(); will(returnValue(2));
			
			allowing(mockEdge3).getIndex(); will(returnValue(3));
			
			allowing(mockEdge4).getIndex(); will(returnValue(4));
			
			allowing(mockEdge5).getIndex(); will(returnValue(5));
			
			allowing(mockEdge6).getIndex(); will(returnValue(6));
		}});
		this.testInstance = new EdgeFromNodeIterator(mockNodeCollection.iterator());
		TestEdge expectedIterationOrder[] = { mockEdge0, mockEdge1, mockEdge2, mockEdge4,
				mockEdge3, mockEdge5, mockEdge6 };
		List<TestEdge> expectedEdgeList = Arrays.asList(expectedIterationOrder);
		for(TestEdge expectedEdge : expectedEdgeList){
			assertEquals("expected next edge", expectedEdge, this.testInstance.next());
		}
		try{
			this.testInstance.next();
			fail("Expected a NoSuchElementException");
		}
		catch(NoSuchElementException e){
			// exception is expected here
		}
		this.mockery.assertIsSatisfied();
	}

	@SuppressWarnings("unchecked")
	@Test
	public final void testEdgeIteratorCollectionOfQextendsINodeNoEdges() {
		final Collection<TestNode> mockNodeCollection = this.mockery.mock(Collection.class, "mockNodeCollection");
		final TestNode mockNode0 = this.mockery.mock(TestNode.class, "mockNode0");
		final TestNode mockNode1 = this.mockery.mock(TestNode.class, "mockNode1");
		final TestNode mockNode2 = this.mockery.mock(TestNode.class, "mockNode2");
		final TestNode mockNode3 = this.mockery.mock(TestNode.class, "mockNode3");
		final TestNode mockNode4 = this.mockery.mock(TestNode.class, "mockNode4");
		final TestNode mockNode5 = this.mockery.mock(TestNode.class, "mockNode5");
		this.mockery.checking(new Expectations(){{
			allowing(mockNodeCollection).iterator(); will(returnIterator(mockNode0, mockNode1, mockNode2, mockNode3, mockNode4, mockNode5));
			
			allowing(mockNode0).edgeIterator(); will(returnIterator());

			allowing(mockNode1).edgeIterator(); will(returnIterator());

			allowing(mockNode2).edgeIterator(); will(returnIterator());

			allowing(mockNode3).edgeIterator(); will(returnIterator());

			allowing(mockNode4).edgeIterator(); will(returnIterator());

			allowing(mockNode5).edgeIterator(); will(returnIterator());
		}});
		this.testInstance = new EdgeFromNodeIterator(mockNodeCollection.iterator());
		TestEdge expectedIterationOrder[] = { };
		List<TestEdge> expectedEdgeList = Arrays.asList(expectedIterationOrder);
		for(TestEdge expectedEdge : expectedEdgeList){
			assertTrue("edge available", this.testInstance.hasNext());
			assertEquals("expected next edge", expectedEdge, this.testInstance.next());
		}
		assertFalse("iterator is exhausted as expected", this.testInstance.hasNext());
		try{
			this.testInstance.next();
			fail("Expected a NoSuchElementException");
		}
		catch(NoSuchElementException e){
			// exception is expected here
		}
		this.mockery.assertIsSatisfied();
	}

	@SuppressWarnings("unchecked")
	@Test
	public final void testEdgeIteratorCollectionOfQextendsINodeNoNodes() {
		final Collection<TestNode> mockNodeCollection = this.mockery.mock(Collection.class, "mockNodeCollection");
		this.mockery.checking(new Expectations(){{
			allowing(mockNodeCollection).iterator(); will(returnIterator());
			
		}});
		this.testInstance = new EdgeFromNodeIterator(mockNodeCollection.iterator());
		TestEdge expectedIterationOrder[] = { };
		List<TestEdge> expectedEdgeList = Arrays.asList(expectedIterationOrder);
		for(TestEdge expectedEdge : expectedEdgeList){
			assertTrue("edge available", this.testInstance.hasNext());
			assertEquals("expected next edge", expectedEdge, this.testInstance.next());
		}
		assertFalse("iterator is exhausted as expected", this.testInstance.hasNext());
		try{
			this.testInstance.next();
			fail("Expected a NoSuchElementException");
		}
		catch(NoSuchElementException e){
			// exception is expected here
		}
		this.mockery.assertIsSatisfied();
	}
	@SuppressWarnings("unchecked")

	@Test
	public final void testEdgeIteratorCollectionOfQextendsINodeMoreDeg0Nodes() {
		final Collection<TestNode> mockNodeCollection = this.mockery.mock(Collection.class, "mockNodeCollection");
		final TestNode mockNode0 = this.mockery.mock(TestNode.class, "mockNode0");
		final TestNode mockNode1 = this.mockery.mock(TestNode.class, "mockNode1");
		final TestNode mockNode2 = this.mockery.mock(TestNode.class, "mockNode2");
		final TestNode mockNode3 = this.mockery.mock(TestNode.class, "mockNode3");
		final TestNode mockNode4 = this.mockery.mock(TestNode.class, "mockNode4");
		final TestNode mockNode5 = this.mockery.mock(TestNode.class, "mockNode5");
		final TestEdge mockEdge0 = this.mockery.mock(TestEdge.class, "mockEdge0");
		final TestEdge mockEdge1 = this.mockery.mock(TestEdge.class, "mockEdge1");
		final TestEdge mockEdge2 = this.mockery.mock(TestEdge.class, "mockEdge2");
		final TestEdge mockEdge3 = this.mockery.mock(TestEdge.class, "mockEdge3");
		final TestEdge mockEdge4 = this.mockery.mock(TestEdge.class, "mockEdge4");
		final TestEdge mockEdge5 = this.mockery.mock(TestEdge.class, "mockEdge5");
		final TestEdge mockEdge6 = this.mockery.mock(TestEdge.class, "mockEdge6");
		this.mockery.checking(new Expectations(){{
			allowing(mockNodeCollection).iterator(); will(returnIterator(mockNode0, mockNode1, mockNode2, mockNode3, mockNode4, mockNode5));
			
			allowing(mockNode0).edgeIterator(); will(returnIterator(mockEdge0, mockEdge3, mockEdge1));

			allowing(mockNode1).edgeIterator(); will(returnIterator(mockEdge0, mockEdge2, mockEdge4, mockEdge4));

			allowing(mockNode2).edgeIterator(); will(returnIterator(mockEdge1, mockEdge2, mockEdge3));

			allowing(mockNode3).edgeIterator(); will(returnIterator());

			allowing(mockNode4).edgeIterator(); will(returnIterator());

			allowing(mockNode5).edgeIterator(); will(returnIterator(mockEdge5, mockEdge5, mockEdge6, mockEdge6));
			
			allowing(mockEdge0).getIndex(); will(returnValue(0));
			
			allowing(mockEdge1).getIndex(); will(returnValue(1));
			
			allowing(mockEdge2).getIndex(); will(returnValue(2));
			
			allowing(mockEdge3).getIndex(); will(returnValue(3));
			
			allowing(mockEdge4).getIndex(); will(returnValue(4));
			
			allowing(mockEdge5).getIndex(); will(returnValue(5));
			
			allowing(mockEdge6).getIndex(); will(returnValue(6));
		}});
		this.testInstance = new EdgeFromNodeIterator(mockNodeCollection.iterator());
		TestEdge expectedIterationOrder[] = { mockEdge0, mockEdge3, mockEdge1, mockEdge2, mockEdge4,
				mockEdge5, mockEdge6 };
		List<TestEdge> expectedEdgeList = Arrays.asList(expectedIterationOrder);
		for(TestEdge expectedEdge : expectedEdgeList){
			assertTrue("edge available", this.testInstance.hasNext());
			assertEquals("expected next edge", expectedEdge, this.testInstance.next());
		}
		assertFalse("iterator is exhausted as expected", this.testInstance.hasNext());
		try{
			this.testInstance.next();
			fail("Expected a NoSuchElementException");
		}
		catch(NoSuchElementException e){
			// exception is expected here
		}
		this.mockery.assertIsSatisfied();
	}
	
	private interface TestEdge extends IBasicEdge<TestNode, TestEdge> {
		
	}
	
	private interface TestNode extends IBasicNode<TestNode, TestEdge> {
		
	}
}