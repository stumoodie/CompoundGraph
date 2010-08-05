package uk.ed.inf.graph.compound.testfixture;

import org.jmock.api.Action;

import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundGraph;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.IRootCompoundNode;

public interface IGraphTestFixture {

	void redefineNode(String id, INodeConstructor nodeConstructor);
	
	void redefineEdge(String id, IEdgeConstructor edgeConstructor);
	
	void redefineGraph(IGraphConstructor graphConstructor);
	
	ICompoundGraph getGraph();
	
	ICompoundNode getNode(String nodeId);
	
	ICompoundEdge getEdge(String edgeId);
	
	void doAll();

	void setElementRemoved(String elementId, boolean markRemoved);

	boolean isRemoved(String elementId);

	IRootCompoundNode getRootNode();

	Action setRemovalState(String elementId);
}
