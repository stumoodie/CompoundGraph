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
import static org.junit.Assert.assertTrue;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.ed.inf.designbycontract.PreConditionException;
import uk.ac.ed.inf.graph.compound.CompoundNodePair;
import uk.ac.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundChildEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.ICompoundNodeFactory;
import uk.ac.ed.inf.graph.compound.testfixture.ComplexGraphFixture;
import uk.ac.ed.inf.graph.compound.testfixture.NodeElementAttribute;
import uk.ac.ed.inf.graph.compound.testfixture.INodeConstructor;
import uk.ac.ed.inf.graph.compound.testfixture.IteratorTestUtility;

@RunWith(JMock.class)
public class CompoundNodeAddEdgesTest {
	private static final int EXPECTED_LEVEL = 2;
	private static final int EXPECTED_DEGREE = 0;
	private static final int EXPECTED_IN_DEGREE = 0;
	private static final int EXPECTED_OUT_DEGREE = 0;

	private Mockery mockery = new JUnit4Mockery();
	
	private ComplexGraphFixture testFixture;
	private ICompoundNode testInstance;
	private ComplexGraphFixture otherTestFixture;
	private NodeElementAttribute expectedAttribute;

	@Before
	public void setUp() throws Exception {
		this.expectedAttribute = new NodeElementAttribute(ComplexGraphFixture.NODE3_ID);
		this.testFixture = new ComplexGraphFixture(this.mockery, "");
		this.testFixture.redefineNode(ComplexGraphFixture.NODE3_ID, new INodeConstructor() {
			
			@Override
			public ICompoundNodeFactory createNodeFactory(IChildCompoundGraph childGraph) {
				return childGraph.nodeFactory();
			}
			
			@Override
			public ICompoundNode createCompoundNode() {
				testInstance = new CompoundNode(testFixture.getEdge1(), ComplexGraphFixture.NODE3_IDX, expectedAttribute);
				return testInstance;
			}
			
			@Override
			public IChildCompoundGraph createCompoundChildGraph(ICompoundNode node) {
				return node.getChildCompoundGraph();
			}
			
			@Override
			public boolean buildNodeFactory(ICompoundNodeFactory nodeFactory) {
				return true;
			}
			
			@Override
			public boolean buildNode(ICompoundNode node) {
				return true;
			}
			
			@Override
			public boolean buildChildGraph(IChildCompoundGraph node) {
				return true;
			}

			@Override
			public boolean buildEdgeFactory(ICompoundEdgeFactory edgeFactory) {
				return false;
			}

			@Override
			public ICompoundChildEdgeFactory createEdgeFactory(IChildCompoundGraph childGraph) {
				return null;
			}
		});
		this.testFixture.buildFixture();
		
		this.otherTestFixture = new ComplexGraphFixture(this.mockery, "other_");
		this.otherTestFixture.buildFixture();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetGraph() {
		assertEquals("expected graph", this.testFixture.getGraph(), this.testInstance.getGraph());
	}

	@Test
	public void testGetChildCompoundGraph() {
		assertNotNull("Child exists", this.testInstance.getChildCompoundGraph());
	}

	@Test
	public void testGetLevel() {
		assertEquals("Expected level", EXPECTED_LEVEL, this.testInstance.getLevel());
	}

	@Test
	public void testGetParent() {
		assertEquals("Expected parent", this.testFixture.getEdge1(), this.testInstance.getParent());
	}

	@Test
	public void testGetRoot() {
		assertEquals("root", this.testFixture.getRootNode(), this.testInstance.getRoot());
	}

	@Test
	public void testConnectedNodeIterator() {
		IteratorTestUtility<ICompoundNode> testIter = new IteratorTestUtility<ICompoundNode>();
		testIter.testSortedIterator(this.testInstance.connectedNodeIterator());
	}

	@Test
	public void testEdgeIterator() {
		IteratorTestUtility<ICompoundEdge> testIter = new IteratorTestUtility<ICompoundEdge>();
		testIter.testSortedIterator(this.testInstance.edgeIterator());
	}

	@Test
	public void testGetDegree() {
		assertEquals("expected degree", EXPECTED_DEGREE, this.testInstance.getDegree());
	}

	@Test(expected=PreConditionException.class)
	public void testGetEdgesWith() {
		this.testInstance.getEdgesWith(this.testFixture.getNode5());
	}

	@Test
	public void testGetExpectedAttribute(){
		assertEquals("expected attribute", this.expectedAttribute, this.testInstance.getAttribute());
	}
	
	@Test
	public void testGetInDegree() {
		assertEquals("expected in degree", EXPECTED_IN_DEGREE, this.testInstance.getInDegree());
	}

	@Test
	public void testGetInEdgeIterator() {
		IteratorTestUtility<ICompoundEdge> testIter = new IteratorTestUtility<ICompoundEdge>();
		testIter.testSortedIterator(this.testInstance.getInEdgeIterator());
	}

	@Test
	public void testOutEdgeIncidentNodesIterator() {
		IteratorTestUtility<ICompoundNode> testIter = new IteratorTestUtility<ICompoundNode>();
		testIter.testIterator(this.testInstance.outEdgeIncidentNodesIterator());
	}

	@Test
	public void testGetIndex() {
		assertEquals("expected index", ComplexGraphFixture.NODE3_IDX, this.testInstance.getIndex());
	}

	@Test
	public void testGetOutDegree() {
		assertEquals("expected in degree", EXPECTED_OUT_DEGREE, this.testInstance.getOutDegree());
	}

	@Test
	public void testGetOutEdgeIterator() {
		IteratorTestUtility<ICompoundEdge> testIter = new IteratorTestUtility<ICompoundEdge>();
		testIter.testSortedIterator(this.testInstance.getOutEdgeIterator());
	}

	@Test(expected=PreConditionException.class)
	public void testGetOutEdgesToWithNonOutNode() {
		this.testInstance.getOutEdgesTo(this.testFixture.getNode2());
	}

	@Test(expected=PreConditionException.class)
	public void testGetOutEdgesToWithNull() {
		this.testInstance.getOutEdgesTo(null);
	}

	@Test
	public void testinEdgeIncidentNodesIterator() {
		IteratorTestUtility<ICompoundNode> testIter = new IteratorTestUtility<ICompoundNode>();
		testIter.testSortedIterator(this.testInstance.inEdgeIncidentNodesIterator());
	}

	@Test
	public void testHasEdgeWith() {
		assertFalse("does not contain incident node", this.testInstance.hasEdgeWith(this.testFixture.getNode2()));
		assertFalse("does not contain incident node", this.testInstance.hasEdgeWith(this.testFixture.getNode5()));
	}

	@Test
	public void testHasInEdgeFrom() {
		assertFalse("not has edge from", this.testInstance.hasInEdgeFrom(this.testFixture.getNode2()));
	}

	@Test
	public void testHasOutEdgeTo() {
		assertFalse("not has out edge to", this.testInstance.hasOutEdgeTo(this.testFixture.getNode5()));
	}

	@Test
	public void testIsAncestor() {
		assertTrue("is ancestor", this.testInstance.isAncestor(this.testFixture.getEdge1()));
	}

	@Test
	public void testIsLink() {
		assertFalse("not link", this.testInstance.isEdge());
	}

	@Test
	public void testIsNode() {
		assertTrue("is node", this.testInstance.isNode());
	}

	@Test
	public void testCompareTo() {
		assertEquals("less than", -1, this.testInstance.compareTo(this.testFixture.getNode5()));
		assertEquals("gt than", 1, this.testInstance.compareTo(this.testFixture.getNode2()));
		assertEquals("equal", 0, this.testInstance.compareTo(this.testInstance));
	}

	@Test
	public void testAncestorIterator() {
		IteratorTestUtility<ICompoundGraphElement> testIter = new IteratorTestUtility<ICompoundGraphElement>(this.testFixture.getRootNode(), this.testFixture.getEdge1());
		testIter.testSortedIterator(this.testInstance.ancestorIterator());
	}

	@Test
	public void testChildIterator() {
		IteratorTestUtility<ICompoundGraphElement> testIter = new IteratorTestUtility<ICompoundGraphElement>();
		testIter.testIterator(this.testInstance.childIterator());
	}

	@Test
	public void testIsRemoved() {
		assertFalse("not remove", this.testInstance.isRemoved());
	}

	@Test
	public void testContainsEdge() {
		assertFalse("contains edge", this.testInstance.containsEdge(this.testFixture.getEdge4()));
		assertFalse("contains edge", this.testInstance.containsEdge(this.testFixture.getEdge2()));
	}

	@Test
	public void testContainsInEdge() {
		assertFalse("contains edge", this.testInstance.containsInEdge(this.testFixture.getEdge4()));
		assertFalse("contains edge", this.testInstance.containsInEdge(this.testFixture.getEdge2()));
	}

	@Test
	public void testContainsOutEdge() {
		assertFalse("contains edge", this.testInstance.containsOutEdge(this.testFixture.getEdge4()));
		assertFalse("contains edge", this.testInstance.containsOutEdge(this.testFixture.getEdge2()));
	}

	@Test
	public void testAddInEdge(){
		this.testInstance.addInEdge(this.testFixture.getEdge4());
		assertTrue("contains edge", this.testInstance.containsEdge(testFixture.getEdge4()));
		assertFalse("contains edge", this.testInstance.containsOutEdge(this.testFixture.getEdge2()));
		assertTrue("contains edge", this.testInstance.containsInEdge(testFixture.getEdge4()));
		assertFalse("contains edge", this.testInstance.containsOutEdge(this.testFixture.getEdge4()));
		assertFalse("contains edge", this.testInstance.containsOutEdge(this.testFixture.getEdge2()));
	}

	@Test(expected=PreConditionException.class)
	public void testAddInEdgeNull(){
		this.testInstance.addInEdge(null);
	}

	@Test(expected=PreConditionException.class)
	public void testAddInEdgeOtherGraph(){
		this.testInstance.addInEdge(this.otherTestFixture.getEdge4());
	}

	@Test(expected=PreConditionException.class)
	public void testAddInEdgeEdgeNodePairDiffers(){
		assertEquals("expected node pair", new CompoundNodePair(this.testFixture.getNode1(), this.testFixture.getNode6()), this.testFixture.getEdge1().getConnectedNodes());
		this.testInstance.addInEdge(this.testFixture.getEdge1());
	}

	@Test(expected=PreConditionException.class)
	public void testAddInEdgeWrongDirectionEdge(){
		assertEquals("expected node pair", new CompoundNodePair(this.testFixture.getNode3(), this.testFixture.getNode5()), this.testFixture.getEdge2().getConnectedNodes());
		this.testInstance.addInEdge(this.testFixture.getEdge2());
	}

	@Test
	public void testAddOutEdge(){
		this.testInstance.addOutEdge(this.testFixture.getEdge2());
		assertTrue("contains edge", this.testInstance.containsEdge(testFixture.getEdge2()));
		assertFalse("contains edge", this.testInstance.containsInEdge(this.testFixture.getEdge4()));
		assertTrue("contains edge", this.testInstance.containsOutEdge(testFixture.getEdge2()));
		assertFalse("contains edge", this.testInstance.containsInEdge(this.testFixture.getEdge4()));
		assertFalse("contains edge", this.testInstance.containsInEdge(this.testFixture.getEdge2()));
	}

	@Test(expected=PreConditionException.class)
	public void testAddOutEdgeNull(){
		this.testInstance.addOutEdge(null);
	}

	@Test(expected=PreConditionException.class)
	public void testAddOutEdgeOtherGraph(){
		this.testInstance.addOutEdge(this.otherTestFixture.getEdge2());
	}

	@Test(expected=PreConditionException.class)
	public void testAddOutEdgeEdgeNodePairDiffers(){
		assertEquals("expected node pair", new CompoundNodePair(this.testFixture.getNode1(), this.testFixture.getNode6()), this.testFixture.getEdge1().getConnectedNodes());
		this.testInstance.addOutEdge(this.testFixture.getEdge1());
	}

	@Test(expected=PreConditionException.class)
	public void testAddOutEdgeWrongDirectionEdge(){
		assertEquals("expected node pair", new CompoundNodePair(this.testFixture.getNode2(), this.testFixture.getNode3()), this.testFixture.getEdge4().getConnectedNodes());
		this.testInstance.addOutEdge(this.testFixture.getEdge4());
	}

}
