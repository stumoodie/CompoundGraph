package uk.ed.inf.graph.compound.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


public class CreatedEmptyCompoundGraphTest {
	
	private CompoundGraph testGraph ;
	
	private static final int NUMERIC[] = {0,1,2,3,4,5,6,7,8,9,10} ;
	
	@Before
	public void setUp() throws Exception {
		testGraph = new CompoundGraph () ;
	}
	
	@After
	public void tearDown() throws Exception {
		
	}
	
	@Test
	public void testNumberOfNodesIsOneAndThisNodeIsRootNode () throws Exception
	{
		assertEquals ( "one Node" , NUMERIC[1] , testGraph.getNumNodes() ) ;
		assertEquals ( "node is RootNode" , NUMERIC[0] , testGraph.getRootNode().getIndex()) ;
	}
	
	@Test
	public void testNumberOfEdgesIsZero () throws Exception 
	{
		assertEquals ( "No Edges" , NUMERIC[0], testGraph.getNumEdges()) ;
	}
	
	@Test
	public void createNodeFactoryAndCheckIfItIsWorkingProperly () throws Exception
	{
		CompoundNodeFactory testNodeFactory = testGraph.nodeFactory() ;
		assertNotNull ( "factory created" , testNodeFactory ) ;
		CompoundNode producedNode = testNodeFactory.createNode() ;
		assertEquals ( "node belongs to Graph" , testGraph , producedNode.getGraph() ) ;
		assertEquals ( "one more Node" , NUMERIC[2] , testGraph.getNumNodes()) ;
		assertTrue ("produced node belongs to Graph" , testGraph.containsNode(producedNode)) ;	
		assertFalse ("not the same instance of factory" , testNodeFactory == testGraph.nodeFactory()) ;
		assertEquals ( "factory is part of Graph" , testGraph , testNodeFactory.getGraph() );
	}
	
	@Test
	public void createEdgeFactoryAndCheckIfItIsWorkingProperly () throws Exception
	{
		CompoundNode producedNode = testGraph.nodeFactory().createNode() ;
		
		CompoundEdgeFactory testEdgeFactory = testGraph.edgeFactory() ;
		testEdgeFactory.setPair(producedNode, producedNode) ;
		CompoundEdge producedEdge = testEdgeFactory.createEdge() ;
		
		assertEquals ( "one Edge" , NUMERIC[1] , testGraph.getNumEdges()) ;
		assertTrue ("produced edge belongs to Graph" , testGraph.containsEdge(producedEdge)) ;	
		assertEquals ( "produced edge belongs to graph" , testGraph , producedEdge.getGraph()) ;
		assertTrue ( "Edge has node" , producedEdge.getConnectedNodes().containsNode(producedNode)) ;
		assertFalse ("not the same instance of factory" , testEdgeFactory == testGraph.edgeFactory()) ;
		assertEquals ( "factory is part of Graph" , testGraph , testEdgeFactory.getGraph() );		
	}
	
	@Test
	public void createSubgraphFactoryAndCheckIfItIsWorkingProperly () throws Exception
	{
		SubCompoundGraphFactory testSubGraphFactory = testGraph.subgraphFactory() ;
		
		SubCompoundGraph producedOrdinarySubGraph = testSubGraphFactory.createSubgraph() ;
		
		assertEquals ( "no nodes" , NUMERIC[0] , producedOrdinarySubGraph.getNumNodes()) ;
		assertEquals ( "no edges" , NUMERIC[0] , producedOrdinarySubGraph.getNumEdges()) ;
		assertEquals ( "belongs to Graph" , testGraph , producedOrdinarySubGraph.getSuperGraph()) ;
		
		SubCompoundGraph producedInducedSubGraph = testSubGraphFactory.createInducedSubgraph() ;
		assertEquals ( "no nodes" , NUMERIC[0] , producedInducedSubGraph.getNumNodes()) ;
		assertEquals ( "no edges" , NUMERIC[0] , producedInducedSubGraph.getNumEdges()) ;
		assertEquals ( "belongs to Graph" , testGraph , producedInducedSubGraph.getSuperGraph()) ;
		
	}
	
	@Test(expected=IllegalStateException.class)
	public void createSubGraphContainingTheRootNodeAndDeleteWithoutAffectingTheRootNode () throws Exception
	{
		SubCompoundGraphFactory testSubGraphFactory = testGraph.subgraphFactory() ;
		
		testSubGraphFactory.addNode(testGraph.getRootNode()) ;
		
		SubCompoundGraph producedSubGraph = testSubGraphFactory.createSubgraph() ;
		
		testGraph.removeSubgraph(producedSubGraph) ;
	}
}
