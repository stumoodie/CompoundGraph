package uk.ac.ed.inf.graph.compound.newimpl;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraphFactory;
import uk.ac.ed.inf.graph.compound.ISubgraphRemovalBuilder;
import uk.ac.ed.inf.graph.compound.testfixture.IGraphTestFixture;
import uk.ac.ed.inf.graph.compound.testfixture.IteratorTestUtility;

public class GraphRemovalIntegrationTest {
	private IGraphTestFixture testFixture;
	private ICompoundGraph testInstance;
	private ISubgraphRemovalBuilder removalBuilder;
	
	@Before
	public void setUp() throws Exception {
		this.testFixture = new IntegrationTestGraphFixture();
		this.testFixture.buildFixture();
		this.testInstance = this.testFixture.getGraph();
		ISubCompoundGraphFactory subgraphFact = this.testInstance.subgraphFactory();
		subgraphFact.addElement(this.testFixture.getNode(IntegrationTestGraphFixture.NODE1_ID));
		subgraphFact.addElement(this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE1_ID));
		this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE2_ID).markRemoved(true);
		this.testFixture.getNode(IntegrationTestGraphFixture.NODE4_ID).markRemoved(true);
		ISubCompoundGraph subgraph = subgraphFact.createPermissiveInducedSubgraph();
		removalBuilder = this.testInstance.newSubgraphRemovalBuilder();
		removalBuilder.setRemovalSubgraph(subgraph);
		removalBuilder.removeSubgraph();
	}

	@After
	public void tearDown() throws Exception {
		this.testInstance = null;
		this.testFixture = null;
		this.removalBuilder = null;
	}

	@Test
	public void testGraphState(){
		assertEquals("expected size", 2, testInstance.numElements());
		assertEquals("expected size", 2, testInstance.numNodes());
		assertEquals("expected size", 0, testInstance.numEdges());
	}

	@Test
	public void testRemovalSubgraph(){
		ISubCompoundGraph subgraph = this.removalBuilder.getRemovalSubgraph();
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
		assertFalse("not consistent", subgraph.isConsistentSnapShot());
		assertTrue("is induced", subgraph.isInducedSubgraph());
		assertTrue("has orphaned edges", subgraph.hasOrphanedEdges());
	}
	
}
