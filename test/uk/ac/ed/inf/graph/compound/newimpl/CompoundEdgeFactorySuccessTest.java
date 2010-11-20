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

import uk.ac.ed.inf.graph.compound.CompoundNodePair;
import uk.ac.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.IGraphStructureChangeAction;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraphFactory;
import uk.ac.ed.inf.graph.compound.testfixture.ComplexGraphFixture;
import uk.ac.ed.inf.graph.compound.testfixture.ElementAttributeFactory;

@RunWith(JMock.class)
public class CompoundEdgeFactorySuccessTest {
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
		this.elementFactory.setName("testElement");
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
		assertTrue("can create edge", this.testInstance.canCreateEdge());
	}
	
	@Test
	public final void testCreateEdge() {
		final IChildCompoundGraph edge1ChildGraph = this.testFixture.getEdge(ComplexGraphFixture.EDGE1_ID).getChildCompoundGraph();
		final ICompoundNode node3 = this.testFixture.getNode3();
		final ICompoundNode node5 = this.testFixture.getNode5();
		final ISubCompoundGraphFactory mockFact = testFixture.getGraph().subgraphFactory();
		mockery.checking(new Expectations(){{
			exactly(1).of(node3).addOutEdge(with(any(ICompoundEdge.class)));
			exactly(1).of(node5).addInEdge(with(any(ICompoundEdge.class)));
			exactly(1).of(edge1ChildGraph).addEdge(with(any(ICompoundEdge.class)));

			one(mockFact).addElement(with(any(ICompoundEdge.class)));
			one(testFixture.getGraph()).notifyGraphStructureChange(with(any(IGraphStructureChangeAction.class)));
		}});
		ICompoundEdge generatedEdge = testInstance.createEdge();
		assertNotNull("expected edge" , generatedEdge) ;
		assertEquals("expected att links", generatedEdge, generatedEdge.getAttribute().getCurrentElement());
		mockery.assertIsSatisfied();
	}

	@Test
	public final void testGetGraph() {
		assertEquals("get graph", this.testFixture.getGraph(), testInstance.getGraph()) ;
	}

}
