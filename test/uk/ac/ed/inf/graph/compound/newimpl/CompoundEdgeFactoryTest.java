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
package uk.ac.ed.inf.graph.compound.newimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
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
import uk.ac.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ac.ed.inf.graph.compound.testfixture.ComplexGraphFixture;

@RunWith(JMock.class)
public class CompoundEdgeFactoryTest {
	private Mockery mockery;
	
	private ICompoundEdgeFactory testInstance ;

	private ComplexGraphFixture testFixture;

	private ComplexGraphFixture otherTestFixture;

	@Before
	public void setUp() throws Exception {
		this.mockery = new JUnit4Mockery();
		this.testFixture = new ComplexGraphFixture(mockery, "");
		this.testFixture.buildFixture();
		
		this.otherTestFixture = new ComplexGraphFixture(mockery, "other_");
		this.otherTestFixture.buildFixture();
		
		testInstance = new CompoundEdgeFactory (this.testFixture.getGraph()) ;
	}

	@After
	public void tearDown() throws Exception {
		this.mockery = null;
		this.testInstance = null;
		this.testFixture = null;
	}

	@Test
	public final void testSetPair() {
		CompoundNodePair expectedNodePair = new CompoundNodePair(this.testFixture.getNode3(),this.testFixture.getNode5());
		testInstance.setPair(expectedNodePair);
		assertEquals("expected pair", expectedNodePair, testInstance.getCurrentNodePair());
	}

	
	@Test(expected=PreConditionException.class)
	public final void testSetPairNull() {
		testInstance.setPair(null) ;
	}
	
	public final void testIsValidNodePair(){
		assertFalse("invalidNodePair", this.testInstance.isValidNodePair(null));
		CompoundNodePair expectedNodePair = new CompoundNodePair(this.testFixture.getNode3(),this.testFixture.getNode5());
		assertTrue("validNodePair", this.testInstance.isValidNodePair(expectedNodePair));
		CompoundNodePair expectedOtherNodePair = new CompoundNodePair(this.otherTestFixture.getNode3(),this.otherTestFixture.getNode5());
		assertFalse("invalidNodePair", this.testInstance.isValidNodePair(expectedOtherNodePair));
	}

	@Test(expected=PreConditionException.class)
	public final void testCreateEdge() {
		testInstance.createEdge();
	}

	@Test
	public final void testGetCurrentNodePair(){
		assertNull("node pair not set", this.testInstance.getCurrentNodePair());
	}

	@Test
	public final void testGetAttributeFactory(){
		assertNull("att fact not set", this.testInstance.getAttributeFactory());
	}
	
	@Test
	public final void testCanCreateEdge(){
		assertFalse("cannot create edge", this.testInstance.canCreateEdge());
	}
	
	@Test
	public final void testGetGraph() {
		assertEquals("get graph", this.testFixture.getGraph(), testInstance.getGraph()) ;
	}

}
