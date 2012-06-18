package uk.ac.ed.inf.graph.compound.newimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundGraphMoveBuilder;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraphFactory;
import uk.ac.ed.inf.graph.compound.testfixture.ElementAttribute;

public class CompoundGraphMoveWithNodeChildAndIncidentEdges {
	private static final int EXPECTED_NODES_IN_ROOT = 2;
	private static final int EXPECTED_ELEMENTS_IN_ROOT = 3;
	private static final int EXPECTED_EDGES_IN_ROOT = 1;
	private static final int EXPECTED_NUM_NODES = 7;
	private static final int EXPECTED_NUM_EDGES = 3;
	private static final int EXPECTED_NUM_ELEMENTS = 10;
	private static final int EXPECTED_INIT_ELEMENTS_IN_NODE3 = 5;
	private static final int EXPECTED_INIT_ELEMENTS_IN_EDGE1 = 0;
	private static final int EXPECTED_NODES_IN_ROOT_POST_MOVE = 3;
	private static final int EXPECTED_ELEMENTS_IN_ROOT_POST_MOVE = 6;
	private static final int EXPECTED_EDGES_IN_ROOT_POST_MOVE = 3;
	private static final int EXPECTED_ELEMENTS_IN_NODE3_POST_MOVE = 2;
	private static final int EXPECTED_NODES_IN_NODE3_POST_MOVE = 2;
	private static final int EXPECTED_EDGES_IN_NODE3_POST_MOVE = 0;
	private static final int EXPECTED_ELEMENTS_IN_NEW_EDGE1_POST_MOVE = 0;
	private static final int EXPECTED_ELEMENTS_IN_NEW_EDGE2_POST_MOVE = 0;
	private static final int EXPECTED_NODES_IN_ROOT_POST_MOVE2 = 4;
	private static final int EXPECTED_ELEMENTS_IN_ROOT_POST_MOVE2 = 7;
	private static final int EXPECTED_EDGES_IN_ROOT_POST_MOVE2 = 3;
	private static final int EXPECTED_ELEMENTS_IN_NODE3_POST_MOVE2 = 1;
	private static final int EXPECTED_NODES_IN_NODE3_POST_MOVE2 = 1;
	private static final int EXPECTED_EDGES_IN_NODE3_POST_MOVE2 = 0;
	private static final int EXPECTED_ELEMENTS_IN_NEW_EDGE1_POST_MOVE2 = 0;
	private static final int EXPECTED_ELEMENTS_IN_NEW_EDGE2_POST_MOVE2 = 0;
	private static final String NODE2_ID = "N2";
	private static final String NODE5_ID = "N5";
	private static final String NODE1_ID = "N1";
	private ICompoundGraph testInstance;
	private ICompoundEdge edge1;
	private ICompoundEdge edge2;
	private ICompoundNode node1;
	private ICompoundNode node2;
	private ICompoundNode node3;
	private ICompoundNode node4;
	private ICompoundNode node5;
	private CompoundGraphMoveWithNodeChildAndIncidentEdgesGraphTestFixture testFixture;
	private ICompoundNode node6;
	private ICompoundEdge edge3;	
	
