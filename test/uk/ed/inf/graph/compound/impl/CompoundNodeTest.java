package uk.ed.inf.graph.compound.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ed.inf.graph.directed.IDirectedPair;


@RunWith(JMock.class)
public class CompoundNodeTest {
	private Mockery mockery = new JUnit4Mockery() {{
		setImposteriser(ClassImposteriser.INSTANCE);
	}};
	private CompoundNode testInstance;

	private static final int EXPECTED_NODE1_IDX = 2;
	private static final int EXPECTED_INITIAL_DEGREE = 0;
	private static final int EXPECTED_CHILD_NODE1_IDX = 1; 
	private static final int EXPECTED_CHILD_NODE2_IDX = 3;
	private static final int EXPECTED_IN_DEGREE = 2; 
	private static final int EXPECTED_OUT_DEGREE = 3; 
	private static final int EXPECTED_DEGREE = EXPECTED_IN_DEGREE + EXPECTED_OUT_DEGREE; 
	
	@Before
	public void setUp() throws Exception {
		this.testInstance = null;
	}

	@After
	public void tearDown() throws Exception {
		this.testInstance = null;
	}

	@Test
	public final void testCiNodeCigraphInt() {
		final CompoundGraph mockGraph = this.mockery.mock(CompoundGraph.class, "mockGraph");
		this.mockery.checking(new Expectations(){{
			
		}});

		this.testInstance = new CompoundNode(mockGraph, EXPECTED_NODE1_IDX);
		assertEquals("expected index", EXPECTED_NODE1_IDX, this.testInstance.getIndex());
		assertEquals("expected graph", mockGraph, this.testInstance.getGraph());
		assertEquals("expected parent", this.testInstance, this.testInstance.getParent());
		assertNotNull("expected subgraph exists", this.testInstance.getChildCigraph());
		assertEquals("expected subgraph root", this.testInstance, this.testInstance.getChildCigraph().getRoot());
		assertEquals("expected initial degree", EXPECTED_INITIAL_DEGREE, this.testInstance.getDegree());
		assertEquals("expected initial degree", EXPECTED_INITIAL_DEGREE, this.testInstance.getInDegree());
		assertEquals("expected initial degree", EXPECTED_INITIAL_DEGREE, this.testInstance.getOutDegree());
		assertTrue("expected empty child iter", this.testInstance.childIterator().hasNext() == false);
		assertTrue("expected empty edge iter", this.testInstance.edgeIterator().hasNext() == false);
		assertTrue("expected empty in edge iter", this.testInstance.getInEdgeIterator().hasNext() == false);
		assertTrue("expected empty out edge iter", this.testInstance.getOutEdgeIterator().hasNext() == false);
		assertTrue("expected empty in node iter", this.testInstance.getInNodeIterator().hasNext() == false);
		assertTrue("expected empty out node iter", this.testInstance.getOutNodeIterator().hasNext() == false);
		this.mockery.assertIsSatisfied();
	}

	@Test
	public final void testCiNodeCiNodeInt() {
		final CompoundGraph mockGraph = this.mockery.mock(CompoundGraph.class, "mockSubgraph");
		final CompoundNode mockParentNode = this.mockery.mock(CompoundNode.class, "mockParentNode");
		this.mockery.checking(new Expectations(){{
			atLeast(1).of(mockParentNode).getGraph(); will(returnValue(mockGraph));
		}});

		this.testInstance = new CompoundNode(mockParentNode, EXPECTED_NODE1_IDX);
		assertEquals("expected index", EXPECTED_NODE1_IDX, this.testInstance.getIndex());
		assertEquals("expected graph", mockGraph, this.testInstance.getGraph());
		assertEquals("expected parent", mockParentNode, this.testInstance.getParent());
		assertNotNull("expected subgraph exists", this.testInstance.getChildCigraph());
		assertEquals("expected subgraph root", this.testInstance, this.testInstance.getChildCigraph().getRoot());
		assertEquals("expected initial degree", EXPECTED_INITIAL_DEGREE, this.testInstance.getDegree());
		assertEquals("expected initial degree", EXPECTED_INITIAL_DEGREE, this.testInstance.getInDegree());
		assertEquals("expected initial degree", EXPECTED_INITIAL_DEGREE, this.testInstance.getOutDegree());
		assertTrue("expected empty child iter", this.testInstance.childIterator().hasNext() == false);
		assertTrue("expected empty edge iter", this.testInstance.edgeIterator().hasNext() == false);
		assertTrue("expected empty in edge iter", this.testInstance.getInEdgeIterator().hasNext() == false);
		assertTrue("expected empty out edge iter", this.testInstance.getOutEdgeIterator().hasNext() == false);
		assertTrue("expected empty in node iter", this.testInstance.getInNodeIterator().hasNext() == false);
		assertTrue("expected empty out node iter", this.testInstance.getOutNodeIterator().hasNext() == false);
		this.mockery.assertIsSatisfied();
	}

	@Test
	public final void testChildIterator() {
		final CompoundGraph mockGraph = this.mockery.mock(CompoundGraph.class, "mockSubgraph");
		final CompoundNode mockParentNode = this.mockery.mock(CompoundNode.class, "mockParentNode");
		final CompoundNode mockChildNode1 = this.mockery.mock(CompoundNode.class, "mockChildNode1");
		final CompoundNode mockChildNode2 = this.mockery.mock(CompoundNode.class, "mockChildNode2");
		this.mockery.checking(new Expectations(){{
			atLeast(1).of(mockParentNode).getGraph(); will(returnValue(mockGraph));
			
			allowing(mockChildNode1).compareTo(mockChildNode2); will(returnValue(-1));
			allowing(mockChildNode1).compareTo(mockChildNode1); will(returnValue(0));
			
			allowing(mockChildNode2).compareTo(mockChildNode1); will(returnValue(1));
			allowing(mockChildNode2).compareTo(mockChildNode2); will(returnValue(0));
		
		}});

		this.testInstance = new CompoundNode(mockParentNode, EXPECTED_NODE1_IDX);
		this.testInstance.getChildCigraph().addNewNode(mockChildNode1);
		this.testInstance.getChildCigraph().addNewNode(mockChildNode2);
		Iterator<CompoundNode> iter = this.testInstance.childIterator();
		assertTrue("expected node iter", iter.hasNext());
		assertEquals("expected node iter", mockChildNode1, iter.next());
		assertTrue("expected node iter", iter.hasNext());
		assertEquals("expected node iter", mockChildNode2, iter.next());
		assertTrue("expected node iter", iter.hasNext() == false);
		this.mockery.assertIsSatisfied();
	}

