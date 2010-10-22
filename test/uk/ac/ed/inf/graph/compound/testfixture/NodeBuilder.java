package uk.ac.ed.inf.graph.compound.testfixture;

import uk.ac.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundChildEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.ICompoundNodeFactory;

public class NodeBuilder implements INodeBuilder {
	private ICompoundNode node;
	private IChildCompoundGraph childGraph;
	private ICompoundNodeFactory nodeFactory;
	private ICompoundChildEdgeFactory edgeFactory;
	private final INodeConstructor defaultConstructor;
	private INodeConstructor nodeConstructor;
	private String elementId;
	
	public NodeBuilder(String elementId, INodeConstructor defaultConstructor){
		this.elementId = elementId;
		this.defaultConstructor = defaultConstructor;
		this.nodeConstructor = null;
	}
	
	public void setNodeContructor(INodeConstructor newConstructor){
		this.nodeConstructor = newConstructor;
	}
	
	public INodeConstructor getNodeContructor(){
		return this.nodeConstructor;
	}
	
	@Override
	public final void create() {
		if(nodeConstructor == null || (node = nodeConstructor.createCompoundNode()) == null){
			node = defaultConstructor.createCompoundNode();
		}
		if(nodeConstructor == null || (childGraph = nodeConstructor.createCompoundChildGraph(node)) == null){
			childGraph = defaultConstructor.createCompoundChildGraph(node);
		}
		if(nodeConstructor == null || (nodeFactory = nodeConstructor.createNodeFactory(childGraph)) == null){
			nodeFactory = defaultConstructor.createNodeFactory(childGraph);
		}
		if(nodeConstructor == null || (edgeFactory = nodeConstructor.createEdgeFactory(childGraph)) == null){
			edgeFactory = defaultConstructor.createEdgeFactory(childGraph);
		}
	}


	@Override
	public ICompoundNode getCompoundNode() {
		return node;
	}


	@Override
	public IChildCompoundGraph getChildGraph() {
		return childGraph;
	}


	@Override
	public ICompoundNodeFactory getNodeFactory() {
		return nodeFactory;
	}



	@Override
	public void buildGraphStructure() {
		if(nodeConstructor == null || !nodeConstructor.buildNode(node)){
			defaultConstructor.buildNode(node);
		}
		if(nodeConstructor == null || !nodeConstructor.buildChildGraph(childGraph)){
			defaultConstructor.buildChildGraph(childGraph);
		}
		if(nodeConstructor == null || !nodeConstructor.buildNodeFactory(nodeFactory)){
			defaultConstructor.buildNodeFactory(nodeFactory);
		}
		if(nodeConstructor == null || !nodeConstructor.buildEdgeFactory(edgeFactory)){
			defaultConstructor.buildEdgeFactory(edgeFactory);
		}
	}



	@Override
	public String getElementId() {
		return this.elementId;
	}

	@Override
	public ICompoundChildEdgeFactory getEdgeFactory() {
		return this.edgeFactory;
	}

}