package uk.ed.inf.graph.compound.base;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import uk.ed.inf.graph.basic.IBasicPair;
import uk.ed.inf.graph.basic.ISubgraphAlgorithms;
import uk.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ed.inf.graph.directed.IDirectedPair;
import uk.ed.inf.graph.util.IDirectedEdgeSet;
import uk.ed.inf.graph.util.IEdgeSet;
import uk.ed.inf.graph.util.IFilterCriteria;
import uk.ed.inf.graph.util.INodeSet;
import uk.ed.inf.graph.util.SubgraphAlgorithms;
import uk.ed.inf.graph.util.impl.FilteredEdgeSet;
import uk.ed.inf.graph.util.impl.FilteredNodeSet;
import uk.ed.inf.graph.util.impl.NodeTreeIterator;
import uk.ed.inf.tree.GeneralTree;
import uk.ed.inf.tree.ITree;
import uk.ed.inf.tree.ITreeNodeAction;

public abstract class BaseSubCompoundGraph implements ISubCompoundGraph<BaseCompoundNode, BaseCompoundEdge> {
	private INodeSet<BaseCompoundNode, BaseCompoundEdge> topNodeSet;
	private IEdgeSet<BaseCompoundNode, BaseCompoundEdge> edgeSet;
	private Set<Integer> initialNodeSet = new HashSet<Integer> () ;
	private Set<Integer> initialEdgeSet = new HashSet<Integer> () ;
	
	protected BaseSubCompoundGraph(){
//		this.nodeSet = new NodeSet<BaseCompoundNode, BaseCompoundEdge>();
//		this.edgeSet = new EdgeSet<BaseCompoundNode, BaseCompoundEdge>();
	}
	
