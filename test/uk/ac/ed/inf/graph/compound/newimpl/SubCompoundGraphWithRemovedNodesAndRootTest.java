package uk.ac.ed.inf.graph.compound.newimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.ed.inf.graph.compound.CompoundNodePair;
import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.testfixture.ComplexGraphFixture;
import uk.ac.ed.inf.graph.compound.testfixture.IteratorTestUtility;

@RunWith(JMock.class)
public class SubCompoundGraphWithRemovedNodesAndRootTest {
	private static final int EXPECTED_NUM_EDGES = 4;
	private static final int EXPECTED_NUM_NODES = 7;
	private static final int EXPECTED_NUM_TOP_NODES = 1;
	private static final int EXPECTED_NUM_ELEMENTS = 11;
	private static final int EXPECTED_NUM_TOP_ELEMENTS = 1;
	private static final int EXPECTED_NUM_TOP_EDGES = 0;
	private SubCompoundGraph testInstance;
	private Mockery mockery;
	private ComplexGraphFixture testFixture;
	
	@Before
	public void setUp() throws Exception {
		this.mockery = new JUnit4Mockery();
		this.testFixture = new ComplexGraphFixture(this.mockery, "");
		this.testFixture.setElementRemoved(ComplexGraphFixture.NODE2_ID, true);
		this.testFixture.setElementRemoved(ComplexGraphFixture.EDGE2_ID, true);
		this.testFixture.setElementRemoved(ComplexGraphFixture.EDGE3_ID, true);
		this.testFixture.setElementRemoved(ComplexGraphFixture.NODE4_ID, true);
		this.testFixture.buildFixture();

		this.testInstance = new SubCompoundGraph(this.testFixture.getGraph());
		this.testInstance.addTopElement(this.testFixture.getRootNode());
	}

	@After
	public void tearDown() throws Exception {
		this.mockery =null;
		this.testFixture = null;
		this.testInstance = null;
	}

	@Test
	public void testContainsConnectionICompoundNodeICompoundNode() {
		CompoundNodePair testPair = this.testFixture.getEdge3().getConnectedNodes();
		assertTrue("connection exists", this.testInstance.containsConnection(testPair.getOutNode(), testPair.getInNode()));
	}

	@Test
	public void testContainsConnectionCompoundNodePair() {
		CompoundNodePair testPair = this.testFixture.getEdge2().getConnectedNodes();
		assertTrue("connection exists", this.testInstance.containsConnection(testPair));
		CompoundNodePair testOutsidePair = this.testFixture.getEdge2().getConnectedNodes();
		assertTrue("no connection exists", this.testInstance.containsConnection(testOutsidePair));
	}

	@Test
	public void testContainsDirectedEdgeICompoundNodeICompoundNode() {
		CompoundNodePair testPair = this.testFixture.getEdge2().getConnectedNodes();
		assertTrue("directed edge exists", this.testInstance.containsDirectedEdge(testPair.getOutNode(), testPair.getInNode()));
		CompoundNodePair reversedPair = this.testFixture.getEdge2().getConnectedNodes().reversedNodes();
		assertFalse("reversed directed edge not exists", this.testInstance.containsDirectedEdge(reversedPair.getOutNode(), testPair.getInNode()));
	}

	@Test
	public void testContainsConnectionRemovedCompoundNodePair() {
		CompoundNodePair testPair = this.testFixture.getEdge3().getConnectedNodes();
		assertTrue("connection exists", this.testInstance.containsConnection(testPair));
	}

	@Test
	public void testContainsDirectedEdgeRemovedICompoundNodeICompoundNode() {
		CompoundNodePair testPair = this.testFixture.getEdge3().getConnectedNodes();
		assertTrue("directed edge exists", this.testInstance.containsDirectedEdge(testPair.getOutNode(), testPair.getInNode()));
	}

	@Test
	public void testContainsDirectedEdgeCompoundNodePair() {
		CompoundNodePair testPair = this.testFixture.getEdge3().getConnectedNodes();
		assertTrue("directed edge exists", this.testInstance.containsDirectedEdge(testPair));
	}

	@Test
	public void testContainsEdgeICompoundEdge() {
		ICompoundEdge testEdge = this.testFixture.getEdge3();
		assertTrue("edge exists", this.testInstance.containsEdge(testEdge));
	}

	@Test
	public void testContainsEdgeInt() {
		ICompoundEdge testEdge = this.testFixture.getEdge3();
		assertTrue("edge exists", this.testInstance.containsEdge(testEdge.getIndex()));
	}

	@Test
	public void testContainsElementICompoundGraphElement() {
		assertTrue("element present", this.testInstance.containsElement(this.testFixture.getRootNode()));
		assertTrue("element present", this.testInstance.containsElement(this.testFixture.getEdge3()));
		assertTrue("element present", this.testInstance.containsElement(this.testFixture.getEdge2()));
		assertTrue("element present", this.testInstance.containsElement(this.testFixture.getNode1()));
	}

	@Test
	public void testContainsElementInt() {
		assertTrue("element present", this.testInstance.containsElement(this.testFixture.getRootNode().getIndex()));
		assertTrue("element present", this.testInstance.containsElement(this.testFixture.getEdge3().getIndex()));
		assertTrue("element present", this.testInstance.containsElement(this.testFixture.getNode4().getIndex()));
		assertTrue("element present", this.testInstance.containsElement(this.testFixture.getNode1().getIndex()));
	}

	@Test
	public void testContainsNodeInt() {
		assertTrue("node present", this.testInstance.containsNode(this.testFixture.getNode4().getIndex()));
		assertTrue("root node present", this.testInstance.containsNode(this.testFixture.getRootNode().getIndex()));
	}

