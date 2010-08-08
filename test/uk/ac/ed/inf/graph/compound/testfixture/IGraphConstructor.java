package uk.ac.ed.inf.graph.compound.testfixture;

import uk.ac.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundNodeFactory;
import uk.ac.ed.inf.graph.compound.IRootChildCompoundGraph;
import uk.ac.ed.inf.graph.compound.IRootCompoundNode;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraphFactory;

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
