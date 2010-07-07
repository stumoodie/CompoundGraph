/*
Copyright 2009, Court of the University of Edinburgh

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License. 
*/
package uk.ed.inf.graph.compound.base;

import java.util.Iterator;

import org.apache.log4j.Logger;

import uk.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.CompoundNodePair;
import uk.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ed.inf.graph.compound.impl.ChildCompoundEdgeFactory;
import uk.ed.inf.graph.compound.impl.CompoundNodeFactory;
import uk.ed.inf.graph.util.IDirectedEdgeSet;
import uk.ed.inf.graph.util.IEdgeSet;
import uk.ed.inf.graph.util.IFilterCriteria;
import uk.ed.inf.graph.util.INodeSet;
import uk.ed.inf.graph.util.impl.FilteredEdgeSet;
import uk.ed.inf.graph.util.impl.FilteredNodeSet;

public abstract class BaseChildCompoundGraph implements IChildCompoundGraph {
	private static final String DEBUG_PROP_NAME = "uk.ed.inf.graph.compound.base.debugging";
	// added debug checks to graph
	private final boolean debuggingEnabled;
	
    private final Logger logger = Logger.getLogger(this.getClass());
	private final BaseGraphCopyBuilder copyBuilder;
	private final BaseGraphMoveBuilder moveBuilder;
	private FilteredEdgeSet<ICompoundNode, ICompoundEdge> edgeSet;
	private FilteredNodeSet<ICompoundNode, ICompoundEdge> nodeSet;
	
	protected BaseChildCompoundGraph(BaseGraphCopyBuilder copyBuilder , BaseGraphMoveBuilder moveBuilder ){
		if(copyBuilder == null || moveBuilder == null ) throw new IllegalArgumentException("builder cannot be null");
		
		this.debuggingEnabled = Boolean.getBoolean(DEBUG_PROP_NAME);
		this.copyBuilder = new BaseGraphCopyBuilder();
		this.moveBuilder = new BaseGraphMoveBuilder();
	}

	protected final void createNodeSet(INodeSet<ICompoundNode, ICompoundEdge> nodeSet){
		this.nodeSet = new FilteredNodeSet<ICompoundNode, ICompoundEdge>(nodeSet, new IFilterCriteria<ICompoundNode>(){

			public boolean matched(ICompoundNode testObj) {
				return !testObj.isRemoved();
			}
	
		});
	}
	
	protected final void createEdgeSet(IDirectedEdgeSet<ICompoundNode, ICompoundEdge> edgeSet){
		this.edgeSet = new FilteredEdgeSet<ICompoundNode, ICompoundEdge>(edgeSet,
				new IFilterCriteria<ICompoundEdge>(){

					public boolean matched(ICompoundEdge testObj) {
						return !testObj.isRemoved();
					}
			
		});
	}
	
	protected final IEdgeSet<ICompoundNode, ICompoundEdge> getEdgeSet(){
		return this.edgeSet;
	}
	
	protected final INodeSet<ICompoundNode, ICompoundEdge> getNodeSet() {
		return this.nodeSet;
	}
	
	@Override
	public abstract BaseCompoundGraphElement getRoot();

	@Override
	public boolean containsDirectedEdge(ICompoundNode outNode, ICompoundNode inNode) {
		boolean retVal = false;
		if(inNode != null && outNode != null){
			retVal = this.edgeSet.contains(outNode, inNode);
		}
		return retVal;
	}

	@Override
	public final boolean containsConnection(ICompoundNode thisNode, ICompoundNode thatNode) {
		boolean retVal = false;
		if(thisNode != null && thatNode != null){
			retVal = this.edgeSet.contains(thisNode, thatNode) || this.edgeSet.contains(thatNode, thisNode);
		}
		return retVal;
	}

	@Override
	public final boolean containsEdge(CompoundNodePair ends) {
		boolean retVal = false;
		if(ends != null){
			ICompoundNode outNode = ends.getOutNode();
			ICompoundNode inNode = ends.getInNode();
			// check that at least one node belongs to this graph, if so then we
			// can be sure that the other node and edge will.
			if(outNode.getGraph().equals(this)){
				retVal = outNode.hasOutEdgeTo(inNode) || inNode.hasOutEdgeTo(outNode);
			}
		}
		return retVal;
	}

	@Override
	public final boolean containsEdge(ICompoundEdge edge) {
		boolean retVal = false;
		if(edge != null){
			retVal = this.edgeSet.contains(edge.getIndex());
		}
		return retVal;
	}

	@Override
	public final boolean containsEdge(int edgeIdx) {
		return this.edgeSet.contains(edgeIdx);
	}

	@Override
	public final boolean containsNode(int nodeIdx) {
		return this.nodeSet.contains(nodeIdx);
	}

	@Override
	public boolean containsNode(ICompoundNode node) {
		boolean retVal = false;
		if(node != null){
			retVal = containsNode(node.getIndex());
		}
		return retVal;
	}

	@Override
	public ICompoundEdge getEdge(int edgeIdx) {
		return this.edgeSet.get(edgeIdx);
	}

