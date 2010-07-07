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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ed.inf.graph.compound.CompoundNodePair;
import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.ICompoundNodeFactory;
import uk.ed.inf.graph.compound.IRootCompoundNode;
import uk.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ed.inf.graph.compound.ISubCompoundGraphFactory;
import uk.ed.inf.graph.state.IGraphState;

public class AdditionalTest {
	
	private CompoundGraph testInstance ;
	private CompoundGraph emptyTestInstance ;
	private IRootCompoundNode rootNode;
	private ICompoundNode node1;
//	private CompoundNode node2;
	private ICompoundEdge edge1;
	
	private static final String EMPTY_ELEMENT_BITSTRING = "{0}" ;
	
	private static final String NOT_EMPTY_ELEMENT_BITSTRING = "{0, 1, 2}" ;
	
	private static final String REMOVED_ELEMENT_BITSTRING = "{0, 2}" ;
	
	private static final int ONLY_ROOT_NODE = 1 ;
	private static final int NO_EDGES = 0 ;
	private static final int NUMERIC_VALUES[] = {0,1,2,3,4,5,6,7,8,9} ;
	
	
	@Before
	public void setUp() throws Exception {
		emptyTestInstance = new CompoundGraph () ;
		testInstance = new CompoundGraph () ;
		this.rootNode = this.testInstance.getRoot();
		ICompoundNodeFactory rootNodeFact = this.testInstance.getRoot().getChildCompoundGraph().nodeFactory();
		node1 = rootNodeFact.createNode();
		rootNodeFact.createNode();
		
		
		ICompoundEdgeFactory edgeFact = this.testInstance.edgeFactory();
		edgeFact.setPair(new CompoundNodePair(node1, node1));
		edge1 = edgeFact.createEdge();
	}
	
	@After
	public void tearDown () throws Exception {
		
	}
	
	@Test
	public final void testCannotCopyASingleEdge () throws Exception 
	{
		ISubCompoundGraphFactory subGraphfactory = testInstance.subgraphFactory() ; 
		subGraphfactory.addElement(edge1) ;
		ISubCompoundGraph  ordinarySubGraph = subGraphfactory.createSubgraph() ;
		assertFalse ( "cannot copy" , rootNode.getChildCompoundGraph().newCopyBuilder().canCopyHere(ordinarySubGraph) );
	}
	
	@Test(expected=IllegalArgumentException.class)
	public final void testTryToCopyASingleEdge () throws Exception 
	{
		ISubCompoundGraphFactory subGraphfactory = testInstance.subgraphFactory() ; 
		subGraphfactory.addElement(edge1) ;
		ISubCompoundGraph  ordinarySubGraph = subGraphfactory.createSubgraph() ;
		rootNode.getChildCompoundGraph().newCopyBuilder().copyHere(ordinarySubGraph) ;
	}
	
	@Test
	public final void testCannotMoveRootNode () throws Exception 
	{
		ISubCompoundGraphFactory subGraphfactory = testInstance.subgraphFactory() ; 
		subGraphfactory.addElement(rootNode) ;
		ISubCompoundGraph  ordinarySubGraph = subGraphfactory.createSubgraph() ;
		assertFalse("cannot move rootNode" , node1.getChildCompoundGraph().newMoveBuilder().canMoveHere(ordinarySubGraph) );
	}
	
	@Test(expected=IllegalArgumentException.class)
	public final void testTryToMoveRootNode () throws Exception 
	{
		ISubCompoundGraphFactory subGraphfactory = testInstance.subgraphFactory() ; 
		subGraphfactory.addElement(rootNode) ;
		ISubCompoundGraph  ordinarySubGraph = subGraphfactory.createSubgraph() ;
		rootNode.getChildCompoundGraph().newMoveBuilder().moveHere(ordinarySubGraph) ;
		assertFalse("cannot move rootNode" , node1.getChildCompoundGraph().newMoveBuilder().canMoveHere(ordinarySubGraph) );
	}	
	
