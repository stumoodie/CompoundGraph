package uk.ed.inf.graph.compound.testfixture;

import uk.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ed.inf.graph.compound.ICompoundGraph;
import uk.ed.inf.graph.compound.ICompoundNodeFactory;
import uk.ed.inf.graph.compound.IRootChildCompoundGraph;
import uk.ed.inf.graph.compound.IRootCompoundNode;
import uk.ed.inf.graph.compound.ISubCompoundGraphFactory;

public interface IGraphBuilder extends IGraphObjectBuilder {

	ICompoundGraph getGraph();
	
	IRootCompoundNode getRootNode();
	
	IRootChildCompoundGraph getChildGraph();
	
	ICompoundNodeFactory getNodeFactory();

	ICompoundEdgeFactory getEdgeFactory();

	ISubCompoundGraphFactory getSubgraphFactory();
}
