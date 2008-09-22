package uk.ed.inf.graph.compound.base;

import java.util.Iterator;

import uk.ed.inf.graph.basic.IBasicPair;
import uk.ed.inf.graph.basic.ISubgraphAlgorithms;
import uk.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ed.inf.graph.directed.IDirectedPair;
import uk.ed.inf.graph.util.SubgraphAlgorithms;
import uk.ed.inf.graph.util.impl.EdgeSet;
import uk.ed.inf.graph.util.impl.NodeSet;

public abstract class BaseSubCompoundGraph implements ISubCompoundGraph<BaseCompoundNode, BaseCompoundEdge> {
	private final NodeSet<BaseCompoundNode, BaseCompoundEdge> nodeSet;
	private final EdgeSet<BaseCompoundNode, BaseCompoundEdge> edgeSet;
	
	protected BaseSubCompoundGraph(){
		this.nodeSet = new NodeSet<BaseCompoundNode, BaseCompoundEdge>();
		this.edgeSet = new EdgeSet<BaseCompoundNode, BaseCompoundEdge>();
	}
	
	public abstract BaseCompoundGraph getSuperGraph();

	public boolean isInducedSubgraph() {
		ISubgraphAlgorithms<BaseCompoundNode, BaseCompoundEdge> alg = new SubgraphAlgorithms<BaseCompoundNode, BaseCompoundEdge>(this);
		return alg.isInducedSubgraph();
	}

	public boolean containsConnection(BaseCompoundNode thisNode, BaseCompoundNode thatNode) {
		boolean retVal = false;
		if(thisNode != null && thatNode != null){
			retVal = thisNode.hasEdgeWith(thatNode);
		}
		return retVal;
	}

	public boolean containsEdge(BaseCompoundEdge edge) {
		return this.edgeSet.contains(edge);
	}

	public boolean containsEdge(int edgeIdx) {
		return this.edgeSet.contains(edgeIdx);
	}

	public boolean containsNode(int nodeIdx) {
		return this.nodeSet.contains(nodeIdx);
	}

	public boolean containsNode(BaseCompoundNode node) {
		return this.nodeSet.contains(node);
	}

	public BaseCompoundEdge getEdge(int edgeIdx) {
		return this.edgeSet.get(edgeIdx);
	}

	public Iterator<BaseCompoundEdge> edgeIterator() {
		return this.edgeSet.iterator();
	}

	public BaseCompoundNode getNode(int nodeIdx) {
		return this.nodeSet.get(nodeIdx);
	}

	public Iterator<BaseCompoundNode> nodeIterator() {
		return this.nodeSet.iterator();
	}

	public int getNumEdges() {
		return this.edgeSet.size();
	}

	public int getNumNodes() {
		return this.nodeSet.size();
	}

	void addNode(BaseCompoundNode newNode){
		this.nodeSet.add(newNode);
	}
	
	void addEdge(BaseCompoundEdge newEdge){
		
		this.edgeSet.add(newEdge);
	}

	public boolean containsDirectedEdge(BaseCompoundNode outNode, BaseCompoundNode inNode) {
		boolean retVal = false;
		if(outNode != null && inNode != null){
			retVal = this.edgeSet.contains(outNode, inNode);
		}
		return retVal;
	}

	public boolean isConsistentSnapShot() {
		boolean retVal = true;
		for(BaseCompoundNode compoundNode : this.nodeSet){
			if(compoundNode.isRemoved()){
				retVal = false;
				break;
			}
		}
		if(retVal){
			for(BaseCompoundEdge compoundEdge : this.edgeSet){
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
	public boolean containsDirectedEdge(IDirectedPair<? extends BaseCompoundNode, ? extends BaseCompoundEdge> ends) {
		boolean retVal = false;
		if(ends != null){
			BaseCompoundNode outNode = (BaseCompoundNode)ends.getOutNode();
			BaseCompoundNode inNode = (BaseCompoundNode)ends.getInNode();
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
	@SuppressWarnings("unchecked")
	public boolean containsConnection(IBasicPair<? extends BaseCompoundNode, ? extends BaseCompoundEdge> ends) {
		boolean retVal = false;
		if(ends != null && ends instanceof IDirectedPair){
			// since this is a directed graph a valid edge pair must be an IDirectedPair
			IDirectedPair<? extends BaseCompoundNode, ? extends BaseCompoundEdge> ciEnds = (IDirectedPair<? extends BaseCompoundNode, ? extends BaseCompoundEdge>)ends;
			BaseCompoundNode oneNode = (BaseCompoundNode)ciEnds.getOutNode();
			BaseCompoundNode twoNode = (BaseCompoundNode)ciEnds.getInNode();
			// check that the nodes belong to this subgraph.
			if(this.nodeSet.contains(oneNode) && this.nodeSet.contains(twoNode)){
				retVal = this.containsConnection(oneNode, twoNode);
			}
		}
		return retVal;
	}
}
