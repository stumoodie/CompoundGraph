package uk.ed.inf.graph.compound.newimpl;

import static org.junit.Assert.*;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.hamcrest.Matchers.*;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.ed.inf.designbycontract.PreConditionException;
import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundGraph;
import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.ICompoundNodePair;
import uk.ed.inf.tree.ITree;

@RunWith(JMock.class)
public class CompoundNodeTest {
	private static final int EXPECTED_IDX = 22;

	private static final int EXPECTED_LEVEL = 1;

	private static final int IN_EDGE_OUT_NODE_IDX = 25;
	private static final int OUT_EDGE_IN_NODE_IDX = 26;

	private static final int OUT_EDGE_IN_NODE_LEVEL = 1;

	private static final int IN_EDGE_OUT_NODE_LEVEL = 1;

	private static final int EXPECTED_NUM_CONNECTED_NODES = 2;

	private static final int EXPECTED_DEGREE = 2;
	private static final int OUT_EDGE_IDX = 30;
	private static final int IN_EDGE_IDX = 32;
	private static final int EXPECTED_IN_DEGREE = 1;
	private static final int EXPECTED_OUT_DEGREE = 1;
	private static final int EXPECTED_NUM_IN_EDGES = 1;
	private static final int EXPECTED_NUM_OUT_EDGES = 1;

	private static final int EXPECTED_PARENT_IDX = 0;

	private static final int EXPECTED_PARENT_LEVEL = 0;

	private static final int EXPECTED_NUM_ANCESTOR_NODES = 2;

	private static final int EXPECTED_NUM_PREORDER_NODES = 1;

	private Mockery mockery = new JUnit4Mockery();
	
	private ICompoundNode testInstance;

	private ICompoundGraphElement mockParent;

	private ICompoundGraph mockGraph;

	private ICompoundGraphElement mockNonParent;

	private ICompoundEdge mockInEdge;

	private ICompoundNodePair mockInEdgePair;

//	private ICompoundNode mockInEdgeInNode;

	private ICompoundNode mockInEdgeOutNode;

	private ICompoundEdge mockOutEdge;

	private ICompoundNodePair mockOutEdgePair;

	private ICompoundNode mockOutEdgeInNode;

	private ICompoundEdge mockOtherGraphEdge;

	private ICompoundGraph mockOtherGraph;

	private ICompoundNode mockOtherGraphNode;

	private ITree<ICompoundGraphElement> mockElementTree;

//	private ICompoundNode mockOutEdgeOutNode;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		this.mockOtherGraphEdge = this.mockery.mock(ICompoundEdge.class, "mockOtherGraphEdge");
		this.mockOtherGraph = this.mockery.mock(ICompoundGraph.class, "mockOtherGraph");
		this.mockOtherGraphNode = this.mockery.mock(ICompoundNode.class, "mockOtherGraphNode");
		
		this.mockParent = this.mockery.mock(ICompoundGraphElement.class, "mockParent");
		this.mockNonParent = this.mockery.mock(ICompoundGraphElement.class, "mockNonParent");
		this.mockGraph = this.mockery.mock(ICompoundGraph.class, "mockGraph");
		this.mockInEdge = this.mockery.mock(ICompoundEdge.class, "mockInEdge");
		this.mockInEdgePair = this.mockery.mock(ICompoundNodePair.class, "mockInEdgePair");
//		this.mockInEdgeInNode = this.mockery.mock(ICompoundNode.class, "mockInEdgeInNode");
		this.mockInEdgeOutNode = this.mockery.mock(ICompoundNode.class, "mockInEdgeOutNode");
		this.mockOutEdge = this.mockery.mock(ICompoundEdge.class, "mockOutEdge");
		this.mockOutEdgePair = this.mockery.mock(ICompoundNodePair.class, "mockOutEdgePair");
		this.mockOutEdgeInNode = this.mockery.mock(ICompoundNode.class, "mockOutEdgeInNode");
//		this.mockOutEdgeOutNode = this.mockery.mock(ICompoundNode.class, "mockOutEdgeOutNode");
		this.mockElementTree = this.mockery.mock(ITree.class, "mockElementTree");
		
		this.mockery.checking(new Expectations(){{
			allowing(mockParent).getGraph(); will(returnValue(mockGraph));
			allowing(mockParent).getParent(); will(returnValue(mockParent));
		}});
		this.testInstance = new CompoundNode(this.mockParent, EXPECTED_IDX);
		
