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

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import uk.ed.inf.graph.compound.CompoundNodePair;
import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ed.inf.graph.compound.ICompoundGraph;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.ICompoundNodeFactory;
import uk.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ed.inf.graph.compound.ISubCompoundGraphFactory;

public class SubCompoundGraphIntegrationTest {
	private static final int NUM_ROOT_NODE_EDGES = 4;
	private static final int NUM_NODE1_EDGES = 2;
	private static final int NUM_NODE2_EDGES = 1;
	private static final int NUM_NODE3_EDGES = 0;
	private static final int NUM_NODE4_EDGES = 0;
	private static final int NUM_NODE5_EDGES = 0;
	private static final int NUM_NODE6_EDGES = 2;
	private static final int NUM_NODE7_EDGES = 0;
	private static final int NUM_NODE8_EDGES = 0;
	private static final int [] NUMERIC_VALUE = { 0,1,2,3,4,5,6,7,8,9,10 } ;

	
	private ICompoundGraph testInstance;
	private ICompoundNode rootNode;
	private ICompoundNode node1;
	private ICompoundNode node2;
	private ICompoundNode node3;
	private ICompoundNode node4;
	private ICompoundNode node5;
	private ICompoundNode node6;
	private ICompoundNode node7;
	private ICompoundNode node8;
	private ICompoundEdge edge1;
	private ICompoundEdge edge2;
	private ICompoundEdge edge3;
	private ICompoundEdge edge4;
	private ICompoundEdge edge5;
	private ICompoundEdge edge6;
	private ICompoundEdge edge7;
	private ICompoundEdge edge8;
	private ICompoundEdge edge9;

	@Before
	public void setUp() throws Exception {
		this.testInstance = new CompoundGraph();
		this.rootNode = this.testInstance.getRoot();
		ICompoundNodeFactory rootNodeFact = this.testInstance.getRoot().getChildCompoundGraph().nodeFactory();
		node1 = rootNodeFact.createNode();
		node2 = rootNodeFact.createNode();
		ICompoundNodeFactory node1Fact = node1.getChildCompoundGraph().nodeFactory();
		node3 = node1Fact.createNode();
		node4 = node1Fact.createNode();
		ICompoundNodeFactory node2Fact = node2.getChildCompoundGraph().nodeFactory();
		node5 = node2Fact.createNode();
		node6 = node2Fact.createNode();
		ICompoundNodeFactory node6Fact = node6.getChildCompoundGraph().nodeFactory();
		node7 = node6Fact.createNode();
		ICompoundNodeFactory node7Fact = node7.getChildCompoundGraph().nodeFactory();
		node8 = node7Fact.createNode();
		
		ICompoundEdgeFactory edgeFact = this.testInstance.edgeFactory();
		edgeFact.setPair(new CompoundNodePair(node1, node2));
		edge1 = edgeFact.createEdge();
		edgeFact.setPair(new CompoundNodePair(node2, node1));
		edge2 = edgeFact.createEdge();
		edgeFact.setPair(new CompoundNodePair(node2, node6));
		edge3 = edgeFact.createEdge();
		edgeFact.setPair(new CompoundNodePair(node2, node4));
		edge4 = edgeFact.createEdge();
		edgeFact.setPair(new CompoundNodePair(node7, node6));
		edge5 = edgeFact.createEdge();
		edgeFact.setPair(new CompoundNodePair(node6, node7));
		edge6 = edgeFact.createEdge();
		edgeFact.setPair(new CompoundNodePair(node7, node3));
		edge7 = edgeFact.createEdge();
		edgeFact.setPair(new CompoundNodePair(node1, node1));
		edge8 = edgeFact.createEdge();
		edge9 = edgeFact.createEdge();
	}

	@After
	public void tearDown() throws Exception {
		this.testInstance =  null;
		this.rootNode= null;
		this.node1 = null;
		this.node2 = null;
		this.node3 = null;
		this.node4 = null;
		this.node5 = null;
		this.node6 = null;
		this.node7 = null;
		this.node8 = null;
		this.edge1 = null;
		this.edge2 = null;
		this.edge3 = null;
		this.edge4 = null;
		this.edge5 = null;
		this.edge6 = null;
		this.edge7 = null;
		this.edge8 = null;
		this.edge9 = null;
	}


