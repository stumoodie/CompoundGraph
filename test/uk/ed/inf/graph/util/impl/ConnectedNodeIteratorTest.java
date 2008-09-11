package uk.ed.inf.graph.util.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ed.inf.graph.basic.IBasicEdge;
import uk.ed.inf.graph.basic.IBasicNode;
import uk.ed.inf.graph.basic.IBasicPair;
import uk.ed.inf.graph.impl.Edge;
import uk.ed.inf.graph.impl.Node;


@RunWith(JMock.class)
public class ConnectedNodeIteratorTest {
	
	private Mockery mockery = new JUnit4Mockery() {{
		setImposteriser(ClassImposteriser.INSTANCE);
	}};
	
	private ConnectedNodeIterator<TestNode, TestEdge> testBasicNodeIterator ;
	private TestNode mockNode ;
	private TestEdge mockEdge ;
	private TestEdge mockEdge2 ;
	private Iterator<TestEdge> mockEdgeIterator ;
	private IBasicPair<TestNode,TestEdge> mockPair ;

	@Before
	public void setUp() throws Exception {

		
		List<TestEdge> aList = new ArrayList <TestEdge> ();
		mockEdge = mockery.mock(TestEdge.class , "mockEdge") ;
		mockEdge2 = mockery.mock(TestEdge.class , "mockEdge2") ;
		
		
		aList.add(mockEdge) ;
		aList.add(mockEdge2) ;
		
		mockNode = mockery.mock(TestNode.class , "mockNode") ;
		mockEdgeIterator = aList.iterator() ;
		
		mockPair = mockery.mock(IBasicPair.class , "mockPair") ;
		
		this.mockery.checking(new Expectations(){{
//			atLeast(1).of(mockEdge).getConnectedNodes() ; returnValue(mockPair) ;
		}});
		
		testBasicNodeIterator = new ConnectedNodeIterator<TestNode, TestEdge> (mockNode, mockEdgeIterator) ;
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testHasNext() {
		assertTrue ( "has next" , testBasicNodeIterator.hasNext()) ;
	}

	@Test
	public final void testNext() {
		TestNode nextNode = testBasicNodeIterator.next() ;
		assertEquals ( "node" , mockNode , nextNode ) ;
	}

	@Test(expected=UnsupportedOperationException.class)
	public final void testRemove() {
		testBasicNodeIterator.remove() ;
	}
	
	private static abstract class TestNode implements IBasicNode<TestNode, TestEdge> { }
	
	private static abstract class TestEdge implements IBasicEdge<TestNode, TestEdge> {}

}
