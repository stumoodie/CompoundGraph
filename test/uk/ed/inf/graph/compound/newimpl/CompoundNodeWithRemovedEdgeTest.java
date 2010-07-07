package uk.ed.inf.graph.compound.newimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.testfixture.ComplexGraphFixture;

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
		this.testFixture = new ComplexGraphFixture(this.mockery, ""){
			
			@Override
			protected void buildNode3(final ICompoundNode node){
				node.addInEdge(getEdge4());
				node.addOutEdge(getEdge2());
			}

			@Override
			protected void buildEdge2(final ICompoundEdge edge){
				mockery.checking(new Expectations(){{
					allowing(edge).isRemoved(); will(returnValue(true));
				}});
				super.buildEdge2(edge);
			}

			@Override
			protected void buildNode4(final ICompoundNode node){
				mockery.checking(new Expectations(){{
					allowing(node).isRemoved(); will(returnValue(true));
				}});
				super.buildNode4(node);
			}
		};
		this.testFixture.createElements();
		this.testFixture.setBuildDependencies(Arrays.asList(new String[]{"graph", "edge1"}));
		this.testFixture.buildObjects();
		this.testInstance = new CompoundNode(this.testFixture.getEdge1(), ComplexGraphFixture.NODE3_IDX);
		this.testFixture.setNode3(testInstance);
		this.testFixture.setBuildDependencies(Arrays.asList(new String[]{"elementTree", "node1", "node2", "node4", "node5",
				"edge1", "edge2", "edge3", "edge4", "node3" }));
		this.testFixture.buildObjects();
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
		IteratorTestUtility<ICompoundNode> testIter = new IteratorTestUtility<ICompoundNode>(this.testFixture.getNode1());
		testIter.testIterator(this.testInstance.connectedNodeIterator());
	}

	@Test
	public void testEdgeIterator() {
		IteratorTestUtility<ICompoundEdge> testIter = new IteratorTestUtility<ICompoundEdge>(this.testFixture.getEdge4());
		testIter.testIterator(this.testInstance.edgeIterator());
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
	public void testGetInNodeIterator() {
		IteratorTestUtility<ICompoundNode> testIter = new IteratorTestUtility<ICompoundNode>();
		testIter.testIterator(this.testInstance.getInNodeIterator());
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
	public void testGetOutNodeIterator() {
		IteratorTestUtility<ICompoundNode> testIter = new IteratorTestUtility<ICompoundNode>(this.testFixture.getNode1());
		testIter.testIterator(this.testInstance.getOutNodeIterator());
	}

	@Test
	public void testHasEdgeWith() {
		assertTrue("contains in edge", this.testInstance.hasEdgeWith(this.testFixture.getNode1()));
		assertFalse("does not contains other edge", this.testInstance.hasEdgeWith(this.testFixture.getNode5()));
	}

	@Test
	public void testHasInEdgeFrom() {
		assertTrue("contains in edge", this.testInstance.hasInEdgeFrom(this.testFixture.getNode1()));
	}

	@Test
	public void testHasOutEdgeTo() {
		assertFalse("not contains in edge", this.testInstance.hasOutEdgeTo(this.testFixture.getNode5()));
	}

	@Test
	public void testAncestorIterator() {
		IteratorTestUtility<ICompoundGraphElement> testIter = new IteratorTestUtility<ICompoundGraphElement>(this.testInstance, this.testFixture.getEdge1(), this.testFixture.getRootNode());
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
	}

	@Test
	public void testContainsInEdge() {
		assertTrue("contains in edge", this.testInstance.containsInEdge(this.testFixture.getEdge4()));
	}

	@Test
	public void testContainsOutEdge() {
		assertFalse("not contains out edge", this.testInstance.containsOutEdge(this.testFixture.getEdge2()));
	}

}