	@Test
	public final void testCannotCopyRootNode () throws Exception 
	{
		ISubCompoundGraphFactory subGraphfactory = testInstance.subgraphFactory() ; 
		subGraphfactory.addElement(rootNode) ;
		ISubCompoundGraph  ordinarySubGraph = subGraphfactory.createSubgraph() ;
		assertFalse("cannot move rootNode" , node1.getChildCompoundGraph().newCopyBuilder().canCopyHere(ordinarySubGraph) );
	}
	
	@Test(expected=IllegalArgumentException.class)
	public final void testTryToCopyRootNode () throws Exception 
	{
		ISubCompoundGraphFactory subGraphfactory = testInstance.subgraphFactory() ; 
		subGraphfactory.addElement(rootNode) ;
		ISubCompoundGraph  ordinarySubGraph = subGraphfactory.createSubgraph() ;
		rootNode.getChildCompoundGraph().newCopyBuilder().copyHere(ordinarySubGraph) ;
	}
	
	@Test(expected=IllegalStateException.class)
	public final void testTryToDeleteRootNode () throws Exception 
	{
		ISubCompoundGraphFactory subGraphfactory = testInstance.subgraphFactory() ; 
		subGraphfactory.addElement(rootNode) ;
		ISubCompoundGraph  ordinarySubGraph = subGraphfactory.createSubgraph() ;
		testInstance.newSubgraphRemovalBuilder().removeSubgraph(ordinarySubGraph) ;
	}
	
	@Test
	public final void testSaveStateOfEmptyDiagram () throws Exception
	{
		IGraphState emptyGraphState = emptyTestInstance.getCurrentState() ;
		
		assertEquals ( "expected state bitstring" , EMPTY_ELEMENT_BITSTRING , emptyGraphState.getElementStates().toString()) ;
	}
	
	@Test
	public final void testSaveStateOfPopulatedDiagram () throws Exception
	{
		IGraphState nonEmptyGraphState = testInstance.getCurrentState() ;
		
		assertEquals ( "expected state bitstring" , NOT_EMPTY_ELEMENT_BITSTRING , nonEmptyGraphState.getElementStates().toString()) ;
	}	
	
	@Test(expected=IllegalArgumentException.class)
	public final void testRestoreStateFromAnotherGraph () throws Exception
	{
		IGraphState nonEmptyGraphState = testInstance.getCurrentState() ;
		emptyTestInstance.restoreState(nonEmptyGraphState) ;
		assertEquals ( "only root" , ONLY_ROOT_NODE , emptyTestInstance.getNumNodes()) ;
		assertEquals ( "no edges" , NO_EDGES , emptyTestInstance.getNumEdges()) ;
	}
	
	@Test
	public final void testRestoreStateFromDeletedElementsGraph () throws Exception 
	{
		assertEquals ( "three Nodes" , NUMERIC_VALUES[3] , testInstance.getNumNodes() ) ;
		assertEquals ( "one Edge" , NUMERIC_VALUES[1] , testInstance.getNumEdges() ) ;
		IGraphState nonEmptyGraphState = testInstance.getCurrentState() ;
		assertNotNull ( "state exists" , nonEmptyGraphState) ;
		assertEquals ( "expected states" , NOT_EMPTY_ELEMENT_BITSTRING , nonEmptyGraphState.getElementStates().toString() ) ;
		ISubCompoundGraphFactory subGraphFactory = testInstance.subgraphFactory() ;
		subGraphFactory.addElement(node1) ;
		ISubCompoundGraph subGraph = subGraphFactory.createSubgraph() ;
		testInstance.newSubgraphRemovalBuilder().removeSubgraph(subGraph) ;
		IGraphState removedNodeGraphState = testInstance.getCurrentState() ;
		assertEquals ( "expected states" , REMOVED_ELEMENT_BITSTRING , removedNodeGraphState.getElementStates().toString()) ;
		assertEquals ( "only two nodes" , NUMERIC_VALUES[2] , testInstance.getNumNodes() ) ;
		assertEquals ( "no edges" , NUMERIC_VALUES[0] , testInstance.getNumEdges() ) ;
		testInstance.restoreState(nonEmptyGraphState) ;
		assertEquals ( "three Nodes" , NUMERIC_VALUES[3] , testInstance.getNumNodes() ) ;
		assertEquals ( "one Edge" , NUMERIC_VALUES[1] , testInstance.getNumEdges() ) ;
		testInstance.restoreState(removedNodeGraphState) ;
		assertEquals ( "only two nodes" , NUMERIC_VALUES[2] , testInstance.getNumNodes() ) ;
		assertEquals ( "no edges" , NUMERIC_VALUES[0] , testInstance.getNumEdges() ) ;
	}
	
