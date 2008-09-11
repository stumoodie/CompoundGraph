package uk.ed.inf.graph.compound.impl;

import uk.ed.inf.graph.compound.archetypal.ArchetypalChildCompoundGraph;


public class ChildCompoundGraph extends ArchetypalChildCompoundGraph {
//	private final CompoundNode root;
//	private final IEdgeSet<CompoundNode, CompoundEdge> edgeSet;
//	private final INodeSet<CompoundNode, CompoundEdge> nodeSet;
	private final CompoundNodeFactory nodeFactory;
	private final ChildCompoundEdgeFactory edgeFactory;
	
	ChildCompoundGraph(CompoundNode root){
		super(root, new CompoundGraphCopyBuilder());
//		if(root == null) throw new IllegalArgumentException("root cannot be null");
//		
//		this.root = root;
//		this.edgeSet = new FilteredEdgeSet<CompoundNode, CompoundEdge>(new EdgeSet<CompoundNode, CompoundEdge>(),
//				new IFilterCriteria<CompoundEdge>(){
//
//					public boolean matched(CompoundEdge testObj) {
//						return !testObj.isRemoved();
//					}
//			
//		});
//		this.nodeSet = new FilteredNodeSet<CompoundNode, CompoundEdge>(new NodeSet<CompoundNode, CompoundEdge>(),
//				new IFilterCriteria<CompoundNode>(){
//
//			public boolean matched(CompoundNode testObj) {
//				return !testObj.isRemoved();
//			}
//	
//		});
		this.nodeFactory = new CompoundNodeFactory(this.getRootNode());
		this.edgeFactory = new ChildCompoundEdgeFactory(this.getRootNode());
	}
	
	@Override
	public CompoundNode getRootNode() {
		return (CompoundNode)super.getRootNode();
	}
//
//	public boolean containsDirectedEdge(CompoundNode iInNode, CompoundNode iOutNode) {
//		boolean retVal = false;
//		if(iInNode != null && iOutNode != null){
//			CompoundNode inNode = (CompoundNode)iInNode;
//			CompoundNode outNode = (CompoundNode)iOutNode;
//			retVal = this.edgeSet.contains(inNode, outNode);
//		}
//		return retVal;
//	}
//
//	public boolean canCreateEdges() {
//		return false;
//	}
//
//	public boolean canCreateNodes() {
//		return true;
//	}
//
//	public boolean containsConnection(CompoundNode iThisNode, CompoundNode iThatNode) {
//		boolean retVal = false;
//		if(iThisNode != null && iThatNode != null){
//			CompoundNode thisNode = (CompoundNode)iThisNode;
//			CompoundNode thatNode = (CompoundNode)iThatNode;
//			retVal = this.edgeSet.contains(thisNode, thatNode) || this.edgeSet.contains(thatNode, thisNode);
//		}
//		return retVal;
//	}
//
//	public boolean containsEdge(CompoundEdge edge) {
//		boolean retVal = false;
//		if(edge != null){
//			IDirectedPair<CompoundNode, CompoundEdge> ends = edge.getConnectedNodes();
//			retVal = this.containsConnection(ends.getOutNode(), ends.getInNode());
//		}
//		return retVal;
//	}
//
//	public boolean containsEdge(int edgeIdx) {
//		return this.edgeSet.contains(edgeIdx);
//	}
//
//	public boolean containsNode(int nodeIdx) {
//		return this.nodeSet.contains(nodeIdx);
//	}
//
//	public boolean containsNode(CompoundNode node) {
//		boolean retVal = false;
//		if(node != null){
//			retVal = containsNode(node.getIndex());
//		}
//		return retVal;
//	}

	public ChildCompoundEdgeFactory edgeFactory() {
		return this.edgeFactory;
	}

	public CompoundNodeFactory nodeFactory() {
		return this.nodeFactory;
	}

//	public CompoundEdge getEdge(int edgeIdx) {
//		return this.edgeSet.get(edgeIdx);
//	}
//
//	public Iterator<CompoundEdge> edgeIterator() {
//		return this.edgeSet.iterator();
//	}
//
//	public CompoundNode getNode(int nodeIdx) {
//		return this.nodeSet.get(nodeIdx);
//	}
//
//	public Iterator<CompoundNode> nodeIterator() {
//		return this.nodeSet.iterator();
//	}
//
//	public int getNumEdges() {
//		return this.edgeSet.size();
//	}
//
//	public int getNumNodes() {
//		return this.nodeSet.size();
//	}
//
//	public boolean canCopyHere(IBasicSubgraph<CompoundNode, CompoundEdge> subGraph) {
//		return subGraph != null && subGraph instanceof ISubCompoundGraph && subGraph.isInducedSubgraph()
//			&& subGraph.isConsistentSnapShot();
//	}
//
//	
//	public void copyHere(IBasicSubgraph<CompoundNode, CompoundEdge> iSubGraph) {
//		if(!canCopyHere(iSubGraph)) throw new IllegalArgumentException("Cannot copy graph here");
//		ISubCompoundGraph<CompoundNode, CompoundEdge> subGraph = (ISubCompoundGraph<CompoundNode, CompoundEdge>)iSubGraph;
//		
//		ChildCompoundGraphBuilder copyBuilder = new ChildCompoundGraphBuilder(this, subGraph);
//		copyBuilder.copyNodes();
//		copyBuilder.copyEquivalentEdges();
//	}
//
//	public CompoundGraph getSuperGraph() {
//		return this.root.getGraph();
//	}
//
//	public boolean isInducedSubgraph() {
//		return false;
//	}
//
////	public CiNode getLcaNode(CiNode inNode, CiNode outNode) {
////		return this.root.getLowestCommonAncestor(inNode, outNode);
////	}
//
//	void addNewNode(CompoundNode newNode){
//		this.nodeSet.add(newNode);
//	}
//	
//	void addNewEdge(CompoundEdge newEdge){
//		this.edgeSet.add(newEdge);
//	}
//
//	public boolean containsDirectedEdge(IDirectedPair<CompoundNode, CompoundEdge> ends) {
//		boolean retVal = false;
//		if(ends != null){
//			for(CompoundEdge edge : this.edgeSet){
//				if(edge.equals(ends)){
//					retVal = true;
//					break;
//				}
//			}
//		}
//		return retVal;
//	}
//
//	public boolean containsConnection(IBasicPair<CompoundNode, CompoundEdge> ends) {
//		boolean retVal = false;
//		if(ends != null && ends instanceof IDirectedPair){
//			IDirectedPair<CompoundNode, CompoundEdge> ciEnds = (IDirectedPair<CompoundNode, CompoundEdge>)ends;
//			retVal = containsDirectedEdge(ciEnds);
//		}
//		return retVal;
//	}
//
//	public void clear() {
//		// TODO Auto-generated method stub
//		
//	}
}
