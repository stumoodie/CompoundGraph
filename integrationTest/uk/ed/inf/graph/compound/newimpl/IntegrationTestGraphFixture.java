package uk.ed.inf.graph.compound.newimpl;

import java.util.HashMap;
import java.util.Map;

import org.jmock.api.Action;

import uk.ed.inf.graph.compound.CompoundNodePair;
import uk.ed.inf.graph.compound.ICompoundChildEdgeFactory;
import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundGraph;
import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.ICompoundNodeFactory;
import uk.ed.inf.graph.compound.IRootCompoundNode;
import uk.ed.inf.graph.compound.testfixture.IEdgeConstructor;
import uk.ed.inf.graph.compound.testfixture.IGraphConstructor;
import uk.ed.inf.graph.compound.testfixture.IGraphTestFixture;
import uk.ed.inf.graph.compound.testfixture.INodeConstructor;

public class IntegrationTestGraphFixture implements IGraphTestFixture {
	public static final String ROOT_NODE_ID = "rootNode";
	public static final String NODE1_ID = "node1";
	public static final String NODE2_ID = "node2";
	public static final String NODE3_ID = "node3";
	public static final String NODE4_ID = "node4";
	public static final String NODE5_ID = "node5";
	public static final String NODE6_ID = "node6";
	public static final String EDGE1_ID = "edge1";
	public static final String EDGE2_ID = "edge2";
	public static final String EDGE3_ID = "edge3";
	public static final String EDGE4_ID = "edge4";

	private ICompoundGraph graph;
	private final Map<String, ICompoundGraphElement> elementMap;
	
	public IntegrationTestGraphFixture(){
		this.elementMap = new HashMap<String, ICompoundGraphElement>();
	}
	
	
	@Override
	public void buildFixture(){
		this.elementMap.clear();
		this.graph = new CompoundGraph();
		this.elementMap.put(ROOT_NODE_ID, this.graph.getRoot());
		ICompoundNodeFactory rootNodeFact = this.graph.getRoot().getChildCompoundGraph().nodeFactory();
		ICompoundNode node1 = rootNodeFact.createNode();
		this.elementMap.put(NODE1_ID, node1);
		ICompoundNode node6 = rootNodeFact.createNode();
		this.elementMap.put(NODE6_ID, node6);
		ICompoundChildEdgeFactory rootEdgeFact = this.graph.getRoot().getChildCompoundGraph().edgeFactory();
		rootEdgeFact.setPair(new CompoundNodePair(node1, node6));
		ICompoundEdge edge1 = rootEdgeFact.createEdge();
		this.elementMap.put(EDGE1_ID, edge1);
		
		ICompoundNodeFactory node1NodeFact = node1.getChildCompoundGraph().nodeFactory();
		ICompoundNode node2 = node1NodeFact.createNode();
		this.elementMap.put(NODE2_ID, node2);
		ICompoundChildEdgeFactory node2EdgeFactory = node2.getChildCompoundGraph().edgeFactory();
		node2EdgeFactory.setPair(new CompoundNodePair(node2, node2));
		ICompoundEdge edge3 = node2EdgeFactory.createEdge();
		this.elementMap.put(EDGE3_ID, edge3);

		ICompoundNodeFactory edge1NodeFactory = edge1.getChildCompoundGraph().nodeFactory();
		ICompoundNode node3 = edge1NodeFactory.createNode();
		this.elementMap.put(NODE3_ID, node3);
		ICompoundNode node5 = edge1NodeFactory.createNode();
		this.elementMap.put(NODE5_ID, node5);
		ICompoundChildEdgeFactory edge1EdgeFactory = edge1.getChildCompoundGraph().edgeFactory();
		edge1EdgeFactory.setPair(new CompoundNodePair(node3, node5));

		ICompoundEdge edge2 = edge1EdgeFactory.createEdge();
		this.elementMap.put(EDGE2_ID, edge2);
		ICompoundNodeFactory edge2NodeFactory = edge2.getChildCompoundGraph().nodeFactory();
		ICompoundNode node4 = edge2NodeFactory.createNode();
		this.elementMap.put(NODE4_ID, node4);
		
		rootEdgeFact.setPair(new CompoundNodePair(node2, node3));
		ICompoundEdge edge4 = rootEdgeFact.createEdge();
		this.elementMap.put(EDGE4_ID, edge4);
	}
	
	public ICompoundGraphElement getElement(String elementId){
		return this.elementMap.get(elementId);
	}
	
	public ICompoundNode getNode(String elementId){
		return (ICompoundNode)this.elementMap.get(elementId);
	}

	public ICompoundEdge getEdge(String elementId){
		return (ICompoundEdge)this.elementMap.get(elementId);
	}
	
	public ICompoundGraph getGraph(){
		return this.graph;
	}


	@Override
	public IRootCompoundNode getRootNode() {
		return (IRootCompoundNode)getNode(ROOT_NODE_ID);
	}


	@Override
	public boolean isRemoved(String elementId) {
		return getElement(elementId).isRemoved();
	}


	@Override
	public void redefineEdge(String id, IEdgeConstructor edgeConstructor) {
		throw new UnsupportedOperationException("Not supported");
	}


	@Override
	public void redefineGraph(IGraphConstructor graphConstructor) {
		throw new UnsupportedOperationException("Not supported");
	}


	@Override
	public void redefineNode(String id, INodeConstructor nodeConstructor) {
		throw new UnsupportedOperationException("Not supported");
	}


	@Override
	public void setElementRemoved(String elementId, boolean markRemoved) {
		getElement(elementId).markRemoved(markRemoved);
	}


	@Override
	public Action setRemovalState(String elementId) {
		throw new UnsupportedOperationException("Not supported");
	}
}
