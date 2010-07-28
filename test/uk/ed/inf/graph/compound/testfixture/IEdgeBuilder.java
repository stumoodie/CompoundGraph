package uk.ed.inf.graph.compound.testfixture;

import uk.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ed.inf.graph.compound.ICompoundNodeFactory;

public interface IEdgeBuilder extends IGraphObjectBuilder {

	ICompoundEdge getCompoundEdge();
	
	IChildCompoundGraph getChildGraph();
	
	ICompoundNodeFactory getNodeFactory();

	ICompoundEdgeFactory getEdgeFactory();
}
