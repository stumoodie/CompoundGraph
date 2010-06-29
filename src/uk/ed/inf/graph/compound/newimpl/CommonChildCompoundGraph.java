package uk.ed.inf.graph.compound.newimpl;

import java.util.Iterator;

import org.apache.log4j.Logger;

import uk.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ed.inf.graph.compound.ICompoundChildEdgeFactory;
import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundGraph;
import uk.ed.inf.graph.compound.ICompoundGraphCopyBuilder;
import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.ICompoundGraphMoveBuilder;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.ICompoundNodeFactory;
import uk.ed.inf.graph.compound.ICompoundNodePair;
import uk.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ed.inf.graph.util.IFilterCriteria;
import uk.ed.inf.graph.util.impl.DirectedEdgeSet;
import uk.ed.inf.graph.util.impl.FilteredEdgeSet;
import uk.ed.inf.graph.util.impl.FilteredNodeSet;
import uk.ed.inf.graph.util.impl.NodeSet;

public abstract class CommonChildCompoundGraph implements IChildCompoundGraph {
	private class CombinedElementIterator implements Iterator<ICompoundGraphElement> {
		private final Iterator<? extends ICompoundGraphElement> inNodeIterator;
		private final Iterator<? extends ICompoundGraphElement> outNodeIterator;
		
		public CombinedElementIterator(){
			this.inNodeIterator = edgeSet.iterator();
			this.outNodeIterator = nodeSet.iterator();
		}
		
		
		public boolean hasNext() {
			boolean retVal = this.inNodeIterator.hasNext();
			if(retVal == false){
				retVal = this.outNodeIterator.hasNext();
			}
			return retVal;
		}

		public ICompoundGraphElement next() {
			ICompoundGraphElement retVal = null;
			if(this.inNodeIterator.hasNext()){
				retVal = this.inNodeIterator.next();
			}
			else{
				retVal = this.outNodeIterator.next();
			}
			return retVal;
		}

		public void remove() {
			throw new UnsupportedOperationException("This iterator does not support removal");
		}
		
	}

	
	private static final String DEBUG_PROP_NAME = "uk.ed.inf.graph.compound.base.debugging";
	// added debug checks to graph
	private final boolean debuggingEnabled;
    private final Logger logger = Logger.getLogger(this.getClass());
	private final ICompoundGraphCopyBuilder copyBuilder;
	private final ICompoundGraphMoveBuilder moveBuilder;
	private final FilteredEdgeSet<ICompoundNode, ICompoundEdge> edgeSet;
	private final FilteredNodeSet<ICompoundNode, ICompoundEdge> nodeSet;
	
	protected CommonChildCompoundGraph(){
		this(new CompoundGraphCopyBuilder(), new CompoundGraphMoveBuilder());
	}
		
	protected CommonChildCompoundGraph(ICompoundGraphCopyBuilder copyBuilder, ICompoundGraphMoveBuilder moveBuilder){
		this.debuggingEnabled = Boolean.getBoolean(DEBUG_PROP_NAME);
		this.copyBuilder = copyBuilder;
		this.moveBuilder = moveBuilder;
		this.nodeSet = new FilteredNodeSet<ICompoundNode, ICompoundEdge>(new NodeSet<ICompoundNode, ICompoundEdge>(), new IFilterCriteria<ICompoundNode>(){

			public boolean matched(ICompoundNode testObj) {
				return !testObj.isRemoved();
			}
	
		});
		this.edgeSet = new FilteredEdgeSet<ICompoundNode, ICompoundEdge>(new DirectedEdgeSet<ICompoundNode, ICompoundEdge>(),
				new IFilterCriteria<ICompoundEdge>(){

					public boolean matched(ICompoundEdge testObj) {
						return !testObj.isRemoved();
					}
			
		});
	}
	
	@Override
	public ICompoundGraph getSuperGraph() {
		return (CompoundGraph)this.getRoot().getGraph();
	}

	@Override
	public boolean containsConnection(ICompoundNode thisNode, ICompoundNode thatNode) {
		boolean retVal = false;
		if(thisNode != null && thatNode != null){
			retVal = this.edgeSet.contains(thisNode, thatNode) || this.edgeSet.contains(thatNode, thisNode);
		}
		return retVal;
	}

