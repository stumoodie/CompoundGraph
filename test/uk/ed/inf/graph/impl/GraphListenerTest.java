package uk.ed.inf.graph.impl;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ed.inf.graph.basic.listeners.GraphStructureChangeType;
import uk.ed.inf.graph.basic.listeners.IGraphChangeListener;
import uk.ed.inf.graph.basic.listeners.IGraphEdgeChangeEvent;
import uk.ed.inf.graph.basic.listeners.IGraphNodeChangeEvent;
import uk.ed.inf.graph.basic.listeners.INodeChangeEvent;
import uk.ed.inf.graph.basic.listeners.INodeChangeListener;

public class GraphListenerTest {
	private Graph testInstance;
	private int edgeCallCount = 0;
	private int nodeCallCount = 0;
	private Node node1;
	private Node node2;
	private Node node3;
	private Node node4;
	private Node node5;
//	private Edge edge1;
//	private Edge edge2;
	private Edge edge3;
//	private Edge edge4;
	private Edge edge5;
	private int nodeChangeCount = 0;
	
	@Before
	public void setUp() throws Exception {
		this.testInstance = new Graph();
		this.edgeCallCount = 0;
		this.nodeCallCount = 0;
		NodeFactory nodeFact = this.testInstance.nodeFactory();
		node1 = nodeFact.createNode();
		node2 = nodeFact.createNode();
		node3 = nodeFact.createNode();
		node4 = nodeFact.createNode();
		node5 = nodeFact.createNode();
		EdgeFactory edgeFact = this.testInstance.edgeFactory();
		edgeFact.setPair(node1, node2);
		edgeFact.createEdge();
		edgeFact.setPair(node3, node4);
		edgeFact.createEdge();
		edgeFact.setPair(node1, node2);
		edgeFact.createEdge();
		edgeFact.setPair(node5, node5);
		edgeFact.createEdge();
		edgeFact.setPair(node3, node5);
		edge5 = edgeFact.createEdge();
		this.testInstance.addGraphChangeListener(new IGraphChangeListener<Node, Edge>(){

			public void edgeStructureChange(IGraphEdgeChangeEvent<Node, Edge> event) {
				if(event.getChangeType().equals(GraphStructureChangeType.ADDED)){
					edgeCallCount += event.numChangedEdges();
				}
				else{
					edgeCallCount -= event.numChangedEdges();
				}
			}

			public void nodeStructureChange(IGraphNodeChangeEvent<Node, Edge> event) {
				if(event.getChangeType().equals(GraphStructureChangeType.ADDED)){
					nodeCallCount += event.numChangedNodes();
				}
				else{
					nodeCallCount -= event.numChangedNodes();
				}
			}
		});
		this.node5.addNodeChangeListener(new INodeChangeListener<Node, Edge>(){

			public void nodeStructureChange(INodeChangeEvent<Node, Edge> event) {
				if(event.getChangeType().equals(GraphStructureChangeType.ADDED)){
					nodeChangeCount++;
				}
				else{
					nodeChangeCount --;
				}
			}
			
		});
	}

	@After
	public void tearDown() throws Exception {
		this.testInstance = null;
	}

	@Test
	public void testGraphChangeOneNodeCreation() {
		NodeFactory nodeFact = this.testInstance.nodeFactory();
		nodeFact.createNode();
		assertEquals("Expected node call count", 1, nodeCallCount);
		assertEquals("Expected edge call count", 0, edgeCallCount);
	}

	@Test
	public void testGraphChangeOneEdgeCreation() {
		EdgeFactory edgeFact = this.testInstance.edgeFactory();
		edgeFact.setPair(node3, node4);
		edgeFact.createEdge();
		assertEquals("Expected node call count", 0, nodeCallCount);
		assertEquals("Expected edge call count", 1, edgeCallCount);
	}

	@Test
	public void testGraphChangeOneEdgeAndOneNodeCreation() {
		EdgeFactory edgeFact = this.testInstance.edgeFactory();
		edgeFact.setPair(node3, node4);
		edgeFact.createEdge();
		NodeFactory nodeFact = this.testInstance.nodeFactory();
		nodeFact.createNode();
		assertEquals("Expected node call count", 1, nodeCallCount);
		assertEquals("Expected edge call count", 1, edgeCallCount);
	}

	@Test
	public void testGraphChangeOneEdgeAndOneNodeDeletion() {
		SubgraphFactory fact = this.testInstance.subgraphFactory();
		fact.addEdge(this.edge3);
		fact.addNode(node5);
		Subgraph subgraph = fact.createSubgraph();
		this.testInstance.removeSubgraph(subgraph);
		assertEquals("Expected node call count", -1, nodeCallCount);
		assertEquals("Expected edge call count", -3, edgeCallCount);
		assertEquals("Expected node change count", 0, nodeChangeCount);
	}

	@Test
	public void testGraphChangeOneEdgeDeletion() {
		SubgraphFactory fact = this.testInstance.subgraphFactory();
		fact.addEdge(this.edge5);
		Subgraph subgraph = fact.createSubgraph();
		this.testInstance.removeSubgraph(subgraph);
		assertEquals("Expected node call count", 0, nodeCallCount);
		assertEquals("Expected edge call count", -1, edgeCallCount);
		assertEquals("Expected node change count", -1, nodeChangeCount);
	}
}
