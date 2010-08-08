package uk.ac.ed.inf.graph.compound.newimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import uk.ac.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ac.ed.inf.graph.compound.ISubgraphRemovalBuilder;
import uk.ac.ed.inf.graph.compound.newimpl.CompoundSubgraphRemovalBuilder;
import uk.ac.ed.inf.graph.compound.testfixture.ComplexGraphFixture;
import uk.ac.ed.inf.graph.compound.testfixture.IGraphTestFixture;

@RunWith(JMock.class)
public class CompoundSubgraphRemovalBuilderTest {
	private Mockery mockery;
	private IGraphTestFixture testFixture;
	private ISubgraphRemovalBuilder testInstance;
	private ISubCompoundGraph mockSubgraph;

	@Before
	public void setUp() throws Exception {
		this.mockery = new JUnit4Mockery();
		testFixture = new ComplexGraphFixture(mockery, "");
		this.testFixture.buildFixture();
		this.testInstance = new CompoundSubgraphRemovalBuilder(this.testFixture.getGraph());
		
		mockSubgraph = this.mockery.mock(ISubCompoundGraph.class, "mockSubgraph");
		this.mockery.checking(new Expectations(){{
			allowing(mockSubgraph).getSuperGraph(); will(returnValue(testFixture.getGraph()));
			allowing(mockSubgraph).containsRoot(); will(returnValue(false));
			allowing(mockSubgraph).isConsistentSnapShot(); will(returnValue(false));
		}});
	}

	@After
	public void tearDown() throws Exception {
		this.mockery = null;
		this.testFixture = null;
		this.testInstance = null;
	}

	@Test(expected=PreConditionException.class)
	public void testCompoundSubgraphRemovalBuilder() {
		new CompoundSubgraphRemovalBuilder(null);
	}

	@Test
	public void testCanRemoveSubgraph() {
		assertFalse("cannot remove", this.testInstance.canRemoveSubgraph());
	}

	@Test
	public void testGetRemovalSubgraph() {
		assertNull("no removal subgraph", this.testInstance.getRemovalSubgraph());
	}

	@Test
	public void testSetRemovalSubgraph() {
		this.testInstance.setRemovalSubgraph(mockSubgraph);
		assertEquals("removal subgraph set", mockSubgraph, this.testInstance.getRemovalSubgraph());
	}

	@Test
	public void testGetGraph() {
		assertEquals("correct graph", this.testFixture.getGraph(), this.testInstance.getGraph());
	}

	@Test(expected=PreConditionException.class)
	public void testRemoveSubgraph() {
		this.testInstance.removeSubgraph();
	}

}
