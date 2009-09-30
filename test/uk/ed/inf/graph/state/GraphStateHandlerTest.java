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
package uk.ed.inf.graph.state;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ed.inf.graph.basic.IBasicEdge;
import uk.ed.inf.graph.basic.IBasicGraph;
import uk.ed.inf.graph.basic.IBasicNode;
@RunWith(JMock.class)
public class GraphStateHandlerTest {
	
	private Mockery mockery = new JUnit4Mockery() {{
		 setImposteriser(ClassImposteriser.INSTANCE);
	}};
	
	private GraphStateHandler<TestNode, TestEdge> testGraphStateHandler ;
	private IBasicGraph<TestNode, TestEdge> mockBasicGraph ;
//	private TestNode mockNode ;
//	private TestEdge mockE ;
	
	private List<TestNode> nodeIterator = new ArrayList<TestNode> () ;
	private List<TestEdge> edgeIterator = new ArrayList<TestEdge> () ;
	
	private static final int [] NUMERIC = {0,1,2,3,4,5} ;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		mockBasicGraph = mockery.mock(IBasicGraph.class , "mockBasicGraph");

		
		testGraphStateHandler = new GraphStateHandler<TestNode, TestEdge> (mockBasicGraph) ;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Ignore @Test
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
		
		IGraphState<TestNode, TestEdge> graphState = testGraphStateHandler.createGraphState() ;
		assertEquals ( "get graph" , mockBasicGraph , graphState.getGraph()) ;
		assertEquals ( "edgeStates"  , NUMERIC[0] , graphState.getEdgeStates().cardinality() ) ;
		assertEquals ( "nodeStates"  , NUMERIC[0] , graphState.getNodeStates().cardinality() ) ;
	}

	@Ignore @Test
	public final void testRestoreState() {
		fail("Not yet implemented"); // TODO  how??	
	}

	private static interface TestNode extends IRestorableGraphElement, IBasicNode<TestNode, TestEdge> {}
	
	private static interface TestEdge extends IRestorableGraphElement, IBasicEdge<TestNode, TestEdge> {}
	
}
