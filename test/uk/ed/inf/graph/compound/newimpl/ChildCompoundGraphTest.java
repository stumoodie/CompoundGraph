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
import uk.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.ICompoundNodeFactory;
import uk.ed.inf.graph.compound.testfixture.ComplexGraphFixture;
import uk.ed.inf.graph.compound.testfixture.IEdgeConstructor;
import uk.ed.inf.graph.compound.testfixture.IteratorTestUtility;

@RunWith(JMock.class)
public class ChildCompoundGraphTest {
	private static final int EXPECTED_MISSING_IDX = 99;
	private static final int EXPECTED_NUM_EDGES = 1;
	private static final int EXPECTED_NUM_NODES = 2;
	protected static final int EXPECTED_ROOT_IDX = 0;
	

	private Mockery mockery;

	private ChildCompoundGraph testInstance;
	private ComplexGraphFixture testFixture;
	
	@Before
	public void setUp() throws Exception {
		this.mockery = new JUnit4Mockery();
		
		this.testFixture = new ComplexGraphFixture(this.mockery, "");
		this.testFixture.redefineEdge(ComplexGraphFixture.EDGE1_ID, new IEdgeConstructor() {
			
			@Override
			public ICompoundNodeFactory createNodeFactory(IChildCompoundGraph childGraph) {
				return childGraph.nodeFactory();
			}
			
			@Override
			public ICompoundEdgeFactory createEdgeFactory(IChildCompoundGraph childGraph) {
				return childGraph.edgeFactory();
			}
			
			@Override
			public ICompoundEdge createCompoundEdge() {
				return null;
			}
			
			@Override
			public IChildCompoundGraph createCompoundChildGraph(final ICompoundEdge edge1) {
				testInstance = new ChildCompoundGraph(edge1);
				mockery.checking(new Expectations(){{
					allowing(edge1).getChildCompoundGraph(); will(returnValue(testInstance));
				}});
				return testInstance;
			}
			
			@Override
			public boolean buildNodeFactory(ICompoundNodeFactory nodeFactory) {
				return true;
			}
			
			@Override
			public boolean buildEdgeFactory(ICompoundEdgeFactory edgeFactory) {
				return true;
			}
			
			@Override
			public boolean buildEdge(ICompoundEdge edge) {
				return false;
			}
			
			@Override
			public boolean buildChildGraph(IChildCompoundGraph childGraph) {
				childGraph.addEdge(testFixture.getEdge2());
				childGraph.addNode(testFixture.getNode3());
				childGraph.addNode(testFixture.getNode5());
				return true;
			}
		});
		this.testFixture.buildFixture();
//		{
//			
//			@Override
//			protected void buildEdge1ChildGraph(IChildCompoundGraph childGraph){
//				childGraph.addEdge(getEdge2());
//				childGraph.addNode(getNode3());
//				childGraph.addNode(getNode5());
//			}
//		};
		
//		this.testFixture.createElements();
//		this.testInstance = new ChildCompoundGraph(this.testFixture.getEdge1());
//		this.testFixture.setEdge1ChildGraph(testInstance);
//		this.testFixture.setBuildDependencies(Arrays.asList(new String[]{
//				"graph", "elementTree", "node1", "node1ChildGraph", "node2", "node2ChildGraph", "node3", "node3ChildGraph",
//				"node4", "node4ChildGraph", "node5", "node5ChildGraph", "edge4", "edge4ChildGraph", "edge3", "edge3ChildGraph",
//				"edge2", "edge2ChildGraph", "edge1", "edge1ChildGraph"
//		}));
//		this.testFixture.buildObjects();
		
	}

	@After
	public void tearDown() throws Exception {
		this.mockery = null;
		this.testInstance = null;
		this.testFixture = null;
	}

	@Test
	public final void testEdgeFactory(){
		ICompoundEdgeFactory actualFact = this.testInstance.edgeFactory();
		assertNotNull("edge fact exists", actualFact);
		assertTrue("factory new instance", this.testInstance.edgeFactory() != actualFact);
	}
	
	@Test
	public final void testNodeFactory(){
		ICompoundNodeFactory actualFact = this.testInstance.nodeFactory();
		assertNotNull("node fact exists", actualFact);
		assertTrue("factory new instance", this.testInstance.nodeFactory() != actualFact);
	}
	
	@Test(expected=PreConditionException.class)
	public final void testSubCigraphNull() {
		new ChildCompoundGraph(null);
	}

	@Test
	public final void testContainsDirectedEdge() {
		assertTrue("has directed edge", this.testInstance.containsDirectedEdge(this.testFixture.getNode3(), this.testFixture.getNode5()));
		assertFalse("has no directed edge", this.testInstance.containsDirectedEdge(this.testFixture.getNode5(), this.testFixture.getNode3()));
		assertFalse("has no directed edge", this.testInstance.containsDirectedEdge(this.testFixture.getNode3(), this.testFixture.getNode1()));
		assertFalse("has no directed edge", this.testInstance.containsDirectedEdge(null, null));
	}

