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
package uk.ac.ed.inf.tree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
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

import uk.ac.ed.inf.tree.ITreeNode;
import uk.ac.ed.inf.tree.LevelOrderTreeIterator;

@RunWith(JMock.class)
public class LevelOrderTreeIteratorTest {
	private Mockery mockery = new JUnit4Mockery();

	private LevelOrderTreeIterator<TestTreeNode> testInstance;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		this.testInstance = null;
	}

	@Test(expected=IllegalArgumentException.class)
	public final void testLevelOrderTreeIteratorNullParam() {
		new LevelOrderTreeIterator<TestTreeNode>(null);
	}

	@Test
	public final void testSingleNodeIterator() {
		final TestTreeNode mockRootNode = this.mockery.mock(TestTreeNode.class, "mockRootNode");
		this.mockery.checking(new Expectations(){{
			
			allowing(mockRootNode).childIterator(); will(returnIterator());
			
		}});
		this.testInstance = new LevelOrderTreeIterator<TestTreeNode>(mockRootNode);
		assertTrue("expected node", this.testInstance.hasNext());
		assertEquals("expected node", mockRootNode, this.testInstance.next());
		assertFalse("iterator exhausted", this.testInstance.hasNext());
		try{
			this.testInstance.next();
			fail("Expected a NoSuchElementException");
		}
		catch(NoSuchElementException e){
			// exception is expected here
		}
		this.mockery.assertIsSatisfied();
	}

	@Test
	public final void testPopulatedIterator() {
		final TestTreeNode mockRootNode = this.mockery.mock(TestTreeNode.class, "mockRootNode");
		final TestTreeNode mockNode1 = this.mockery.mock(TestTreeNode.class, "mockNode1");
		final TestTreeNode mockNode2 = this.mockery.mock(TestTreeNode.class, "mockNode2");
		final TestTreeNode mockNode3 = this.mockery.mock(TestTreeNode.class, "mockNode3");
		final TestTreeNode mockNode4 = this.mockery.mock(TestTreeNode.class, "mockNode4");
		final TestTreeNode mockNode5 = this.mockery.mock(TestTreeNode.class, "mockNode5");
		final TestTreeNode mockNode6 = this.mockery.mock(TestTreeNode.class, "mockNode6");
		final TestTreeNode mockNode7 = this.mockery.mock(TestTreeNode.class, "mockNode7");
		final TestTreeNode mockNode8 = this.mockery.mock(TestTreeNode.class, "mockNode8");
		this.mockery.checking(new Expectations(){{
			
			allowing(mockRootNode).childIterator(); will(returnIterator( mockNode1, mockNode2 ));

			allowing(mockNode1).childIterator(); will(returnIterator(mockNode3, mockNode4));

			allowing(mockNode2).childIterator(); will(returnIterator(mockNode5, mockNode6));

			allowing(mockNode3).childIterator(); will(returnIterator());

			allowing(mockNode4).childIterator(); will(returnIterator());

			allowing(mockNode5).childIterator(); will(returnIterator());

			allowing(mockNode6).childIterator(); will(returnIterator(mockNode7));

			allowing(mockNode7).childIterator(); will(returnIterator(mockNode8));

			allowing(mockNode8).childIterator(); will(returnIterator());
		}});
		this.testInstance = new LevelOrderTreeIterator<TestTreeNode>(mockRootNode);
		TestTreeNode expectedIterationOrder[] = { mockRootNode, mockNode1, mockNode2, mockNode3,
				mockNode4, mockNode5, mockNode6, mockNode7, mockNode8 };
		List<TestTreeNode> expectedNodeList = Arrays.asList(expectedIterationOrder);
		for(TestTreeNode expectedNode : expectedNodeList){
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

	@Test(expected=UnsupportedOperationException.class)
	public final void testRemove() {
		final TestTreeNode mockRootNode = this.mockery.mock(TestTreeNode.class, "mockRootNode");
		this.mockery.checking(new Expectations(){{
		}});
		this.testInstance = new LevelOrderTreeIterator<TestTreeNode>(mockRootNode);
		this.mockery.assertIsSatisfied();
		this.testInstance.remove();
	}

	private interface TestTreeNode extends ITreeNode<TestTreeNode> {
		
	}
}
