package uk.ed.inf.graph.compound.newimpl;

import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundGraph;
import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.ICompoundNodePair;
import uk.ed.inf.tree.ITree;

@RunWith(JMock.class)
public class NewCompoundEdgeTest {
	private static final int EXPECTED_EDGE_IDX = 22;

	private static final int PARENT_IDX = 0;

	private static final int PARENT_LEVEL = 0;

	private static final int EXPECTED_LEVEL = 1;

	private static final int OUT_NODE_IDX = 25;
	private static final int IN_NODE_IDX = 29;

	private static final int EXPECTED_NUM_ANCESTOR_NODES = 2;

	private static final int EXPECTED_NUM_PREORDER_NODES = 1;

	private Mockery mockery;

	private ICompoundEdge testInstance;

	private ICompoundGraphElement mockParent;

	private ICompoundNode mockOutNode;

	private ICompoundNode mockInNode;

	private ICompoundGraph mockGraph;

	private ICompoundGraph mockOtherGraph;

	private ICompoundNode mockOtherGraphOutNode;

	private ICompoundNode mockOtherGraphInNode;

	private ICompoundGraphElement mockOtherParent;

	private ITree<ICompoundGraphElement> mockElementTree;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		this.mockery = new JUnit4Mockery();
		this.mockGraph = this.mockery.mock(ICompoundGraph.class, "mockGraph");
		this.mockParent = this.mockery.mock(ICompoundGraphElement.class, "mockParent");
		this.mockOutNode = this.mockery.mock(ICompoundNode.class, "mockOutNode");
		this.mockInNode = this.mockery.mock(ICompoundNode.class, "mockInNode");
		this.mockElementTree = this.mockery.mock(ITree.class, "mockElementTree");

		this.mockOtherGraph = this.mockery.mock(ICompoundGraph.class, "mockOtherGraph");
		this.mockOtherParent = this.mockery.mock(ICompoundGraphElement.class, "mockOtherParent");
		this.mockOtherGraphOutNode = this.mockery.mock(ICompoundNode.class, "mockOtherGraphOutNode");
		this.mockOtherGraphInNode = this.mockery.mock(ICompoundNode.class, "mockOtherGraphInNode");

		this.mockery.checking(new Expectations(){{
			allowing(mockParent).getGraph(); will(returnValue(mockGraph));
			allowing(mockParent).getParent(); will(returnValue(mockParent));
			allowing(mockParent).getIndex(); will(returnValue(PARENT_IDX));
			allowing(mockParent).getLevel(); will(returnValue(PARENT_LEVEL));

			allowing(mockOutNode).getGraph(); will(returnValue(mockGraph));
			allowing(mockOutNode).addOutEdge(with(any(ICompoundEdge.class)));
			allowing(mockOutNode).getIndex(); will(returnValue(OUT_NODE_IDX));

			allowing(mockInNode).getGraph(); will(returnValue(mockGraph));
			allowing(mockInNode).addInEdge(with(any(ICompoundEdge.class)));
			allowing(mockInNode).getIndex(); will(returnValue(IN_NODE_IDX));
		}});

		this.testInstance = new CompoundEdge(this.mockParent, EXPECTED_EDGE_IDX, this.mockOutNode, this.mockInNode);
		