		this.mockery.checking(new Expectations(){{
			allowing(mockOtherGraphEdge).getIndex(); will(returnValue(OUT_EDGE_IDX));
			allowing(mockOtherGraphEdge).getGraph(); will(returnValue(mockOtherGraph));
			allowing(mockOtherGraphEdge).compareTo(mockInEdge); will(returnValue(1));
			allowing(mockOtherGraphEdge).compareTo(mockOutEdge); will(returnValue(0));
			allowing(mockOtherGraphEdge).isRemoved(); will(returnValue(false));
			
			allowing(mockOtherGraphNode).getIndex(); will(returnValue(EXPECTED_PARENT_IDX));
			allowing(mockOtherGraphNode).getGraph(); will(returnValue(mockOtherGraph));

			allowing(mockGraph).getElementTree(); will(returnValue(mockElementTree));

			allowing(mockElementTree).isAncestor(testInstance, mockParent); will(returnValue(true));
			allowing(mockElementTree).isAncestor(with(any(ICompoundNode.class)), with(not(mockParent))); will(returnValue(false));
			allowing(mockElementTree).isDescendant(with(any(ICompoundNode.class)), with(any(ICompoundGraphElement.class))); will(returnValue(false));
			
			allowing(mockParent).getGraph(); will(returnValue(mockGraph));
			allowing(mockParent).getParent(); will(returnValue(mockParent));
			allowing(mockParent).getRoot(); will(returnValue(mockParent));
			allowing(mockParent).getIndex(); will(returnValue(EXPECTED_PARENT_IDX));
			allowing(mockParent).getLevel(); will(returnValue(EXPECTED_PARENT_LEVEL));
		
			allowing(mockInEdge).getGraph(); will(returnValue(mockGraph));
			allowing(mockInEdge).getConnectedNodes(); will(returnValue(mockInEdgePair));
			allowing(mockInEdge).isRemoved(); will(returnValue(false));
			allowing(mockInEdge).getIndex(); will(returnValue(IN_EDGE_IDX));
			allowing(mockInEdge).compareTo(mockOutEdge); will(returnValue(1));
			allowing(mockInEdge).compareTo(mockOtherGraphEdge); will(returnValue(1));
			allowing(mockInEdge).compareTo(mockInEdge); will(returnValue(0));
			
			allowing(mockInEdgePair).getInNode(); will(returnValue(testInstance));
			allowing(mockInEdgePair).getOutNode(); will(returnValue(mockInEdgeOutNode));
			allowing(mockInEdgePair).getOtherNode(testInstance); will(returnValue(mockInEdgeOutNode));
			allowing(mockInEdgePair).getOtherNode(mockInEdgeOutNode); will(returnValue(testInstance));
			allowing(mockInEdgePair).containsNode(mockInEdgeOutNode); will(returnValue(true));
			allowing(mockInEdgePair).containsNode(with(not(mockInEdgeOutNode))); will(returnValue(false));
			
			allowing(mockInEdgeOutNode).getIndex(); will(returnValue(IN_EDGE_OUT_NODE_IDX));
			allowing(mockInEdgeOutNode).getLevel(); will(returnValue(IN_EDGE_OUT_NODE_LEVEL));
			
			allowing(mockOutEdge).getGraph(); will(returnValue(mockGraph));
			allowing(mockOutEdge).getConnectedNodes(); will(returnValue(mockOutEdgePair));
			allowing(mockOutEdge).isRemoved(); will(returnValue(false));
			allowing(mockOutEdge).getIndex(); will(returnValue(OUT_EDGE_IDX));
			allowing(mockOutEdge).compareTo(mockInEdge); will(returnValue(-1));
			allowing(mockOutEdge).compareTo(mockOutEdge); will(returnValue(0));
			allowing(mockOutEdge).compareTo(mockOtherGraphEdge); will(returnValue(0));
			
			allowing(mockOutEdgePair).getInNode(); will(returnValue(mockOutEdgeInNode));
			allowing(mockOutEdgePair).getOutNode(); will(returnValue(testInstance));
			allowing(mockOutEdgePair).getOtherNode(mockOutEdgeInNode); will(returnValue(testInstance));
			allowing(mockOutEdgePair).getOtherNode(testInstance); will(returnValue(mockOutEdgeInNode));
			allowing(mockOutEdgePair).containsNode(mockOutEdgeInNode); will(returnValue(true));
			allowing(mockOutEdgePair).containsNode(with(not(mockOutEdgeInNode))); will(returnValue(false));
			
			allowing(mockOutEdgeInNode).getIndex(); will(returnValue(OUT_EDGE_IN_NODE_IDX));
			allowing(mockOutEdgeInNode).getLevel(); will(returnValue(OUT_EDGE_IN_NODE_LEVEL));
		}});
		
