package uk.ed.inf.graph.compound.base;

import java.util.Iterator;

import uk.ed.inf.graph.basic.IBasicPair;
import uk.ed.inf.graph.basic.IBasicSubgraph;
import uk.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ed.inf.graph.compound.ICompoundGraphCopyBuilder;
import uk.ed.inf.graph.compound.IModifiableChildCompoundGraph;
import uk.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ed.inf.graph.directed.IDirectedPair;
import uk.ed.inf.graph.util.IDirectedEdgeSet;
import uk.ed.inf.graph.util.IEdgeSet;
import uk.ed.inf.graph.util.IFilterCriteria;
import uk.ed.inf.graph.util.INodeSet;
import uk.ed.inf.graph.util.impl.FilteredEdgeSet;
import uk.ed.inf.graph.util.impl.FilteredNodeSet;

public abstract class BaseChildCompoundGraph implements IChildCompoundGraph<BaseCompoundNode, BaseCompoundEdge>,	IModifiableChildCompoundGraph<BaseCompoundNode, BaseCompoundEdge> {
//	private final BaseCompoundNode root;
	private final ICompoundGraphCopyBuilder copyBuilder;
	private IEdgeSet<BaseCompoundNode, BaseCompoundEdge> edgeSet;
	private INodeSet<BaseCompoundNode, BaseCompoundEdge> nodeSet;
	
	protected BaseChildCompoundGraph(ICompoundGraphCopyBuilder builder){
//		if(root == null) throw new IllegalArgumentException("root cannot be null");
		
		this.copyBuilder = builder;
	}

	protected final void createNodeSet(INodeSet<BaseCompoundNode, BaseCompoundEdge> nodeSet){
		this.nodeSet = new FilteredNodeSet<BaseCompoundNode, BaseCompoundEdge>(nodeSet, new IFilterCriteria<BaseCompoundNode>(){

			public boolean matched(BaseCompoundNode testObj) {
				return !testObj.isRemoved();
			}
	
		});
	}
	
	protected final void createEdgeSet(IDirectedEdgeSet<BaseCompoundNode, BaseCompoundEdge> edgeSet){
		this.edgeSet = new FilteredEdgeSet<BaseCompoundNode, BaseCompoundEdge>(edgeSet,
				new IFilterCriteria<BaseCompoundEdge>(){

					public boolean matched(BaseCompoundEdge testObj) {
						return !testObj.isRemoved();
					}
			
		});
	}
	
	protected final IEdgeSet<BaseCompoundNode, BaseCompoundEdge> getEdgeSet(){
		return this.edgeSet;
	}
	
	protected final INodeSet<BaseCompoundNode, BaseCompoundEdge> getNodeSet() {
		return this.nodeSet;
	}
	
	public abstract BaseCompoundNode getRootNode();

	public boolean containsDirectedEdge(BaseCompoundNode outNode, BaseCompoundNode inNode) {
		boolean retVal = false;
		if(inNode != null && outNode != null){
			retVal = this.edgeSet.contains(outNode, inNode);
		}
		return retVal;
	}

	public final boolean containsConnection(BaseCompoundNode iThisNode, BaseCompoundNode iThatNode) {
		boolean retVal = false;
		if(iThisNode != null && iThatNode != null){
			BaseCompoundNode thisNode = (BaseCompoundNode)iThisNode;
			BaseCompoundNode thatNode = (BaseCompoundNode)iThatNode;
			retVal = this.edgeSet.contains(thisNode, thatNode) || this.edgeSet.contains(thatNode, thisNode);
		}
		return retVal;
	}

	public final boolean containsEdge(BaseCompoundEdge edge) {
		boolean retVal = false;
		if(edge != null){
			retVal = this.edgeSet.contains(edge.getIndex());
		}
		return retVal;
	}

	public final boolean containsEdge(int edgeIdx) {
		return this.edgeSet.contains(edgeIdx);
	}

	public final boolean containsNode(int nodeIdx) {
		return this.nodeSet.contains(nodeIdx);
	}

	public boolean containsNode(BaseCompoundNode node) {
		boolean retVal = false;
		if(node != null){
			retVal = containsNode(node.getIndex());
		}
		return retVal;
	}

	public final BaseCompoundEdge getEdge(int edgeIdx) {
		return this.edgeSet.get(edgeIdx);
	}

	public final Iterator<BaseCompoundEdge> edgeIterator() {
		return this.edgeSet.iterator();
	}

	public final BaseCompoundNode getNode(int nodeIdx) {
		return this.nodeSet.get(nodeIdx);
	}

	public final Iterator<BaseCompoundNode> nodeIterator() {
		return this.nodeSet.iterator();
	}

	public final int getNumEdges() {
		return this.edgeSet.size();
	}

	public final int getNumNodes() {
		return this.nodeSet.size();
	}

	public final boolean canCopyHere(IBasicSubgraph<BaseCompoundNode, BaseCompoundEdge> subGraph) {
		return subGraph != null && subGraph instanceof ISubCompoundGraph && subGraph.isInducedSubgraph()
			&& subGraph.isConsistentSnapShot();
	}

	
	public final void copyHere(IBasicSubgraph<BaseCompoundNode, BaseCompoundEdge> iSubGraph) {
		if(!canCopyHere(iSubGraph)) throw new IllegalArgumentException("Cannot copy graph here");
		ISubCompoundGraph<BaseCompoundNode, BaseCompoundEdge> subGraph = (ISubCompoundGraph<BaseCompoundNode, BaseCompoundEdge>)iSubGraph;
		
//		ChildCompoundGraphBuilder copyBuilder = new ChildCompoundGraphBuilder(this, subGraph);
		copyBuilder.setDestinatChildCompoundGraph(this);
		copyBuilder.setSourceSubgraph(subGraph);
		copyBuilder.copyNodes();
		copyBuilder.copyEquivalentEdges();
	}

	public final BaseCompoundGraph getSuperGraph() {
		return this.getRootNode().getGraph();
	}

	public final boolean isInducedSubgraph() {
		return false;
	}

//	public CiNode getLcaNode(CiNode inNode, CiNode outNode) {
//		return this.root.getLowestCommonAncestor(inNode, outNode);
//	}

	final void addNewNode(BaseCompoundNode newNode){
		this.nodeSet.add(newNode);
	}
	
	final void addNewEdge(BaseCompoundEdge newEdge){
		this.edgeSet.add(newEdge);
	}

	public final boolean containsDirectedEdge(IDirectedPair<BaseCompoundNode, BaseCompoundEdge> ends) {
		boolean retVal = false;
		if(ends != null){
			for(BaseCompoundEdge edge : this.edgeSet){
				if(edge.equals(ends)){
					retVal = true;
					break;
				}
			}
		}
		return retVal;
	}

	public final boolean containsConnection(IBasicPair<BaseCompoundNode, BaseCompoundEdge> ends) {
		boolean retVal = false;
		if(ends != null && ends instanceof IDirectedPair){
			IDirectedPair<BaseCompoundNode, BaseCompoundEdge> ciEnds = (IDirectedPair<BaseCompoundNode, BaseCompoundEdge>)ends;
			retVal = containsDirectedEdge(ciEnds);
		}
		return retVal;
	}

	public final void clear() {
		// TODO: implement this!
		throw new UnsupportedOperationException("Implement this method!");
	}
	
	public abstract BaseCompoundNodeFactory nodeFactory();
	
	public abstract BaseChildCompoundEdgeFactory edgeFactory();
}
