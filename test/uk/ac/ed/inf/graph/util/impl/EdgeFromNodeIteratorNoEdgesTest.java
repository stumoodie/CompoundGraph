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
package uk.ac.ed.inf.graph.util.impl;

import java.util.Iterator;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.testfixture.ComplexGraphFixture;
import uk.ac.ed.inf.graph.compound.testfixture.IGraphTestFixture;
import uk.ac.ed.inf.graph.compound.testfixture.IteratorTestUtility;
import uk.ac.ed.inf.graph.util.impl.EdgeFromNodeIterator;

@RunWith(JMock.class)
public class EdgeFromNodeIteratorNoEdgesTest {
	private Mockery mockery;
	private IGraphTestFixture testFixture;
	private Iterator<ICompoundEdge> testInstance;
	

	@Before
	public void setUp() throws Exception {
		mockery = new JUnit4Mockery();
		this.testFixture = new ComplexGraphFixture(mockery, "");
		this.testFixture.buildFixture();
		
		this.testInstance = new EdgeFromNodeIterator<ICompoundNode, ICompoundEdge>(this.testFixture.getEdge(ComplexGraphFixture.EDGE2_ID).getChildCompoundGraph().nodeIterator());
	}

	@After
	public void tearDown() throws Exception {
		this.mockery = null;
		this.testFixture = null;
	}

	@Test(expected=IllegalArgumentException.class)
	public final void testEdgeIteratorCollectionOfQextendsINodeNullCollection() {
		this.testInstance = new EdgeFromNodeIterator<ICompoundNode, ICompoundEdge>(null);
	}

	@Test
	public final void testEdgeIteratorContents() {
		IteratorTestUtility<ICompoundEdge> testIterator = new IteratorTestUtility<ICompoundEdge>();
		testIterator.testSortedIterator(this.testInstance);
	}

	
}