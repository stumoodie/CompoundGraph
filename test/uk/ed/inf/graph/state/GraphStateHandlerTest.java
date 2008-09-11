package uk.ed.inf.graph.state;

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

import uk.ed.inf.bitstring.IBitString;
import uk.ed.inf.graph.basic.IBasicEdge;
import uk.ed.inf.graph.basic.IBasicGraph;
import uk.ed.inf.graph.basic.IBasicNode;
@RunWith(JMock.class)
public class GraphStateHandlerTest {
	
	private Mockery mockery = new JUnit4Mockery() {{
		 setImposteriser(ClassImposteriser.INSTANCE);
	}};
	
	private GraphStateHandler testGraphStateHandler ;
	private IBasicGraph mockBasicGraph ;
	private IBasicNode mockNode ;
	private IBasicEdge mockE ;
	
	private List nodeIterator = new ArrayList () ;
	private List edgeIterator = new ArrayList () ;
	
	private static final int [] NUMERIC = {0,1,2,3,4,5} ;

	@Before
	public void setUp() throws Exception {
		mockBasicGraph = mockery.mock(IBasicGraph.class , "mockBasicGraph");

		
		testGraphStateHandler = new GraphStateHandler (mockBasicGraph) ;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testGraphStateHandler() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetGraph() {
		assertEquals ( "get graph" , mockBasicGraph , testGraphStateHandler.getGraph()) ;
	}

	@Test
	public final void testCreateGraphState() {
		
		this.mockery.checking(new Expectations(){{
			atLeast(1).of(mockBasicGraph).nodeIterator() ; returnIterator(nodeIterator) ;
			atLeast(1).of(mockBasicGraph).edgeIterator() ; returnIterator(edgeIterator) ;
		}});
		
		IGraphState graphState = testGraphStateHandler.createGraphState() ;
		assertEquals ( "get graph" , mockBasicGraph , graphState.getGraph()) ;
		assertEquals ( "edgeStates"  , NUMERIC[0] , graphState.getEdgeStates().cardinality() ) ;
		assertEquals ( "nodeStates"  , NUMERIC[0] , graphState.getNodeStates().cardinality() ) ;
	}

	@Test
	public final void testRestoreState() {
		fail("Not yet implemented"); // TODO  how??	
	}

}