	@Test
	public final void testGetInDegree() {
		final CompoundGraph mockGraph = this.mockery.mock(CompoundGraph.class, "mockSubgraph");
		final CompoundNode mockParentNode = this.mockery.mock(CompoundNode.class, "mockParentNode");
		final CompoundEdge mockInEdge1 = this.mockery.mock(CompoundEdge.class, "mockInEdge1");
		final CompoundEdge mockInEdge2 = this.mockery.mock(CompoundEdge.class, "mockInEdge2");
		final CompoundEdge mockOutEdge1 = this.mockery.mock(CompoundEdge.class, "mockOutEdge1");
		final CompoundEdge mockOutEdge2 = this.mockery.mock(CompoundEdge.class, "mockOutEdge2");
		this.mockery.checking(new Expectations(){{
			atLeast(1).of(mockParentNode).getGraph(); will(returnValue(mockGraph));

			allowing(mockInEdge1).compareTo(mockInEdge2); will(returnValue(-1));
			allowing(mockInEdge1).compareTo(mockInEdge1); will(returnValue(0));
			
			allowing(mockInEdge2).compareTo(mockInEdge1); will(returnValue(1));

			allowing(mockOutEdge1).compareTo(mockOutEdge2); will(returnValue(-1));
			allowing(mockOutEdge1).compareTo(mockOutEdge1); will(returnValue(0));
			
			allowing(mockOutEdge2).compareTo(mockOutEdge1); will(returnValue(1));
		}});

		this.testInstance = new CompoundNode(mockParentNode, EXPECTED_NODE1_IDX);
		this.testInstance.addInEdge(mockInEdge1);
		this.testInstance.addInEdge(mockInEdge2);
		this.testInstance.addOutEdge(mockOutEdge1);
		this.testInstance.addOutEdge(mockOutEdge2);
		assertEquals("expected in degree", EXPECTED_IN_DEGREE, this.testInstance.getInDegree());
		this.mockery.assertIsSatisfied();
	}

	@SuppressWarnings("unchecked")
	@Test
	public final void testGetInEdgeFrom() {
		final CompoundGraph mockGraph = this.mockery.mock(CompoundGraph.class, "mockSubgraph");
		final CompoundNode mockParentNode = this.mockery.mock(CompoundNode.class, "mockParentNode");
		final CompoundEdge mockInEdge1 = this.mockery.mock(CompoundEdge.class, "mockInEdge1");
		final CompoundEdge mockInEdge2 = this.mockery.mock(CompoundEdge.class, "mockInEdge2");
		final CompoundNode mockOutNode2 = this.mockery.mock(CompoundNode.class, "mockOutNode2");
		final IDirectedPair<CompoundNode, CompoundEdge> mockPair1 = this.mockery.mock(IDirectedPair.class, "mockPair1");
		final IDirectedPair<CompoundNode, CompoundEdge> mockPair2 = this.mockery.mock(IDirectedPair.class, "mockPair2");
		this.mockery.checking(new Expectations(){{
			atLeast(1).of(mockParentNode).getGraph(); will(returnValue(mockGraph));

			allowing(mockInEdge1).compareTo(mockInEdge2); will(returnValue(-1));
			allowing(mockInEdge1).compareTo(mockInEdge1); will(returnValue(0));
			allowing(mockInEdge1).getConnectedNodes(); will(returnValue(mockPair1));
			
			allowing(mockInEdge2).compareTo(mockInEdge1); will(returnValue(1));
			allowing(mockInEdge2).getConnectedNodes(); will(returnValue(mockPair2));
			
			allowing(mockPair1).containsNode(mockOutNode2); will(returnValue(false));

			allowing(mockPair2).containsNode(mockOutNode2); will(returnValue(true));
		}});

		this.testInstance = new CompoundNode(mockParentNode, EXPECTED_NODE1_IDX);
		this.testInstance.addInEdge(mockInEdge1);
		this.testInstance.addInEdge(mockInEdge2);
		CompoundEdge expectedEdge = mockInEdge2;
		CompoundEdge actualEdge = this.testInstance.getInEdgesFrom(mockOutNode2).first();
		assertEquals("expected edge", expectedEdge, actualEdge);
		this.mockery.assertIsSatisfied();
	}

	@Test
	public final void testGetInEdgeIterator() {
		final CompoundGraph mockGraph = this.mockery.mock(CompoundGraph.class, "mockSubgraph");
		final CompoundNode mockParentNode = this.mockery.mock(CompoundNode.class, "mockParentNode");
		final CompoundEdge mockInEdge1 = this.mockery.mock(CompoundEdge.class, "mockInEdge1");
		final CompoundEdge mockInEdge2 = this.mockery.mock(CompoundEdge.class, "mockInEdge2");
		final CompoundEdge mockOutEdge1 = this.mockery.mock(CompoundEdge.class, "mockOutEdge1");
		final CompoundEdge mockOutEdge2 = this.mockery.mock(CompoundEdge.class, "mockOutEdge2");
		this.mockery.checking(new Expectations(){{
			atLeast(1).of(mockParentNode).getGraph(); will(returnValue(mockGraph));

		}});

		this.testInstance = new CompoundNode(mockParentNode, EXPECTED_NODE1_IDX);
		this.testInstance.addInEdge(mockInEdge1);
		this.testInstance.addInEdge(mockInEdge2);
		this.testInstance.addOutEdge(mockOutEdge1);
		this.testInstance.addOutEdge(mockOutEdge2);
		Iterator<CompoundEdge> iter = this.testInstance.getInEdgeIterator();
		assertTrue("expected in edge iter", iter.hasNext());
		assertEquals("expected edge iter", mockInEdge1, iter.next());
		assertTrue("expected in edge iter", iter.hasNext());
		assertEquals("expected edge iter", mockInEdge2, iter.next());
		assertTrue("expected in edge iter", iter.hasNext() == false);
		this.mockery.assertIsSatisfied();
	}

