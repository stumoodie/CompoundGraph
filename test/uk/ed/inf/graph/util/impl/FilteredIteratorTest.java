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


import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.testfixture.ComplexGraphFixture;
import uk.ed.inf.graph.compound.testfixture.IGraphTestFixture;
import uk.ed.inf.graph.compound.testfixture.IteratorTestUtility;
import uk.ed.inf.graph.util.IFilterCriteria;

@RunWith(JMock.class)
public class FilteredIteratorTest {
	
	private Mockery mockery;
	private FilteredIterator<ICompoundNode> testInstance;
	private IGraphTestFixture testFixture;
	
	@Before
	public void setUp() throws Exception {
		 mockery = new JUnit4Mockery();
		 this.testFixture = new ComplexGraphFixture(mockery, "");
		 this.testFixture.setElementRemoved(ComplexGraphFixture.NODE4_ID, true);
		 this.testFixture.setElementRemoved(ComplexGraphFixture.NODE2_ID, true);
		 this.testFixture.setElementRemoved(ComplexGraphFixture.EDGE3_ID, true);
		 this.testFixture.setElementRemoved(ComplexGraphFixture.NODE4_ID, true);
		 this.testFixture.buildFixture();
		 this.testInstance = new FilteredIterator<ICompoundNode>(this.testFixture.getGraph().nodeIterator(), new IFilterCriteria<ICompoundNode>() {

			@Override
			public boolean matched(ICompoundNode testObj) {
				return !testObj.isRemoved();
			}
		});
	}

	@After
	public void tearDown() throws Exception {
		this.testInstance = null;
		this.mockery = null;
		this.testFixture = null;
	}

	
	@Test
	public void testFullContents(){
		IteratorTestUtility<ICompoundNode> testIterator = new IteratorTestUtility<ICompoundNode>(this.testFixture.getRootNode(),
				this.testFixture.getNode(ComplexGraphFixture.NODE1_ID),this.testFixture.getNode(ComplexGraphFixture.NODE3_ID),
				this.testFixture.getNode(ComplexGraphFixture.NODE5_ID),this.testFixture.getNode(ComplexGraphFixture.NODE6_ID));
		testIterator.testSortedIterator(testInstance);
	}
	

	@Test(expected=IllegalArgumentException.class)
	public void testNullIterator(){
		this.testInstance = new FilteredIterator<ICompoundNode>(null, null);
	}

	@Test(expected=UnsupportedOperationException.class)
	public void testRemove(){
		this.testInstance.remove();
	}
}
