package uk.ac.ed.inf.graph.compound.newimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.ed.inf.designbycontract.PreConditionException;
import uk.ac.ed.inf.graph.compound.CompoundNodePair;
import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.testfixture.ComplexGraphFixture;
import uk.ac.ed.inf.graph.compound.testfixture.IteratorTestUtility;

@RunWith(JMock.class)
public class SubGraphTest {
	private static final int INVALID_VALUE = -99;
	private static final int EXPECTED_NUM_EDGES = 2;
	private static final int EXPECTED_NUM_NODES = 3;
	private static final int EXPECTED_NUM_ELEMENTS = 5;
	private SubGraph testInstance;
	private Mockery mockery;
	private ComplexGraphFixture testFixture;
	private ComplexGraphFixture otherTestFixture;
	
	@Before
	public void setUp() throws Exception {
		this.mockery = new JUnit4Mockery();
		this.testFixture = new ComplexGraphFixture(this.mockery, "");
		this.testFixture.buildFixture();
		this.otherTestFixture = new ComplexGraphFixture(this.mockery, "other_");
		this.otherTestFixture.buildFixture();

		this.testInstance = new SubGraph(this.testFixture.getGraph());
		this.testInstance.addElement(this.testFixture.getEdge2());
		this.testInstance.addElement(this.testFixture.getEdge3());
		this.testInstance.addElement(this.testFixture.getNode1());
		this.testInstance.addElement(this.testFixture.getNode2());
		this.testInstance.addElement(this.testFixture.getNode4());
	}

	@After
	public void tearDown() throws Exception {
		this.mockery =null;
		this.testFixture = null;
		this.testInstance = null;
	}

	@Test
	public void testContainsConnectionICompoundNodeICompoundNode() {
		CompoundNodePair testPair = this.testFixture.getEdge2().getConnectedNodes();
		assertTrue("connection exists", this.testInstance.containsConnection(testPair.getOutNode(), testPair.getInNode()));
		assertTrue("connection exists", this.testInstance.containsConnection(testPair.getInNode(), testPair.getOutNode()));
		assertFalse("no connection exists", this.testInstance.containsConnection(testPair.getInNode(), testPair.getInNode()));
		assertFalse("no connection exists", this.testInstance.containsConnection(null, null));
		CompoundNodePair testOutsidePair = this.testFixture.getEdge4().getConnectedNodes();
		assertFalse("no connection exists", this.testInstance.containsConnection(testOutsidePair.getOutNode(), testOutsidePair.getInNode()));
		assertFalse("no connection exists", this.testInstance.containsConnection(testOutsidePair.getInNode(), testOutsidePair.getOutNode()));
		CompoundNodePair testOtherGraphPair = this.otherTestFixture.getEdge2().getConnectedNodes();
		assertFalse("no connection exists", this.testInstance.containsConnection(testOtherGraphPair.getInNode(), testOtherGraphPair.getOutNode()));
		assertFalse("no connection exists", this.testInstance.containsConnection(testOtherGraphPair.getOutNode(), testOtherGraphPair.getInNode()));
	}

	@Test
	public void testContainsConnectionCompoundNodePair() {
		CompoundNodePair testPair = this.testFixture.getEdge2().getConnectedNodes();
		assertTrue("connection exists", this.testInstance.containsConnection(testPair));
		assertTrue("connection exists", this.testInstance.containsConnection(testPair.reversedNodes()));
		assertFalse("no connection exists", this.testInstance.containsConnection(null));
		CompoundNodePair testOutsidePair = this.testFixture.getEdge4().getConnectedNodes();
		assertFalse("no connection exists", this.testInstance.containsConnection(testOutsidePair));
		CompoundNodePair testOtherGraphPair = this.otherTestFixture.getEdge2().getConnectedNodes();
		assertFalse("no connection exists", this.testInstance.containsConnection(testOtherGraphPair));
	}

	@Test
	public void testContainsDirectedEdgeICompoundNodeICompoundNode() {
		CompoundNodePair testPair = this.testFixture.getEdge2().getConnectedNodes();
		assertTrue("directed edge exists", this.testInstance.containsDirectedEdge(testPair.getOutNode(), testPair.getInNode()));
		assertFalse("no directed edge exists", this.testInstance.containsDirectedEdge(testPair.getInNode(), testPair.getOutNode()));
		assertFalse("no edge exists", this.testInstance.containsDirectedEdge(testPair.getInNode(), testPair.getInNode()));
		assertFalse("no edge exists", this.testInstance.containsDirectedEdge(null, null));
		CompoundNodePair testOutsidePair = this.testFixture.getEdge4().getConnectedNodes();
		assertFalse("no edge exists", this.testInstance.containsDirectedEdge(testOutsidePair.getOutNode(), testOutsidePair.getInNode()));
		assertFalse("no edge exists", this.testInstance.containsDirectedEdge(testOutsidePair.getInNode(), testOutsidePair.getOutNode()));
		CompoundNodePair testOtherGraphPair = this.otherTestFixture.getEdge2().getConnectedNodes();
		assertFalse("no edge exists", this.testInstance.containsDirectedEdge(testOtherGraphPair.getInNode(), testOtherGraphPair.getOutNode()));
		assertFalse("no edge exists", this.testInstance.containsDirectedEdge(testOtherGraphPair.getOutNode(), testOtherGraphPair.getInNode()));
	}

