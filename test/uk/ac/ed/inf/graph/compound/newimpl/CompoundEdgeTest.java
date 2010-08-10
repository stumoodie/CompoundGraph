package uk.ac.ed.inf.graph.compound.newimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.ed.inf.graph.compound.CompoundNodePair;
import uk.ac.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.ICompoundNodeFactory;
import uk.ac.ed.inf.graph.compound.testfixture.ComplexGraphFixture;
import uk.ac.ed.inf.graph.compound.testfixture.ElementAttribute;
import uk.ac.ed.inf.graph.compound.testfixture.IEdgeConstructor;
import uk.ac.ed.inf.graph.compound.testfixture.IteratorTestUtility;
import uk.ac.ed.inf.tree.ITree;

@RunWith(JMock.class)
public class CompoundEdgeTest {
	private static final int EXPECTED_LEVEL = 2;

	private Mockery mockery;

	private ICompoundEdge testInstance;

	private ICompoundGraphElement mockParent;

	private ICompoundNode mockOutNode;

	private ICompoundNode mockInNode;

//	private ICompoundGraph mockGraph;

	private ICompoundNode mockOtherGraphOutNode;

	private ICompoundNode mockOtherGraphInNode;

	private ICompoundGraphElement mockOtherParent;

	private ComplexGraphFixture testFixture;

	private ComplexGraphFixture otherTestFixture;

	private ElementAttribute expectedElementAttribute;
	
	@Before
	public void setUp() throws Exception {
		this.mockery = new JUnit4Mockery();
		this.expectedElementAttribute = new ElementAttribute(ComplexGraphFixture.EDGE2_ID);
		this.testFixture = new ComplexGraphFixture(mockery, "");
		this.testFixture.redefineEdge(ComplexGraphFixture.EDGE2_ID, new IEdgeConstructor(){
			
			@Override
			public boolean buildChildGraph(IChildCompoundGraph childGraph) {
				childGraph.addNode(testFixture.getNode4());
				return true;
			}

			@Override
			public boolean buildEdge(final ICompoundEdge edge) {
				return true;
			}

			@Override
			public boolean buildEdgeFactory(ICompoundEdgeFactory edgeFactory) {
				return true;
			}

			@Override
			public boolean buildNodeFactory(ICompoundNodeFactory nodeFactory) {
				return true;
			}

			@Override
			public IChildCompoundGraph createCompoundChildGraph(ICompoundEdge edge) {
				return edge.getChildCompoundGraph();
			}

			@Override
			public ICompoundEdge createCompoundEdge() {
				final ICompoundNode node3 = testFixture.getNode3();
				final ICompoundNode node5 = testFixture.getNode5();
				final ICompoundEdge edge1 = testFixture.getEdge1();
				final ITree<ICompoundGraphElement> elementTree = testFixture.getGraph().getElementTree();
				mockery.checking(new Expectations(){{
					exactly(1).of(node3).addOutEdge(with(any(ICompoundEdge.class)));
					exactly(1).of(node5).addInEdge(with(any(ICompoundEdge.class)));
//					allowing(node3).addOutEdge(with(any(ICompoundEdge.class)));
					allowing(elementTree).getLowestCommonAncestor(node3, node5); will(returnValue(edge1));
					allowing(elementTree).getLowestCommonAncestor(node5, node3); will(returnValue(edge1));
				}});
				testInstance = new CompoundEdge(edge1, ComplexGraphFixture.EDGE2_IDX, expectedElementAttribute, node3, node5);
				return testInstance;
			}

			@Override
			public ICompoundEdgeFactory createEdgeFactory(IChildCompoundGraph childGraph) {
				return childGraph.edgeFactory();
			}

			@Override
			public ICompoundNodeFactory createNodeFactory(IChildCompoundGraph childGraph) {
				return childGraph.nodeFactory();
			}
		});

		this.testFixture.buildFixture();

		
		this.otherTestFixture = new ComplexGraphFixture(this.mockery, "other_");
		this.otherTestFixture.buildFixture();
		
		this.mockOtherParent = this.otherTestFixture.getEdge1();
		this.mockOtherGraphOutNode = this.otherTestFixture.getNode3();
		this.mockOtherGraphInNode = this.testFixture.getNode5();

		this.mockInNode = this.testFixture.getNode5();
		this.mockOutNode = this.testFixture.getNode3();
		this.mockParent = this.testFixture.getEdge1();
	}

	@After
	public void tearDown() throws Exception {
		this.mockery = null;
	}

	@Test
	public void testGetConnectedNodes() {
		CompoundNodePair actualPair = this.testInstance.getConnectedNodes();
		assertNotNull("pair exists", actualPair);
		assertEquals("exepcted in node", this.testFixture.getNode5(), actualPair.getInNode());
		assertEquals("exepcted out node", this.testFixture.getNode3(), actualPair.getOutNode());
	}

