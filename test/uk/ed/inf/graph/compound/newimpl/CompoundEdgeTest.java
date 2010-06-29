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

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ed.inf.graph.compound.ICompoundGraph;
import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.ICompoundNode;

@RunWith(JMock.class)
public class CompoundEdgeTest {
	private Mockery mockery = new JUnit4Mockery();

	private static int EXPECTED_EDGE_IDX = 10;
	private static int EXPECTED_EDGE1_IDX = 1;
	private static int EXPECTED_EDGE2_IDX = 100;
	private static int EXPECTED_IN_NODE_IDX = 1;
	private static int EXPECTED_OUT_NODE_IDX = 1;
	private CompoundEdge testInstance;

	private ICompoundGraphElement mockParent;

	private IChildCompoundGraph mockSubgraph;

	private ICompoundGraph mockGraph;

	private ICompoundNode mockInNode;

	private ICompoundNode mockOutNode;
	
	@Before
	public void setUp() throws Exception {
		mockParent = this.mockery.mock(ICompoundGraphElement.class, "mockParent");
		mockSubgraph = this.mockery.mock(IChildCompoundGraph.class, "mockSubgraph");
		mockGraph = this.mockery.mock(ICompoundGraph.class, "mockGraph");
		mockInNode = this.mockery.mock(ICompoundNode.class, "mockInNode");
		mockOutNode = this.mockery.mock(ICompoundNode.class, "mockOutNode");
		this.mockery.checking(new Expectations(){{
			allowing(mockInNode).getIndex(); will(returnValue(EXPECTED_IN_NODE_IDX));
//			atLeast(1).of(mockInNode).addInEdge(with(any(CompoundEdge.class)));

			allowing(mockOutNode).getIndex(); will(returnValue(EXPECTED_OUT_NODE_IDX));
//			atLeast(1).of(mockOutNode).addOutEdge(with(any(CompoundEdge.class)));
			
			atLeast(1).of(mockSubgraph).getSuperGraph(); will(returnValue(mockGraph));
		}});
		this.testInstance = new CompoundEdge(mockParent, EXPECTED_EDGE_IDX, mockOutNode, mockInNode);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Ignore @Test
	public final void testCiEdge() {
		assertEquals("expected in node", mockInNode, this.testInstance.getConnectedNodes().getInNode());
		assertEquals("expected out node", mockOutNode, this.testInstance.getConnectedNodes().getOutNode());
		assertEquals("expected edge idx", EXPECTED_EDGE_IDX, this.testInstance.getIndex());
		assertEquals("expected subgraph", mockSubgraph, this.testInstance.getChildCompoundGraph());
		assertEquals("expected graph", mockGraph, this.testInstance.getGraph());
		this.mockery.assertIsSatisfied();
	}

	@Ignore @Test
	public final void testCompareTo() {
		this.mockery.assertIsSatisfied();
		final CompoundEdge mockEdge1 = this.mockery.mock(CompoundEdge.class, "mockEdge1");
		final CompoundEdge mockEdge2 = this.mockery.mock(CompoundEdge.class, "mockEdge2");
		this.mockery.checking(new Expectations(){{
			allowing(mockEdge1).getIndex(); will(returnValue(EXPECTED_EDGE1_IDX));

			allowing(mockEdge2).getIndex(); will(returnValue(EXPECTED_EDGE2_IDX));
		}});
		assertEquals("compare less than", 1, this.testInstance.compareTo(mockEdge1));
		assertEquals("compare less than", 0, this.testInstance.compareTo(this.testInstance));
		assertEquals("compare less than", -1, this.testInstance.compareTo(mockEdge2));
		this.mockery.assertIsSatisfied();
	}
}
