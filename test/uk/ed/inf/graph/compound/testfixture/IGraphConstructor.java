package uk.ed.inf.graph.compound.testfixture;

import uk.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ed.inf.graph.compound.ICompoundGraph;
import uk.ed.inf.graph.compound.ICompoundNodeFactory;
import uk.ed.inf.graph.compound.IRootChildCompoundGraph;
import uk.ed.inf.graph.compound.IRootCompoundNode;
import uk.ed.inf.graph.compound.ISubCompoundGraphFactory;

public interface IGraphConstructor {

	IRootChildCompoundGraph createChildGraph(IRootCompoundNode node);
	
	ICompoundNodeFactory createNodeFactory(ICompoundGraph graph);
	
	ICompoundEdgeFactory createEdgeFactory(ICompoundGraph graph);

	ISubCompoundGraphFactory createSubgraphFactory(ICompoundGraph graph);

	IRootCompoundNode createRootNode(ICompoundGraph graph);
	
	ICompoundGraph createGraph();
	
	boolean buildGraph(ICompoundGraph graph);
	
	boolean buildRootNode(IRootCompoundNode node);
	
	boolean buildChildGraph(IRootChildCompoundGraph childGraph);
	
	boolean buildNodeFactory(ICompoundNodeFactory nodeFactory);
	
	boolean buildEdgeFactory(ICompoundEdgeFactory edgeFactory);

	boolean buildSubgraphFactory(ISubCompoundGraphFactory subgraphFactory);
}
