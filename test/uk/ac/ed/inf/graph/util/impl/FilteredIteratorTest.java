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
package uk.ac.ed.inf.graph.util.impl;


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

import uk.ac.ed.inf.graph.basic.IBasicEdge;
import uk.ac.ed.inf.graph.basic.IBasicNode;
import uk.ac.ed.inf.graph.util.IFilterCriteria;
import uk.ac.ed.inf.graph.util.INodeSet;
import uk.ac.ed.inf.graph.util.impl.FilteredIterator;

@RunWith(JMock.class)
public class FilteredIteratorTest {
	private static final boolean EXPECTED_NODE_0_PRESENT = false;
	private static final boolean EXPECTED_NODE_1_PRESENT = false;
	private static final boolean EXPECTED_NODE_2_PRESENT = false;
	private static final boolean EXPECTED_NODE_3_PRESENT = false;
	private static final boolean EXPECTED_NODE_4_PRESENT = false;
	private static final boolean EXPECTED_NODE_5_PRESENT = false;
	private static final boolean EXPECTED_NODE_0_REMOVED = true;
	private static final boolean EXPECTED_NODE_1_REMOVED = true;
	private static final boolean EXPECTED_NODE_2_REMOVED = true;
	private static final boolean EXPECTED_NODE_3_REMOVED = true;
	private static final boolean EXPECTED_NODE_4_REMOVED = true;
	private static final boolean EXPECTED_NODE_5_REMOVED = true;
	