	@Test
	public final void testGraphStructure(){
		assertEquals("root subgraph edges", NUM_ROOT_NODE_EDGES, rootNode.getChildCompoundGraph().getNumEdges());
		assertEquals("node1 subgraph edges", NUM_NODE1_EDGES, node1.getChildCompoundGraph().getNumEdges());
		assertEquals("node2 subgraph edges", NUM_NODE2_EDGES, node2.getChildCompoundGraph().getNumEdges());
		assertEquals("node3 subgraph edges", NUM_NODE3_EDGES, node3.getChildCompoundGraph().getNumEdges());
		assertEquals("node4 subgraph edges", NUM_NODE4_EDGES, node4.getChildCompoundGraph().getNumEdges());
		assertEquals("node5 subgraph edges", NUM_NODE5_EDGES, node5.getChildCompoundGraph().getNumEdges());
		assertEquals("node6 subgraph edges", NUM_NODE6_EDGES, node6.getChildCompoundGraph().getNumEdges());
		assertEquals("node7 subgraph edges", NUM_NODE7_EDGES, node7.getChildCompoundGraph().getNumEdges());
		assertEquals("node8 subgraph edges", NUM_NODE8_EDGES, node8.getChildCompoundGraph().getNumEdges());
	}
	
	@Test
	public final void testCreateSubGraph () throws Exception
	{
		ISubCompoundGraphFactory subGraphfactory = testInstance.subgraphFactory() ; 
		subGraphfactory.addElement(node1) ;
		ISubCompoundGraph  nonInducedSubGraph = subGraphfactory.createSubgraph() ;
		assertEquals ( "from original Graph" , testInstance , nonInducedSubGraph.getSuperGraph()) ;
		assertEquals ( "1 nodes" , NUMERIC_VALUE[1] , nonInducedSubGraph.getNumTopNodes()) ;
		assertEquals ( "3 nodes" , NUMERIC_VALUE[3] , nonInducedSubGraph.getNumNodes()) ;
		assertEquals ( "2 edges" , NUMERIC_VALUE[2] , nonInducedSubGraph.getNumEdges()) ;
	}
	
	@Test
	public final void testCreateNonInducedSubGraph () throws Exception
	{
		ISubCompoundGraphFactory subGraphfactory = testInstance.subgraphFactory() ; 
		subGraphfactory.addElement(node1) ;
		subGraphfactory.addElement(node2) ;
		ISubCompoundGraph  nonInducedSubGraph = subGraphfactory.createSubgraph() ;
		assertEquals ( "from original Graph" , testInstance , nonInducedSubGraph.getSuperGraph()) ;
		assertEquals ( "8 nodes" , NUMERIC_VALUE[8] , nonInducedSubGraph.getNumNodes()) ;
		assertEquals ( "5 edges" , NUMERIC_VALUE[5] , nonInducedSubGraph.getNumEdges()) ;
		assertEquals ( "2 top nodes" , NUMERIC_VALUE[2] , nonInducedSubGraph.getNumTopNodes()) ;
	}
	
	@Test
	public final void testCreateNonInducedSubGraphWithAddedEdge () throws Exception
	{
		ISubCompoundGraphFactory subGraphfactory = testInstance.subgraphFactory() ; 
		subGraphfactory.addElement(node1) ;
		subGraphfactory.addElement(node2) ;
		subGraphfactory.addElement(edge4);
		subGraphfactory.addElement(edge7);
		ISubCompoundGraph  nonInducedSubGraph = subGraphfactory.createSubgraph() ;
		assertEquals ( "from original Graph" , testInstance , nonInducedSubGraph.getSuperGraph()) ;
		assertEquals ( "8 nodes" , NUMERIC_VALUE[8] , nonInducedSubGraph.getNumNodes()) ;
		assertEquals ( "7 edges" , NUMERIC_VALUE[7] , nonInducedSubGraph.getNumEdges()) ;
		assertEquals ( "2 top nodes" , NUMERIC_VALUE[2] , nonInducedSubGraph.getNumTopNodes()) ;
	}
	
	@Ignore @Test
	public final void testCreateInducedSubGraph () throws Exception
	{
		ISubCompoundGraphFactory subGraphfactory = testInstance.subgraphFactory() ; 
		subGraphfactory.addElement(node1) ;
		subGraphfactory.addElement(node2) ;
		ISubCompoundGraph  inducedSubGraph = subGraphfactory.createInducedSubgraph() ;
		assertEquals ( "from original Graph" , testInstance , inducedSubGraph.getSuperGraph()) ;
		assertEquals ( "8 nodes" , NUMERIC_VALUE[8] , inducedSubGraph.getNumNodes()) ;
		assertEquals ( "9 edges" , NUMERIC_VALUE[9] , inducedSubGraph.getNumEdges()) ;
		assertEquals ( "2 top nodes" , NUMERIC_VALUE[2] , inducedSubGraph.getNumTopNodes()) ;
	}
	