	@Test
	public void testContainsNodeICompoundNode() {
		assertTrue("root node present", this.testInstance.containsNode(this.testFixture.getRootNode()));
		assertTrue("element not present", this.testInstance.containsNode(this.testFixture.getNode4()));
		assertTrue("element present", this.testInstance.containsNode(this.testFixture.getNode2()));
		assertTrue("element present", this.testInstance.containsNode(this.testFixture.getNode1()));
	}

	@Test
	public void testContainsRoot() {
		assertTrue("contains root", this.testInstance.containsRoot());
	}

	@Test
	public void testEdgeIterator() {
		IteratorTestUtility<ICompoundEdge> testIter = new IteratorTestUtility<ICompoundEdge>(this.testFixture.getEdge1(), this.testFixture.getEdge2(),
				this.testFixture.getEdge3(), this.testFixture.getEdge4());
		testIter.testSortedIterator(this.testInstance.edgeIterator());
	}

	@Test
	public void testElementIterator() {
		IteratorTestUtility<ICompoundGraphElement> testIter = new IteratorTestUtility<ICompoundGraphElement>(this.testFixture.getRootNode(), this.testFixture.getNode1(), this.testFixture.getNode2(),
				this.testFixture.getNode3(), this.testFixture.getNode4(), this.testFixture.getNode5(), this.testFixture.getNode6(),
				this.testFixture.getEdge1(), this.testFixture.getEdge2(),
				this.testFixture.getEdge3(), this.testFixture.getEdge4());
		testIter.testSortedIterator(this.testInstance.elementIterator());
	}

	@Test
	public void testGetEdge() {
		assertEquals("expected edge", this.testFixture.getEdge2(), this.testInstance.getEdge(this.testFixture.getEdge2().getIndex()));
	}

	@Test
	public void testGetElement() {
		assertEquals("expected edge", this.testFixture.getEdge2(), this.testInstance.getElement(this.testFixture.getEdge2().getIndex()));
		assertEquals("expected node", this.testFixture.getNode4(), this.testInstance.getElement(this.testFixture.getNode4().getIndex()));
	}

	@Test
	public void testGetNode() {
		assertEquals("expected node", this.testFixture.getNode4(), this.testInstance.getNode(this.testFixture.getNode4().getIndex()));
	}

	@Test
	public void testGetNumEdges() {
		assertEquals("expected num", EXPECTED_NUM_EDGES, this.testInstance.getNumEdges());
	}

	@Test
	public void testGetNumNodes() {
		assertEquals("expected num", EXPECTED_NUM_NODES, this.testInstance.getNumNodes());
	}

	@Test
	public void testGetNumTopNodes() {
		assertEquals("expected num", EXPECTED_NUM_TOP_NODES, this.testInstance.getNumTopNodes());
	}

	@Test
	public void testGetNumTopEdges() {
		assertEquals("expected num", EXPECTED_NUM_TOP_EDGES, this.testInstance.getNumTopEdges());
	}

	@Test
	public void testGetSuperGraph() {
		assertEquals("expected graph", this.testFixture.getGraph(), this.testInstance.getSuperGraph());
	}

	@Test
	public void testIsConsistentSnapShot() {
		assertFalse("inconsistent snapshot", this.testInstance.isConsistentSnapShot());
	}

	@Test
	public void testIsInducedSubgraph() {
		assertTrue("is induced subgraph", this.testInstance.isInducedSubgraph());
	}

	@Test
	public void testNodeIterator() {
		IteratorTestUtility<ICompoundNode> testIter = new IteratorTestUtility<ICompoundNode>(this.testFixture.getRootNode(), this.testFixture.getNode1(), this.testFixture.getNode2(),
				this.testFixture.getNode3(), this.testFixture.getNode4(), this.testFixture.getNode5(), this.testFixture.getNode6());
		testIter.testSortedIterator(this.testInstance.nodeIterator());
	}

	@Test
	public void testNumElements() {
		assertEquals("expected num", EXPECTED_NUM_ELEMENTS, this.testInstance.numElements());
	}

	@Test
	public void testNumTopElements() {
		assertEquals("expected num", EXPECTED_NUM_TOP_ELEMENTS, this.testInstance.numTopElements());
	}

	@Test
	public void testTopElementIterator() {
		IteratorTestUtility<ICompoundGraphElement> testIter = new IteratorTestUtility<ICompoundGraphElement>(this.testFixture.getRootNode());
		testIter.testSortedIterator(this.testInstance.topElementIterator());
	}

	@Test
	public void testTopNodeIterator() {
		IteratorTestUtility<ICompoundNode> testIter = new IteratorTestUtility<ICompoundNode>(this.testFixture.getRootNode());
		testIter.testSortedIterator(this.testInstance.topNodeIterator());
	}

	@Test
	public void testTopEdgeIterator() {
		IteratorTestUtility<ICompoundEdge> testIter = new IteratorTestUtility<ICompoundEdge>();
		testIter.testSortedIterator(this.testInstance.topEdgeIterator());
	}

	@Test
	public void testEdgeLastElementIterator() {
		IteratorTestUtility<ICompoundGraphElement> testIter = new IteratorTestUtility<ICompoundGraphElement>(this.testFixture.getRootNode(), this.testFixture.getNode1(), this.testFixture.getNode2(),
				this.testFixture.getEdge3(), this.testFixture.getNode6(), this.testFixture.getEdge1(), this.testFixture.getNode3(), this.testFixture.getNode5(), this.testFixture.getEdge2(), this.testFixture.getNode4(),
				this.testFixture.getEdge4());
		testIter.testIterator(this.testInstance.edgeLastElementIterator());
	}

}
