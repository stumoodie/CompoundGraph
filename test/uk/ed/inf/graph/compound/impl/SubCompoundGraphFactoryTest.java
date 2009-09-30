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
package uk.ed.inf.graph.compound.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ed.inf.graph.compound.base.BaseCompoundEdge;
import uk.ed.inf.graph.compound.base.BaseCompoundNode;

@RunWith(JMock.class)
public class SubCompoundGraphFactoryTest {
	private Mockery mockery = new JUnit4Mockery() {{
		setImposteriser(ClassImposteriser.INSTANCE);
	}};

	private SubCompoundGraphFactory testSubCompoundGraphFactory ;
	private CompoundGraph mockCompoundGraph ;
	private CompoundNode mockCompoundNode ;
	private CompoundEdge mockCompoundEdge ;
	private CompoundNode mockCompoundNode2 ;
	private CompoundEdge mockCompoundEdge2 ;
	
	private static final int [] NUMERICAL = { 0,1,2,3,4,5} ;
	
	
	@Before
	public void setUp() throws Exception {
		
		mockCompoundGraph = mockery.mock(CompoundGraph.class , "mockCompoundGraph");
		mockCompoundNode = mockery.mock(CompoundNode.class , "mockCompoundNode") ;
		mockCompoundEdge = mockery.mock(CompoundEdge.class , "mockCompoundEdge") ;
		mockCompoundNode2 = mockery.mock(CompoundNode.class , "mockCompoundNode2") ;
		mockCompoundEdge2 = mockery.mock(CompoundEdge.class , "mockCompoundEdge2") ;
		
		testSubCompoundGraphFactory = new SubCompoundGraphFactory (mockCompoundGraph) ;
		
		testSubCompoundGraphFactory.addNode(mockCompoundNode ) ;
		testSubCompoundGraphFactory.addEdge(mockCompoundEdge) ;
		
		this.mockery.checking(new Expectations(){{
			
		}});
 
	}

	@After
	public void tearDown() throws Exception {
	}

	@Ignore @Test
	public final void testSubCigraphFactory() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testAddNode() {

		List<CompoundNode> nodeList = new ArrayList<CompoundNode> () ; 
		
		nodeList.add(mockCompoundNode) ;
		nodeList.add(mockCompoundNode2) ;
		
		testSubCompoundGraphFactory.addEdge(mockCompoundEdge2) ; 
		
		testSubCompoundGraphFactory.addNode(mockCompoundNode2) ;
		
		Iterator<BaseCompoundNode> nodeIterator = testSubCompoundGraphFactory.nodeIterator() ;
		
		int counter = 0 ;
		while ( nodeIterator.hasNext())
		{
			assertTrue ( "same Node" , nodeList.contains(nodeIterator.next())) ;
			counter++ ;
		}
		
		assertEquals ( "correct size" , NUMERICAL[2] , counter  ) ;
	}

	@Test
	public final void testAddEdge() {
		List<CompoundEdge> edgeList = new ArrayList<CompoundEdge> () ; 
		
		edgeList.add(mockCompoundEdge) ;
		edgeList.add(mockCompoundEdge2) ;
		
		testSubCompoundGraphFactory.addEdge(mockCompoundEdge2) ; 
		
		Iterator<BaseCompoundEdge> edgeIterator = testSubCompoundGraphFactory.edgeIterator() ;
		
		int counter = 0 ;
		while ( edgeIterator.hasNext())
		{
			assertTrue ( "same Edge" , edgeList.contains(edgeIterator.next())) ;
			counter++ ;
		}
		assertEquals ( "correct size" , NUMERICAL[2] , counter  ) ;

	}

	@Test
	public final void testNodeIterator() {
		CompoundNode nodeArray [] = { mockCompoundNode } ;
		
		Iterator<BaseCompoundNode> nodeIterator = testSubCompoundGraphFactory.nodeIterator() ;
		
		int counter = 0 ;
		while ( nodeIterator.hasNext())
		{
			assertEquals ( "same Node" , nodeArray[counter] , nodeIterator.next()) ;
			counter++ ;
		}
	}

	@Test
	public final void testEdgeIterator() {
		CompoundEdge edgeArray [] = { mockCompoundEdge } ;
		
		Iterator<BaseCompoundEdge> edgeIterator = testSubCompoundGraphFactory.edgeIterator() ;
		
		int counter = 0 ;
		while ( edgeIterator.hasNext())
		{
			assertEquals ( "same Edge" , edgeArray[counter] , edgeIterator.next()) ;
			counter++ ;
		}
	}

	@Ignore @Test
	public final void testCreateSubgraph() {
		SubCompoundGraph generatedSubGraph = testSubCompoundGraphFactory.createSubgraph() ;
		
		generatedSubGraph.containsEdge(mockCompoundEdge) ;
		generatedSubGraph.containsNode(mockCompoundNode) ;
		
	}

	@Ignore @Test
	public final void testCreateInducedSubgraph() {
		fail("Not yet implemented"); // TODO
	}

}
