package uk.ed.inf.graph.compound.impl;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ed.inf.graph.compound.base.BaseChildCompoundGraph;
import uk.ed.inf.graph.impl.Edge;
import uk.ed.inf.graph.impl.Graph;
import uk.ed.inf.graph.impl.Node;

public class CompoundEdgeFactoryTest {
	
	private CompoundEdgeFactory testEdgeFactory ;
	
	private static CompoundGraph mockCompoundGraph ;
	private static ChildCompoundGraph mockChildCompoundGraph ;
	private static CompoundNode mockCompoundNode ;
	private static CompoundNode mockOneNode ;
	private static CompoundNode mockTwoNode ;
	private static CompoundNode otherOneNode ;
	private static CompoundNode otherTwoNode ;
	
	private static final int COMPOUND_NODE_INDEX_ONE = 1 ;
	private static final int COMPOUND_NODE_INDEX_TWO = 2 ;
	private static final int COMPOUND_NODE_INDEX_THREE = 3 ;
	private static final int COMPOUND_NODE_INDEX_FOUR = 2 ;
	private static final int COMPOUND_NODE_INDEX_FIVE = 3 ;

	@Before
	public void setUp() throws Exception {
		Graph mockGraph = new Graph () ;
		

		
		mockCompoundGraph = new CompoundGraph () ;
		mockOneNode = new CompoundNode ( mockCompoundGraph , COMPOUND_NODE_INDEX_TWO ) ;
		mockTwoNode = new CompoundNode ( mockCompoundGraph , COMPOUND_NODE_INDEX_THREE ) ;
		mockCompoundNode = new CompoundNode ( mockCompoundGraph , COMPOUND_NODE_INDEX_ONE) ;
		mockChildCompoundGraph = new ChildCompoundGraph (mockCompoundNode ) ;
		
		otherOneNode = new CompoundNode ( mockCompoundGraph , COMPOUND_NODE_INDEX_FOUR ) ;
		otherTwoNode = new CompoundNode ( mockCompoundGraph , COMPOUND_NODE_INDEX_FIVE ) ;
		
		testEdgeFactory = new CompoundEdgeFactory (mockCompoundGraph ) ;
		
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testCiEdgeFactory() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSetPair() {
		testEdgeFactory.setPair(otherOneNode, otherTwoNode) ;
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testCreateEdge() {
		
		CompoundEdge generatedEdge = testEdgeFactory.createEdge() ;//newEdge(mockChildCompoundGraph, COMPOUND_NODE_INDEX_ONE, mockOneNode, mockTwoNode) ;
		assertEquals ( "get graph" , mockCompoundGraph , generatedEdge.getGraph() ) ;
		assertEquals ( "get index" , COMPOUND_NODE_INDEX_ONE , generatedEdge.getIndex() ) ;
	}

	@Test
	public final void testGetGraph() {
		assertEquals ( "get graph" , mockCompoundGraph , testEdgeFactory.getGraph()) ;
	}

}