		this.testInstance.addInEdge(this.mockInEdge);
		this.testInstance.addOutEdge(this.mockOutEdge);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetGraph() {
		assertEquals("expected graph", this.mockGraph, this.testInstance.getGraph());
	}

	@Test
	public void testGetChildCompoundGraph() {
		assertNotNull("Child exists", this.testInstance.getChildCompoundGraph());
	}

	@Test
	public void testGetLevel() {
		assertEquals("Expected level", EXPECTED_LEVEL, this.testInstance.getLevel());
	}

	@Test
	public void testGetParent() {
		assertEquals("Expected parent", this.mockParent, this.testInstance.getParent());
	}

	@Test
	public void testIsParent() {
		assertTrue("isparent", this.testInstance.isParent(this.mockParent));
		assertFalse("is not parent", this.testInstance.isParent(this.mockNonParent));
		assertFalse("null parent", this.testInstance.isParent(null));
	}

	@Test
	public void testGetRoot() {
		assertEquals("root", this.mockParent, this.testInstance.getRoot());
	}

	@Test
	public void testConnectedNodeIterator() {
		ICompoundNode expectedresults[] = new ICompoundNode[] { this.mockInEdgeOutNode, this.mockOutEdgeInNode };
		SortedSet<ICompoundNode> sortedSet = new TreeSet<ICompoundNode>(new Comparator<ICompoundNode>(){

			@Override
			public int compare(ICompoundNode o1, ICompoundNode o2) {
				return (o1.getIndex() < o2.getIndex() ? -1 : (o1.getIndex() > o2.getIndex() ? 1 : 0));
			}
			
		});
		Iterator<ICompoundNode> iter = this.testInstance.connectedNodeIterator();
		while(iter.hasNext()){
			ICompoundNode node = iter.next();
			sortedSet.add(node);
		}
		assertEquals("expected num connected nodes", EXPECTED_NUM_CONNECTED_NODES, sortedSet.size());
		int cntr = 0;
		for(ICompoundNode actualNode : sortedSet){
			assertEquals("expectedNodes", expectedresults[cntr++], actualNode);
		}
	}

	@Test
	public void testEdgeIterator() {
		ICompoundEdge expectedresults[] = new ICompoundEdge[] { this.mockOutEdge, this.mockInEdge };
		SortedSet<ICompoundEdge> sortedSet = new TreeSet<ICompoundEdge>(new Comparator<ICompoundEdge>(){

			@Override
			public int compare(ICompoundEdge o1, ICompoundEdge o2) {
				return (o1.getIndex() < o2.getIndex() ? -1 : (o1.getIndex() > o2.getIndex() ? 1 : 0));
			}
			
		});
		Iterator<ICompoundEdge> iter = this.testInstance.edgeIterator();
		while(iter.hasNext()){
			ICompoundEdge node = iter.next();
			sortedSet.add(node);
		}
		assertEquals("expected num connected nodes", EXPECTED_NUM_CONNECTED_NODES, sortedSet.size());
		int cntr = 0;
		for(ICompoundEdge actualNode : sortedSet){
			assertEquals("expectedNodes", expectedresults[cntr++], actualNode);
		}
	}

	@Test
	public void testGetDegree() {
		assertEquals("expected degree", EXPECTED_DEGREE, this.testInstance.getDegree());
	}

	@Test
	public void testGetEdgesWith() {
		assertEquals("expected in edge", this.mockInEdge, this.testInstance.getEdgesWith(this.mockInEdgeOutNode).first());
		assertEquals("expected out edge", this.mockOutEdge, this.testInstance.getEdgesWith(this.mockOutEdgeInNode).first());
	}

	@Test
	public void testGetInDegree() {
		assertEquals("expected in degree", EXPECTED_IN_DEGREE, this.testInstance.getInDegree());
	}

	@Test
	public void testGetInEdgeIterator() {
		ICompoundEdge expectedresults[] = new ICompoundEdge[] { this.mockInEdge };
		SortedSet<ICompoundEdge> sortedSet = new TreeSet<ICompoundEdge>(new Comparator<ICompoundEdge>(){

			@Override
			public int compare(ICompoundEdge o1, ICompoundEdge o2) {
				return (o1.getIndex() < o2.getIndex() ? -1 : (o1.getIndex() > o2.getIndex() ? 1 : 0));
			}
			
		});
		Iterator<ICompoundEdge> iter = this.testInstance.getInEdgeIterator();
		while(iter.hasNext()){
			ICompoundEdge node = iter.next();
			sortedSet.add(node);
		}
		assertEquals("expected num connected nodes", EXPECTED_NUM_IN_EDGES, sortedSet.size());
		int cntr = 0;
		for(ICompoundEdge actualEdge : sortedSet){
			assertEquals("expectedEdge", expectedresults[cntr++], actualEdge);
		}
	}

