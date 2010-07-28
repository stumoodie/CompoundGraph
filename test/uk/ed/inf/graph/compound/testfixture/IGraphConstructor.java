package uk.ed.inf.graph.compound.testfixture;

import uk.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ed.inf.graph.compound.ICompoundGraph;
import uk.ed.inf.graph.compound.ICompoundNodeFactory;
import uk.ed.inf.graph.compound.IRootChildCompoundGraph;
import uk.ed.inf.graph.compound.IRootCompoundNode;

public interface IGraphConstructor {

	IRootChildCompoundGraph createChildGraph(IRootCompoundNode node);
	
	ICompoundNodeFactory createNodeFactory(ICompoundGraph childGraph);
	
	ICompoundEdgeFactory createEdgeFactory(ICompoundGraph childGraph);

	IRootCompoundNode createRootNode(ICompoundGraph graph);
	
	ICompoundGraph createGraph();
	
	boolean buildGraph(ICompoundGraph graph);
	
	boolean buildRootNode(IRootCompoundNode node);
	
	boolean buildChildGraph(IRootChildCompoundGraph node);
	
	boolean buildNodeFactory(ICompoundNodeFactory nodeFactory);
	
	boolean buildEdgeFactory(ICompoundEdgeFactory edgeFactory);
}
