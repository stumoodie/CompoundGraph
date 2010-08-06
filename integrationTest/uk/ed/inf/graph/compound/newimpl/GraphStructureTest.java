package uk.ed.inf.graph.compound.newimpl;


import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundGraph;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.IRootCompoundNode;
import uk.ed.inf.graph.compound.testfixture.IGraphTestFixture;

public class GraphStructureTest {
	private static final int EXPECTED_NUM_ELEMENTS = 11;
	private static final int EXPECTED_NUM_EDGES = 4;
	private static final int EXPECTED_NUM_NODES = 7;
	private IGraphTestFixture testFixture;
	
	
	@Before
	public void setUp() throws Exception {
		this.testFixture = new IntegrationTestGraphFixture();
		this.testFixture.buildFixture();
	}

	@After
	public void tearDown() throws Exception {
		this.testFixture = null;
	}

	
	@Test
	public void testGraph(){
		ICompoundGraph testInstance = this.testFixture.getGraph();
		assertEquals("expected num elements", EXPECTED_NUM_ELEMENTS, testInstance.numElements());
		assertEquals("expected num edges", EXPECTED_NUM_EDGES, testInstance.numEdges());
		assertEquals("expected num nodes", EXPECTED_NUM_NODES, testInstance.numNodes());
	}
	
	@Test
	public void testRootNode(){
		IRootCompoundNode testInstance = this.testFixture.getGraph().getRoot();
		GraphNodeValidator validator = new GraphNodeValidator(testInstance, 0, testFixture.getRootNode(),
				testFixture.getNode(IntegrationTestGraphFixture.NODE1_ID), 
				testFixture.getNode(IntegrationTestGraphFixture.NODE6_ID), testFixture.getEdge(IntegrationTestGraphFixture.EDGE1_ID),
				testFixture.getEdge(IntegrationTestGraphFixture.EDGE4_ID));
		validator.validate();
	}

	@Test
	public void testNode1(){
		ICompoundNode testInstance = this.testFixture.getNode(IntegrationTestGraphFixture.NODE1_ID);
		GraphNodeValidator validator = new GraphNodeValidator(testInstance, 1, testFixture.getRootNode(),
				testFixture.getNode(IntegrationTestGraphFixture.NODE2_ID));
		validator.setExpectedOutEdges(this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE1_ID));
		validator.validate();
	}

	@Test
	public void testNode2(){
		ICompoundNode testInstance = this.testFixture.getNode(IntegrationTestGraphFixture.NODE2_ID);
		GraphNodeValidator validator = new GraphNodeValidator(testInstance, 2, testFixture.getNode(IntegrationTestGraphFixture.NODE1_ID),
				testFixture.getEdge(IntegrationTestGraphFixture.EDGE3_ID));
		validator.setExpectedOutEdges(this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE3_ID), this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE4_ID));
		validator.setExpectedInEdges(this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE3_ID));
		validator.validate();
	}

	@Test
	public void testNode3(){
		ICompoundNode testInstance = this.testFixture.getNode(IntegrationTestGraphFixture.NODE3_ID);
		GraphNodeValidator validator = new GraphNodeValidator(testInstance, 2, testFixture.getEdge(IntegrationTestGraphFixture.EDGE1_ID));
		validator.setExpectedOutEdges(this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE2_ID));
		validator.setExpectedInEdges(this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE4_ID));
		validator.validate();
	}

	@Test
	public void testNode4(){
		ICompoundNode testInstance = this.testFixture.getNode(IntegrationTestGraphFixture.NODE4_ID);
		GraphNodeValidator validator = new GraphNodeValidator(testInstance, 3, testFixture.getEdge(IntegrationTestGraphFixture.EDGE2_ID));
		validator.validate();
	}

	@Test
	public void testNode5(){
		ICompoundNode testInstance = this.testFixture.getNode(IntegrationTestGraphFixture.NODE5_ID);
		GraphNodeValidator validator = new GraphNodeValidator(testInstance, 2, testFixture.getEdge(IntegrationTestGraphFixture.EDGE1_ID));
		validator.setExpectedInEdges(this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE2_ID));
		validator.validate();
	}

	@Test
	public void testNode6(){
		ICompoundNode testInstance = this.testFixture.getNode(IntegrationTestGraphFixture.NODE6_ID);
		GraphNodeValidator validator = new GraphNodeValidator(testInstance, 1, this.testFixture.getRootNode());
		validator.setExpectedInEdges(this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE1_ID));
		validator.validate();
	}

	@Test
	public void testEdge1(){
		ICompoundEdge testInstance = this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE1_ID);
		GraphEdgeValidator validator = new GraphEdgeValidator(testInstance, 1, this.testFixture.getRootNode(), this.testFixture.getNode(IntegrationTestGraphFixture.NODE3_ID),
				this.testFixture.getNode(IntegrationTestGraphFixture.NODE5_ID), this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE2_ID));
		validator.setExpectedInNode(this.testFixture.getNode(IntegrationTestGraphFixture.NODE6_ID));
		validator.setExpectedOutNode(this.testFixture.getNode(IntegrationTestGraphFixture.NODE1_ID));
		validator.validate();
	}

	@Test
	public void testEdge2(){
		ICompoundEdge testInstance = this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE2_ID);
		GraphEdgeValidator validator = new GraphEdgeValidator(testInstance, 2, this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE1_ID),
				this.testFixture.getNode(IntegrationTestGraphFixture.NODE4_ID));
		validator.setExpectedInNode(this.testFixture.getNode(IntegrationTestGraphFixture.NODE5_ID));
		validator.setExpectedOutNode(this.testFixture.getNode(IntegrationTestGraphFixture.NODE3_ID));
		validator.validate();
	}

	@Test
	public void testEdge3(){
		ICompoundEdge testInstance = this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE3_ID);
		GraphEdgeValidator validator = new GraphEdgeValidator(testInstance, 3, this.testFixture.getNode(IntegrationTestGraphFixture.NODE2_ID));
		validator.setExpectedInNode(this.testFixture.getNode(IntegrationTestGraphFixture.NODE2_ID));
		validator.setExpectedOutNode(this.testFixture.getNode(IntegrationTestGraphFixture.NODE2_ID));
		validator.validate();
	}

	@Test
	public void testEdge4(){
		ICompoundEdge testInstance = this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE4_ID);
		GraphEdgeValidator validator = new GraphEdgeValidator(testInstance, 1, this.testFixture.getRootNode());
		validator.setExpectedInNode(this.testFixture.getNode(IntegrationTestGraphFixture.NODE3_ID));
		validator.setExpectedOutNode(this.testFixture.getNode(IntegrationTestGraphFixture.NODE2_ID));
		validator.validate();
	}
}
