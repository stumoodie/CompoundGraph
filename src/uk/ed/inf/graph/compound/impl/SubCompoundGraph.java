package uk.ed.inf.graph.compound.impl;

import java.util.Iterator;

import uk.ed.inf.graph.basic.IBasicPair;
import uk.ed.inf.graph.basic.ISubgraphAlgorithms;
import uk.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ed.inf.graph.directed.IDirectedPair;
import uk.ed.inf.graph.util.SubgraphAlgorithms;
import uk.ed.inf.graph.util.impl.EdgeSet;
import uk.ed.inf.graph.util.impl.NodeSet;

public class SubCompoundGraph implements ISubCompoundGraph<CompoundNode, CompoundEdge> {
	private final CompoundGraph superGraph;
	private final NodeSet<CompoundNode, CompoundEdge> nodeSet;
	private final EdgeSet<CompoundNode, CompoundEdge> edgeSet;
	
	public SubCompoundGraph(CompoundGraph superGraph){
		this.superGraph = superGraph;
		this.nodeSet = new NodeSet<CompoundNode, CompoundEdge>();
		this.edgeSet = new EdgeSet<CompoundNode, CompoundEdge>();
	}
	
//	public boolean canCopyHere(IBasicSubgraph<CiNode, CiEdge> subGraph) {
//		return false;
//	}
//
//	public void copyHere(IBasicSubgraph<CiNode, CiEdge> subGraph) {
//		throw new UnsupportedOperationException("Operation not supported by GeneralSubgraph");
//	}

	public CompoundGraph getSuperGraph() {
		return this.superGraph;
	}

	public boolean isInducedSubgraph() {
		ISubgraphAlgorithms<CompoundNode, CompoundEdge> alg = new SubgraphAlgorithms<CompoundNode, CompoundEdge>(this);
		return alg.isInducedSubgraph();
	}

	public boolean containsConnection(CompoundNode thisNode, CompoundNode thatNode) {
		boolean retVal = false;
		if(thisNode != null && thatNode != null){
			retVal = thisNode.hasEdgeWith(thatNode);
		}
		return retVal;
	}

	public boolean containsEdge(CompoundEdge edge) {
		return this.edgeSet.contains(edge);
	}

	public boolean containsEdge(int edgeIdx) {
		return this.edgeSet.contains(edgeIdx);
	}

	public boolean containsNode(int nodeIdx) {
		return this.nodeSet.contains(nodeIdx);
	}

	public boolean containsNode(CompoundNode node) {
		return this.nodeSet.contains(node);
	}

//	public IBasicEdgeFactory<CiNode, CiEdge> basicEdgeFactory() {
//		throw new UnsupportedOperationException("Operation not supported by GeneralSubgraph");
//	}
//
//	public IBasicNodeFactory<CiNode, CiEdge> basicNodeFactory() {
//		throw new UnsupportedOperationException("Operation not supported by GeneralSubgraph");
//	}
//
//	public IBasicSubgraphFactory<CiNode, CiEdge> basicSubgraphFactory() {
//		throw new UnsupportedOperationException("Operation not supported by GeneralSubgraph");
//	}

	public CompoundEdge getEdge(int edgeIdx) {
		return this.edgeSet.get(edgeIdx);
	}

	public Iterator<CompoundEdge> edgeIterator() {
		return this.edgeSet.iterator();
	}

	public CompoundNode getNode(int nodeIdx) {
		return this.nodeSet.get(nodeIdx);
	}

	public Iterator<CompoundNode> nodeIterator() {
		return this.nodeSet.iterator();
	}

	public int getNumEdges() {
		return this.edgeSet.size();
	}

	public int getNumNodes() {
		return this.nodeSet.size();
	}

	public void addNode(CompoundNode iNewNode){
		CompoundNode newNode = (CompoundNode)iNewNode;
		
		this.nodeSet.add(newNode);
	}
	
	public void addEdge(CompoundEdge iNewEdge){
		CompoundEdge newEdge = (CompoundEdge)iNewEdge;
		
		this.edgeSet.add(newEdge);
	}

	public boolean containsDirectedEdge(CompoundNode iInNode, CompoundNode iOutNode) {
		boolean retVal = false;
		if(iInNode != null && iOutNode != null){
			CompoundNode inNode = (CompoundNode)iInNode;
			CompoundNode outNode = (CompoundNode)iOutNode;
			retVal = this.edgeSet.contains(inNode, outNode);
		}
		return retVal;
	}

	public boolean isConsistentSnapShot() {
		boolean retVal = true;
		for(CompoundNode compoundNode : this.nodeSet){
			if(compoundNode.isRemoved()){
				retVal = false;
				break;
			}
		}
		if(retVal){
			for(CompoundEdge compoundEdge : this.edgeSet){
				if(compoundEdge.isRemoved()){
					retVal = false;
					break;
				}
			}
		}
		return retVal;
	}

	/**
	 * Tests if the ends define one or more directed edges.
	 */
	public boolean containsDirectedEdge(IDirectedPair<CompoundNode, CompoundEdge> ends) {
		boolean retVal = false;
		if(ends != null){
			CompoundNode outNode = ends.getOutNode();
			CompoundNode inNode = ends.getInNode();
			// check that both nodes exist in this subgraph
			if(this.nodeSet.contains(outNode) && this.nodeSet.contains(inNode)){
				retVal = outNode.hasOutEdgeTo(inNode);
			}
		}
		return retVal;
	}

	/**
	 * Tests if the ends define any edge in this graph. Note that the node pair must
	 * be created by this graph as the method expects <code>ends</code> to be of type
	 * <code>IDirectedPair</code> and will return false if it is not.
	 * @param ends the pair of nodes that may define the edges of an edge.
	 * @return true if it does, false otherwise.  
	 */
	public boolean containsConnection(IBasicPair<CompoundNode, CompoundEdge> ends) {
		boolean retVal = false;
		if(ends != null && ends instanceof IDirectedPair){
			// since this is a directed graph a valid edge pair must be an IDirectedPair
			IDirectedPair<CompoundNode, CompoundEdge> ciEnds = (IDirectedPair<CompoundNode, CompoundEdge>)ends;
			CompoundNode oneNode = ciEnds.getOutNode();
			CompoundNode twoNode = ciEnds.getInNode();
			// check that the nodes belong to this subgraph.
			if(this.nodeSet.contains(oneNode) && this.nodeSet.contains(twoNode)){
				retVal = this.containsConnection(oneNode, twoNode);
			}
		}
		return retVal;
	}

	public void clear() {
		// TODO Auto-generated method stub
		
	}
}