	@Ignore @Test
	public final void testCreateNonInducedSubGraphContainsNodeByIndx () throws Exception
	{
		ISubCompoundGraphFactory subGraphfactory = testInstance.subgraphFactory() ; 
		subGraphfactory.addElement(node1) ;
		subGraphfactory.addElement(node2) ;
		ISubCompoundGraph  actualSubGraph = subGraphfactory.createSubgraph() ;
		assertEquals ( "from original Graph" , testInstance , actualSubGraph.getSuperGraph()) ;
		assertTrue ( "no root" , !actualSubGraph.containsNode(this.rootNode.getIndex())) ;
		assertTrue ( "has node 1" , actualSubGraph.containsNode(this.node1.getIndex())) ;
		assertTrue ( "has node 2" , actualSubGraph.containsNode(this.node2.getIndex())) ;
		assertTrue ( "has node 3" , actualSubGraph.containsNode(this.node3.getIndex())) ;
		assertTrue ( "has node 4" , actualSubGraph.containsNode(this.node4.getIndex())) ;
		assertTrue ( "has node 5" , actualSubGraph.containsNode(this.node5.getIndex())) ;
		assertTrue ( "has node 6" , actualSubGraph.containsNode(this.node6.getIndex())) ;
		assertTrue ( "has node 7" , actualSubGraph.containsNode(this.node7.getIndex())) ;
		assertTrue ( "has node 8" , actualSubGraph.containsNode(this.node8.getIndex())) ;
	}
	
	@Ignore @Test
	public final void testCreateNonInducedSubGraphGetNode () throws Exception
	{
		ISubCompoundGraphFactory subGraphfactory = testInstance.subgraphFactory() ; 
		subGraphfactory.addElement(node1) ;
		subGraphfactory.addElement(node2) ;
		ISubCompoundGraph  actualSubGraph = subGraphfactory.createSubgraph() ;
		assertEquals ( "from original Graph" , testInstance , actualSubGraph.getSuperGraph()) ;
		assertEquals ( "has node 1" , node1, actualSubGraph.getNode(this.node1.getIndex())) ;
		assertEquals ( "has node 2" , node2, actualSubGraph.getNode(this.node2.getIndex())) ;
		assertEquals ( "has node 3" , node3, actualSubGraph.getNode(this.node3.getIndex())) ;
		assertEquals ( "has node 4" , node4, actualSubGraph.getNode(this.node4.getIndex())) ;
		assertEquals ( "has node 5" , node5, actualSubGraph.getNode(this.node5.getIndex())) ;
		assertEquals ( "has node 6" , node6, actualSubGraph.getNode(this.node6.getIndex())) ;
		assertEquals ( "has node 7" , node7, actualSubGraph.getNode(this.node7.getIndex())) ;
		assertEquals ( "has node 8" , node8, actualSubGraph.getNode(this.node8.getIndex())) ;
	}
	
	@Ignore @Test(expected=IllegalArgumentException.class)
	public final void testCreateNonInducedSubGraphGetNodeThrowsExceptionWhenNodeNotPresent (){
		ISubCompoundGraphFactory subGraphfactory = testInstance.subgraphFactory() ; 
		subGraphfactory.addElement(node1) ;
		subGraphfactory.addElement(node2) ;
		ISubCompoundGraph  actualSubGraph = subGraphfactory.createSubgraph() ;
		assertEquals ( "from original Graph" , testInstance , actualSubGraph.getSuperGraph()) ;
		actualSubGraph.getNode(this.rootNode.getIndex());
	}
	
	@Ignore @Test
	public final void testCreateNonInducedSubGraphContainsNode () throws Exception
	{
		ISubCompoundGraphFactory subGraphfactory = testInstance.subgraphFactory() ; 
		subGraphfactory.addElement(node1) ;
		subGraphfactory.addElement(node2) ;
		ISubCompoundGraph  actualSubGraph = subGraphfactory.createSubgraph() ;
		assertEquals ( "from original Graph" , testInstance , actualSubGraph.getSuperGraph()) ;
		assertTrue ( "no root" , !actualSubGraph.containsNode(this.rootNode)) ;
		assertTrue ( "has node 1" , actualSubGraph.containsNode(this.node1)) ;
		assertTrue ( "has node 2" , actualSubGraph.containsNode(this.node2)) ;
		assertTrue ( "has node 3" , actualSubGraph.containsNode(this.node3)) ;
		assertTrue ( "has node 4" , actualSubGraph.containsNode(this.node4)) ;
		assertTrue ( "has node 5" , actualSubGraph.containsNode(this.node5)) ;
		assertTrue ( "has node 6" , actualSubGraph.containsNode(this.node6)) ;
		assertTrue ( "has node 7" , actualSubGraph.containsNode(this.node7)) ;
		assertTrue ( "has node 8" , actualSubGraph.containsNode(this.node8)) ;
	}
	