	@SuppressWarnings("unchecked")
	@Test
	public final void testGetInNodeIterator() {
		final CompoundGraph mockGraph = new CompoundGraph();
		final CompoundNode mockParentNode = mockGraph.getRoot();
		this.testInstance = mockParentNode.getChildCigraph().nodeFactory().createNode();
		final CompoundNode mockInNode1 = mockParentNode.getChildCigraph().nodeFactory().createNode();
		final CompoundNode mockInNode2 = mockParentNode.getChildCigraph().nodeFactory().createNode();
		final CompoundNode mockOutNode1 = mockParentNode.getChildCigraph().nodeFactory().createNode();
		final CompoundNode mockOutNode2 = mockParentNode.getChildCigraph().nodeFactory().createNode();
		CompoundEdgeFactory edgeFact = mockGraph.edgeFactory();
		edgeFact.setPair(mockOutNode1, this.testInstance);
		edgeFact.createEdge();
		edgeFact.setPair(mockOutNode2, this.testInstance);
		edgeFact.createEdge();;
		edgeFact.setPair(this.testInstance, mockInNode1);
		edgeFact.createEdge();
		edgeFact.setPair(this.testInstance, mockInNode2);
		edgeFact.createEdge();
		Iterator<CompoundNode> iter = this.testInstance.getInNodeIterator();
		boolean actualNextNode1 = iter.hasNext();
		CompoundNode actualNode1 =  iter.next();
		boolean actualNextNode2 = iter.hasNext();
		CompoundNode actualNode2 =  iter.next();
		boolean actualNextNode3 = iter.hasNext();

		assertTrue("expected in node iter", actualNextNode1);
		assertEquals("expected in node iter", mockOutNode1, actualNode1);
		assertTrue("expected in node iter", actualNextNode2);
		assertEquals("expected in node iter", mockOutNode2, actualNode2);
		assertTrue("expected in node iter", actualNextNode3 == false);
	}

	@Test
	public final void testGetOutDegree() {
		final CompoundGraph mockGraph = this.mockery.mock(CompoundGraph.class, "mockSubgraph");
		final CompoundNode mockParentNode = this.mockery.mock(CompoundNode.class, "mockParentNode");
		final CompoundEdge mockInEdge1 = this.mockery.mock(CompoundEdge.class, "mockInEdge1");
		final CompoundEdge mockInEdge2 = this.mockery.mock(CompoundEdge.class, "mockInEdge2");
		final CompoundEdge mockOutEdge1 = this.mockery.mock(CompoundEdge.class, "mockOutEdge1");
		final CompoundEdge mockOutEdge2 = this.mockery.mock(CompoundEdge.class, "mockOutEdge2");
		final CompoundEdge mockOutEdge3 = this.mockery.mock(CompoundEdge.class, "mockOutEdge3");
		this.mockery.checking(new Expectations(){{
			atLeast(1).of(mockParentNode).getGraph(); will(returnValue(mockGraph));

			allowing(mockInEdge1).compareTo(mockInEdge2); will(returnValue(-1));
			allowing(mockInEdge1).compareTo(mockInEdge1); will(returnValue(0));
			
			allowing(mockInEdge2).compareTo(mockInEdge1); will(returnValue(1));

			allowing(mockOutEdge1).compareTo(mockOutEdge2); will(returnValue(-1));
			allowing(mockOutEdge1).compareTo(mockOutEdge1); will(returnValue(0));
			allowing(mockOutEdge1).compareTo(mockOutEdge3); will(returnValue(-1));
			
			allowing(mockOutEdge2).compareTo(mockOutEdge1); will(returnValue(1));
			allowing(mockOutEdge2).compareTo(mockOutEdge2); will(returnValue(0));
			allowing(mockOutEdge2).compareTo(mockOutEdge3); will(returnValue(-1));

			allowing(mockOutEdge3).compareTo(mockOutEdge1); will(returnValue(1));
			allowing(mockOutEdge3).compareTo(mockOutEdge3); will(returnValue(0));
			allowing(mockOutEdge3).compareTo(mockOutEdge2); will(returnValue(1));
		}});

		this.testInstance = new CompoundNode(mockParentNode, EXPECTED_NODE1_IDX);
		this.testInstance.addInEdge(mockInEdge1);
		this.testInstance.addInEdge(mockInEdge2);
		this.testInstance.addOutEdge(mockOutEdge1);
		this.testInstance.addOutEdge(mockOutEdge2);
		this.testInstance.addOutEdge(mockOutEdge3);
		assertEquals("expected out degree", EXPECTED_OUT_DEGREE, this.testInstance.getOutDegree());
		this.mockery.assertIsSatisfied();
	}

