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
package uk.ed.inf.graph.impl;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EdgeFactoryTest {
	
	private EdgeFactory testEdgeFactory ;
	private static Graph mockGraph ;
	
	private Node oneNode ;
	private Node twoNode ;
	
	private static final int [] INDEX = { 1 , 2 , 3 , 4 , 5 } ;
//	private static final int [] NUMERIC = {0,1,2,3,4,5} ;

	@Before
	public void setUp() throws Exception {
		
		mockGraph = new Graph () ;
		testEdgeFactory = new EdgeFactory (mockGraph) ;
		
		oneNode = new Node (mockGraph , INDEX[0]) ;
		twoNode = new Node (mockGraph , INDEX[1]) ;
		
		testEdgeFactory.setPair(oneNode, twoNode) ;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testSetPair() {
		NodePair anEdgePair = testEdgeFactory.createEdge().getConnectedNodes() ;
		assertEquals ( "oneNode" , oneNode , anEdgePair.getOneNode()) ;
		assertEquals ( "oneNode" , twoNode , anEdgePair.getTwoNode()) ;
	}

	@Test
	public final void testCreateEdge() {
		Edge createdEdge = testEdgeFactory.createEdge() ;
		assertEquals ( "same graph" , mockGraph , createdEdge.getGraph()) ;
	}

	@Test
	public final void testGetGraph() {
		assertEquals ( "get graph" , mockGraph , testEdgeFactory.getGraph()) ;
	}

}
