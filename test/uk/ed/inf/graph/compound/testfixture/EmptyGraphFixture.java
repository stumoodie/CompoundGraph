package uk.ed.inf.graph.compound.testfixture;

import org.jmock.Expectations;
import org.jmock.Mockery;

import uk.ed.inf.graph.compound.ICompoundGraph;
import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.IRootChildCompoundGraph;
import uk.ed.inf.graph.compound.IRootCompoundNode;

public class EmptyGraphFixture {
	public static final int EXPECTED_ROOT_IDX = 0;

	private final Mockery mockery;
	
	private IRootCompoundNode mockRootNode;
	private IRootChildCompoundGraph mockChildgraphRoot;
	private ICompoundGraph mockCompoundGraph;
	
	public EmptyGraphFixture(Mockery mockery){
		this.mockery = mockery;
	}
	
	public Mockery getMockery(){
		return this.mockery;
	}
	
	
	public void initialiseFixture(){
		mockCompoundGraph = this.mockery.mock(ICompoundGraph.class, "mockCompoundGraph");
		mockRootNode = this.mockery.mock(IRootCompoundNode.class, "mockRootNode");
		this.mockChildgraphRoot = this.mockery.mock(IRootChildCompoundGraph.class, "mockChildgraphRoot");

		this.mockery.checking(new Expectations(){{
			allowing(mockCompoundGraph).getRoot(); will(returnValue(mockRootNode));
			
			allowing(mockRootNode).getGraph(); will(returnValue(mockCompoundGraph));
			allowing(mockRootNode).getParent(); will(returnValue(mockRootNode));
			allowing(mockRootNode).getRoot(); will(returnValue(mockRootNode));
			allowing(mockRootNode).getIndex(); will(returnValue(EXPECTED_ROOT_IDX));
			allowing(mockRootNode).childIterator(); will(returnIterator(new ICompoundGraphElement[0]));
			allowing(mockRootNode).ancestorIterator(); will(returnIterator(mockRootNode));
			allowing(mockRootNode).preOrderIterator(); will(returnIterator(mockRootNode));
			allowing(mockRootNode).getOutDegree(); will(returnValue(0));
			allowing(mockRootNode).getInDegree(); will(returnValue(0));
			allowing(mockRootNode).getDegree(); will(returnValue(0));
			allowing(mockRootNode).getLevel(); will(returnValue(0));
		
			allowing(mockChildgraphRoot).getRoot(); will(returnValue(mockRootNode));
			allowing(mockChildgraphRoot).numElements(); will(returnValue(0));
			allowing(mockChildgraphRoot).numEdges(); will(returnValue(0));
			allowing(mockChildgraphRoot).numNodes(); will(returnValue(0));
			allowing(mockChildgraphRoot).getSuperGraph(); will(returnValue(mockCompoundGraph));
		
		}});
	}
	
	public ICompoundGraph getCompoundGraph(){
		return this.mockCompoundGraph;
	}
}
