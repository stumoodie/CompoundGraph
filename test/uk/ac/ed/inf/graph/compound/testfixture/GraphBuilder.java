package uk.ac.ed.inf.graph.compound.testfixture;

import uk.ac.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundNodeFactory;
import uk.ac.ed.inf.graph.compound.IRootChildCompoundGraph;
import uk.ac.ed.inf.graph.compound.IRootCompoundNode;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraphFactory;

public class GraphBuilder implements IGraphBuilder {
	private ICompoundGraph graph;
	private IRootCompoundNode rootNode;
	private IRootChildCompoundGraph childGraph;
	private ICompoundNodeFactory nodeFactory;
	private ICompoundEdgeFactory edgeFactory;
	private ISubCompoundGraphFactory subgraphFactory;
	private final IGraphConstructor defaultConstructor;
	private IGraphConstructor overridingConstructor;
	private String elementId;
	
	public GraphBuilder(String elementId, IGraphConstructor defaultConstructor){
		this.elementId = elementId;
		this.defaultConstructor = defaultConstructor;
		this.overridingConstructor = null;
	}
	
	public void setGraphContructor(IGraphConstructor newConstructor){
		this.overridingConstructor = newConstructor;
	}
	
	public IGraphConstructor getGraphContructor(){
		return this.overridingConstructor;
	}
	
	@Override
	public final void create() {
		if(overridingConstructor == null || (graph = overridingConstructor.createGraph()) == null){
			graph = defaultConstructor.createGraph();
		}
		if(overridingConstructor == null || (rootNode = overridingConstructor.createRootNode(graph)) == null){
			rootNode = defaultConstructor.createRootNode(graph);
		}
		if(overridingConstructor == null || (childGraph = overridingConstructor.createChildGraph(rootNode)) == null){
			childGraph = defaultConstructor.createChildGraph(rootNode);
		}
		if(overridingConstructor == null || (nodeFactory = overridingConstructor.createNodeFactory(graph)) == null){
			nodeFactory = defaultConstructor.createNodeFactory(graph);
		}
		if(overridingConstructor == null || (edgeFactory = overridingConstructor.createEdgeFactory(graph)) == null){
			edgeFactory = defaultConstructor.createEdgeFactory(graph);
		}
		if(overridingConstructor == null || (subgraphFactory = overridingConstructor.createSubgraphFactory(graph)) == null){
			subgraphFactory = defaultConstructor.createSubgraphFactory(graph);
		}
	}


	@Override
	public IRootCompoundNode getRootNode() {
		return rootNode;
	}


	@Override
	public IRootChildCompoundGraph getChildGraph() {
		return childGraph;
	}


	@Override
	public ICompoundNodeFactory getNodeFactory() {
		return nodeFactory;
	}



	@Override
	public void buildGraphStructure() {
		if(overridingConstructor == null || !overridingConstructor.buildGraph(graph)){
			defaultConstructor.buildGraph(graph);
		}
		if(overridingConstructor == null || !overridingConstructor.buildRootNode(rootNode)){
			defaultConstructor.buildRootNode(rootNode);
		}
		if(overridingConstructor == null || !overridingConstructor.buildChildGraph(childGraph)){
			defaultConstructor.buildChildGraph(childGraph);
		}
		if(overridingConstructor == null || !overridingConstructor.buildNodeFactory(nodeFactory)){
			defaultConstructor.buildNodeFactory(nodeFactory);
		}
		if(overridingConstructor == null || !overridingConstructor.buildEdgeFactory(edgeFactory)){
			defaultConstructor.buildEdgeFactory(edgeFactory);
		}
		if(overridingConstructor == null || !overridingConstructor.buildSubgraphFactory(subgraphFactory)){
			defaultConstructor.buildSubgraphFactory(subgraphFactory);
		}
	}



	@Override
	public String getElementId() {
		return this.elementId;
	}

	@Override
	public ICompoundEdgeFactory getEdgeFactory() {
		return this.edgeFactory;
	}

	@Override
	public ICompoundGraph getGraph() {
		return this.graph;
	}

	@Override
	public ISubCompoundGraphFactory getSubgraphFactory() {
		return this.subgraphFactory;
	}

}
