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

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.ed.inf.designbycontract.PreConditionException;
import uk.ac.ed.inf.graph.compound.CompoundNodePair;
import uk.ac.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.testfixture.ComplexGraphFixture;
import uk.ac.ed.inf.graph.compound.testfixture.ElementAttributeFactory;

@RunWith(JMock.class)
public class CompoundEdgeFactoryAttFactProhibitTest {
	private Mockery mockery;
	
	private ICompoundEdgeFactory testInstance ;
	private ComplexGraphFixture testFixture;
	private ElementAttributeFactory elementFactory;
	private CompoundNodePair expectedNodePair;

	@Before
	public void setUp() throws Exception {
		this.mockery = new JUnit4Mockery();
		this.testFixture = new ComplexGraphFixture(mockery, "");
		this.testFixture.buildFixture();
		
		testInstance = new CompoundEdgeFactory (this.testFixture.getGraph()) ;
		final ICompoundNode node3 = this.testFixture.getNode3();
		final ICompoundNode node5 = this.testFixture.getNode5();
		expectedNodePair = new CompoundNodePair(node3, node5);
		this.testInstance.setPair(expectedNodePair);
		this.elementFactory = new ElementAttributeFactory();
		this.testInstance.setAttributeFactory(this.elementFactory);
	}

	@After
	public void tearDown() throws Exception {
		this.mockery = null;
		this.testInstance = null;
		this.testFixture = null;
	}

	@Test
	public final void testGetCurrentNodePair(){
		assertEquals("expected node pair", this.expectedNodePair, this.testInstance.getCurrentNodePair());
	}

	@Test
	public final void testGetAttributeFactory(){
		assertEquals("expected att fact", this.elementFactory, this.testInstance.getAttributeFactory());
	}
	
	@Test
	public final void testCanCreateEdge(){
		assertFalse("can create edge", this.testInstance.canCreateEdge());
	}
	
	@Test(expected=PreConditionException.class)
	public final void testCreateEdge() {
		testInstance.createEdge();
	}

	@Test
	public final void testGetGraph() {
		assertEquals("get graph", this.testFixture.getGraph(), testInstance.getGraph()) ;
	}

}