	@Ignore @Test
	public final void testCreateNonInducedSubGraphContainsConnectionPair () throws Exception
	{
		ISubCompoundGraphFactory subGraphfactory = testInstance.subgraphFactory() ; 
		subGraphfactory.addElement(node1) ;
		subGraphfactory.addElement(node2) ;
		ISubCompoundGraph  actualSubGraph = subGraphfactory.createSubgraph() ;
		assertEquals ( "from original Graph" , testInstance , actualSubGraph.getSuperGraph()) ;
		assertTrue ( "has edge 3", actualSubGraph.containsConnection(edge3.getConnectedNodes())) ;
		assertTrue ( "has edge 5", actualSubGraph.containsConnection(edge5.getConnectedNodes())) ;
		assertTrue ( "has edge 6", actualSubGraph.containsConnection(edge6.getConnectedNodes())) ;
		assertTrue ( "has edge 8/9", actualSubGraph.containsConnection(edge8.getConnectedNodes())) ;
	}
	
	@Ignore @Test
	public final void testCreateNonInducedSubGraphContainsConnectionNodeNode () throws Exception
	{
		ISubCompoundGraphFactory subGraphfactory = testInstance.subgraphFactory() ; 
		subGraphfactory.addElement(node1) ;
		subGraphfactory.addElement(node2) ;
		ISubCompoundGraph  actualSubGraph = subGraphfactory.createSubgraph() ;
		assertEquals ( "from original Graph" , testInstance , actualSubGraph.getSuperGraph()) ;
		assertTrue ( "has edge 3", actualSubGraph.containsConnection(node2, node6)) ;
		assertTrue ( "has edge 3 reversed", actualSubGraph.containsConnection(node6, node2)) ;
		assertTrue ( "has edge 5", actualSubGraph.containsConnection(node7, node6)) ;
		assertTrue ( "has edge 6", actualSubGraph.containsConnection(node6, node7)) ;
		assertTrue ( "has edge 8 or 9", actualSubGraph.containsConnection(node1, node1)) ;
	}
	
	@Ignore @Test
	public final void testCreateNonInducedSubGraphContainsDirectedEdgeNodeNode () throws Exception
	{
		ISubCompoundGraphFactory subGraphfactory = testInstance.subgraphFactory() ; 
		subGraphfactory.addElement(node1) ;
		subGraphfactory.addElement(node2) ;
		ISubCompoundGraph  actualSubGraph = subGraphfactory.createSubgraph() ;
		assertEquals ( "from original Graph" , testInstance , actualSubGraph.getSuperGraph()) ;
		assertTrue ( "has edge 3", actualSubGraph.containsDirectedEdge(node2, node6)) ;
		assertTrue ( "has edge 5", actualSubGraph.containsDirectedEdge(node7, node6)) ;
		assertTrue ( "has edge 6", actualSubGraph.containsDirectedEdge(node6, node7)) ;
		assertTrue ( "has edge 8 or 9", actualSubGraph.containsDirectedEdge(node1, node1)) ;
	}
	
	@Ignore @Test
	public final void testCreateNonInducedSubGraphContainsEdge () throws Exception
	{
		ISubCompoundGraphFactory subGraphfactory = testInstance.subgraphFactory() ; 
		subGraphfactory.addElement(node1) ;
		subGraphfactory.addElement(node2) ;
		ISubCompoundGraph  actualSubGraph = subGraphfactory.createSubgraph() ;
		assertEquals ( "from original Graph" , testInstance , actualSubGraph.getSuperGraph()) ;
		assertTrue ( "has edge 3", actualSubGraph.containsEdge(this.edge3)) ;
		assertFalse ( "has edge 4", actualSubGraph.containsEdge(this.edge4)) ;
		assertTrue ( "has edge 5", actualSubGraph.containsEdge(this.edge5)) ;
		assertTrue ( "has edge 6", actualSubGraph.containsEdge(this.edge6)) ;
		assertTrue ( "has edge 8", actualSubGraph.containsEdge(this.edge8)) ;
		assertTrue ( "has edge 9", actualSubGraph.containsEdge(this.edge9)) ;
	}
	
