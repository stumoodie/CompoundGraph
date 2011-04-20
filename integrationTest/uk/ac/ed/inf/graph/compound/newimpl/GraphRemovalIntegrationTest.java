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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraphFactory;
import uk.ac.ed.inf.graph.compound.ISubgraphRemovalBuilder;
import uk.ac.ed.inf.graph.compound.testfixture.IGraphTestFixture;
import uk.ac.ed.inf.graph.compound.testfixture.IteratorTestUtility;

public class GraphRemovalIntegrationTest {
	private IGraphTestFixture testFixture;
	private ICompoundGraph testInstance;
	private ISubgraphRemovalBuilder removalBuilder;
	
	@Before
	public void setUp() throws Exception {
		this.testFixture = new IntegrationTestGraphFixture();
		this.testFixture.buildFixture();
		this.testInstance = this.testFixture.getGraph();
		ISubCompoundGraphFactory subgraphFact = this.testInstance.subgraphFactory();
		subgraphFact.addElement(this.testFixture.getNode(IntegrationTestGraphFixture.NODE1_ID));
		subgraphFact.addElement(this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE1_ID));
		this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE2_ID).markRemoved(true);
		this.testFixture.getNode(IntegrationTestGraphFixture.NODE4_ID).markRemoved(true);
		ISubCompoundGraph subgraph = subgraphFact.createPermissiveInducedSubgraph();
		removalBuilder = this.testInstance.newSubgraphRemovalBuilder();
		removalBuilder.setRemovalSubgraph(subgraph);
		removalBuilder.removeSubgraph();
	}

	@After
	public void tearDown() throws Exception {
		this.testInstance = null;
		this.testFixture = null;
		this.removalBuilder = null;
	}

	@Test
	public void testGraphState(){
		assertEquals("expected size", 2, testInstance.numElements());
		assertEquals("expected size", 2, testInstance.numNodes());
		assertEquals("expected size", 0, testInstance.numEdges());
	}

	@Test
	public void testRemovalSubgraph(){
		ISubCompoundGraph subgraph = this.removalBuilder.getRemovalSubgraph();
		assertEquals("expected size", 7, subgraph.numElements());
		assertEquals("expected size", 3, subgraph.numTopElements());
		assertEquals("expected size", 4, subgraph.getNumNodes());
		assertEquals("expected size", 3, subgraph.getNumEdges());
		IteratorTestUtility<ICompoundGraphElement> testIter = new IteratorTestUtility<ICompoundGraphElement>(this.testFixture.getNode(IntegrationTestGraphFixture.NODE1_ID),
				this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE1_ID),	this.testFixture.getNode(IntegrationTestGraphFixture.NODE2_ID),
				this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE3_ID),	this.testFixture.getNode(IntegrationTestGraphFixture.NODE3_ID),
				this.testFixture.getNode(IntegrationTestGraphFixture.NODE5_ID), this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE4_ID));
		testIter.testSortedIterator(subgraph.elementIterator());
		testIter = new IteratorTestUtility<ICompoundGraphElement>(this.testFixture.getNode(IntegrationTestGraphFixture.NODE1_ID),
				this.testFixture.getNode(IntegrationTestGraphFixture.NODE2_ID), this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE3_ID),
				this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE1_ID),	this.testFixture.getNode(IntegrationTestGraphFixture.NODE3_ID),
				this.testFixture.getNode(IntegrationTestGraphFixture.NODE5_ID), this.testFixture.getEdge(IntegrationTestGraphFixture.EDGE4_ID));
		testIter.testIterator(subgraph.edgeLastElementIterator());
		assertFalse("not consistent", subgraph.isConsistentSnapShot());
		assertTrue("is induced", subgraph.isInducedSubgraph());
		assertTrue("has orphaned edges", subgraph.hasOrphanedEdges());
	}
	
}
