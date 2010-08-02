package uk.ed.inf.graph.compound.newimpl;

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

import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ed.inf.graph.compound.ISubCompoundGraphFactory;
import uk.ed.inf.graph.compound.testfixture.ComplexGraphFixture;
import uk.ed.inf.graph.compound.testfixture.IteratorTestUtility;

@RunWith(JMock.class)
public class SubCompoundGraphFactoryEdgeOnlyTest {
	private static final int EXPECTED_TOP_SUBGRAPH_ELEMENTS = 1;
	private static final int EXPECTED_NUM_SUBGRAPH_ELEMENTS = 5;
	private static final int EXPECTED_TOP_PERMSSIVE_INDUCED_SUBGRAPH_ELEMENTS = 1;
	private static final int EXPECTED_PERMSSIVE_INDUCED_SUBGRAPH_ELEMENTS = 5;
	private static final int EXPECTED_TOP_INDUCED_SUBGRAPH_ELEMENTS = 0;
	private static final int EXPECTED_INDUCED_SUBGRAPH_ELEMENTS = 0;

	private Mockery mockery;
	private ComplexGraphFixture testFixture;
	private ISubCompoundGraphFactory testSubCompoundGraphFactory;

	@Before
	public void setUp() throws Exception {
		this.mockery = new JUnit4Mockery();
		this.testFixture = new ComplexGraphFixture(mockery, "");
		this.testFixture.doAll();
		this.testSubCompoundGraphFactory = new SubCompoundGraphFactory(this.testFixture.getGraph());
		this.testSubCompoundGraphFactory.addElement(this.testFixture.getEdge1());
	}

	@After
	public void tearDown() throws Exception {
		this.mockery = null;
		this.testFixture = null;
		this.testSubCompoundGraphFactory = null;
	}

	@Test
	public final void testAddElement() {
		testSubCompoundGraphFactory.addElement(this.testFixture.getNode5()) ; 
		testSubCompoundGraphFactory.addElement(this.testFixture.getEdge4()) ; 
		
		assertEquals ( "correct size" , 3 , this.testSubCompoundGraphFactory.numElements()) ;

	}

	@Test
	public final void testElementIterator() {
		IteratorTestUtility<ICompoundGraphElement> testIterator = new IteratorTestUtility<ICompoundGraphElement>(this.testFixture.getEdge1());
		testIterator.testSortedIterator(testSubCompoundGraphFactory.elementIterator());
	}

	@Test
	public final void testCreateSubgraph() {
		ISubCompoundGraph generatedSubGraph = testSubCompoundGraphFactory.createSubgraph() ;
		
		assertTrue("edge found", generatedSubGraph.containsEdge(this.testFixture.getEdge2()));
		assertFalse("edge not found", generatedSubGraph.containsEdge(this.testFixture.getEdge3()));
		assertTrue("edge found", generatedSubGraph.containsEdge(this.testFixture.getEdge1()));
		assertFalse("edge not found", generatedSubGraph.containsEdge(this.testFixture.getEdge4()));
		assertFalse("node not found", generatedSubGraph.containsNode(this.testFixture.getNode2()));
		assertTrue("node found", generatedSubGraph.containsNode(this.testFixture.getNode3()));
		assertTrue("node found", generatedSubGraph.containsNode(this.testFixture.getNode4()));
		assertFalse("node not found", generatedSubGraph.containsNode(this.testFixture.getRootNode()));
		assertFalse("node not found", generatedSubGraph.containsNode(this.testFixture.getNode1()));
		assertTrue("node found", generatedSubGraph.containsNode(this.testFixture.getNode5()));
		assertFalse("node not found", generatedSubGraph.containsNode(this.testFixture.getNode6()));
		assertEquals("expected top elements", EXPECTED_TOP_SUBGRAPH_ELEMENTS, generatedSubGraph.numTopElements());
		assertEquals("expected elements", EXPECTED_NUM_SUBGRAPH_ELEMENTS, generatedSubGraph.numElements());
	}

	@Test
	public final void testCreateInducedSubgraph() {
		ISubCompoundGraph generatedSubGraph = testSubCompoundGraphFactory.createInducedSubgraph() ;
		
//		assertFalse("edge not found", generatedSubGraph.containsEdge(this.testFixture.getEdge2()));
//		assertTrue("edge found", generatedSubGraph.containsEdge(this.testFixture.getEdge3()));
//		assertFalse("not edge found", generatedSubGraph.containsEdge(this.testFixture.getEdge1()));
//		assertTrue("edge found", generatedSubGraph.containsEdge(this.testFixture.getEdge4()));
//		assertTrue("node found", generatedSubGraph.containsNode(this.testFixture.getNode2()));
//		assertTrue("node found", generatedSubGraph.containsNode(this.testFixture.getNode3()));
//		assertFalse("node not found", generatedSubGraph.containsNode(this.testFixture.getNode4()));
//		assertFalse("node not found", generatedSubGraph.containsNode(this.testFixture.getRootNode()));
//		assertFalse("node not found", generatedSubGraph.containsNode(this.testFixture.getNode1()));
//		assertFalse("node not found", generatedSubGraph.containsNode(this.testFixture.getNode5()));
//		assertFalse("node not found", generatedSubGraph.containsNode(this.testFixture.getNode6()));
		assertEquals("expected top elements", EXPECTED_TOP_INDUCED_SUBGRAPH_ELEMENTS, generatedSubGraph.numTopElements());
		assertEquals("expected elemenbt", EXPECTED_INDUCED_SUBGRAPH_ELEMENTS, generatedSubGraph.numElements());
	}

	@Test
	public final void testCreatePermissiveInducedSubgraph() {
		ISubCompoundGraph generatedSubGraph = testSubCompoundGraphFactory.createPermissiveInducedSubgraph() ;
		
		assertTrue("edge found", generatedSubGraph.containsEdge(this.testFixture.getEdge2()));
		assertFalse("edge not found", generatedSubGraph.containsEdge(this.testFixture.getEdge3()));
		assertTrue("edge found", generatedSubGraph.containsEdge(this.testFixture.getEdge1()));
		assertFalse("edge not found", generatedSubGraph.containsEdge(this.testFixture.getEdge4()));
		assertFalse("node not found", generatedSubGraph.containsNode(this.testFixture.getNode2()));
		assertTrue("node found", generatedSubGraph.containsNode(this.testFixture.getNode3()));
		assertTrue("node found", generatedSubGraph.containsNode(this.testFixture.getNode4()));
		assertFalse("node not found", generatedSubGraph.containsNode(this.testFixture.getRootNode()));
		assertFalse("node not found", generatedSubGraph.containsNode(this.testFixture.getNode1()));
		assertTrue("node found", generatedSubGraph.containsNode(this.testFixture.getNode5()));
		assertFalse("node not found", generatedSubGraph.containsNode(this.testFixture.getNode6()));
		assertEquals("expected top elements", EXPECTED_TOP_PERMSSIVE_INDUCED_SUBGRAPH_ELEMENTS, generatedSubGraph.numTopElements());
		assertEquals("expected elemenbt", EXPECTED_PERMSSIVE_INDUCED_SUBGRAPH_ELEMENTS, generatedSubGraph.numElements());
	}

}
