package uk.ac.ed.inf.graph.compound.testfixture;

import uk.ac.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundNodeFactory;
import uk.ac.ed.inf.graph.compound.IRootChildCompoundGraph;
import uk.ac.ed.inf.graph.compound.IRootCompoundNode;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraphFactory;

public interface IGraphBuilder extends IGraphObjectBuilder {

	ICompoundGraph getGraph();
	
	IRootCompoundNode getRootNode();
	
	IRootChildCompoundGraph getChildGraph();
	
	ICompoundNodeFactory getNodeFactory();

	ICompoundEdgeFactory getEdgeFactory();

	ISubCompoundGraphFactory getSubgraphFactory();
}
