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
import static org.junit.Assert.assertTrue;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundChildEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.ICompoundNodeFactory;
import uk.ac.ed.inf.graph.compound.testfixture.ComplexGraphFixture;
import uk.ac.ed.inf.graph.compound.testfixture.ElementAttribute;
import uk.ac.ed.inf.graph.compound.testfixture.INodeConstructor;

@RunWith(JMock.class)
public class CompoundNodeRemovedStateTest {
	private Mockery mockery;
	
	private ComplexGraphFixture testFixture;
	private ICompoundNode testInstance;
	private ComplexGraphFixture otherTestFixture;
	private ElementAttribute expectedAttribute;

	@Before
	public void setUp() throws Exception {
		this.mockery = new JUnit4Mockery();
		this.expectedAttribute = new ElementAttribute(ComplexGraphFixture.NODE1_ID);
		this.testFixture = new ComplexGraphFixture(this.mockery, "");
		this.testFixture.redefineNode(ComplexGraphFixture.NODE1_ID, new INodeConstructor() {
			
			@Override
			public ICompoundNodeFactory createNodeFactory(IChildCompoundGraph childGraph) {
				return childGraph.nodeFactory();
			}
			
			@Override
			public ICompoundNode createCompoundNode() {
				testInstance = new CompoundNode(testFixture.getRootNode(), ComplexGraphFixture.NODE1_IDX, expectedAttribute);
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
				node.addOutEdge(testFixture.getEdge1());
				return true;
			}
			
			@Override
			public boolean buildChildGraph(IChildCompoundGraph node) {
				node.addNode(testFixture.getNode2());
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
		this.expectedAttribute.setCurrentElement(this.otherTestFixture.getNode2());
		
		this.testInstance.markRemoved(true);
	}

	@After
	public void tearDown() throws Exception {
		this.mockery = null;
		this.expectedAttribute = null;
		this.testFixture = null;
		this.otherTestFixture = null;
		this.testInstance = null;
	}

	
	@Test
	public void testMarkRemovedTrue(){
		this.testInstance.markRemoved(false);
		assertFalse("node removed", this.testInstance.isRemoved());
		assertEquals("expected attribute", this.expectedAttribute, this.testInstance.getAttribute());
		assertEquals("expected current element", this.testInstance, this.expectedAttribute.getCurrentElement());
	}
	
	@Test
	public void testGetAttribute(){
		assertEquals("expected attribute", this.expectedAttribute, this.testInstance.getAttribute());
		assertEquals("expected current element", this.otherTestFixture.getNode2(), this.expectedAttribute.getCurrentElement());
	}
	
	@Test
	public void testIsRemoved() {
		assertTrue("remove", this.testInstance.isRemoved());
	}

}
