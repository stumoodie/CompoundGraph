package uk.ed.inf.graph.compound.newimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.ed.inf.designbycontract.PreConditionException;
import uk.ed.inf.graph.compound.ICompoundGraphCopyBuilder;
import uk.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ed.inf.graph.compound.testfixture.ComplexGraphFixture;

@RunWith(JMock.class)
public class CompoundGraphCopyBuilderWithNoSrcTest {

	private Mockery mockery;

	private ICompoundGraphCopyBuilder testInstance;
	private ComplexGraphFixture testFixture;
	private ComplexGraphFixture destnFixture;
	private ISubCompoundGraph mockSrcSubgraph;
	
	@Before
	public void setUp() throws Exception {
		this.mockery = new JUnit4Mockery();

		this.testFixture = new ComplexGraphFixture(mockery, "");
		this.testFixture.buildFixture();
		// src graph
		this.mockSrcSubgraph = this.mockery.mock(ISubCompoundGraph.class, "mockSrcSubgraph");

		// dstn graph
		this.destnFixture = new ComplexGraphFixture(mockery, "destn");
		this.destnFixture.buildFixture();
		
		this.mockery.checking(new Expectations(){{
			allowing(mockSrcSubgraph).getSuperGraph(); will(returnValue(testFixture.getGraph()));
			allowing(mockSrcSubgraph).elementIterator(); will(returnIterator(testFixture.getNode1(), testFixture.getNode2(), testFixture.getEdge2(), testFixture.getNode3(), testFixture.getNode4(), testFixture.getNode5()));
			allowing(mockSrcSubgraph).edgeLastElementIterator(); will(returnIterator(testFixture.getNode1(), testFixture.getNode2(), testFixture.getNode3(), testFixture.getNode4(), testFixture.getNode5(), testFixture.getEdge2()));
			allowing(mockSrcSubgraph).isInducedSubgraph(); will(returnValue(true));
			allowing(mockSrcSubgraph).isConsistentSnapShot(); will(returnValue(true));
			allowing(mockSrcSubgraph).containsRoot(); will(returnValue(false));
		}});
		
		this.testInstance = new CompoundGraphCopyBuilder(this.destnFixture.getGraph().getRoot().getChildCompoundGraph());
	}

	@After
	public void tearDown() throws Exception {
		this.mockery = null;
		this.testInstance = null;
	}

	@Test
	public void testGetCopiedComponents() {
		assertNull("not copied contents", this.testInstance.getCopiedComponents());
	}

	@Test
	public void testGetDestinationChildGraph() {
		assertEquals("expected childGraph", this.destnFixture.getGraph().getRoot().getChildCompoundGraph(), this.testInstance.getDestinationChildGraph());
	}

	@Test
	public void testGetSourceSubgraph() {
		assertNull("expected subgraph", this.testInstance.getSourceSubgraph());
	}

	@Test(expected=PreConditionException.class)
	public void testMakeCopy(){
		this.testInstance.makeCopy();
		
	}
	
	@Test
	public void testCanCopyHere(){
		assertFalse("cannot copy", this.testInstance.canCopyHere());
	}
}
