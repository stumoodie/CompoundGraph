package uk.ac.ed.inf.graph.compound.newimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.ed.inf.graph.compound.CompoundNodePair;
import uk.ac.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundChildEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundGraphCopyBuilder;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.ICompoundNodeFactory;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraphFactory;
import uk.ac.ed.inf.graph.compound.newimpl.CompoundGraphCopyBuilder;
import uk.ac.ed.inf.graph.compound.testfixture.ComplexGraphFixture;

@RunWith(JMock.class)
public class CompoundGraphCopyBuilderTest {

	private Mockery mockery;

	private ICompoundGraphCopyBuilder testInstance;
	private ComplexGraphFixture testFixture;
	private ComplexGraphFixture destnFixture;
	private ISubCompoundGraph mockSrcSubgraph;
	
	@Before
	public void setUp() throws Exception {
		this.mockery = new JUnit4Mockery();

		this.testFixture = new ComplexGraphFixture(mockery, "");
		this.testFixture.buildFixture();
		// src graph
		this.mockSrcSubgraph = this.mockery.mock(ISubCompoundGraph.class, "mockSrcSubgraph");

		// dstn graph
		this.destnFixture = new ComplexGraphFixture(mockery, "destn");
		this.destnFixture.buildFixture();
		
		this.mockery.checking(new Expectations(){{
			allowing(mockSrcSubgraph).getSuperGraph(); will(returnValue(testFixture.getGraph()));
			allowing(mockSrcSubgraph).elementIterator(); will(returnIterator(testFixture.getNode1(), testFixture.getNode2(), testFixture.getEdge2(), testFixture.getNode3(), testFixture.getNode4(), testFixture.getNode5()));
			allowing(mockSrcSubgraph).edgeLastElementIterator(); will(returnIterator(testFixture.getNode1(), testFixture.getNode2(), testFixture.getNode3(), testFixture.getNode4(), testFixture.getNode5(), testFixture.getEdge2()));
			allowing(mockSrcSubgraph).isInducedSubgraph(); will(returnValue(true));
			allowing(mockSrcSubgraph).isConsistentSnapShot(); will(returnValue(true));
			allowing(mockSrcSubgraph).containsRoot(); will(returnValue(false));
		}});
		
		this.testInstance = new CompoundGraphCopyBuilder(this.destnFixture.getGraph().getRoot().getChildCompoundGraph());
		this.testInstance.setSourceSubgraph(this.mockSrcSubgraph);
	}

	@After
	public void tearDown() throws Exception {
		this.mockery = null;
		this.testInstance = null;
	}

	@Test
	public void testGetCopiedComponents() {
		assertNull("not copied contents", this.testInstance.getCopiedComponents());
	}

	@Test
	public void testGetDestinationChildGraph() {
		assertEquals("expected childGraph", this.destnFixture.getGraph().getRoot().getChildCompoundGraph(), this.testInstance.getDestinationChildGraph());
	}

	@Test
	public void testGetSourceSubgraph() {
		assertEquals("expected subgraph", this.mockSrcSubgraph, this.testInstance.getSourceSubgraph());
	}

	@Test
	public void testSetSourceSubgraph() {
		this.testInstance.setSourceSubgraph(null);
		assertNull("src is null", this.testInstance.getSourceSubgraph());
	}
	
	@Test
	public void testMakeCopy(){
		final ICompoundNodeFactory nodeFact = this.destnFixture.getGraph().nodeFactory();
		final ISubCompoundGraphFactory destnSubgraphFactory = this.destnFixture.getGraph().subgraphFactory();
		final ICompoundNode mockNode = this.mockery.mock(ICompoundNode.class, "mockNode");
		final ICompoundEdge mockEdge = this.mockery.mock(ICompoundEdge.class, "mockEdge");
		final IChildCompoundGraph mockChildGraph = this.mockery.mock(IChildCompoundGraph.class, "mockChildGraph");
		final ICompoundChildEdgeFactory destnrootChildEdgeFactory = destnFixture.getGraph().getRoot().getChildCompoundGraph().edgeFactory();
		final ISubCompoundGraph mockDestnSubgraph = this.mockery.mock(ISubCompoundGraph.class, "mockDestnSubgraph");
		this.mockery.checking(new Expectations(){{
			allowing(mockNode).getChildCompoundGraph(); will(returnValue(mockChildGraph));
			allowing(mockNode).getGraph(); will(returnValue(destnFixture.getGraph()));
			
			allowing(mockEdge).getChildCompoundGraph(); will(returnValue(mockChildGraph));
			allowing(mockEdge).getGraph(); will(returnValue(destnFixture.getGraph()));
			
			allowing(mockChildGraph).nodeFactory(); will(returnValue(nodeFact));
			allowing(mockChildGraph).getSuperGraph(); will(returnValue(destnFixture.getGraph()));

			exactly(5).of(nodeFact).createNode(); will(returnValue(mockNode));
			
			exactly(6).of(destnSubgraphFactory).addElement(with(any(ICompoundGraphElement.class)));
			exactly(1).of(destnSubgraphFactory).createSubgraph(); will(returnValue(mockDestnSubgraph));
			
			allowing(mockDestnSubgraph).getSuperGraph(); will(returnValue(destnFixture.getGraph()));
			
			exactly(1).of(destnrootChildEdgeFactory).setPair(with(any(CompoundNodePair.class)));
			exactly(1).of(destnrootChildEdgeFactory).createEdge(); will(returnValue(mockEdge));
			
		}});
		this.testInstance.makeCopy();
		
	}
	
	@Test
	public void testCanCopyHere(){
		assertTrue("can copy", this.testInstance.canCopyHere());
	}
}
