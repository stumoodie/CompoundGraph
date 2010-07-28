package uk.ed.inf.graph.compound.newimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ed.inf.graph.compound.CompoundNodePair;
import uk.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.ICompoundNodeFactory;
import uk.ed.inf.graph.compound.testfixture.ComplexGraphFixture;
import uk.ed.inf.graph.compound.testfixture.IEdgeConstructor;
import uk.ed.inf.graph.compound.testfixture.INodeConstructor;

@RunWith(JMock.class)
public class SubCompoundGraphWithRemovedNodesTest {
	private static final int EXPECTED_NUM_EDGES = 4;
	private static final int EXPECTED_NUM_NODES = 6;
	private static final int EXPECTED_NUM_TOP_NODES = 1;
	private static final int EXPECTED_NUM_ELEMENTS = 10;
	private static final int EXPECTED_NUM_TOP_ELEMENTS = 1;
	private static final int EXPECTED_NUM_TOP_EDGES = 0;
	private SubCompoundGraph testInstance;
	private Mockery mockery;
	private ComplexGraphFixture testFixture;
	
	@Before
	public void setUp() throws Exception {
		this.mockery = new JUnit4Mockery();
		this.testFixture = new ComplexGraphFixture(this.mockery, "");
		this.testFixture.redefineNode(ComplexGraphFixture.NODE3_ID, new INodeConstructor() {
			
			@Override
			public ICompoundNodeFactory createNodeFactory(IChildCompoundGraph childGraph) {
				return null;
			}
			
			@Override
			public ICompoundNode createCompoundNode() {
				return null;
			}
			
			@Override
			public IChildCompoundGraph createCompoundChildGraph(ICompoundNode node) {
				return null;
			}
			
			@Override
			public boolean buildNodeFactory(ICompoundNodeFactory nodeFactory) {
				return false;
			}
			
			@Override
			public boolean buildNode(final ICompoundNode node) {
				mockery.checking(new Expectations(){{
					allowing(node).isRemoved(); will(returnValue(true));
				}});
				return false;
			}
			
			@Override
			public boolean buildChildGraph(IChildCompoundGraph node) {
				return false;
			}
		});
		this.testFixture.redefineNode(ComplexGraphFixture.NODE4_ID, new INodeConstructor() {
			
			@Override
			public ICompoundNodeFactory createNodeFactory(IChildCompoundGraph childGraph) {
				return null;
			}
			
			@Override
			public ICompoundNode createCompoundNode() {
				return null;
			}
			
			@Override
			public IChildCompoundGraph createCompoundChildGraph(ICompoundNode node) {
				return null;
			}
			
			@Override
			public boolean buildNodeFactory(ICompoundNodeFactory nodeFactory) {
				return false;
			}
			
			@Override
			public boolean buildNode(final ICompoundNode node) {
				mockery.checking(new Expectations(){{
					allowing(node).isRemoved(); will(returnValue(true));
				}});
				return false;
			}
			
			@Override
			public boolean buildChildGraph(IChildCompoundGraph node) {
				return false;
			}
		});
		this.testFixture.redefineNode(ComplexGraphFixture.NODE5_ID, new INodeConstructor() {
			
			@Override
			public ICompoundNodeFactory createNodeFactory(IChildCompoundGraph childGraph) {
				return null;
			}
			
			@Override
			public ICompoundNode createCompoundNode() {
				return null;
			}
			
			@Override
			public IChildCompoundGraph createCompoundChildGraph(ICompoundNode node) {
				return null;
			}
			
			@Override
			public boolean buildNodeFactory(ICompoundNodeFactory nodeFactory) {
				return false;
			}
			
			@Override
			public boolean buildNode(final ICompoundNode node) {
				mockery.checking(new Expectations(){{
					allowing(node).isRemoved(); will(returnValue(true));
				}});
				return false;
			}
			
			@Override
			public boolean buildChildGraph(IChildCompoundGraph node) {
				return false;
			}
		});
		this.testFixture.redefineEdge(ComplexGraphFixture.EDGE1_ID, new IEdgeConstructor() {
			
			@Override
			public ICompoundNodeFactory createNodeFactory(IChildCompoundGraph childGraph) {
				return null;
			}
			
			@Override
			public ICompoundEdgeFactory createEdgeFactory(IChildCompoundGraph childGraph) {
				return null;
			}
			
			@Override
			public ICompoundEdge createCompoundEdge() {
				return null;
			}
			
			@Override
			public IChildCompoundGraph createCompoundChildGraph(ICompoundEdge edge) {
				return null;
			}
			
			@Override
			public boolean buildNodeFactory(ICompoundNodeFactory nodeFactory) {
				return false;
			}
			
			@Override
			public boolean buildEdgeFactory(ICompoundEdgeFactory edgeFactory) {
				return false;
			}
			
			@Override
			public boolean buildEdge(final ICompoundEdge edge) {
				mockery.checking(new Expectations(){{
					allowing(edge).isRemoved(); will(returnValue(true));
				}});
				return false;
			}
			
			@Override
			public boolean buildChildGraph(IChildCompoundGraph childGraph) {
				return false;
			}
		});
		this.testFixture.redefineEdge(ComplexGraphFixture.EDGE2_ID, new IEdgeConstructor() {
			
			@Override
			public ICompoundNodeFactory createNodeFactory(IChildCompoundGraph childGraph) {
				return null;
			}
			
			@Override
			public ICompoundEdgeFactory createEdgeFactory(IChildCompoundGraph childGraph) {
				return null;
			}
			
			@Override
			public ICompoundEdge createCompoundEdge() {
				return null;
			}
			
			@Override
			public IChildCompoundGraph createCompoundChildGraph(ICompoundEdge edge) {
				return null;
			}
			
			@Override
			public boolean buildNodeFactory(ICompoundNodeFactory nodeFactory) {
				return false;
			}
			
			@Override
			public boolean buildEdgeFactory(ICompoundEdgeFactory edgeFactory) {
				return false;
			}
			
			@Override
			public boolean buildEdge(final ICompoundEdge edge) {
				mockery.checking(new Expectations(){{
					allowing(edge).isRemoved(); will(returnValue(true));
				}});
				return false;
			}
			
			@Override
			public boolean buildChildGraph(IChildCompoundGraph childGraph) {
				return false;
			}
		});
		this.testFixture.redefineEdge(ComplexGraphFixture.EDGE3_ID, new IEdgeConstructor() {
			
			@Override
			public ICompoundNodeFactory createNodeFactory(IChildCompoundGraph childGraph) {
				return null;
			}
			
			@Override
			public ICompoundEdgeFactory createEdgeFactory(IChildCompoundGraph childGraph) {
				return null;
			}
			
			@Override
			public ICompoundEdge createCompoundEdge() {
				return null;
			}
			
			@Override
			public IChildCompoundGraph createCompoundChildGraph(ICompoundEdge edge) {
				return null;
			}
			
			@Override
			public boolean buildNodeFactory(ICompoundNodeFactory nodeFactory) {
				return false;
			}
			
			@Override
			public boolean buildEdgeFactory(ICompoundEdgeFactory edgeFactory) {
				return false;
			}
			
			@Override
			public boolean buildEdge(final ICompoundEdge edge) {
				mockery.checking(new Expectations(){{
					allowing(edge).isRemoved(); will(returnValue(true));
				}});
				return false;
			}
			
			@Override
			public boolean buildChildGraph(IChildCompoundGraph childGraph) {
				return false;
			}
		});
//		{
//			
//			@Override
//			protected void buildNode3(final ICompoundNode node){
//				mockery.checking(new Expectations(){{
//					allowing(node).isRemoved(); will(returnValue(true));
//				}});
//				super.buildNode3(node);
//			}
//			
//			@Override
//			protected void buildNode4(final ICompoundNode node){
//				mockery.checking(new Expectations(){{
//					allowing(node).isRemoved(); will(returnValue(true));
//				}});
//				super.buildNode4(node);
//			}
//			
//			@Override
//			protected void buildNode5(final ICompoundNode node){
//				mockery.checking(new Expectations(){{
//					allowing(node).isRemoved(); will(returnValue(true));
//				}});
//				super.buildNode5(node);
//			}
//			
//			@Override
//			protected void buildEdge1(final ICompoundEdge edge){
//				mockery.checking(new Expectations(){{
//					allowing(edge).isRemoved(); will(returnValue(true));
//				}});
//				super.buildEdge1(edge);
//			}
//			
//			@Override
//			protected void buildEdge2(final ICompoundEdge edge){
//				mockery.checking(new Expectations(){{
//					allowing(edge).isRemoved(); will(returnValue(true));
//				}});
//				super.buildEdge2(edge);
//			}
//			
//			@Override
//			protected void buildEdge3(final ICompoundEdge edge){
//				mockery.checking(new Expectations(){{
//					allowing(edge).isRemoved(); will(returnValue(true));
//				}});
//				super.buildEdge3(edge);
//			}
//			
//		};
		this.testFixture.doAll();
//		this.testFixture.createElements();
//		this.testFixture.buildObjects();

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
		CompoundNodePair testOutsidePair = this.testFixture.getEdge1().getConnectedNodes();
		assertTrue("no connection exists", this.testInstance.containsConnection(testOutsidePair.getOutNode(), testOutsidePair.getInNode()));
	}

