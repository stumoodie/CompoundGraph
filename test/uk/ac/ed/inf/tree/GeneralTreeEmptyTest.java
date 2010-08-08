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

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.ed.inf.tree.GeneralTree;
import uk.ac.ed.inf.tree.ITreeNode;

@RunWith(JMock.class)
public class GeneralTreeEmptyTest {
	private static final int EXPECTED_ROOT_NODE_IDX = 0;
	private static final int EXPECTED_MISSING_NODE_IDX = 9;
	private static final int EXPECTED_EMPTY_SIZE = 1;
	private Mockery mockery = new JUnit4Mockery();
	private GeneralTree<TestTreeNode> testInstance;
	private TestTreeNode mockRootNode;
	
	@Before
	public void setUp() throws Exception {
		mockRootNode = this.mockery.mock(TestTreeNode.class, "mockRootNode");
		this.mockery.checking(new Expectations(){{
			allowing(mockRootNode).childIterator(); will(returnIterator(new TestTreeNode[0]));
			allowing(mockRootNode).getIndex(); will(returnValue(EXPECTED_ROOT_NODE_IDX));
			allowing(mockRootNode).getParent(); will(returnValue(mockRootNode));
			allowing(mockRootNode).getRoot(); will(returnValue(mockRootNode));
		}});
		this.testInstance = new GeneralTree<TestTreeNode>(mockRootNode);
		this.testInstance = new GeneralTree<TestTreeNode>(mockRootNode);
	}

	@After
	public void tearDown() throws Exception {
		this.testInstance = null;
	}

	@Test
	public final void testGeneralTree() {
//		final TestTreeNode mockRootNode = this.mockery.mock(TestTreeNode.class, "mockRootNode");
//		this.mockery.checking(new Expectations(){{
//			allowing(mockRootNode).getRoot(); will(returnValue(mockRootNode));
//		}});
		assertEquals("expected root node", mockRootNode, this.testInstance.getRootNode());
	}

	@Test(expected=NullPointerException.class)
	public final void testGeneralTreeNullRootNode() {
		new GeneralTree<TestTreeNode>(null);
	}

	@Test
	public final void testContainsNodeInt() {
		assertFalse("does not contain node", this.testInstance.containsNode(EXPECTED_MISSING_NODE_IDX));
	}

	@Test
	public final void testGet() {
		assertEquals("expected node", mockRootNode, this.testInstance.get(EXPECTED_ROOT_NODE_IDX));
		assertNull("does not contain node", this.testInstance.get(EXPECTED_MISSING_NODE_IDX));
	}

	@Test
	public final void testGetLowestCommonAncestor() {
		assertEquals("expected lca", mockRootNode, this.testInstance.getLowestCommonAncestor(mockRootNode, mockRootNode));
	}

	@Test
	public final void testLevelOrderIterator() {
		GeneralIteratorTestUtility<TestTreeNode> testIter = new GeneralIteratorTestUtility<TestTreeNode>(this.mockRootNode);
		testIter.testIterator(this.testInstance.levelOrderIterator());
	}

	@Test
	public final void testEmptySize() {
		assertEquals("expected un populated size", EXPECTED_EMPTY_SIZE, this.testInstance.size());
		this.mockery.assertIsSatisfied();
	}
		
	private interface TestTreeNode extends ITreeNode<TestTreeNode> {
		
	}
}