	@Ignore @Test
	public final void testCreateInducedSubGraphContainsNode () throws Exception
	{
		ISubCompoundGraphFactory subGraphfactory = testInstance.subgraphFactory() ; 
		subGraphfactory.addElement(node1) ;
		subGraphfactory.addElement(node2) ;
		ISubCompoundGraph  inducedSubGraph = subGraphfactory.createInducedSubgraph() ;
		assertEquals ( "from original Graph" , testInstance , inducedSubGraph.getSuperGraph()) ;
		assertTrue ( "no root" , !inducedSubGraph.containsNode(this.rootNode)) ;
		assertTrue ( "has node 1" , inducedSubGraph.containsNode(this.node1)) ;
		assertTrue ( "has node 2" , inducedSubGraph.containsNode(this.node2)) ;
		assertTrue ( "has node 3" , inducedSubGraph.containsNode(this.node3)) ;
		assertTrue ( "has node 4" , inducedSubGraph.containsNode(this.node4)) ;
		assertTrue ( "has node 5" , inducedSubGraph.containsNode(this.node5)) ;
		assertTrue ( "has node 6" , inducedSubGraph.containsNode(this.node6)) ;
		assertTrue ( "has node 7" , inducedSubGraph.containsNode(this.node7)) ;
		assertTrue ( "has node 8" , inducedSubGraph.containsNode(this.node8)) ;
	}
	
	@Ignore @Test
	public final void testCreateInducedSubGraphContainsConnectionPair () throws Exception
	{
		ISubCompoundGraphFactory subGraphfactory = testInstance.subgraphFactory() ; 
		subGraphfactory.addElement(node1) ;
		subGraphfactory.addElement(node2) ;
		ISubCompoundGraph  inducedSubGraph = subGraphfactory.createInducedSubgraph() ;
		assertEquals ( "from original Graph" , testInstance , inducedSubGraph.getSuperGraph()) ;
		assertTrue ( "has edge 1", inducedSubGraph.containsConnection(edge1.getConnectedNodes())) ;
		assertTrue ( "has edge 2", inducedSubGraph.containsConnection(edge2.getConnectedNodes())) ;
		assertTrue ( "has edge 3", inducedSubGraph.containsConnection(edge3.getConnectedNodes())) ;
		assertTrue ( "has edge 4", inducedSubGraph.containsConnection(edge4.getConnectedNodes())) ;
		assertTrue ( "has edge 5", inducedSubGraph.containsConnection(edge5.getConnectedNodes())) ;
		assertTrue ( "has edge 6", inducedSubGraph.containsConnection(edge6.getConnectedNodes())) ;
		assertTrue ( "has edge 7", inducedSubGraph.containsConnection(edge7.getConnectedNodes())) ;
		assertTrue ( "has edge 8", inducedSubGraph.containsConnection(edge8.getConnectedNodes())) ;
		assertTrue ( "has edge 1 reversed", inducedSubGraph.containsConnection(edge1.getConnectedNodes().reversedNodes())) ;
		assertTrue ( "has edge 2 reversed", inducedSubGraph.containsConnection(edge2.getConnectedNodes().reversedNodes())) ;
		assertTrue ( "has edge 3 reversed", inducedSubGraph.containsConnection(edge3.getConnectedNodes().reversedNodes())) ;
		assertTrue ( "has edge 4 reversed", inducedSubGraph.containsConnection(edge4.getConnectedNodes().reversedNodes())) ;
		assertTrue ( "has edge 5 reversed", inducedSubGraph.containsConnection(edge5.getConnectedNodes().reversedNodes())) ;
		assertTrue ( "has edge 6 reversed", inducedSubGraph.containsConnection(edge6.getConnectedNodes().reversedNodes())) ;
		assertTrue ( "has edge 7 reversed", inducedSubGraph.containsConnection(edge7.getConnectedNodes().reversedNodes())) ;
		assertTrue ( "has edge 8 reversed", inducedSubGraph.containsConnection(edge8.getConnectedNodes().reversedNodes())) ;
	}
	
	@Ignore @Test
	public final void testCreateInducedSubGraphContainsConnectionNodeNode () throws Exception
	{
		ISubCompoundGraphFactory subGraphfactory = testInstance.subgraphFactory() ; 
		subGraphfactory.addElement(node1) ;
		subGraphfactory.addElement(node2) ;
		ISubCompoundGraph  inducedSubGraph = subGraphfactory.createInducedSubgraph() ;
		assertEquals ( "from original Graph" , testInstance , inducedSubGraph.getSuperGraph()) ;
		assertTrue ( "has edge 1", inducedSubGraph.containsConnection(node1, node2)) ;
		assertTrue ( "has edge 2", inducedSubGraph.containsConnection(node2, node1)) ;
		assertTrue ( "has edge 3", inducedSubGraph.containsConnection(node2, node6)) ;
		assertTrue ( "has edge 3 reversed", inducedSubGraph.containsConnection(node6, node2)) ;
		assertTrue ( "has edge 4", inducedSubGraph.containsConnection(node2, node4)) ;
		assertTrue ( "has edge 4", inducedSubGraph.containsConnection(node4, node2)) ;
		assertTrue ( "has edge 5", inducedSubGraph.containsConnection(node7, node6)) ;
		assertTrue ( "has edge 6", inducedSubGraph.containsConnection(node6, node7)) ;
		assertTrue ( "has edge 7", inducedSubGraph.containsConnection(node7, node3)) ;
		assertTrue ( "has edge 7 reversed", inducedSubGraph.containsConnection(node3, node7)) ;
		assertTrue ( "has edge 8 or 9", inducedSubGraph.containsConnection(node1, node1)) ;
	}
	
