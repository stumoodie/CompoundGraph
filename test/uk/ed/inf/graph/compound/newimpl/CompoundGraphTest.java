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

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ed.inf.graph.compound.ICompoundGraph;
import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.ICompoundNodeFactory;
import uk.ed.inf.graph.compound.IRootChildCompoundGraph;
import uk.ed.inf.graph.compound.IRootCompoundNode;
import uk.ed.inf.graph.compound.ISubCompoundGraphFactory;
import uk.ed.inf.graph.compound.testfixture.ComplexGraphFixture;
import uk.ed.inf.graph.compound.testfixture.IGraphConstructor;

@RunWith(JMock.class)
public class CompoundGraphTest {
	private Mockery mockery = new JUnit4Mockery();
	
	private ICompoundGraph testCompoundGraph ;
//	private ICompoundGraph anotherCompoundGraph ;
	
//	private ICompoundNode aNode ;
//	private ICompoundNode anotherNode ;
//	private ICompoundNode rootNode ;
	
//	private IGraphState originalState ; 
//	private IGraphState currentState ;
	
//	private ICompoundEdge anEdge ;
	
//	private ICompoundEdge subEdge ;
//	private ICompoundNode subNode ;
	
//	private ICompoundEdgeFactory edgeFactory ;
//	private ICompoundNodeFactory nodeFactory ;
//	private ISubCompoundGraphFactory subGraphFactory ;
	
//	private ICompoundEdgeFactory anotherEdgeFactory ;
//	private ICompoundNodeFactory anotherNodeFactory ;
//	private ISubCompoundGraphFactory anotherSubGraphFactory ;
//
//	private ICompoundGraphCopyBuilder mockCopyBuilder;
//
//	private ISubCompoundGraph mockSrcSubgraph;
//
//	private ISubCompoundGraph mockCopiedSubgraph;

	private ComplexGraphFixture testFixture;

	private ComplexGraphFixture otherTestFixture;
	
	private static final String EXPECTED_STATE_STRING = "{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10}";

	private static final int UNFOUND_EDGE_IDX = 99;

	private static final int UNFOUND_NODE_IDX = 77;

	private static final int EXPECTED_NUM_EDGES = 4;

	private static final int EXPECTED_NUM_NODES = 7;

	private static final int EXPECTED_NUM_ELEMENTS = 11;
	
	
	@Before
	public void setUp() throws Exception {
		this.testFixture = new ComplexGraphFixture(mockery, "");
		this.testFixture.redefineGraph(new IGraphConstructor() {
			
			@Override
			public IRootCompoundNode createRootNode(ICompoundGraph graph) {
				return graph.getRoot();
			}
			
			@Override
			public ICompoundNodeFactory createNodeFactory(ICompoundGraph graph) {
				return graph.nodeFactory();
			}
			
			@Override
			public ICompoundGraph createGraph() {
				return testCompoundGraph = new CompoundGraph();
			}
			
			@Override
			public ICompoundEdgeFactory createEdgeFactory(ICompoundGraph graph) {
				return graph.edgeFactory();
			}
			
			@Override
			public IRootChildCompoundGraph createChildGraph(IRootCompoundNode node) {
				return node.getChildCompoundGraph();
			}
			
			@Override
			public boolean buildRootNode(IRootCompoundNode node) {
				return true;
			}
			
			@Override
			public boolean buildNodeFactory(ICompoundNodeFactory nodeFactory) {
				return true;
			}
			
			@Override
			public boolean buildGraph(ICompoundGraph graph) {
				return true;
			}
			
			@Override
			public boolean buildEdgeFactory(ICompoundEdgeFactory edgeFactory) {
				return true;
			}
			
			@Override
			public boolean buildChildGraph(IRootChildCompoundGraph childGraph) {
				childGraph.addNode(testFixture.getNode1());
				childGraph.addNode(testFixture.getNode6());
				childGraph.addEdge(testFixture.getEdge1());
				childGraph.addEdge(testFixture.getEdge4());
				return true;
			}

			@Override
			public boolean buildSubgraphFactory(ISubCompoundGraphFactory subgraphFactory) {
				return true;
			}

			@Override
			public ISubCompoundGraphFactory createSubgraphFactory(ICompoundGraph graph) {
				return graph.subgraphFactory();
			}
		});
		this.testFixture.doAll();
//		{
//			@Override
//			protected void buildGraph(ICompoundGraph graph){
//				graph.getRoot().getChildCompoundGraph().addNode(getNode1());
//				graph.getRoot().getChildCompoundGraph().addEdge(getEdge1());
//				graph.getRoot().getChildCompoundGraph().addEdge(getEdge4());
//			}
//			
//			@Override
//			protected void buildElementTree(ITree<ICompoundGraphElement> tree){
//				
//			}
//		};
//		this.testFixture.createElements();
//		testCompoundGraph = new CompoundGraph () ;
//		this.testFixture.setGraph(testCompoundGraph);
//		this.testFixture.setRootNode(this.testCompoundGraph.getRoot());
//		this.testFixture.setRootChildGraph(this.testCompoundGraph.getRoot().getChildCompoundGraph());
//		this.testFixture.setElementTree(this.testCompoundGraph.getElementTree());
//		this.testFixture.buildObjects();
		
		this.otherTestFixture = new ComplexGraphFixture(mockery, "other_");
		this.otherTestFixture.doAll();
//		this.otherTestFixture.createElements();
//		this.otherTestFixture.buildObjects();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testGetRoot() {
		assertTrue ( "not null" , testCompoundGraph.getRoot() != null ) ;
		ICompoundNode rootNode = testCompoundGraph.getRoot() ;
		assertEquals ( "same graph" , testCompoundGraph , rootNode.getGraph() );
	}

