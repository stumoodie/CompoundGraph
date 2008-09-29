package uk.ed.inf.graph.compound.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.corba.se.impl.naming.cosnaming.NamingUtils;

import uk.ed.inf.bitstring.IBitString;
import uk.ed.inf.graph.compound.base.BaseCompoundEdge;
import uk.ed.inf.graph.compound.base.BaseCompoundNode;
import uk.ed.inf.graph.state.IGraphState;

public class AdditionalTests {
	
	private CompoundGraph testInstance ;
	private CompoundGraph emptyTestInstance ;
	private CompoundNode rootNode;
	private CompoundNode node1;
	private CompoundEdge edge1;
	
	private static final String EMPTY_NODE_BITSTRING = "{0}" ;
	private static final String EMPTY_EDGE_BITSTRING = "{}" ;
	
	private static final String NOT_EMPTY_NODE_BITSTRING = "{0, 1}" ;
	private static final String NOT_EMPTY_EDGE_BITSTRING = "{0}" ;
	
	private static final int ONLY_ROOT_NODE = 1 ;
	private static final int NO_EDGES = 0 ;
	private static final int NUMERIC_VALUES[] = {0,1,2,3,4,5,6,7,8,9} ;
	
	
	@Before
	public void setUp() throws Exception {
		emptyTestInstance = new CompoundGraph () ;
		testInstance = new CompoundGraph () ;
		this.rootNode = this.testInstance.getRootNode();
		CompoundNodeFactory rootNodeFact = this.testInstance.getRootNode().getChildCompoundGraph().nodeFactory();
		node1 = rootNodeFact.createNode();
		
		CompoundEdgeFactory edgeFact = this.testInstance.edgeFactory();
		edgeFact.setPair(node1, node1);
		edge1 = edgeFact.createEdge();
	}
	
	@After
	public void tearDown () throws Exception {
		
	}
	
	@Test
	public final void testCannotCopyASingleEdge () throws Exception 
	{
		SubCompoundGraphFactory subGraphfactory = testInstance.subgraphFactory() ; 
		subGraphfactory.addEdge(edge1) ;
		SubCompoundGraph  ordinarySubGraph = subGraphfactory.createSubgraph() ;
		assertFalse ( "cannot copy" , rootNode.getChildCompoundGraph().canCopyHere(ordinarySubGraph) );
	}
	
	@Test(expected=IllegalArgumentException.class)
	public final void testTryToCopyASingleEdge () throws Exception 
	{
		SubCompoundGraphFactory subGraphfactory = testInstance.subgraphFactory() ; 
		subGraphfactory.addEdge(edge1) ;
		SubCompoundGraph  ordinarySubGraph = subGraphfactory.createSubgraph() ;
		rootNode.getChildCompoundGraph().copyHere(ordinarySubGraph) ;
	}
	
	@Test
	public final void testCannotMoveRootNode () throws Exception 
	{
		SubCompoundGraphFactory subGraphfactory = testInstance.subgraphFactory() ; 
		subGraphfactory.addNode(rootNode) ;
		SubCompoundGraph  ordinarySubGraph = subGraphfactory.createSubgraph() ;
		assertFalse("cannot move rootNode" , node1.getChildCompoundGraph().canMoveHere(ordinarySubGraph) );
	}
	
	@Test(expected=IllegalArgumentException.class)
	public final void testTryToMoveRootNode () throws Exception 
	{
		SubCompoundGraphFactory subGraphfactory = testInstance.subgraphFactory() ; 
		subGraphfactory.addNode(rootNode) ;
		SubCompoundGraph  ordinarySubGraph = subGraphfactory.createSubgraph() ;
		rootNode.getChildCompoundGraph().moveHere(ordinarySubGraph) ;
		assertFalse("cannot move rootNode" , node1.getChildCompoundGraph().canMoveHere(ordinarySubGraph) );
	}	
	
	@Test
	public final void testCannotCopyRootNode () throws Exception 
	{
		SubCompoundGraphFactory subGraphfactory = testInstance.subgraphFactory() ; 
		subGraphfactory.addNode(rootNode) ;
		SubCompoundGraph  ordinarySubGraph = subGraphfactory.createSubgraph() ;
		assertFalse("cannot move rootNode" , node1.getChildCompoundGraph().canCopyHere(ordinarySubGraph) );
	}
	
	@Test(expected=IllegalArgumentException.class)
	public final void testTryToCopyRootNode () throws Exception 
	{
		SubCompoundGraphFactory subGraphfactory = testInstance.subgraphFactory() ; 
		subGraphfactory.addNode(rootNode) ;
		SubCompoundGraph  ordinarySubGraph = subGraphfactory.createSubgraph() ;
		rootNode.getChildCompoundGraph().copyHere(ordinarySubGraph) ;
	}
	
