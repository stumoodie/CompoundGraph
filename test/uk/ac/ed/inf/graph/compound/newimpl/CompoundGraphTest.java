/*
  Licensed to the Court of the University of Edinburgh (UofE) under one
  or more contributor license agreements.  See the NOTICE file
  distributed with this work for additional information
  regarding copyright ownership.  The UofE licenses this file
  to you under the Apache License, Version 2.0 (the
  "License"); you may not use this file except in compliance
  with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing,
  software distributed under the License is distributed on an
  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
  KIND, either express or implied.  See the License for the
  specific language governing permissions and limitations
  under the License.
*/

package uk.ac.ed.inf.graph.compound.newimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.ed.inf.bitstring.BitStringBuffer;
import uk.ac.ed.inf.bitstring.IBitString;
import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.ICompoundNodeFactory;
import uk.ac.ed.inf.graph.compound.IElementAttribute;
import uk.ac.ed.inf.graph.compound.IRootChildCompoundGraph;
import uk.ac.ed.inf.graph.compound.IRootCompoundNode;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraphFactory;
import uk.ac.ed.inf.graph.compound.ISubgraphRemovalBuilder;
import uk.ac.ed.inf.graph.compound.testfixture.ComplexGraphFixture;
import uk.ac.ed.inf.graph.compound.testfixture.ElementAttribute;
import uk.ac.ed.inf.graph.compound.testfixture.IGraphConstructor;
import uk.ac.ed.inf.graph.compound.testfixture.IteratorTestUtility;
import uk.ac.ed.inf.graph.state.IGraphState;

@RunWith(JMock.class)
public class CompoundGraphTest {
	private Mockery mockery;
	
	private ICompoundGraph testCompoundGraph ;
	private ComplexGraphFixture testFixture;
	private ComplexGraphFixture otherTestFixture;
	private IGraphState expectedRestoreState;
	private IBitString expectedBitString;

	private IElementAttribute expectedAttribute;
	
	private static final String EXPECTED_STATE_STRING = "{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10}";
	private static final int UNFOUND_EDGE_IDX = 99;
	private static final int UNFOUND_NODE_IDX = 77;
	private static final int EXPECTED_NUM_EDGES = 4;
	private static final int EXPECTED_NUM_NODES = 7;
	private static final int EXPECTED_NUM_ELEMENTS = 11;
	
	
	@Before
	public void setUp() throws Exception {
		this.expectedAttribute = new ElementAttribute(ComplexGraphFixture.GRAPH_ID);
		this.mockery = new JUnit4Mockery();
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
				return testCompoundGraph = new CompoundGraph(expectedAttribute);
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
		this.testFixture.buildFixture();
		
		this.otherTestFixture = new ComplexGraphFixture(mockery, "other_");
		this.otherTestFixture.buildFixture();
		
		BitStringBuffer buf = new BitStringBuffer();
		buf.set(0, this.testFixture.getGraph().numElements(), true);
		buf.set(4, false);
		this.expectedBitString = buf.toBitString();
		expectedRestoreState = mockery.mock(IGraphState.class, "expectedRestoreState");
		mockery.checking(new Expectations(){{
			allowing(expectedRestoreState).getElementStates(); will(returnValue(expectedBitString));
			allowing(expectedRestoreState).getGraph(); will(returnValue(testFixture.getGraph()));
		}});
	}

	@After
	public void tearDown() throws Exception {
		this.mockery = null;
		this.testFixture = null;
		this.otherTestFixture = null;
		this.expectedRestoreState = null;
	}

	@Test
	public final void testGetRoot() {
		assertTrue ( "not null" , testCompoundGraph.getRoot() != null ) ;
		ICompoundNode rootNode = testCompoundGraph.getRoot() ;
		assertEquals ( "same graph" , testCompoundGraph , rootNode.getGraph() );
	}

	@Test
	public final void testGetRootAttribute() {
		ICompoundNode rootNode = testCompoundGraph.getRoot() ;
		assertEquals ( "expected attribute" , this.expectedAttribute, rootNode.getAttribute() );
		assertEquals ( "expected attribute has curr element" , rootNode, rootNode.getAttribute().getCurrentElement());
	}