	@Test
	public final void testGetOutEdgeIterator() {
		final CompoundGraph mockGraph = this.mockery.mock(CompoundGraph.class, "mockSubgraph");
		final CompoundNode mockParentNode = this.mockery.mock(CompoundNode.class, "mockParentNode");
		final CompoundEdge mockInEdge1 = this.mockery.mock(CompoundEdge.class, "mockInEdge1");
		final CompoundEdge mockInEdge2 = this.mockery.mock(CompoundEdge.class, "mockInEdge2");
		final CompoundEdge mockOutEdge1 = this.mockery.mock(CompoundEdge.class, "mockOutEdge1");
		final CompoundEdge mockOutEdge2 = this.mockery.mock(CompoundEdge.class, "mockOutEdge2");
		this.mockery.checking(new Expectations(){{
			atLeast(1).of(mockParentNode).getGraph(); will(returnValue(mockGraph));

			allowing(mockInEdge1).compareTo(mockInEdge2); will(returnValue(-1));
			allowing(mockInEdge1).compareTo(mockInEdge1); will(returnValue(0));
			
			allowing(mockInEdge2).compareTo(mockInEdge1); will(returnValue(1));

			allowing(mockOutEdge1).compareTo(mockOutEdge2); will(returnValue(-1));
			allowing(mockOutEdge1).compareTo(mockOutEdge1); will(returnValue(0));
			
			allowing(mockOutEdge2).compareTo(mockOutEdge1); will(returnValue(1));
		}});

		this.testInstance = new CompoundNode(mockParentNode, EXPECTED_NODE1_IDX);
		this.testInstance.addInEdge(mockInEdge1);
		this.testInstance.addInEdge(mockInEdge2);
		this.testInstance.addOutEdge(mockOutEdge1);
		this.testInstance.addOutEdge(mockOutEdge2);
		Iterator<CompoundEdge> iter = this.testInstance.getOutEdgeIterator();
		assertTrue("expected out edge iter", iter.hasNext());
		assertEquals("expected edge iter", mockOutEdge1, iter.next());
		assertTrue("expected out edge iter", iter.hasNext());
		assertEquals("expected edge iter", mockOutEdge2, iter.next());
		assertTrue("expected out edge iter", iter.hasNext() == false);
		this.mockery.assertIsSatisfied();
	}

	@SuppressWarnings("unchecked")
	@Test
	public final void testGetOutEdgeTo() {
		final CompoundGraph mockGraph = this.mockery.mock(CompoundGraph.class, "mockSubgraph");
		final CompoundNode mockParentNode = this.mockery.mock(CompoundNode.class, "mockParentNode");
		final CompoundEdge mockOutEdge1 = this.mockery.mock(CompoundEdge.class, "mockOutEdge1");
		final CompoundEdge mockOutEdge2 = this.mockery.mock(CompoundEdge.class, "mockOutEdge2");
		final CompoundNode mockInNode2 = this.mockery.mock(CompoundNode.class, "mockInNode2");
		final IDirectedPair<CompoundNode, CompoundEdge> mockPair1 = this.mockery.mock(IDirectedPair.class, "mockPair1");
		final IDirectedPair<CompoundNode, CompoundEdge> mockPair2 = this.mockery.mock(IDirectedPair.class, "mockPair2");
		this.mockery.checking(new Expectations(){{
			atLeast(1).of(mockParentNode).getGraph(); will(returnValue(mockGraph));

			allowing(mockOutEdge1).compareTo(mockOutEdge2); will(returnValue(-1));
			allowing(mockOutEdge1).compareTo(mockOutEdge1); will(returnValue(0));
			allowing(mockOutEdge1).getConnectedNodes(); will(returnValue(mockPair1));
			
			allowing(mockOutEdge2).compareTo(mockOutEdge1); will(returnValue(1));
			allowing(mockOutEdge2).getConnectedNodes(); will(returnValue(mockPair2));
			
			allowing(mockPair1).containsNode(mockInNode2); will(returnValue(false));

			allowing(mockPair2).containsNode(mockInNode2); will(returnValue(true));
		}});

		this.testInstance = new CompoundNode(mockParentNode, EXPECTED_NODE1_IDX);
		this.testInstance.addInEdge(mockOutEdge1);
		this.testInstance.addInEdge(mockOutEdge2);
		CompoundEdge expectedEdge = mockOutEdge2;
		CompoundEdge actualEdge = this.testInstance.getInEdgesFrom(mockInNode2).first();
		assertEquals("expected edge", expectedEdge, actualEdge);
		this.mockery.assertIsSatisfied();
	}

	@Test
	public final void testGetOutNodeIterator() {
		final CompoundGraph mockGraph = new CompoundGraph();
		final CompoundNode mockParentNode = mockGraph.getRoot();
		this.testInstance = mockParentNode.getChildCigraph().nodeFactory().createNode();
		final CompoundNode mockInNode1 = mockParentNode.getChildCigraph().nodeFactory().createNode();
		final CompoundNode mockInNode2 = mockParentNode.getChildCigraph().nodeFactory().createNode();
		final CompoundNode mockOutNode1 = mockParentNode.getChildCigraph().nodeFactory().createNode();
		final CompoundNode mockOutNode2 = mockParentNode.getChildCigraph().nodeFactory().createNode();
		CompoundEdgeFactory edgeFact = mockGraph.edgeFactory();
		edgeFact.setPair(mockOutNode1, this.testInstance);
		edgeFact.createEdge();
		edgeFact.setPair(mockOutNode2, this.testInstance);
		edgeFact.createEdge();;
		edgeFact.setPair(this.testInstance, mockInNode1);
		edgeFact.createEdge();
		edgeFact.setPair(this.testInstance, mockInNode2);
		edgeFact.createEdge();
		Iterator<CompoundNode> iter = this.testInstance.getOutNodeIterator();
		boolean actualNextNode1 = iter.hasNext();
		CompoundNode actualNode1 =  iter.next();
		boolean actualNextNode2 = iter.hasNext();
		CompoundNode actualNode2 =  iter.next();
		boolean actualNextNode3 = iter.hasNext();

		assertTrue("expected out node iter", actualNextNode1);
		assertEquals("expected out node iter", mockInNode1, actualNode1);
		assertTrue("expected out node iter", actualNextNode2);
		assertEquals("expected out node iter", mockInNode2, actualNode2);
		assertTrue("expected out node iter", actualNextNode3 == false);
	}