	@Test
	public final void testRestoreStateFromDeletedSingleEdge () throws Exception
	{
		assertEquals ( "three Nodes" , NUMERIC_VALUES[3] , testInstance.getNumNodes() ) ;
		assertEquals ( "one Edge" , NUMERIC_VALUES[1] , testInstance.getNumEdges() ) ;
		IGraphState nonEmptyGraphState = testInstance.getCurrentState() ;
		assertNotNull ( "state exists" , nonEmptyGraphState) ;
		assertEquals ( "has Nodes" , NOT_EMPTY_ELEMENT_BITSTRING , nonEmptyGraphState.getElementStates().toString() ) ;
		ISubCompoundGraphFactory subGraphFactory = testInstance.subgraphFactory() ;
		subGraphFactory.addElement(edge1) ;
		ISubCompoundGraph subGraph = subGraphFactory.createSubgraph() ;
		testInstance.newSubgraphRemovalBuilder().removeSubgraph(subGraph) ;
		assertEquals ( "three nodes" , NUMERIC_VALUES[3] , testInstance.getNumNodes() ) ;
		assertEquals ( "no edges" , NUMERIC_VALUES[0] , testInstance.getNumEdges() ) ;
		testInstance.restoreState(nonEmptyGraphState) ;
		assertEquals ( "three Nodes" , NUMERIC_VALUES[3] , testInstance.getNumNodes() ) ;
		assertEquals ( "one Edge" , NUMERIC_VALUES[1] , testInstance.getNumEdges() ) ;
	}
	
	@Test
	public final void testCanMoveBetweenDifferentGraphs () throws Exception
	{
		ISubCompoundGraphFactory subGraphFactory = testInstance.subgraphFactory() ;
		subGraphFactory.addElement(node1) ;
		ISubCompoundGraph subGraph = subGraphFactory.createSubgraph() ;
		assertFalse ("cannot move to other diagram" , this.emptyTestInstance.getRoot().getChildCompoundGraph().newMoveBuilder().canMoveHere(subGraph)) ;
	}
	
	@Test(expected=IllegalArgumentException.class)
	public final void testTryToMoveBetweenDifferentGraphs () throws Exception
	{
		ISubCompoundGraphFactory subGraphFactory = testInstance.subgraphFactory() ;
		subGraphFactory.addElement(node1) ;
		ISubCompoundGraph subGraph = subGraphFactory.createSubgraph() ;
		this.emptyTestInstance.getRoot().getChildCompoundGraph().newMoveBuilder().moveHere(subGraph) ;
	}
	
	@Test
	public final void testCanCopyBetweenDifferentGraphs () throws Exception
	{
		ISubCompoundGraphFactory subGraphFactory = testInstance.subgraphFactory() ;
		subGraphFactory.addElement(node1) ;
		ISubCompoundGraph subGraph = subGraphFactory.createSubgraph() ;
		assertTrue ("cannot move to other diagram" , this.emptyTestInstance.getRoot().getChildCompoundGraph().newCopyBuilder().canCopyHere(subGraph)) ;
	}
	
	@Test
	public final void testTryToCopyBetweenDifferentGraphs () throws Exception
	{
		ISubCompoundGraphFactory subGraphFactory = testInstance.subgraphFactory() ;
		subGraphFactory.addElement(node1) ;
		ISubCompoundGraph subGraph = subGraphFactory.createSubgraph() ;
		this.emptyTestInstance.getRoot().getChildCompoundGraph().newCopyBuilder().canCopyHere(subGraph) ;
		assertEquals ( "emptyGraph has one node" , NUMERIC_VALUES[1] , this.emptyTestInstance.getNumNodes() ) ;
	}
	
}
