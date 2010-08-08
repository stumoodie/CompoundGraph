package uk.ac.ed.inf.graph.compound.newimpl;

import java.util.Iterator;

import uk.ac.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundChildEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundGraphCopyBuilder;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ICompoundGraphMoveBuilder;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.ICompoundNodeFactory;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ac.ed.inf.graph.util.IFilterCriteria;
import uk.ac.ed.inf.graph.util.impl.DirectedEdgeSet;
import uk.ac.ed.inf.graph.util.impl.FilteredEdgeSet;
import uk.ac.ed.inf.graph.util.impl.FilteredNodeSet;
import uk.ac.ed.inf.graph.util.impl.NodeSet;

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

	
	// added debug checks to graph
//    private final Logger logger = Logger.getLogger(this.getClass());
	private final FilteredEdgeSet<ICompoundNode, ICompoundEdge> edgeSet;
	private final FilteredNodeSet<ICompoundNode, ICompoundEdge> nodeSet;
	
	protected CommonChildCompoundGraph(){
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
		return this.getRoot().getGraph();
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
	public int numEdges() {
		return this.edgeSet.size();
	}

	@Override
	public int numNodes() {
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
	public void addNode(ICompoundNode node){
		this.nodeSet.add(node);
	}
	
	@Override
	public void addEdge(ICompoundEdge edge){
		this.edgeSet.add(edge);
	}
	
	
	@Override
	public ICompoundChildEdgeFactory edgeFactory() {
		return new CompoundChildEdgeFactory(this.getRoot());
	}

	@Override
	public ICompoundGraphCopyBuilder newCopyBuilder(){
		return new CompoundGraphCopyBuilder(this);
	}
	
	@Override
	public ICompoundGraphMoveBuilder newMoveBuilder(){
		return new CompoundGraphMoveBuilder(this);
	}
	
	@Override
	public ICompoundNodeFactory nodeFactory() {
		ICompoundNodeFactory fact = new CompoundNodeFactory(this.getRoot());
		return fact;
	}


	protected abstract void notifyCopyOperationComplete(ISubCompoundGraph originalSubgraph,
			ISubCompoundGraph copiedSubgraph);

	protected abstract void notifyMoveOperationComplete(ISubCompoundGraph originalSubgraph,
			ISubCompoundGraph movedSubgraph);

}