	@Test
	public void testContainsConnectionCompoundNodePair() {
		CompoundNodePair testPair = this.testFixture.getEdge3().getConnectedNodes();
		assertTrue("connection exists", this.testInstance.containsConnection(testPair));
		CompoundNodePair testOutsidePair = this.testFixture.getEdge1().getConnectedNodes();
		assertTrue("no connection exists", this.testInstance.containsConnection(testOutsidePair));
	}

	@Test
	public void testContainsDirectedEdgeICompoundNodeICompoundNode() {
		CompoundNodePair testPair = this.testFixture.getEdge3().getConnectedNodes();
		assertTrue("directed edge exists", this.testInstance.containsDirectedEdge(testPair.getOutNode(), testPair.getInNode()));
		CompoundNodePair testOutsidePair = this.testFixture.getEdge1().getConnectedNodes();
		assertTrue("no edge exists", this.testInstance.containsDirectedEdge(testOutsidePair.getOutNode(), testOutsidePair.getInNode()));
	}

	@Test
	public void testContainsDirectedEdgeCompoundNodePair() {
		CompoundNodePair testPair = this.testFixture.getEdge3().getConnectedNodes();
		assertTrue("directed edge exists", this.testInstance.containsDirectedEdge(testPair));
		CompoundNodePair testOutsidePair = this.testFixture.getEdge1().getConnectedNodes();
		assertTrue("no edge exists", this.testInstance.containsDirectedEdge(testOutsidePair));
	}