	@SuppressWarnings("unchecked")
	@Test
	public final void testHasInEdgeFrom() {
		final CompoundGraph mockGraph = this.mockery.mock(CompoundGraph.class, "mockSubgraph");
		final CompoundNode mockParentNode = this.mockery.mock(CompoundNode.class, "mockParentNode");
		final CompoundEdge mockInEdge1 = this.mockery.mock(CompoundEdge.class, "mockInEdge1");
		final CompoundEdge mockInEdge2 = this.mockery.mock(CompoundEdge.class, "mockInEdge2");
		final CompoundNode mockOutNode2 = this.mockery.mock(CompoundNode.class, "mockOutNode2");
		final IDirectedPair<CompoundNode, CompoundEdge> mockPair1 = this.mockery.mock(IDirectedPair.class, "mockPair1");
		final IDirectedPair<CompoundNode, CompoundEdge> mockPair2 = this.mockery.mock(IDirectedPair.class, "mockPair2");
		this.mockery.checking(new Expectations(){{
			atLeast(1).of(mockParentNode).getGraph(); will(returnValue(mockGraph));

			allowing(mockInEdge1).compareTo(mockInEdge2); will(returnValue(-1));
			allowing(mockInEdge1).compareTo(mockInEdge1); will(returnValue(0));
			allowing(mockInEdge1).getConnectedNodes(); will(returnValue(mockPair1));
			
			allowing(mockInEdge2).compareTo(mockInEdge1); will(returnValue(1));
			allowing(mockInEdge2).getConnectedNodes(); will(returnValue(mockPair2));
			
			allowing(mockPair1).containsNode(mockOutNode2); will(returnValue(false));

			allowing(mockPair2).containsNode(mockOutNode2); will(returnValue(true));
		}});

		this.testInstance = new CompoundNode(mockParentNode, EXPECTED_NODE1_IDX);
		this.testInstance.addInEdge(mockInEdge1);
		this.testInstance.addInEdge(mockInEdge2);
		assertTrue("expected edge", this.testInstance.hasInEdgeFrom(mockOutNode2));
		this.mockery.assertIsSatisfied();
	}

	@SuppressWarnings("unchecked")
	@Test
	public final void testHasOutEdgeTo() {
		final CompoundGraph mockGraph = this.mockery.mock(CompoundGraph.class, "mockSubgraph");
		final CompoundNode mockParentNode = this.mockery.mock(CompoundNode.class, "mockParentNode");
		final CompoundEdge mockOutEdge1 = this.mockery.mock(CompoundEdge.class, "mockOutEdge1");
		final CompoundEdge mockOutEdge2 = this.mockery.mock(CompoundEdge.class, "mockOutEdge2");
		final CompoundNode mockInNode2 = this.mockery.mock(CompoundNode.class, "mockInNode2");
		final IDirectedPair<CompoundNode, CompoundEdge> mockPair1 = this.mockery.mock(IDirectedPair.class, "mockPair1");
		final IDirectedPair<CompoundNode, CompoundEdge> mockPair2 = this.mockery.mock(IDirectedPair.class, "mockPair2");
		this.mockery.checking(new Expectations(){{
			atLeast(1).of(mockParentNode).getGraph(); will(returnValue(mockGraph));

			allowing(mockOutEdge1).compareTo(mockOutEdge2); will(returnValue(-1));
			allowing(mockOutEdge1).compareTo(mockOutEdge1); will(returnValue(0));
			allowing(mockOutEdge1).getConnectedNodes(); will(returnValue(mockPair1));
			
			allowing(mockOutEdge2).compareTo(mockOutEdge1); will(returnValue(1));
			allowing(mockOutEdge2).getConnectedNodes(); will(returnValue(mockPair2));
			
			allowing(mockPair1).containsNode(mockInNode2); will(returnValue(false));

			allowing(mockPair2).containsNode(mockInNode2); will(returnValue(true));
		}});

		this.testInstance = new CompoundNode(mockParentNode, EXPECTED_NODE1_IDX);
		this.testInstance.addInEdge(mockOutEdge1);
		this.testInstance.addInEdge(mockOutEdge2);
		assertTrue("expected edge", this.testInstance.hasOutEdgeTo(mockInNode2));
		this.mockery.assertIsSatisfied();
	}

	@Test
	public final void testGetConnectedNodeIterator() {
		final CompoundGraph mockGraph = new CompoundGraph();
		final CompoundNode mockParentNode = mockGraph.getRoot();
		this.testInstance = mockParentNode.getChildCigraph().nodeFactory().createNode();
		final CompoundNode mockInNode1 = mockParentNode.getChildCigraph().nodeFactory().createNode();
		final CompoundNode mockInNode2 = mockParentNode.getChildCigraph().nodeFactory().createNode();
		final CompoundNode mockOutNode1 = mockParentNode.getChildCigraph().nodeFactory().createNode();
		final CompoundNode mockOutNode2 = mockParentNode.getChildCigraph().nodeFactory().createNode();
		CompoundEdgeFactory edgeFact = mockGraph.edgeFactory();
		edgeFact.setPair(mockOutNode1, this.testInstance);
		edgeFact.createEdge();
		edgeFact.setPair(mockOutNode2, this.testInstance);
		edgeFact.createEdge();;
		edgeFact.setPair(this.testInstance, mockInNode1);
		edgeFact.createEdge();
		edgeFact.setPair(this.testInstance, mockInNode2);
		edgeFact.createEdge();
		Iterator<CompoundNode> iter = this.testInstance.connectedNodeIterator();
		boolean actualNextNode1 = iter.hasNext();
		CompoundNode actualNode1 =  iter.next();
		boolean actualNextNode2 = iter.hasNext();
		CompoundNode actualNode2 =  iter.next();
		boolean actualNextNode3 = iter.hasNext();
		CompoundNode actualNode3 =  iter.next();
		boolean actualNextNode4 = iter.hasNext();
		CompoundNode actualNode4 =  iter.next();
		boolean actualNextNode5 = iter.hasNext();

		assertTrue("expected node iter", actualNextNode1);
		assertEquals("expected node iter", mockOutNode1, actualNode1);
		assertTrue("expected node iter", actualNextNode2);
		assertEquals("expected node iter", mockOutNode2, actualNode2);
		assertTrue("expected node iter", actualNextNode3);
		assertEquals("expected node iter", mockInNode1, actualNode3);
		assertTrue("expected node iter", actualNextNode4);
		assertEquals("expected node iter", mockInNode2, actualNode4);
		assertTrue("expected node iter", actualNextNode5 == false);
	}

