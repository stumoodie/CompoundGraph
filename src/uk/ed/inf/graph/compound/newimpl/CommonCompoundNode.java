package uk.ed.inf.graph.compound.newimpl;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundGraph;
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
	public Iterator<ICompoundEdge> getEdgesWith(ICompoundNode other) {
		final SortedSet<ICompoundEdge> retVal = new TreeSet<ICompoundEdge>();
		if(this.edgeInList.hasEdgesWith(this, other)){
			Iterator<ICompoundEdge> iter = this.edgeInList.getEdgesWith(this, other); 
			while(iter.hasNext()){
				retVal.add(iter.next());
			}
		}
		if(this.edgeOutList.hasEdgesWith(this, other)){
			Iterator<ICompoundEdge> iter = this.edgeOutList.getEdgesWith(this, other);
			while(iter.hasNext()){
				retVal.add(iter.next());
			}
		}
		return retVal.iterator();
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
	public Iterator<ICompoundEdge> getInEdgesFrom(ICompoundNode outNode) {
		return this.edgeInList.getEdgesWith(this, outNode);
	}

	@Override
	public Iterator<ICompoundNode> getInNodeIterator() {
		return new ConnectedNodeIterator(this, this.edgeOutList.iterator());
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
	public Iterator<ICompoundEdge> getOutEdgesTo(ICompoundNode inNode) {
		return this.edgeOutList.getEdgesWith(this, inNode);
	}

	@Override
	public Iterator<ICompoundNode> getOutNodeIterator() {
		return new ConnectedNodeIterator(this, this.edgeInList.iterator());
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
	public boolean isChild(ICompoundGraphElement childElement) {
		boolean retVal = false;
		if(childElement != null){
			Iterator<ICompoundGraphElement> childIter = this.getChildCompoundGraph().elementIterator();
			while(childIter.hasNext() && !retVal){
				ICompoundGraphElement possChild = childIter.next();
				if(possChild.equals(childElement)){
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

	public void markRemoved(boolean setRemoved) {
		this.removed = setRemoved;
	}

	@Override
	public boolean containsEdge(ICompoundEdge edge) {
		boolean retVal = false;
		if(edge != null && edge.getGraph().equals(this.getGraph())){
			retVal = this.edgeInList.contains(edge) || this.edgeOutList.contains(edge);
		}
		return retVal;
	}

	@Override
	public boolean containsInEdge(ICompoundEdge edge) {
		boolean retVal = false;
		ICompoundGraph thisGraph = this.getGraph();
		if(edge != null && edge.getGraph().equals(thisGraph)){
			retVal = this.edgeInList.contains(edge);
		}
		return retVal;
	}

	@Override
	public boolean containsOutEdge(ICompoundEdge edge) {
		boolean retVal = false;
		if(edge != null && edge.getGraph().equals(this.getGraph())){
			retVal = this.edgeOutList.contains(edge);
		}
		return retVal;
	}

	@Override
	public void addOutEdge(ICompoundEdge compoundEdge) {
		this.edgeOutList.add(compoundEdge);
	}

	@Override
	public void addInEdge(ICompoundEdge compoundEdge) {
		this.edgeInList.add(compoundEdge);
	}

}
