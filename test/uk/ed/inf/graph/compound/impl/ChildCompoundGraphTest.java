package uk.ed.inf.graph.compound.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ed.inf.graph.compound.base.BaseCompoundEdge;
import uk.ed.inf.graph.compound.base.BaseCompoundNode;
import uk.ed.inf.graph.compound.base.BaseSubCompoundGraph;
import uk.ed.inf.graph.directed.IDirectedPair;

@RunWith(JMock.class)
public class ChildCompoundGraphTest {
	private static final int NUMERIC [] = {0,1,2,3,4,5} ;

	private static final int EXPECTED_POST_COPY_EDGES = 0;

	private static final int EXPECTED_POST_COPY_NODES = 0;

	private Mockery mockery = new JUnit4Mockery() {{
		setImposteriser(ClassImposteriser.INSTANCE);
	}};
	
	private ChildCompoundGraph testChildCompoundGraph ;
	
	private ChildCompoundEdgeFactory edgeFactory ;
	private CompoundNodeFactory nodeFactory ;
	
	private CompoundNode mockRootNode ;
	private CompoundGraph mockGraph ;
	
	private CompoundNode inNode ;
	private CompoundNode outNode ;
	
	private CompoundEdge anEdge ;

	@Before
	public void setUp() throws Exception {
		mockGraph = new CompoundGraph () ;
		mockRootNode = mockGraph.getRootNode() ;
		
		testChildCompoundGraph = mockGraph.getRootNode().getChildCompoundGraph() ;
		
		nodeFactory = testChildCompoundGraph.nodeFactory() ;
		edgeFactory = testChildCompoundGraph.edgeFactory() ;
		
		inNode = nodeFactory.createNode() ;
		outNode = nodeFactory.createNode() ;
		
		edgeFactory.setPair(outNode, inNode) ;
		anEdge = edgeFactory.createEdge() ;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testGetRoot() {
		assertEquals ( "root" , mockRootNode , testChildCompoundGraph.getRootNode()) ;
	}

	@SuppressWarnings("unchecked")
	@Ignore @Test
	public final void testContainsDirectedEdgeCiNodeCiNode() {
		IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockDirectedPair = mockery.mock(IDirectedPair.class , "mockDirectedPair") ;
		
		mockery.checking( new Expectations () {{
			
		}} );
		
		assertTrue ( "contains directed edge" , testChildCompoundGraph.containsDirectedEdge(mockDirectedPair)) ;
	}

	@Test
	public final void testCanCreateEdges() {
		assertTrue ( "hasFactory" , edgeFactory != null ) ;
		assertTrue ( "non singleton factory" , edgeFactory != testChildCompoundGraph.edgeFactory() ) ;
		edgeFactory.createEdge() ;
		assertEquals ( "two edges" , NUMERIC[2] , testChildCompoundGraph.getNumEdges() ) ;
	}

	@Test
	public final void testCanCreateNodes() {
		assertTrue ( "hasFactory" , nodeFactory != null ) ;
		assertTrue ( "non singleton factory" , testChildCompoundGraph.nodeFactory() != nodeFactory ) ;
		nodeFactory.createNode() ;
		assertEquals ( "one node" , NUMERIC[3] , testChildCompoundGraph.getNumNodes()) ;
	}

	@Test
	public final void testContainsConnectionCiNodeCiNode() {
		assertTrue ( "connection" , testChildCompoundGraph.containsConnection(inNode, outNode)) ;
	}

	@Test
	public final void testContainsEdgeCiEdge() {
		assertTrue ( "contains this edge" , testChildCompoundGraph.containsEdge(anEdge)) ;
	}

	@Test
	public final void testContainsEdgeInt() {
		assertTrue ( "contains edge" , testChildCompoundGraph.containsEdge(1)) ;
	}

	@Test
	public final void testContainsNodeInt() {
		assertTrue ( "contains node" , testChildCompoundGraph.containsNode(1)) ;
	}

	@Test
	public final void testContainsNodeCiNode() {
		assertTrue ( "contains inNode" , testChildCompoundGraph.containsNode(inNode)) ;
	}


	@Test
	public final void testGetEdge() {
		assertEquals ( "get edge" , anEdge , testChildCompoundGraph.getEdge(1)) ;
	}

	@Test
	public final void testEdgeIterator() {
		CompoundEdge [] edgeArray = { anEdge } ;
		
		Iterator<BaseCompoundEdge> edgeIterator = testChildCompoundGraph.edgeIterator() ;
		
		int counter = 0; 
		
		while ( edgeIterator.hasNext())
		{
			assertEquals ( "same edge" , edgeArray[counter] , edgeIterator.next()) ;
			counter++ ;
		}
		
		assertEquals ( "one element" , NUMERIC[1] , counter) ;
		
	}

	@Test
	public final void testGetNode() {
		assertEquals ( "get inNode" , inNode , testChildCompoundGraph.getNode(1) ) ;
	}

	@Test
	public final void testNodeIterator() {
		CompoundNode [] nodeArray = { inNode , outNode } ;
		
		Iterator<BaseCompoundNode> nodeIterator = testChildCompoundGraph.nodeIterator() ;
		
		int counter = 0; 
		
		while ( nodeIterator.hasNext())
		{
			assertEquals ( "same edge" , nodeArray[counter] , nodeIterator.next()) ;
			counter++ ;
		}
		
		assertEquals ( "two elements" , NUMERIC[2] , counter) ;
	}

	@Test
	public final void testGetNumEdges() {
		assertEquals( "one edge" , NUMERIC[1] , testChildCompoundGraph.getNumEdges() ) ;
	}

	@Test
	public final void testGetNumNodes() {
		assertEquals ( "no nodes" , NUMERIC[2] , testChildCompoundGraph.getNumNodes()) ;
	}

	@Test
	public final void testCanCopyHere() {
		BaseSubCompoundGraph aBasicSubgraph =  mockGraph.subgraphFactory().createSubgraph() ;
		
		assertTrue ( "canCopy" , testChildCompoundGraph.canCopyHere(aBasicSubgraph) );
	}

	@Test
	public final void testCopyHere() {
		BaseSubCompoundGraph aBasicSubgraph = mockGraph.subgraphFactory().createSubgraph() ;
		
		testChildCompoundGraph.copyHere(aBasicSubgraph) ;
		assertEquals ( "same BaseGraph" , testChildCompoundGraph.getSuperGraph() , aBasicSubgraph.getSuperGraph()) ;
		assertEquals ( "same no of Nodes" , EXPECTED_POST_COPY_NODES , aBasicSubgraph.getNumNodes()) ;
		assertEquals ( "same no of Edges" , EXPECTED_POST_COPY_EDGES , aBasicSubgraph.getNumEdges()) ;
	}

	@Test
	public final void testGetSuperGraph() {
		assertEquals ( "graph" , mockGraph , testChildCompoundGraph.getSuperGraph()) ;
	}

	@Test
	public final void testIsInducedSubgraph() {
		assertFalse ( "not Indused" , testChildCompoundGraph.isInducedSubgraph() );
	}

	@Test
	public final void testAddNewNode() {
		CompoundNode newNode = testChildCompoundGraph.nodeFactory().createNode() ;
		assertEquals ( "one more node" , NUMERIC[3] , testChildCompoundGraph.getNumNodes()) ;
		assertTrue ( "contains new Node" , testChildCompoundGraph.containsNode(newNode)) ;
	}

	@Test
	public final void testAddNewEdge() {
		CompoundEdge newEdge = edgeFactory.createEdge() ;
		assertEquals ( "one more edge" , NUMERIC[2] , testChildCompoundGraph.getNumEdges()) ;
		assertTrue ( "contains new Node" , testChildCompoundGraph.containsEdge(newEdge)) ;
	}

	@SuppressWarnings("unchecked")
	@Ignore @Test
	public final void testContainsDirectedEdgeIDirectedPairOfCiNodeCiEdge() {
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> aDirectedPair = mockery.mock(IDirectedPair.class , "aDirectedPair") ;
			
		assertTrue ( "contains directed pair" , testChildCompoundGraph.containsDirectedEdge(aDirectedPair) );
	}

	@Ignore @Test
	public final void testContainsConnectionIBasicPairOfCiNodeCiEdge() {
		fail("Not yet implemented"); // TODO
	}

}
