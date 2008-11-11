package uk.ed.inf.graph.compound.base;

import java.util.Iterator;

import uk.ed.inf.graph.basic.IBasicPair;
import uk.ed.inf.graph.compound.IChildCompoundGraph;
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
	private final BaseGraphCopyBuilder copyBuilder;
	private final BaseGraphMoveBuilder moveBuilder;
	private FilteredEdgeSet<BaseCompoundNode, BaseCompoundEdge> edgeSet;
	private FilteredNodeSet<BaseCompoundNode, BaseCompoundEdge> nodeSet;
	
	protected BaseChildCompoundGraph(BaseGraphCopyBuilder copyBuilder , BaseGraphMoveBuilder moveBuilder ){
		if(copyBuilder == null || moveBuilder == null ) throw new IllegalArgumentException("builder cannot be null");
		
		this.copyBuilder = copyBuilder;
		this.moveBuilder = moveBuilder ;
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

	public final boolean containsConnection(BaseCompoundNode thisNode, BaseCompoundNode thatNode) {
		boolean retVal = false;
		if(thisNode != null && thatNode != null){
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

	public BaseCompoundEdge getEdge(int edgeIdx) {
		return this.edgeSet.get(edgeIdx);
	}

	public final Iterator<BaseCompoundEdge> edgeIterator() {
		return this.edgeSet.iterator();
	}

	protected final Iterator<BaseCompoundEdge> unfilteredEdgeIterator() {
		return this.edgeSet.getUnfilteredEdgeSet().iterator();
	}
	
	protected final Iterator<BaseCompoundNode> unfilteredNodeIterator() {
		return this.nodeSet.getUnfilteredNodeSet().iterator();
	}

	public BaseCompoundNode getNode(int nodeIdx) {
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

	public final boolean canCopyHere(ISubCompoundGraph<? extends BaseCompoundNode, ? extends BaseCompoundEdge> subGraph) {
		return subGraph != null && subGraph instanceof ISubCompoundGraph && subGraph.isInducedSubgraph()
			&& subGraph.isConsistentSnapShot() && !subGraph.containsRoot();
	}

	
	public final void copyHere(ISubCompoundGraph<? extends BaseCompoundNode, ? extends BaseCompoundEdge> subGraph) {
		if(!canCopyHere(subGraph)) throw new IllegalArgumentException("Cannot copy graph here");
		
		copyBuilder.setDestinatChildCompoundGraph(this);
		copyBuilder.setSourceSubgraph(subGraph);
		copyBuilder.makeCopy();
	}

	public BaseCompoundGraph getSuperGraph() {
		return this.getRootNode().getGraph();
	}

	public final boolean isInducedSubgraph() {
		return false;
	}

	final void addNewNode(BaseCompoundNode newNode){
		this.nodeSet.add(newNode);
	}
	
	final void addNewEdge(BaseCompoundEdge newEdge){
		this.edgeSet.add(newEdge);
	}

	public final boolean containsDirectedEdge(IDirectedPair<? extends BaseCompoundNode, ? extends BaseCompoundEdge> ends) {
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

	public final boolean containsConnection(IBasicPair<? extends BaseCompoundNode, ? extends BaseCompoundEdge> ends) {
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

	public abstract BaseCompoundNodeFactory nodeFactory();
	
	public abstract BaseChildCompoundEdgeFactory edgeFactory();

	/**
	 * Tests whether the subGraph can be moved to this graph. To be true the subgraph must be an induced subgraph
	 *  that is a consistent of the super graph. It must also be not null and belong to the
	 *  same graph as this one. The subgraph must also be valid.
	 *  Also no nodes in the induced sub-graph of <code>subGraph</code> can be children
	 *  of this child compound graph. 
	 * @param subGraph the subgraph to test, can be null. 
	 * @return true if the subgraph is valid to copy from, false otherwise.
	 */
	public boolean canMoveHere(ISubCompoundGraph<? extends BaseCompoundNode, ? extends BaseCompoundEdge> subGraph){
		boolean retVal = subGraph != null && subGraph.getSuperGraph().equals(this.getSuperGraph())
			&& subGraph.isInducedSubgraph() && subGraph.isConsistentSnapShot();
		if(retVal){
			retVal = !this.intersects(subGraph);
		}
		return retVal;
	}

	/**
	 * Moves a subgraph into this graph. It does this by creating a new set of
	 * nodes in this subgraph and removing the nodes defined in <code>subGraph</code>.
	 * The new nodes from the move can be found in <code>getMovedComponents()</code> 
	 * and the removed nodes will be found in <code>subGraph</code>.
	 * @param subGraph the subgraph to move.
	 * @throws IllegalArgumentException if <code>canMoveHere(subGraph) == false</code>.
	 */
	public void moveHere(ISubCompoundGraph<? extends BaseCompoundNode, ? extends BaseCompoundEdge> subGraph){
		if(!canMoveHere(subGraph)) throw new IllegalArgumentException("Cannot move graph here");
		
		moveBuilder.setDestinatChildCompoundGraph(this);
		moveBuilder.setSourceSubgraph(subGraph);
		moveBuilder.makeMove();
	}
	
	/**
	 * Retrieves the nodes and edges created in this graph by the last copy operation. The subgraph
	 * is <b>not</b> guaranteed to be a consistent snapshot of this graph.   If not copy operation has
	 * been performed then an empty subset will be returned.
	 * @return the subgraph of copied components, or an empty subset of not copy operation has been perfromed. 
	 */
	public BaseSubCompoundGraph getMovedComponents(){
		return this.moveBuilder.getMovedComponents();
	}
	
	/**
	 * Tests if the subgraph contains nodes or edges that are contained by this
	 * child graph or its children.
	 * @param subgraph the subgraph to test, which can be null.
	 * @return true if the subgraph intersects with this child graph, false otherwise.
	 */
	public final boolean intersects(ISubCompoundGraph<? extends BaseCompoundNode, ? extends BaseCompoundEdge> subgraph){
		Iterator<? extends BaseCompoundNode>  nodesIterator = this.nodeIterator() ;
		Iterator<? extends BaseCompoundEdge>  edgesIterator = this.edgeIterator() ;
		
		boolean retVal = false ;
		
		while (nodesIterator.hasNext()){
			retVal =  subgraph.containsNode(nodesIterator.next().getIndex()) ;
		}
		if ( !retVal){
			while(edgesIterator.hasNext()){
				retVal =  subgraph.containsEdge(edgesIterator.next().getIndex()) ;
			}
		}
		
		return retVal ;
	}

	public BaseSubCompoundGraph getCopiedComponents(){
		return this.copyBuilder.getCopiedComponents();
	}

}