		this.mockery.checking(new Expectations(){{
			allowing(mockParent).getParent(); will(returnValue(mockParent));
			allowing(mockParent).getRoot(); will(returnValue(mockParent));
			allowing(mockParent).getGraph(); will(returnValue(mockGraph));
			allowing(mockParent).getIndex(); will(returnValue(PARENT_IDX));
			allowing(mockParent).getLevel(); will(returnValue(PARENT_LEVEL));
			
			allowing(mockOutNode).getGraph(); will(returnValue(mockGraph));
			allowing(mockOutNode).addOutEdge(with(any(ICompoundEdge.class)));
			allowing(mockOutNode).getIndex(); will(returnValue(OUT_NODE_IDX));

			allowing(mockInNode).getGraph(); will(returnValue(mockGraph));
			allowing(mockInNode).addInEdge(with(any(ICompoundEdge.class)));
			allowing(mockInNode).getIndex(); will(returnValue(IN_NODE_IDX));

			allowing(mockGraph).getElementTree(); will(returnValue(mockElementTree));

			allowing(mockElementTree).isAncestor(with(equal(testInstance)), with(mockParent)); will(returnValue(true));
			allowing(mockElementTree).isAncestor(with(equal(testInstance)), with(not(mockParent))); will(returnValue(false));
			allowing(mockElementTree).isDescendant(with(any(ICompoundGraphElement.class)), with(any(ICompoundGraphElement.class))); will(returnValue(false));
			
			allowing(mockOtherParent).getParent(); will(returnValue(mockOtherParent));
			allowing(mockOtherParent).getRoot(); will(returnValue(mockOtherParent));
			allowing(mockOtherParent).getGraph(); will(returnValue(mockOtherGraph));
			allowing(mockOtherParent).getIndex(); will(returnValue(PARENT_IDX));
			allowing(mockOtherParent).getLevel(); will(returnValue(PARENT_LEVEL));

			allowing(mockOtherGraphOutNode).getGraph(); will(returnValue(mockOtherGraph));
			allowing(mockOtherGraphOutNode).getIndex(); will(returnValue(OUT_NODE_IDX));

			allowing(mockOtherGraphInNode).getGraph(); will(returnValue(mockOtherGraph));
			allowing(mockOtherGraphInNode).getIndex(); will(returnValue(IN_NODE_IDX));
			
		}});
	}

	@After
	public void tearDown() throws Exception {
		this.mockery = null;
	}

	@Test
	public void testGetConnectedNodes() {
		ICompoundNodePair actualPair = this.testInstance.getConnectedNodes();
		assertNotNull("pair exists", actualPair);
		assertEquals("exepcted in node", actualPair.getInNode(), this.mockInNode);
		assertEquals("exepcted out node", actualPair.getOutNode(), this.mockOutNode);
	}

	@Test
	public void testGetIndex() {
		assertEquals("expected idx", EXPECTED_EDGE_IDX, this.testInstance.getIndex());
	}

	@Test
	public void testHasDirectedEndsICompoundNodeICompoundNode() {
		assertTrue("ends present", this.testInstance.hasDirectedEnds(this.mockOutNode, this.mockInNode));
		assertFalse("ends not present", this.testInstance.hasDirectedEnds(this.mockInNode, this.mockOutNode));
		assertFalse("null ends not present", this.testInstance.hasDirectedEnds(null, null));
		assertFalse("ends present", this.testInstance.hasDirectedEnds(this.mockOtherGraphOutNode, this.mockInNode));
		assertFalse("ends present", this.testInstance.hasDirectedEnds(this.mockOtherGraphOutNode, this.mockOtherGraphInNode));
	}

	@Test
	public void testHasDirectedEndsICompoundNodePair() {
		assertTrue("ends present", this.testInstance.hasDirectedEnds(new CompoundNodePair(this.mockOutNode, this.mockInNode)));
		assertFalse("ends not present", this.testInstance.hasDirectedEnds(new CompoundNodePair(this.mockOtherGraphOutNode, this.mockOtherGraphInNode)));
		assertFalse("null ends not present", this.testInstance.hasDirectedEnds(null));
		assertFalse("reversed directed ends present", this.testInstance.hasDirectedEnds(new CompoundNodePair(this.mockInNode, this.mockOutNode)));
	}

	@Test
	public void testHasEndsICompoundNodeICompoundNode() {
		assertTrue("ends present", this.testInstance.hasEnds(this.mockOutNode, this.mockInNode));
		assertTrue("inveted ends present", this.testInstance.hasEnds(this.mockInNode, this.mockOutNode));
		assertFalse("null ends not present", this.testInstance.hasEnds(null, null));
		assertFalse("ends not present", this.testInstance.hasEnds(this.mockOtherGraphOutNode, this.mockInNode));
		assertFalse("ends not present", this.testInstance.hasEnds(this.mockOtherGraphOutNode, this.mockOtherGraphInNode));
	}

	@Test
	public void testHasEndsICompoundNodePair() {
		assertTrue("ends present", this.testInstance.hasEnds(new CompoundNodePair(this.mockOutNode, this.mockInNode)));
		assertFalse("ends not present", this.testInstance.hasEnds(new CompoundNodePair(this.mockOtherGraphOutNode, this.mockOtherGraphInNode)));
		assertFalse("null ends not present", this.testInstance.hasEnds(null));
		assertTrue("reversed directed ends present", this.testInstance.hasEnds(new CompoundNodePair(this.mockInNode, this.mockOutNode)));
	}

	@Test
	public void testIsSelfEdge() {
		assertFalse("not self edge", this.testInstance.isSelfEdge());
	}

	@Test
	public void testGetChildCompoundGraph() {
		assertNotNull("has compound graph", this.testInstance.getChildCompoundGraph());
	}

	@Test
	public void testGetGraph() {
		assertEquals("expected graph", this.mockGraph, this.testInstance.getGraph());
	}

	@Test
	public void testIsAncestor() {
		assertTrue("is ancestor", this.testInstance.isAncestor(mockParent));
		assertFalse("not ancestor", this.testInstance.isAncestor(mockOtherParent));
		assertFalse("not ancestor", this.testInstance.isAncestor(null));
		assertFalse("not ancestor", this.testInstance.isAncestor(this.testInstance));
		assertFalse("not ancestor", this.testInstance.isAncestor(mockInNode));
	}

	@Test
	public void testIsDescendent() {
		assertFalse("is descendent", this.testInstance.isDescendent(mockParent));
		assertFalse("not descendent", this.testInstance.isDescendent(mockOtherParent));
		assertFalse("not descendent", this.testInstance.isDescendent(null));
		assertFalse("not descendent", this.testInstance.isDescendent(this.testInstance));
		assertFalse("not descendent", this.testInstance.isDescendent(mockInNode));
	}

	@Test
	public void testIsLink() {
		assertTrue("is edge", this.testInstance.isLink());
	}

	@Test
	public void testIsNode() {
		assertFalse("not node", this.testInstance.isNode());
	}

	@Test
	public void testIsRemoved() {
		assertFalse("not removed", this.testInstance.isRemoved());
	}

	@Test
	public void testMarkRemoved() {
		assertFalse("not removed", this.testInstance.isRemoved());
		this.testInstance.markRemoved(true);
		assertTrue("removed", this.testInstance.isRemoved());
	}

	@Test
	public void testCompareTo() {
		assertEquals("expected cmp", 1, this.testInstance.compareTo(mockParent));
		assertEquals("expected cmp", -1, this.testInstance.compareTo(mockInNode));
		assertEquals("expected cmp", 0, this.testInstance.compareTo(testInstance));
	}

	@Test
	public void testAncestorIterator() {
		ICompoundGraphElement expectedresults[] = new ICompoundGraphElement[] { this.testInstance, this.mockParent };
		List<ICompoundGraphElement> actualResults = new LinkedList<ICompoundGraphElement>();
		Iterator<ICompoundGraphElement> iter = this.testInstance.ancestorIterator();
		while(iter.hasNext()){
			ICompoundGraphElement node = iter.next();
			actualResults.add(node);
		}
		assertEquals("expected num ancestor nodes", EXPECTED_NUM_ANCESTOR_NODES, actualResults.size());
		int cntr = 0;
		for(ICompoundGraphElement actualNode : actualResults){
			assertEquals("expectedNodes", expectedresults[cntr++], actualNode);
		}
	}

	@Test
	public void testChildIterator() {
		assertFalse("no children", this.testInstance.childIterator().hasNext());
	}

	@Test
	public void testGetLevel() {
		assertEquals("expected level", EXPECTED_LEVEL, this.testInstance.getLevel());
	}

	@Test
	public void testGetParent() {
		assertEquals("expected parent", this.mockParent, this.testInstance.getParent());
	}

	@Test
	public void testGetRoot() {
		assertEquals("expected root", this.mockParent, this.testInstance.getRoot());
	}

	@Test
	public void testIsChild() {
		assertFalse("not child", this.testInstance.isChild(mockParent));
		assertFalse("null child", this.testInstance.isChild(null));
	}

	@Test
	public void testIsParent() {
		assertFalse("not parent", this.testInstance.isParent(mockInNode));
		assertTrue("parent", this.testInstance.isParent(mockParent));
		assertFalse("not parent", this.testInstance.isParent(mockOtherParent));
		assertFalse("null parent", this.testInstance.isParent(null));
	}

	@Test
	public void testLevelOrderIterator() {
		assertEquals("expected node", this.testInstance, this.testInstance.levelOrderIterator().next());
	}

	@Test
	public void testPreOrderIterator() {
		ICompoundGraphElement expectedresults[] = new ICompoundGraphElement[] { this.testInstance };
		List<ICompoundGraphElement> actualResults = new LinkedList<ICompoundGraphElement>();
		Iterator<ICompoundGraphElement> iter = this.testInstance.preOrderIterator();
		while(iter.hasNext()){
			ICompoundGraphElement node = iter.next();
			actualResults.add(node);
		}
		assertEquals("expected num ancestor nodes", EXPECTED_NUM_PREORDER_NODES, actualResults.size());
		int cntr = 0;
		for(ICompoundGraphElement actualNode : actualResults){
			assertEquals("expectedNodes", expectedresults[cntr++], actualNode);
		}
	}

}
