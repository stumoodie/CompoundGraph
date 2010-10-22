package uk.ac.ed.inf.graph.compound.newimpl;


import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraphFactory;
import uk.ac.ed.inf.graph.compound.testfixture.IGraphTestFixture;
import uk.ac.ed.inf.graph.compound.testfixture.IteratorTestUtility;

public class SubCompoundGraphFactoryEdgeOnlyIntegrationTest {
	private IGraphTestFixture testFixture;
	private ISubCompoundGraphFactory testInstance;
	
	@Before
	public void setUp() throws Exception {
		this.testFixture = new IntegrationTestGraphFixture();
		this.testFixture.buildFixture();
		this.testInstance = new SubCompoundGraphFactory(this.testFixture.getGraph());
		this.testInstance.addElement(this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE2_ID));
	}

	@After
	public void tearDown() throws Exception {
		this.testInstance = null;
		this.testFixture = null;
	}

	@Test
	public void testSubGraphCreation(){
		ISubCompoundGraph subgraph = this.testInstance.createSubgraph();
		assertEquals("expected size", 2, subgraph.numElements());
		assertEquals("expected size", 1, subgraph.numTopElements());
		assertEquals("expected size", 1, subgraph.getNumNodes());
		assertEquals("expected size", 1, subgraph.getNumEdges());
		IteratorTestUtility<ICompoundGraphElement> testIter = new IteratorTestUtility<ICompoundGraphElement>(this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE2_ID),
				this.testFixture.getNode(IntegrationTestGraphFixture.NODE4_ID));
		testIter.testSortedIterator(subgraph.elementIterator());
		testIter.testIterator(subgraph.edgeLastElementIterator());
	}

	@Test
	public void testSubGraphInducesCreation(){
		ISubCompoundGraph subgraph = this.testInstance.createInducedSubgraph();
		assertEquals("expected size", 0, subgraph.numElements());
		assertEquals("expected size", 0, subgraph.numTopElements());
		assertEquals("expected size", 0, subgraph.getNumNodes());
		assertEquals("expected size", 0, subgraph.getNumEdges());
		IteratorTestUtility<ICompoundGraphElement> testIter = new IteratorTestUtility<ICompoundGraphElement>();
		testIter.testSortedIterator(subgraph.elementIterator());
		testIter.testIterator(subgraph.edgeLastElementIterator());
	}

	@Test
	public void testSubGraphInducedPermissiveCreation(){
		ISubCompoundGraph subgraph = this.testInstance.createPermissiveInducedSubgraph();
		assertEquals("expected size", 2, subgraph.numElements());
		assertEquals("expected size", 1, subgraph.numTopElements());
		assertEquals("expected size", 1, subgraph.getNumNodes());
		assertEquals("expected size", 1, subgraph.getNumEdges());
		IteratorTestUtility<ICompoundGraphElement> testIter = new IteratorTestUtility<ICompoundGraphElement>(this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE2_ID),
				this.testFixture.getNode(IntegrationTestGraphFixture.NODE4_ID));
		testIter.testSortedIterator(subgraph.elementIterator());
		testIter.testIterator(subgraph.edgeLastElementIterator());
	}
}
