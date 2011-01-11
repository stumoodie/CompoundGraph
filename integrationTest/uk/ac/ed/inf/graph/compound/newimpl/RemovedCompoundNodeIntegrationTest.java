package uk.ac.ed.inf.graph.compound.newimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ac.ed.inf.designbycontract.PreConditionException;
import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElementVisitor;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.testfixture.ComplexGraphFixture;
import uk.ac.ed.inf.graph.compound.testfixture.ElementAttribute;
import uk.ac.ed.inf.graph.compound.testfixture.IGraphTestFixture;
import uk.ac.ed.inf.graph.compound.testfixture.IteratorTestUtility;

public class RemovedCompoundNodeIntegrationTest {
	private static final int EXPECTED_LEVEL = 2;
	private static final int EXPECTED_DEGREE = 0;
	private static final int EXPECTED_IN_DEGREE = 0;
	private static final int EXPECTED_OUT_DEGREE = 0;
//	private static final int EXPECTED_NUM_IN_EDGES = 0;

	private IGraphTestFixture testFixture;
	private ICompoundNode testInstance;
	private ICompoundGraphElement node1;
//	private ICompoundGraphElement mockNonParent;
//	private ICompoundEdge mockInEdge;
//	private ICompoundNode mockInEdgeOutNode;
//	private ICompoundEdge mockOutEdge;
//	private ICompoundNode mockOutEdgeInNode;
//	private ICompoundEdge mockOtherGraphEdge;
//	private ICompoundNode mockOtherGraphNode;
//	private IGraphTestFixture otherTestFixture;
	private ElementAttribute expectedAttribute;
	private ICompoundEdge edge4;
	private ICompoundEdge edge3;
	private ICompoundNode node3;
	private boolean visitNodeCalled;

	@Before
	public void setUp() throws Exception {
		this.expectedAttribute = new ElementAttribute(ComplexGraphFixture.NODE2_ID);
		this.testFixture = new IntegrationTestGraphFixture();
		this.testFixture.buildFixture();
		this.testInstance = this.testFixture.getNode(IntegrationTestGraphFixture.NODE2_ID);
		this.testInstance.markRemoved(true);
		this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE3_ID).markRemoved(true);
		this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE4_ID).markRemoved(true);

//		this.mockNonParent = this.testFixture.getRootNode();
		this.node1 = this.testFixture.getNode(IntegrationTestGraphFixture.NODE1_ID);
//		this.mockOutEdge = this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE4_ID);
//		this.mockOutEdgeInNode = this.testFixture.getNode(IntegrationTestGraphFixture.NODE2_ID);
		
//		this.otherTestFixture = new IntegrationTestGraphFixture();
//		this.otherTestFixture.buildFixture();
		
