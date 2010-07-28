package uk.ed.inf.graph.compound.newimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.ICompoundNodeFactory;
import uk.ed.inf.graph.compound.testfixture.ComplexGraphFixture;
import uk.ed.inf.graph.compound.testfixture.IEdgeConstructor;
import uk.ed.inf.graph.compound.testfixture.INodeConstructor;

@RunWith(JMock.class)
public class CompoundNodeWithRemovedEdgeTest {
	private static final int EXPECTED_DEGREE = 2;
	private static final int EXPECTED_IN_DEGREE = 1;
	private static final int EXPECTED_OUT_DEGREE = 1;

	private Mockery mockery = new JUnit4Mockery();
	
	private ComplexGraphFixture testFixture;
	private ICompoundNode testInstance;

	@Before
	public void setUp() throws Exception {
		this.testFixture = new ComplexGraphFixture(this.mockery, "");
		this.testFixture.redefineNode(ComplexGraphFixture.NODE3_ID, new INodeConstructor() {
			
			@Override
			public ICompoundNodeFactory createNodeFactory(IChildCompoundGraph childGraph) {
				return childGraph.nodeFactory();
			}
			
			@Override
			public ICompoundNode createCompoundNode() {
				testInstance = new CompoundNode(testFixture.getEdge1(), ComplexGraphFixture.NODE3_IDX);
				return testInstance;
			}
			
			@Override
			public IChildCompoundGraph createCompoundChildGraph(ICompoundNode node) {
				return node.getChildCompoundGraph();
			}
			
			@Override
			public boolean buildNodeFactory(ICompoundNodeFactory nodeFactory) {
				return true;
			}
			
			@Override
			public boolean buildNode(ICompoundNode node) {
				node.addInEdge(testFixture.getEdge4());
				node.addOutEdge(testFixture.getEdge2());
				return true;
			}
			
			@Override
			public boolean buildChildGraph(IChildCompoundGraph node) {
				return true;
			}
		});
//		final ElementBuilder originalNode3Builder = this.testFixture.getElementBuilder("node3");
		this.testFixture.redefineEdge(ComplexGraphFixture.EDGE2_ID, new IEdgeConstructor() {
			
			@Override
			public ICompoundNodeFactory createNodeFactory(IChildCompoundGraph childGraph) {
				return null;
			}
			
			@Override
			public ICompoundEdgeFactory createEdgeFactory(IChildCompoundGraph childGraph) {
				return null;
			}
			
			@Override
			public ICompoundEdge createCompoundEdge() {
				return null;
			}
			
			@Override
			public IChildCompoundGraph createCompoundChildGraph(ICompoundEdge edge) {
				return null;
			}
			
			@Override
			public boolean buildNodeFactory(ICompoundNodeFactory nodeFactory) {
				return false;
			}
			
			@Override
			public boolean buildEdgeFactory(ICompoundEdgeFactory edgeFactory) {
				return false;
			}
			
			@Override
			public boolean buildEdge(final ICompoundEdge edge) {
				mockery.checking(new Expectations(){{
					allowing(edge).isRemoved(); will(returnValue(true));
				}});
				return false;
			}
			
			@Override
			public boolean buildChildGraph(IChildCompoundGraph childGraph) {
				return false;
			}
		});
//		final ElementBuilder originalEdge2Builder = this.testFixture.getElementBuilder("edge2");
		this.testFixture.redefineNode(ComplexGraphFixture.NODE4_ID, new INodeConstructor() {
			
			@Override
			public ICompoundNodeFactory createNodeFactory(IChildCompoundGraph childGraph) {
				return null;
			}
			
			@Override
			public ICompoundNode createCompoundNode() {
				return null;
			}
			
			@Override
			public IChildCompoundGraph createCompoundChildGraph(ICompoundNode node) {
				return null;
			}
			
			@Override
			public boolean buildNodeFactory(ICompoundNodeFactory nodeFactory) {
				return false;
			}
			
			@Override
			public boolean buildNode(final ICompoundNode node) {
				mockery.checking(new Expectations(){{
					allowing(node).isRemoved(); will(returnValue(true));
				}});
				return false;
			}
			
			@Override
			public boolean buildChildGraph(IChildCompoundGraph node) {
				return false;
			}
		});
//		final ElementBuilder originalNode4Builder = this.testFixture.getElementBuilder("node4");
//		ElementBuilder node3Builder = new ElementBuilder() {
//			
//			@Override
//			public String getElementId() {
//				return originalNode3Builder.getElementId();
//			}
//			
//			@Override
//			public void buildGraphStructure() {
//			}
//			
//			@Override
//			public ICompoundNode createElement() {
//				ICompoundNode retVal = new CompoundNode(testFixture.getEdge1(), ComplexGraphFixture.NODE3_IDX);
//				return retVal;
//			}
//			
//			@Override
//			public IChildCompoundGraph createChildGraph() {
//				return originalNode3Builder.createChildGraph();
//			}
//			
//			@Override
//			public void buildElement() {
//				
//			}
//			
//			@Override
//			public void buildChildGraph() {
//				originalNode3Builder.buildChildGraph();
//			}
//		};
//		ElementBuilder edge2Builder = new ElementBuilder() {
//			
//			@Override
//			public String getElementId() {
//				return originalEdge2Builder.getElementId();
//			}
//			
//			@Override
//			public void buildGraphStructure() {
//				originalEdge2Builder.buildGraphStructure();
//			}
//			
//			@Override
//			public ICompoundGraphElement createElement() {
//				return originalEdge2Builder.createElement();
//			}
//			
//			@Override
//			public IChildCompoundGraph createChildGraph() {
//				return originalEdge2Builder.createChildGraph();
//			}
//			
//			@Override
//			public void buildElement() {
//				mockery.checking(new Expectations(){{
//					allowing(getElement()).isRemoved(); will(returnValue(true));
//				}});
//				originalEdge2Builder.buildElement();
//			}
//			
//			@Override
//			public void buildChildGraph() {
//				originalEdge2Builder.buildChildGraph();
//			}
//		};
//		ElementBuilder node4Builder = new ElementBuilder() {
//			
//			@Override
//			public String getElementId() {
//				return originalNode4Builder.getElementId();
//			}
//			
//			@Override
//			public void buildGraphStructure() {
//				originalNode4Builder.buildGraphStructure();
//			}
//			
//			@Override
//			public ICompoundGraphElement createElement() {
//				return originalNode4Builder.createElement();
//			}
//			
//			@Override
//			public IChildCompoundGraph createChildGraph() {
//				return originalNode4Builder.createChildGraph();
//			}
//			
//			@Override
//			public void buildElement() {
//				mockery.checking(new Expectations(){{
//					allowing(getElement()).isRemoved(); will(returnValue(true));
//				}});
//				originalNode4Builder.buildElement();
//			}
//			
//			@Override
//			public void buildChildGraph() {
//				originalNode4Builder.buildChildGraph();
//			}
//		};
		this.testFixture.doAll();
//		this.testFixture.setBuilder(node4Builder);
//		this.testFixture.setBuilder(edge2Builder);
//		this.testFixture.setBuilder(node3Builder);
//		this.testFixture.setCreationDependencies(Arrays.asList(new String[]{"graph", "elementTree", "node1", "node2", "node4", "node5",
//				"edge1", "edge2", "edge3", "edge4"}));
//		this.testFixture.createElements();
//		this.testFixture.setBuildDependencies(Arrays.asList(new String[]{"graph", "edge1"}));
//		this.testFixture.buildTree();
//		this.testFixture.setCreationDependencies("node3");
//		this.testFixture.createElements();
//		this.testInstance = testFixture.getNode3();
//		this.testFixture.setBuildDependencies(Arrays.asList(new String[]{"elementTree", "node1", "node2", "node4", "node5",
//		"edge2", "edge3", "edge4", "node3" }));
//		this.testFixture.buildTree();
//		this.testFixture.setBuildDependencies(Arrays.asList(new String[]{"graph", "elementTree", "node1", "node2", "node3", "node4", "node5",
//				"edge1", "edge2", "edge3", "edge4" }));
//		this.testFixture.buildGraphStructure();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetParent() {
		assertEquals("Expected parent", this.testFixture.getEdge1(), this.testInstance.getParent());
	}

