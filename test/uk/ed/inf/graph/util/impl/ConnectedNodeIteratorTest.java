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

import uk.ed.inf.graph.basic.IBasicEdge;
import uk.ed.inf.graph.basic.IBasicNode;
import uk.ed.inf.graph.basic.IBasicPair;


@RunWith(JMock.class)
public class ConnectedNodeIteratorTest {
	
	private Mockery mockery = new JUnit4Mockery();
	
	private ConnectedNodeIterator<TestNode, TestEdge> testBasicNodeIterator ;
	private TestNode mockNode ;
	private TestEdge mockEdge ;
	private TestNode mockNode2 ;
	private Iterator<TestEdge> mockEdgeIterator ;
	private IBasicPair<TestNode,TestEdge> mockPair ;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		mockNode = mockery.mock(TestNode.class , "mockNode") ;
		mockEdge = mockery.mock(TestEdge.class, "mocEdge");
		mockEdgeIterator = this.mockery.mock(Iterator.class, "mockEdgeIterator");
		mockNode2 = mockery.mock(TestNode.class, "mockNode2");
		mockPair = mockery.mock(IBasicPair.class , "mockPair") ;
		
		this.mockery.checking(new Expectations(){{
		}});
		
		testBasicNodeIterator = new ConnectedNodeIterator<TestNode, TestEdge> (mockNode, mockEdgeIterator) ;
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
		
		TestNode nextNode = testBasicNodeIterator.next() ;
		assertEquals ( "node" , mockNode2 , nextNode ) ;
		this.mockery.assertIsSatisfied();
	}

	@Test(expected=UnsupportedOperationException.class)
	public final void testRemove() {
		testBasicNodeIterator.remove() ;
	}
	
	private static interface TestNode extends IBasicNode<TestNode, TestEdge> { }
	
	private static interface TestEdge extends IBasicEdge<TestNode, TestEdge> {}

}
