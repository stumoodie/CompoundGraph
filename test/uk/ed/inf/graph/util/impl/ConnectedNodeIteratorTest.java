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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.ICompoundNodePair;


@RunWith(JMock.class)
public class ConnectedNodeIteratorTest {
	
	private Mockery mockery = new JUnit4Mockery();
	
	private ConnectedNodeIterator testBasicNodeIterator ;
	private ICompoundNode mockNode ;
	private ICompoundEdge mockEdge ;
	private ICompoundNode mockNode2 ;
	private Iterator<ICompoundEdge> mockEdgeIterator ;
	private ICompoundNodePair mockPair ;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		mockNode = mockery.mock(ICompoundNode.class , "mockNode") ;
		mockEdge = mockery.mock(ICompoundEdge.class, "mocEdge");
		mockEdgeIterator = this.mockery.mock(Iterator.class, "mockEdgeIterator");
		mockNode2 = mockery.mock(ICompoundNode.class, "mockNode2");
		mockPair = mockery.mock(ICompoundNodePair.class , "mockPair") ;
		
		this.mockery.checking(new Expectations(){{
		}});
		
		testBasicNodeIterator = new ConnectedNodeIterator (mockNode, mockEdgeIterator) ;
		this.mockery.assertIsSatisfied();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testHasNext() {
		this.mockery.checking(new Expectations(){{
//			atLeast(1).of(mockEdge).getConnectedNodes() ; will(returnValue(mockPair)) ;
			
			oneOf(mockEdgeIterator).hasNext(); will(returnValue(true));
			oneOf(mockEdgeIterator).hasNext(); will(returnValue(false));
		}});
		
		assertTrue ( "has next" , testBasicNodeIterator.hasNext()) ;
		assertTrue ( "iter exhausted" , !testBasicNodeIterator.hasNext()) ;
		this.mockery.assertIsSatisfied();
	}

	@Test
	public final void testNext() {
		this.mockery.checking(new Expectations(){{
			atLeast(1).of(mockEdge).getConnectedNodes() ; will(returnValue(mockPair)) ;
			atLeast(1).of(mockPair).getOtherNode(mockNode); will(returnValue(mockNode2));
			
			oneOf(mockEdgeIterator).next(); will(returnValue(mockEdge));
			
		}});
		
		ICompoundNode nextNode = testBasicNodeIterator.next() ;
		assertEquals ( "node" , mockNode2 , nextNode ) ;
		this.mockery.assertIsSatisfied();
	}

	@Test(expected=UnsupportedOperationException.class)
	public final void testRemove() {
		testBasicNodeIterator.remove() ;
	}
	
}