	@Override
	public final Iterator<ICompoundEdge> edgeIterator() {
		return this.edgeSet.iterator();
	}

	protected final Iterator<ICompoundEdge> unfilteredEdgeIterator() {
		return this.edgeSet.getUnfilteredEdgeSet().iterator();
	}
	
	protected final Iterator<ICompoundNode> unfilteredNodeIterator() {
		return this.nodeSet.getUnfilteredNodeSet().iterator();
	}

	@Override
	public ICompoundNode getNode(int nodeIdx) {
		return this.nodeSet.get(nodeIdx);
	}

	@Override
	public final Iterator<ICompoundNode> nodeIterator() {
		return this.nodeSet.iterator();
	}

	@Override
	public final int getNumEdges() {
		return this.edgeSet.size();
	}

	@Override
	public final int getNumNodes() {
		return this.nodeSet.size();
	}

	@SuppressWarnings("unchecked")
	public final boolean canCopyHere(ISubCompoundGraph subGraph) {
		return subGraph != null && subGraph instanceof ISubCompoundGraph && subGraph.isInducedSubgraph()
			&& subGraph.isConsistentSnapShot() && !subGraph.containsRoot();
	}

	
	@Override
	public final void copyHere(ISubCompoundGraph subGraph) {
		if(this.debuggingEnabled && !canCopyHere(subGraph)) throw new IllegalArgumentException("Cannot copy graph here");
		
		copyBuilder.setDestinatChildCompoundGraph(this);
		copyBuilder.setSourceSubgraph(subGraph);
		copyBuilder.makeCopy();
		notifyCopyOperationComplete(copyBuilder.getSourceSubgraph(), copyBuilder.getCopiedComponents());
	}

	@Override
	public BaseCompoundGraph getSuperGraph() {
		return this.getRoot().getGraph();
	}

//	@Override
//	public final boolean isInducedSubgraph() {
//		return false;
//	}

	protected void addNewNode(ICompoundNode newNode){
		this.nodeSet.add(newNode);
	}
	
	protected void addNewEdge(ICompoundEdge newEdge){
		this.edgeSet.add(newEdge);
	}

	@Override
	public final boolean containsDirectedEdge(CompoundNodePair ends) {
		boolean retVal = false;
		if(ends != null){
			for(ICompoundEdge edge : this.edgeSet){
				if(edge.equals(ends)){
					retVal = true;
					break;
				}
			}
		}
		return retVal;
	}

	@Override
	public final boolean containsConnection(CompoundNodePair ends) {
		boolean retVal = false;
		if(ends != null){
			for(ICompoundEdge edge : this.edgeSet){
				if(edge.equals(ends)){
					retVal = true;
					break;
				}
			}
		}
		return retVal;
	}

	@Override
	public BaseChildCompoundEdgeFactory edgeFactory() {
		return new BaseChildCompoundEdgeFactory(this.getRoot());
	}

	@Override
	public BaseCompoundNodeFactory nodeFactory() {
		return new BaseCompoundNodeFactory(this.getRoot());
	}

	/**
	 * Tests whether the subGraph can be moved to this graph. To be true the subgraph must be an induced subgraph
	 *  that is a consistent of the super graph. It must also be not null and belong to the
	 *  same graph as this one. The subgraph must also be valid.
	 *  Also no nodes in the induced sub-graph of <code>subGraph</code> can be children.
	 *  In addition at least one node must be moving to a new child graph.
	 *  of this child compound graph. 
	 * @param subGraph the subgraph to test, can be null. 
	 * @return true if the subgraph is valid to copy from, false otherwise.
	 */
	@Override
	public boolean canMoveHere(ISubCompoundGraph iSubGraph){
		BaseSubCompoundGraph subGraph = (BaseSubCompoundGraph)iSubGraph;
		boolean retVal = subGraph != null && subGraph.getSuperGraph().equals(this.getSuperGraph())
			&& subGraph.isInducedSubgraph() && subGraph.isConsistentSnapShot()
			&& !subGraph.containsElement(this.getRoot());
		if(retVal){
			retVal = false;
			Iterator<? extends ICompoundNode> topNodeIter = subGraph.topNodeIterator();
			while(topNodeIter.hasNext()){
				ICompoundNode topNode = topNodeIter.next();
				if(!topNode.getParent().equals(this.getRoot())){
					retVal = true;
				}
			}
		}
		return retVal;
	}

	/**
	 * Moves a subgraph into this graph. It does this by creating a new set of
	 * nodes in this subgraph and removing the nodes defined in <code>subGraph</code>.
	 * The new nodes from the move can be found in <code>getMovedComponents()</code> 
	 * and the removed nodes will be found in <code>subGraph</code>.
	 * At least one node must be moving to a new child graph. Only those nodes and edges
	 * that have a new owning child graph will be moved, other nodes will remain where they are.
	 * @param subGraph the subgraph to move.
	 * @throws IllegalArgumentException if <code>canMoveHere(subGraph) == false</code>.
	 */
	@Override
	public void moveHere(ISubCompoundGraph subGraph){
		if(this.debuggingEnabled && !canMoveHere(subGraph)) throw new IllegalArgumentException("Cannot move graph here");
		
		moveBuilder.setDestinatChildCompoundGraph(this);
		moveBuilder.setSourceSubgraph(subGraph);
		moveBuilder.makeMove();
		notifyMoveOperationComplete(moveBuilder.getSourceSubgraph(), moveBuilder.getMovedComponents());
	}
	
