package uk.ac.ed.inf.graph.compound.testfixture;

import uk.ac.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundChildEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.ICompoundNodeFactory;

public interface INodeBuilder extends IGraphObjectBuilder {

	ICompoundNode getCompoundNode();
	
	IChildCompoundGraph getChildGraph();
	
	ICompoundNodeFactory getNodeFactory();

	ICompoundChildEdgeFactory getEdgeFactory();

}
