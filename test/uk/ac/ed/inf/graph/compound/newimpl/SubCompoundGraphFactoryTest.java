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

import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ac.ed.inf.graph.compound.testfixture.ComplexGraphFixture;
import uk.ac.ed.inf.graph.compound.testfixture.IteratorTestUtility;

@RunWith(JMock.class)
public class SubCompoundGraphFactoryTest {
	private Mockery mockery;

	private SubCompoundGraphFactory testSubCompoundGraphFactory ;
	private ComplexGraphFixture testFixture;
	
	private static final int EXPECTED_TOP_SUBGRAPH_ELEMENTS = 3;
	private static final int EXPECTED_NUM_SUBGRAPH_ELEMENTS = 5;
	private static final int EXPECTED_TOP_PERMSSIVE_INDUCED_SUBGRAPH_ELEMENTS = 4;
	private static final int EXPECTED_PERMSSIVE_INDUCED_SUBGRAPH_ELEMENTS = 6;
	private static final int EXPECTED_TOP_INDUCED_SUBGRAPH_ELEMENTS = 3;
	private static final int EXPECTED_INDUCED_SUBGRAPH_ELEMENTS = 4;
	
	
	@Before
	public void setUp() throws Exception {
		mockery = new JUnit4Mockery();
	
		this.testFixture = new ComplexGraphFixture(mockery, ""); 
		this.testFixture.buildFixture();
		
		testSubCompoundGraphFactory = new SubCompoundGraphFactory (testFixture.getGraph()) ;
		
		testSubCompoundGraphFactory.addElement(this.testFixture.getNode2()) ;
		testSubCompoundGraphFactory.addElement(this.testFixture.getNode3()) ;
		testSubCompoundGraphFactory.addElement(this.testFixture.getEdge2()) ;
	}

	@After
	public void tearDown() throws Exception {
		this.testFixture = null;
		this.mockery = null;
		this.testSubCompoundGraphFactory = null;
	}

	@Test
	public final void testAddElement() {
		testSubCompoundGraphFactory.addElement(this.testFixture.getNode5()) ; 
		testSubCompoundGraphFactory.addElement(this.testFixture.getEdge4()) ; 
		
		assertEquals ( "correct size" , 5 , this.testSubCompoundGraphFactory.numElements()) ;

	}

	@Test
	public final void testElementIterator() {
		IteratorTestUtility<ICompoundGraphElement> testIterator = new IteratorTestUtility<ICompoundGraphElement>(this.testFixture.getNode2(), this.testFixture.getNode3(), this.testFixture.getEdge2());
		testIterator.testSortedIterator(testSubCompoundGraphFactory.elementIterator());
	}

	@Test
	public final void testCreateSubgraph() {
		ISubCompoundGraph generatedSubGraph = testSubCompoundGraphFactory.createSubgraph() ;
		
		assertTrue("edge found", generatedSubGraph.containsEdge(this.testFixture.getEdge2()));
		assertTrue("edge found", generatedSubGraph.containsEdge(this.testFixture.getEdge3()));
		assertFalse("not edge found", generatedSubGraph.containsEdge(this.testFixture.getEdge1()));
		assertFalse("edge not found", generatedSubGraph.containsEdge(this.testFixture.getEdge4()));
		assertTrue("node found", generatedSubGraph.containsNode(this.testFixture.getNode2()));
		assertTrue("node found", generatedSubGraph.containsNode(this.testFixture.getNode3()));
		assertTrue("node found", generatedSubGraph.containsNode(this.testFixture.getNode4()));
		assertFalse("node not found", generatedSubGraph.containsNode(this.testFixture.getRootNode()));
		assertFalse("node not found", generatedSubGraph.containsNode(this.testFixture.getNode1()));
		assertFalse("node not found", generatedSubGraph.containsNode(this.testFixture.getNode5()));
		assertFalse("node not found", generatedSubGraph.containsNode(this.testFixture.getNode6()));
		assertEquals("expected top elements", EXPECTED_TOP_SUBGRAPH_ELEMENTS, generatedSubGraph.numTopElements());
		assertEquals("expected elements", EXPECTED_NUM_SUBGRAPH_ELEMENTS, generatedSubGraph.numElements());
	}

	@Test
	public final void testCreateInducedSubgraph() {
		ISubCompoundGraph generatedSubGraph = testSubCompoundGraphFactory.createInducedSubgraph() ;
		
		assertFalse("edge not found", generatedSubGraph.containsEdge(this.testFixture.getEdge2()));
		assertTrue("edge found", generatedSubGraph.containsEdge(this.testFixture.getEdge3()));
		assertFalse("not edge found", generatedSubGraph.containsEdge(this.testFixture.getEdge1()));
		assertTrue("edge found", generatedSubGraph.containsEdge(this.testFixture.getEdge4()));
		assertTrue("node found", generatedSubGraph.containsNode(this.testFixture.getNode2()));
		assertTrue("node found", generatedSubGraph.containsNode(this.testFixture.getNode3()));
		assertFalse("node not found", generatedSubGraph.containsNode(this.testFixture.getNode4()));
		assertFalse("node not found", generatedSubGraph.containsNode(this.testFixture.getRootNode()));
		assertFalse("node not found", generatedSubGraph.containsNode(this.testFixture.getNode1()));
		assertFalse("node not found", generatedSubGraph.containsNode(this.testFixture.getNode5()));
		assertFalse("node not found", generatedSubGraph.containsNode(this.testFixture.getNode6()));
		assertEquals("expected top elements", EXPECTED_TOP_INDUCED_SUBGRAPH_ELEMENTS, generatedSubGraph.numTopElements());
		assertEquals("expected elemenbt", EXPECTED_INDUCED_SUBGRAPH_ELEMENTS, generatedSubGraph.numElements());
	}

	@Test
	public final void testCreatePermissiveInducedSubgraph() {
		ISubCompoundGraph generatedSubGraph = testSubCompoundGraphFactory.createPermissiveInducedSubgraph() ;
		
		assertTrue("edge found", generatedSubGraph.containsEdge(this.testFixture.getEdge2()));
		assertTrue("edge found", generatedSubGraph.containsEdge(this.testFixture.getEdge3()));
		assertFalse("not edge found", generatedSubGraph.containsEdge(this.testFixture.getEdge1()));
		assertTrue("edge found", generatedSubGraph.containsEdge(this.testFixture.getEdge4()));
		assertTrue("node found", generatedSubGraph.containsNode(this.testFixture.getNode2()));
		assertTrue("node found", generatedSubGraph.containsNode(this.testFixture.getNode3()));
		assertTrue("node found", generatedSubGraph.containsNode(this.testFixture.getNode4()));
		assertFalse("node not found", generatedSubGraph.containsNode(this.testFixture.getRootNode()));
		assertFalse("node not found", generatedSubGraph.containsNode(this.testFixture.getNode1()));
		assertFalse("node not found", generatedSubGraph.containsNode(this.testFixture.getNode5()));
		assertFalse("node not found", generatedSubGraph.containsNode(this.testFixture.getNode6()));
		assertEquals("expected top elements", EXPECTED_TOP_PERMSSIVE_INDUCED_SUBGRAPH_ELEMENTS, generatedSubGraph.numTopElements());
		assertEquals("expected elemenbt", EXPECTED_PERMSSIVE_INDUCED_SUBGRAPH_ELEMENTS, generatedSubGraph.numElements());
	}

}
