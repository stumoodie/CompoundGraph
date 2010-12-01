package uk.ac.ed.inf.graph.compound.newimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.ed.inf.graph.compound.IGraphStructureChangeAction;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraphFactory;
import uk.ac.ed.inf.graph.compound.ISubgraphRemovalBuilder;
import uk.ac.ed.inf.graph.compound.testfixture.ComplexGraphFixture;
import uk.ac.ed.inf.graph.compound.testfixture.IGraphTestFixture;

@RunWith(JMock.class)
public class CompoundSubgraphRemovalBuilderWithSubgraphTest {
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
			allowing(mockSubgraph).isConsistentSnapShot(); will(returnValue(true));
			allowing(mockSubgraph).elementIterator(); will(returnValue(testFixture.getNode(ComplexGraphFixture.NODE2_ID).levelOrderIterator()));
		}});
		this.testInstance.setRemovalSubgraph(mockSubgraph);
	}

	@After
	public void tearDown() throws Exception {
		this.mockery = null;
		this.testFixture = null;
		this.testInstance = null;
	}

	@Test
	public void testCanRemoveSubgraph() {
		assertTrue("can remove", this.testInstance.canRemoveSubgraph());
	}

	@Test
	public void testGetRemovalSubgraph() {
		assertEquals("expected removal subgraph", this.mockSubgraph, this.testInstance.getRemovalSubgraph());
	}

	@Test
	public void testRemoveSubgraph() {
		final ISubCompoundGraphFactory mocksubgraphFactory = this.testFixture.getGraph().subgraphFactory();
		this.mockery.checking(new Expectations(){{
			one(testFixture.getNode(ComplexGraphFixture.NODE2_ID)).markRemoved(true); will(testFixture.setRemovalState(ComplexGraphFixture.NODE2_ID));
			one(testFixture.getEdge(ComplexGraphFixture.EDGE3_ID)).markRemoved(true); will(testFixture.setRemovalState(ComplexGraphFixture.EDGE3_ID));
			one(testFixture.getEdge(ComplexGraphFixture.EDGE4_ID)).markRemoved(true); will(testFixture.setRemovalState(ComplexGraphFixture.EDGE4_ID));
			
			exactly(1).of(mocksubgraphFactory).addElement(with(testFixture.getEdge(ComplexGraphFixture.EDGE4_ID)));
			exactly(1).of(mocksubgraphFactory).addElement(with(testFixture.getEdge(ComplexGraphFixture.EDGE3_ID)));
			exactly(1).of(mocksubgraphFactory).addElement(with(testFixture.getNode(ComplexGraphFixture.NODE2_ID)));

			ignoring(mocksubgraphFactory).createSubgraph();
			
			one(testFixture.getGraph()).notifyGraphStructureChange(with(any(IGraphStructureChangeAction.class)));
		}});
		this.testInstance.removeSubgraph();
		this.mockery.assertIsSatisfied();
	}

}
