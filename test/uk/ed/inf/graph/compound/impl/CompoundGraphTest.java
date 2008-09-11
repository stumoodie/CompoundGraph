package uk.ed.inf.graph.compound.impl;

import static org.junit.Assert.*;

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

import uk.ed.inf.graph.basic.IBasicPair;
import uk.ed.inf.graph.basic.IBasicSubgraph;
import uk.ed.inf.graph.compound.base.BaseCompoundEdge;
import uk.ed.inf.graph.compound.base.BaseCompoundNode;
import uk.ed.inf.graph.directed.IDirectedPair;
import uk.ed.inf.graph.impl.Edge;
import uk.ed.inf.graph.impl.Node;
import uk.ed.inf.graph.state.IGraphState;

@RunWith(JMock.class)
public class CompoundGraphTest {
	private Mockery mockery = new JUnit4Mockery() {{
		setImposteriser(ClassImposteriser.INSTANCE);
	}};
	
	private CompoundGraph testCompoundGraph ;
	
	private CompoundNode aNode ;
	private CompoundNode anotherNode ;
	private CompoundNode rootNode ;
	
	private IGraphState<BaseCompoundNode, BaseCompoundEdge> originalState ; 
	private IGraphState<BaseCompoundNode, BaseCompoundEdge> currentState ;
	
	private CompoundEdge anEdge ;
	