	@Ignore @Test
	public final void testCreateInducedSubGraphContainsEdge () throws Exception
	{
		ISubCompoundGraphFactory subGraphfactory = testInstance.subgraphFactory() ; 
		subGraphfactory.addElement(node1) ;
		subGraphfactory.addElement(node2) ;
		ISubCompoundGraph  actualSubGraph = subGraphfactory.createInducedSubgraph() ;
		assertEquals ( "from original Graph" , testInstance , actualSubGraph.getSuperGraph()) ;
		assertTrue ( "has edge 1", actualSubGraph.containsEdge(this.edge1)) ;
		assertTrue ( "has edge 2", actualSubGraph.containsEdge(this.edge2)) ;
		assertTrue ( "has edge 3", actualSubGraph.containsEdge(this.edge3)) ;
		assertTrue ( "has edge 4", actualSubGraph.containsEdge(this.edge4)) ;
		assertTrue ( "has edge 5", actualSubGraph.containsEdge(this.edge5)) ;
		assertTrue ( "has edge 6", actualSubGraph.containsEdge(this.edge6)) ;
		assertTrue ( "has edge 7", actualSubGraph.containsEdge(this.edge7)) ;
		assertTrue ( "has edge 8", actualSubGraph.containsEdge(this.edge8)) ;
		assertTrue ( "has edge 9", actualSubGraph.containsEdge(this.edge9)) ;
	}
	
	@Ignore @Test
	public final void testCreateSubGraphOnlyEdge () throws Exception
	{
		ISubCompoundGraphFactory subGraphfactory = testInstance.subgraphFactory() ; 
		subGraphfactory.addElement(edge1) ;
		ISubCompoundGraph  inducedSubGraph = subGraphfactory.createSubgraph() ;
		assertEquals ( "from original Graph" , testInstance , inducedSubGraph.getSuperGraph()) ;
		assertEquals ( "0 nodes" , NUMERIC_VALUE[0] , inducedSubGraph.getNumNodes()) ;
		assertEquals ( "1 edges" , NUMERIC_VALUE[1] , inducedSubGraph.getNumEdges()) ;
		assertEquals ( "0 top nodes" , NUMERIC_VALUE[0] , inducedSubGraph.getNumTopNodes()) ;
	}
	
	@Ignore @Test
	public final void testCreateInducedSubGraphFromBranches () throws Exception
	{
		ISubCompoundGraphFactory subGraphfactory = testInstance.subgraphFactory() ; 
		subGraphfactory.addElement(node3) ;
		subGraphfactory.addElement(node4) ;
		subGraphfactory.addElement(node6) ;
		ISubCompoundGraph  inducedSubGraph = subGraphfactory.createInducedSubgraph() ;
		assertEquals ( "from original Graph" , testInstance , inducedSubGraph.getSuperGraph()) ;
		assertEquals ( "5 nodes" , NUMERIC_VALUE[5] , inducedSubGraph.getNumNodes()) ;
		assertEquals ( "3 edges" , NUMERIC_VALUE[3] , inducedSubGraph.getNumEdges()) ;
		assertEquals ( "3 top nodes" , NUMERIC_VALUE[3] , inducedSubGraph.getNumTopNodes()) ;
	}
	
