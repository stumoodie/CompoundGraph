package uk.ed.inf.graph.compound.testfixture;

import uk.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.ICompoundNodeFactory;

public interface INodeBuilder extends IGraphObjectBuilder {

	ICompoundNode getCompoundNode();
	
	IChildCompoundGraph getChildGraph();
	
	ICompoundNodeFactory getNodeFactory();

}
