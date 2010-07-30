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
package uk.ed.inf.graph.util.impl;

import static org.junit.Assert.assertTrue;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.newimpl.IteratorTestUtility;
import uk.ed.inf.graph.compound.testfixture.ComplexGraphFixture;


@RunWith(JMock.class)
public class ConnectedNodeIteratorTest {
	
	private Mockery mockery = new JUnit4Mockery();
	
	private ConnectedNodeIterator testBasicNodeIterator ;
    private ComplexGraphFixture testFixture;

	@Before
	public void setUp() throws Exception {
		this.testFixture = new ComplexGraphFixture(mockery, "");
		this.testFixture.doAll();
//		this.testFixture.createElements();
//		this.testFixture.buildObjects();
		testBasicNodeIterator = new ConnectedNodeIterator (this.testFixture.getNode2(), this.testFixture.getNode2().edgeIterator()) ;
	}

	@After
	public void tearDown() throws Exception {
		this.testFixture = null;
		this.testBasicNodeIterator = null;
	}

	@Test
	public final void testHasNext() {
		assertTrue ( "has next" , testBasicNodeIterator.hasNext()) ;
	}

	@Test
	public void testExpectedResults(){
		IteratorTestUtility<ICompoundNode> testIter = new IteratorTestUtility<ICompoundNode>(this.testFixture.getNode2(), this.testFixture.getNode3());
		testIter.testSortedIterator(this.testBasicNodeIterator);
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public final void testRemove() {
		testBasicNodeIterator.remove() ;
	}
	
}
