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

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.ICompoundNodeFactory;
import uk.ed.inf.graph.compound.testfixture.ComplexGraphFixture;
import uk.ed.inf.graph.compound.testfixture.INodeConstructor;

@RunWith(JMock.class)
public class CompoundNodeFactoryTest {
	private Mockery mockery;
	private ComplexGraphFixture testFixture;
	private ICompoundNodeFactory testInstance;
	
	@Before
	public void setUp() throws Exception {
		this.mockery = new JUnit4Mockery();
		this.testFixture = new ComplexGraphFixture(mockery, "");
		this.testFixture.redefineNode(ComplexGraphFixture.NODE1_ID, new INodeConstructor() {
			
			@Override
			public ICompoundNodeFactory createNodeFactory(IChildCompoundGraph childGraph) {
				testInstance = new CompoundNodeFactory(childGraph.getRoot());
				return testInstance;
			}
			
			@Override
			public ICompoundNode createCompoundNode() {
				return null;
			}
			
			@Override
			public IChildCompoundGraph createCompoundChildGraph(ICompoundNode node) {
				return null;
			}
			
			@Override
			public boolean buildNodeFactory(ICompoundNodeFactory nodeFactory) {
				return true;
			}
			
			@Override
			public boolean buildNode(ICompoundNode node) {
				return false;
			}
			
			@Override
			public boolean buildChildGraph(IChildCompoundGraph node) {
				return false;
			}
		});
		this.testFixture.doAll();
//		this.testFixture.createElements();
//		this.testFixture.buildObjects();
//		this.testInstance = 
	}

	@After
	public void tearDown() throws Exception {
		this.mockery = null;
		this.testFixture = null;
	}

	@Test
	public void testGetGraph(){
		assertEquals("correct graph", this.testFixture.getGraph(), this.testInstance.getGraph());
	}
	
	@Test
	public void testGetParent(){
		assertEquals("correct parent", this.testFixture.getNode1(), this.testInstance.getParentNode());
	}
	
	@Test
	public final void testCreateNode() {
		mockery.checking(new Expectations(){{
			one(testInstance.getParentNode().getChildCompoundGraph()).addNode(with(any(ICompoundNode.class)));
		}});
		ICompoundNode newNode = this.testInstance.createNode();
		assertEquals("expected parent", this.testFixture.getNode1(), newNode.getParent());
		assertEquals("expected graph", this.testFixture.getGraph(), newNode.getGraph());
	}

}