	@Test
	public final void testGetDegree() {
		final CompoundGraph mockGraph = this.mockery.mock(CompoundGraph.class, "mockSubgraph");
		final CompoundNode mockParentNode = this.mockery.mock(CompoundNode.class, "mockParentNode");
		final CompoundEdge mockInEdge1 = this.mockery.mock(CompoundEdge.class, "mockInEdge1");
		final CompoundEdge mockInEdge2 = this.mockery.mock(CompoundEdge.class, "mockInEdge2");
		final CompoundEdge mockOutEdge1 = this.mockery.mock(CompoundEdge.class, "mockOutEdge1");
		final CompoundEdge mockOutEdge2 = this.mockery.mock(CompoundEdge.class, "mockOutEdge2");
		final CompoundEdge mockOutEdge3 = this.mockery.mock(CompoundEdge.class, "mockOutEdge3");
		this.mockery.checking(new Expectations(){{
			atLeast(1).of(mockParentNode).getGraph(); will(returnValue(mockGraph));

			allowing(mockInEdge1).compareTo(mockInEdge2); will(returnValue(-1));
			allowing(mockInEdge1).compareTo(mockInEdge1); will(returnValue(0));
			
			allowing(mockInEdge2).compareTo(mockInEdge1); will(returnValue(1));

			allowing(mockOutEdge1).compareTo(mockOutEdge2); will(returnValue(-1));
			allowing(mockOutEdge1).compareTo(mockOutEdge1); will(returnValue(0));
			allowing(mockOutEdge1).compareTo(mockOutEdge3); will(returnValue(-1));
			
			allowing(mockOutEdge2).compareTo(mockOutEdge1); will(returnValue(1));
			allowing(mockOutEdge2).compareTo(mockOutEdge2); will(returnValue(0));
			allowing(mockOutEdge2).compareTo(mockOutEdge3); will(returnValue(-1));

			allowing(mockOutEdge3).compareTo(mockOutEdge1); will(returnValue(1));
			allowing(mockOutEdge3).compareTo(mockOutEdge3); will(returnValue(0));
			allowing(mockOutEdge3).compareTo(mockOutEdge2); will(returnValue(1));
		}});

		this.testInstance = new CompoundNode(mockParentNode, EXPECTED_NODE1_IDX);
		this.testInstance.addInEdge(mockInEdge1);
		this.testInstance.addInEdge(mockInEdge2);
		this.testInstance.addOutEdge(mockOutEdge1);
		this.testInstance.addOutEdge(mockOutEdge2);
		this.testInstance.addOutEdge(mockOutEdge3);
		assertEquals("expected degree", EXPECTED_DEGREE, this.testInstance.getDegree());
		this.mockery.assertIsSatisfied();
	}

	@Test
	public final void testGetEdgeIterator() {
		final CompoundGraph mockGraph = this.mockery.mock(CompoundGraph.class, "mockSubgraph");
		final CompoundNode mockParentNode = this.mockery.mock(CompoundNode.class, "mockParentNode");
		final CompoundEdge mockInEdge1 = this.mockery.mock(CompoundEdge.class, "mockInEdge1");
		final CompoundEdge mockInEdge2 = this.mockery.mock(CompoundEdge.class, "mockInEdge2");
		final CompoundEdge mockOutEdge1 = this.mockery.mock(CompoundEdge.class, "mockOutEdge1");
		final CompoundEdge mockOutEdge2 = this.mockery.mock(CompoundEdge.class, "mockOutEdge2");
		final CompoundEdge mockOutEdge3 = this.mockery.mock(CompoundEdge.class, "mockOutEdge3");
		this.mockery.checking(new Expectations(){{
			atLeast(1).of(mockParentNode).getGraph(); will(returnValue(mockGraph));

			allowing(mockInEdge1).compareTo(mockInEdge2); will(returnValue(-1));
			allowing(mockInEdge1).compareTo(mockInEdge1); will(returnValue(0));
			
			allowing(mockInEdge2).compareTo(mockInEdge1); will(returnValue(1));
			allowing(mockInEdge2).compareTo(mockInEdge2); will(returnValue(0));

			allowing(mockOutEdge1).compareTo(mockOutEdge2); will(returnValue(-1));
			allowing(mockOutEdge1).compareTo(mockOutEdge1); will(returnValue(0));
			allowing(mockOutEdge1).compareTo(mockOutEdge3); will(returnValue(-1));
			
			allowing(mockOutEdge2).compareTo(mockOutEdge1); will(returnValue(1));
			allowing(mockOutEdge2).compareTo(mockOutEdge2); will(returnValue(0));
			allowing(mockOutEdge2).compareTo(mockOutEdge3); will(returnValue(-1));

			allowing(mockOutEdge3).compareTo(mockOutEdge1); will(returnValue(1));
			allowing(mockOutEdge3).compareTo(mockOutEdge3); will(returnValue(0));
			allowing(mockOutEdge3).compareTo(mockOutEdge2); will(returnValue(1));
		}});

		this.testInstance = new CompoundNode(mockParentNode, EXPECTED_NODE1_IDX);
		this.testInstance.addInEdge(mockInEdge1);
		this.testInstance.addInEdge(mockInEdge2);
		this.testInstance.addOutEdge(mockOutEdge1);
		this.testInstance.addOutEdge(mockOutEdge2);
		this.testInstance.addOutEdge(mockOutEdge3);
		Iterator<CompoundEdge> iter = this.testInstance.edgeIterator();
		assertTrue("expected edge iter", iter.hasNext());
		assertEquals("expected edge iter", mockInEdge1, iter.next());
		assertTrue("expected edge iter", iter.hasNext());
		assertEquals("expected edge iter", mockInEdge2, iter.next());
		assertTrue("expected edge iter", iter.hasNext());
		assertEquals("expected edge iter", mockOutEdge1, iter.next());
		assertTrue("expected edge iter", iter.hasNext());
		assertEquals("expected edge iter", mockOutEdge2, iter.next());
		assertTrue("expected edge iter", iter.hasNext());
		assertEquals("expected edge iter", mockOutEdge3, iter.next());
		assertTrue("expected edge iter", iter.hasNext() == false);
		this.mockery.assertIsSatisfied();
	}

