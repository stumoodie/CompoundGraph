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

import uk.ac.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundChildEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.ICompoundNodeFactory;
import uk.ac.ed.inf.graph.compound.newimpl.CompoundNode;
import uk.ac.ed.inf.graph.compound.testfixture.ComplexGraphFixture;
import uk.ac.ed.inf.graph.compound.testfixture.INodeConstructor;
import uk.ac.ed.inf.graph.compound.testfixture.IteratorTestUtility;

@RunWith(JMock.class)
public class CompoundNodeWithRemovedEdgeTest {
	private static final int EXPECTED_DEGREE = 1;
	private static final int EXPECTED_IN_DEGREE = 1;
	private static final int EXPECTED_OUT_DEGREE = 0;

	private Mockery mockery = new JUnit4Mockery();
	
	private ComplexGraphFixture testFixture;
	private ICompoundNode testInstance;

	@Before
	public void setUp() throws Exception {
		this.testFixture = new ComplexGraphFixture(this.mockery, "");
		this.testFixture.redefineNode(ComplexGraphFixture.NODE3_ID, new INodeConstructor() {
			
			@Override
			public ICompoundNodeFactory createNodeFactory(IChildCompoundGraph childGraph) {
				return childGraph.nodeFactory();
			}
			
			@Override
			public ICompoundNode createCompoundNode() {
				testInstance = new CompoundNode(testFixture.getEdge1(), ComplexGraphFixture.NODE3_IDX);
				return testInstance;
			}
			
			@Override
			public IChildCompoundGraph createCompoundChildGraph(ICompoundNode node) {
				return node.getChildCompoundGraph();
			}
			
			@Override
			public boolean buildNodeFactory(ICompoundNodeFactory nodeFactory) {
				return true;
			}
			
			@Override
			public boolean buildNode(ICompoundNode node) {
				node.addInEdge(testFixture.getEdge4());
				node.addOutEdge(testFixture.getEdge2());
				return true;
			}
			
			@Override
			public boolean buildChildGraph(IChildCompoundGraph node) {
				return true;
			}

			@Override
			public boolean buildEdgeFactory(ICompoundEdgeFactory edgeFactory) {
				return false;
			}

			@Override
			public ICompoundChildEdgeFactory createEdgeFactory(IChildCompoundGraph childGraph) {
				return null;
			}
		});
		this.testFixture.setElementRemoved(ComplexGraphFixture.EDGE2_ID, true);
		this.testFixture.setElementRemoved(ComplexGraphFixture.NODE4_ID, true);
		this.testFixture.buildFixture();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetParent() {
		assertEquals("Expected parent", this.testFixture.getEdge1(), this.testInstance.getParent());
	}

	@Test
	public void testConnectedNodeIterator() {
		IteratorTestUtility<ICompoundNode> testIter = new IteratorTestUtility<ICompoundNode>(this.testFixture.getNode2());
		testIter.testIterator(this.testInstance.connectedNodeIterator());
	}

	@Test
	public void testEdgeIterator() {
		IteratorTestUtility<ICompoundEdge> testIter = new IteratorTestUtility<ICompoundEdge>(this.testFixture.getEdge4());
		testIter.testSortedIterator(this.testInstance.edgeIterator());
	}

	@Test
	public void testGetDegree() {
		assertEquals("expected degree", EXPECTED_DEGREE, this.testInstance.getDegree());
	}

	@Test
	public void testGetInDegree() {
		assertEquals("expected in degree", EXPECTED_IN_DEGREE, this.testInstance.getInDegree());
	}

	@Test
	public void testGetInEdgeIterator() {
		IteratorTestUtility<ICompoundEdge> testIter = new IteratorTestUtility<ICompoundEdge>(this.testFixture.getEdge4());
		testIter.testIterator(this.testInstance.getInEdgeIterator());
	}

	@Test
	public void testOutEdgeIncidentNodesIterator() {
		IteratorTestUtility<ICompoundNode> testIter = new IteratorTestUtility<ICompoundNode>();
		testIter.testIterator(this.testInstance.outEdgeIncidentNodesIterator());
	}

	@Test
	public void testGetIndex() {
		assertEquals("expected index", ComplexGraphFixture.NODE3_IDX, this.testInstance.getIndex());
	}

	@Test
	public void testGetOutDegree() {
		assertEquals("expected in degree", EXPECTED_OUT_DEGREE, this.testInstance.getOutDegree());
	}

	@Test
	public void testGetOutEdgeIterator() {
		IteratorTestUtility<ICompoundEdge> testIter = new IteratorTestUtility<ICompoundEdge>();
		testIter.testIterator(this.testInstance.getOutEdgeIterator());
	}

	@Test
	public void testInEdgeIncidentNodesIterator() {
		IteratorTestUtility<ICompoundNode> testIter = new IteratorTestUtility<ICompoundNode>(this.testFixture.getNode2());
		testIter.testIterator(this.testInstance.inEdgeIncidentNodesIterator());
	}

	@Test
	public void testHasEdgeWith() {
		assertTrue("contains in edge", this.testInstance.hasEdgeWith(this.testFixture.getNode2()));
		assertFalse("not contains removed edge", this.testInstance.hasEdgeWith(this.testFixture.getNode5()));
		assertFalse("does not contains other edge", this.testInstance.hasEdgeWith(this.testFixture.getNode4()));
	}

	@Test
	public void testHasInEdgeFrom() {
		assertTrue("contains in edge", this.testInstance.hasInEdgeFrom(this.testFixture.getNode2()));
		assertFalse("does not contains other edge", this.testInstance.hasEdgeWith(this.testFixture.getNode5()));
	}

	@Test
	public void testHasOutEdgeTo() {
		assertFalse("contains out edge", this.testInstance.hasOutEdgeTo(this.testFixture.getNode5()));
		assertFalse("not contains out edge", this.testInstance.hasOutEdgeTo(this.testFixture.getNode1()));
		assertFalse("not contains out edge", this.testInstance.hasOutEdgeTo(this.testFixture.getNode4()));
	}

	@Test
	public void testAncestorIterator() {
		IteratorTestUtility<ICompoundGraphElement> testIter = new IteratorTestUtility<ICompoundGraphElement>(this.testFixture.getEdge1(), this.testFixture.getRootNode());
		testIter.testIterator(this.testInstance.ancestorIterator());
	}

	@Test
	public void testChildIterator() {
		IteratorTestUtility<ICompoundGraphElement> testIter = new IteratorTestUtility<ICompoundGraphElement>();
		testIter.testIterator(this.testInstance.childIterator());
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
		assertFalse("not remove", this.testInstance.isRemoved());
	}

	@Test
	public void testContainsEdge() {
		assertTrue("contains edge", this.testInstance.containsEdge(this.testFixture.getEdge4()));
		assertFalse("not contains edge", this.testInstance.containsEdge(this.testFixture.getEdge2()));
		assertFalse("not contains edge", this.testInstance.containsEdge(this.testFixture.getEdge3()));
	}

	@Test
	public void testContainsInEdge() {
		assertTrue("contains in edge", this.testInstance.containsInEdge(this.testFixture.getEdge4()));
	}

	@Test
	public void testContainsOutEdge() {
		assertFalse("contains out edge", this.testInstance.containsOutEdge(this.testFixture.getEdge4()));
		assertFalse("not contains out edge", this.testInstance.containsOutEdge(this.testFixture.getEdge2()));
		assertFalse("not contains out edge", this.testInstance.containsOutEdge(this.testFixture.getEdge3()));
	}

}
