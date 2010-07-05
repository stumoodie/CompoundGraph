package uk.ed.inf.graph.compound.newimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.ed.inf.designbycontract.PreConditionException;
import uk.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundGraph;
import uk.ed.inf.graph.compound.ICompoundGraphCopyBuilder;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.IRootChildCompoundGraph;
import uk.ed.inf.graph.compound.IRootCompoundNode;
import uk.ed.inf.graph.compound.ISubCompoundGraph;

@RunWith(JMock.class)
public class CompoundGraphCopyBuilderTest {

	private static final int SRC_ROOT_NODE_IDX = 0;
	private static final int SRC_ROOT_NODE_LEVEL = 0;
	private static final int SRC_NODE_1_IDX = 1;
	private static final int SRC_NODE_1_LEVEL = 1;
	private static final int SRC_NODE_2_IDX = 2;
	private static final int SRC_NODE_2_LEVEL = 2;
	private static final int SRC_NODE_3_IDX = 3;
	private static final int SRC_NODE_3_LEVEL = 2;
	private static final int SRC_NODE_4_IDX = 4;
	private static final int SRC_NODE_4_LEVEL = 3;
	private static final int SRC_EDGE_1_IDX = 5;
	private static final int SRC_EDGE_1_LEVEL = 1;
	private static final int SRC_EDGE_2_IDX = 6;
	private static final int SRC_EDGE_2_LEVEL = 2;

	private static final int DST_ROOT_NODE_IDX = 0;
	private static final int DST_ROOT_NODE_LVL = 0;

	private Mockery mockery;

	private ICompoundGraphCopyBuilder testInstance;
	private ISubCompoundGraph mockSrcSubgraph;
	private IChildCompoundGraph mockDestnChildGraph;

	private ICompoundGraph mockSrcGraph;

	private IRootCompoundNode mockSrcRootNode;

	private ICompoundNode mockSrcNode1;

	private ICompoundNode mockSrcNode2;

	private ICompoundNode mockSrcNode3;

	private ICompoundNode mockSrcNode4;

	private ICompoundEdge mockSrcEdge1;

	private ICompoundEdge mockSrcEdge2;

	private ICompoundGraph mockDestnGraph;

	private IRootCompoundNode mockDestnRootNode;

	private IRootChildCompoundGraph mockSrcRootChildGraph;
	