	@Test
	public void testContainsDirectedEdgeCompoundNodePair() {
		CompoundNodePair testPair = this.testFixture.getEdge2().getConnectedNodes();
		assertTrue("directed edge exists", this.testInstance.containsDirectedEdge(testPair));
		assertFalse("no edge exists", this.testInstance.containsDirectedEdge(null));
		CompoundNodePair testOutsidePair = this.testFixture.getEdge4().getConnectedNodes();
		assertFalse("no edge exists", this.testInstance.containsDirectedEdge(testOutsidePair));
		CompoundNodePair testOtherGraphPair = this.otherTestFixture.getEdge2().getConnectedNodes();
		assertFalse("no edge exists", this.testInstance.containsDirectedEdge(testOtherGraphPair));
	}

	@Test
	public void testContainsEdgeICompoundEdge() {
		ICompoundEdge testEdge = this.testFixture.getEdge2();
		assertTrue("edge exists", this.testInstance.containsEdge(testEdge));
		assertFalse("no edge exists", this.testInstance.containsEdge(null));
		ICompoundEdge testOutsideEdge = this.testFixture.getEdge4();
		assertFalse("no edge exists", this.testInstance.containsEdge(testOutsideEdge));
		ICompoundEdge testOtherGraphEdge = this.otherTestFixture.getEdge2();
		assertFalse("no edge exists", this.testInstance.containsEdge(testOtherGraphEdge));
	}

	@Test
	public void testContainsEdgeInt() {
		ICompoundEdge testEdge = this.testFixture.getEdge2();
		assertTrue("edge exists", this.testInstance.containsEdge(testEdge.getIndex()));
		assertFalse("no edge exists", this.testInstance.containsEdge(INVALID_VALUE));
		assertFalse("no edge exists", this.testInstance.containsEdge(this.testFixture.getNode1().getIndex()));
		ICompoundEdge testOutsideEdge = this.testFixture.getEdge4();
		assertFalse("no edge exists", this.testInstance.containsEdge(testOutsideEdge.getIndex()));
	}

	@Test
	public void testContainsElementICompoundGraphElement() {
		assertTrue("element present", this.testInstance.containsElement(this.testFixture.getEdge3()));
		assertTrue("element present", this.testInstance.containsElement(this.testFixture.getNode4()));
		assertFalse("no element present", this.testInstance.containsElement(this.testFixture.getEdge1()));
		assertFalse("no element present", this.testInstance.containsElement(this.testFixture.getNode5()));
		assertFalse("no element present", this.testInstance.containsElement(this.otherTestFixture.getEdge3()));
		assertFalse("no element present", this.testInstance.containsElement(this.otherTestFixture.getNode4()));
		assertFalse("no element", this.testInstance.containsElement(null));
	}

	@Test
	public void testContainsElementInt() {
		assertTrue("element present", this.testInstance.containsElement(this.testFixture.getEdge3().getIndex()));
		assertTrue("element present", this.testInstance.containsElement(this.testFixture.getNode4().getIndex()));
		assertFalse("no element present", this.testInstance.containsElement(this.testFixture.getEdge1().getIndex()));
		assertFalse("no element present", this.testInstance.containsElement(this.testFixture.getNode5().getIndex()));
		assertFalse("no element present", this.testInstance.containsElement(INVALID_VALUE));
	}

	@Test
	public void testContainsNodeInt() {
		assertFalse("no node present", this.testInstance.containsNode(this.testFixture.getEdge3().getIndex()));
		assertTrue("node present", this.testInstance.containsNode(this.testFixture.getNode4().getIndex()));
		assertFalse("no node present", this.testInstance.containsNode(this.testFixture.getEdge1().getIndex()));
		assertFalse("no node present", this.testInstance.containsNode(this.testFixture.getNode5().getIndex()));
		assertFalse("no node present", this.testInstance.containsNode(INVALID_VALUE));
	}

