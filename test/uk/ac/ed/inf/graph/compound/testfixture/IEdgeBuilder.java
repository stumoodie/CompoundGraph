package uk.ac.ed.inf.graph.compound.testfixture;

import uk.ac.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundNodeFactory;

public interface IEdgeBuilder extends IGraphObjectBuilder {

	ICompoundEdge getCompoundEdge();
	
	IChildCompoundGraph getChildGraph();
	
	ICompoundNodeFactory getNodeFactory();

	ICompoundEdgeFactory getEdgeFactory();
}