	@Ignore @Test
	public final void testCreateInducedSubGraphFromBranchesWithExplicitEdge () throws Exception
	{
		ISubCompoundGraphFactory subGraphfactory = testInstance.subgraphFactory() ; 
		subGraphfactory.addElement(node3) ;
		subGraphfactory.addElement(node4) ;
		subGraphfactory.addElement(node6) ;
		subGraphfactory.addElement(edge7);
		ISubCompoundGraph  inducedSubGraph = subGraphfactory.createInducedSubgraph() ;
		assertEquals ( "from original Graph" , testInstance , inducedSubGraph.getSuperGraph()) ;
		assertEquals ( "5 nodes" , NUMERIC_VALUE[5] , inducedSubGraph.getNumNodes()) ;
		assertEquals ( "3 edges" , NUMERIC_VALUE[3] , inducedSubGraph.getNumEdges()) ;
		assertEquals ( "3 top nodes" , NUMERIC_VALUE[3] , inducedSubGraph.getNumTopNodes()) ;
	}
	
//	@Test
//	public final void testRemoveSubgraphWithSingleNode() {
//		ISubCompoundGraphFactory subgraphFact = this.testInstance.subgraphFactory();
//		subgraphFact.addNode(this.node2);
//		ISubCompoundGraph subGraph = subgraphFact.createInducedSubgraph();
//		this.testInstance.removeSubgraph(subGraph);
//		assertEquals("Expected num nodes", EXPECTED_NODES_AFTER_NODE2_DELETED, this.testInstance.getNumNodes());
//		assertEquals("Expected num edges", EXPECTED_EDGES_AFTER_NODE2_DELETED, this.testInstance.getNumEdges());
//		assertTrue("node 2 removed", this.testInstance.containsNode(this.node2)== false);
//		assertTrue("edge 2 removed", this.testInstance.containsEdge(this.edge2)== false);
//		assertTrue("edge 1 removed", this.testInstance.containsEdge(this.edge1)== false);
//		assertTrue("edge 3 removed", this.testInstance.containsEdge(this.edge2)== false);
//		assertTrue("edge 4 removed", this.testInstance.containsEdge(this.edge4)== false);
//		assertTrue("node 2 marked removed", this.node2.isRemoved());
//		assertTrue("edge 2 marked removed", this.edge2.isRemoved());
//		assertTrue("edge 1 marked removed", this.edge1.isRemoved());
//		assertTrue("edge 3 marked removed", this.edge2.isRemoved());
//		assertTrue("edge 4 marked removed", this.edge4.isRemoved());
//	}
//
//	@Test
//	public final void testRemoveSubgraphWithSingleEdge() {
//		ISubCompoundGraphFactory subgraphFact = this.testInstance.subgraphFactory();
//		subgraphFact.addEdge(this.edge2);
//		ISubCompoundGraph subGraph = subgraphFact.createInducedSubgraph();
//		this.testInstance.removeSubgraph(subGraph);
//		assertEquals("Expected num nodes", EXPECTED_NUM_NODES, this.testInstance.getNumNodes());
//		assertEquals("Expected num edges", EXPECTED_EDGES_AFTER_EDGE2_DELETED, this.testInstance.getNumEdges());
//		assertTrue("edge 2 removed", this.testInstance.containsDirectedEdge(this.edge2.getConnectedNodes())== false);
//		assertTrue("edge 2 removed", this.testInstance.containsEdge(this.edge2)== false);
//		assertTrue("edge 2 marked removed", this.edge2.isRemoved());
//	}
//
//	@Test
//	public final void testRemoveInducedSubgraphWithSingleEdge() {
//		ISubCompoundGraphFactory subgraphFact = this.testInstance.subgraphFactory();
//		subgraphFact.addNode(this.node5);
//		subgraphFact.addEdge(this.edge2);
//		ISubCompoundGraph subGraph = subgraphFact.createInducedSubgraph();
//		this.testInstance.removeSubgraph(subGraph);
//		assertEquals("Expected num nodes", EXPECTED_NODES_AFTER_NODE5_DELETED, this.testInstance.getNumNodes());
//		assertEquals("Expected num edges", EXPECTED_EDGES_AFTER_EDGE2_NODE5_DELETED, this.testInstance.getNumEdges());
//		assertTrue("node 5 removed", this.testInstance.containsNode(this.node5)== false);
//		assertTrue("edge 2 removed", this.testInstance.containsEdge(this.edge2)== false);
//		assertTrue("edge 2 removed", this.testInstance.containsDirectedEdge(this.edge2.getConnectedNodes())== false);
//		assertTrue("edge 1 present", this.testInstance.containsEdge(this.edge1));
//		assertTrue("node 5 marked removed", this.node5.isRemoved());
//		assertTrue("edge 2 marked removed", this.edge2.isRemoved());
//	}
//
//	@Test
//	public final void testRemoveSubgraphRemoveInducedGraph() {
//		ISubCompoundGraphFactory subgraphFact = this.testInstance.subgraphFactory();
//		subgraphFact.addNode(this.node1);
//		subgraphFact.addNode(this.node2);
//		ISubCompoundGraph subGraph = subgraphFact.createInducedSubgraph();
//		this.testInstance.removeSubgraph(subGraph);
//		assertEquals("Expected num nodes", EXPECTED_NODES_AFTER_NODES_1_AND_2_DELETED, this.testInstance.getNumNodes());
//		assertEquals("Expected num edges", EXPECTED_EDGES_AFTER_NODES_1_AND_2_DELETED, this.testInstance.getNumEdges());
//		assertTrue("node 1 removed", this.testInstance.containsNode(this.node1)== false);
//		assertTrue("node 2 removed", this.testInstance.containsNode(this.node2)== false);
//		assertTrue("edge 2 removed", this.testInstance.containsEdge(this.edge2)== false);
//		assertTrue("edge 2 removed", this.testInstance.containsDirectedEdge(this.edge2.getConnectedNodes())== false);
//		assertTrue("edge 1 removed", this.testInstance.containsEdge(this.edge1) == false);
//		assertTrue("node 1 marked removed", this.node1.isRemoved());
//		assertTrue("node 2 marked removed", this.node2.isRemoved());
//	}
//
//	@Test
//	public final void testRemoveSubgraphRemoveNonInducedGraph() {
//		ISubCompoundGraphFactory subgraphFact = this.testInstance.subgraphFactory();
//		subgraphFact.addNode(this.node1);
//		subgraphFact.addNode(this.node2);
//		ISubCompoundGraph subGraph = subgraphFact.createSubgraph();
//		this.testInstance.removeSubgraph(subGraph);
//		assertEquals("Expected num nodes", EXPECTED_NODES_AFTER_NODES_1_AND_2_DELETED, this.testInstance.getNumNodes());
//		assertEquals("Expected num edges", EXPECTED_EDGES_AFTER_NODES_1_AND_2_DELETED, this.testInstance.getNumEdges());
//		assertTrue("node 1 removed", this.testInstance.containsNode(this.node1)== false);
//		assertTrue("node 2 removed", this.testInstance.containsNode(this.node2)== false);
//		assertTrue("edge 2 removed", this.testInstance.containsEdge(this.edge2)== false);
//		assertTrue("edge 2 removed", this.testInstance.containsDirectedEdge(this.edge2.getConnectedNodes())== false);
//		assertTrue("edge 1 removed", this.testInstance.containsEdge(this.edge1) == false);
//		assertTrue("node 1 marked removed", this.node1.isRemoved());
//		assertTrue("node 2 marked removed", this.node2.isRemoved());
//	}
//
//	
//	@Ignore @Test//(expected=IllegalArgumentException.class) == ?? 
//	public final void testCopyNonInducedSubGraph(){
//		ISubCompoundGraphFactory subGraphfactory = testInstance.subgraphFactory() ; 
//		subGraphfactory.addElement(node1) ;
//		ISubCompoundGraph  nonInducedSubGraph = subGraphfactory.createSubgraph() ;
//		this.testInstance.getRootNode().getChildCompoundGraph().copyHere(nonInducedSubGraph) ;
//		assertEquals ( "copied Edges nodes" , EXPECTED_NUM_NODES + 3 , testInstance.getNumNodes() ) ;
//		assertEquals ( "CopiedEdges edges" , EXPECTED_NUM_EDGES, testInstance.getNumEdges() ) ;
//	}
//	
//	@Ignore @Test
//	public final void testCopyInducedSubGraph(){
//		ISubCompoundGraphFactory subGraphfactory = testInstance.subgraphFactory() ; 
//		subGraphfactory.addElement(node1) ;
//		subGraphfactory.addElement(node2) ;
//		ISubCompoundGraph  inducedSubGraph = subGraphfactory.createInducedSubgraph() ;
//		this.testInstance.getRootNode().getChildCompoundGraph().copyHere(inducedSubGraph) ;
//		assertEquals ( "copied Edges nodes" , EXPECTED_NUM_NODES + 8 , testInstance.getNumNodes() ) ;
//		assertEquals ( "CopiedEdges edges" , EXPECTED_NUM_EDGES + 9, testInstance.getNumEdges() ) ;
//	}
//	
//	@Test//(expected=IllegalArgumentException.class) == ?? 
//	public final void testCopyEmptySubGraph(){
//		ISubCompoundGraphFactory subGraphfactory = testInstance.subgraphFactory() ; 
//		ISubCompoundGraph  inducedSubGraph = subGraphfactory.createInducedSubgraph() ;
//		this.testInstance.getRootNode().getChildCompoundGraph().copyHere(inducedSubGraph) ;
//		assertEquals ( "copied Edges nodes" , EXPECTED_NUM_NODES , testInstance.getNumNodes() ) ;
//		assertEquals ( "CopiedEdges edges" , EXPECTED_NUM_EDGES, testInstance.getNumEdges() ) ;
//	}
	
}