	@Before
	public void setUp() throws Exception {
		this.mockery = new JUnit4Mockery();

		// src graph
		this.mockSrcGraph = this.mockery.mock(ICompoundGraph.class, "mockSrcGraph");
		this.mockSrcRootNode = this.mockery.mock(IRootCompoundNode.class, "mockSrcRootNode");
		this.mockSrcRootChildGraph = this.mockery.mock(IRootChildCompoundGraph.class, "mockSrcRootChildGraph");
		this.mockSrcNode1 = this.mockery.mock(ICompoundNode.class, "mockSrcNode1"); 
		this.mockSrcNode2 = this.mockery.mock(ICompoundNode.class, "mockSrcNode2"); 
		this.mockSrcNode3 = this.mockery.mock(ICompoundNode.class, "mockSrcNode3"); 
		this.mockSrcNode4 = this.mockery.mock(ICompoundNode.class, "mockSrcNode4"); 
		this.mockSrcEdge1 = this.mockery.mock(ICompoundEdge.class, "mockSrcEdge1"); 
		this.mockSrcEdge2 = this.mockery.mock(ICompoundEdge.class, "mockSrcEdge2"); 
		this.mockSrcSubgraph = this.mockery.mock(ISubCompoundGraph.class, "mockSrcSubgraph");

		// dstn graph
		this.mockDestnGraph = this.mockery.mock(ICompoundGraph.class, "mockDestnGraph");
		this.mockDestnRootNode = this.mockery.mock(IRootCompoundNode.class, "mockDestnRootNode");
		this.mockDestnChildGraph = this.mockery.mock(IRootChildCompoundGraph.class, "mockDesnChildGraph");
		
		this.mockery.checking(new Expectations(){{
			allowing(mockSrcGraph).getRoot(); will(returnValue(mockSrcRootNode));
			
			allowing(mockSrcRootNode).getIndex(); will(returnValue(SRC_ROOT_NODE_IDX));
			allowing(mockSrcRootNode).getLevel(); will(returnValue(SRC_ROOT_NODE_LEVEL));
			allowing(mockSrcRootNode).getParent(); will(returnValue(mockSrcRootNode));
			allowing(mockSrcRootNode).getChildCompoundGraph(); will(returnValue(mockSrcRootChildGraph));

			allowing(mockSrcNode1).getIndex(); will(returnValue(SRC_NODE_1_IDX));
			allowing(mockSrcNode1).getLevel(); will(returnValue(SRC_NODE_1_LEVEL));
			allowing(mockSrcNode1).getParent(); will(returnValue(mockSrcRootNode));
			allowing(mockSrcNode1).getRoot(); will(returnValue(mockSrcRootNode));
			allowing(mockSrcNode1).childIterator(); will(returnIterator(mockSrcNode2));
			
			allowing(mockSrcNode2).getIndex(); will(returnValue(SRC_NODE_2_IDX));
			allowing(mockSrcNode2).getLevel(); will(returnValue(SRC_NODE_2_LEVEL));
			allowing(mockSrcNode2).getParent(); will(returnValue(mockSrcNode1));
			allowing(mockSrcNode2).getRoot(); will(returnValue(mockSrcRootNode));
			allowing(mockSrcNode2).childIterator(); will(returnIterator());
			
			allowing(mockSrcNode3).getIndex(); will(returnValue(SRC_NODE_3_IDX));
			allowing(mockSrcNode3).getLevel(); will(returnValue(SRC_NODE_3_LEVEL));
			allowing(mockSrcNode3).getParent(); will(returnValue(mockSrcEdge1));
			allowing(mockSrcNode3).getRoot(); will(returnValue(mockSrcRootNode));
			allowing(mockSrcNode3).childIterator(); will(returnIterator());
			
			allowing(mockSrcNode4).getIndex(); will(returnValue(SRC_NODE_4_IDX));
			allowing(mockSrcNode4).getLevel(); will(returnValue(SRC_NODE_4_LEVEL));
			allowing(mockSrcNode4).getParent(); will(returnValue(mockSrcEdge2));
			allowing(mockSrcNode4).getRoot(); will(returnValue(mockSrcRootNode));
			allowing(mockSrcNode4).childIterator(); will(returnIterator());
			
			allowing(mockSrcEdge1).getIndex(); will(returnValue(SRC_EDGE_1_IDX));
			allowing(mockSrcEdge1).getLevel(); will(returnValue(SRC_EDGE_1_LEVEL));
			allowing(mockSrcEdge1).getParent(); will(returnValue(mockSrcRootNode));
			allowing(mockSrcEdge1).getRoot(); will(returnValue(mockSrcRootNode));
			allowing(mockSrcEdge1).childIterator(); will(returnIterator(mockSrcNode3, mockSrcEdge2));
			allowing(mockSrcEdge1).getConnectedNodes(); will(returnValue(new CompoundNodePair(mockSrcNode2, mockSrcNode1)));
			
			allowing(mockSrcEdge2).getIndex(); will(returnValue(SRC_EDGE_2_IDX));
			allowing(mockSrcEdge2).getLevel(); will(returnValue(SRC_EDGE_2_LEVEL));
			allowing(mockSrcEdge2).getParent(); will(returnValue(mockSrcEdge1));
			allowing(mockSrcEdge2).getRoot(); will(returnValue(mockSrcRootNode));
			allowing(mockSrcEdge2).childIterator(); will(returnIterator(mockSrcNode4));
			allowing(mockSrcEdge2).getConnectedNodes(); will(returnValue(new CompoundNodePair(mockSrcNode3, mockSrcNode4)));
			
			allowing(mockSrcSubgraph).getSuperGraph(); will(returnValue(mockSrcGraph));
			allowing(mockSrcSubgraph).elementIterator(); will(returnIterator(mockSrcNode1, mockSrcEdge1, mockSrcNode2, mockSrcEdge2, mockSrcNode3, mockSrcNode4));
			allowing(mockSrcSubgraph).edgeLastElementIterator(); will(returnIterator(mockSrcNode1, mockSrcNode2, mockSrcNode3, mockSrcNode4, mockSrcEdge1, mockSrcEdge2));
			
			
			allowing(mockDestnGraph).getRoot(); will(returnValue(mockDestnRootNode));
			ignoring(mockDestnGraph).subgraphFactory();

			allowing(mockDestnRootNode).getGraph(); will(returnValue(mockDestnGraph));
			allowing(mockDestnRootNode).getIndex(); will(returnValue(DST_ROOT_NODE_IDX));
			allowing(mockDestnRootNode).getLevel(); will(returnValue(DST_ROOT_NODE_LVL));
			allowing(mockDestnRootNode).getParent(); will(returnValue(mockDestnRootNode));
			allowing(mockDestnRootNode).childIterator(); will(returnIterator());
			allowing(mockDestnRootNode).addInEdge(with(any(ICompoundEdge.class)));
			allowing(mockDestnRootNode).addOutEdge(with(any(ICompoundEdge.class)));
			allowing(mockDestnRootNode).getChildCompoundGraph(); will(returnValue(mockDestnChildGraph));
			
			allowing(mockDestnChildGraph).getSuperGraph(); will(returnValue(mockDestnGraph));
			allowing(mockDestnChildGraph).getRoot(); will(returnValue(mockDestnRootNode));
			ignoring(mockDestnChildGraph).nodeFactory();
			ignoring(mockDestnChildGraph).edgeFactory();
		}});
		
		this.testInstance = new CompoundGraphCopyBuilder(this.mockDestnChildGraph);
		this.testInstance.setSourceSubgraph(this.mockSrcSubgraph);
	}

	@After
	public void tearDown() throws Exception {
		this.mockery = null;
		this.testInstance = null;
	}

	@Test
	public void testGetCopiedComponents() {
		assertNull("not copied contents", this.testInstance.getCopiedComponents());
	}

	@Test
	public void testGetDestinationChildGraph() {
		assertEquals("expected childGraph", this.mockDestnChildGraph, this.testInstance.getDestinationChildGraph());
	}

	@Test
	public void testGetSourceSubgraph() {
		assertEquals("expected subgraph", this.mockSrcSubgraph, this.testInstance.getSourceSubgraph());
	}

	@Test
	public void testMakeCopy() {
		this.testInstance.makeCopy();
		ISubCompoundGraph copiedComponents = this.testInstance.getCopiedComponents();
		assertNotNull("copied elements present", copiedComponents);
	}

	@Test(expected=PreConditionException.class)
	public void testSetSourceSubgraph() {
		this.testInstance.setSourceSubgraph(null);
	}

}
