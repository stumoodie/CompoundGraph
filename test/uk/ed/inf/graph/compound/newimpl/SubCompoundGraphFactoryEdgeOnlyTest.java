package uk.ed.inf.graph.compound.newimpl;

import static org.junit.Assert.*;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.ed.inf.designbycontract.PreConditionException;
import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ed.inf.graph.compound.ISubCompoundGraphFactory;
import uk.ed.inf.graph.compound.testfixture.ComplexGraphFixture;

@RunWith(JMock.class)
public class SubCompoundGraphFactoryEdgeOnlyTest {
	private static final int EXPECTED_NUM_INDUCED_ELEMENTS = 0;
	private static final int EXPECTED_NUM_PERMISSIVE_INDUCED_ELEMENTS = 5;
	private Mockery mockery;
	private ComplexGraphFixture testFixture;
	private ISubCompoundGraphFactory testInstance;

	@Before
	public void setUp() throws Exception {
		this.mockery = new JUnit4Mockery();
		this.testFixture = new ComplexGraphFixture(mockery, "");
		this.testFixture.createElements();
		this.testFixture.buildObjects();
		this.testInstance = new SubCompoundGraphFactory(this.testFixture.getGraph());
		this.testInstance.addElement(this.testFixture.getEdge1());
	}

	@After
	public void tearDown() throws Exception {
		this.mockery = null;
		this.testFixture = null;
		this.testInstance = null;
	}

	@Test(expected=PreConditionException.class)
	public void testSubCompoundGraphFactory() {
		new SubCompoundGraphFactory(null);
	}

	@Test
	public void testAddElement() {
		IteratorTestUtility<ICompoundGraphElement> testIterator = new IteratorTestUtility<ICompoundGraphElement>(this.testFixture.getEdge1());
		testIterator.testIterator(this.testInstance.elementIterator());
	}

	@Test
	public void testCreateInducedSubgraph() {
		ISubCompoundGraph actualSubgraph = testInstance.createInducedSubgraph();
		assertEquals("expected num elements", EXPECTED_NUM_INDUCED_ELEMENTS, actualSubgraph.numElements());
	}

	@Test
	public void testCreatePermissiveInducedSubgraph() {
		ISubCompoundGraph actualSubgraph = testInstance.createPermissiveInducedSubgraph();
		assertEquals("expected num elements", EXPECTED_NUM_PERMISSIVE_INDUCED_ELEMENTS, actualSubgraph.numElements());
	}

	@Test
	public void testCreateSubgraph() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testElementIterator() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetGraph() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testNumElements() {
		fail("Not yet implemented"); // TODO
	}

}
