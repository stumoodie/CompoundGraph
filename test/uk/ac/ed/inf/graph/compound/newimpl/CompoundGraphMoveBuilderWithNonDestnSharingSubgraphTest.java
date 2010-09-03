package uk.ac.ed.inf.graph.compound.newimpl;

import static org.hamcrest.Matchers.isOneOf;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.ed.inf.designbycontract.PreConditionException;
import uk.ac.ed.inf.graph.compound.CompoundNodePair;
import uk.ac.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundChildEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ICompoundGraphMoveBuilder;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.ICompoundNodeFactory;
import uk.ac.ed.inf.graph.compound.IElementAttributeFactory;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraphFactory;
import uk.ac.ed.inf.graph.compound.testfixture.ComplexGraphFixture;
import uk.ac.ed.inf.graph.compound.testfixture.ElementAttribute;
import uk.ac.ed.inf.graph.compound.testfixture.IGraphTestFixture;

@RunWith(JMock.class)
public class CompoundGraphMoveBuilderWithNonDestnSharingSubgraphTest {
	private static final int NUM_TOP_ELEMENTS = 4;
	private Mockery mockery;
	private ICompoundGraphMoveBuilder testInstance;
	private IGraphTestFixture testFixture;
	private ISubCompoundGraph mockSrcSubgraph;
	
	@Before
	public void setUp() throws Exception {
		this.mockery = new JUnit4Mockery();
		this.testFixture = new ComplexGraphFixture(mockery, "");
		this.testFixture.buildFixture();
		this.testInstance = new CompoundGraphMoveBuilder(this.testFixture.getNode(ComplexGraphFixture.NODE6_ID).getChildCompoundGraph());
		this.mockSrcSubgraph = this.mockery.mock(ISubCompoundGraph.class, "mockSrcSubgraph");
		this.mockery.checking(new Expectations(){{
			allowing(mockSrcSubgraph).getSuperGraph(); will(returnValue(testFixture.getGraph()));
			allowing(mockSrcSubgraph).topElementIterator(); will(returnIterator(testFixture.getNode(ComplexGraphFixture.NODE2_ID),
					testFixture.getEdge(ComplexGraphFixture.EDGE4_ID), testFixture.getEdge(ComplexGraphFixture.EDGE2_ID),
					testFixture.getNode(ComplexGraphFixture.NODE3_ID), testFixture.getNode(ComplexGraphFixture.NODE5_ID)));
			allowing(mockSrcSubgraph).numTopElements(); will(returnValue(NUM_TOP_ELEMENTS));
			allowing(mockSrcSubgraph).elementIterator(); will(returnIterator(testFixture.getNode(ComplexGraphFixture.NODE2_ID),
					testFixture.getEdge(ComplexGraphFixture.EDGE4_ID), testFixture.getEdge(ComplexGraphFixture.EDGE3_ID),
					testFixture.getEdge(ComplexGraphFixture.EDGE2_ID),
					testFixture.getNode(ComplexGraphFixture.NODE3_ID), testFixture.getNode(ComplexGraphFixture.NODE4_ID),
					testFixture.getNode(ComplexGraphFixture.NODE5_ID), testFixture.getEdge(ComplexGraphFixture.EDGE4_ID)));
			allowing(mockSrcSubgraph).edgeLastElementIterator(); will(returnIterator(testFixture.getNode(ComplexGraphFixture.NODE2_ID),
					testFixture.getEdge(ComplexGraphFixture.EDGE3_ID), testFixture.getNode(ComplexGraphFixture.NODE3_ID),
					testFixture.getNode(ComplexGraphFixture.NODE5_ID), testFixture.getEdge(ComplexGraphFixture.EDGE2_ID),
					testFixture.getNode(ComplexGraphFixture.NODE4_ID), testFixture.getEdge(ComplexGraphFixture.EDGE4_ID)));
			allowing(mockSrcSubgraph).isInducedSubgraph(); will(returnValue(true));
			allowing(mockSrcSubgraph).isConsistentSnapShot(); will(returnValue(true));
			allowing(mockSrcSubgraph).containsRoot(); will(returnValue(false));
			allowing(mockSrcSubgraph).containsNode(with(isOneOf(testFixture.getNode(ComplexGraphFixture.NODE2_ID), testFixture.getNode(ComplexGraphFixture.NODE3_ID),
					testFixture.getNode(ComplexGraphFixture.NODE4_ID), testFixture.getNode(ComplexGraphFixture.NODE5_ID)))); will(returnValue(true));
			allowing(mockSrcSubgraph).containsNode(with(not(isOneOf(testFixture.getNode(ComplexGraphFixture.NODE2_ID), testFixture.getNode(ComplexGraphFixture.NODE3_ID),
					testFixture.getNode(ComplexGraphFixture.NODE4_ID), testFixture.getNode(ComplexGraphFixture.NODE5_ID))))); will(returnValue(false));
		}});
		this.testInstance.setSourceSubgraph(mockSrcSubgraph);
	}