	@Test
	public void testGetIndex() {
		assertEquals("expected idx", ComplexGraphFixture.EDGE2_IDX, this.testInstance.getIndex());
	}

	@Test
	public void testHasDirectedEndsICompoundNodeICompoundNode() {
		assertTrue("ends present", this.testInstance.hasDirectedEnds(this.mockOutNode, this.mockInNode));
		assertFalse("ends not present", this.testInstance.hasDirectedEnds(this.mockInNode, this.mockOutNode));
		assertFalse("null ends not present", this.testInstance.hasDirectedEnds(null, null));
		assertFalse("ends present", this.testInstance.hasDirectedEnds(this.mockOtherGraphOutNode, this.mockInNode));
		assertFalse("ends present", this.testInstance.hasDirectedEnds(this.mockOtherGraphOutNode, this.mockOtherGraphInNode));
	}

	@Test
	public void testHasDirectedEndsCompoundNodePair() {
		assertTrue("ends present", this.testInstance.hasDirectedEnds(new CompoundNodePair(this.mockOutNode, this.mockInNode)));
		assertFalse("null ends not present", this.testInstance.hasDirectedEnds(null));
		assertFalse("reversed directed ends present", this.testInstance.hasDirectedEnds(new CompoundNodePair(this.mockInNode, this.mockOutNode)));
	}

	@Test
	public void testHasEndsICompoundNodeICompoundNode() {
		assertTrue("ends present", this.testInstance.hasEnds(this.mockOutNode, this.mockInNode));
		assertTrue("inveted ends present", this.testInstance.hasEnds(this.mockInNode, this.mockOutNode));
		assertFalse("null ends not present", this.testInstance.hasEnds(null, null));
		assertFalse("ends not present", this.testInstance.hasEnds(this.mockOtherGraphOutNode, this.mockInNode));
		assertFalse("ends not present", this.testInstance.hasEnds(this.mockOtherGraphOutNode, this.mockOtherGraphInNode));
	}

	@Test
	public void testHasEndsCompoundNodePair() {
		assertTrue("ends present", this.testInstance.hasEnds(new CompoundNodePair(this.mockOutNode, this.mockInNode)));
		assertFalse("null ends not present", this.testInstance.hasEnds(null));
		assertTrue("reversed directed ends present", this.testInstance.hasEnds(new CompoundNodePair(this.mockInNode, this.mockOutNode)));
	}

	@Test
	public void testIsSelfEdge() {
		assertFalse("not self edge", this.testInstance.isSelfEdge());
	}

	@Test
	public void testGetAttribute(){
		assertEquals("expected attribute", this.expectedElementAttribute, this.testInstance.getAttribute());
	}
	
	@Test
	public void testGetChildCompoundGraph() {
		assertNotNull("has compound graph", this.testInstance.getChildCompoundGraph());
	}

	@Test
	public void testGetGraph() {
		assertEquals("expected graph", this.testFixture.getGraph(), this.testInstance.getGraph());
	}

	@Test
	public void testIsAncestor() {
		assertTrue("is ancestor", this.testInstance.isAncestor(this.testFixture.getRootNode()));
		assertTrue("is ancestor", this.testInstance.isAncestor(this.testFixture.getEdge1()));
		assertFalse("not ancestor", this.testInstance.isAncestor(mockOtherParent));
		assertFalse("not ancestor", this.testInstance.isAncestor(null));
		assertFalse("not ancestor", this.testInstance.isAncestor(this.testInstance));
		assertFalse("not ancestor", this.testInstance.isAncestor(mockInNode));
	}

	@Test
	public void testIsDescendent() {
		assertFalse("is descendent", this.testInstance.isDescendent(mockParent));
		assertFalse("not descendent", this.testInstance.isDescendent(mockOtherParent));
		assertFalse("not descendent", this.testInstance.isDescendent(null));
		assertFalse("not descendent", this.testInstance.isDescendent(this.testInstance));
		assertFalse("not descendent", this.testInstance.isDescendent(mockInNode));
	}

	@Test
	public void testIsLink() {
		assertTrue("is edge", this.testInstance.isLink());
	}

	@Test
	public void testIsNode() {
		assertFalse("not node", this.testInstance.isNode());
	}

	@Test
	public void testIsRemoved() {
		assertFalse("not removed", this.testInstance.isRemoved());
	}

	@Test
	public void testMarkRemoved() {
		assertFalse("not removed", this.testInstance.isRemoved());
		this.testInstance.markRemoved(true);
		assertTrue("removed", this.testInstance.isRemoved());
	}