	@Before
	public void setUp() throws Exception {
		this.testFixture = new CompoundGraphMoveWithNodeChildAndIncidentEdgesGraphTestFixture();
		this.testInstance = this.testFixture.getGraph();
		this.node1 = this.testFixture.getNoden1();
		this.node2 = this.testFixture.getNoden2();
		this.node3 = this.testFixture.getNoden3();
		this.node4 = this.testFixture.getNoden4();
		this.node5 = this.testFixture.getNoden5();
		this.edge1 = this.testFixture.getEdgee1();
		this.edge2 = this.testFixture.getEdgee2();
		this.edge3 = this.testFixture.getEdgee3();
		this.node6 = this.testFixture.getNoden6();
		
//		IElementAttribute rootAtt = new ElementAttribute("root");
//		ElementAttributeFactory attFact = new ElementAttributeFactory(); 
//		this.testInstance = new CompoundGraph(rootAtt);
//		ICompoundNodeFactory rootNodeFact = this.testInstance.nodeFactory(); 
//		rootNodeFact.setAttributeFactory(attFact);
//		attFact.setName("N1");
//		node1 = rootNodeFact.createNode();
//		attFact.setName("N2");
//		node2 = rootNodeFact.createNode();
//		attFact.setName("N3");
//		node3 = rootNodeFact.createNode();
//		attFact.setName("N4");
//		node4 = rootNodeFact.createNode();
//		ICompoundEdgeFactory edgeFact = this.testInstance.edgeFactory();
//		edgeFact.setAttributeFactory(attFact);
//		attFact.setName("E1");
//		edgeFact.setPair(new CompoundNodePair(node1, node2));
//		edge1 = edgeFact.createEdge();
//		ICompoundNodeFactory edge1NodeFact = edge1.getChildCompoundGraph().nodeFactory();
//		attFact.setName("N5");
//		edge1NodeFact.setAttributeFactory(attFact);
//		node5 = edge1NodeFact.createNode();
//		
//		attFact.setName("E2");
//		edgeFact.setPair(new CompoundNodePair(node2, node4));
//		edge2 = edgeFact.createEdge();
	}