	@After
	public void tearDown() throws Exception {
		this.mockery = null;
		this.testFixture = null;
		this.testInstance = null;
	}

	@Test(expected=PreConditionException.class)
	public void testCompoundGraphMoveBuilder() {
		new CompoundGraphMoveBuilder(null);
	}

	@Test
	public void testGetMovedComponents() {
		final ISubCompoundGraphFactory destn_subgraphFactory = this.testFixture.getGraph().subgraphFactory();
		final ISubCompoundGraph mockMovedSubgraph = this.mockery.mock(ISubCompoundGraph.class, "mockMovedSubgraph");
		this.mockery.checking(new Expectations(){{
			exactly(1).of(destn_subgraphFactory).createSubgraph(); will(returnValue(mockMovedSubgraph));
		}});
		ISubCompoundGraph actualResult = this.testInstance.getMovedComponents();
		assertNotNull("moved components", actualResult);
		assertEquals("expected subgraph", mockMovedSubgraph, actualResult);
	}

	@Test
	public void testGetDestinationChildGraph() {
		assertEquals("destn child graph", this.testFixture.getNode(ComplexGraphFixture.NODE6_ID).getChildCompoundGraph(), this.testInstance.getDestinationChildGraph());
	}

	@Test
	public void testGetSourceSubgraph() {
		assertEquals("expected src subgraph", this.mockSrcSubgraph, this.testInstance.getSourceSubgraph());
	}

	@Test
	public void testCanMoveHere() {
		assertTrue("can move", this.testInstance.canMoveHere());
	}

