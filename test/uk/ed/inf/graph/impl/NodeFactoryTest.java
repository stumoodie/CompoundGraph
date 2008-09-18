package uk.ed.inf.graph.impl;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NodeFactoryTest {
	
	private static final int INDEX_ZERO = 0 ;
	
	private NodeFactory testNodeFactory ;
	private Graph mockGraph ;

	@Before
	public void setUp() throws Exception {
		
		mockGraph = new Graph () ;
		
		testNodeFactory = new NodeFactory (mockGraph) ;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testCreateNode() {
		Node createdNode = testNodeFactory.createNode() ;
		assertEquals ( "get graph" , mockGraph , createdNode.getGraph() ) ;
		assertEquals ( "get index" , INDEX_ZERO , createdNode.getIndex()) ;
	}

}