	@Test
	public void testConnectedNodeIterator() {
		IteratorTestUtility<ICompoundNode> testIter = new IteratorTestUtility<ICompoundNode>(this.testFixture.getNode1(), this.testFixture.getNode5());
		testIter.testIterator(this.testInstance.connectedNodeIterator());
	}

	@Test
	public void testEdgeIterator() {
		IteratorTestUtility<ICompoundEdge> testIter = new IteratorTestUtility<ICompoundEdge>(this.testFixture.getEdge2(), this.testFixture.getEdge4());
		testIter.testSortedIterator(this.testInstance.edgeIterator());
	}

	@Test
	public void testGetDegree() {
		assertEquals("expected degree", EXPECTED_DEGREE, this.testInstance.getDegree());
	}

	@Test
	public void testGetInDegree() {
		assertEquals("expected in degree", EXPECTED_IN_DEGREE, this.testInstance.getInDegree());
	}

	@Test
	public void testGetInEdgeIterator() {
		IteratorTestUtility<ICompoundEdge> testIter = new IteratorTestUtility<ICompoundEdge>(this.testFixture.getEdge4());
		testIter.testIterator(this.testInstance.getInEdgeIterator());
	}

	@Test
	public void testGetInNodeIterator() {
		IteratorTestUtility<ICompoundNode> testIter = new IteratorTestUtility<ICompoundNode>(this.testFixture.getNode1());
		testIter.testIterator(this.testInstance.getInNodeIterator());
	}