	@Test
	public void testGetInEdgesFrom() {
		assertEquals("expected in edge", this.mockInEdge, this.testInstance.getInEdgesFrom(this.mockInEdgeOutNode).first());
	}

	@Test(expected=PreConditionException.class)
	public void testGetInEdgesFromWithNonOutNode() {
		this.testInstance.getInEdgesFrom(this.mockOutEdgeInNode);
	}

	@Test(expected=PreConditionException.class)
	public void testGetInEdgesFromWithNull() {
		this.testInstance.getInEdgesFrom(null);
	}

	@Test
	public void testGetInNodeIterator() {
		ICompoundNode expectedresults[] = new ICompoundNode[] { this.mockOutEdgeInNode };
		SortedSet<ICompoundNode> sortedSet = new TreeSet<ICompoundNode>(new Comparator<ICompoundNode>(){

			@Override
			public int compare(ICompoundNode o1, ICompoundNode o2) {
				return (o1.getIndex() < o2.getIndex() ? -1 : (o1.getIndex() > o2.getIndex() ? 1 : 0));
			}
			
		});
		Iterator<ICompoundNode> iter = this.testInstance.getInNodeIterator();
		while(iter.hasNext()){
			ICompoundNode node = iter.next();
			sortedSet.add(node);
		}
		assertEquals("expected num connected nodes", EXPECTED_NUM_IN_EDGES, sortedSet.size());
		int cntr = 0;
		for(ICompoundNode actualNode : sortedSet){
			assertEquals("expectedNodes", expectedresults[cntr++], actualNode);
		}
	}

	@Test
	public void testGetIndex() {
		assertEquals("expected index", EXPECTED_IDX, this.testInstance.getIndex());
	}

	@Test
	public void testGetOutDegree() {
		assertEquals("expected in degree", EXPECTED_OUT_DEGREE, this.testInstance.getOutDegree());
	}

	@Test
	public void testGetOutEdgeIterator() {
		ICompoundEdge expectedresults[] = new ICompoundEdge[] { this.mockOutEdge };
		SortedSet<ICompoundEdge> sortedSet = new TreeSet<ICompoundEdge>(new Comparator<ICompoundEdge>(){

			@Override
			public int compare(ICompoundEdge o1, ICompoundEdge o2) {
				return (o1.getIndex() < o2.getIndex() ? -1 : (o1.getIndex() > o2.getIndex() ? 1 : 0));
			}
			
		});
		Iterator<ICompoundEdge> iter = this.testInstance.getOutEdgeIterator();
		while(iter.hasNext()){
			ICompoundEdge node = iter.next();
			sortedSet.add(node);
		}
		assertEquals("expected num connected nodes", EXPECTED_NUM_OUT_EDGES, sortedSet.size());
		int cntr = 0;
		for(ICompoundEdge actualEdge : sortedSet){
			assertEquals("expectedEdge", expectedresults[cntr++], actualEdge);
		}
	}

	@Test
	public void testGetOutEdgesTo() {
		assertEquals("expected out edge", this.mockOutEdge, this.testInstance.getOutEdgesTo(this.mockOutEdgeInNode).first());
	}

	@Test(expected=PreConditionException.class)
	public void testGetOutEdgesToWithNonOutNode() {
		this.testInstance.getOutEdgesTo(this.mockInEdgeOutNode);
	}

	@Test(expected=PreConditionException.class)
	public void testGetOutEdgesToWithNull() {
		this.testInstance.getOutEdgesTo(null);
	}

	@Test
	public void testGetOutNodeIterator() {
		ICompoundNode expectedresults[] = new ICompoundNode[] { this.mockInEdgeOutNode };
		SortedSet<ICompoundNode> sortedSet = new TreeSet<ICompoundNode>(new Comparator<ICompoundNode>(){

			@Override
			public int compare(ICompoundNode o1, ICompoundNode o2) {
				return (o1.getIndex() < o2.getIndex() ? -1 : (o1.getIndex() > o2.getIndex() ? 1 : 0));
			}
			
		});
		Iterator<ICompoundNode> iter = this.testInstance.getOutNodeIterator();
		while(iter.hasNext()){
			ICompoundNode node = iter.next();
			sortedSet.add(node);
		}
		assertEquals("expected num connected nodes", EXPECTED_NUM_OUT_EDGES, sortedSet.size());
		int cntr = 0;
		for(ICompoundNode actualNode : sortedSet){
			assertEquals("expectedNodes", expectedresults[cntr++], actualNode);
		}
	}

