package uk.ac.ed.inf.graph.compound.testfixture;

import uk.ac.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundChildEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.ICompoundNodeFactory;

public interface INodeConstructor {

	IChildCompoundGraph createCompoundChildGraph(ICompoundNode node);
	
	ICompoundNodeFactory createNodeFactory(IChildCompoundGraph childGraph);

	ICompoundChildEdgeFactory createEdgeFactory(IChildCompoundGraph childGraph);
	
	ICompoundNode createCompoundNode();
	
	boolean buildNode(ICompoundNode node);
	
	boolean buildChildGraph(IChildCompoundGraph node);
	
	boolean buildNodeFactory(ICompoundNodeFactory nodeFactory);
	
	boolean buildEdgeFactory(ICompoundEdgeFactory edgeFactory);
}