	protected final void createNodeSet(INodeSet<BaseCompoundNode, BaseCompoundEdge> nodeSet){
		this.topNodeSet = new FilteredNodeSet<BaseCompoundNode, BaseCompoundEdge>(nodeSet, new IFilterCriteria<BaseCompoundNode>(){

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
	
	public abstract BaseCompoundGraph getSuperGraph();

	public boolean isInducedSubgraph() {
		ISubgraphAlgorithms<BaseCompoundNode, BaseCompoundEdge> alg = new SubgraphAlgorithms<BaseCompoundNode, BaseCompoundEdge>(this);
		return alg.isInducedSubgraph();
	}

	public boolean containsConnection(BaseCompoundNode thisNode, BaseCompoundNode thatNode) {
		boolean retVal = false;
		if(thisNode != null && thatNode != null){
			if(this.containsNode(thisNode) && this.containsNode(thatNode)){
				retVal = thisNode.hasEdgeWith(thatNode);
			}
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
		boolean retVal = false;
		for(BaseCompoundNode topNode : this.topNodeSet){
			if(!retVal){
				ITree<BaseCompoundNode> topTree = new GeneralTree<BaseCompoundNode>(topNode);
				TopNodeContainsIndexAction queryAction = new TopNodeContainsIndexAction(nodeIdx); 
				topTree.levelOrderTreeWalker(queryAction).visitTree();
				retVal = queryAction.wasMatchFound();
			}
			else{
				break;
			}
		}
		return retVal;
	}

	public boolean containsNode(BaseCompoundNode node) {
		boolean retVal = false;
		for(BaseCompoundNode topNode : this.topNodeSet){
			if(!retVal){
				ITree<BaseCompoundNode> topTree = new GeneralTree<BaseCompoundNode>(topNode);
				TopNodeContainsNodeAction queryAction = new TopNodeContainsNodeAction(node); 
				topTree.levelOrderTreeWalker(queryAction).visitTree();
				retVal = queryAction.wasMatchFound();
			}
			else{
				break;
			}
		}
		return retVal;
	}

	public BaseCompoundEdge getEdge(int edgeIdx) {
		return this.edgeSet.get(edgeIdx);
	}

	public Iterator<BaseCompoundEdge> edgeIterator() {
		return this.edgeSet.iterator();
	}

	public BaseCompoundNode getNode(int nodeIdx) {
		BaseCompoundNode retVal = null;
		for(BaseCompoundNode topNode : this.topNodeSet){
			if(retVal == null){
				ITree<BaseCompoundNode> topTree = new GeneralTree<BaseCompoundNode>(topNode);
				TopNodeFindNodeByIdxAction queryAction = new TopNodeFindNodeByIdxAction(nodeIdx); 
				topTree.levelOrderTreeWalker(queryAction).visitTree();
				retVal = queryAction.getMatchedNode();
			}
			else{
				break;
			}
		}
		if(retVal == null){
			throw new IllegalArgumentException("Node matching nodeIdx is not present");
		}
		return retVal;
	}

	public Iterator<BaseCompoundNode> nodeIterator() {
		return new NodeTreeIterator<BaseCompoundNode, BaseCompoundEdge>(this.topNodeSet.iterator());
	}

	public int getNumEdges() {
		int count = 0;
		Iterator<BaseCompoundEdge> iter = this.edgeIterator();
		while(iter.hasNext()){
			iter.next();
			count++;
		}
		return count;
	}

	public int getNumNodes() {
		int count = 0;
		Iterator<BaseCompoundNode> iter = this.nodeIterator();
		while(iter.hasNext()){
			iter.next();
			count++;
		}
		return count;
	}

	void addTopNode(BaseCompoundNode newNode){
		this.topNodeSet.add(newNode);
	}
	
	void addEdge(BaseCompoundEdge newEdge){
		
		this.edgeSet.add(newEdge);
	}
	
	void buildComplete(){
		Iterator<BaseCompoundNode> nodeIter = this.nodeIterator() ;
		while ( nodeIter.hasNext())
		{
			BaseCompoundNode node = nodeIter.next() ;
			this.initialNodeSet.add(node.getIndex()) ;
		}
		
		Iterator<BaseCompoundEdge> edgeIter = this.edgeIterator() ;
		while ( edgeIter.hasNext())
		{
			BaseCompoundEdge edge = edgeIter.next() ;
			this.initialEdgeSet.add(edge.getIndex()) ;
		}

	}

	public boolean containsDirectedEdge(BaseCompoundNode outNode, BaseCompoundNode inNode) {
		boolean retVal = false;
		if(outNode != null && inNode != null){
			retVal = this.edgeSet.contains(outNode, inNode);
		}
		return retVal;
	}

	public boolean isConsistentSnapShot() {
		Set <Integer> cloneOfInitialNodes = new HashSet <Integer> ( this.initialNodeSet) ;
		Set <Integer> cloneOfInitialEdges = new HashSet <Integer> ( this.initialEdgeSet) ;
		
		Iterator <BaseCompoundNode> nodeIter = this.nodeIterator() ;
		Iterator <BaseCompoundEdge> edgeIter = this.edgeIterator() ;
		
		while ( nodeIter.hasNext())
		{
			cloneOfInitialNodes.remove(nodeIter.next().getIndex()) ;
		}
		
		while ( edgeIter.hasNext())
		{
			cloneOfInitialEdges.remove(edgeIter.next().getIndex()) ;
		}		
		
		return ( cloneOfInitialEdges.isEmpty() && cloneOfInitialNodes.isEmpty()) ;
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
			retVal = outNode.hasOutEdgeTo(inNode);
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
			retVal = this.containsConnection(oneNode, twoNode);
		}
		return retVal;
	}

	public Iterator<BaseCompoundNode> topNodeIterator(){
		return this.topNodeSet.iterator();
	}
	
	public int getNumTopNodes(){
		return this.topNodeSet.size();
	}
	
	/**
	 * Checks if this SubGraph contains the RootNode of the CompoundGraph.
	 * @return true if the rootNode is contained in this SubGraph.
	 */
	public boolean containsRoot () 
	{
		 return this.containsNode(this.getSuperGraph().getRootNode()) ;
	}
	
	
	private static class TopNodeContainsIndexAction implements ITreeNodeAction<BaseCompoundNode>{ 
		private final int query;
		private boolean queryMatched = false;
		
		public TopNodeContainsIndexAction(int query){
			this.query = query;
		}

		public boolean canContinue() {
			return !this.queryMatched;
		}

		public void visit(BaseCompoundNode node) {
			if(node.getIndex() == query){
				this.queryMatched = true;
			}
		}
		
		public boolean wasMatchFound(){
			return this.queryMatched;
		}
	}

	private static class TopNodeFindNodeByIdxAction implements ITreeNodeAction<BaseCompoundNode>{ 
		private final int query;
		private BaseCompoundNode queryMatch = null;
		
		public TopNodeFindNodeByIdxAction(int query){
			this.query = query;
		}

		public BaseCompoundNode getMatchedNode() {
			return this.queryMatch;
		}

		public boolean canContinue() {
			return this.queryMatch == null;
		}

		public void visit(BaseCompoundNode node) {
			if(node.getIndex() == query){
				this.queryMatch = node;
			}
		}
		
		public boolean wasMatchFound(){
			return this.queryMatch != null;
		}
	}

	private static class TopNodeContainsNodeAction implements ITreeNodeAction<BaseCompoundNode>{ 
		private final BaseCompoundNode query;
		private boolean queryMatched = false;
		
		public TopNodeContainsNodeAction(BaseCompoundNode query){
			this.query = query;
		}

		public boolean canContinue() {
			return !this.queryMatched;
		}

		public void visit(BaseCompoundNode node) {
			if(node.equals(query)){
				this.queryMatched = true;
			}
		}
		
		public boolean wasMatchFound(){
			return this.queryMatched;
		}
	}
}