	@Test
	public final void testNewSubgraphRemovalBuilder(){
		ISubgraphRemovalBuilder firstInstance = this.testCompoundGraph.newSubgraphRemovalBuilder();
		assertNotNull("removalBuilder created", firstInstance);
		ISubgraphRemovalBuilder secondInstance = this.testCompoundGraph.newSubgraphRemovalBuilder();
		assertTrue("not same instance", firstInstance != secondInstance);
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

	@Test
	public final void testGetEdgeFails() {
		assertNull ( "get Edge" , testCompoundGraph.getEdge(UNFOUND_EDGE_IDX)) ;
	}

	@Test
	public final void testEdgeIterator() {
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
		IteratorTestUtility<ICompoundNode> testIterator = new IteratorTestUtility<ICompoundNode>(this.testFixture.getRootNode(), this.testFixture.getNode1(),
				this.testFixture.getNode2(), this.testFixture.getNode3(), this.testFixture.getNode4(), this.testFixture.getNode5(), this.testFixture.getNode6());
		testIterator.testSortedIterator(this.testCompoundGraph.nodeIterator());
	}

	@Test
	public final void testGetNumEdges() {
		assertEquals ( "num edges" , EXPECTED_NUM_EDGES , testCompoundGraph.numEdges()) ;
	}

	@Test
	public final void testGetNumNodes() {
		assertEquals ( "num nodes" , EXPECTED_NUM_NODES , testCompoundGraph.numNodes()) ;
	}

	@Test
	public final void testGetCurrentState() {
		assertNotNull("current state graph" , testCompoundGraph.getCurrentState());
	}

	@Test
	public final void testRestoreState() {
		assertEquals ("correct Original node State" , EXPECTED_STATE_STRING , testCompoundGraph.getCurrentState().getElementStates().toString() ) ;
//		final Sequence rootNodeSequence = this.mockery.sequence("rootNodeSequence"); 
		final Sequence node1Sequence = this.mockery.sequence("node1Sequence"); 
		final Sequence node2Sequence = this.mockery.sequence("node2Sequence"); 
		final Sequence node3Sequence = this.mockery.sequence("node3Sequence"); 
		final Sequence node4Sequence = this.mockery.sequence("node4Sequence"); 
		final Sequence node5Sequence = this.mockery.sequence("node5Sequence"); 
		final Sequence node6Sequence = this.mockery.sequence("node6Sequence"); 
		final Sequence edge1Sequence = this.mockery.sequence("edge1Sequence"); 
		final Sequence edge2Sequence = this.mockery.sequence("edge2Sequence"); 
		final Sequence edge3Sequence = this.mockery.sequence("edge3Sequence"); 
		final Sequence edge4Sequence = this.mockery.sequence("edge4Sequence"); 
		this.mockery.checking(new Expectations(){{
//			one(testFixture.getRootNode()).markRemoved(true); inSequence(rootNodeSequence);
//			one(testFixture.getRootNode()).markRemoved(false); inSequence(rootNodeSequence);
//			one(testFixture.getNode(ComplexGraphFixture.NODE1_ID)).markRemoved(true); inSequence(node1Sequence);
			one(testFixture.getNode(ComplexGraphFixture.NODE1_ID)).markRemoved(false); inSequence(node1Sequence);
//			one(testFixture.getNode(ComplexGraphFixture.NODE2_ID)).markRemoved(true); inSequence(node2Sequence);
			one(testFixture.getNode(ComplexGraphFixture.NODE2_ID)).markRemoved(false); inSequence(node2Sequence);
//			one(testFixture.getNode(ComplexGraphFixture.NODE3_ID)).markRemoved(true); inSequence(node3Sequence);
			one(testFixture.getNode(ComplexGraphFixture.NODE3_ID)).markRemoved(false); inSequence(node3Sequence);
//			one(testFixture.getNode(ComplexGraphFixture.NODE4_ID)).markRemoved(true); inSequence(node4Sequence);
			one(testFixture.getNode(ComplexGraphFixture.NODE4_ID)).markRemoved(true); inSequence(node4Sequence);
//			one(testFixture.getNode(ComplexGraphFixture.NODE5_ID)).markRemoved(true); inSequence(node5Sequence);
			one(testFixture.getNode(ComplexGraphFixture.NODE5_ID)).markRemoved(false); inSequence(node5Sequence);
//			one(testFixture.getNode(ComplexGraphFixture.NODE6_ID)).markRemoved(true); inSequence(node6Sequence);
			one(testFixture.getNode(ComplexGraphFixture.NODE6_ID)).markRemoved(false); inSequence(node6Sequence);
//			one(testFixture.getEdge(ComplexGraphFixture.EDGE1_ID)).markRemoved(true); inSequence(edge1Sequence);
			one(testFixture.getEdge(ComplexGraphFixture.EDGE1_ID)).markRemoved(false); inSequence(edge1Sequence);
//			one(testFixture.getEdge(ComplexGraphFixture.EDGE2_ID)).markRemoved(true); inSequence(edge2Sequence);
			one(testFixture.getEdge(ComplexGraphFixture.EDGE2_ID)).markRemoved(false); inSequence(edge2Sequence);
//			one(testFixture.getEdge(ComplexGraphFixture.EDGE3_ID)).markRemoved(true); inSequence(edge3Sequence);
			one(testFixture.getEdge(ComplexGraphFixture.EDGE3_ID)).markRemoved(false); inSequence(edge3Sequence);
//			one(testFixture.getEdge(ComplexGraphFixture.EDGE4_ID)).markRemoved(true); inSequence(edge4Sequence);
			one(testFixture.getEdge(ComplexGraphFixture.EDGE4_ID)).markRemoved(false); inSequence(edge4Sequence);
		}});
		testCompoundGraph.restoreState(expectedRestoreState) ;
		this.mockery.assertIsSatisfied();
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