	@Test
	public final void testContainsDirectedEdgeCompoundNodeCompoundNode() {
		assertTrue ("directed edge" ,  testCompoundGraph.containsDirectedEdge(this.testFixture.getNode3(), this.testFixture.getNode5()) );
		assertFalse ("no directed edge" ,  testCompoundGraph.containsDirectedEdge(this.testFixture.getNode5(), this.testFixture.getNode3()) );
		assertFalse ("no directed edge" ,  testCompoundGraph.containsDirectedEdge(this.testFixture.getNode5(), this.testFixture.getNode5()) );
		assertFalse ("no directed edge" ,  testCompoundGraph.containsDirectedEdge(null, null));
	}  

	@Test
	public final void testContainsConnectionCompoundNodeCompoundNode() {
		assertTrue ("has edge" ,  testCompoundGraph.containsConnection(this.testFixture.getNode3(), this.testFixture.getNode5()) );
		assertFalse ("has no edge" ,  testCompoundGraph.containsConnection(this.testFixture.getNode5(), this.testFixture.getNode5()) );
		assertFalse ("has no edge" ,  testCompoundGraph.containsConnection(null, null));
	}

	@Test
	public final void testContainsEdgeCompoundEdge() {
		assertTrue ( "contains Edge" , testCompoundGraph.containsEdge(testFixture.getEdge1())) ;
		assertFalse ( "contains Edge" , testCompoundGraph.containsEdge(null)) ;
	}

	@Test
	public final void testContainsEdgeInt() {
		assertTrue ("contains edge there", testCompoundGraph.containsEdge(this.testFixture.getEdge3().getIndex())) ;
		assertFalse("Not contains edge", testCompoundGraph.containsEdge(UNFOUND_EDGE_IDX));
	}

	@Test
	public final void testContainsNodeInt() {
		assertTrue ( "contains node there" , testCompoundGraph.containsNode(this.testFixture.getNode3().getIndex())) ;
		assertFalse ( "contains node there" , testCompoundGraph.containsNode(UNFOUND_NODE_IDX)) ;
	}

	@Test
	public final void testContainsNodeCompoundNode() {
		assertTrue ( "contains this node" , testCompoundGraph.containsNode(this.testFixture.getNode3()) ) ;
		assertFalse ( "not contains this node" , testCompoundGraph.containsNode(null) ) ;
	}

	@Test
	public final void testEdgeFactory() {
		ICompoundEdgeFactory actualFact = this.testFixture.getGraph().edgeFactory();
		assertTrue ( "has edge factory" , actualFact != null ) ;
		assertTrue ( "not same instance" , testCompoundGraph.edgeFactory() != actualFact) ;
	}

	@Test
	public final void testNodeFactory() {
		ICompoundNodeFactory actualFact = this.testFixture.getGraph().nodeFactory();
		assertTrue ( "has node factory" , actualFact != null ) ;
		assertTrue ( "not same instance" , testCompoundGraph.nodeFactory() != actualFact) ;
	}

	@Test
	public final void testSubgraphFactory() {
		ISubCompoundGraphFactory subGraphFactory = this.testFixture.getGraph().subgraphFactory();
		assertNotNull ( "has Subgraph factory" , subGraphFactory) ;
		assertTrue ( "not same instance" , testCompoundGraph.subgraphFactory() != subGraphFactory ) ;
	}

