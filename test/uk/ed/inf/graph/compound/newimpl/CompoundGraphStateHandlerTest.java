package uk.ed.inf.graph.compound.newimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ed.inf.bitstring.BitStringBuffer;
import uk.ed.inf.bitstring.IBitString;
import uk.ed.inf.graph.compound.testfixture.ComplexGraphFixture;
import uk.ed.inf.graph.compound.testfixture.IGraphTestFixture;
import uk.ed.inf.graph.state.IGraphState;
import uk.ed.inf.graph.state.IGraphStateHandler;

@RunWith(JMock.class)
public class CompoundGraphStateHandlerTest {
	private Mockery mockery;
	private IGraphStateHandler testInstance;
	private IGraphTestFixture testFixture;
	private String expectedBitString = "{0, 1, 2, 3, 4, 5, 8, 9, 10}";
	private IGraphState mockState;
	private IBitString restoreBitString;
	
	
	@Before
	public void setUp() throws Exception {
		this.mockery = new JUnit4Mockery();
		this.testFixture = new ComplexGraphFixture(mockery, "");
		this.testFixture.setElementRemoved(ComplexGraphFixture.NODE6_ID, true);
		this.testFixture.setElementRemoved(ComplexGraphFixture.EDGE1_ID, true);
		this.testFixture.doAll();
		
		this.testInstance = new CompoundGraphStateHandler(this.testFixture.getGraph());
		this.mockState = this.mockery.mock(IGraphState.class, "mockState");
		int bitStringSize = this.testFixture.getGraph().numElements();
		BitStringBuffer buf = new BitStringBuffer(bitStringSize);
		buf.set(0, bitStringSize-1, true);
		this.restoreBitString = buf.toBitString();
		this.mockery.checking(new Expectations(){{
			allowing(mockState).getGraph(); will(returnValue(testFixture.getGraph()));
			allowing(mockState).getElementStates(); will(returnValue(restoreBitString));
		}});
	}

	@After
	public void tearDown() throws Exception {
		this.mockery = null;
		this.testFixture = null;
		this.testInstance = null;
		this.mockState = null;
	}

	@Test
	public void testCompoundGraphStateHandler() {
		new CompoundGraphStateHandler(null);
	}

	@Test
	public void testCreateGraphState() {
		IGraphState actualResult = this.testInstance.createGraphState();
		assertNotNull("expected not null", actualResult);
		assertEquals("expected bit string", expectedBitString , actualResult.getElementStates().toString());
	}

	@Test
	public void testGetGraph() {
		assertEquals("expected graph", this.testFixture.getGraph(), this.testInstance.getGraph());
	}

	@Test
	public void testRestoreState() {
		this.testInstance.restoreState(mockState);
	}

	@Test(expected=NullPointerException.class)
	public void testRestoreStateIsNull() {
		this.testInstance.restoreState(null);
	}

}