	@SuppressWarnings("unchecked")
	@Test
	public final void testGetEdgeWith() {
		final CompoundGraph mockGraph = this.mockery.mock(CompoundGraph.class, "mockSubgraph");
		final CompoundNode mockParentNode = this.mockery.mock(CompoundNode.class, "mockParentNode");
		final CompoundEdge mockInEdge1 = this.mockery.mock(CompoundEdge.class, "mockInEdge1");
		final CompoundEdge mockInEdge2 = this.mockery.mock(CompoundEdge.class, "mockInEdge2");
		final CompoundEdge mockOutEdge1 = this.mockery.mock(CompoundEdge.class, "mockOutEdge1");
		final CompoundEdge mockOutEdge2 = this.mockery.mock(CompoundEdge.class, "mockOutEdge2");
		final CompoundEdge mockOutEdge3 = this.mockery.mock(CompoundEdge.class, "mockOutEdge3");
		final CompoundNode mockInNode2 = this.mockery.mock(CompoundNode.class, "mockInNode2");
		final IDirectedPair<CompoundNode, CompoundEdge> mockOutPair1 = this.mockery.mock(IDirectedPair.class, "mockOutPair1");
		final IDirectedPair<CompoundNode, CompoundEdge> mockOutPair2 = this.mockery.mock(IDirectedPair.class, "mockOutPair2");
		final IDirectedPair<CompoundNode, CompoundEdge> mockOutPair3 = this.mockery.mock(IDirectedPair.class, "mockOutPair3");
		final IDirectedPair<CompoundNode, CompoundEdge> mockInPair1 = this.mockery.mock(IDirectedPair.class, "mockInPair1");
		final IDirectedPair<CompoundNode, CompoundEdge> mockInPair2 = this.mockery.mock(IDirectedPair.class, "mockInPair2");
		this.mockery.checking(new Expectations(){{
			atLeast(1).of(mockParentNode).getGraph(); will(returnValue(mockGraph));

			allowing(mockOutEdge1).compareTo(mockOutEdge2); will(returnValue(-1));
			allowing(mockOutEdge1).compareTo(mockOutEdge1); will(returnValue(0));
			allowing(mockOutEdge1).compareTo(mockOutEdge3); will(returnValue(-1));
			allowing(mockOutEdge1).getConnectedNodes(); will(returnValue(mockOutPair1));
			
			allowing(mockOutEdge2).compareTo(mockOutEdge1); will(returnValue(1));
			allowing(mockOutEdge2).compareTo(mockOutEdge2); will(returnValue(0));
			allowing(mockOutEdge2).compareTo(mockOutEdge3); will(returnValue(-1));
			allowing(mockOutEdge2).getConnectedNodes(); will(returnValue(mockOutPair2));
			
			allowing(mockOutEdge3).compareTo(mockOutEdge1); will(returnValue(1));
			allowing(mockOutEdge3).compareTo(mockOutEdge3); will(returnValue(0));
			allowing(mockOutEdge3).compareTo(mockOutEdge2); will(returnValue(1));
			allowing(mockOutEdge3).getConnectedNodes(); will(returnValue(mockOutPair3));

			allowing(mockInEdge1).compareTo(mockInEdge2); will(returnValue(-1));
			allowing(mockInEdge1).compareTo(mockInEdge1); will(returnValue(0));
			allowing(mockInEdge1).getConnectedNodes(); will(returnValue(mockInPair1));
			
			allowing(mockInEdge2).compareTo(mockInEdge1); will(returnValue(1));
			allowing(mockInEdge2).compareTo(mockInEdge2); will(returnValue(0));
			allowing(mockInEdge2).getConnectedNodes(); will(returnValue(mockInPair2));

			allowing(mockOutPair1).containsNode(mockInNode2); will(returnValue(false));

			allowing(mockOutPair2).containsNode(mockInNode2); will(returnValue(true));

			allowing(mockOutPair3).containsNode(mockInNode2); will(returnValue(false));

			allowing(mockInPair1).containsNode(mockInNode2); will(returnValue(false));

			allowing(mockInPair2).containsNode(mockInNode2); will(returnValue(false));
		}});

		this.testInstance = new CompoundNode(mockParentNode, EXPECTED_NODE1_IDX);
		this.testInstance.addInEdge(mockInEdge1);
		this.testInstance.addInEdge(mockInEdge2);
		this.testInstance.addOutEdge(mockOutEdge1);
		this.testInstance.addOutEdge(mockOutEdge2);
		this.testInstance.addOutEdge(mockOutEdge3);
		CompoundEdge expectedEdge = mockOutEdge2;
		CompoundEdge actualEdge = this.testInstance.getEdgesWith(mockInNode2).first();
		assertEquals("expected edge", expectedEdge, actualEdge);
		this.mockery.assertIsSatisfied();
	}