	private Mockery mockery = new JUnit4Mockery();
	private FilteredIterator<TestNode> testInstance;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		this.testInstance = null;
	}

	
	@SuppressWarnings("unchecked")
	@Test
	public void testFullList(){
		final INodeSet<TestNode, TestEdge> mockNodeCollection = this.mockery.mock(INodeSet.class, "mockNodeCollection");
		final TestNode mockNode0 = this.mockery.mock(TestNode.class, "mockNode0");
		final TestNode mockNode1 = this.mockery.mock(TestNode.class, "mockNode1");
		final TestNode mockNode2 = this.mockery.mock(TestNode.class, "mockNode2");
		final TestNode mockNode3 = this.mockery.mock(TestNode.class, "mockNode3");
		final TestNode mockNode4 = this.mockery.mock(TestNode.class, "mockNode4");
		final TestNode mockNode5 = this.mockery.mock(TestNode.class, "mockNode5");
		this.mockery.checking(new Expectations(){{
			allowing(mockNodeCollection).iterator(); will(returnIterator(mockNode0, mockNode1, mockNode2, mockNode3, mockNode4, mockNode5));
			
			allowing(mockNode0).getIndex(); will(returnValue(0));
			allowing(mockNode0).isRemoved(); will(returnValue(EXPECTED_NODE_0_PRESENT));
			
			allowing(mockNode1).getIndex(); will(returnValue(1));
			allowing(mockNode1).isRemoved(); will(returnValue(EXPECTED_NODE_1_PRESENT));
			
			allowing(mockNode2).getIndex(); will(returnValue(2));
			allowing(mockNode2).isRemoved(); will(returnValue(EXPECTED_NODE_2_PRESENT));
			
			allowing(mockNode3).getIndex(); will(returnValue(3));
			allowing(mockNode3).isRemoved(); will(returnValue(EXPECTED_NODE_3_PRESENT));
			
			allowing(mockNode4).getIndex(); will(returnValue(4));
			allowing(mockNode4).isRemoved(); will(returnValue(EXPECTED_NODE_4_PRESENT));
			
			allowing(mockNode5).getIndex(); will(returnValue(5));
			allowing(mockNode5).isRemoved(); will(returnValue(EXPECTED_NODE_5_PRESENT));
		}});
		this.testInstance = new FilteredIterator(mockNodeCollection.iterator(), new IFilterCriteria<TestNode>(){

			public boolean matched(TestNode testObj) {
				return !testObj.isRemoved();
			}	
		});
		TestNode expectedIterationOrder[] = { mockNode0, mockNode1, mockNode2, mockNode3,
				mockNode4, mockNode5 };
		List<TestNode> expectedNodeList = Arrays.asList(expectedIterationOrder);
		for(TestNode expectedNode : expectedNodeList){
			assertTrue("node available", this.testInstance.hasNext());
			assertEquals("expected next node", expectedNode, this.testInstance.next());
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
	public void testGapsList(){
		final Collection<TestNode> mockNodeCollection = this.mockery.mock(Collection.class, "mockNodeCollection");
		final TestNode mockNode0 = this.mockery.mock(TestNode.class, "mockNode0");
		final TestNode mockNode1 = this.mockery.mock(TestNode.class, "mockNode1");
		final TestNode mockNode2 = this.mockery.mock(TestNode.class, "mockNode2");
		final TestNode mockNode3 = this.mockery.mock(TestNode.class, "mockNode3");
		final TestNode mockNode4 = this.mockery.mock(TestNode.class, "mockNode4");
		final TestNode mockNode5 = this.mockery.mock(TestNode.class, "mockNode5");
		this.mockery.checking(new Expectations(){{
			allowing(mockNodeCollection).iterator(); will(returnIterator(mockNode0, mockNode1, mockNode2, mockNode3, mockNode4, mockNode5));
			
			allowing(mockNode0).getIndex(); will(returnValue(0));
			allowing(mockNode0).isRemoved(); will(returnValue(EXPECTED_NODE_0_PRESENT));
			
			allowing(mockNode1).getIndex(); will(returnValue(1));
			allowing(mockNode1).isRemoved(); will(returnValue(EXPECTED_NODE_1_PRESENT));
			
			allowing(mockNode2).getIndex(); will(returnValue(2));
			allowing(mockNode2).isRemoved(); will(returnValue(EXPECTED_NODE_2_REMOVED));
			
			allowing(mockNode3).getIndex(); will(returnValue(3));
			allowing(mockNode3).isRemoved(); will(returnValue(EXPECTED_NODE_3_PRESENT));
			
			allowing(mockNode4).getIndex(); will(returnValue(4));
			allowing(mockNode4).isRemoved(); will(returnValue(EXPECTED_NODE_4_REMOVED));
			
			allowing(mockNode5).getIndex(); will(returnValue(5));
			allowing(mockNode5).isRemoved(); will(returnValue(EXPECTED_NODE_5_PRESENT));
		}});
		this.testInstance = new FilteredIterator(mockNodeCollection.iterator(), new IFilterCriteria<TestNode>(){

			public boolean matched(TestNode testObj) {
				return !testObj.isRemoved();
			}	
		});
		TestNode expectedIterationOrder[] = { mockNode0, mockNode1, mockNode3, mockNode5 };
		List<TestNode> expectedNodeList = Arrays.asList(expectedIterationOrder);
		for(TestNode expectedNode : expectedNodeList){
			assertTrue("node available", this.testInstance.hasNext());
			assertEquals("expected next node", expectedNode, this.testInstance.next());
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
	public void testAllNullList(){
		final TestNode mockNode0 = this.mockery.mock(TestNode.class, "mockNode0");
		final TestNode mockNode1 = this.mockery.mock(TestNode.class, "mockNode1");
		final TestNode mockNode2 = this.mockery.mock(TestNode.class, "mockNode2");
		final TestNode mockNode3 = this.mockery.mock(TestNode.class, "mockNode3");
		final TestNode mockNode4 = this.mockery.mock(TestNode.class, "mockNode4");
		final TestNode mockNode5 = this.mockery.mock(TestNode.class, "mockNode5");
		final Collection<TestNode> mockNodeCollection = this.mockery.mock(Collection.class, "mockNodeCollection");
		this.mockery.checking(new Expectations(){{
			allowing(mockNodeCollection).iterator(); will(returnIterator(mockNode0, mockNode1, mockNode2, mockNode3, mockNode4, mockNode5));

			allowing(mockNode0).getIndex(); will(returnValue(0));
			allowing(mockNode0).isRemoved(); will(returnValue(EXPECTED_NODE_0_REMOVED));
			
			allowing(mockNode1).getIndex(); will(returnValue(1));
			allowing(mockNode1).isRemoved(); will(returnValue(EXPECTED_NODE_1_REMOVED));
			
			allowing(mockNode2).getIndex(); will(returnValue(2));
			allowing(mockNode2).isRemoved(); will(returnValue(EXPECTED_NODE_2_REMOVED));
			
			allowing(mockNode3).getIndex(); will(returnValue(3));
			allowing(mockNode3).isRemoved(); will(returnValue(EXPECTED_NODE_3_REMOVED));
			
			allowing(mockNode4).getIndex(); will(returnValue(4));
			allowing(mockNode4).isRemoved(); will(returnValue(EXPECTED_NODE_4_REMOVED));
			
			allowing(mockNode5).getIndex(); will(returnValue(5));
			allowing(mockNode5).isRemoved(); will(returnValue(EXPECTED_NODE_5_REMOVED));
		}});
		this.testInstance = new FilteredIterator(mockNodeCollection.iterator(), new IFilterCriteria<TestNode>(){

			public boolean matched(TestNode testObj) {
				return !testObj.isRemoved();
			}	
		});
		TestNode expectedIterationOrder[] = { };
		List<TestNode> expectedNodeList = Arrays.asList(expectedIterationOrder);
		for(TestNode expectedNode : expectedNodeList){
			assertTrue("node available", this.testInstance.hasNext());
			assertEquals("expected next node", expectedNode, this.testInstance.next());
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

	@Test(expected=IllegalArgumentException.class)
	public void testNullIterator(){
		this.testInstance = new FilteredIterator<TestNode>(null, null);
	}
	
	private interface TestEdge extends IBasicEdge<TestNode, TestEdge> {
		
	}
	
	private interface TestNode extends IBasicNode<TestNode, TestEdge> {
		
	}
}