	@Override
	public boolean containsConnection(ICompoundNodePair ends) {
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
	public boolean containsDirectedEdge(ICompoundNode outNode, ICompoundNode inNode) {
		boolean retVal = false;
		if(inNode != null && outNode != null){
			retVal = this.edgeSet.contains(outNode, inNode);
		}
		return retVal;
	}

	@Override
	public boolean containsEdge(ICompoundEdge edge) {
		boolean retVal = false;
		if(edge != null){
			retVal = this.edgeSet.contains(edge.getIndex());
		}
		return retVal;
	}

	@Override
	public boolean containsEdge(int edgeIdx) {
		return this.edgeSet.contains(edgeIdx);
	}

	@Override
	public boolean containsNode(int nodeIdx) {
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
	public Iterator<ICompoundEdge> edgeIterator() {
		return this.edgeSet.iterator();
	}

	protected final Iterator<ICompoundEdge> unfilteredEdgeIterator() {
		return this.edgeSet.getUnfilteredEdgeSet().iterator();
	}
	
	protected final Iterator<ICompoundNode> unfilteredNodeIterator() {
		return this.nodeSet.getUnfilteredNodeSet().iterator();
	}

	@Override
	public Iterator<ICompoundGraphElement> elementIterator() {
		return new CombinedElementIterator();
	}

	@Override
	public ICompoundEdge getEdge(int edgeIdx) {
		return this.edgeSet.get(edgeIdx);
	}

	@Override
	public ICompoundNode getNode(int nodeIdx) {
		return this.nodeSet.get(nodeIdx);
	}

	@Override
	public int getNumEdges() {
		return this.edgeSet.size();
	}

	@Override
	public int getNumNodes() {
		return this.nodeSet.size();
	}

	@Override
	public Iterator<ICompoundNode> nodeIterator() {
		return this.nodeSet.iterator();
	}

	@Override
	public int numElements() {
		return this.nodeSet.size() + this.edgeSet.size();
	}

	@Override
	public boolean canCopyHere(ISubCompoundGraph subGraph) {
		return subGraph != null && subGraph instanceof ISubCompoundGraph && subGraph.isInducedSubgraph()
		&& subGraph.isConsistentSnapShot() && !subGraph.containsRoot();
	}

	@Override
	public boolean canMoveHere(ISubCompoundGraph subGraph) {
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

	@Override
	public void copyHere(ISubCompoundGraph subGraph) {
		if(this.debuggingEnabled && !canCopyHere(subGraph)) throw new IllegalArgumentException("Cannot copy graph here");
		
		copyBuilder.setDestinatChildCompoundGraph(this);
		copyBuilder.setSourceSubgraph(subGraph);
		copyBuilder.makeCopy();
		notifyCopyOperationComplete(copyBuilder.getSourceSubgraph(), copyBuilder.getCopiedComponents());
	}

	@Override
	public ICompoundChildEdgeFactory edgeFactory() {
		return new CompoundChildEdgeFactory(this.getRoot(), new ICompoundElementRegistration() {
			
			@Override
			public void registerNode(ICompoundNode node) {
			}
			
			@Override
			public void registerEdge(ICompoundEdge edge) {
				edgeSet.add(edge);
			}
		});
	}

	@Override
	public ISubCompoundGraph getCopiedComponents() {
		return this.copyBuilder.getCopiedComponents();
	}

	@Override
	public ISubCompoundGraph getMovedComponents() {
		return this.moveBuilder.getMovedComponents();
	}

	@Override
	public boolean isValid() {
        boolean retVal = true;
        ICompoundGraph graph = this.getSuperGraph();
        ICompoundGraphElement rootNode = this.getRoot();
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
            	ICompoundNodePair pair = edge.getConnectedNodes();
                ICompoundNode inNode = pair.getInNode();
                ICompoundNode outNode = pair.getOutNode();
                if (edge.getChildCompoundGraph().equals(this)) {
                    if (inNode != null && outNode != null) {
                        if(!(inNode.containsEdge(edge)
                                && outNode.containsEdge(edge)
                                && graph.getElementTree().getLowestCommonAncestor(inNode, outNode).equals(rootNode))){
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

	protected abstract boolean hasPassedAdditionalValidation();

	@Override
	public void moveHere(ISubCompoundGraph subGraph) {
		if(this.debuggingEnabled && !canMoveHere(subGraph)) throw new IllegalArgumentException("Cannot move graph here");
		
		moveBuilder.setDestinatChildCompoundGraph(this);
		moveBuilder.setSourceSubgraph(subGraph);
		moveBuilder.makeMove();
		notifyMoveOperationComplete(moveBuilder.getSourceSubgraph(), moveBuilder.getMovedComponents());
	}

	@Override
	public ICompoundNodeFactory nodeFactory() {
		ICompoundNodeFactory fact = new CompoundNodeFactory(this.getRoot(), new ICompoundElementRegistration() {
			
			@Override
			public void registerNode(ICompoundNode node) {
				nodeSet.add(node);
			}
			
			@Override
			public void registerEdge(ICompoundEdge edge) {
			}
		});
		return fact;
	}


	protected abstract void notifyCopyOperationComplete(ISubCompoundGraph originalSubgraph,
			ISubCompoundGraph copiedSubgraph);

	protected abstract void notifyMoveOperationComplete(ISubCompoundGraph originalSubgraph,
			ISubCompoundGraph movedSubgraph);

}
