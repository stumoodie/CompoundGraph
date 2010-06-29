/*
Copyright 2009, Court of the University of Edinburgh

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License. 
*/
package uk.ed.inf.graph.compound.newimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.ICompoundNodeFactory;
import uk.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ed.inf.graph.compound.ISubCompoundGraphFactory;
import uk.ed.inf.graph.state.IGraphState;


public class CreatedEmptyCompoundGraphTest {
	
	private CompoundGraph testGraph ;
	private IGraphState emptyState ;
	
	
	private static final int NUMERIC[] = {0,1,2,3,4,5,6,7,8,9,10} ;
	
	@Before
	public void setUp() throws Exception {
		testGraph = new CompoundGraph () ;
		emptyState = testGraph.getCurrentState() ;
	}
	
	@After
	public void tearDown() throws Exception {
		
	}
	
	@Test
	public void testNumberOfNodesIsOneAndThisNodeIsRootNode () throws Exception
	{
		assertEquals ( "one Node" , NUMERIC[1] , testGraph.getNumNodes() ) ;
		assertEquals ( "node is RootNode" , NUMERIC[0] , testGraph.getRoot().getIndex()) ;
	}
	
	@Test
	public void testNumberOfEdgesIsZero () throws Exception 
	{
		assertEquals ( "No Edges" , NUMERIC[0], testGraph.getNumEdges()) ;
	}
	
	@Test
	public void createNodeFactoryAndCheckIfItIsWorkingProperly () throws Exception
	{
		ICompoundNodeFactory testNodeFactory = testGraph.nodeFactory() ;
		assertNotNull ( "factory created" , testNodeFactory ) ;
		ICompoundNode producedNode = testNodeFactory.createNode() ;
		assertEquals ( "node belongs to Graph" , testGraph , producedNode.getGraph() ) ;
		assertEquals ( "one more Node" , NUMERIC[2] , testGraph.getNumNodes()) ;
		assertTrue ("produced node belongs to Graph" , testGraph.containsNode(producedNode)) ;	
		assertFalse ("not the same instance of factory" , testNodeFactory == testGraph.nodeFactory()) ;
		assertEquals ( "factory is part of Graph" , testGraph , testNodeFactory.getGraph() );
	}
	
	@Test
	public void createEdgeFactoryAndCheckIfItIsWorkingProperly () throws Exception
	{
		ICompoundNode producedNode = testGraph.nodeFactory().createNode() ;
		
		ICompoundEdgeFactory testEdgeFactory = testGraph.edgeFactory() ;
		testEdgeFactory.setPair(producedNode, producedNode) ;
		ICompoundEdge producedEdge = testEdgeFactory.createEdge() ;
		
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
		ISubCompoundGraphFactory testSubGraphFactory = testGraph.subgraphFactory() ;
		
		ISubCompoundGraph producedOrdinarySubGraph = testSubGraphFactory.createSubgraph() ;
		
		assertEquals ( "no nodes" , NUMERIC[0] , producedOrdinarySubGraph.getNumNodes()) ;
		assertEquals ( "no edges" , NUMERIC[0] , producedOrdinarySubGraph.getNumEdges()) ;
		assertEquals ( "belongs to Graph" , testGraph , producedOrdinarySubGraph.getSuperGraph()) ;
		
		ISubCompoundGraph producedInducedSubGraph = testSubGraphFactory.createInducedSubgraph() ;
		assertEquals ( "no nodes" , NUMERIC[0] , producedInducedSubGraph.getNumNodes()) ;
		assertEquals ( "no edges" , NUMERIC[0] , producedInducedSubGraph.getNumEdges()) ;
		assertEquals ( "belongs to Graph" , testGraph , producedInducedSubGraph.getSuperGraph()) ;
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void createSubGraphContainingTheRootNodeAndDeleteWithoutAffectingTheRootNode () throws Exception
	{
		ISubCompoundGraphFactory testSubGraphFactory = testGraph.subgraphFactory() ;
		
		testSubGraphFactory.addElement(testGraph.getRoot()) ;
		
		ISubCompoundGraph producedSubGraph = testSubGraphFactory.createSubgraph() ;
		
		testGraph.removeSubgraph(producedSubGraph) ;
	}
	
	@Test
	public void testRestoreEmptyState () throws Exception
	{
		ICompoundNodeFactory nodeFactory = testGraph.nodeFactory() ;
		ICompoundNode node1 = nodeFactory.createNode() ;
		ICompoundNode node2 = nodeFactory.createNode() ;
		
		ICompoundEdgeFactory edgeFactory = testGraph.edgeFactory() ;
		edgeFactory.setPair(node1, node2) ;
		edgeFactory.createEdge() ;
		
		assertEquals ( "three nodes" , NUMERIC[3] , testGraph.getNumNodes()) ;
		assertEquals ( "an edge" , NUMERIC[1] , testGraph.getNumEdges()) ;
		
		testGraph.restoreState(emptyState) ;
		assertEquals ( "one node" , NUMERIC[1] , testGraph.getNumNodes()) ;
		assertEquals ( "no edges" , NUMERIC[0] , testGraph.getNumEdges()) ;		
		
	} 
	
}
