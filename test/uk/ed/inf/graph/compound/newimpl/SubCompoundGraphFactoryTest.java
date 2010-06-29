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
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
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

import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundGraph;
import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ed.inf.graph.compound.newimpl.SubCompoundGraphFactory;

@RunWith(JMock.class)
public class SubCompoundGraphFactoryTest {
	private Mockery mockery = new JUnit4Mockery() {{
		setImposteriser(ClassImposteriser.INSTANCE);
	}};

	private SubCompoundGraphFactory testSubCompoundGraphFactory ;
	private ICompoundGraph mockCompoundGraph ;
	private ICompoundNode mockCompoundNode ;
	private ICompoundEdge mockCompoundEdge ;
	private ICompoundNode mockCompoundNode2 ;
	private ICompoundEdge mockCompoundEdge2 ;
	private List<ICompoundGraphElement> expectedElements;
	
	private static final int EXPECTED_COMPOUND_NODE_IDX = 1;
	private static final int EXPECTED_COMPOUND_NODE_LEVEL = 1;
	private static final int EXPECTED_COMPOUND_NODE2_IDX = 3;
	private static final int EXPECTED_COMPOUND_NODE2_LEVEL = 1;
	private static final int EXPECTED_COMPOUND_EDGE_IDX = 2;
	private static final int EXPECTED_COMPOUND_EDGE_LEVEL = 1;
	private static final int EXPECTED_COMPOUND_EDGE2_IDX = 4;
	private static final int EXPECTED_COMPOUND_EDGE2_LEVEL = 1;

	private static final int EXPECTED_TOP_SUBGRAPH_ELEMENTS = 2;
	private static final int EXPECTED_SUBGRAPH_ELEMENTS = 2;
	
	
	@Before
	public void setUp() throws Exception {
		
		mockCompoundGraph = mockery.mock(ICompoundGraph.class , "mockCompoundGraph");
		mockCompoundNode = mockery.mock(ICompoundNode.class , "mockCompoundNode") ;
		mockCompoundEdge = mockery.mock(ICompoundEdge.class , "mockCompoundEdge") ;
		mockCompoundNode2 = mockery.mock(ICompoundNode.class , "mockCompoundNode2") ;
		mockCompoundEdge2 = mockery.mock(ICompoundEdge.class , "mockCompoundEdge2") ;
		
		testSubCompoundGraphFactory = new SubCompoundGraphFactory (mockCompoundGraph) ;
		
		this.mockery.checking(new Expectations(){{
			allowing(mockCompoundNode).getIndex(); will(returnValue(EXPECTED_COMPOUND_NODE_IDX));
			allowing(mockCompoundNode).getLevel(); will(returnValue(EXPECTED_COMPOUND_NODE_LEVEL));
			
			allowing(mockCompoundNode2).getIndex(); will(returnValue(EXPECTED_COMPOUND_NODE2_IDX));
			allowing(mockCompoundNode2).getLevel(); will(returnValue(EXPECTED_COMPOUND_NODE2_LEVEL));

			allowing(mockCompoundEdge).getIndex(); will(returnValue(EXPECTED_COMPOUND_EDGE_IDX));
			allowing(mockCompoundEdge).getLevel(); will(returnValue(EXPECTED_COMPOUND_EDGE_LEVEL));

			allowing(mockCompoundEdge2).getIndex(); will(returnValue(EXPECTED_COMPOUND_EDGE2_IDX));
			allowing(mockCompoundEdge2).getLevel(); will(returnValue(EXPECTED_COMPOUND_EDGE2_LEVEL));
		}});
 
		testSubCompoundGraphFactory.addElement(mockCompoundNode ) ;
		testSubCompoundGraphFactory.addElement(mockCompoundEdge) ;
		this.expectedElements = new LinkedList<ICompoundGraphElement>(Arrays.asList(new ICompoundGraphElement[] { this.mockCompoundNode, this.mockCompoundEdge }));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testAddElement() {
		testSubCompoundGraphFactory.addElement(mockCompoundNode2) ; 
		testSubCompoundGraphFactory.addElement(mockCompoundEdge2) ; 
		
		assertEquals ( "correct size" , 4 , this.testSubCompoundGraphFactory.numElements()) ;

	}

	@Test
	public final void testElementIterator() {
		
		Iterator<ICompoundGraphElement> nodeIterator = testSubCompoundGraphFactory.elementIterator() ;
		
		while ( nodeIterator.hasNext()){
			assertTrue ( "same Element" , this.expectedElements.remove(nodeIterator.next()));
		}
		this.mockery.assertIsSatisfied();
	}

	@Test
	public final void testCreateSubgraph() {
		ISubCompoundGraph generatedSubGraph = testSubCompoundGraphFactory.createSubgraph() ;
		
		assertTrue("edge found", generatedSubGraph.containsEdge(mockCompoundEdge));
		assertTrue("node found", generatedSubGraph.containsNode(mockCompoundNode));
		assertEquals("expected top elements", EXPECTED_TOP_SUBGRAPH_ELEMENTS, generatedSubGraph.numTopElements());
		assertEquals("expected elemenbt", EXPECTED_TOP_SUBGRAPH_ELEMENTS, generatedSubGraph.numElements());
	}

}
