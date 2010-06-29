package uk.ed.inf.graph.compound.newimpl;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.util.IFilterCriteria;
import uk.ed.inf.graph.util.IFilteredEdgeSet;
import uk.ed.inf.graph.util.impl.ConnectedNodeIterator;
import uk.ed.inf.graph.util.impl.DirectedEdgeSet;
import uk.ed.inf.graph.util.impl.FilteredEdgeSet;
import uk.ed.inf.tree.AncestorTreeIterator;
import uk.ed.inf.tree.LevelOrderTreeIterator;
import uk.ed.inf.tree.PreOrderTreeIterator;

public abstract class CommonCompoundNode implements ICompoundNode {
	private static class EdgeExistanceCriteria implements IFilterCriteria<ICompoundEdge> {

		public boolean matched(ICompoundEdge testObj) {
			return !testObj.isRemoved();
		}
		
	}

	private class CombinedConnectedNodeIterator implements Iterator<ICompoundNode> {
		private final ConnectedNodeIterator inNodeIterator;
		private final ConnectedNodeIterator outNodeIterator;
		
		public CombinedConnectedNodeIterator(CommonCompoundNode initialNode){
			this.inNodeIterator = new ConnectedNodeIterator(initialNode, edgeInList.iterator());
			this.outNodeIterator = new ConnectedNodeIterator(initialNode, edgeOutList.iterator());
		}
		
		
		public boolean hasNext() {
			boolean retVal = this.inNodeIterator.hasNext();
			if(retVal == false){
				retVal = this.outNodeIterator.hasNext();
			}
			return retVal;
		}

