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
package uk.ac.ed.inf.graph.util.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Iterator;

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

import uk.ac.ed.inf.graph.compound.base.BaseCompoundEdge;
import uk.ac.ed.inf.graph.compound.base.BaseCompoundNode;
import uk.ac.ed.inf.graph.compound.impl.CompoundGraph;
import uk.ac.ed.inf.graph.util.IFilterCriteria;
import uk.ac.ed.inf.graph.util.INodeSet;
import uk.ac.ed.inf.graph.util.impl.FilteredNodeSet;
import uk.ac.ed.inf.graph.util.impl.NodeSet;

@RunWith(JMock.class)
public class FilteredNodeSetTest {
	
	
	private Mockery mockery = new JUnit4Mockery() {{
		setImposteriser(ClassImposteriser.INSTANCE);
	}};
	
	private FilteredNodeSet<BaseCompoundNode , BaseCompoundEdge> testFilteredNodeSet ;
	private INodeSet<BaseCompoundNode , BaseCompoundEdge> mockNodeSet ;
//	private List<BaseCompoundNode> nodeSetList = new ArrayList<BaseCompoundNode> () ;
	private CompoundGraph graph ;
	BaseCompoundNode mockBasicNode ;
	IFilterCriteria<BaseCompoundNode> mockFilterCriteria ;
	
	private static final int [] NUMERIC = {0,1,2,3,4,5} ;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		
		graph = new CompoundGraph () ;
		
		mockBasicNode = graph.nodeFactory().createNode() ;
		mockNodeSet = new NodeSet<BaseCompoundNode , BaseCompoundEdge> () ;
		mockFilterCriteria = mockery.mock(IFilterCriteria.class, "mockFilterCriteria") ;
		
		testFilteredNodeSet = new FilteredNodeSet<BaseCompoundNode , BaseCompoundEdge> (mockNodeSet, mockFilterCriteria) ;
		
		testFilteredNodeSet.add(mockBasicNode) ;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Ignore @Test
	public final void testFilteredNodeSet() {
		
		fail("Not yet implemented"); // TODO
	}

	@Ignore @Test
	public final void testAdd() {
		final BaseCompoundNode mockBasicNode2 = graph.nodeFactory().createNode() ;
		testFilteredNodeSet.add(mockBasicNode2) ;
		
		mockery.checking(new Expectations () {{ 
			atLeast(1).of(mockFilterCriteria).matched(mockBasicNode2) ; returnValue(true) ;
			atLeast(1).of(mockFilterCriteria).matched(mockBasicNode) ; returnValue(true) ;
		}});
		
		
		
		assertEquals ( "has nodes" , NUMERIC[2] , testFilteredNodeSet.size() );
	}

	@Ignore @Test
	public final void testAddAll() {
		fail("Not yet implemented"); // TODO
	}

	@Ignore @Test
	public final void testClear() {
		fail("Not yet implemented"); // TODO
	}

	@Ignore @Test
	public final void testComparator() {
		fail("Not yet implemented"); // TODO
	}

	@Ignore @Test
	public final void testGet() {
		assertEquals ( "get Node" , mockBasicNode , testFilteredNodeSet.get(1) );
	}

	@Ignore @Test
	public final void testRemove() {
		fail("Not yet implemented"); // TODO
	}

	@Ignore @Test
	public final void testRemoveAll() {
		fail("Not yet implemented"); // TODO
	}

	@Ignore @Test
	public final void testRetainAll() {
		fail("Not yet implemented"); // TODO
	}

	@Ignore @Test
	public final void testToString() {
		fail("Not yet implemented"); // TODO
	}

	@Ignore @Test
	public final void testContains() {
		fail("Not yet implemented"); // TODO
	}

	@Ignore @Test
	public final void testContainsAll() {
		fail("Not yet implemented"); // TODO
	}

	@Ignore @Test
	public final void testIsEmpty() {
		fail("Not yet implemented"); // TODO
	}

	@Ignore @Test
	public final void testIterator() {
		BaseCompoundNode [] nodeArray = { mockBasicNode } ;
		Iterator<BaseCompoundNode> nodeIterator = testFilteredNodeSet.iterator() ;
		
		int counter = 0 ;
		while ( nodeIterator.hasNext())
		{
			assertEquals ( "same node" , nodeArray[counter] , nodeIterator.next()) ;
			counter++ ;
		}
		
		assertEquals ( "one node" , NUMERIC[1] , counter) ;
	}

	@Ignore @Test
	public final void testSize() {
		fail("Not yet implemented"); // TODO
	}

	@Ignore @Test
	public final void testToArray() {
		fail("Not yet implemented"); // TODO
	}

	@Ignore @Test
	public final void testToArrayTArray() {
		fail("Not yet implemented"); // TODO
	}

}