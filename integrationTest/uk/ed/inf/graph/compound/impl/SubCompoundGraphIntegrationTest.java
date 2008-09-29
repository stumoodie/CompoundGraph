package uk.ed.inf.graph.compound.impl;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SubCompoundGraphIntegrationTest {
	private static final int EXPECTED_INITIAL_EDGES = 0;
	private static final int EXPECTED_INITIAL_NODES = 1;
	private static final int EXPECTED_NUM_EDGES = 9;
	private static final int EXPECTED_NUM_NODES = 9;
	private static final int EXPECTED_ROOT_NODE_IDX = 0;
	private static final int UNKNOWN_EDGE_HIGH_IDX = 99;
	private static final int UNKNOWN_EDGE_LOW_IDX = -1;
	private static final int UNKNOWN_NODE_HIGH_IDX = 99;
	private static final int UNKNOWN_NODE_LOW_IDX = -1;
//	private static final int EXPECTED_LAST_NODE = 8;
	private static final int NUM_ROOT_NODE_EDGES = 4;
	private static final int NUM_NODE1_EDGES = 2;
	private static final int NUM_NODE2_EDGES = 1;
	private static final int NUM_NODE3_EDGES = 0;
	private static final int NUM_NODE4_EDGES = 0;
	private static final int NUM_NODE5_EDGES = 0;
	private static final int NUM_NODE6_EDGES = 2;
	private static final int NUM_NODE7_EDGES = 0;
	private static final int NUM_NODE8_EDGES = 0;
	private static final int EXPECTED_NODES_AFTER_NODE2_DELETED = 4;
	private static final int EXPECTED_EDGES_AFTER_NODE2_DELETED = 2;
	private static final int EXPECTED_EDGES_AFTER_EDGE2_DELETED = 8;
	private static final int EXPECTED_NODES_AFTER_NODE5_DELETED = 8;
	private static final int EXPECTED_EDGES_AFTER_EDGE2_NODE5_DELETED = 8;
	private static final int EXPECTED_NODES_AFTER_NODES_1_AND_2_DELETED = 1;
	private static final int EXPECTED_EDGES_AFTER_NODES_1_AND_2_DELETED = 0;
	private static final int [] NUMERIC_VALUE = { 0,1,2,3,4,5,6,7,8,9,10 } ;

	
	private CompoundGraph testInstance;
	private CompoundNode rootNode;
	private CompoundNode node1;
	private CompoundNode node2;
	private CompoundNode node3;
	private CompoundNode node4;
	private CompoundNode node5;
	private CompoundNode node6;
	private CompoundNode node7;
	private CompoundNode node8;
	private CompoundEdge edge1;
	private CompoundEdge edge2;
	private CompoundEdge edge3;
	private CompoundEdge edge4;
	private CompoundEdge edge5;
	private CompoundEdge edge6;
	private CompoundEdge edge7;
	private CompoundEdge edge8;
	private CompoundEdge edge9;

	@Before
	public void setUp() throws Exception {
		this.testInstance = new CompoundGraph();
		this.rootNode = this.testInstance.getRootNode();
		CompoundNodeFactory rootNodeFact = this.testInstance.getRootNode().getChildCompoundGraph().nodeFactory();
		node1 = rootNodeFact.createNode();
		node2 = rootNodeFact.createNode();
		CompoundNodeFactory node1Fact = node1.getChildCompoundGraph().nodeFactory();
		node3 = node1Fact.createNode();
		node4 = node1Fact.createNode();
		CompoundNodeFactory node2Fact = node2.getChildCompoundGraph().nodeFactory();
		node5 = node2Fact.createNode();
		node6 = node2Fact.createNode();
		CompoundNodeFactory node6Fact = node6.getChildCompoundGraph().nodeFactory();
		node7 = node6Fact.createNode();
		CompoundNodeFactory node7Fact = node7.getChildCompoundGraph().nodeFactory();
		node8 = node7Fact.createNode();
		
		CompoundEdgeFactory edgeFact = this.testInstance.edgeFactory();
		edgeFact.setPair(node1, node2);
		edge1 = edgeFact.createEdge();
		edgeFact.setPair(node2, node1);
		edge2 = edgeFact.createEdge();
		edgeFact.setPair(node2, node6);
		edge3 = edgeFact.createEdge();
		edgeFact.setPair(node2, node4);
		edge4 = edgeFact.createEdge();
		edgeFact.setPair(node7, node6);
		edge5 = edgeFact.createEdge();
		edgeFact.setPair(node6, node7);
		edge6 = edgeFact.createEdge();
		edgeFact.setPair(node7, node3);
		edge7 = edgeFact.createEdge();
		edgeFact.setPair(node1, node1);
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
		SubCompoundGraphFactory subGraphfactory = testInstance.subgraphFactory() ; 
		subGraphfactory.addNode(node1) ;
		SubCompoundGraph  nonInducedSubGraph = subGraphfactory.createSubgraph() ;
		assertEquals ( "from original Graph" , testInstance , nonInducedSubGraph.getSuperGraph()) ;
		assertEquals ( "3 nodes" , NUMERIC_VALUE[3] , nonInducedSubGraph.getNumNodes()) ;
		assertEquals ( "1 edges" , NUMERIC_VALUE[2] , nonInducedSubGraph.getNumEdges()) ;
		assertEquals ( "1 nodes" , NUMERIC_VALUE[1] , nonInducedSubGraph.getNumTopNodes()) ;
	}
	
	@Test
	public final void testCreateInducedSubGraph () throws Exception
	{
		SubCompoundGraphFactory subGraphfactory = testInstance.subgraphFactory() ; 
		subGraphfactory.addNode(node1) ;
		subGraphfactory.addNode(node2) ;
		SubCompoundGraph  nonInducedSubGraph = subGraphfactory.createSubgraph() ;
		assertEquals ( "from original Graph" , testInstance , nonInducedSubGraph.getSuperGraph()) ;
		assertEquals ( "8 nodes" , NUMERIC_VALUE[8] , nonInducedSubGraph.getNumNodes()) ;
		assertEquals ( "9 edges" , NUMERIC_VALUE[9] , nonInducedSubGraph.getNumEdges()) ;
		assertEquals ( "2 top nodes" , NUMERIC_VALUE[2] , nonInducedSubGraph.getNumTopNodes()) ;
	}
	
//	@Test
//	public final void testRemoveSubgraphWithSingleNode() {
//		SubCompoundGraphFactory subgraphFact = this.testInstance.subgraphFactory();
//		subgraphFact.addNode(this.node2);
//		SubCompoundGraph subGraph = subgraphFact.createInducedSubgraph();
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
//		SubCompoundGraphFactory subgraphFact = this.testInstance.subgraphFactory();
//		subgraphFact.addEdge(this.edge2);
//		SubCompoundGraph subGraph = subgraphFact.createInducedSubgraph();
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
//		SubCompoundGraphFactory subgraphFact = this.testInstance.subgraphFactory();
//		subgraphFact.addNode(this.node5);
//		subgraphFact.addEdge(this.edge2);
//		SubCompoundGraph subGraph = subgraphFact.createInducedSubgraph();
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
//		SubCompoundGraphFactory subgraphFact = this.testInstance.subgraphFactory();
//		subgraphFact.addNode(this.node1);
//		subgraphFact.addNode(this.node2);
//		SubCompoundGraph subGraph = subgraphFact.createInducedSubgraph();
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
//		SubCompoundGraphFactory subgraphFact = this.testInstance.subgraphFactory();
//		subgraphFact.addNode(this.node1);
//		subgraphFact.addNode(this.node2);
//		SubCompoundGraph subGraph = subgraphFact.createSubgraph();
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
//		SubCompoundGraphFactory subGraphfactory = testInstance.subgraphFactory() ; 
//		subGraphfactory.addNode(node1) ;
//		SubCompoundGraph  nonInducedSubGraph = subGraphfactory.createSubgraph() ;
//		this.testInstance.getRootNode().getChildCompoundGraph().copyHere(nonInducedSubGraph) ;
//		assertEquals ( "copied Edges nodes" , EXPECTED_NUM_NODES + 3 , testInstance.getNumNodes() ) ;
//		assertEquals ( "CopiedEdges edges" , EXPECTED_NUM_EDGES, testInstance.getNumEdges() ) ;
//	}
//	
//	@Ignore @Test
//	public final void testCopyInducedSubGraph(){
//		SubCompoundGraphFactory subGraphfactory = testInstance.subgraphFactory() ; 
//		subGraphfactory.addNode(node1) ;
//		subGraphfactory.addNode(node2) ;
//		SubCompoundGraph  inducedSubGraph = subGraphfactory.createInducedSubgraph() ;
//		this.testInstance.getRootNode().getChildCompoundGraph().copyHere(inducedSubGraph) ;
//		assertEquals ( "copied Edges nodes" , EXPECTED_NUM_NODES + 8 , testInstance.getNumNodes() ) ;
//		assertEquals ( "CopiedEdges edges" , EXPECTED_NUM_EDGES + 9, testInstance.getNumEdges() ) ;
//	}
//	
//	@Test//(expected=IllegalArgumentException.class) == ?? 
//	public final void testCopyEmptySubGraph(){
//		SubCompoundGraphFactory subGraphfactory = testInstance.subgraphFactory() ; 
//		SubCompoundGraph  inducedSubGraph = subGraphfactory.createInducedSubgraph() ;
//		this.testInstance.getRootNode().getChildCompoundGraph().copyHere(inducedSubGraph) ;
//		assertEquals ( "copied Edges nodes" , EXPECTED_NUM_NODES , testInstance.getNumNodes() ) ;
//		assertEquals ( "CopiedEdges edges" , EXPECTED_NUM_EDGES, testInstance.getNumEdges() ) ;
//	}
	
}
