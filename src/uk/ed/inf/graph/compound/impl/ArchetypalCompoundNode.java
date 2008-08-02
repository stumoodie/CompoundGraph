package uk.ed.inf.graph.compound.impl;

import java.util.Iterator;
import java.util.SortedSet;

import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.state.IRestorableGraphElement;
import uk.ed.inf.graph.util.IEdgeSet;
import uk.ed.inf.graph.util.IFilterCriteria;
import uk.ed.inf.graph.util.impl.ConnectedNodeIterator;
import uk.ed.inf.graph.util.impl.EdgeSet;
import uk.ed.inf.graph.util.impl.FilteredEdgeSet;
import uk.ed.inf.tree.AncestorTreeIterator;
import uk.ed.inf.tree.LevelOrderTreeIterator;

public abstract class ArchetypalCompoundNode implements ICompoundNode<ArchetypalCompoundNode, ArchetypalCompoundEdge>, IRestorableGraphElement {
	private final ArchetypalCompoundNode parent;
	private final IEdgeSet<ArchetypalCompoundNode, ArchetypalCompoundEdge> edgeInList;
	private final IEdgeSet<ArchetypalCompoundNode, ArchetypalCompoundEdge> edgeOutList;
	private final ArchetypalCompoundGraph superGraph; 
	private final int index;
	private boolean removed;
	
	protected ArchetypalCompoundNode(ArchetypalCompoundGraph superGraph, int index){
		this(superGraph, null, index);
	}
	
	protected ArchetypalCompoundNode(ArchetypalCompoundNode parent, int index){
		this(parent.getGraph(), parent, index);
	}
	
	private ArchetypalCompoundNode(ArchetypalCompoundGraph superGraph, ArchetypalCompoundNode parent, int index){
		this.superGraph = superGraph;
		this.index = index;
		if(parent == null){
			this.parent = this;
		}
		else{
			this.parent = parent;
		}
		this.edgeInList = new FilteredEdgeSet<ArchetypalCompoundNode, ArchetypalCompoundEdge>(new EdgeSet<ArchetypalCompoundNode, ArchetypalCompoundEdge>(),
				new CiEdgeExistanceCriteria());
		this.edgeOutList = new FilteredEdgeSet<ArchetypalCompoundNode, ArchetypalCompoundEdge>(new EdgeSet<ArchetypalCompoundNode, ArchetypalCompoundEdge>(),
				new CiEdgeExistanceCriteria());
	}
	
	public Iterator<ArchetypalCompoundNode> childIterator() {
		return this.getChildCigraph().nodeIterator();
	}

	public ArchetypalCompoundNode getParent() {
		return this.parent;
	}

	public abstract ArchetypalChildCompoundGraph getChildCigraph();

	public int getInDegree() {
		return this.edgeInList.size();
	}

	public SortedSet<ArchetypalCompoundEdge> getInEdgesFrom(ArchetypalCompoundNode outNode) {
		return this.edgeInList.getEdgesWith(outNode);
	}

	public Iterator<ArchetypalCompoundEdge> getInEdgeIterator() {
		return this.edgeInList.iterator();
	}

	public Iterator<ArchetypalCompoundNode> getInNodeIterator() {
		return new ConnectedNodeIterator<ArchetypalCompoundNode, ArchetypalCompoundEdge>(this, this.edgeInList.iterator());
	}

	public int getOutDegree() {
		return this.edgeOutList.size();
	}

	public Iterator<ArchetypalCompoundEdge> getOutEdgeIterator() {
		return this.edgeOutList.iterator();
	}

	public SortedSet<ArchetypalCompoundEdge> getOutEdgesTo(ArchetypalCompoundNode inNode) {
		return this.edgeOutList.getEdgesWith(inNode);
	}

	public Iterator<ArchetypalCompoundNode> getOutNodeIterator() {
		return new ConnectedNodeIterator<ArchetypalCompoundNode, ArchetypalCompoundEdge>(this, this.edgeOutList.iterator());
	}

	public boolean hasInEdgeFrom(ArchetypalCompoundNode outNode) {
		return this.edgeInList.hasEdgesWith(outNode);
	}

	public boolean hasOutEdgeTo(ArchetypalCompoundNode inNode) {
		return this.edgeOutList.hasEdgesWith(inNode);
	}

	public Iterator<ArchetypalCompoundNode> connectedNodeIterator() {
		return new CombinedConnectedNodeIterator();
	}

	public int getDegree() {
		return this.edgeInList.size() + this.edgeOutList.size();
	}

	public Iterator<ArchetypalCompoundEdge> edgeIterator() {
		return new CombinedEdgeIterator();
	}