	@Test
	public void testHasEdgeWith() {
		assertTrue("contains in edge", this.testInstance.hasEdgeWith(this.mockInEdgeOutNode));
		assertTrue("contains out edge", this.testInstance.hasEdgeWith(this.mockOutEdgeInNode));
		assertFalse("does not contains null", this.testInstance.hasEdgeWith(null));
		assertFalse("does not contains other edge", this.testInstance.hasEdgeWith(this.mockOtherGraphNode));
	}

	@Test
	public void testHasInEdgeFrom() {
		assertTrue("contains in edge", this.testInstance.hasInEdgeFrom(this.mockInEdgeOutNode));
		assertFalse("not contains out edge", this.testInstance.hasInEdgeFrom(this.mockOutEdgeInNode));
		assertFalse("does not contains null", this.testInstance.hasInEdgeFrom(null));
		assertFalse("does not contains other edge", this.testInstance.hasInEdgeFrom(this.mockOtherGraphNode));
	}

	@Test
	public void testHasOutEdgeTo() {
		assertFalse("not contains in edge", this.testInstance.hasOutEdgeTo(this.mockInEdgeOutNode));
		assertTrue("contains out edge", this.testInstance.hasOutEdgeTo(this.mockOutEdgeInNode));
		assertFalse("does not contains null", this.testInstance.hasOutEdgeTo(null));
		assertFalse("does not contains other edge", this.testInstance.hasOutEdgeTo(this.mockOtherGraphNode));
	}

	@Test
	public void testIsAncestor() {
		assertTrue("is ancestor", this.testInstance.isAncestor(mockParent));
		assertFalse("not ancestor", this.testInstance.isAncestor(null));
		assertFalse("not ancestor", this.testInstance.isAncestor(this.testInstance));
		assertFalse("not ancestor", this.testInstance.isAncestor(this.mockOtherGraphNode));
	}

	@Test
	public void testIsDescendent() {
		assertFalse("not is ancestor", this.testInstance.isDescendent(mockParent));
		assertFalse("not ancestor", this.testInstance.isDescendent(null));
		assertFalse("not ancestor", this.testInstance.isDescendent(this.testInstance));
		assertFalse("not ancestor", this.testInstance.isDescendent(this.mockOtherGraphNode));
	}

	@Test
	public void testIsLink() {
		assertFalse("not link", this.testInstance.isLink());
	}

	@Test
	public void testIsNode() {
		assertTrue("is node", this.testInstance.isNode());
	}

	@Test
	public void testCompareTo() {
		assertEquals("less than", -1, this.testInstance.compareTo(mockOutEdgeInNode));
		assertEquals("gt than", 1, this.testInstance.compareTo(this.mockParent));
		assertEquals("equal", 0, this.testInstance.compareTo(this.testInstance));
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
	public void testIsChild() {
		assertFalse("not child", this.testInstance.isChild(mockParent));
		assertFalse("not child", this.testInstance.isChild(null));
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

	@Test
	public void testIsRemoved() {
		assertFalse("not remove", this.testInstance.isRemoved());
	}

	@Test
	public void testContainsEdge() {
		assertTrue("contains edge", this.testInstance.containsEdge(mockInEdge));
		assertTrue("contains edge", this.testInstance.containsEdge(mockOutEdge));
		assertFalse("not contains edge", this.testInstance.containsEdge(null));
		assertFalse("not contains edge", this.testInstance.containsEdge(this.mockOtherGraphEdge));
	}

	@Test
	public void testContainsInEdge() {
		assertTrue("contains in edge", this.testInstance.containsInEdge(mockInEdge));
		assertFalse("not contains in edge", this.testInstance.containsInEdge(mockOutEdge));
		assertFalse("not contains in edge", this.testInstance.containsInEdge(null));
		assertFalse("not contains in edge", this.testInstance.containsInEdge(this.mockOtherGraphEdge));
	}

	@Test
	public void testContainsOutEdge() {
		assertFalse("not contains out edge", this.testInstance.containsOutEdge(mockInEdge));
		assertTrue("contains out edge", this.testInstance.containsOutEdge(mockOutEdge));
		assertFalse("not contains out edge", this.testInstance.containsOutEdge(null));
		assertFalse("not contains out edge", this.testInstance.containsOutEdge(this.mockOtherGraphEdge));
	}

}