	@Test
	public void testMakeMove() {
		final ICompoundNode destnNode = this.testFixture.getNode(ComplexGraphFixture.NODE6_ID);
		final ICompoundNodeFactory destnNodeNodeFact = destnNode.getChildCompoundGraph().nodeFactory();
		final ICompoundChildEdgeFactory destnNodeEdgeFactory = destnNode.getChildCompoundGraph().edgeFactory();
		
		final ISubCompoundGraphFactory movedNodesSubgraphFactory = this.testFixture.getGraph().subgraphFactory();
//		final ISubCompoundGraphFactory removedNodesSubgraphFactory = this.testFixture.getGraph().subgraphFactory();
		
		final ICompoundNode mockNode = this.mockery.mock(ICompoundNode.class, "mockNode");
		final IChildCompoundGraph mockNodeChildGraph = this.mockery.mock(IChildCompoundGraph.class, "mockNodeChildGraph");
//		final ICompoundNodeFactory mockNodeNodeFactory = this.mockery.mock(ICompoundNodeFactory.class, "mockNodeNodeFactory");
		final ICompoundChildEdgeFactory mockNodeEdgeFactory = this.mockery.mock(ICompoundChildEdgeFactory.class, "mockNodeEdgeFactory");
		
		final ICompoundEdge mockEdge = this.mockery.mock(ICompoundEdge.class, "mockEdge");
		final IChildCompoundGraph mockEdgeChildGraph = this.mockery.mock(IChildCompoundGraph.class, "mockEdgeChildGraph");
		final ICompoundNodeFactory mockEdgeNodeFactory = this.mockery.mock(ICompoundNodeFactory.class, "mockEdgeNodeFactory");
//		final ICompoundChildEdgeFactory mockEdgeEdgeFactory = this.mockery.mock(ICompoundChildEdgeFactory.class, "mockEdgeEdgeFactory");

//		final ICompoundChildEdgeFactory destnrootChildEdgeFactory = testFixture.getGraph().getRoot().getChildCompoundGraph().edgeFactory();
		final ISubCompoundGraph mockDestnSubgraph = this.mockery.mock(ISubCompoundGraph.class, "mockDestnSubgraph");
		
		this.mockery.checking(new Expectations(){{
			allowing(mockNode).getChildCompoundGraph(); will(returnValue(mockNodeChildGraph));
			allowing(mockNode).getGraph(); will(returnValue(testFixture.getGraph()));
			allowing(mockNode).getAttribute(); will(returnValue(new ElementAttribute("mockNodeAtt")));
			
			allowing(mockEdge).getChildCompoundGraph(); will(returnValue(mockEdgeChildGraph));
			allowing(mockEdge).getGraph(); will(returnValue(testFixture.getGraph()));
			allowing(mockEdge).getAttribute(); will(returnValue(new ElementAttribute("mockNodeAtt")));
		
			exactly(1).of(testFixture.getNode(ComplexGraphFixture.NODE2_ID)).markRemoved(true);
			exactly(1).of(testFixture.getNode(ComplexGraphFixture.NODE3_ID)).markRemoved(true);
			exactly(1).of(testFixture.getNode(ComplexGraphFixture.NODE4_ID)).markRemoved(true);
			exactly(1).of(testFixture.getNode(ComplexGraphFixture.NODE5_ID)).markRemoved(true);
			exactly(1).of(testFixture.getEdge(ComplexGraphFixture.EDGE3_ID)).markRemoved(true);
			exactly(1).of(testFixture.getEdge(ComplexGraphFixture.EDGE4_ID)).markRemoved(true);
			exactly(1).of(testFixture.getEdge(ComplexGraphFixture.EDGE2_ID)).markRemoved(true);
			
			exactly(14).of(movedNodesSubgraphFactory).addElement(with(any(ICompoundGraphElement.class)));
			exactly(2).of(movedNodesSubgraphFactory).createSubgraph(); will(returnValue(mockDestnSubgraph));
			
			allowing(mockDestnSubgraph).getSuperGraph(); will(returnValue(testFixture.getGraph()));
		}});
		this.mockery.checking(new Expectations(){{
			exactly(3).of(destnNodeNodeFact).setAttributeFactory(with(any(IElementAttributeFactory.class)));
			exactly(3).of(destnNodeNodeFact).createNode(); will(returnValue(mockNode));
			
			exactly(2).of(destnNodeEdgeFactory).setAttributeFactory(with(any(IElementAttributeFactory.class)));
			exactly(2).of(destnNodeEdgeFactory).setPair(with(equal(new CompoundNodePair(mockNode, mockNode))));
			exactly(2).of(destnNodeEdgeFactory).createEdge(); will(returnValue(mockEdge));
			
			exactly(1).of(mockEdgeChildGraph).nodeFactory(); will(returnValue(mockEdgeNodeFactory));

			exactly(1).of(mockEdgeNodeFactory).setAttributeFactory(with(any(IElementAttributeFactory.class)));
			
			exactly(1).of(mockEdgeNodeFactory).createNode(); will(returnValue(mockNode));	
			
			exactly(1).of(mockNodeChildGraph).edgeFactory(); will(returnValue(mockNodeEdgeFactory));

			exactly(1).of(mockNodeEdgeFactory).setAttributeFactory(with(any(IElementAttributeFactory.class)));
			exactly(1).of(mockNodeEdgeFactory).setPair(with(equal(new CompoundNodePair(mockNode, mockNode))));	
			exactly(1).of(mockNodeEdgeFactory).createEdge(); will(returnValue(mockEdge));	
			
		}});
		this.testInstance.makeMove();
		this.mockery.assertIsSatisfied();
	}

	@Test
	public void testSetSourceSubgraph() {
		this.testInstance.setSourceSubgraph(mockSrcSubgraph);
		assertEquals("expected subgraph", this.mockSrcSubgraph, this.testInstance.getSourceSubgraph());
	}

	@Test
	public void testGetRemovedComponents() {
		final ISubCompoundGraphFactory subgraphFactory = this.testFixture.getGraph().subgraphFactory();
		final ISubCompoundGraph mockRemovedSubgraph = this.mockery.mock(ISubCompoundGraph.class, "mockRemovedSubgraph");
		this.mockery.checking(new Expectations(){{
			exactly(1).of(subgraphFactory).createSubgraph(); will(returnValue(mockRemovedSubgraph));
		}});
		ISubCompoundGraph actualResult = this.testInstance.getRemovedComponents();
		assertNotNull("removed components", actualResult);
		assertEquals("expected subgraph", mockRemovedSubgraph, actualResult);
	}

}