	@Test
	public final void testGetEdge() {
		ICompoundEdge expectedEdge = this.testFixture.getEdge4();
		assertEquals ( "get Edge" , expectedEdge, testCompoundGraph.getEdge(expectedEdge.getIndex())) ;
	}

	@Test(expected=IllegalArgumentException.class)
	public final void testGetEdgeFails() {
		ICompoundEdge expectedEdge = this.testFixture.getEdge4();
		assertEquals ( "get Edge" , expectedEdge , testCompoundGraph.getEdge(UNFOUND_EDGE_IDX)) ;
	}

	@Test
	public final void testEdgeIterator() {
//		ICompoundEdge edgeArray [] = {  } ;
//		
//		Iterator<ICompoundEdge> edgeIterator = testCompoundGraph.edgeIterator() ;
//		
//		int counter = 0 ;
//		
//		while ( edgeIterator.hasNext())
//		{
//			assertEquals ( "same node" , edgeArray[counter] , edgeIterator.next()) ;
//			counter++ ;
//		}
//		
//		assertEquals ( "correct number" , NUMERIC[1] , counter ) ;
		IteratorTestUtility<ICompoundEdge> testIterator = new IteratorTestUtility<ICompoundEdge>(this.testFixture.getEdge4(), this.testFixture.getEdge1(),
				this.testFixture.getEdge2(), this.testFixture.getEdge3());
		testIterator.testIterator(this.testCompoundGraph.edgeIterator());
	}

	@Test
	public final void testGetNode() {
		assertEquals ( "get node" , this.testFixture.getNode1() , testCompoundGraph.getNode(ComplexGraphFixture.NODE1_IDX) ) ;
	}

	@Test
	public final void testNodeIterator() {
//		ICompoundNode [] nodeArray = { rootNode , aNode , anotherNode } ;
//		
//		Iterator<ICompoundNode> nodeIterator = testCompoundGraph.nodeIterator() ;
//		
//		int counter = 0 ;
//		
//		while ( nodeIterator.hasNext())
//		{
//			assertEquals ( "same node" , nodeArray[counter] , nodeIterator.next()) ;
//			counter++ ;
//		}
//		
//		assertEquals ( "correct number" , NUMERIC[3] , counter ) ;
		IteratorTestUtility<ICompoundNode> testIterator = new IteratorTestUtility<ICompoundNode>(this.testFixture.getRootNode(), this.testFixture.getNode1(),
				this.testFixture.getNode2(), this.testFixture.getNode3(), this.testFixture.getNode4(), this.testFixture.getNode5(), this.testFixture.getNode6());
		testIterator.testSortedIterator(this.testCompoundGraph.nodeIterator());
	}

	@Test
	public final void testGetNumEdges() {
		assertEquals ( "num edges" , EXPECTED_NUM_EDGES , testCompoundGraph.getNumEdges()) ;
	}

	@Test
	public final void testGetNumNodes() {
		assertEquals ( "num nodes" , EXPECTED_NUM_NODES , testCompoundGraph.getNumNodes()) ;
	}

	@Test
	public final void testGetCurrentState() {
		assertNotNull("current state graph" , testCompoundGraph.getCurrentState());
	}

	@Test
	public final void testRestoreState() {
		assertEquals ("correct Original node State" , EXPECTED_STATE_STRING , testCompoundGraph.getCurrentState().getElementStates().toString() ) ;
//		FIXME: do this! 
//		testCompoundGraph.restoreState(originalState) ;
//		assertEquals("current state graph" , originalState.getGraph() , testCompoundGraph.getCurrentState().getGraph() );
//		assertEquals ( "Only one node" , NUMERIC[1] , testCompoundGraph.getNumNodes()) ;
//		assertEquals ( "no edges" , NUMERIC[0] , testCompoundGraph.getNumEdges()) ;
	}
	
	@Test
	public final void numElementsTest(){
		assertEquals("expected num elements", EXPECTED_NUM_ELEMENTS, this.testCompoundGraph.numElements());
	}

	@Test
	public void elementIteratorTest(){
		IteratorTestUtility<ICompoundGraphElement> iterTest = new IteratorTestUtility<ICompoundGraphElement>(this.testFixture.getRootNode(), this.testFixture.getNode1(),
				this.testFixture.getNode2(), this.testFixture.getNode3(), this.testFixture.getNode4(), this.testFixture.getNode5(), this.testFixture.getNode6(),
				this.testFixture.getEdge1(), this.testFixture.getEdge2(), this.testFixture.getEdge3(), this.testFixture.getEdge4());
		iterTest.testSortedIterator(this.testCompoundGraph.elementIterator());
	}
}
