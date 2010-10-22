package uk.ac.ed.inf.graph.compound.testfixture;

import uk.ac.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundNodeFactory;

public interface IEdgeConstructor {

	IChildCompoundGraph createCompoundChildGraph(ICompoundEdge edge);
	
	ICompoundNodeFactory createNodeFactory(IChildCompoundGraph childGraph);
	
	ICompoundEdgeFactory createEdgeFactory(IChildCompoundGraph childGraph);
	
	ICompoundEdge createCompoundEdge();
	
	boolean buildEdge(ICompoundEdge edge);
	
	boolean buildChildGraph(IChildCompoundGraph childGraph);
	
	boolean buildNodeFactory(ICompoundNodeFactory nodeFactory);
	
	boolean buildEdgeFactory(ICompoundEdgeFactory edgeFactory);
}
