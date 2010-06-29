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
import static org.junit.Assert.assertTrue;

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

import uk.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ed.inf.graph.compound.ICompoundChildEdgeFactory;
import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.ICompoundNodeFactory;
import uk.ed.inf.graph.compound.ICompoundNodePair;
import uk.ed.inf.graph.compound.IRootCompoundNode;
import uk.ed.inf.graph.compound.ISubCompoundGraph;

@RunWith(JMock.class)
public class ChildCompoundGraphTest {
	private static final int NUMERIC [] = {0,1,2,3,4,5} ;

	private static final int EXPECTED_POST_COPY_EDGES = 0;

	private static final int EXPECTED_POST_COPY_NODES = 0;

	private Mockery mockery = new JUnit4Mockery() {{
		setImposteriser(ClassImposteriser.INSTANCE);
	}};
	
	private IChildCompoundGraph testChildCompoundGraph ;
	
	private ICompoundChildEdgeFactory edgeFactory ;
	private ICompoundNodeFactory nodeFactory ;
	
	private IRootCompoundNode mockRootNode ;
	private CompoundGraph mockGraph ;
	
	private ICompoundNode inNode ;
	private ICompoundNode outNode ;
	
	private ICompoundEdge anEdge ;

	@Before
	public void setUp() throws Exception {
		mockGraph = new CompoundGraph () ;
		mockRootNode = mockGraph.getRoot() ;
		
		testChildCompoundGraph = mockGraph.getRoot().getChildCompoundGraph() ;
		
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
		assertEquals ( "root" , mockRootNode , testChildCompoundGraph.getRoot()) ;
	}

	@SuppressWarnings("unchecked")
	@Ignore @Test
	public final void testContainsDirectedEdgeCiNodeCiNode() {
		ICompoundNodePair mockDirectedPair = mockery.mock(ICompoundNodePair.class , "mockDirectedPair") ;
		
		mockery.checking( new Expectations () {{
			
		}} );
		
		assertTrue ( "contains directed edge" , testChildCompoundGraph.containsConnection(mockDirectedPair)) ;
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
		assertTrue ( "contains edge" , testChildCompoundGraph.containsEdge(this.anEdge.getIndex())) ;
		assertFalse ( "not contains edge" , testChildCompoundGraph.containsEdge(this.inNode.getIndex())) ;
	}

	@Test
	public final void testContainsNodeInt() {
		assertTrue ( "contains node" , testChildCompoundGraph.containsNode(this.outNode.getIndex())) ;
	}

	@Test
	public final void testContainsNodeCiNode() {
		assertTrue ( "contains inNode" , testChildCompoundGraph.containsNode(inNode)) ;
	}


	@Test
	public final void testGetEdge() {
		assertEquals ( "get edge" , anEdge , testChildCompoundGraph.getEdge(this.anEdge.getIndex())) ;
	}

	@Test
	public final void testEdgeIterator() {
		ICompoundEdge [] edgeArray = { anEdge } ;
		
		Iterator<ICompoundEdge> edgeIterator = testChildCompoundGraph.edgeIterator() ;
		
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
		ICompoundNode [] nodeArray = { inNode , outNode } ;
		
		Iterator<ICompoundNode> nodeIterator = testChildCompoundGraph.nodeIterator() ;
		
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
		ISubCompoundGraph aBasicSubgraph =  mockGraph.subgraphFactory().createSubgraph() ;
		
		assertTrue ( "canCopy" , testChildCompoundGraph.canCopyHere(aBasicSubgraph) );
	}

	@Test
	public final void testCopyHere() {
		ISubCompoundGraph aBasicSubgraph = mockGraph.subgraphFactory().createSubgraph() ;
		
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
	public final void testAddNewNode() {
		ICompoundNode newNode = testChildCompoundGraph.nodeFactory().createNode() ;
		assertEquals ( "one more node" , NUMERIC[3] , testChildCompoundGraph.getNumNodes()) ;
		assertTrue ( "contains new Node" , testChildCompoundGraph.containsNode(newNode)) ;
	}

	@Test
	public final void testAddNewEdge() {
		ICompoundEdge newEdge = edgeFactory.createEdge() ;
		assertEquals ( "one more edge" , NUMERIC[2] , testChildCompoundGraph.getNumEdges()) ;
		assertTrue ( "contains new Node" , testChildCompoundGraph.containsEdge(newEdge)) ;
	}

	@SuppressWarnings("unchecked")
	@Ignore @Test
	public final void testContainsDirectedEdgeIDirectedPairOfCiNodeCiEdge() {
		final ICompoundNodePair aDirectedPair = mockery.mock(ICompoundNodePair.class , "aDirectedPair") ;
			
		assertTrue ( "contains directed pair" , testChildCompoundGraph.containsConnection(aDirectedPair) );
	}

}
