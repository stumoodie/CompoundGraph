package uk.ac.ed.inf.graph.compound.newimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import uk.ac.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.ICompoundNodeFactory;
import uk.ac.ed.inf.graph.compound.IGraphStructureChangeAction;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraphFactory;
import uk.ac.ed.inf.graph.compound.testfixture.ComplexGraphFixture;
import uk.ac.ed.inf.graph.compound.testfixture.ElementAttributeFactory;
import uk.ac.ed.inf.graph.compound.testfixture.IEdgeConstructor;
import uk.ac.ed.inf.graph.compound.testfixture.IGraphTestFixture;

@RunWith(JMock.class)
public class CompoundChildEdgeFactoryTest {
	private Mockery mockery;
	private IGraphTestFixture testFixture;
	private ICompoundChildEdgeFactory testInstance;
	private CompoundNodePair testNodePair;
	private IGraphTestFixture otherTestFixture;
	private ElementAttributeFactory expectedAttributeFactory;
	
	@Before
	public void setUp() throws Exception {
		this.mockery = new JUnit4Mockery();
		
		this.otherTestFixture = new ComplexGraphFixture(mockery, "other");
		this.otherTestFixture.buildFixture();
		
		this.testFixture = new ComplexGraphFixture(mockery, "");
		
		this.testFixture.redefineEdge(ComplexGraphFixture.EDGE1_ID, new IEdgeConstructor() {
			
			@Override
			public ICompoundNodeFactory createNodeFactory(IChildCompoundGraph childGraph) {
				return null;
			}
			
			@Override
			public ICompoundEdgeFactory createEdgeFactory(final IChildCompoundGraph childGraph) {
				testInstance = new CompoundChildEdgeFactory(childGraph.getRoot());
				return testInstance;
			}
			
			@Override
			public ICompoundEdge createCompoundEdge() {
				return null;
			}
			
			@Override
			public IChildCompoundGraph createCompoundChildGraph(ICompoundEdge edge) {
				return null;
			}
			
			@Override
			public boolean buildNodeFactory(ICompoundNodeFactory nodeFactory) {
				return false;
			}
			
			@Override
			public boolean buildEdgeFactory(ICompoundEdgeFactory edgeFactory) {
				return true;
			}
			
			@Override
			public boolean buildEdge(ICompoundEdge edge) {
				return false;
			}
			
			@Override
			public boolean buildChildGraph(IChildCompoundGraph childGraph) {
				return false;
			}
		});
		this.testFixture.buildFixture();
		this.testNodePair = new CompoundNodePair(this.testFixture.getNode(ComplexGraphFixture.NODE5_ID), this.testFixture.getNode(ComplexGraphFixture.NODE3_ID));
		this.expectedAttributeFactory = new ElementAttributeFactory();
		this.expectedAttributeFactory.setName("new att");
		this.testInstance.setAttributeFactory(this.expectedAttributeFactory);
		this.testInstance.setPair(this.testNodePair);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(expected=PreConditionException.class)
	public void testCompoundChildEdgeFactoryWithNull() {
		new CompoundChildEdgeFactory(null);
	}

	@Test
	public void testCanCreateEdge() {
		assertTrue("can create edge", this.testInstance.canCreateEdge());
	}

	@Test
	public void testCreateEdge() {
		final ICompoundNode node3 = this.testFixture.getNode(ComplexGraphFixture.NODE3_ID);
		final ICompoundNode node5 = this.testFixture.getNode(ComplexGraphFixture.NODE5_ID);
		final ICompoundEdge edge1 = this.testFixture.getEdge(ComplexGraphFixture.EDGE1_ID);
		final ISubCompoundGraphFactory mockFact = testFixture.getGraph().subgraphFactory();
		mockery.checking(new Expectations(){{
			one(node3).addInEdge(with(any(ICompoundEdge.class)));
			one(node5).addOutEdge(with(any(ICompoundEdge.class)));
			one(edge1.getChildCompoundGraph()).addEdge(with(any(ICompoundEdge.class)));
			one(mockFact).addElement(with(any(ICompoundEdge.class)));
			one(testFixture.getGraph()).notifyGraphStructureChange(with(any(IGraphStructureChangeAction.class)));
		}});
		ICompoundEdge edge = this.testInstance.createEdge();
		assertNotNull("edge exists", edge);
		assertEquals("expected att links", edge, edge.getAttribute().getCurrentElement());
		mockery.assertIsSatisfied();
	}

	@Test
	public void testGetCurrentNodePair() {
		assertEquals("expected node pair", this.testNodePair, this.testInstance.getCurrentNodePair());
	}

	@Test
	public void testGetGraph() {
		assertEquals("expected graph", this.testFixture.getGraph(), this.testInstance.getGraph());
	}

	@Test
	public void testGetParent() {
		assertEquals("exected parent", this.testFixture.getEdge(ComplexGraphFixture.EDGE1_ID), this.testInstance.getParent());
	}

	@Test
	public void testIsValidNodePair() {
		assertTrue("valid node pair", this.testInstance.isValidNodePair(testNodePair));
	}

	@Test
	public void testIsValidNodePairNull() {
		assertFalse("invalid node pair", this.testInstance.isValidNodePair(null));
	}

	@Test
	public void testIsValidNodePairOtherGraph() {
		assertFalse("invalid node pair", this.testInstance.isValidNodePair(this.otherTestFixture.getEdge(ComplexGraphFixture.EDGE2_ID).getConnectedNodes()));
	}

	@Test
	public void testSetPair() {
		CompoundNodePair altExpectedPair = this.testNodePair.reversedNodes();
		this.testInstance.setPair(altExpectedPair);
		assertEquals("expected pair", altExpectedPair, this.testInstance.getCurrentNodePair());
	}

	@Test(expected=PreConditionException.class)
	public void testSetPairWrongLCN() {
		CompoundNodePair altExpectedPair = new CompoundNodePair(this.testFixture.getNode(ComplexGraphFixture.NODE1_ID), this.testFixture.getNode(ComplexGraphFixture.NODE3_ID));
		this.testInstance.setPair(altExpectedPair);
	}

	@Test(expected=PreConditionException.class)
	public void testSetPairNull() {
		this.testInstance.setPair(null);
	}

}
