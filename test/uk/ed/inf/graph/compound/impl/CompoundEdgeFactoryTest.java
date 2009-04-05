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
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class CompoundEdgeFactoryTest {
	
	private CompoundEdgeFactory testEdgeFactory ;
	
	private static CompoundGraph mockCompoundGraph ;
//	private static ChildCompoundGraph mockChildCompoundGraph ;
//	private static CompoundNode mockCompoundNode ;
	private static CompoundNode mockOneNode ;
	private static CompoundNode mockTwoNode ;
	private static CompoundNode otherOneNode ;
	private static CompoundNode otherTwoNode ;
	
	private static final int COMPOUND_NODE_INDEX_ONE = 1 ;
	private static final int COMPOUND_NODE_INDEX_TWO = 2 ;
	private static final int COMPOUND_NODE_INDEX_THREE = 3 ;
	private static final int COMPOUND_NODE_INDEX_FOUR = 2 ;
	private static final int COMPOUND_NODE_INDEX_FIVE = 3 ;

	@Before
	public void setUp() throws Exception {
		mockCompoundGraph = new CompoundGraph () ;
		mockOneNode = new CompoundNode ( mockCompoundGraph , COMPOUND_NODE_INDEX_TWO ) ;
		mockTwoNode = new CompoundNode ( mockCompoundGraph , COMPOUND_NODE_INDEX_THREE ) ;
//		mockCompoundNode = new CompoundNode ( mockCompoundGraph , COMPOUND_NODE_INDEX_ONE) ;
//		mockChildCompoundGraph = new ChildCompoundGraph (mockCompoundNode ) ;
		
		otherOneNode = new CompoundNode ( mockCompoundGraph , COMPOUND_NODE_INDEX_FOUR ) ;
		otherTwoNode = new CompoundNode ( mockCompoundGraph , COMPOUND_NODE_INDEX_FIVE ) ;
		
		testEdgeFactory = new CompoundEdgeFactory (mockCompoundGraph ) ;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Ignore @Test
	public final void testCiEdgeFactory() {
		fail("Not yet implemented"); // TODO
	}

	@Ignore @Test
	public final void testSetPair() {
		testEdgeFactory.setPair(otherOneNode, otherTwoNode) ;
		fail("Not yet implemented"); // TODO
	}

	@Ignore @Test
	public final void testCreateEdge() {
		this.testEdgeFactory.setPair(mockOneNode, mockTwoNode);
		CompoundEdge generatedEdge = testEdgeFactory.createEdge() ;//newEdge(mockChildCompoundGraph, COMPOUND_NODE_INDEX_ONE, mockOneNode, mockTwoNode) ;
		assertEquals ( "get graph" , mockCompoundGraph , generatedEdge.getGraph() ) ;
		assertEquals ( "get index" , COMPOUND_NODE_INDEX_ONE , generatedEdge.getIndex() ) ;
	}

	@Ignore @Test
	public final void testGetGraph() {
		assertEquals ( "get graph" , mockCompoundGraph , testEdgeFactory.getGraph()) ;
	}

}