	@Test
	public void testCompareTo() {
		assertEquals("expected cmp", 1, this.testInstance.compareTo(mockParent));
		assertEquals("expected cmp", 1, this.testInstance.compareTo(mockInNode));
		assertEquals("expected cmp", -1, this.testInstance.compareTo(this.testFixture.getEdge4()));
		assertEquals("expected cmp", 0, this.testInstance.compareTo(testInstance));
	}

	@Test
	public void testAncestorIterator() {
//		ICompoundGraphElement expectedresults[] = new ICompoundGraphElement[] { this.testInstance, this.testFixture.getEdge1(), this.testFixture.getRootNode() };
//		List<ICompoundGraphElement> actualResults = new LinkedList<ICompoundGraphElement>();
//		Iterator<ICompoundGraphElement> iter = this.testInstance.ancestorIterator();
//		while(iter.hasNext()){
//			ICompoundGraphElement node = iter.next();
//			actualResults.add(node);
//		}
//		assertEquals("expected num ancestor nodes", expectedresults.length, actualResults.size());
//		int cntr = 0;
//		for(ICompoundGraphElement actualNode : actualResults){
//			assertEquals("expectedNodes", expectedresults[cntr++], actualNode);
//		}
		IteratorTestUtility<ICompoundGraphElement> testUtility = new IteratorTestUtility<ICompoundGraphElement>(this.testFixture.getEdge1(), this.testFixture.getRootNode());
		testUtility.testIterator(this.testInstance.ancestorIterator());
	}

	@Test
	public void testChildIterator() {
//		ICompoundGraphElement expectedresults[] = new ICompoundGraphElement[] { this.testFixture.getNode4() };
//		List<ICompoundGraphElement> actualResults = new LinkedList<ICompoundGraphElement>();
//		Iterator<ICompoundGraphElement> iter = this.testInstance.childIterator();
//		while(iter.hasNext()){
//			ICompoundGraphElement node = iter.next();
//			actualResults.add(node);
//		}
//		assertEquals("expected num children", expectedresults.length, actualResults.size());
//		int cntr = 0;
//		for(ICompoundGraphElement actualNode : actualResults){
//			assertEquals("expectedElements", expectedresults[cntr++], actualNode);
//		}
		IteratorTestUtility<ICompoundGraphElement> testUtility = new IteratorTestUtility<ICompoundGraphElement>(new ICompoundGraphElement[] { this.testFixture.getNode4() });
		testUtility.testIterator(this.testInstance.childIterator());
	}

	@Test
	public void testGetLevel() {
		assertEquals("expected level", EXPECTED_LEVEL, this.testInstance.getLevel());
	}

	@Test
	public void testGetParent() {
		assertEquals("expected parent", this.mockParent, this.testInstance.getParent());
	}

	@Test
	public void testGetRoot() {
		assertEquals("expected root", this.testFixture.getRootNode(), this.testInstance.getRoot());
	}

	@Test
	public void testIsChild() {
		assertTrue("is child", this.testInstance.isChild(this.testFixture.getNode4()));
		assertFalse("not child", this.testInstance.isChild(mockParent));
		assertFalse("null child", this.testInstance.isChild(null));
	}

	@Test
	public void testIsParent() {
		assertFalse("not parent", this.testInstance.isParent(mockInNode));
		assertTrue("parent", this.testInstance.isParent(mockParent));
		assertFalse("not parent", this.testInstance.isParent(mockOtherParent));
		assertFalse("null parent", this.testInstance.isParent(null));
	}

	@Test
	public void testLevelOrderIterator() {
		assertEquals("expected node", this.testInstance, this.testInstance.levelOrderIterator().next());
	}

	@Test
	public void testPreOrderIterator() {
//		ICompoundGraphElement expectedresults[] = new ICompoundGraphElement[] { this.testInstance, this.testFixture.getNode4() };
//		List<ICompoundGraphElement> actualResults = new LinkedList<ICompoundGraphElement>();
//		Iterator<ICompoundGraphElement> iter = this.testInstance.preOrderIterator();
//		while(iter.hasNext()){
//			ICompoundGraphElement node = iter.next();
//			actualResults.add(node);
//		}
//		assertEquals("expected num ancestor nodes", expectedresults.length, actualResults.size());
//		int cntr = 0;
//		for(ICompoundGraphElement actualNode : actualResults){
//			assertEquals("expectedNodes", expectedresults[cntr++], actualNode);
//		}
		IteratorTestUtility<ICompoundGraphElement> testUtility = new IteratorTestUtility<ICompoundGraphElement>(new ICompoundGraphElement[] { this.testInstance, this.testFixture.getNode4() });
		testUtility.testIterator(this.testInstance.preOrderIterator());
	}

}
