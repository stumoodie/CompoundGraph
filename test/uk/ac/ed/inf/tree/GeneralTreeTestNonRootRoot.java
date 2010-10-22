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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class GeneralTreeTestNonRootRoot {
	private static final int EXPECTED_ROOT_NODE_IDX = 0;
	private static final int EXPECTED_NODE1_IDX = 1;
	private static final int EXPECTED_NODE2_IDX = 2;
	private static final int EXPECTED_NODE3_IDX = 3;
	private static final int EXPECTED_NODE4_IDX = 4;
	private static final int EXPECTED_NODE5_IDX = 5;
	private static final int EXPECTED_NODE6_IDX = 6;
	private static final int EXPECTED_NODE7_IDX = 7;
	private static final int EXPECTED_NODE8_IDX = 8;
	private static final int EXPECTED_MISSING_NODE_IDX = 9;
//	private static final int EXPECTED_POPULATED_SIZE = 3;
	private Mockery mockery = new JUnit4Mockery();
	private GeneralTree<TestTreeNode> testInstance;
	private TestTreeNode mockRootNode;
	private TestTreeNode mockNode7;
	private TestTreeNode mockNode1;
	private TestTreeNode mockNode3;
	private TestTreeNode mockNode8;
	private TestTreeNode mockNode6;
	private TestTreeNode mockNode5;
	private TestTreeNode mockNode4;
	private TestTreeNode mockNode2;
	private TestTreeNode mockOtherRootNode;
	private TestTreeNode mockOtherNode7;
	private TestTreeNode mockOtherNode1;
	private TestTreeNode mockOtherNode3;
	private TestTreeNode mockOtherNode8;
	private TestTreeNode mockOtherNode6;
	private TestTreeNode mockOtherNode5;
	private TestTreeNode mockOtherNode4;
	private TestTreeNode mockOtherNode2;
	
	@Before
	public void setUp() throws Exception {
		mockRootNode = this.mockery.mock(TestTreeNode.class, "mockRootNode");
		mockNode1 = this.mockery.mock(TestTreeNode.class, "mockNode1");
		mockNode2 = this.mockery.mock(TestTreeNode.class, "mockNode2");
		mockNode3 = this.mockery.mock(TestTreeNode.class, "mockNode3");
		mockNode4 = this.mockery.mock(TestTreeNode.class, "mockNode4");
		mockNode5 = this.mockery.mock(TestTreeNode.class, "mockNode5");
		mockNode6 = this.mockery.mock(TestTreeNode.class, "mockNode6");
		mockNode7 = this.mockery.mock(TestTreeNode.class, "mockNode7");
		mockNode8 = this.mockery.mock(TestTreeNode.class, "mockNode8");
		this.mockery.checking(new Expectations(){{
			allowing(mockRootNode).childIterator(); will(returnIterator( mockNode1, mockNode2 ));
			allowing(mockRootNode).getIndex(); will(returnValue(EXPECTED_ROOT_NODE_IDX));
			allowing(mockRootNode).getParent(); will(returnValue(mockRootNode));
			allowing(mockRootNode).getRoot(); will(returnValue(mockRootNode));

			allowing(mockNode1).childIterator(); will(returnIterator(mockNode3, mockNode4));
			allowing(mockNode1).getIndex(); will(returnValue(EXPECTED_NODE1_IDX));
			allowing(mockNode1).getParent(); will(returnValue(mockRootNode));
			allowing(mockNode1).getRoot(); will(returnValue(mockRootNode));

			allowing(mockNode2).childIterator(); will(returnIterator(mockNode5, mockNode6));
			allowing(mockNode2).getIndex(); will(returnValue(EXPECTED_NODE2_IDX));
			allowing(mockNode2).getParent(); will(returnValue(mockRootNode));
			allowing(mockNode2).getRoot(); will(returnValue(mockRootNode));

			allowing(mockNode3).childIterator(); will(returnIterator(new TestTreeNode[0]));
			allowing(mockNode3).getIndex(); will(returnValue(EXPECTED_NODE3_IDX));
			allowing(mockNode3).getParent(); will(returnValue(mockNode1));
			allowing(mockNode3).getRoot(); will(returnValue(mockRootNode));

			allowing(mockNode4).childIterator(); will(returnIterator(new TestTreeNode[0]));
			allowing(mockNode4).getIndex(); will(returnValue(EXPECTED_NODE4_IDX));
			allowing(mockNode4).getParent(); will(returnValue(mockNode1));
			allowing(mockNode4).getRoot(); will(returnValue(mockRootNode));

			allowing(mockNode5).childIterator(); will(returnIterator(new TestTreeNode[0]));
			allowing(mockNode5).getIndex(); will(returnValue(EXPECTED_NODE5_IDX));
			allowing(mockNode5).getParent(); will(returnValue(mockNode2));
			allowing(mockNode5).getRoot(); will(returnValue(mockRootNode));

			allowing(mockNode6).childIterator(); will(returnIterator(mockNode7));
			allowing(mockNode6).getIndex(); will(returnValue(EXPECTED_NODE6_IDX));
			allowing(mockNode6).getParent(); will(returnValue(mockNode2));
			allowing(mockNode6).getRoot(); will(returnValue(mockRootNode));

			allowing(mockNode7).childIterator(); will(returnIterator(mockNode8));
			allowing(mockNode7).getIndex(); will(returnValue(EXPECTED_NODE7_IDX));
			allowing(mockNode7).getParent(); will(returnValue(mockNode6));
			allowing(mockNode7).getRoot(); will(returnValue(mockRootNode));

			allowing(mockNode8).childIterator(); will(returnIterator(new TestTreeNode[0]));
			allowing(mockNode8).getIndex(); will(returnValue(EXPECTED_NODE8_IDX));
			allowing(mockNode8).getParent(); will(returnValue(mockNode7));
			allowing(mockNode8).getRoot(); will(returnValue(mockRootNode));
		}});
		this.testInstance = new GeneralTree<TestTreeNode>(mockRootNode);
		mockOtherRootNode = this.mockery.mock(TestTreeNode.class, "mockOtherRootNode");
		mockOtherNode1 = this.mockery.mock(TestTreeNode.class, "mockOtherNode1");
		mockOtherNode2 = this.mockery.mock(TestTreeNode.class, "mockOtherNode2");
		mockOtherNode3 = this.mockery.mock(TestTreeNode.class, "mockOtherNode3");
		mockOtherNode4 = this.mockery.mock(TestTreeNode.class, "mockOtherNode4");
		mockOtherNode5 = this.mockery.mock(TestTreeNode.class, "mockOtherNode5");
		mockOtherNode6 = this.mockery.mock(TestTreeNode.class, "mockOtherNode6");
		mockOtherNode7 = this.mockery.mock(TestTreeNode.class, "mockOtherNode7");
		mockOtherNode8 = this.mockery.mock(TestTreeNode.class, "mockOtherNode8");
		this.mockery.checking(new Expectations(){{
			allowing(mockOtherRootNode).childIterator(); will(returnIterator( mockOtherNode1, mockOtherNode2 ));
			allowing(mockOtherRootNode).getIndex(); will(returnValue(EXPECTED_ROOT_NODE_IDX));
			allowing(mockOtherRootNode).getParent(); will(returnValue(mockOtherRootNode));
			allowing(mockOtherRootNode).getRoot(); will(returnValue(mockOtherRootNode));

			allowing(mockOtherNode1).childIterator(); will(returnIterator(mockOtherNode3, mockOtherNode4));
			allowing(mockOtherNode1).getIndex(); will(returnValue(EXPECTED_NODE1_IDX));
			allowing(mockOtherNode1).getParent(); will(returnValue(mockOtherRootNode));
			allowing(mockOtherNode1).getRoot(); will(returnValue(mockOtherRootNode));

			allowing(mockOtherNode2).childIterator(); will(returnIterator(mockOtherNode5, mockOtherNode6));
			allowing(mockOtherNode2).getIndex(); will(returnValue(EXPECTED_NODE2_IDX));
			allowing(mockOtherNode2).getParent(); will(returnValue(mockOtherRootNode));
			allowing(mockOtherNode2).getRoot(); will(returnValue(mockOtherRootNode));

			allowing(mockOtherNode3).childIterator(); will(returnIterator(new TestTreeNode[0]));
			allowing(mockOtherNode3).getIndex(); will(returnValue(EXPECTED_NODE3_IDX));
			allowing(mockOtherNode3).getParent(); will(returnValue(mockOtherNode1));
			allowing(mockOtherNode3).getRoot(); will(returnValue(mockOtherRootNode));

			allowing(mockOtherNode4).childIterator(); will(returnIterator(new TestTreeNode[0]));
			allowing(mockOtherNode4).getIndex(); will(returnValue(EXPECTED_NODE4_IDX));
			allowing(mockOtherNode4).getParent(); will(returnValue(mockOtherNode1));
			allowing(mockOtherNode4).getRoot(); will(returnValue(mockOtherRootNode));

			allowing(mockOtherNode5).childIterator(); will(returnIterator(new TestTreeNode[0]));
			allowing(mockOtherNode5).getIndex(); will(returnValue(EXPECTED_NODE5_IDX));
			allowing(mockOtherNode5).getParent(); will(returnValue(mockOtherNode2));
			allowing(mockOtherNode5).getRoot(); will(returnValue(mockOtherRootNode));

			allowing(mockOtherNode6).childIterator(); will(returnIterator(mockOtherNode7));
			allowing(mockOtherNode6).getIndex(); will(returnValue(EXPECTED_NODE6_IDX));
			allowing(mockOtherNode6).getParent(); will(returnValue(mockOtherNode2));
			allowing(mockOtherNode6).getRoot(); will(returnValue(mockOtherRootNode));

			allowing(mockOtherNode7).childIterator(); will(returnIterator(mockOtherNode8));
			allowing(mockOtherNode7).getIndex(); will(returnValue(EXPECTED_NODE7_IDX));
			allowing(mockOtherNode7).getParent(); will(returnValue(mockOtherNode6));
			allowing(mockOtherNode7).getRoot(); will(returnValue(mockOtherRootNode));

			allowing(mockOtherNode8).childIterator(); will(returnIterator(new TestTreeNode[0]));
			allowing(mockOtherNode8).getIndex(); will(returnValue(EXPECTED_NODE8_IDX));
			allowing(mockOtherNode8).getParent(); will(returnValue(mockOtherNode7));
			allowing(mockOtherNode8).getRoot(); will(returnValue(mockOtherRootNode));
		}});
	}

	@After
	public void tearDown() throws Exception {
		this.testInstance = null;
	}

	@Test
	public final void testContainsNodeInt() {
		assertTrue("contains node", this.testInstance.containsNode(EXPECTED_NODE1_IDX));
		assertTrue("contains node", this.testInstance.containsNode(EXPECTED_NODE6_IDX));
		assertTrue("contains node", this.testInstance.containsNode(EXPECTED_NODE7_IDX));
		assertFalse("does not contain node", this.testInstance.containsNode(EXPECTED_MISSING_NODE_IDX));
	}

	@Test
	public final void testContainsNodeT() {
		assertTrue("contains node", this.testInstance.containsNode(this.mockNode1));
		assertTrue("contains node", this.testInstance.containsNode(this.mockNode7));
		assertTrue("contains node", this.testInstance.containsNode(this.mockRootNode));
		assertFalse("does not contain node", this.testInstance.containsNode(this.mockOtherNode2));
	}

	@Test
	public final void testGet() {
		assertEquals("expected node", mockNode6, this.testInstance.get(EXPECTED_NODE6_IDX));
		assertEquals("expected node", mockNode7, this.testInstance.get(EXPECTED_NODE7_IDX));
		assertEquals("expected node", this.mockRootNode, this.testInstance.get(EXPECTED_ROOT_NODE_IDX));
		assertNull("does not contain node", this.testInstance.get(EXPECTED_MISSING_NODE_IDX));
	}


	@Test(expected=NullPointerException.class)
	public final void testGetLowestCommonAncestorNullNull() {
		this.testInstance.getLowestCommonAncestor(null, null);
	}		
		
	@Test(expected=IllegalArgumentException.class)
	public final void testGetLowestCommonAncestorFromNonRoot() {
		this.testInstance.getLowestCommonAncestor(mockNode6, mockOtherNode1);
	}

	@Test
	public final void testGetLowestCommonAncestor() {
		assertEquals("expected lcm", mockNode7, this.testInstance.getLowestCommonAncestor(mockNode8, mockNode7));
		assertEquals("expected lcm", mockNode6, this.testInstance.getLowestCommonAncestor(mockNode6, mockNode8));
		assertEquals("expected lcm", this.mockRootNode, this.testInstance.getLowestCommonAncestor(mockNode8, mockRootNode));
	}


	private interface TestTreeNode extends ITreeNode<TestTreeNode> {
		
	}
}
