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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AdjListTest {
	private static final int EXPECTED_LARGE_CAPACITY = 200;
	private static final int TEST_NODE1_FIXTURE_IDX = 100;
	private static final int TEST_NODE2_FIXTURE_IDX = 0;
	private static final int TEST_NODE3_FIXTURE_IDX = 77;
	private static final int TEST_NODE_IDX = 10;
	private static final int TEST_EDGE3_FIXTURE_IDX = 0;
	private static final int TEST_EDGE1_IDX = 99;
//	private static final int TEST_EDGE2_IDX = -99;
	private AdjList testInstance;
	
	@Before
	public void setUp() throws Exception {
		this.testInstance = new AdjList();
		this.testInstance.addNode(TEST_NODE1_FIXTURE_IDX);
		this.testInstance.addNode(TEST_NODE2_FIXTURE_IDX);
		this.testInstance.addNode(TEST_NODE3_FIXTURE_IDX);
		this.testInstance.addEdge(TEST_EDGE3_FIXTURE_IDX, TEST_NODE1_FIXTURE_IDX, TEST_NODE2_FIXTURE_IDX);
	}

	@After
	public void tearDown() throws Exception {
		this.testInstance = null;
	}

	@Test
	public final void testAdjListInt() {
		new AdjList(EXPECTED_LARGE_CAPACITY);
	}

	@Test
	public final void testAddNode() {
		this.testInstance.addNode(TEST_NODE_IDX);
		assertTrue("node contained", this.testInstance.containsNode(TEST_NODE_IDX));
	}

	@Test
	public final void testContainsNode() {
		assertFalse("node not contained", this.testInstance.containsNode(TEST_NODE_IDX));
	}

	@Test
	public final void testAddEdge() {
		this.testInstance.addEdge(TEST_EDGE1_IDX, TEST_NODE1_FIXTURE_IDX, TEST_NODE2_FIXTURE_IDX);
	}

	@Test(expected=IllegalArgumentException.class)
	public final void testAddEdgeUncreatedNode() {
		this.testInstance.addEdge(TEST_EDGE1_IDX, TEST_NODE_IDX, TEST_NODE2_FIXTURE_IDX);
	}

	@Test
	public final void testIsConnected() {
		assertTrue("edge exists", this.testInstance.isConnected(TEST_NODE1_FIXTURE_IDX, TEST_NODE2_FIXTURE_IDX));
		assertTrue("edge exists", this.testInstance.isConnected(TEST_NODE2_FIXTURE_IDX, TEST_NODE1_FIXTURE_IDX));
		assertFalse("edge does not exist", this.testInstance.isConnected(TEST_NODE3_FIXTURE_IDX, TEST_NODE2_FIXTURE_IDX));
		assertFalse("edge does not exist", this.testInstance.isConnected(TEST_NODE2_FIXTURE_IDX, TEST_NODE3_FIXTURE_IDX));
		assertFalse("edge and node does not exist", this.testInstance.isConnected(TEST_NODE_IDX, TEST_NODE2_FIXTURE_IDX));
		assertFalse("edge and node does not exist", this.testInstance.isConnected(TEST_NODE1_FIXTURE_IDX, TEST_NODE_IDX));
	}

	@Test
	public final void testGetEdge() {
		int actualIdx = this.testInstance.getEdge(TEST_NODE1_FIXTURE_IDX, TEST_NODE2_FIXTURE_IDX);
		assertEquals("got expected edge", TEST_EDGE3_FIXTURE_IDX, actualIdx);
	}

	@Test(expected=IllegalArgumentException.class)
	public final void testGetEdgeNotExists() {
		this.testInstance.getEdge(TEST_NODE_IDX, TEST_NODE2_FIXTURE_IDX);
	}

}
