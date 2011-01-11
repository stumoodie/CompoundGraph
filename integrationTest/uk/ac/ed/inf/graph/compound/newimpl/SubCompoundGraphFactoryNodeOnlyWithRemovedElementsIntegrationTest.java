package uk.ac.ed.inf.graph.compound.newimpl;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraphFactory;
import uk.ac.ed.inf.graph.compound.testfixture.IGraphTestFixture;
import uk.ac.ed.inf.graph.compound.testfixture.IteratorTestUtility;

public class SubCompoundGraphFactoryNodeOnlyWithRemovedElementsIntegrationTest {
	private IGraphTestFixture testFixture;
	private ISubCompoundGraphFactory testInstance;
	
	@Before
	public void setUp() throws Exception {
		this.testFixture = new IntegrationTestGraphFixture();
		this.testFixture.buildFixture();
		this.testInstance = new SubCompoundGraphFactory(this.testFixture.getGraph());
		this.testInstance.addElement(this.testFixture.getNode(IntegrationTestGraphFixture.NODE1_ID));
		this.testInstance.addElement(this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE1_ID));
		this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE2_ID).markRemoved(true);
		this.testFixture.getNode(IntegrationTestGraphFixture.NODE4_ID).markRemoved(true);
	}

	@After
	public void tearDown() throws Exception {
		this.testInstance = null;
		this.testFixture = null;
	}

	@Test
	public void testSubGraphCreation(){
		ISubCompoundGraph subgraph = this.testInstance.createSubgraph();
		assertEquals("expected size", 6, subgraph.numElements());
		assertEquals("expected size", 2, subgraph.numTopElements());
		assertEquals("expected size", 4, subgraph.getNumNodes());
		assertEquals("expected size", 2, subgraph.getNumEdges());
		IteratorTestUtility<ICompoundGraphElement> testIter = new IteratorTestUtility<ICompoundGraphElement>(this.testFixture.getNode(IntegrationTestGraphFixture.NODE1_ID),
				this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE1_ID),	this.testFixture.getNode(IntegrationTestGraphFixture.NODE2_ID),
				this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE3_ID),	this.testFixture.getNode(IntegrationTestGraphFixture.NODE3_ID),
				this.testFixture.getNode(IntegrationTestGraphFixture.NODE5_ID));
		testIter.testSortedIterator(subgraph.elementIterator());
		testIter = new IteratorTestUtility<ICompoundGraphElement>(this.testFixture.getNode(IntegrationTestGraphFixture.NODE1_ID),
				this.testFixture.getNode(IntegrationTestGraphFixture.NODE2_ID), this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE3_ID),
				this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE1_ID),	this.testFixture.getNode(IntegrationTestGraphFixture.NODE3_ID),
				this.testFixture.getNode(IntegrationTestGraphFixture.NODE5_ID));
		testIter.testIterator(subgraph.edgeLastElementIterator());
		assertTrue("is consistent", subgraph.isConsistentSnapShot());
		assertFalse("not induced", subgraph.isInducedSubgraph());
		assertTrue("has orphaned edges", subgraph.hasOrphanedEdges());
	}

	@Test
	public void testSubGraphInducesCreation(){
		ISubCompoundGraph subgraph = this.testInstance.createInducedSubgraph();
		assertEquals("expected size", 3, subgraph.numElements());
		assertEquals("expected size", 1, subgraph.numTopElements());
		assertEquals("expected size", 2, subgraph.getNumNodes());
		assertEquals("expected size", 1, subgraph.getNumEdges());
		IteratorTestUtility<ICompoundGraphElement> testIter = new IteratorTestUtility<ICompoundGraphElement>(this.testFixture.getNode(IntegrationTestGraphFixture.NODE1_ID),
				this.testFixture.getNode(IntegrationTestGraphFixture.NODE2_ID), this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE3_ID));
		testIter.testSortedIterator(subgraph.elementIterator());
		testIter.testIterator(subgraph.edgeLastElementIterator());
		assertTrue("is consistent", subgraph.isConsistentSnapShot());
		assertTrue("is induced", subgraph.isInducedSubgraph());
		assertFalse("no orphaned edges", subgraph.hasOrphanedEdges());
	}

	@Test
	public void testSubGraphInducedPermissiveCreation(){
		ISubCompoundGraph subgraph = this.testInstance.createPermissiveInducedSubgraph();
		assertEquals("expected size", 7, subgraph.numElements());
		assertEquals("expected size", 3, subgraph.numTopElements());
		assertEquals("expected size", 4, subgraph.getNumNodes());
		assertEquals("expected size", 3, subgraph.getNumEdges());
		IteratorTestUtility<ICompoundGraphElement> testIter = new IteratorTestUtility<ICompoundGraphElement>(this.testFixture.getNode(IntegrationTestGraphFixture.NODE1_ID),
				this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE1_ID),	this.testFixture.getNode(IntegrationTestGraphFixture.NODE2_ID),
				this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE3_ID),	this.testFixture.getNode(IntegrationTestGraphFixture.NODE3_ID),
				this.testFixture.getNode(IntegrationTestGraphFixture.NODE5_ID), this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE4_ID));
		testIter.testSortedIterator(subgraph.elementIterator());
		testIter = new IteratorTestUtility<ICompoundGraphElement>(this.testFixture.getNode(IntegrationTestGraphFixture.NODE1_ID),
				this.testFixture.getNode(IntegrationTestGraphFixture.NODE2_ID), this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE3_ID),
				this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE1_ID),	this.testFixture.getNode(IntegrationTestGraphFixture.NODE3_ID),
				this.testFixture.getNode(IntegrationTestGraphFixture.NODE5_ID), this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE4_ID));
		testIter.testIterator(subgraph.edgeLastElementIterator());
		assertTrue("is consistent", subgraph.isConsistentSnapShot());
		assertTrue("is induced", subgraph.isInducedSubgraph());
		assertTrue("has orphaned edges", subgraph.hasOrphanedEdges());
	}
	
}