	private static final int [] NUMERIC = {0,1,2,3,4,5} ;
	
	
	@Before
	public void setUp() throws Exception {
		testCompoundGraph = new CompoundGraph () ;
		
		originalState = testCompoundGraph.getCurrentState() ;
		
		aNode = testCompoundGraph.nodeFactory().createNode() ;
		anotherNode = testCompoundGraph.nodeFactory().createNode() ;
		rootNode = testCompoundGraph.getRootNode() ;
		
		testCompoundGraph.edgeFactory().setPair(aNode,anotherNode) ;
		anEdge = testCompoundGraph.edgeFactory().createEdge() ;
		
		currentState = testCompoundGraph.getCurrentState() ;
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testCompoundGraphCompoundGraph() {
		CompoundGraph anotherGraph = new CompoundGraph ( testCompoundGraph) ;
		
		assertTrue ( "created" , anotherGraph != null  ) ;
	}

	@Test
	public final void testGetRoot() {
		assertTrue ( "not null" , testCompoundGraph.getRootNode() != null ) ;
		CompoundNode rootNode = testCompoundGraph.getRootNode() ;
		assertEquals ( "same graph" , testCompoundGraph , rootNode.getGraph() );
	}

	@Test
	public final void testContainsDirectedEdgeCompoundNodeCompoundNode() {
		assertTrue ("directed edge" ,  testCompoundGraph.containsDirectedEdge(aNode, anotherNode) );
	}  

	@Test
	public final void testContainsConnectionCompoundNodeCompoundNode() {
		assertTrue ( "contains connection" , testCompoundGraph.containsConnection(aNode, anotherNode) );
	}

	@Test
	public final void testContainsEdgeCompoundEdge() {
		assertTrue ( "contains Edge" , testCompoundGraph.containsEdge(anEdge)) ;
	}

	@Test
	public final void testContainsEdgeInt() {
		assertTrue ( "contains edge there " , testCompoundGraph.containsEdge(1)) ;
	}

	@Test
	public final void testContainsNodeInt() {
		assertTrue ( "contains node there" , testCompoundGraph.containsNode(NUMERIC[0])) ;
		assertTrue ( "contains node there" , testCompoundGraph.containsNode(NUMERIC[1])) ;
	}

	@Test
	public final void testContainsNodeCompoundNode() {
		assertTrue ( "contains this node" , testCompoundGraph.containsNode(aNode) ) ;
	}

	@Test
	public final void testEdgeFactory() {
		assertTrue ( "has edge factory" , testCompoundGraph.edgeFactory() != null ) ;
		assertTrue ( "same instance" , testCompoundGraph.edgeFactory() == testCompoundGraph.edgeFactory() ) ;
		
		testCompoundGraph.edgeFactory().createEdge() ;
		
		assertEquals ( "two nodes" , NUMERIC[2] , testCompoundGraph.getNumEdges()) ;
	}

	@Test
	public final void testNodeFactory() {
		assertTrue ( "has node factory" , testCompoundGraph.nodeFactory() != null ) ;
		assertTrue ( "same instance" , testCompoundGraph.nodeFactory() == testCompoundGraph.nodeFactory() ) ;
		
		testCompoundGraph.nodeFactory().createNode() ;
		
		assertEquals ( "one more node" , NUMERIC[4] , testCompoundGraph.getNumNodes()) ;
	}

	@Test
	public final void testSubgraphFactory() {
		assertTrue ( "has Subgraph factory" , testCompoundGraph.subgraphFactory() != null ) ;
		assertTrue ( "same instance" , testCompoundGraph.subgraphFactory() == testCompoundGraph.subgraphFactory() ) ;
	}

	@Test
	public final void testGetEdge() {
		assertEquals ( "get Edge" , anEdge , testCompoundGraph.getEdge(1)) ;
	}

	@Test
	public final void testEdgeIterator() {
		CompoundEdge edgeArray [] = { anEdge } ;
		
		Iterator<BaseCompoundEdge> edgeIterator = testCompoundGraph.edgeIterator() ;
		
		int counter = 0 ;
		
		while ( edgeIterator.hasNext())
		{
			assertEquals ( "same node" , edgeArray[counter] , edgeIterator.next()) ;
			counter++ ;
		}
		
		assertEquals ( "correct number" , NUMERIC[1] , counter ) ;
	}

	@Test
	public final void testGetNode() {
		assertEquals ( "getnode" , aNode , testCompoundGraph.getNode(1) ) ;
	}

	@Test
	public final void testNodeIterator() {
		CompoundNode [] nodeArray = { rootNode , aNode , anotherNode } ;
		
		Iterator<BaseCompoundNode> nodeIterator = testCompoundGraph.nodeIterator() ;
		
		int counter = 0 ;
		
		while ( nodeIterator.hasNext())
		{
			assertEquals ( "same node" , nodeArray[counter] , nodeIterator.next()) ;
			counter++ ;
		}
		
		assertEquals ( "correct number" , NUMERIC[3] , counter ) ;
	}

	@Test
	public final void testGetNumEdges() {
		assertEquals ( "num edges" , NUMERIC[1] , testCompoundGraph.getNumEdges()) ;
	}

	@Test
	public final void testGetNumNodes() {
		assertEquals ( "num nodes" , NUMERIC[3] , testCompoundGraph.getNumNodes()) ;
	}

	@Test
	public final void testRemoveSubgraph() {
		SubCompoundGraph aSubGraph = testCompoundGraph.subgraphFactory().createSubgraph() ;
		assertEquals ( testCompoundGraph , aSubGraph.getSuperGraph()) ;
		testCompoundGraph.removeSubgraph(aSubGraph) ;
		assertEquals ( testCompoundGraph , aSubGraph.getSuperGraph()) ;
		// TODO is this ok ?? 
	}

	@Test
	public final void testGetLcaNode() {
		fail("Not yet implemented"); // TODO ?? 
	}

	@Test
	public final void testGetNodeCounter() {
		assertEquals ( "3 nodes" , NUMERIC[3] , testCompoundGraph.getNumNodes()) ;
	}

	@Test
	public final void testGetEdgeCounter() {
		assertEquals ( "1 edge" , NUMERIC[1] , testCompoundGraph.getNumEdges()) ;
	}

	@Test
	public final void testContainsDirectedEdgeIDirectedPairOfCompoundNodeCompoundEdge() {
		final IDirectedPair<BaseCompoundNode, BaseCompoundEdge> mockDirectedPair = mockery.mock(IDirectedPair.class , "mockDirectedPair") ;
		
		this.mockery.checking(new Expectations(){{
			atLeast(1).of(mockDirectedPair).getOutNode() ; returnValue(aNode) ;
			atLeast(1).of(mockDirectedPair).getInNode() ; returnValue(anotherNode) ;
		}});
		
		assertTrue ( "has directed Pair" , testCompoundGraph.containsDirectedEdge(mockDirectedPair) ) ;
	}

	@Test
	public final void testContainsConnectionIBasicPairOfCompoundNodeCompoundEdge() {
		final IBasicPair<BaseCompoundNode, BaseCompoundEdge> mockBasicPair = mockery.mock(IBasicPair.class , "mockBasicPair") ;
		
		assertTrue ( "has directed Pair" , testCompoundGraph.containsConnection(mockBasicPair)) ;
	}

	@Test
	public final void testGetCurrentState() {
		assertEquals("current state graph" , currentState.getGraph() , testCompoundGraph.getCurrentState().getGraph() );
		assertEquals("current state edges" , currentState.getEdgeStates() , testCompoundGraph.getCurrentState().getEdgeStates());
		assertEquals("current state nodes" , currentState.getNodeStates() , testCompoundGraph.getCurrentState().getNodeStates() );
	}

	@Test
	public final void testRestoreState() {
		testCompoundGraph.restoreState(originalState) ;
		
		assertEquals("current state graph" , originalState.getGraph() , testCompoundGraph.getCurrentState().getGraph() );
		assertEquals("current state edges" , originalState.getEdgeStates() , testCompoundGraph.getCurrentState().getEdgeStates());
		assertEquals("current state nodes" , originalState.getNodeStates() , testCompoundGraph.getCurrentState().getNodeStates() );
	}

	@Test
	public final void testCanCopyHere() {
		IBasicSubgraph<BaseCompoundNode, BaseCompoundEdge> aBasicSubgraph =  testCompoundGraph.subgraphFactory().createSubgraph() ;
		
		assertTrue ( "canCopy" , testCompoundGraph.canCopyHere(aBasicSubgraph) );
	}

	@Test
	public final void testCopyHere() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testCreateCopy() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testClear() {
		testCompoundGraph.clear() ;	
		assertEquals("one node", NUMERIC[1] , testCompoundGraph.getNumNodes()) ;
		assertEquals("no edges", NUMERIC[0] , testCompoundGraph.getNumEdges()) ;
		assertEquals ( " root node" , rootNode , testCompoundGraph.getNode(0) ) ;
	}

	@Test
	public final void testGetCopiedComponents() {
		IBasicSubgraph<BaseCompoundNode, BaseCompoundEdge> copyOfGraph = testCompoundGraph.getCopiedComponents() ;
		
		assertEquals ( "same edges" , testCompoundGraph.edgeIterator() , copyOfGraph.edgeIterator() );
	}

}