	@Test
	public void testGetIndex() {
		assertEquals("expected index", ComplexGraphFixture.NODE3_IDX, this.testInstance.getIndex());
	}

	@Test
	public void testGetOutDegree() {
		assertEquals("expected in degree", EXPECTED_OUT_DEGREE, this.testInstance.getOutDegree());
	}

	@Test
	public void testGetOutEdgeIterator() {
		IteratorTestUtility<ICompoundEdge> testIter = new IteratorTestUtility<ICompoundEdge>(this.testFixture.getEdge2());
		testIter.testIterator(this.testInstance.getOutEdgeIterator());
	}

	@Test
	public void testGetOutNodeIterator() {
		IteratorTestUtility<ICompoundNode> testIter = new IteratorTestUtility<ICompoundNode>(this.testFixture.getNode5());
		testIter.testIterator(this.testInstance.getOutNodeIterator());
	}

	@Test
	public void testHasEdgeWith() {
		assertTrue("contains in edge", this.testInstance.hasEdgeWith(this.testFixture.getNode1()));
		assertTrue("contains in edge", this.testInstance.hasEdgeWith(this.testFixture.getNode5()));
		assertFalse("does not contains other edge", this.testInstance.hasEdgeWith(this.testFixture.getNode4()));
	}

	@Test
	public void testHasInEdgeFrom() {
		assertTrue("contains in edge", this.testInstance.hasInEdgeFrom(this.testFixture.getNode1()));
	}

	@Test
	public void testHasOutEdgeTo() {
		assertTrue("contains out edge", this.testInstance.hasOutEdgeTo(this.testFixture.getNode5()));
		assertFalse("not contains out edge", this.testInstance.hasOutEdgeTo(this.testFixture.getNode1()));
		assertFalse("not contains out edge", this.testInstance.hasOutEdgeTo(this.testFixture.getNode4()));
	}

	@Test
	public void testAncestorIterator() {
		IteratorTestUtility<ICompoundGraphElement> testIter = new IteratorTestUtility<ICompoundGraphElement>(this.testInstance, this.testFixture.getEdge1(), this.testFixture.getRootNode());
		testIter.testIterator(this.testInstance.ancestorIterator());
	}

	@Test
	public void testChildIterator() {
		IteratorTestUtility<ICompoundGraphElement> testIter = new IteratorTestUtility<ICompoundGraphElement>();
		testIter.testIterator(this.testInstance.childIterator());
	}

	@Test
	public void testLevelOrderIterator() {
		IteratorTestUtility<ICompoundGraphElement> testIter = new IteratorTestUtility<ICompoundGraphElement>(this.testInstance);
		testIter.testIterator(this.testInstance.levelOrderIterator());
	}

	@Test
	public void testPreOrderIterator() {
		IteratorTestUtility<ICompoundGraphElement> testIter = new IteratorTestUtility<ICompoundGraphElement>(this.testInstance);
		testIter.testIterator(this.testInstance.preOrderIterator());
	}

	@Test
	public void testIsRemoved() {
		assertFalse("not remove", this.testInstance.isRemoved());
	}

	@Test
	public void testContainsEdge() {
		assertTrue("contains edge", this.testInstance.containsEdge(this.testFixture.getEdge4()));
		assertTrue("contains edge", this.testInstance.containsEdge(this.testFixture.getEdge2()));
		assertFalse("not contains edge", this.testInstance.containsEdge(this.testFixture.getEdge3()));
	}

	@Test
	public void testContainsInEdge() {
		assertTrue("contains in edge", this.testInstance.containsInEdge(this.testFixture.getEdge4()));
	}

	@Test
	public void testContainsOutEdge() {
		assertTrue("contains out edge", this.testInstance.containsOutEdge(this.testFixture.getEdge2()));
		assertFalse("not contains out edge", this.testInstance.containsOutEdge(this.testFixture.getEdge4()));
		assertFalse("not contains out edge", this.testInstance.containsOutEdge(this.testFixture.getEdge3()));
	}

}
