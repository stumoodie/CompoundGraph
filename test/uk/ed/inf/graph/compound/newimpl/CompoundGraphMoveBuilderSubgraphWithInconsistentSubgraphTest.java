package uk.ed.inf.graph.compound.newimpl;

import static org.junit.Assert.assertFalse;

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
import uk.ed.inf.graph.compound.testfixture.ComplexGraphFixture;
import uk.ed.inf.graph.compound.testfixture.IGraphTestFixture;

@RunWith(JMock.class)
public class CompoundGraphMoveBuilderSubgraphWithInconsistentSubgraphTest {
	private Mockery mockery;
	private ICompoundGraphMoveBuilder testInstance;
	private IGraphTestFixture testFixture;
	private ISubCompoundGraph mockSrcSubgraph;
	
	@Before
	public void setUp() throws Exception {
		this.mockery = new JUnit4Mockery();
		this.testFixture = new ComplexGraphFixture(mockery, "");
		this.testFixture.setElementRemoved(ComplexGraphFixture.EDGE3_ID, true);
		this.testFixture.buildFixture();
		this.testInstance = new CompoundGraphMoveBuilder(this.testFixture.getNode(ComplexGraphFixture.NODE6_ID).getChildCompoundGraph());
		this.mockSrcSubgraph = this.mockery.mock(ISubCompoundGraph.class, "mockSrcSubgraph");
		this.mockery.checking(new Expectations(){{
			allowing(mockSrcSubgraph).getSuperGraph(); will(returnValue(testFixture.getGraph()));
			allowing(mockSrcSubgraph).isInducedSubgraph(); will(returnValue(true));
			allowing(mockSrcSubgraph).isConsistentSnapShot(); will(returnValue(true));
			allowing(mockSrcSubgraph).containsRoot(); will(returnValue(true));
//			allowing(mockSrcSubgraph).containsElement(with(isOneOf(testFixture.getNode(ComplexGraphFixture.NODE1_ID), testFixture.getNode(ComplexGraphFixture.NODE2_ID), testFixture.getEdge(ComplexGraphFixture.EDGE2_ID), testFixture.getNode(ComplexGraphFixture.NODE3_ID), testFixture.getNode(ComplexGraphFixture.NODE4_ID), testFixture.getNode(ComplexGraphFixture.NODE5_ID)))); will(returnValue(true));
//			allowing(mockSrcSubgraph).containsElement(with(not(isOneOf(testFixture.getNode(ComplexGraphFixture.NODE1_ID), testFixture.getNode(ComplexGraphFixture.NODE2_ID), testFixture.getEdge(ComplexGraphFixture.EDGE2_ID), testFixture.getNode(ComplexGraphFixture.NODE3_ID), testFixture.getNode(ComplexGraphFixture.NODE4_ID), testFixture.getNode(ComplexGraphFixture.NODE5_ID))))); will(returnValue(true));
		}});
		this.testInstance.setSourceSubgraph(mockSrcSubgraph);
	}

	@After
	public void tearDown() throws Exception {
		this.mockery = null;
		this.testFixture = null;
		this.testInstance = null;
	}

	@Test
	public void testCanMoveHere() {
		assertFalse("can move", this.testInstance.canMoveHere());
	}

	@Test(expected=PreConditionException.class)
	public void testMakeMove() {
		this.testInstance.makeMove();
	}
}