//		this.mockOtherGraphEdge = this.otherTestFixture.getEdge(IntegrationTestGraphFixture.EDGE4_ID);
//		this.mockOtherGraphNode = this.otherTestFixture.getNode(IntegrationTestGraphFixture.NODE1_ID);
		this.edge4 = this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE4_ID);
		this.edge3 = this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE3_ID);
		this.node3 = this.testFixture.getNode(IntegrationTestGraphFixture.NODE3_ID);
	}

	@After
	public void tearDown() throws Exception {
		this.testInstance = null;
		this.testFixture = null;
	}

	@Test
	public void testGetGraph() {
		assertEquals("expected graph", this.testFixture.getGraph(), this.testInstance.getGraph());
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
		assertEquals("Expected parent", this.node1, this.testInstance.getParent());
	}

	@Test
	public void testIsParent() {
		assertTrue("isparent", this.testInstance.isParent(this.node1));
	}

	@Test
	public void testGetRoot() {
		assertEquals("root", this.testFixture.getRootNode(), this.testInstance.getRoot());
	}

	@Test
	public void testConnectedNodeIterator() {
		IteratorTestUtility<ICompoundNode> testIter = new IteratorTestUtility<ICompoundNode>();
		testIter.testSortedIterator(this.testInstance.connectedNodeIterator());
	}

	@Test
	public void testEdgeIterator() {
		IteratorTestUtility<ICompoundEdge> testIter = new IteratorTestUtility<ICompoundEdge>();
		testIter.testSortedIterator(this.testInstance.edgeIterator());
	}

	@Test
	public void testGetDegree() {
		assertEquals("expected degree", EXPECTED_DEGREE, this.testInstance.getDegree());
	}

	@Test(expected=PreConditionException.class)
	public void testGetEdgesWith() {
		IteratorTestUtility<ICompoundEdge> outNodetest = new IteratorTestUtility<ICompoundEdge>();
		outNodetest.testIterator(this.testInstance.getEdgesWith(this.node3));
	}

	@Test(expected=PreConditionException.class)
	public void testGetEdgesWithNull(){
		this.testInstance.getEdgesWith(null);
	}
	
	@Test(expected=PreConditionException.class)
	public void testGetEdgesWithNonIncidentNode(){
		this.testInstance.getEdgesWith(this.testFixture.getRootNode());
	}
	
	@Test
	public void testMarkRemovedTrue(){
		this.testInstance.markRemoved(true);
		assertTrue("node removed", this.testInstance.isRemoved());
	}
	
	@Test
	public void testGetInDegree() {
		assertEquals("expected in degree", EXPECTED_IN_DEGREE, this.testInstance.getInDegree());
	}

	@Test
	public void testGetInEdgeIterator() {
		IteratorTestUtility<ICompoundEdge> testIter = new IteratorTestUtility<ICompoundEdge>();
		testIter.testIterator(this.testInstance.getInEdgeIterator());
	}

	@Test
	public void testOutEdgeIncidentNodesIterator() {
		IteratorTestUtility<ICompoundNode> testIter = new IteratorTestUtility<ICompoundNode>();
		testIter.testIterator(this.testInstance.outEdgeIncidentNodesIterator());
	}

	@Test
	public void testGetOutDegree() {
		assertEquals("expected in degree", EXPECTED_OUT_DEGREE, this.testInstance.getOutDegree());
	}

	@Test
	public void testGetOutEdgeIterator() {
		IteratorTestUtility<ICompoundEdge> testIter = new IteratorTestUtility<ICompoundEdge>();
		testIter.testSortedIterator(this.testInstance.getOutEdgeIterator());
//		ICompoundEdge expectedresults[] = new ICompoundEdge[] { this.mockOutEdge };
//		SortedSet<ICompoundEdge> sortedSet = new TreeSet<ICompoundEdge>(new Comparator<ICompoundEdge>(){
//
//			@Override
//			public int compare(ICompoundEdge o1, ICompoundEdge o2) {
//				return (o1.getIndex() < o2.getIndex() ? -1 : (o1.getIndex() > o2.getIndex() ? 1 : 0));
//			}
//			
//		});
//		Iterator<ICompoundEdge> iter = this.testInstance.getOutEdgeIterator();
//		while(iter.hasNext()){
//			ICompoundEdge node = iter.next();
//			sortedSet.add(node);
//		}
//		assertEquals("expected num connected nodes", EXPECTED_NUM_OUT_EDGES, sortedSet.size());
//		int cntr = 0;
//		for(ICompoundEdge actualEdge : sortedSet){
//			assertEquals("expectedEdge", expectedresults[cntr++], actualEdge);
//		}
	}

	@Test(expected=PreConditionException.class)
	public void testGetOutEdgesTo() {
		IteratorTestUtility<ICompoundEdge> testIter = new IteratorTestUtility<ICompoundEdge>();
		testIter.testSortedIterator(this.testInstance.getOutEdgesTo(node3));
	}

	@Test
	public void testinEdgeIncidentNodesIterator() {
		IteratorTestUtility<ICompoundNode> testIter = new IteratorTestUtility<ICompoundNode>();
		testIter.testSortedIterator(this.testInstance.inEdgeIncidentNodesIterator());
	}

	@Test
	public void testHasEdgeWith() {
		assertFalse("contains no edge", this.testInstance.hasEdgeWith(this.node3));
		assertFalse("contains no edge", this.testInstance.hasEdgeWith(this.testInstance));
	}

	@Test
	public void testHasInEdgeFrom() {
		assertFalse("not contains edge", this.testInstance.hasInEdgeFrom(this.testInstance));
	}

	@Test
	public void testHasOutEdgeTo() {
		assertFalse("not contains in edge", this.testInstance.hasOutEdgeTo(this.node3));
		assertFalse("contains no edge", this.testInstance.hasOutEdgeTo(this.testInstance));
	}

	@Test
	public void testIsAncestor() {
		assertTrue("is ancestor", this.testInstance.isAncestor(node1));
		assertTrue("is ancestor", this.testInstance.isAncestor(testFixture.getRootNode()));
	}

	@Test
	public void testIsDescendent() {
		assertFalse("edge3 is ancestor", this.testInstance.isDescendent(this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE3_ID)));
	}

	@Test
	public void testIsLink() {
		assertFalse("not link", this.testInstance.isEdge());
	}

	@Test
	public void testIsNode() {
		assertTrue("is node", this.testInstance.isNode());
	}

	@Test
	public void testCompareTo() {
		assertEquals("less than", -1, this.testInstance.compareTo(node3));
		assertEquals("gt than", 1, this.testInstance.compareTo(node1));
		assertEquals("equal", 0, this.testInstance.compareTo(this.testInstance));
	}

	@Test
	public void testAncestorIterator() {
		IteratorTestUtility<ICompoundGraphElement> testIter = new IteratorTestUtility<ICompoundGraphElement>(this.node1, this.testFixture.getRootNode());
		testIter.testIterator(this.testInstance.ancestorIterator());
	}

	@Test
	public void testChildIterator() {
		IteratorTestUtility<ICompoundGraphElement> testIter = new IteratorTestUtility<ICompoundGraphElement>();
		testIter.testIterator(this.testInstance.childIterator());
	}

	@Test
	public void testIsChild() {
		assertFalse("is child", this.testInstance.isChild(edge3));
	}

	@Test
	public void testLevelOrderIterator() {
		IteratorTestUtility<ICompoundGraphElement> testIter = new IteratorTestUtility<ICompoundGraphElement>(this.testInstance);
		testIter.testIterator(this.testInstance.levelOrderIterator());
	}

	@Test
	public void testPreOrderIterator() {
		IteratorTestUtility<ICompoundGraphElement> testIter = new IteratorTestUtility<ICompoundGraphElement>(this.testInstance);
		testIter.testIterator(this.testInstance.preOrderIterator());
	}

	@Test
	public void testIsRemoved() {
		assertTrue("removed", this.testInstance.isRemoved());
	}

	@Test
	public void testContainsEdge() {
		assertFalse("contains no edge", this.testInstance.containsEdge(edge3));
		assertFalse("contains no edge", this.testInstance.containsEdge(edge4));
	}

	@Test
	public void testContainsInEdge() {
		assertFalse("contains in edge", this.testInstance.containsInEdge(edge3));
	}

	@Test
	public void testContainsOutEdge() {
		assertFalse("contains out edge", this.testInstance.containsOutEdge(edge3));
		assertFalse("contains out edge", this.testInstance.containsOutEdge(edge4));
	}


	@Test
	public void testUnfilteredEdgeIterator() {
		IteratorTestUtility<ICompoundEdge> testIter = new IteratorTestUtility<ICompoundEdge>(edge3, edge4);
		testIter.testSortedIterator(this.testInstance.unfilteredEdgeIterator());
	}


	@Test(expected=PreConditionException.class)
	public void testGetInEdgesFrom() {
		this.testInstance.getInEdgesFrom(this.testInstance);
	}


	@Test
	public void testInEdgeIncidentNodesIterator() {
		IteratorTestUtility<ICompoundNode> testIter = new IteratorTestUtility<ICompoundNode>();
		testIter.testSortedIterator(this.testInstance.inEdgeIncidentNodesIterator());
	}


	@Test
	public void testIsLowestCommonAncestor() {
		assertTrue("is lca of itself", this.testInstance.isLowestCommonAncestor(testInstance, testInstance));
		assertFalse("is lca of itself", this.testInstance.isLowestCommonAncestor(testInstance, node1));
		assertFalse("is lca of itself", this.testInstance.isLowestCommonAncestor(null, null));
	}

	@Test
	public void testVisit() {
		visitNodeCalled = false;
		this.testInstance.visit(new ICompoundGraphElementVisitor() {
			
			@Override
			public void visitNode(ICompoundNode node) {
				visitNodeCalled = true;
			}
			
			@Override
			public void visitEdge(ICompoundEdge edge) {
				visitNodeCalled = false;
			}
		});
		assertTrue("visited node", this.visitNodeCalled);
	}

}