	@Test
	public final void testContainsConnection() {
		assertTrue("has conection", this.testInstance.containsConnection(this.testFixture.getEdge2().getConnectedNodes().getOutNode(), this.testFixture.getEdge2().getConnectedNodes().getInNode()));
		assertTrue("has conection", this.testInstance.containsConnection(this.testFixture.getEdge2().getConnectedNodes().getInNode(), this.testFixture.getEdge2().getConnectedNodes().getOutNode()));
		assertFalse("has no conection", this.testInstance.containsConnection(this.testFixture.getEdge3().getConnectedNodes().getOutNode(), this.testFixture.getEdge3().getConnectedNodes().getInNode()));
		assertFalse("has no conection", this.testInstance.containsConnection(this.testFixture.getEdge2().getConnectedNodes().getInNode(), this.testFixture.getEdge2().getConnectedNodes().getInNode()));
		assertFalse("has no edge", this.testInstance.containsConnection(null, null));
	}

	@Test
	public final void testContainsEdgeInt() {
		assertTrue("has edge", this.testInstance.containsEdge(this.testFixture.getEdge2().getIndex()));
		assertFalse("has edge", this.testInstance.containsEdge(this.testFixture.getEdge4().getIndex()));
		assertFalse("not has edge", this.testInstance.containsEdge(EXPECTED_MISSING_IDX));
		this.mockery.assertIsSatisfied();
	}

	@Test
	public final void testContainsEdgeIEdge() {
		assertTrue("has edge", this.testInstance.containsEdge(this.testFixture.getEdge2()));
		assertFalse("has edge", this.testInstance.containsEdge(this.testFixture.getEdge4()));
		assertFalse("not has edge", this.testInstance.containsEdge(null));
		this.mockery.assertIsSatisfied();
	}

	@Test
	public final void testContainsNodeInt() {
		assertTrue("has node", this.testInstance.containsNode(this.testFixture.getNode3().getIndex()));
		assertFalse("not has node", this.testInstance.containsNode(this.testFixture.getNode4().getIndex()));
		assertFalse("no node", this.testInstance.containsNode(EXPECTED_MISSING_IDX));
	}

	@Test
	public final void testContainsNodeINode() {
		assertTrue("has node", this.testInstance.containsNode(this.testFixture.getNode3()));
		assertFalse("not has node", this.testInstance.containsNode(this.testFixture.getNode4()));
		assertFalse("no node", this.testInstance.containsNode(null));
	}

	@Test(expected=PreConditionException.class)
	public final void testGetEdgeFailed() {
		this.testInstance.getEdge(EXPECTED_MISSING_IDX);
	}

	@Test
	public final void testGetEdge() {
		assertEquals("expected edge", this.testFixture.getEdge2(), this.testInstance.getEdge(this.testFixture.getEdge2().getIndex()));
	}

	@Test
	public final void testGetNode() {
		assertEquals("expected node", this.testFixture.getNode3(), this.testInstance.getNode(this.testFixture.getNode3().getIndex()));
	}

	@Test
	public final void testEdgeIterator() {
		IteratorTestUtility<ICompoundEdge> iterTest = new IteratorTestUtility<ICompoundEdge>(this.testFixture.getEdge2());
		iterTest.testIterator(this.testInstance.edgeIterator());
	}

	@Test(expected=PreConditionException.class)
	public final void testGetNodeMissingIndex() {
		this.testInstance.getNode(EXPECTED_MISSING_IDX);
	}

	@Test
	public final void testNodeIterator() {
		IteratorTestUtility<ICompoundNode> iterTest = new IteratorTestUtility<ICompoundNode>(this.testFixture.getNode3(), this.testFixture.getNode5());
		iterTest.testIterator(this.testInstance.nodeIterator());
	}

	@Test
	public final void testUnfilteredNodeIterator() {
		IteratorTestUtility<ICompoundNode> iterTest = new IteratorTestUtility<ICompoundNode>(this.testFixture.getNode3(), this.testFixture.getNode5());
		iterTest.testIterator(this.testInstance.unfilteredNodeIterator());
	}

	@Test
	public void testUnfilteredEdgeIterator(){
		IteratorTestUtility<ICompoundEdge> iterTest = new IteratorTestUtility<ICompoundEdge>(this.testFixture.getEdge2());
		iterTest.testIterator(this.testInstance.unfilteredEdgeIterator());
	}
	
	@Test
	public final void testGetNumEdges() {
		assertEquals("expected num edges", EXPECTED_NUM_EDGES, this.testInstance.numEdges());
		this.mockery.assertIsSatisfied();
	}

	@Test
	public final void testGetNumNodes() {
		assertEquals("num nodes", EXPECTED_NUM_NODES, this.testInstance.numNodes());
		this.mockery.assertIsSatisfied();
	}

}
