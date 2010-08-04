package uk.ed.inf.graph.compound.newimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
public class CompoundGraphMoveBuilderTest {
	private Mockery mockery;
	private ICompoundGraphMoveBuilder testInstance;
	private IGraphTestFixture testFixture;
	private IGraphTestFixture destnFixture;
	private ISubCompoundGraph mockSubgraph;
	
	@Before
	public void setUp() throws Exception {
		this.mockery = new JUnit4Mockery();
		this.testFixture = new ComplexGraphFixture(mockery, "");
		this.testFixture.doAll();
		this.destnFixture = new ComplexGraphFixture(mockery, "destn_");
		this.destnFixture.doAll();
		this.testInstance = new CompoundGraphMoveBuilder(this.destnFixture.getGraph().getRoot().getChildCompoundGraph());
		this.mockSubgraph = this.mockery.mock(ISubCompoundGraph.class, "mockSubgraph");
		this.mockery.checking(new Expectations(){{
			allowing(mockSubgraph).getSuperGraph(); will(returnValue(testFixture.getGraph()));
			allowing(mockSubgraph).isConsistentSnapShot(); will(returnValue(true));
			allowing(mockSubgraph).isInducedSubgraph(); will(returnValue(true));
		}});
	}

	@After
	public void tearDown() throws Exception {
		this.mockery = null;
		this.testFixture = null;
		this.destnFixture = null;
		this.testInstance = null;
	}

	@Test(expected=PreConditionException.class)
	public void testCompoundGraphMoveBuilder() {
		new CompoundGraphMoveBuilder(null);
	}

	@Test
	public void testGetMovedComponents() {
		final ISubCompoundGraphFactory destn_subgraphFactory = this.destnFixture.getGraph().subgraphFactory();
		this.mockery.checking(new Expectations(){{
			exactly(1).of(destn_subgraphFactory).createSubgraph(); will(returnValue(mockSubgraph));
		}});
		assertNotNull("moved components", this.testInstance.getMovedComponents());
	}

	@Test
	public void testGetDestinationChildGraph() {
		assertEquals("destn child graph", this.destnFixture.getRootNode().getChildCompoundGraph(), this.testInstance.getDestinationChildGraph());
	}

	@Test
	public void testGetSourceSubgraph() {
		assertNull("no src subgraph", this.testInstance.getSourceSubgraph());
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
		this.testInstance.setSourceSubgraph(mockSubgraph);
		assertEquals("expected subgraph", this.mockSubgraph, this.testInstance.getSourceSubgraph());
	}

	@Test
	public void testGetRemovedComponents() {
		final ISubCompoundGraphFactory destn_subgraphFactory = this.destnFixture.getGraph().subgraphFactory();
		this.mockery.checking(new Expectations(){{
			exactly(1).of(destn_subgraphFactory).createSubgraph(); will(returnValue(mockSubgraph));
		}});
		assertNotNull("removed components", this.testInstance.getRemovedComponents());
	}

}