	protected abstract void notifyCopyOperationComplete(ISubCompoundGraph originalSubgraph,
			ISubCompoundGraph copiedSubgraph);

	protected abstract void notifyMoveOperationComplete(ISubCompoundGraph originalSubgraph,
			ISubCompoundGraph movedSubgraph);

	/**
	 * Retrieves the nodes and edges created in this graph by the last copy operation. The subgraph
	 * is <b>not</b> guaranteed to be a consistent snapshot of this graph.   If not copy operation has
	 * been performed then an empty subset will be returned.
	 * @return the subgraph of copied components, or an empty subset of not copy operation has been perfromed. 
	 */
	@Override
	public BaseSubCompoundGraph getMovedComponents(){
		return this.moveBuilder.getMovedComponents();
	}
	
//	/**
//	 * Tests if the subgraph contains this child graph. If it
//	 * does then the move is recursive and cannot be performed.
//	 * @param subgraph the subgraph to test, which cannot be null.
//	 * @return true if the subgraph intersects with this child graph, false otherwise.
//	 */
//	private boolean intersects(ISubCompoundGraph subgraph){
//		Iterator<? extends ICompoundNode>  nodesIterator = this.nodeIterator() ;
//		
//		boolean retVal = false ;
//		
//		while (nodesIterator.hasNext()){
//			retVal =  subgraph.containsNode(nodesIterator.next().getIndex()) ;
//		}
//		if ( !retVal){
//			while(edgesIterator.hasNext()){
//				retVal =  subgraph.containsEdge(edgesIterator.next().getIndex()) ;
//			}
//		}
//		
//		return retVal ;
//	}

	@Override
	public BaseSubCompoundGraph getCopiedComponents(){
		return this.copyBuilder.getCopiedComponents();
	}
	
	/**
	 * A hook method that should be used to provide addition validation for the class inheriting from 
	 * this one.
	 * @return
	 */
	protected abstract boolean hasPassedAdditionalValidation();

	@Override
	public boolean isValid() {
        boolean retVal = true;
        BaseCompoundGraph graph = this.getSuperGraph();
        BaseCompoundGraphElement rootNode = this.getRoot();
        retVal = rootNode.getChildCompoundGraph().equals(this);
        if (retVal) {
            for (ICompoundNode node : this.nodeSet.getUnfilteredNodeSet()) {
                if(!(retVal = node.getParent().getChildCompoundGraph().equals(this)
                        && node.getGraph().equals(graph)
                        && node.getParent().equals(rootNode))){
                    logger.error("Graph Invalid: node: " + node
                            + " has inconsistent relationships or belongs to another graph");
                    retVal = false;
                    break;
                }
                else if(!node.getChildCompoundGraph().isValid()){
                    logger.error("Invalid child graph: " + node);
                    retVal = false;
                    break;
                }
            }
        }
        if (retVal) {
            for (ICompoundEdge edge : this.edgeSet.getUnfilteredEdgeSet()) {
            	CompoundNodePair pair = edge.getConnectedNodes();
                BaseCompoundNode inNode = (BaseCompoundNode)pair.getInNode();
                BaseCompoundNode outNode = (BaseCompoundNode)pair.getOutNode();
                if (edge.getChildCompoundGraph().equals(this)) {
                    if (inNode != null && outNode != null) {
                        if(!(inNode.getEdgeInList().getUnfilteredEdgeSet().contains(edge)
                                && outNode.getEdgeOutList().getUnfilteredEdgeSet().contains(edge)
                                && graph.getLcaNode(inNode, outNode).equals(rootNode))){
                            logger.error("Graph invalid: edge: " + edge
                                    + " has inconsistent nodes or has the wrong owning child graph (not LCA).");
                            retVal = false;
                            break;
                        }
                    } else {
                        logger
                                .error("Graph invalid: edge: "
                                        + edge
                                        + " has one or node null nodes assigned to it.");
                        retVal = false;
                        break;
                    }
                } else {
                    logger.equals("Graph Invalid: edge " + edge
                            + " has in inconsistent child graph assignment");
                    retVal = false;
                    break;
                }
            }
        }
		if(logger.isDebugEnabled()){
			logger.debug("Child Compound Graph: " + this + " has validity = "
					+ retVal);
		}
        if(!this.hasPassedAdditionalValidation()){
            logger.error("Graph Invalid: addition validation from super class has failed");
        	retVal = false;
        }
        return retVal;
    }

	protected abstract void notifyNewNode(ICompoundNode newNode);

	protected abstract void notifyNewEdge(ICompoundEdge newEdge);

}
