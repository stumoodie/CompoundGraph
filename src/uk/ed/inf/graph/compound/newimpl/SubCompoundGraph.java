package uk.ed.inf.graph.compound.newimpl;

import java.util.Iterator;

import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundGraph;
import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.ICompoundNodePair;
import uk.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ed.inf.graph.compound.ISubgraphAlgorithms;
import uk.ed.inf.graph.util.SubgraphAlgorithms;

public class SubCompoundGraph implements ISubCompoundGraph {
	private final ElementTreeStructure topElements;
	private Boolean isInducedSubgraphFlag = null;
	private final ICompoundGraph graph;

	public SubCompoundGraph(ICompoundGraph graph){
		this.graph = graph;
		this.topElements = new ElementTreeStructure();
	}
	
	@Override
	public boolean containsConnection(ICompoundNode thisNode, ICompoundNode thatNode) {
		boolean retVal = false;
		// first test if the connection exists at all
		if(thisNode.hasEdgeWith(thatNode)){
			retVal = topElements.containsNodes(thisNode, thatNode);
		}
		return retVal;
	}
	
	@Override
	public boolean containsConnection(ICompoundNodePair ends) {
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
		// check if directed edge is present then if nores are in subgraph
		if(outNode.hasOutEdgeTo(inNode)){
			retVal = this.topElements.containsNodes(outNode, inNode);
		}
		return retVal;
	}

	@Override
	public boolean containsDirectedEdge(ICompoundNodePair ends) {
		boolean retVal = false;
		if(ends != null){
			ICompoundNode outNode = ends.getOutNode();
			ICompoundNode inNode = ends.getInNode();
			// check that both nodes exist in this subgraph
			retVal = containsDirectedEdge(outNode, inNode);
		}
		return retVal;
	}

	@Override
	public boolean containsEdge(ICompoundEdge edge) {
		return this.topElements.containsEdges(edge);
	}

	@Override
	public boolean containsEdge(int edgeIdx) {
		return this.topElements.containsEdges(edgeIdx);
	}

	@Override
	public boolean containsElement(ICompoundGraphElement element) {
		return this.topElements.containsElement(element);
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
		return this.topElements.containsNodes(node);
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
		while ( nodeIter.hasNext() && retVal){
			retVal = !nodeIter.next().isRemoved();
		}
		return retVal;
	}

	@Override
	public boolean isInducedSubgraph() {
		if(isInducedSubgraphFlag  == null){
			ISubgraphAlgorithms alg = new SubgraphAlgorithms(this);
			this.isInducedSubgraphFlag = new Boolean(alg.isInducedSubgraph());
		}
		return this.isInducedSubgraphFlag;
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
		return new DFSNodeFirstIterator(this.topElements.topElementIterator());
	}

	public void addTopNode(ICompoundGraphElement element) {
		this.topElements.addTopElement(element);
	}

}
