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
package uk.ed.inf.tree;

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

import uk.ed.inf.graph.compound.ICompoundGraphElement;

@RunWith(JMock.class)
public class GeneralTreeTest {
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
	private static final int EXPECTED_POPULATED_SIZE = 9;
//	private static final int EXPECTED_EMPTY_SIZE = 1;
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

			allowing(mockNode3).childIterator(); will(returnIterator(new ICompoundGraphElement[0]));
			allowing(mockNode3).getIndex(); will(returnValue(EXPECTED_NODE3_IDX));
			allowing(mockNode3).getParent(); will(returnValue(mockNode1));
			allowing(mockNode3).getRoot(); will(returnValue(mockRootNode));

			allowing(mockNode4).childIterator(); will(returnIterator(new ICompoundGraphElement[0]));
			allowing(mockNode4).getIndex(); will(returnValue(EXPECTED_NODE4_IDX));
			allowing(mockNode4).getParent(); will(returnValue(mockNode1));
			allowing(mockNode4).getRoot(); will(returnValue(mockRootNode));

			allowing(mockNode5).childIterator(); will(returnIterator(new ICompoundGraphElement[0]));
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

			allowing(mockNode8).childIterator(); will(returnIterator(new ICompoundGraphElement[0]));
			allowing(mockNode8).getIndex(); will(returnValue(EXPECTED_NODE8_IDX));
			allowing(mockNode8).getParent(); will(returnValue(mockNode7));
			allowing(mockNode8).getRoot(); will(returnValue(mockRootNode));
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
		assertEquals("expected root node", mockRootNode, this.testInstance.getRootNode());
	}

	@Test(expected=NullPointerException.class)
	public final void testGeneralTreeNullRootNode() {
		new GeneralTree<TestTreeNode>(null);
	}

	@Test
	public final void testContainsNodeInt() {
		assertTrue("contains node", this.testInstance.containsNode(EXPECTED_NODE7_IDX));
		assertFalse("does not contain node", this.testInstance.containsNode(EXPECTED_MISSING_NODE_IDX));
	}

	@Test
	public final void testGet() {
		assertEquals("expected node", mockNode7, this.testInstance.get(EXPECTED_NODE7_IDX));
		assertEquals("expected node", mockRootNode, this.testInstance.get(EXPECTED_ROOT_NODE_IDX));
		assertNull("does not contain node", this.testInstance.get(EXPECTED_MISSING_NODE_IDX));
	}

	@Test
	public final void testIsDescendent(){
		assertTrue("descendent", this.testInstance.isDescendant(mockNode1, mockNode3));
		assertTrue("descendent", this.testInstance.isDescendant(mockRootNode, mockNode8));
		assertFalse("not descendent", this.testInstance.isDescendant(mockNode1, mockNode6));
		assertFalse("not descendent", this.testInstance.isDescendant(mockNode1, mockNode1));
		assertFalse("not descendent", this.testInstance.isDescendant(mockNode3, mockNode4));
		assertFalse("not descendent", this.testInstance.isDescendant(mockNode3, mockNode1));
	}
	
	@Test
	public final void testIsAnscestor(){
		assertTrue("anscestor", this.testInstance.isAncestor(mockNode3, mockNode1));
		assertTrue("anscestor", this.testInstance.isAncestor(mockNode8, mockRootNode));
		assertFalse("not anscestor", this.testInstance.isAncestor(mockNode6, mockNode1));
		assertFalse("not anscestor", this.testInstance.isAncestor(mockNode1, mockNode1));
		assertFalse("not anscestor", this.testInstance.isAncestor(mockNode3, mockNode4));
	}
	
	@Test
	public final void testContainsNodeNull(){
		assertFalse("not contained", this.testInstance.containsNode(null));
	}
	
	@Test
	public final void testGetLowestCommonAncestor() {
//		final TestTreeNode mockRootNode = this.mockery.mock(TestTreeNode.class, "mockRootNode");
//		final TestTreeNode mockNode1 = this.mockery.mock(TestTreeNode.class, "mockNode1");
//		final TestTreeNode mockNode2 = this.mockery.mock(TestTreeNode.class, "mockNode2");
//		final TestTreeNode mockNode3 = this.mockery.mock(TestTreeNode.class, "mockNode3");
//		final TestTreeNode mockNode4 = this.mockery.mock(TestTreeNode.class, "mockNode4");
//		final TestTreeNode mockNode5 = this.mockery.mock(TestTreeNode.class, "mockNode5");
//		final TestTreeNode mockNode6 = this.mockery.mock(TestTreeNode.class, "mockNode6");
//		final TestTreeNode mockNode7 = this.mockery.mock(TestTreeNode.class, "mockNode7");
//		final TestTreeNode mockNode8 = this.mockery.mock(TestTreeNode.class, "mockNode8");
//		final TestTreeNode mockAltRootNode = this.mockery.mock(TestTreeNode.class, "mockAltRootNode");
//		final TestTreeNode mockNode91 = this.mockery.mock(TestTreeNode.class, "mockNode91");
//		final TestTreeNode mockNode92 = this.mockery.mock(TestTreeNode.class, "mockNode92");
//		final TestTreeNode mockNode93 = this.mockery.mock(TestTreeNode.class, "mockNode93");
//		final TestTreeNode mockNode94 = this.mockery.mock(TestTreeNode.class, "mockNode94");
//		final TestTreeNode mockNode95 = this.mockery.mock(TestTreeNode.class, "mockNode95");
//		final TestTreeNode mockNode96 = this.mockery.mock(TestTreeNode.class, "mockNode96");
//		final TestTreeNode mockNode97 = this.mockery.mock(TestTreeNode.class, "mockNode97");
//		final TestTreeNode mockNode98 = this.mockery.mock(TestTreeNode.class, "mockNode98");
//
//		this.mockery.checking(new Expectations(){{
//			allowing(mockRootNode).childIterator(); will(returnIterator( mockNode1, mockNode2 ));
//			allowing(mockRootNode).getIndex(); will(returnValue(EXPECTED_ROOT_NODE_IDX));
//			allowing(mockRootNode).getParent(); will(returnValue(mockRootNode));
//			allowing(mockRootNode).getRoot(); will(returnValue(mockRootNode));
//
//			allowing(mockNode1).childIterator(); will(returnIterator(mockNode3, mockNode4));
//			allowing(mockNode1).getIndex(); will(returnValue(EXPECTED_NODE1_IDX));
//			allowing(mockNode1).getParent(); will(returnValue(mockRootNode));
//
//			allowing(mockNode2).childIterator(); will(returnIterator(mockNode5, mockNode6));
//			allowing(mockNode2).getIndex(); will(returnValue(EXPECTED_NODE2_IDX));
//			allowing(mockNode2).getParent(); will(returnValue(mockRootNode));
//
//			allowing(mockNode3).childIterator(); will(returnIterator());
//			allowing(mockNode3).getIndex(); will(returnValue(EXPECTED_NODE3_IDX));
//			allowing(mockNode3).getParent(); will(returnValue(mockNode1));
//			allowing(mockNode3).getRoot(); will(returnValue(mockRootNode));
//
//			allowing(mockNode4).childIterator(); will(returnIterator());
//			allowing(mockNode4).getIndex(); will(returnValue(EXPECTED_NODE4_IDX));
//			allowing(mockNode4).getParent(); will(returnValue(mockNode1));
//			allowing(mockNode4).getRoot(); will(returnValue(mockRootNode));
//
//			allowing(mockNode5).childIterator(); will(returnIterator());
//			allowing(mockNode5).getIndex(); will(returnValue(EXPECTED_NODE5_IDX));
//			allowing(mockNode5).getParent(); will(returnValue(mockNode2));
//			allowing(mockNode5).getRoot(); will(returnValue(mockRootNode));
//
//			allowing(mockNode6).childIterator(); will(returnIterator(mockNode7));
//			allowing(mockNode6).getIndex(); will(returnValue(EXPECTED_NODE6_IDX));
//			allowing(mockNode6).getParent(); will(returnValue(mockNode2));
//
//			allowing(mockNode7).childIterator(); will(returnIterator(mockNode8));
//			allowing(mockNode7).getIndex(); will(returnValue(EXPECTED_NODE7_IDX));
//			allowing(mockNode7).getParent(); will(returnValue(mockNode6));
//
//			allowing(mockNode8).childIterator(); will(returnIterator());
//			allowing(mockNode8).getIndex(); will(returnValue(EXPECTED_NODE8_IDX));
//			allowing(mockNode8).getParent(); will(returnValue(mockNode7));
//			allowing(mockNode8).getRoot(); will(returnValue(mockRootNode));
//
//			allowing(mockAltRootNode).childIterator(); will(returnIterator( mockNode91, mockNode92 ));
//			allowing(mockAltRootNode).getIndex(); will(returnValue(EXPECTED_ROOT_NODE_IDX));
//			allowing(mockAltRootNode).getParent(); will(returnValue(mockAltRootNode));
//			allowing(mockAltRootNode).getRoot(); will(returnValue(mockAltRootNode));
//
//			allowing(mockNode91).childIterator(); will(returnIterator(mockNode93, mockNode94));
//			allowing(mockNode91).getIndex(); will(returnValue(EXPECTED_NODE1_IDX));
//			allowing(mockNode91).getParent(); will(returnValue(mockAltRootNode));
//			allowing(mockNode91).getRoot(); will(returnValue(mockAltRootNode));
//
//			allowing(mockNode92).childIterator(); will(returnIterator(mockNode95, mockNode96));
//			allowing(mockNode92).getIndex(); will(returnValue(EXPECTED_NODE2_IDX));
//			allowing(mockNode92).getParent(); will(returnValue(mockAltRootNode));
//
//			allowing(mockNode93).childIterator(); will(returnIterator());
//			allowing(mockNode93).getIndex(); will(returnValue(EXPECTED_NODE3_IDX));
//			allowing(mockNode93).getParent(); will(returnValue(mockNode91));
//
//			allowing(mockNode94).childIterator(); will(returnIterator());
//			allowing(mockNode94).getIndex(); will(returnValue(EXPECTED_NODE4_IDX));
//			allowing(mockNode94).getParent(); will(returnValue(mockNode91));
//
//			allowing(mockNode95).childIterator(); will(returnIterator());
//			allowing(mockNode95).getIndex(); will(returnValue(EXPECTED_NODE5_IDX));
//			allowing(mockNode95).getParent(); will(returnValue(mockNode92));
//
//			allowing(mockNode96).childIterator(); will(returnIterator(mockNode97));
//			allowing(mockNode96).getIndex(); will(returnValue(EXPECTED_NODE6_IDX));
//			allowing(mockNode96).getParent(); will(returnValue(mockNode92));
//
//			allowing(mockNode97).childIterator(); will(returnIterator(mockNode98));
//			allowing(mockNode97).getIndex(); will(returnValue(EXPECTED_NODE7_IDX));
//			allowing(mockNode97).getParent(); will(returnValue(mockNode96));
//
//			allowing(mockNode98).childIterator(); will(returnIterator());
//			allowing(mockNode98).getIndex(); will(returnValue(EXPECTED_NODE8_IDX));
//			allowing(mockNode98).getParent(); will(returnValue(mockNode97));
//			allowing(mockNode98).getRoot(); will(returnValue(mockAltRootNode));
//		}});
//		this.testInstance = new GeneralTree<TestTreeNode>(mockRootNode);
		assertEquals("expected lca", mockNode1, this.testInstance.getLowestCommonAncestor(mockNode3, mockNode4));
		assertEquals("expected lca", mockRootNode, this.testInstance.getLowestCommonAncestor(mockNode3, mockNode8));
		assertEquals("expected lca", mockRootNode, this.testInstance.getLowestCommonAncestor(mockNode8, mockNode4));
		assertEquals("expected lca", mockRootNode, this.testInstance.getLowestCommonAncestor(mockNode8, mockRootNode));
		assertEquals("expected lca", mockRootNode, this.testInstance.getLowestCommonAncestor(mockRootNode, mockNode5));
//		try{
//			this.testInstance.getLowestCommonAncestor(mockNode98, mockAltRootNode);
//			fail("Exception was expected!");
//		}
//		catch(IllegalArgumentException e){
//			// success! expected exception to be thrown
//		}
//		try{
//			this.testInstance.getLowestCommonAncestor(mockAltRootNode, mockNode95);
//			fail("Exception was expected!");
//		}
//		catch(IllegalArgumentException e){
//			// success! expected exception to be thrown
//		}
//		try{
//			this.testInstance.getLowestCommonAncestor(mockNode8, mockNode91);
//			fail("Exception was expected!");
//		}
//		catch(IllegalArgumentException e){
//			// success! expected exception to be thrown
//		}
//		try{
//			this.testInstance.getLowestCommonAncestor(mockNode8, null);
//			fail("Exception was expected!");
//		}
//		catch(NullPointerException e){
//			// success! expected exception to be thrown
//		}
//		try{
//			this.testInstance.getLowestCommonAncestor(null, mockNode8);
//			fail("Exception was expected!");
//		}
//		catch(NullPointerException e){
//			// success! expected exception to be thrown
//		}
//		try{
//			this.testInstance.getLowestCommonAncestor(null, null);
//			fail("Exception was expected!");
//		}
//		catch(NullPointerException e){
//			// success! expected exception to be thrown
//		}
//		this.mockery.assertIsSatisfied();
	}

	@Test(expected=NullPointerException.class)
	public final void testGetLowestCommonAncestorNullNull() {
		this.testInstance.getLowestCommonAncestor(null, null);
	}		
		

	@Test
	public final void testLevelOrderIterator() {
		GeneralIteratorTestUtility<TestTreeNode> testIter = new GeneralIteratorTestUtility<TestTreeNode>(this.mockRootNode,
				this.mockNode1, this.mockNode2, this.mockNode3, this.mockNode4, this.mockNode5,
				this.mockNode6, this.mockNode7, this.mockNode8);
		testIter.testIterator(this.testInstance.levelOrderIterator());
	}

	@Test
	public final void testPopulatedSize() {
//		final TestTreeNode mockRootNode = this.mockery.mock(TestTreeNode.class, "mockRootNode");
//		final TestTreeNode mockNode1 = this.mockery.mock(TestTreeNode.class, "mockNode1");
//		final TestTreeNode mockNode2 = this.mockery.mock(TestTreeNode.class, "mockNode2");
//		final TestTreeNode mockNode3 = this.mockery.mock(TestTreeNode.class, "mockNode3");
//		final TestTreeNode mockNode4 = this.mockery.mock(TestTreeNode.class, "mockNode4");
//		final TestTreeNode mockNode5 = this.mockery.mock(TestTreeNode.class, "mockNode5");
//		final TestTreeNode mockNode6 = this.mockery.mock(TestTreeNode.class, "mockNode6");
//		final TestTreeNode mockNode7 = this.mockery.mock(TestTreeNode.class, "mockNode7");
//		final TestTreeNode mockNode8 = this.mockery.mock(TestTreeNode.class, "mockNode8");
//		this.mockery.checking(new Expectations(){{
//			allowing(mockRootNode).childIterator(); will(returnIterator( mockNode1, mockNode2 ));
//			allowing(mockRootNode).getIndex(); will(returnValue(EXPECTED_ROOT_NODE_IDX));
//			allowing(mockRootNode).getRoot(); will(returnValue(mockRootNode));
//
//			allowing(mockNode1).childIterator(); will(returnIterator(mockNode3, mockNode4));
//			allowing(mockNode1).getIndex(); will(returnValue(EXPECTED_NODE1_IDX));
//
//			allowing(mockNode2).childIterator(); will(returnIterator(mockNode5, mockNode6));
//			allowing(mockNode2).getIndex(); will(returnValue(EXPECTED_NODE2_IDX));
//
//			allowing(mockNode3).childIterator(); will(returnIterator());
//			allowing(mockNode3).getIndex(); will(returnValue(EXPECTED_NODE3_IDX));
//
//			allowing(mockNode4).childIterator(); will(returnIterator());
//			allowing(mockNode4).getIndex(); will(returnValue(EXPECTED_NODE4_IDX));
//
//			allowing(mockNode5).childIterator(); will(returnIterator());
//			allowing(mockNode5).getIndex(); will(returnValue(EXPECTED_NODE5_IDX));
//
//			allowing(mockNode6).childIterator(); will(returnIterator(mockNode7));
//			allowing(mockNode6).getIndex(); will(returnValue(EXPECTED_NODE6_IDX));
//
//			allowing(mockNode7).childIterator(); will(returnIterator(mockNode8));
//			allowing(mockNode7).getIndex(); will(returnValue(EXPECTED_NODE7_IDX));
//
//			allowing(mockNode8).childIterator(); will(returnIterator());
//			allowing(mockNode8).getIndex(); will(returnValue(EXPECTED_NODE8_IDX));
//		}});
//		this.testInstance = new GeneralTree<TestTreeNode>(mockRootNode);
		assertEquals("expected un populated size", EXPECTED_POPULATED_SIZE, this.testInstance.size());
//		this.mockery.assertIsSatisfied();
	}

	private interface TestTreeNode extends ITreeNode<TestTreeNode> {
		
	}
}
