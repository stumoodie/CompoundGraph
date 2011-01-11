package uk.ac.ed.inf.graph.compound.newimpl;

import java.util.Iterator;

import uk.ac.ed.inf.graph.compound.CompoundNodePair;
import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElementVisitor;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ac.ed.inf.graph.compound.ISubgraphAlgorithms;
import uk.ac.ed.inf.graph.util.SubgraphAlgorithms;

public class SubCompoundGraph implements ISubCompoundGraph {
	
	private final ElementTreeStructure topElements;
	private Boolean isInducedSubgraphFlag = null;
	private final ICompoundGraph graph;

	SubCompoundGraph(ICompoundGraph graph){
		this.graph = graph;
		this.topElements = new ElementTreeStructure();
	}
	
	@Override
	public boolean containsConnection(ICompoundNode thisNode, ICompoundNode thatNode) {
		boolean retVal = false;
		//first test existence of edge
		if(thisNode != null && thatNode != null){
			CompoundNodePair testPair = new CompoundNodePair(thisNode, thatNode);
			retVal = topElements.containsConnection(testPair);
		}
		return retVal;
	}
	
	@Override
	public boolean containsConnection(CompoundNodePair ends) {
		boolean retVal = false;
		if(ends != null){
			ICompoundNode oneNode = ends.getOutNode();
			ICompoundNode twoNode = ends.getInNode();
			// check that the nodes belong to this subgraph.
			retVal = this.containsConnection(oneNode, twoNode);
		}
		return retVal;
	}

	@Override
	public boolean containsDirectedEdge(ICompoundNode outNode, ICompoundNode inNode) {
		boolean retVal = false;
		// check if directed edge is present then if nodes are in subgraph
		if(outNode != null && inNode != null
				&& outNode.getGraph().equals(this.graph)
				&& inNode.getGraph().equals(this.graph)){
			CompoundNodePair ends = new CompoundNodePair(outNode, inNode);
			retVal = this.containsDirectedEdge(ends);
		}
		return retVal;
	}

	@Override
	public boolean containsDirectedEdge(CompoundNodePair ends) {
		boolean retVal = false;
		retVal = ends != null && ends.getGraph().equals(this.graph) && this.topElements.containsDirectedConnection(ends);
		return retVal;
	}

	@Override
	public boolean containsEdge(ICompoundEdge edge) {
		boolean retVal = false;
		if(edge != null && edge.getGraph().equals(this.graph)){
			retVal = this.topElements.containsEdges(edge); 
		}
		return retVal;
	}

	@Override
	public boolean containsEdge(int edgeIdx) {
		return this.topElements.containsEdges(edgeIdx);
	}

	@Override
	public boolean containsElement(ICompoundGraphElement element) {
		boolean retVal = false;
		if(element != null && element.getGraph().equals(this.graph)){
			retVal = this.topElements.containsElement(element);
		}
		return retVal;
	}

	@Override
	public boolean containsElement(int uniqueId) {
		return this.topElements.containsElement(uniqueId);
	}

	@Override
	public boolean containsNode(int nodeIdx) {
		return this.topElements.containsNodes(nodeIdx);
	}

	@Override
	public boolean containsNode(ICompoundNode node) {
		boolean retVal = false;
		if(node != null && node.getGraph().equals(this.graph)){
			retVal = this.topElements.containsNodes(node);
		}
		return retVal;
	}

	@Override
	public boolean containsRoot() {
		return this.containsElement(this.getSuperGraph().getRoot());
	}

	@Override
	public Iterator<ICompoundEdge> edgeIterator() {
		return this.topElements.edgeIterator();
	}

	@Override
	public Iterator<ICompoundGraphElement> elementIterator() {
		return this.topElements.elementIterator();
	}

	@Override
	public ICompoundEdge getEdge(int edgeIdx) {
		return this.topElements.getEdge(edgeIdx);
	}

	@Override
	public ICompoundGraphElement getElement(int uniqueId) {
		return this.topElements.getElement(uniqueId);
	}

	@Override
	public ICompoundNode getNode(int nodeIdx) {
		return this.topElements.getNode(nodeIdx);
	}

	@Override
	public int getNumEdges() {
		return this.topElements.numEdges();
	}

	@Override
	public int getNumNodes() {
		return this.topElements.numNodes();
	}

	@Override
	public int getNumTopNodes() {
		return this.topElements.numTopNodes();
	}

	@Override
	public ICompoundGraph getSuperGraph() {
		return this.graph;
	}

	@Override
	public boolean isConsistentSnapShot() {
		boolean retVal = true;
		Iterator <ICompoundGraphElement> nodeIter = this.elementIterator() ;
		while (nodeIter.hasNext() && retVal){
			ICompoundGraphElement element = nodeIter.next(); 
			retVal = !element.isRemoved();
		}
		return retVal;
	}

	@Override
	public boolean isInducedSubgraph() {
		if(isInducedSubgraphFlag  == null){
			ISubgraphAlgorithms alg = new SubgraphAlgorithms(this);
			this.isInducedSubgraphFlag = Boolean.valueOf(alg.isInducedSubgraph());
		}
		return this.isInducedSubgraphFlag;
	}

	@Override
	public boolean hasOrphanedEdges(){
		Iterator<ICompoundEdge> iter = this.topElements.edgeIterator();
		boolean retVal = false;
		while(iter.hasNext() && !retVal){
			ICompoundEdge edge = iter.next();
			CompoundNodePair pair = edge.getConnectedNodes();
			retVal = !(this.containsNode(pair.getOutNode())
					&& this.containsNode(pair.getInNode()));
		}
		return retVal;
	}
	
	@Override
	public Iterator<ICompoundNode> nodeIterator() {
		return this.topElements.nodeIterator();
	}

	@Override
	public int numElements() {
		return this.topElements.numElements();
	}

	@Override
	public int numTopElements() {
		return this.topElements.numTopElements();
	}

	@Override
	public Iterator<ICompoundGraphElement> topElementIterator() {
		return this.topElements.topElementIterator();
	}

	@Override
	public Iterator<ICompoundNode> topNodeIterator() {
		return this.topElements.topNodeIterator();
	}

	@Override
	public Iterator<ICompoundEdge> topEdgeIterator() {
		return this.topElements.topEdgeIterator();
	}

	@Override
	public Iterator<ICompoundGraphElement> edgeLastElementIterator() {
		return this.topElements.edgeLastElementIterator();
	}

	void addTopElement(ICompoundGraphElement element) {
		this.topElements.addTopElement(element);
	}
	
//	void addElement(ICompoundGraphElement element){
//		this.topElements.addElement(element);
//	}

	@Override
	public int getNumTopEdges() {
		return this.topElements.numTopEdges();
	}

	@Override
	public void visitAll(ICompoundGraphElementVisitor visitor) {
		Iterator<ICompoundGraphElement> iter = this.elementIterator();
		while(iter.hasNext()){
			ICompoundGraphElement el = iter.next();
			if(el.isNode()){
				visitor.visitNode((ICompoundNode)el);
			}
			else{
				visitor.visitEdge((ICompoundEdge)el);
			}
				
		}
	}

}
