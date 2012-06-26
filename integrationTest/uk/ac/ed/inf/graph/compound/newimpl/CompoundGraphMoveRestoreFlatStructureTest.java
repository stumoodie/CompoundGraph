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
import uk.ac.ed.inf.graph.compound.IElementAttribute;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraphFactory;
import uk.ac.ed.inf.graph.compound.testfixture.ElementAttribute;
import uk.ac.ed.inf.graph.state.IGraphState;

public class CompoundGraphMoveRestoreFlatStructureTest {
	private static final int EXPECTED_NODES_IN_ROOT = 5;
	private static final int EXPECTED_ELEMENTS_IN_ROOT = 7;
	private static final int EXPECTED_EDGES_IN_ROOT = 2;
	private static final int EXPECTED_NUM_NODES = 6;
	private static final int EXPECTED_NUM_EDGES = 2;
	private static final int EXPECTED_NUM_ELEMENTS = 8;
	private static final int EXPECTED_NODES_IN_ROOT_POST_MOVE = 4;
	private static final int EXPECTED_ELEMENTS_IN_ROOT_POST_MOVE = 6;
	private static final int EXPECTED_EDGES_IN_ROOT_POST_MOVE = 2;
	private static final int EXPECTED_ELEMENTS_IN_NODE3_POST_MOVE = 1;
	private static final int EXPECTED_NODES_IN_NODE3_POST_MOVE = 1;
	private static final int EXPECTED_EDGES_IN_NODE3_POST_MOVE = 0;
	private ICompoundGraph testInstance;
	private ICompoundEdge edge1;
	private ICompoundEdge edge2;
	private ICompoundNode node1;
	private ICompoundNode node2;
	private ICompoundNode node3;
	private ICompoundNode node4;
	private ICompoundNode node5;
	private ICompoundEdge newEdge1;
	private ICompoundEdge newEdge2;
	private FlatGraphTestFixture testFixture;
	private ICompoundGraphMoveBuilder moveBuilder;
	private IGraphState originalState;	
	private IElementAttribute nodeAttribute1;
	private IElementAttribute nodeAttribute2;
	private IElementAttribute nodeAttribute3;
	private IElementAttribute nodeAttribute4;
	private IElementAttribute nodeAttribute5;
	private IElementAttribute edgeAttribute1;
	private IElementAttribute edgeAttribute2;	
	
	@Before
	public void setUp() throws Exception {
		this.testFixture = new FlatGraphTestFixture();
		this.testInstance = this.testFixture.getGraph();
		this.node1 = this.testFixture.getNoden1();
		this.node2 = this.testFixture.getNoden2();
		this.node3 = this.testFixture.getNoden3();
		this.node4 = this.testFixture.getNoden4();
		this.node5 = this.testFixture.getNoden5();
		this.edge1 = this.testFixture.getEdgee1();
		this.edge2 = this.testFixture.getEdgee2();
		this.nodeAttribute1 = this.node1.getAttribute();
		this.nodeAttribute2 = this.node2.getAttribute();
		this.nodeAttribute3 = this.node3.getAttribute();
		this.nodeAttribute4 = this.node4.getAttribute();
		this.nodeAttribute5 = this.node5.getAttribute();
		this.edgeAttribute1 = this.edge1.getAttribute();
		this.edgeAttribute2 = this.edge2.getAttribute();
		originalState = this.testInstance.getCurrentState();
		ISubCompoundGraphFactory subGraphFact = this.testInstance.subgraphFactory();
		subGraphFact.addElement(node2);
		ISubCompoundGraph sub = subGraphFact.createInducedSubgraph();
		moveBuilder = this.node3.getChildCompoundGraph().newMoveBuilder();
		moveBuilder.setSourceSubgraph(sub);
		moveBuilder.makeMove();
	}

