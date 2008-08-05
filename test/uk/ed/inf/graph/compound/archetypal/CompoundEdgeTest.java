package uk.ed.inf.graph.compound.archetypal;

import static org.junit.Assert.assertEquals;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class CompoundEdgeTest {
	private Mockery mockery = new JUnit4Mockery() {{
		setImposteriser(ClassImposteriser.INSTANCE);
	}};

	private static int EXPECTED_EDGE_IDX = 10;
	private static int EXPECTED_EDGE1_IDX = 1;
	private static int EXPECTED_EDGE2_IDX = 100;
	private static int EXPECTED_IN_NODE_IDX = 1;
	private static int EXPECTED_OUT_NODE_IDX = 1;
	private ArchetypalCompoundEdge testInstance;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@SuppressWarnings("unchecked")
	@Test
	public final void testCiEdge() {
		final ArchetypalChildCompoundGraph mockSubgraph = this.mockery.mock(ArchetypalChildCompoundGraph.class, "mockSubgraph");
		final ArchetypalCompoundGraph mockGraph = this.mockery.mock(ArchetypalCompoundGraph.class, "mockGraph");
		final ArchetypalCompoundNode mockInNode = this.mockery.mock(ArchetypalCompoundNode.class, "mockInNode");
		final ArchetypalCompoundNode mockOutNode = this.mockery.mock(ArchetypalCompoundNode.class, "mockOutNode");
//		final IEdgeColourHandler<CompoundNode, CompoundEdge> colourHandler = this.mockery.mock(IEdgeColourHandler.class, "colourHandler");
		this.mockery.checking(new Expectations(){{
			allowing(mockInNode).getIndex(); will(returnValue(EXPECTED_IN_NODE_IDX));
			atLeast(1).of(mockInNode).addInEdge(with(any(ArchetypalCompoundEdge.class)));

			allowing(mockOutNode).getIndex(); will(returnValue(EXPECTED_OUT_NODE_IDX));
			atLeast(1).of(mockOutNode).addOutEdge(with(any(ArchetypalCompoundEdge.class)));
			
			atLeast(1).of(mockSubgraph).getSuperGraph(); will(returnValue(mockGraph));
		}});
		this.testInstance = new TestEdge(mockSubgraph, EXPECTED_EDGE_IDX, mockOutNode, mockInNode);
		assertEquals("expected in node", mockInNode, this.testInstance.getConnectedNodes().getInNode());
		assertEquals("expected out node", mockOutNode, this.testInstance.getConnectedNodes().getOutNode());
		assertEquals("expected edge idx", EXPECTED_EDGE_IDX, this.testInstance.getIndex());
		assertEquals("expected subgraph", mockSubgraph, this.testInstance.getOwningChildGraph());
		assertEquals("expected graph", mockGraph, this.testInstance.getGraph());
		this.mockery.assertIsSatisfied();
	}

	@SuppressWarnings("unchecked")
	@Test
	public final void testCompareTo() {
		final ArchetypalChildCompoundGraph mockSubgraph = this.mockery.mock(ArchetypalChildCompoundGraph.class, "mockSubgraph");
		final ArchetypalCompoundNode mockInNode = this.mockery.mock(ArchetypalCompoundNode.class, "mockInNode");
		final ArchetypalCompoundNode mockOutNode = this.mockery.mock(ArchetypalCompoundNode.class, "mockOutNode");
//		final IEdgeColourHandler<CompoundNode, CompoundEdge> colourHandler = this.mockery.mock(IEdgeColourHandler.class, "colourHandler");
		this.mockery.checking(new Expectations(){{
			allowing(mockInNode).getIndex(); will(returnValue(EXPECTED_IN_NODE_IDX));
			atLeast(1).of(mockInNode).addInEdge(with(any(ArchetypalCompoundEdge.class)));

			allowing(mockOutNode).getIndex(); will(returnValue(EXPECTED_OUT_NODE_IDX));
			atLeast(1).of(mockOutNode).addOutEdge(with(any(ArchetypalCompoundEdge.class)));
		}});
		this.testInstance = new TestEdge(mockSubgraph, EXPECTED_EDGE_IDX, mockOutNode, mockInNode);
		this.mockery.assertIsSatisfied();
		final ArchetypalCompoundEdge mockEdge1 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge1");
		final ArchetypalCompoundEdge mockEdge2 = this.mockery.mock(ArchetypalCompoundEdge.class, "mockEdge2");
		this.mockery.checking(new Expectations(){{
			allowing(mockEdge1).getIndex(); will(returnValue(EXPECTED_EDGE1_IDX));

			allowing(mockEdge2).getIndex(); will(returnValue(EXPECTED_EDGE2_IDX));
		}});
		assertEquals("compare less than", 1, this.testInstance.compareTo(mockEdge1));
		assertEquals("compare less than", 0, this.testInstance.compareTo(this.testInstance));
		assertEquals("compare less than", -1, this.testInstance.compareTo(mockEdge2));
		this.mockery.assertIsSatisfied();
	}
	
	private static class TestEdge extends ArchetypalCompoundEdge {

		protected TestEdge(ArchetypalChildCompoundGraph owningSubgraph,
				int index, ArchetypalCompoundNode outNode,
				ArchetypalCompoundNode inNode) {
			super(owningSubgraph, index, outNode, inNode);
		}
		
	}
}