		public ICompoundNode next() {
			ICompoundNode retVal = null;
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

	private class CombinedEdgeIterator implements Iterator<ICompoundEdge> {
		private final Iterator<ICompoundEdge> inEdgeIterator;
		private final Iterator<ICompoundEdge> outEdgeIterator;
		
		public CombinedEdgeIterator(){
			final SortedSet<ICompoundEdge> inEdgesCopy = new TreeSet<ICompoundEdge>(edgeInList);
			final SortedSet<ICompoundEdge> outEdgesCopy = new TreeSet<ICompoundEdge>(edgeOutList);
			this.inEdgeIterator = inEdgesCopy.iterator();
			this.outEdgeIterator = outEdgesCopy.iterator();
		}
		
		
		public boolean hasNext() {
			boolean retVal = this.inEdgeIterator.hasNext();
			if(retVal == false){
				retVal = this.outEdgeIterator.hasNext();
			}
			return retVal;
		}

		public ICompoundEdge next() {
			ICompoundEdge retVal = null;
			if(this.inEdgeIterator.hasNext()){
				retVal = this.inEdgeIterator.next();
			}
			else{
				retVal = this.outEdgeIterator.next();
			}
			return retVal;
		}

		public void remove() {
			throw new UnsupportedOperationException("This iterator does not support removal");
		}
		
	}

	private int index;
	private final IFilteredEdgeSet<ICompoundNode, ICompoundEdge> edgeInList;
	private final IFilteredEdgeSet<ICompoundNode, ICompoundEdge> edgeOutList;
	private boolean removed;

	protected CommonCompoundNode(int idx){
		this.index = idx;
		this.edgeInList = new FilteredEdgeSet<ICompoundNode, ICompoundEdge>(new DirectedEdgeSet<ICompoundNode, ICompoundEdge>(), new EdgeExistanceCriteria());
		this.edgeOutList = new FilteredEdgeSet<ICompoundNode, ICompoundEdge>(new DirectedEdgeSet<ICompoundNode, ICompoundEdge>(), new EdgeExistanceCriteria());
		this.removed = false;
	}
	
	@Override
	public abstract CompoundGraph getGraph();
	
	@Override
	public Iterator<ICompoundNode> connectedNodeIterator() {
		return new CombinedConnectedNodeIterator(this);
	}

	@Override
	public Iterator<ICompoundEdge> edgeIterator() {
		return new CombinedEdgeIterator();
	}

	@Override
	public int getDegree() {
		return this.edgeInList.size() + this.edgeOutList.size();
	}

	@Override
	public SortedSet<ICompoundEdge> getEdgesWith(ICompoundNode other) {
		if(other == null) throw new IllegalArgumentException("IOther cannot be null");

		final SortedSet<ICompoundEdge> retVal = new TreeSet<ICompoundEdge>();
		if(this.edgeInList.hasEdgesWith(this, other)){
			retVal.addAll(this.edgeInList.getEdgesWith(this, other));
		}
		if(this.edgeOutList.hasEdgesWith(this, other)){
			retVal.addAll(this.edgeOutList.getEdgesWith(this, other));
		}
		if(retVal.isEmpty()){
			throw new IllegalArgumentException("edge must be contained by this node");
		}
		return retVal;
	}

	@Override
	public int getInDegree() {
		return this.edgeInList.size();
	}

	@Override
	public Iterator<ICompoundEdge> getInEdgeIterator() {
		return this.edgeInList.iterator();
	}

	@Override
	public SortedSet<ICompoundEdge> getInEdgesFrom(ICompoundNode outNode) {
		return this.edgeInList.getEdgesWith(this, outNode);
	}

	@Override
	public Iterator<ICompoundNode> getInNodeIterator() {
		return new ConnectedNodeIterator(this, this.edgeInList.iterator());
	}

	@Override
	public int getIndex() {
		return this.index;
	}

	@Override
	public int getOutDegree() {
		return this.edgeOutList.size();
	}

	@Override
	public Iterator<ICompoundEdge> getOutEdgeIterator() {
		return this.edgeOutList.iterator();
	}

	@Override
	public SortedSet<ICompoundEdge> getOutEdgesTo(ICompoundNode inNode) {
		return this.edgeOutList.getEdgesWith(this, inNode);
	}

	@Override
	public Iterator<ICompoundNode> getOutNodeIterator() {
		return new ConnectedNodeIterator(this, this.edgeOutList.iterator());
	}

	@Override
	public boolean hasEdgeWith(ICompoundNode other) {
		boolean retVal = false;
		if(other != null){ 
			retVal = this.edgeInList.hasEdgesWith(this, other);
			if(retVal == false){
				retVal = this.edgeOutList.hasEdgesWith(this, other);
			}
		}
		return retVal;
	}

	@Override
	public boolean hasInEdgeFrom(ICompoundNode outNode) {
		return this.edgeInList.hasEdgesWith(this, outNode);
	}

	@Override
	public boolean hasOutEdgeTo(ICompoundNode inNode) {
		return this.edgeOutList.hasEdgesWith(this, inNode);
	}

	@Override
	public boolean isAncestor(ICompoundGraphElement testNode) {
	    boolean retVal = false;
	    if(testNode != null) {
	        retVal = this.getGraph().getElementTree().isAncestor(this, testNode);
	    }
	    return retVal;
	}

	@Override
	public boolean isDescendent(ICompoundGraphElement testNode) {
        boolean retVal = false;
        if(testNode != null) {
            retVal = this.getGraph().getElementTree().isDescendant(this, testNode);
        }
        return retVal;
	}

	@Override
	public boolean isLink() {
		return false;
	}

	@Override
	public boolean isNode() {
		return true;
	}

	@Override
	public int compareTo(ICompoundGraphElement o) {
		return Integer.valueOf(this.index).compareTo(o.getIndex());
	}

	@Override
	public Iterator<ICompoundGraphElement> ancestorIterator() {
		return new AncestorTreeIterator<ICompoundGraphElement>(this);
	}

	@Override
	public Iterator<ICompoundGraphElement> childIterator() {
		return this.getChildCompoundGraph().elementIterator();
	}

	@Override
	public boolean isChild(ICompoundGraphElement childNode) {
		boolean retVal = false;
		if(childNode != null){
			Iterator<ICompoundNode> childIter = this.getChildCompoundGraph().nodeIterator();
			while(childIter.hasNext() && retVal == false){
				ICompoundNode possChild = childIter.next();
				if(possChild.equals(childNode)){
					retVal = true;
				}
			}
		}
		return retVal;
	}

	@Override
	public Iterator<ICompoundGraphElement> levelOrderIterator() {
		return new LevelOrderTreeIterator<ICompoundGraphElement>(this);
	}

	@Override
	public Iterator<ICompoundGraphElement> preOrderIterator() {
		return new PreOrderTreeIterator<ICompoundGraphElement>(this);
	}

	@Override
	public boolean isRemoved() {
		return this.removed;
	}

	@Override
	public void markRemoved(boolean setRemoved) {
		this.removed = setRemoved;
	}

	@Override
	public boolean containsEdge(ICompoundEdge edge) {
		return this.edgeInList.contains(edge) || this.edgeOutList.contains(edge);
	}

	@Override
	public boolean containsInEdge(ICompoundEdge edge) {
		return this.edgeInList.contains(edge);
	}

	@Override
	public boolean containsOutEdge(ICompoundEdge edge) {
		return this.edgeOutList.contains(edge);
	}

}