	public SortedSet<ArchetypalCompoundEdge> getEdgesWith(ArchetypalCompoundNode iOther) {
		if(iOther == null) throw new IllegalArgumentException("IOther cannot be null");
		ArchetypalCompoundNode other = (ArchetypalCompoundNode)iOther;
		SortedSet<ArchetypalCompoundEdge> retVal = null;
		if(this.edgeInList.hasEdgesWith(other)){
			retVal = this.edgeInList.getEdgesWith(other);
		}
		else if(this.edgeOutList.hasEdgesWith(other)){
			retVal = this.edgeOutList.getEdgesWith(other);
		}
		else{
			throw new IllegalArgumentException("edge must be contained by this node");
		}
		return retVal;
	}

	public ArchetypalCompoundGraph getGraph() {
		return this.superGraph;
	}

	public int getIndex() {
		return this.index;
	}

	public boolean isChild(ArchetypalCompoundNode childNode) {
		boolean retVal = false;
		if(childNode != null){
			Iterator<? extends ArchetypalCompoundNode> childIter = this.childIterator();
			while(childIter.hasNext() && retVal == false){
				ArchetypalCompoundNode possChild = childIter.next();
				if(possChild.equals(childNode)){
					retVal = true;
				}
			}
		}
		return retVal;
	}
	
	public boolean hasEdgeWith(ArchetypalCompoundNode iOther) {
		boolean retVal = false;
		if(iOther != null){ 
			ArchetypalCompoundNode other = (ArchetypalCompoundNode)iOther;
			retVal = this.edgeInList.hasEdgesWith(other);
			if(retVal == false){
				retVal = this.edgeOutList.hasEdgesWith(other);
			}
		}
		return retVal;
	}

	void addInEdge(ArchetypalCompoundEdge edge){
		this.edgeInList.add(edge);
	}
	
	void addOutEdge(ArchetypalCompoundEdge edge){
		this.edgeOutList.add(edge);
	}
	
	public int compareTo(ArchetypalCompoundNode o) {
		return this.index < o.getIndex() ? -1 : (this.index == o.getIndex() ? 0 : 1);
	}

	public boolean isRemoved() {
		return this.removed;
	}

	public void markRemoved(boolean removed){
		this.removed = removed;
	}
	
	private class CombinedConnectedNodeIterator implements Iterator<ArchetypalCompoundNode> {
		private final ConnectedNodeIterator<ArchetypalCompoundNode, ArchetypalCompoundEdge> inNodeIterator;
		private final ConnectedNodeIterator<ArchetypalCompoundNode, ArchetypalCompoundEdge> outNodeIterator;
		
		public CombinedConnectedNodeIterator(){
			this.inNodeIterator = new ConnectedNodeIterator<ArchetypalCompoundNode, ArchetypalCompoundEdge>(ArchetypalCompoundNode.this, edgeInList.iterator());
			this.outNodeIterator = new ConnectedNodeIterator<ArchetypalCompoundNode, ArchetypalCompoundEdge>(ArchetypalCompoundNode.this, edgeOutList.iterator());
		}
		
		
		public boolean hasNext() {
			boolean retVal = this.inNodeIterator.hasNext();
			if(retVal == false){
				retVal = this.outNodeIterator.hasNext();
			}
			return retVal;
		}

		public ArchetypalCompoundNode next() {
			ArchetypalCompoundNode retVal = null;
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

	public boolean isParent(ArchetypalCompoundNode parentNode) {
		boolean retVal = false;
		if(parentNode != null){
			retVal = this.parent.equals(parentNode);
		}
		return retVal;
	}

	public ArchetypalCompoundNode getRoot() {
		return this.superGraph.getRoot();
	}

	public Iterator<ArchetypalCompoundNode> ancestorIterator() {
		return new AncestorTreeIterator<ArchetypalCompoundNode>(this);
	}

	public Iterator<ArchetypalCompoundNode> levelOrderIterator() {
		return new LevelOrderTreeIterator<ArchetypalCompoundNode>(this);
	}

	private class CombinedEdgeIterator implements Iterator<ArchetypalCompoundEdge> {
		private final Iterator<ArchetypalCompoundEdge> inEdgeIterator;
		private final Iterator<ArchetypalCompoundEdge> outEdgeIterator;
		
		public CombinedEdgeIterator(){
			this.inEdgeIterator = edgeInList.iterator();
			this.outEdgeIterator = edgeOutList.iterator();
		}
		
		
		public boolean hasNext() {
			boolean retVal = this.inEdgeIterator.hasNext();
			if(retVal == false){
				retVal = this.outEdgeIterator.hasNext();
			}
			return retVal;
		}

		public ArchetypalCompoundEdge next() {
			ArchetypalCompoundEdge retVal = null;
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
	
	private class CiEdgeExistanceCriteria implements IFilterCriteria<ArchetypalCompoundEdge> {

		public boolean matched(ArchetypalCompoundEdge testObj) {
			return !testObj.isRemoved();
		}
		
	}
}
