package uk.ac.ed.inf.graph.compound.newimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

import uk.ac.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundGraphCopyBuilder;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElementFactory;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.IGraphStructureChangeAction;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraphFactory;
import uk.ac.ed.inf.graph.compound.testfixture.ComplexGraphFixture;
import uk.ac.ed.inf.graph.compound.testfixture.ElementAttribute;

@RunWith(JMock.class)
public class CompoundGraphCopyBuilderSameGraphTest {

	private Mockery mockery;

	private ICompoundGraphCopyBuilder testInstance;
	private ComplexGraphFixture testFixture;
	private ComplexGraphFixture destnFixture;
	private ISubCompoundGraph mockSrcSubgraph;

	private ICompoundGraphElementFactory mockElementFactory;

	@Before
	public void setUp() throws Exception {
		this.mockery = new JUnit4Mockery();

		this.testFixture = new ComplexGraphFixture(mockery, "");
		this.testFixture.buildFixture();
		// src graph
		this.mockSrcSubgraph = this.mockery.mock(ISubCompoundGraph.class, "mockSrcSubgraph");
		this.mockElementFactory = this.mockery.mock(ICompoundGraphElementFactory.class, "mockElementFactory");

		// dstn graph
		this.destnFixture = this.testFixture;
		
		this.mockery.checking(new Expectations(){{
			allowing(mockSrcSubgraph).getSuperGraph(); will(returnValue(testFixture.getGraph()));
			allowing(mockSrcSubgraph).topElementIterator(); will(returnIterator(testFixture.getNode1(), testFixture.getEdge2(), testFixture.getNode3(), testFixture.getNode5()));
			allowing(mockSrcSubgraph).elementIterator(); will(returnIterator(testFixture.getNode1(), testFixture.getNode2(), testFixture.getEdge3(), testFixture.getEdge2(), testFixture.getNode3(), testFixture.getNode4(), testFixture.getNode5()));
			allowing(mockSrcSubgraph).edgeLastElementIterator(); will(returnIterator(testFixture.getNode1(), testFixture.getNode2(), testFixture.getEdge3(), testFixture.getNode3(), testFixture.getNode5(), testFixture.getEdge2(), testFixture.getNode4()));
			allowing(mockSrcSubgraph).isInducedSubgraph(); will(returnValue(true));
			allowing(mockSrcSubgraph).isConsistentSnapShot(); will(returnValue(true));
			allowing(mockSrcSubgraph).containsRoot(); will(returnValue(false));
		}});
		
		this.testInstance = new CompoundGraphCopyBuilder(this.destnFixture.getGraph().getRoot().getChildCompoundGraph(), this.mockElementFactory);
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
		final ICompoundGraphElement destElement = destnFixture.getGraph().getRoot();
		final IChildCompoundGraph destChildGraph = destElement.getChildCompoundGraph();
//		final ICompoundNodeFactory destnRootNodeFact = destElement.getChildCompoundGraph().nodeFactory();
//		final ICompoundNodeFactory mockNodeFact = this.mockery.mock(ICompoundNodeFactory.class, "mockNodeFact");
		final ISubCompoundGraphFactory destnSubgraphFactory = this.destnFixture.getGraph().subgraphFactory();
		final ICompoundNode mockNode = this.mockery.mock(ICompoundNode.class, "mockNode");
		final ICompoundEdge mockEdge = this.mockery.mock(ICompoundEdge.class, "mockEdge");
		final IChildCompoundGraph mockChildGraph = this.mockery.mock(IChildCompoundGraph.class, "mockChildGraph");
//		final ICompoundChildEdgeFactory destnrootChildEdgeFactory = destnFixture.getGraph().getRoot().getChildCompoundGraph().edgeFactory();
		final ISubCompoundGraph mockDestnSubgraph = this.mockery.mock(ISubCompoundGraph.class, "mockDestnSubgraph");
//		final ICompoundChildEdgeFactory mockEdgeFact = this.mockery.mock(ICompoundChildEdgeFactory.class, "mockEdgeFact");
		this.mockery.checking(new Expectations(){{
			allowing(mockNode).getChildCompoundGraph(); will(returnValue(mockChildGraph));
			allowing(mockNode).getGraph(); will(returnValue(destnFixture.getGraph()));
			allowing(mockNode).getAttribute(); will(returnValue(new ElementAttribute("mockNodeAtt")));
			
			allowing(mockEdge).getChildCompoundGraph(); will(returnValue(mockChildGraph));
			allowing(mockEdge).getGraph(); will(returnValue(destnFixture.getGraph()));
			allowing(mockEdge).getAttribute(); will(returnValue(new ElementAttribute("mockNodeAtt")));
			
//			allowing(mockChildGraph).nodeFactory(); will(returnValue(mockNodeFact));
//			allowing(mockChildGraph).edgeFactory(); will(returnValue(mockEdgeFact));
			allowing(mockChildGraph).getSuperGraph(); will(returnValue(destnFixture.getGraph()));

//			allowing(mockEdgeFact).setAttributeFactory(with(any(IElementAttributeFactory.class)));
//			exactly(1).of(mockEdgeFact).setPair(with(any(CompoundNodePair.class)));
//			exactly(1).of(mockEdgeFact).createEdge();
			
//			allowing(destnRootNodeFact).setAttributeFactory(with(any(IElementAttributeFactory.class)));
//			exactly(3).of(destnRootNodeFact).createNode(); will(returnValue(mockNode));

//			allowing(mockNodeFact).setAttributeFactory(with(any(IElementAttributeFactory.class)));
//			exactly(2).of(mockNodeFact).createNode(); will(returnValue(mockNode));
			
			exactly(7).of(destnSubgraphFactory).addElement(with(any(ICompoundGraphElement.class)));
			exactly(1).of(destnSubgraphFactory).createSubgraph(); will(returnValue(mockDestnSubgraph));
			
			allowing(mockDestnSubgraph).getSuperGraph(); will(returnValue(destnFixture.getGraph()));
			
//			allowing(destnrootChildEdgeFactory).setAttributeFactory(with(any(IElementAttributeFactory.class)));
//			exactly(1).of(destnrootChildEdgeFactory).setPair(with(any(CompoundNodePair.class)));
//			exactly(1).of(destnrootChildEdgeFactory).createEdge(); will(returnValue(mockEdge));
			
			exactly(7).of(mockElementFactory).setIndex(with(any(Integer.class)));
			exactly(4).of(mockElementFactory).setParent(with(destElement));
			exactly(2).of(mockElementFactory).setParent(with(mockNode));
			exactly(1).of(mockElementFactory).setParent(with(mockEdge));
			exactly(7).of(mockElementFactory).setAttribute(with(any(ElementAttribute.class)));
			exactly(5).of(mockElementFactory).createNode(); will(returnValue(mockNode));
			exactly(2).of(mockElementFactory).createEdge(mockNode, mockNode); will(returnValue(mockEdge));
			
			exactly(3).of(destChildGraph).addNode(mockNode);
			exactly(1).of(destChildGraph).addEdge(mockEdge);

			exactly(2).of(mockChildGraph).addNode(mockNode);
			exactly(1).of(mockChildGraph).addEdge(mockEdge);
			
			one(testFixture.getGraph()).notifyGraphStructureChange(with(any(IGraphStructureChangeAction.class)));
		}});
		this.testInstance.makeCopy();
		assertNotNull("copied contents", this.testInstance.getCopiedComponents());
		this.mockery.assertIsSatisfied();
	}
	
	@Test
	public void testCanCopyHere(){
		assertTrue("can copy", this.testInstance.canCopyHere());
	}
}