	@After
	public void tearDown() throws Exception {
		this.testInstance = null;
		this.node1 = null;
		this.node2 = null;
		this.node3 = null;
		this.node4 = null;
		this.node5 = null;
		this.node6 = null;
		this.edge1 = null;
		this.edge2 = null;
		this.edge3 = null;
	}

	
	@Test
	public void testValidStructure() {
		assertEquals("num nodes in total", EXPECTED_NUM_NODES, this.testInstance.numNodes());
		assertEquals("num edges in total", EXPECTED_NUM_EDGES, this.testInstance.numEdges());
		assertEquals("num nodes in total", EXPECTED_NUM_ELEMENTS, this.testInstance.numElements());
		assertEquals("num nodes in root", EXPECTED_NODES_IN_ROOT, this.testInstance.getRoot().getChildCompoundGraph().numNodes());
		assertEquals("num edges in root", EXPECTED_EDGES_IN_ROOT, this.testInstance.getRoot().getChildCompoundGraph().numEdges());
		assertEquals("num nodes in root", EXPECTED_ELEMENTS_IN_ROOT, this.testInstance.getRoot().getChildCompoundGraph().numElements());
		assertEquals("num elements in node3", EXPECTED_INIT_ELEMENTS_IN_NODE3, this.node3.getChildCompoundGraph().numElements());
		assertEquals("num elements in edge1", EXPECTED_INIT_ELEMENTS_IN_EDGE1, this.edge1.getChildCompoundGraph().numElements());
		assertTrue("expected node", this.testInstance.getRoot().getChildCompoundGraph().containsNode(node3));
		assertTrue("expected node", this.testInstance.getRoot().getChildCompoundGraph().containsNode(node4));
		assertTrue("expected edge", this.testInstance.getRoot().getChildCompoundGraph().containsEdge(edge2));
		assertTrue("expected node", this.node3.getChildCompoundGraph().containsNode(node1));
		assertTrue("expected node", this.node3.getChildCompoundGraph().containsNode(node2));
		assertTrue("expected node", this.node3.getChildCompoundGraph().containsEdge(edge1));
		assertTrue("expected node", this.node2.getChildCompoundGraph().containsNode(node5));
		assertTrue("expected node", this.node3.getChildCompoundGraph().containsNode(node6));
		assertTrue("expected node", this.node3.getChildCompoundGraph().containsEdge(edge3));
		assertEquals("expected parent", this.node2, this.node5.getParent());
		assertTrue("expected connected pair", this.edge1.hasDirectedEnds(node1, node2));
	}
	
	
	@Test
	public void testMoveWithIncidentEdge() {
		ISubCompoundGraphFactory subGraphFact = this.testInstance.subgraphFactory();
		subGraphFact.addElement(node2);
		ISubCompoundGraph sub = subGraphFact.createInducedSubgraph();
		ICompoundGraphMoveBuilder moveBuilder = this.testInstance.getRoot().getChildCompoundGraph().newMoveBuilder();
		moveBuilder.setSourceSubgraph(sub);
		moveBuilder.makeMove();
		ISubCompoundGraph removedSub = moveBuilder.getRemovedComponents();
		ISubCompoundGraph movedSub = moveBuilder.getMovedComponents();
		ICompoundEdge newEdge1 = null;
		ICompoundEdge newEdge2 = null;
		ICompoundEdge newEdge3 = null;
		Iterator<ICompoundEdge> movedEdgeIter = movedSub.edgeIterator();
		while(movedEdgeIter.hasNext()){
			ICompoundEdge edge = movedEdgeIter.next(); 
			String edgeName = ((ElementAttribute)edge.getAttribute()).getName();
			if(edgeName.equals("E1")){
				newEdge1 = edge;
			}
			else if(edgeName.equals("E2")){
				newEdge2 = edge;
			}
			else if(edgeName.equals("E3")){
				newEdge3 = edge;
			}
		}
		Iterator<ICompoundNode> movedNodeIter = movedSub.nodeIterator();
		ICompoundNode newNode2 = null;
		ICompoundNode newNode5 = null;
		while(movedNodeIter.hasNext()){
			ICompoundNode newNode = movedNodeIter.next();
			if(((ElementAttribute)newNode.getAttribute()).getName().equals(NODE2_ID)){
				newNode2  = newNode;
			}
			else if(((ElementAttribute)newNode.getAttribute()).getName().equals(NODE5_ID)){
				newNode5  = newNode;
			}
		}
		ICompoundEdge removedEdge = null;
		Iterator<ICompoundEdge> removedEdgeIter = removedSub.edgeIterator();
		if(removedEdgeIter.hasNext()){
			removedEdge = removedEdgeIter.next();
		}
		ICompoundNode removedNode = null;
		Iterator<ICompoundNode> removedNodeIter = removedSub.nodeIterator();
		if(removedNodeIter.hasNext()){
			removedNode = removedNodeIter.next();
		}
		assertEquals("num nodes in total", EXPECTED_NUM_NODES, this.testInstance.numNodes());
		assertEquals("num edges in total", EXPECTED_NUM_EDGES, this.testInstance.numEdges());
		assertEquals("num nodes in total", EXPECTED_NUM_ELEMENTS, this.testInstance.numElements());
		assertEquals("num nodes in root", EXPECTED_NODES_IN_ROOT_POST_MOVE, this.testInstance.getRoot().getChildCompoundGraph().numNodes());
		assertEquals("num edges in root", EXPECTED_EDGES_IN_ROOT_POST_MOVE, this.testInstance.getRoot().getChildCompoundGraph().numEdges());
		assertEquals("num nodes in root", EXPECTED_ELEMENTS_IN_ROOT_POST_MOVE, this.testInstance.getRoot().getChildCompoundGraph().numElements());
		assertEquals("num nodes in node3", EXPECTED_NODES_IN_NODE3_POST_MOVE, this.node3.getChildCompoundGraph().numNodes());
		assertEquals("num edges in node3", EXPECTED_EDGES_IN_NODE3_POST_MOVE, this.node3.getChildCompoundGraph().numEdges());
		assertEquals("num element in node3", EXPECTED_ELEMENTS_IN_NODE3_POST_MOVE, this.node3.getChildCompoundGraph().numElements());
		assertTrue("expected node", this.testInstance.getRoot().getChildCompoundGraph().containsNode(node3));
		assertTrue("expected node", this.testInstance.getRoot().getChildCompoundGraph().containsNode(newNode2));
		assertTrue("expected node", this.testInstance.getRoot().getChildCompoundGraph().containsNode(node4));
		assertTrue("expected edge", this.testInstance.getRoot().getChildCompoundGraph().containsEdge(newEdge1));
		assertTrue("expected edge", this.testInstance.getRoot().getChildCompoundGraph().containsEdge(newEdge2));
		assertTrue("expected node", this.node3.getChildCompoundGraph().containsNode(node1));
		assertTrue("expected node", this.node3.getChildCompoundGraph().containsNode(node6));
		assertTrue("expected node", newNode2.getChildCompoundGraph().containsNode(newNode5));
		assertTrue("expected edge removed", this.edge1.isRemoved());
		assertTrue("expected edge removed", this.edge2.isRemoved());
		assertTrue("expected node removed", this.node2.isRemoved());
		assertTrue("expected node not removed", this.node5.isRemoved());
		assertNotNull("new edge exists", newEdge1);
		assertNotNull("new edge exists", newEdge2);
		assertTrue("expected elements", newNode2.getChildCompoundGraph().containsNode(newNode5));
		assertEquals("expected elements", EXPECTED_ELEMENTS_IN_NEW_EDGE1_POST_MOVE, newEdge1.getChildCompoundGraph().numElements());
		assertEquals("expected elements", EXPECTED_ELEMENTS_IN_NEW_EDGE2_POST_MOVE, newEdge2.getChildCompoundGraph().numElements());
		assertTrue("new out node exists", newEdge1.getConnectedNodes().hasDirectedEnds(node1, newNode2));
		assertTrue("new in node exists", newEdge2.getConnectedNodes().hasDirectedEnds(node4, newNode2));
		assertTrue("new in node exists", newEdge3.getConnectedNodes().hasDirectedEnds(newNode5, node6));
		assertNotNull("new node exists", newNode2);
		assertNotNull("removed edge exists", removedEdge);
		assertNotNull("removed node exists", removedNode);
	}