	@After
	public void tearDown() throws Exception {
		this.testInstance = null;
		this.node1 = null;
		this.node2 = null;
		this.node3 = null;
		this.node4 = null;
		this.node5 = null;
		this.edge1 = null;
		this.edge2 = null;
		this.newEdge1 = null;
		this.newEdge2 = null;
		this.nodeAttribute1 = null;
		this.nodeAttribute2 = null;
		this.nodeAttribute3 = null;
		this.nodeAttribute4 = null;
		this.nodeAttribute5 = null;
		this.edgeAttribute1 = null;
		this.edgeAttribute2 = null;
	}

	
	@Test
	public void testValidStructure() {
		this.testInstance.restoreState(this.originalState);
		assertEquals("num nodes in total", EXPECTED_NUM_NODES, this.testInstance.numNodes());
		assertEquals("num edges in total", EXPECTED_NUM_EDGES, this.testInstance.numEdges());
		assertEquals("num nodes in total", EXPECTED_NUM_ELEMENTS, this.testInstance.numElements());
		assertEquals("num nodes in root", EXPECTED_NODES_IN_ROOT, this.testInstance.getRoot().getChildCompoundGraph().numNodes());
		assertEquals("num edges in root", EXPECTED_EDGES_IN_ROOT, this.testInstance.getRoot().getChildCompoundGraph().numEdges());
		assertEquals("num nodes in root", EXPECTED_ELEMENTS_IN_ROOT, this.testInstance.getRoot().getChildCompoundGraph().numElements());
		assertTrue("expected node", this.testInstance.getRoot().getChildCompoundGraph().containsNode(node1));
		assertTrue("expected node", this.testInstance.getRoot().getChildCompoundGraph().containsNode(node2));
		assertTrue("expected node", this.testInstance.getRoot().getChildCompoundGraph().containsNode(node3));
		assertTrue("expected node", this.testInstance.getRoot().getChildCompoundGraph().containsNode(node4));
		assertTrue("expected node", this.testInstance.getRoot().getChildCompoundGraph().containsNode(node5));
		assertTrue("expected edge", this.testInstance.getRoot().getChildCompoundGraph().containsEdge(edge1));
		assertTrue("expected edge", this.testInstance.getRoot().getChildCompoundGraph().containsEdge(edge2));
		assertEquals("expected curr element", this.node1, this.nodeAttribute1.getCurrentElement());
		assertEquals("expected curr element", this.node2, this.nodeAttribute2.getCurrentElement());
		assertEquals("expected curr element", this.node3, this.nodeAttribute3.getCurrentElement());
		assertEquals("expected curr element", this.node4, this.nodeAttribute4.getCurrentElement());
		assertEquals("expected curr element", this.node5, this.nodeAttribute5.getCurrentElement());
		assertEquals("expected curr element", this.edge1, this.edgeAttribute1.getCurrentElement());
		assertEquals("expected curr element", this.edge2, this.edgeAttribute2.getCurrentElement());
		assertTrue("expected connected pair", this.edge1.hasDirectedEnds(node1, node2));
		assertTrue("expected connected pair", this.edge2.hasDirectedEnds(node4, node2));
	}
	
	
	@Test
	public void testMoveWithIncidentEdge() {
		ISubCompoundGraph removedSub = moveBuilder.getRemovedComponents();
		ISubCompoundGraph movedSub = moveBuilder.getMovedComponents();
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
		}
		ICompoundNode newNode = null;
		Iterator<ICompoundNode> movedNodeIter = movedSub.nodeIterator();
		if(movedNodeIter.hasNext()){
			newNode = movedNodeIter.next();
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
		assertEquals("num element in node3", EXPECTED_ELEMENTS_IN_NODE3_POST_MOVE, this.node3.getChildCompoundGraph().numElements());
		assertEquals("num nodes in node3", EXPECTED_NODES_IN_NODE3_POST_MOVE, this.node3.getChildCompoundGraph().numNodes());
		assertEquals("num edges in node3", EXPECTED_EDGES_IN_NODE3_POST_MOVE, this.node3.getChildCompoundGraph().numEdges());
		assertTrue("expected node", this.testInstance.getRoot().getChildCompoundGraph().containsNode(node1));
		assertFalse("expected no node", this.testInstance.getRoot().getChildCompoundGraph().containsNode(node2));
		assertTrue("expected node", this.testInstance.getRoot().getChildCompoundGraph().containsNode(node3));
		assertFalse("expected no edge", this.testInstance.getRoot().getChildCompoundGraph().containsEdge(edge1));
		assertFalse("expected no edge", this.testInstance.getRoot().getChildCompoundGraph().containsEdge(edge2));
		assertTrue("expected edge removed", this.edge1.isRemoved());
		assertTrue("expected edge removed", this.edge2.isRemoved());
		assertTrue("expected node removed", this.node2.isRemoved());
		assertFalse("expected node removed", this.node1.isRemoved());
		assertFalse("expected node removed", this.node3.isRemoved());
		assertFalse("expected node removed", this.node4.isRemoved());
		assertFalse("expected node removed", this.node5.isRemoved());
		assertNotNull("new edge exists", newEdge1);
		assertNotNull("new edge exists", newEdge2);
		assertNotNull("new node exists", newNode);
		assertNotNull("removed edge exists", removedEdge);
		assertNotNull("removed node exists", removedNode);
		assertEquals("expected curr element", this.node1, this.nodeAttribute1.getCurrentElement());
		assertEquals("expected curr element", newNode, this.nodeAttribute2.getCurrentElement());
		assertEquals("expected curr element", this.node3, this.nodeAttribute3.getCurrentElement());
		assertEquals("expected curr element", this.node4, this.nodeAttribute4.getCurrentElement());
		assertEquals("expected curr element", this.node5, this.nodeAttribute5.getCurrentElement());
		assertEquals("expected curr element", newEdge1, this.edgeAttribute1.getCurrentElement());
		assertEquals("expected curr element", newEdge2, this.edgeAttribute2.getCurrentElement());
	}


}