	@SuppressWarnings("unchecked")
	@Test
	public final void testHasEdgeWith() {
		final CompoundGraph mockGraph = this.mockery.mock(CompoundGraph.class, "mockSubgraph");
		final CompoundNode mockParentNode = this.mockery.mock(CompoundNode.class, "mockParentNode");
		final CompoundEdge mockInEdge1 = this.mockery.mock(CompoundEdge.class, "mockInEdge1");
		final CompoundEdge mockInEdge2 = this.mockery.mock(CompoundEdge.class, "mockInEdge2");
		final CompoundEdge mockOutEdge1 = this.mockery.mock(CompoundEdge.class, "mockOutEdge1");
		final CompoundEdge mockOutEdge2 = this.mockery.mock(CompoundEdge.class, "mockOutEdge2");
		final CompoundEdge mockOutEdge3 = this.mockery.mock(CompoundEdge.class, "mockOutEdge3");
		final CompoundNode mockInNode2 = this.mockery.mock(CompoundNode.class, "mockInNode2");
		final IDirectedPair<CompoundNode, CompoundEdge> mockOutPair1 = this.mockery.mock(IDirectedPair.class, "mockOutPair1");
		final IDirectedPair<CompoundNode, CompoundEdge> mockOutPair2 = this.mockery.mock(IDirectedPair.class, "mockOutPair2");
		final IDirectedPair<CompoundNode, CompoundEdge> mockOutPair3 = this.mockery.mock(IDirectedPair.class, "mockOutPair3");
		final IDirectedPair<CompoundNode, CompoundEdge> mockInPair1 = this.mockery.mock(IDirectedPair.class, "mockInPair1");
		final IDirectedPair<CompoundNode, CompoundEdge> mockInPair2 = this.mockery.mock(IDirectedPair.class, "mockInPair2");
		this.mockery.checking(new Expectations(){{
			atLeast(1).of(mockParentNode).getGraph(); will(returnValue(mockGraph));

			allowing(mockOutEdge1).compareTo(mockOutEdge2); will(returnValue(-1));
			allowing(mockOutEdge1).compareTo(mockOutEdge1); will(returnValue(0));
			allowing(mockOutEdge1).compareTo(mockOutEdge3); will(returnValue(-1));
			allowing(mockOutEdge1).getConnectedNodes(); will(returnValue(mockOutPair1));
			
			allowing(mockOutEdge2).compareTo(mockOutEdge1); will(returnValue(1));
			allowing(mockOutEdge2).compareTo(mockOutEdge2); will(returnValue(0));
			allowing(mockOutEdge2).compareTo(mockOutEdge3); will(returnValue(-1));
			allowing(mockOutEdge2).getConnectedNodes(); will(returnValue(mockOutPair2));
			
			allowing(mockOutEdge3).compareTo(mockOutEdge1); will(returnValue(1));
			allowing(mockOutEdge3).compareTo(mockOutEdge3); will(returnValue(0));
			allowing(mockOutEdge3).compareTo(mockOutEdge2); will(returnValue(1));
			allowing(mockOutEdge3).getConnectedNodes(); will(returnValue(mockOutPair3));

			allowing(mockInEdge1).compareTo(mockInEdge2); will(returnValue(-1));
			allowing(mockInEdge1).compareTo(mockInEdge1); will(returnValue(0));
			allowing(mockInEdge1).getConnectedNodes(); will(returnValue(mockInPair1));
			
			allowing(mockInEdge2).compareTo(mockInEdge1); will(returnValue(1));
			allowing(mockInEdge2).compareTo(mockInEdge2); will(returnValue(0));
			allowing(mockInEdge2).getConnectedNodes(); will(returnValue(mockInPair2));

			allowing(mockOutPair1).containsNode(mockInNode2); will(returnValue(false));

			allowing(mockOutPair2).containsNode(mockInNode2); will(returnValue(true));

			allowing(mockOutPair3).containsNode(mockInNode2); will(returnValue(false));

			allowing(mockInPair1).containsNode(mockInNode2); will(returnValue(false));

			allowing(mockInPair2).containsNode(mockInNode2); will(returnValue(false));
		}});

		this.testInstance = new CompoundNode(mockParentNode, EXPECTED_NODE1_IDX);
		this.testInstance.addInEdge(mockInEdge1);
		this.testInstance.addInEdge(mockInEdge2);
		this.testInstance.addOutEdge(mockOutEdge1);
		this.testInstance.addOutEdge(mockOutEdge2);
		this.testInstance.addOutEdge(mockOutEdge3);
		assertTrue("expected edge", this.testInstance.hasEdgeWith(mockInNode2));
		this.mockery.assertIsSatisfied();
	}

	@Test
	public final void testRemoveInEdge() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testRemoveOutEdge() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testRemoveNode() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testCompareTo() {
		final CompoundGraph mockGraph = this.mockery.mock(CompoundGraph.class, "mockSubgraph");
		final CompoundNode mockParentNode = this.mockery.mock(CompoundNode.class, "mockParentNode");
		final CompoundNode mockInNode1 = this.mockery.mock(CompoundNode.class, "mockInNode1");
		final CompoundNode mockInNode2 = this.mockery.mock(CompoundNode.class, "mockInNode2");
		this.mockery.checking(new Expectations(){{
			atLeast(1).of(mockParentNode).getGraph(); will(returnValue(mockGraph));

			allowing(mockInNode1).getIndex(); will(returnValue(EXPECTED_CHILD_NODE1_IDX));

			allowing(mockInNode2).getIndex(); will(returnValue(EXPECTED_CHILD_NODE2_IDX));
		}});

		this.testInstance = new CompoundNode(mockParentNode, EXPECTED_NODE1_IDX);
		assertTrue("compare correct", this.testInstance.compareTo(mockInNode1) > 0);
		assertTrue("compare correct", this.testInstance.compareTo(mockInNode2) < 0);
		assertTrue("compare correct", this.testInstance.compareTo(this.testInstance) == 0);
		this.mockery.assertIsSatisfied();
	}

}