	@Test
	public void testContainsNodeICompoundNode() {
		assertTrue("element present", this.testInstance.containsNode(this.testFixture.getNode4()));
		assertTrue("element present", this.testInstance.containsNode(this.testFixture.getNode2()));
		assertTrue("element present", this.testInstance.containsNode(this.testFixture.getNode1()));
		assertFalse("no element present", this.testInstance.containsNode(this.testFixture.getNode5()));
		assertFalse("no element present", this.testInstance.containsNode(this.otherTestFixture.getNode4()));
		assertFalse("no element", this.testInstance.containsNode(null));
	}

	@Test
	public void testEdgeIterator() {
		IteratorTestUtility<ICompoundEdge> testIter = new IteratorTestUtility<ICompoundEdge>(this.testFixture.getEdge2(), this.testFixture.getEdge3());
		testIter.testSortedIterator(this.testInstance.edgeIterator());
	}

	@Test
	public void testElementIterator() {
		IteratorTestUtility<ICompoundGraphElement> testIter = new IteratorTestUtility<ICompoundGraphElement>(this.testFixture.getNode1(), this.testFixture.getNode2(), this.testFixture.getNode4(),
				this.testFixture.getEdge2(), this.testFixture.getEdge3());
		testIter.testSortedIterator(this.testInstance.elementIterator());
	}

	@Test
	public void testGetEdge() {
		assertEquals("expected edge", this.testFixture.getEdge2(), this.testInstance.getEdge(this.testFixture.getEdge2().getIndex()));
	}

	@Test
	public void testGetEdgeOutsideEdge() {
		assertNull("no edge found", this.testInstance.getEdge(this.testFixture.getEdge1().getIndex()));
	}

	@Test
	public void testGetElement() {
		assertEquals("expected edge", this.testFixture.getEdge2(), this.testInstance.getElement(this.testFixture.getEdge2().getIndex()));
		assertEquals("expected node", this.testFixture.getNode4(), this.testInstance.getElement(this.testFixture.getNode4().getIndex()));
	}

	@Test(expected=PreConditionException.class)
	public void testGetElementOutsideEdge() {
		this.testInstance.getElement(this.testFixture.getEdge1().getIndex());
	}

	@Test
	public void testGetNode() {
		assertEquals("expected node", this.testFixture.getNode4(), this.testInstance.getNode(this.testFixture.getNode4().getIndex()));
	}

	@Test
	public void testGetNodeOutsideEdge() {
		assertNull("no node", this.testInstance.getNode(this.testFixture.getNode5().getIndex()));
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
	public void testGetSuperGraph() {
		assertEquals("expected graph", this.testFixture.getGraph(), this.testInstance.getSuperGraph());
		assertFalse("not expected graph", this.otherTestFixture.getGraph().equals(this.testInstance.getSuperGraph()));
	}

	@Test
	public void testIsConsistentSnapShot() {
		assertTrue("consistent snapshot", this.testInstance.isConsistentSnapShot());
	}

	@Test
	public void testIsInducedSubgraph() {
		assertFalse("not induced subgraph", this.testInstance.isInducedSubgraph());
	}

	@Test
	public void testNodeIterator() {
		IteratorTestUtility<ICompoundNode> testIter = new IteratorTestUtility<ICompoundNode>(this.testFixture.getNode1(), this.testFixture.getNode2(), this.testFixture.getNode4());
		testIter.testSortedIterator(this.testInstance.nodeIterator());
	}

	@Test
	public void testNumElements() {
		assertEquals("expected num", EXPECTED_NUM_ELEMENTS, this.testInstance.numElements());
	}

	@Test
	public void testAddElements() {
		this.testInstance.addElement(this.testFixture.getEdge4());
		assertEquals("expected num", EXPECTED_NUM_ELEMENTS+1, this.testInstance.numElements());
		assertEquals("expected num", EXPECTED_NUM_NODES, this.testInstance.getNumNodes());
		assertEquals("expected num", EXPECTED_NUM_EDGES+1, this.testInstance.getNumEdges());
		assertTrue("contains edge", this.testInstance.containsEdge(this.testFixture.getEdge4()));
		assertTrue("contains edge", this.testInstance.containsEdge(this.testFixture.getEdge4().getIndex()));
		IteratorTestUtility<ICompoundGraphElement> testIter = new IteratorTestUtility<ICompoundGraphElement>(this.testFixture.getNode1(), this.testFixture.getNode2(), this.testFixture.getNode4(),
					this.testFixture.getEdge2(), this.testFixture.getEdge3(), this.testFixture.getEdge4());
		testIter.testSortedIterator(this.testInstance.elementIterator());
	}

}
