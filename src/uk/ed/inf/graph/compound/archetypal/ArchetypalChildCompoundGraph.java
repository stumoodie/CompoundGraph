package uk.ed.inf.graph.compound.archetypal;

import java.util.Iterator;

import uk.ed.inf.graph.basic.IBasicPair;
import uk.ed.inf.graph.basic.IBasicSubgraph;
import uk.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ed.inf.graph.compound.IModifiableChildCompoundGraph;
import uk.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ed.inf.graph.directed.IDirectedPair;
import uk.ed.inf.graph.util.IEdgeSet;
import uk.ed.inf.graph.util.IFilterCriteria;
import uk.ed.inf.graph.util.INodeSet;
import uk.ed.inf.graph.util.impl.DirectedEdgeSet;
import uk.ed.inf.graph.util.impl.FilteredEdgeSet;
import uk.ed.inf.graph.util.impl.FilteredNodeSet;
import uk.ed.inf.graph.util.impl.NodeSet;

public abstract class ArchetypalChildCompoundGraph implements IChildCompoundGraph<ArchetypalCompoundNode, ArchetypalCompoundEdge>,
		IModifiableChildCompoundGraph<ArchetypalCompoundNode, ArchetypalCompoundEdge> {
	private final ArchetypalCompoundNode root;
	private final ArchetypalGraphCopyBuilder copyBuilder;
	private final IEdgeSet<ArchetypalCompoundNode, ArchetypalCompoundEdge> edgeSet;
	private final INodeSet<ArchetypalCompoundNode, ArchetypalCompoundEdge> nodeSet;
	
	protected ArchetypalChildCompoundGraph(ArchetypalCompoundNode root, ArchetypalGraphCopyBuilder builder){
		if(root == null) throw new IllegalArgumentException("root cannot be null");
		
		this.copyBuilder = builder;
		this.root = root;
		this.edgeSet = new FilteredEdgeSet<ArchetypalCompoundNode, ArchetypalCompoundEdge>(new DirectedEdgeSet<ArchetypalCompoundNode, ArchetypalCompoundEdge>(),
				new IFilterCriteria<ArchetypalCompoundEdge>(){

					public boolean matched(ArchetypalCompoundEdge testObj) {
						return !testObj.isRemoved();
					}
			
		});
		this.nodeSet = new FilteredNodeSet<ArchetypalCompoundNode, ArchetypalCompoundEdge>(new NodeSet<ArchetypalCompoundNode, ArchetypalCompoundEdge>(),
				new IFilterCriteria<ArchetypalCompoundNode>(){

			public boolean matched(ArchetypalCompoundNode testObj) {
				return !testObj.isRemoved();
			}
	
		});
	}
	
	public ArchetypalCompoundNode getRootNode() {
		return this.root;
	}

	public boolean containsDirectedEdge(ArchetypalCompoundNode outNode, ArchetypalCompoundNode inNode) {
		boolean retVal = false;
		if(inNode != null && outNode != null){
			retVal = this.edgeSet.contains(outNode, inNode);
		}
		return retVal;
	}

	public boolean canCreateEdges() {
		return false;
	}

	public boolean canCreateNodes() {
		return true;
	}

	public boolean containsConnection(ArchetypalCompoundNode iThisNode, ArchetypalCompoundNode iThatNode) {
		boolean retVal = false;
		if(iThisNode != null && iThatNode != null){
			ArchetypalCompoundNode thisNode = (ArchetypalCompoundNode)iThisNode;
			ArchetypalCompoundNode thatNode = (ArchetypalCompoundNode)iThatNode;
			retVal = this.edgeSet.contains(thisNode, thatNode) || this.edgeSet.contains(thatNode, thisNode);
		}
		return retVal;
	}

	public boolean containsEdge(ArchetypalCompoundEdge edge) {
		boolean retVal = false;
		if(edge != null){
			retVal = this.edgeSet.contains(edge.getIndex());
		}
		return retVal;
	}

	public boolean containsEdge(int edgeIdx) {
		return this.edgeSet.contains(edgeIdx);
	}

	public boolean containsNode(int nodeIdx) {
		return this.nodeSet.contains(nodeIdx);
	}

	public boolean containsNode(ArchetypalCompoundNode node) {
		boolean retVal = false;
		if(node != null){
			retVal = containsNode(node.getIndex());
		}
		return retVal;
	}

	public ArchetypalCompoundEdge getEdge(int edgeIdx) {
		return this.edgeSet.get(edgeIdx);
	}

	public Iterator<ArchetypalCompoundEdge> edgeIterator() {
		return this.edgeSet.iterator();
	}

	public ArchetypalCompoundNode getNode(int nodeIdx) {
		return this.nodeSet.get(nodeIdx);
	}

	public Iterator<ArchetypalCompoundNode> nodeIterator() {
		return this.nodeSet.iterator();
	}

	public int getNumEdges() {
		return this.edgeSet.size();
	}

	public int getNumNodes() {
		return this.nodeSet.size();
	}

	public boolean canCopyHere(IBasicSubgraph<ArchetypalCompoundNode, ArchetypalCompoundEdge> subGraph) {
		return subGraph != null && subGraph instanceof ISubCompoundGraph && subGraph.isInducedSubgraph()
			&& subGraph.isConsistentSnapShot();
	}

	
	public void copyHere(IBasicSubgraph<ArchetypalCompoundNode, ArchetypalCompoundEdge> iSubGraph) {
		if(!canCopyHere(iSubGraph)) throw new IllegalArgumentException("Cannot copy graph here");
		ISubCompoundGraph<ArchetypalCompoundNode, ArchetypalCompoundEdge> subGraph = (ISubCompoundGraph<ArchetypalCompoundNode, ArchetypalCompoundEdge>)iSubGraph;
		
//		ChildCompoundGraphBuilder copyBuilder = new ChildCompoundGraphBuilder(this, subGraph);
		copyBuilder.setDestinatChildCompoundGraph(this);
		copyBuilder.setSourceSubgraph(subGraph);
		copyBuilder.copyNodes();
		copyBuilder.copyEquivalentEdges();
	}

	public ArchetypalCompoundGraph getSuperGraph() {
		return this.root.getGraph();
	}

	public boolean isInducedSubgraph() {
		return false;
	}

//	public CiNode getLcaNode(CiNode inNode, CiNode outNode) {
//		return this.root.getLowestCommonAncestor(inNode, outNode);
//	}

	void addNewNode(ArchetypalCompoundNode newNode){
		this.nodeSet.add(newNode);
	}
	
	void addNewEdge(ArchetypalCompoundEdge newEdge){
		this.edgeSet.add(newEdge);
	}

	public boolean containsDirectedEdge(IDirectedPair<ArchetypalCompoundNode, ArchetypalCompoundEdge> ends) {
		boolean retVal = false;
		if(ends != null){
			for(ArchetypalCompoundEdge edge : this.edgeSet){
				if(edge.equals(ends)){
					retVal = true;
					break;
				}
			}
		}
		return retVal;
	}

	public boolean containsConnection(IBasicPair<ArchetypalCompoundNode, ArchetypalCompoundEdge> ends) {
		boolean retVal = false;
		if(ends != null && ends instanceof IDirectedPair){
			IDirectedPair<ArchetypalCompoundNode, ArchetypalCompoundEdge> ciEnds = (IDirectedPair<ArchetypalCompoundNode, ArchetypalCompoundEdge>)ends;
			retVal = containsDirectedEdge(ciEnds);
		}
		return retVal;
	}

	public void clear() {
		// TODO: implement this!
		throw new UnsupportedOperationException("Implement this method!");
	}
	
	public abstract ArchetypalCompoundNodeFactory nodeFactory();
	
	public abstract ArchetypalChildCompoundEdgeFactory edgeFactory();
}
