package uk.ed.inf.graph.compound.newimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.testfixture.ComplexGraphFixture;

@RunWith(JMock.class)
public class CompoundNodeWithSelfEdgeTest {
	private static final int EXPECTED_DEGREE = 2;
	private static final int EXPECTED_IN_DEGREE = 1;
	private static final int EXPECTED_OUT_DEGREE = 1;

	private Mockery mockery = new JUnit4Mockery();
	
	private ComplexGraphFixture testFixture;
	private ICompoundNode testInstance;

	@Before
	public void setUp() throws Exception {
		this.testFixture = new ComplexGraphFixture(this.mockery, ""){
			
			@Override
			protected void buildNode2(final ICompoundNode node){
				node.addInEdge(getEdge3());
				node.addOutEdge(getEdge3());
			}
			
			@Override
			protected void buildNode2ChildGraph(final IChildCompoundGraph child){
				child.addEdge(getEdge3());
			}
			
		};
		this.testFixture.createElements();
		this.testFixture.setBuildDependencies(Arrays.asList(new String[]{"graph", "node1", "node1ChildGraph" }));
		this.testFixture.buildObjects();
		this.testInstance = new CompoundNode(this.testFixture.getNode1(), ComplexGraphFixture.NODE2_IDX);
		this.testFixture.setNode2(testInstance);
		this.testFixture.setNode2ChildGraph(this.testInstance.getChildCompoundGraph());
		this.testFixture.setBuildDependencies(Arrays.asList(new String[]{"elementTree", "node3", "node3ChildGraph", "node4", "node4ChildGraph", "node5", "node5ChildGraph",
				"edge1", "edge1ChildGraph", "edge2", "edge2ChildGraph", "edge3", "edge3ChildGraph",
				"edge4", "edge4ChildGraph", "node2", "node2ChildGraph" }));
		this.testFixture.buildObjects();
	}

	@After
	public void tearDown() throws Exception {
		this.mockery = null;
		this.testFixture = null;
		this.testInstance = null;
	}

	@Test
	public void testGetChildCompoundGraph() {
		assertNotNull("Child exists", this.testInstance.getChildCompoundGraph());
		assertEquals("expected child graph", this.testFixture.getNode2ChildGraph(), this.testInstance.getChildCompoundGraph());
	}

	@Test
	public void testGetParent() {
		assertEquals("Expected parent", this.testFixture.getNode1(), this.testInstance.getParent());
	}

	@Test
	public void testGetRoot() {
		assertEquals("root", this.testFixture.getRootNode(), this.testInstance.getRoot());
	}

	@Test
	public void testConnectedNodeIterator() {
		IteratorTestUtility<ICompoundNode> testIter = new IteratorTestUtility<ICompoundNode>(this.testFixture.getNode2(), this.testFixture.getNode2());
		testIter.testIterator(this.testInstance.connectedNodeIterator());
	}

	@Test
	public void testEdgeIterator() {
		IteratorTestUtility<ICompoundEdge> testIter = new IteratorTestUtility<ICompoundEdge>(this.testFixture.getEdge3(), this.testFixture.getEdge3());
		testIter.testIterator(this.testInstance.edgeIterator());
	}

	@Test
	public void testGetDegree() {
		assertEquals("expected degree", EXPECTED_DEGREE, this.testInstance.getDegree());
	}

	@Test
	public void testGetEdgesWith() {
		IteratorTestUtility<ICompoundEdge> inNodetest = new IteratorTestUtility<ICompoundEdge>(this.testFixture.getEdge3());
		inNodetest.testIterator(this.testInstance.getEdgesWith(this.testInstance));
		IteratorTestUtility<ICompoundEdge> outNodetest = new IteratorTestUtility<ICompoundEdge>(this.testFixture.getEdge3());
		outNodetest.testIterator(this.testInstance.getEdgesWith(this.testInstance));
	}

	@Test
	public void testGetInDegree() {
		assertEquals("expected in degree", EXPECTED_IN_DEGREE, this.testInstance.getInDegree());
	}

	@Test
	public void testGetInEdgeIterator() {
		IteratorTestUtility<ICompoundEdge> testIter = new IteratorTestUtility<ICompoundEdge>(this.testFixture.getEdge3());
		testIter.testIterator(this.testInstance.getInEdgeIterator());
	}