	@Test
	public void testContainsEdgeICompoundEdge() {
		ICompoundEdge testEdge = this.testFixture.getEdge3();
		assertTrue("edge exists", this.testInstance.containsEdge(testEdge));
		ICompoundEdge testOutsideEdge = this.testFixture.getEdge1();
		assertTrue("edge exists", this.testInstance.containsEdge(testOutsideEdge));
	}

	@Test
	public void testContainsEdgeInt() {
		ICompoundEdge testEdge = this.testFixture.getEdge3();
		assertTrue("edge exists", this.testInstance.containsEdge(testEdge.getIndex()));
		ICompoundEdge testOutsideEdge = this.testFixture.getEdge1();
		assertTrue("edge exists", this.testInstance.containsEdge(testOutsideEdge.getIndex()));
	}

	@Test
	public void testContainsElementICompoundGraphElement() {
		assertTrue("element present", this.testInstance.containsElement(this.testFixture.getEdge3()));
		assertTrue("element present", this.testInstance.containsElement(this.testFixture.getNode1()));
		assertTrue("element present", this.testInstance.containsElement(this.testFixture.getEdge1()));
		assertTrue("element present", this.testInstance.containsElement(this.testFixture.getNode5()));
	}

	@Test
	public void testContainsElementInt() {
		assertTrue("element present", this.testInstance.containsElement(this.testFixture.getEdge3().getIndex()));
		assertTrue("element present", this.testInstance.containsElement(this.testFixture.getNode4().getIndex()));
		assertTrue("element present", this.testInstance.containsElement(this.testFixture.getEdge1().getIndex()));
		assertTrue("element present", this.testInstance.containsElement(this.testFixture.getNode5().getIndex()));
	}

	@Test
	public void testContainsNodeInt() {
		assertTrue("node present", this.testInstance.containsNode(this.testFixture.getNode4().getIndex()));
		assertTrue("no node present", this.testInstance.containsNode(this.testFixture.getNode5().getIndex()));
	}

	@Test
	public void testContainsNodeICompoundNode() {
		assertTrue("element present", this.testInstance.containsNode(this.testFixture.getNode4()));
		assertTrue("element present", this.testInstance.containsNode(this.testFixture.getNode2()));
		assertTrue("element present", this.testInstance.containsNode(this.testFixture.getNode1()));
		assertTrue("element present", this.testInstance.containsNode(this.testFixture.getNode5()));
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
		IteratorTestUtility<ICompoundGraphElement> testIter = new IteratorTestUtility<ICompoundGraphElement>(this.testFixture.getRootNode(), this.testFixture.getNode1(),
				this.testFixture.getNode2(), this.testFixture.getNode3(), this.testFixture.getNode4(), this.testFixture.getNode5(), this.testFixture.getEdge1(), this.testFixture.getEdge2(),
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
				this.testFixture.getNode3(), this.testFixture.getNode4(), this.testFixture.getNode5());
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
		IteratorTestUtility<ICompoundGraphElement> testIter = new IteratorTestUtility<ICompoundGraphElement>(this.testFixture.getRootNode(), this.testFixture.getNode1(), this.testFixture.getNode2(), this.testFixture.getNode3(),
				this.testFixture.getNode4(), this.testFixture.getNode5(), this.testFixture.getEdge1(), this.testFixture.getEdge2(), this.testFixture.getEdge3(),
				this.testFixture.getEdge4());
		testIter.testSortedIterator(this.testInstance.edgeLastElementIterator());
	}

}
