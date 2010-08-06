package uk.ed.inf.graph.compound.newimpl;

import static org.hamcrest.Matchers.isOneOf;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.ed.inf.designbycontract.PreConditionException;
import uk.ed.inf.graph.compound.ICompoundGraphMoveBuilder;
import uk.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ed.inf.graph.compound.ISubCompoundGraphFactory;
import uk.ed.inf.graph.compound.testfixture.ComplexGraphFixture;
import uk.ed.inf.graph.compound.testfixture.IGraphTestFixture;

@RunWith(JMock.class)
public class CompoundGraphMoveBuilderWithIncidentNodeAsChildTest {
	private static final int NUM_TOP_ELEMENTS = 1;
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
			allowing(mockSrcSubgraph).topElementIterator(); will(returnIterator(testFixture.getNode(ComplexGraphFixture.NODE1_ID)));
			allowing(mockSrcSubgraph).numTopElements(); will(returnValue(NUM_TOP_ELEMENTS));
			allowing(mockSrcSubgraph).elementIterator(); will(returnIterator(testFixture.getNode(ComplexGraphFixture.NODE1_ID), testFixture.getNode(ComplexGraphFixture.NODE2_ID), testFixture.getEdge(ComplexGraphFixture.EDGE3_ID)));
			allowing(mockSrcSubgraph).edgeLastElementIterator(); will(returnIterator(testFixture.getNode(ComplexGraphFixture.NODE1_ID), testFixture.getNode(ComplexGraphFixture.NODE2_ID), testFixture.getEdge(ComplexGraphFixture.EDGE3_ID)));
			allowing(mockSrcSubgraph).isInducedSubgraph(); will(returnValue(true));
			allowing(mockSrcSubgraph).isConsistentSnapShot(); will(returnValue(true));
			allowing(mockSrcSubgraph).containsRoot(); will(returnValue(false));
			allowing(mockSrcSubgraph).containsNode(with(isOneOf(testFixture.getNode(ComplexGraphFixture.NODE1_ID), testFixture.getNode(ComplexGraphFixture.NODE2_ID)))); will(returnValue(true));
			allowing(mockSrcSubgraph).containsNode(with(not(isOneOf(testFixture.getNode(ComplexGraphFixture.NODE1_ID), testFixture.getNode(ComplexGraphFixture.NODE2_ID))))); will(returnValue(false));
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
		assertFalse("cannot move", this.testInstance.canMoveHere());
	}

	@Test(expected=PreConditionException.class)
	public void testMakeMove() {
		this.testInstance.makeMove();
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