	@Test
	public void testMoveWithIncidentNodes() {
		ISubCompoundGraphFactory subGraphFact = this.testInstance.subgraphFactory();
		subGraphFact.addElement(node1);
		subGraphFact.addElement(node2);
		ISubCompoundGraph sub = subGraphFact.createInducedSubgraph();
		ICompoundGraphMoveBuilder moveBuilder = this.testInstance.getRoot().getChildCompoundGraph().newMoveBuilder();
		moveBuilder.setSourceSubgraph(sub);
		moveBuilder.makeMove();
		ISubCompoundGraph removedSub = moveBuilder.getRemovedComponents();
		ISubCompoundGraph movedSub = moveBuilder.getMovedComponents();
		ICompoundNode newNode1 = null;
		ICompoundNode newNode2 = null;
		ICompoundNode newNode5 = null;
		Iterator<ICompoundNode> movedNodeIter = movedSub.nodeIterator();
		while(movedNodeIter.hasNext()){
			ICompoundNode node = movedNodeIter.next(); 
			String nodeName = ((ElementAttribute)node.getAttribute()).getName();
			if(nodeName.equals(NODE1_ID)){
				newNode1 = node;
			}
			else if(nodeName.equals(NODE2_ID)){
				newNode2 = node;
			}
			else if(nodeName.equals(NODE5_ID)){
				newNode5 = node;
			}
		}
		ICompoundEdge newEdge1 = null;
		ICompoundEdge newEdge2 = null;
		ICompoundEdge newEdge3 = null;
		Iterator<ICompoundEdge> movedEdgeIter = movedSub.edgeIterator();
		while(movedEdgeIter.hasNext()){
			ICompoundEdge edge = movedEdgeIter.next(); 
			String edgeName = ((ElementAttribute)edge.getAttribute()).getName();
			if(edgeName.equals("E1")){
				newEdge1 = edge;
			}
			else if(edgeName.equals("E2")){
				newEdge2 = edge;
			}
			else if(edgeName.equals("E3")){
				newEdge3 = edge;
			}
		}
//		ICompoundNode newOutNode = newEdge1.getConnectedNodes().getOutNode();
//		ICompoundNode newInNode = newEdge1.getConnectedNodes().getInNode();
		ICompoundEdge removedEdge = null;
		Iterator<ICompoundEdge> removedEdgeIter = removedSub.edgeIterator();
		if(removedEdgeIter.hasNext()){
			removedEdge = removedEdgeIter.next();
		}
//		ICompoundNode removedOutNode = removedEdge.getConnectedNodes().getOutNode();
//		ICompoundNode removedInNode = removedEdge.getConnectedNodes().getInNode();
		assertEquals("num nodes in total", EXPECTED_NUM_NODES, this.testInstance.numNodes());
		assertEquals("num edges in total", EXPECTED_NUM_EDGES, this.testInstance.numEdges());
		assertEquals("num nodes in total", EXPECTED_NUM_ELEMENTS, this.testInstance.numElements());
		assertEquals("num nodes in root", EXPECTED_NODES_IN_ROOT_POST_MOVE2, this.testInstance.getRoot().getChildCompoundGraph().numNodes());
		assertEquals("num edges in root", EXPECTED_EDGES_IN_ROOT_POST_MOVE2, this.testInstance.getRoot().getChildCompoundGraph().numEdges());
		assertEquals("num nodes in root", EXPECTED_ELEMENTS_IN_ROOT_POST_MOVE2, this.testInstance.getRoot().getChildCompoundGraph().numElements());
		assertEquals("num element in node3", EXPECTED_ELEMENTS_IN_NODE3_POST_MOVE2, this.node3.getChildCompoundGraph().numElements());
		assertEquals("num nodes in node3", EXPECTED_NODES_IN_NODE3_POST_MOVE2, this.node3.getChildCompoundGraph().numNodes());
		assertEquals("num edges in node3", EXPECTED_EDGES_IN_NODE3_POST_MOVE2, this.node3.getChildCompoundGraph().numEdges());
		assertTrue("expected node", this.testInstance.getRoot().getChildCompoundGraph().containsNode(newNode1));
		assertTrue("expected node", this.testInstance.getRoot().getChildCompoundGraph().containsNode(newNode2));
		assertTrue("expected node", this.testInstance.getRoot().getChildCompoundGraph().containsNode(node3));
		assertTrue("expected node", this.testInstance.getRoot().getChildCompoundGraph().containsNode(node4));
		assertTrue("expected edge", this.testInstance.getRoot().getChildCompoundGraph().containsEdge(newEdge1));
		assertTrue("expected edge", this.testInstance.getRoot().getChildCompoundGraph().containsEdge(newEdge2));
		assertTrue("expected edge", this.testInstance.getRoot().getChildCompoundGraph().containsEdge(newEdge3));
		assertTrue("expected node", newNode2.getChildCompoundGraph().containsNode(newNode5));
		assertEquals("expected elements", EXPECTED_ELEMENTS_IN_NEW_EDGE1_POST_MOVE2, newEdge1.getChildCompoundGraph().numElements());
		assertEquals("expected elements", EXPECTED_ELEMENTS_IN_NEW_EDGE2_POST_MOVE2, newEdge2.getChildCompoundGraph().numElements());
		assertTrue("expected edge removed", this.edge1.isRemoved());
		assertTrue("expected edge removed", this.edge2.isRemoved());
		assertTrue("expected node removed", this.node1.isRemoved());
		assertTrue("expected node removed", this.node2.isRemoved());
		assertTrue("expected node removed", this.node5.isRemoved());
		assertNotNull("new edge exists", newEdge1);
		assertNotNull("new edge exists", newEdge2);
		assertNotNull("new edge exists", newEdge3);
		assertTrue("new out node exists", newEdge1.getConnectedNodes().hasDirectedEnds(newNode1, newNode2));
		assertTrue("new in node exists", newEdge2.getConnectedNodes().hasDirectedEnds(node4, newNode2));
		assertTrue("new in node exists", newEdge3.getConnectedNodes().hasDirectedEnds(newNode5, node6));
		assertNotNull("removed edge exists", removedEdge);
	}

}
