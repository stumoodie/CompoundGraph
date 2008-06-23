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

public class CompoundNode implements ICompoundNode<CompoundNode, CompoundEdge>, IRestorableGraphElement {
	private final CompoundNode parent;
	private final IEdgeSet<CompoundNode, CompoundEdge> edgeInList;
	private final IEdgeSet<CompoundNode, CompoundEdge> edgeOutList;
	private final ChildCompoundGraph childCompoundGraph;
	private final CompoundGraph superGraph; 
	private final int index;
	private boolean removed;
	
	public CompoundNode(CompoundGraph superGraph, int index){
		this(superGraph, null, index);
	}
	
	public CompoundNode(CompoundNode parent, int index){
		this(parent.getGraph(), parent, index);
	}
	
	private CompoundNode(CompoundGraph superGraph, CompoundNode parent, int index){
		this.superGraph = superGraph;
		this.index = index;
		if(parent == null){
			this.parent = this;
		}
		else{
			this.parent = parent;
		}
		this.edgeInList = new FilteredEdgeSet<CompoundNode, CompoundEdge>(new EdgeSet<CompoundNode, CompoundEdge>(),
				new CiEdgeExistanceCriteria());
		this.edgeOutList = new FilteredEdgeSet<CompoundNode, CompoundEdge>(new EdgeSet<CompoundNode, CompoundEdge>(),
				new CiEdgeExistanceCriteria());
		this.childCompoundGraph = new ChildCompoundGraph(this);
	}
	
	public Iterator<CompoundNode> childIterator() {
		return this.childCompoundGraph.nodeIterator();
	}

	public CompoundNode getParent() {
		return this.parent;
	}

	public ChildCompoundGraph getChildCigraph() {
		return this.childCompoundGraph;
	}

	public int getInDegree() {
		return this.edgeInList.size();
	}

	public SortedSet<CompoundEdge> getInEdgesFrom(CompoundNode outNode) {
		return this.edgeInList.getEdgesWith(outNode);
	}

	public Iterator<CompoundEdge> getInEdgeIterator() {
		return this.edgeInList.iterator();
	}

	public Iterator<CompoundNode> getInNodeIterator() {
		return new ConnectedNodeIterator<CompoundNode, CompoundEdge>(this, this.edgeInList.iterator());
	}

	public int getOutDegree() {
		return this.edgeOutList.size();
	}

	public Iterator<CompoundEdge> getOutEdgeIterator() {
		return this.edgeOutList.iterator();
	}

	public SortedSet<CompoundEdge> getOutEdgesTo(CompoundNode inNode) {
		return this.edgeOutList.getEdgesWith(inNode);
	}

	public Iterator<CompoundNode> getOutNodeIterator() {
		return new ConnectedNodeIterator<CompoundNode, CompoundEdge>(this, this.edgeOutList.iterator());
	}

	public boolean hasInEdgeFrom(CompoundNode outNode) {
		return this.edgeInList.hasEdgesWith(outNode);
	}

	public boolean hasOutEdgeTo(CompoundNode inNode) {
		return this.edgeInList.hasEdgesWith(inNode);
	}

	public Iterator<CompoundNode> connectedNodeIterator() {
		return new CombinedConnectedNodeIterator();
	}

	public int getDegree() {
		return this.edgeInList.size() + this.edgeOutList.size();
	}

	public Iterator<CompoundEdge> edgeIterator() {
		return new CombinedEdgeIterator();
	}

	public SortedSet<CompoundEdge> getEdgesWith(CompoundNode iOther) {
		if(iOther == null) throw new IllegalArgumentException("IOther cannot be null");
		CompoundNode other = (CompoundNode)iOther;
		SortedSet<CompoundEdge> retVal = null;
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

	public CompoundGraph getGraph() {
		return this.superGraph;
	}

	public int getIndex() {
		return this.index;
	}

	public boolean isChild(CompoundNode childNode) {
		boolean retVal = false;
		if(childNode != null){
			Iterator<CompoundNode> childIter = this.childIterator();
			while(childIter.hasNext() && retVal == false){
				CompoundNode possChild = childIter.next();
				if(possChild.equals(childNode)){
					retVal = true;
				}
			}
		}
		return retVal;
	}
	
	public boolean hasEdgeWith(CompoundNode iOther) {
		boolean retVal = false;
		if(iOther != null){ 
			CompoundNode other = (CompoundNode)iOther;
			retVal = this.edgeInList.hasEdgesWith(other);
			if(retVal == false){
				retVal = this.edgeOutList.hasEdgesWith(other);
			}
		}
		return retVal;
	}

	void addInEdge(CompoundEdge edge){
		this.edgeInList.add(edge);
	}
	
//	void removeInEdge(CiEdge edge) {
//		this.edgeInList.remove(edge);
//	}

	void addOutEdge(CompoundEdge edge){
		this.edgeOutList.add(edge);
	}
	
//	void removeOutEdge(CiEdge edge) {
//		this.edgeOutList.remove(edge);
//	}

//	public void removeNode() {
//		throw new UnsupportedOperationException("Implement this!");
//	}

	public int compareTo(CompoundNode o) {
		return this.index < o.getIndex() ? -1 : (this.index == o.getIndex() ? 0 : 1);
	}

	public boolean isRemoved() {
		return this.removed;
	}

	public void markRemoved(boolean removed){
		this.removed = removed;
	}
	
	private class CombinedConnectedNodeIterator implements Iterator<CompoundNode> {
		private final ConnectedNodeIterator<CompoundNode, CompoundEdge> inNodeIterator;
		private final ConnectedNodeIterator<CompoundNode, CompoundEdge> outNodeIterator;
		
		public CombinedConnectedNodeIterator(){
			this.inNodeIterator = new ConnectedNodeIterator<CompoundNode, CompoundEdge>(CompoundNode.this, edgeInList.iterator());
			this.outNodeIterator = new ConnectedNodeIterator<CompoundNode, CompoundEdge>(CompoundNode.this, edgeOutList.iterator());
		}
		
		
		public boolean hasNext() {
			boolean retVal = this.inNodeIterator.hasNext();
			if(retVal == false){
				retVal = this.outNodeIterator.hasNext();
			}
			return retVal;
		}

		public CompoundNode next() {
			CompoundNode retVal = null;
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

	private class CombinedEdgeIterator implements Iterator<CompoundEdge> {
		private final Iterator<CompoundEdge> inEdgeIterator;
		private final Iterator<CompoundEdge> outEdgeIterator;
		
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

		public CompoundEdge next() {
			CompoundEdge retVal = null;
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
	
	private class CiEdgeExistanceCriteria implements IFilterCriteria<CompoundEdge> {

		public boolean matched(CompoundEdge testObj) {
			return !testObj.isRemoved();
		}
		
	}

	public boolean isParent(CompoundNode parentNode) {
		boolean retVal = false;
		if(parentNode != null){
			retVal = this.parent.equals(parentNode);
		}
		return retVal;
	}

	public CompoundNode getRoot() {
		return this.superGraph.getRoot();
	}

	public Iterator<CompoundNode> ancestorIterator() {
		return new AncestorTreeIterator<CompoundNode>(this);
	}

	public Iterator<CompoundNode> levelOrderIterator() {
		return new LevelOrderTreeIterator<CompoundNode>(this);
	}
}
