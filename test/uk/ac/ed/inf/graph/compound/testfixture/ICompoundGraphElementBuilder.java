package uk.ac.ed.inf.graph.compound.testfixture;

import uk.ac.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ICompoundNodeFactory;

public interface ICompoundGraphElementBuilder extends IGraphObjectBuilder {

	ICompoundGraphElement getCompoundGraphElement();
	
	IChildCompoundGraph getChildGraph();
	
	ICompoundNodeFactory getNodeFactory();

	ICompoundEdgeFactory getEdgeFactory();

}
