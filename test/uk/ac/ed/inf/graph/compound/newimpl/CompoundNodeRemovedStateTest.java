package uk.ac.ed.inf.graph.compound.newimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundChildEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.ICompoundNodeFactory;
import uk.ac.ed.inf.graph.compound.testfixture.ComplexGraphFixture;
import uk.ac.ed.inf.graph.compound.testfixture.ElementAttribute;
import uk.ac.ed.inf.graph.compound.testfixture.INodeConstructor;

@RunWith(JMock.class)
public class CompoundNodeRemovedStateTest {
	private Mockery mockery;
	
	private ComplexGraphFixture testFixture;
	private ICompoundNode testInstance;
	private ComplexGraphFixture otherTestFixture;
	private ElementAttribute expectedAttribute;

	@Before
	public void setUp() throws Exception {
		this.mockery = new JUnit4Mockery();
		this.expectedAttribute = new ElementAttribute(ComplexGraphFixture.NODE1_ID);
		this.testFixture = new ComplexGraphFixture(this.mockery, "");
		this.testFixture.redefineNode(ComplexGraphFixture.NODE1_ID, new INodeConstructor() {
			
			@Override
			public ICompoundNodeFactory createNodeFactory(IChildCompoundGraph childGraph) {
				return childGraph.nodeFactory();
			}
			
			@Override
			public ICompoundNode createCompoundNode() {
				testInstance = new CompoundNode(testFixture.getRootNode(), ComplexGraphFixture.NODE1_IDX, expectedAttribute);
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
				node.addOutEdge(testFixture.getEdge1());
				return true;
			}
			
			@Override
			public boolean buildChildGraph(IChildCompoundGraph node) {
				node.addNode(testFixture.getNode2());
				return true;
			}

			@Override
			public boolean buildEdgeFactory(ICompoundEdgeFactory edgeFactory) {
				return false;
			}

			@Override
			public ICompoundChildEdgeFactory createEdgeFactory(IChildCompoundGraph childGraph) {
				return null;
			}
		});
		this.testFixture.buildFixture();
		
		this.otherTestFixture = new ComplexGraphFixture(this.mockery, "other_");
		this.otherTestFixture.buildFixture();
		this.expectedAttribute.setCurrentElement(this.otherTestFixture.getNode2());
		
		this.testInstance.markRemoved(true);
	}

	@After
	public void tearDown() throws Exception {
		this.mockery = null;
		this.expectedAttribute = null;
		this.testFixture = null;
		this.otherTestFixture = null;
		this.testInstance = null;
	}

	
	@Test
	public void testMarkRemovedTrue(){
		this.testInstance.markRemoved(false);
		assertFalse("node removed", this.testInstance.isRemoved());
		assertEquals("expected attribute", this.expectedAttribute, this.testInstance.getAttribute());
		assertEquals("expected current element", this.testInstance, this.expectedAttribute.getCurrentElement());
	}
	
	@Test
	public void testGetAttribute(){
		assertEquals("expected attribute", this.expectedAttribute, this.testInstance.getAttribute());
		assertEquals("expected current element", this.otherTestFixture.getNode2(), this.expectedAttribute.getCurrentElement());
	}
	
	@Test
	public void testIsRemoved() {
		assertTrue("remove", this.testInstance.isRemoved());
	}

}