	@Test(expected=IllegalStateException.class)
	public final void testTryToDeleteRootNode () throws Exception 
	{
		SubCompoundGraphFactory subGraphfactory = testInstance.subgraphFactory() ; 
		subGraphfactory.addNode(rootNode) ;
		SubCompoundGraph  ordinarySubGraph = subGraphfactory.createSubgraph() ;
		testInstance.removeSubgraph(ordinarySubGraph) ;
	}
	
	@Test
	public final void testSaveStateOfEmptyDiagram () throws Exception
	{
		IGraphState<BaseCompoundNode, BaseCompoundEdge> emptyGraphState = emptyTestInstance.getCurrentState() ;
		
		assertEquals ( "only one node" , EMPTY_NODE_BITSTRING , emptyGraphState.getNodeStates().toString()) ;
		assertEquals ( "no egdes" , EMPTY_EDGE_BITSTRING , emptyGraphState.getEdgeStates().toString() );
	}
	
	@Test
	public final void testSaveStateOfPopulatedDiagram () throws Exception
	{
		IGraphState<BaseCompoundNode, BaseCompoundEdge> nonEmptyGraphState = testInstance.getCurrentState() ;
		
		assertEquals ( "two nodes" , NOT_EMPTY_NODE_BITSTRING , nonEmptyGraphState.getNodeStates().toString()) ;
		assertEquals ( "one node" , NOT_EMPTY_EDGE_BITSTRING , nonEmptyGraphState.getEdgeStates().toString() );
	}	
	
	@Test(expected=IllegalArgumentException.class)
	public final void testRestoreStateFromAnotherGraph () throws Exception
	{
		IGraphState<BaseCompoundNode, BaseCompoundEdge> nonEmptyGraphState = testInstance.getCurrentState() ;
		emptyTestInstance.restoreState(nonEmptyGraphState) ;
		assertEquals ( "only root" , ONLY_ROOT_NODE , emptyTestInstance.getNumNodes()) ;
		assertEquals ( "no edges" , NO_EDGES , emptyTestInstance.getNumEdges()) ;
	}
	
	@Test
	public final void testRestoreStateFromDeletedElementsGraph () throws Exception 
	{
		IGraphState<BaseCompoundNode, BaseCompoundEdge> nonEmptyGraphState = testInstance.getCurrentState() ;
		assertNotNull ( "state exists" , nonEmptyGraphState) ;
		assertEquals ( "has Nodes" , NOT_EMPTY_NODE_BITSTRING , nonEmptyGraphState.getNodeStates().toString() ) ;
		assertEquals ( "has Edge" , NOT_EMPTY_EDGE_BITSTRING , nonEmptyGraphState.getEdgeStates().toString() ) ;
		SubCompoundGraphFactory subGraphFactory = testInstance.subgraphFactory() ;
		subGraphFactory.addNode(node1) ;
		SubCompoundGraph subGraph = subGraphFactory.createSubgraph() ;
		testInstance.removeSubgraph(subGraph) ;
		assertEquals ( "only one node" , NUMERIC_VALUES[1] , testInstance.getNumNodes() ) ;
		assertEquals ( "no edges" , NUMERIC_VALUES[0] , testInstance.getNumEdges() ) ;
		testInstance.restoreState(nonEmptyGraphState) ;
		assertEquals ( "two Nodes" , NUMERIC_VALUES[2] , testInstance.getNumNodes() ) ;
		assertEquals ( "one Edge" , NUMERIC_VALUES[1] , testInstance.getNumEdges() ) ;
	}
	
	@Test
	public final void testRestoreStateFromDeletedSingleEdge () throws Exception
	{
		IGraphState<BaseCompoundNode, BaseCompoundEdge> nonEmptyGraphState = testInstance.getCurrentState() ;
		assertNotNull ( "state exists" , nonEmptyGraphState) ;
		assertEquals ( "has Nodes" , NOT_EMPTY_NODE_BITSTRING , nonEmptyGraphState.getNodeStates().toString() ) ;
		assertEquals ( "has Edge" , NOT_EMPTY_EDGE_BITSTRING , nonEmptyGraphState.getEdgeStates().toString() ) ;
		SubCompoundGraphFactory subGraphFactory = testInstance.subgraphFactory() ;
		subGraphFactory.addEdge(edge1) ;
		SubCompoundGraph subGraph = subGraphFactory.createSubgraph() ;
		testInstance.removeSubgraph(subGraph) ;
		assertEquals ( "two nodes" , NUMERIC_VALUES[2] , testInstance.getNumNodes() ) ;
		assertEquals ( "no edges" , NUMERIC_VALUES[0] , testInstance.getNumEdges() ) ;
		testInstance.restoreState(nonEmptyGraphState) ;
		assertEquals ( "two Nodes" , NUMERIC_VALUES[2] , testInstance.getNumNodes() ) ;
		assertEquals ( "one Edge" , NUMERIC_VALUES[1] , testInstance.getNumEdges() ) ;
		
	}

}