	@Test
	public void testGetInEdgesFrom() {
		IteratorTestUtility<ICompoundEdge> inNodetest = new IteratorTestUtility<ICompoundEdge>(this.testFixture.getEdge3());
		inNodetest.testIterator(this.testInstance.getInEdgesFrom(this.testInstance));
	}

	@Test
	public void testGetInNodeIterator() {
		IteratorTestUtility<ICompoundNode> testIter = new IteratorTestUtility<ICompoundNode>(this.testFixture.getNode2());
		testIter.testIterator(this.testInstance.getInNodeIterator());
	}

	@Test
	public void testGetIndex() {
		assertEquals("expected index", ComplexGraphFixture.NODE2_IDX, this.testInstance.getIndex());
	}

	@Test
	public void testGetOutDegree() {
		assertEquals("expected in degree", EXPECTED_OUT_DEGREE, this.testInstance.getOutDegree());
	}

	@Test
	public void testGetOutEdgeIterator() {
		IteratorTestUtility<ICompoundEdge> testIter = new IteratorTestUtility<ICompoundEdge>(this.testFixture.getEdge3());
		testIter.testIterator(this.testInstance.getOutEdgeIterator());
	}

	@Test
	public void testGetOutEdgesTo() {
		IteratorTestUtility<ICompoundEdge> inNodetest = new IteratorTestUtility<ICompoundEdge>(this.testFixture.getEdge3());
		inNodetest.testIterator(this.testInstance.getOutEdgesTo(this.testInstance));
	}

	@Test
	public void testGetOutNodeIterator() {
		IteratorTestUtility<ICompoundNode> testIter = new IteratorTestUtility<ICompoundNode>(this.testFixture.getNode2());
		testIter.testIterator(this.testInstance.getOutNodeIterator());
	}

	@Test
	public void testHasEdgeWith() {
		assertTrue("contains in edge", this.testInstance.hasEdgeWith(this.testFixture.getNode2()));
	}

	@Test
	public void testHasInEdgeFrom() {
		assertTrue("contains in edge", this.testInstance.hasInEdgeFrom(this.testFixture.getNode2()));
	}

	@Test
	public void testHasOutEdgeTo() {
		assertTrue("contains out edge", this.testInstance.hasOutEdgeTo(this.testFixture.getNode2()));
	}

	@Test
	public void testChildIterator() {
		IteratorTestUtility<ICompoundGraphElement> testIter = new IteratorTestUtility<ICompoundGraphElement>(this.testFixture.getEdge3());
		testIter.testIterator(this.testInstance.childIterator());
	}

	@Test
	public void testIsChild() {
		assertTrue("is child", this.testInstance.isChild(this.testFixture.getEdge3()));
	}

	@Test
	public void testIsDescendent() {
		assertTrue("is descendent", this.testInstance.isDescendent(this.testFixture.getEdge3()));
	}

	@Test
	public void testLevelOrderIterator() {
		IteratorTestUtility<ICompoundGraphElement> testIter = new IteratorTestUtility<ICompoundGraphElement>(this.testInstance, this.testFixture.getEdge3());
		testIter.testIterator(this.testInstance.levelOrderIterator());
	}

	@Test
	public void testPreOrderIterator() {
		IteratorTestUtility<ICompoundGraphElement> testIter = new IteratorTestUtility<ICompoundGraphElement>(this.testInstance, this.testFixture.getEdge3());
		testIter.testIterator(this.testInstance.preOrderIterator());
	}

	@Test
	public void testIsRemoved() {
		assertFalse("not remove", this.testInstance.isRemoved());
	}

	@Test
	public void testContainsEdge() {
		assertTrue("contains edge", this.testInstance.containsEdge(this.testFixture.getEdge3()));
	}

	@Test
	public void testContainsInEdge() {
		assertTrue("contains in edge", this.testInstance.containsInEdge(this.testFixture.getEdge3()));
	}

	@Test
	public void testContainsOutEdge() {
		assertTrue("contains out edge", this.testInstance.containsOutEdge(this.testFixture.getEdge3()));
	}

}
