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
import java.util.SortedSet;
import java.util.TreeSet;

import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.state.IRestorableGraphElement;
import uk.ed.inf.graph.util.IDirectedEdgeSet;
import uk.ed.inf.graph.util.IFilterCriteria;
import uk.ed.inf.graph.util.IFilteredEdgeSet;
import uk.ed.inf.graph.util.impl.ConnectedNodeIterator;
import uk.ed.inf.graph.util.impl.FilteredEdgeSet;
import uk.ed.inf.tree.AncestorTreeIterator;
import uk.ed.inf.tree.LevelOrderTreeIterator;

public abstract class BaseCompoundNode implements ICompoundNode<BaseCompoundNode, BaseCompoundEdge>, IRestorableGraphElement {
	public static final int ROOT_LEVEL = 0;
	private IFilteredEdgeSet<BaseCompoundNode, BaseCompoundEdge> edgeInList;
	private IFilteredEdgeSet<BaseCompoundNode, BaseCompoundEdge> edgeOutList;
	
	protected BaseCompoundNode(){
		this.edgeInList = null;
		this.edgeOutList = null;
		this.setRemoved(false);
	}
	
	protected int calcTreeLevel(){
		BaseCompoundNode p = this;
		int level = ROOT_LEVEL;
		while(p != p.getParent()){
			p = p.getParent();
			level++;
		}
		return level;
	}
	
	public final Iterator<BaseCompoundNode> childIterator() {
		return this.getChildCompoundGraph().nodeIterator();
	}

	/**
	 * The parent node cannot be null and should be the root node if the current node is the root
	 * node. This follows the standard conversion for tree data structures.
	 */
	public abstract BaseCompoundNode getParent();
	
	protected final void createInEdgeSet(IDirectedEdgeSet<BaseCompoundNode, BaseCompoundEdge> edgeSet){
		this.edgeInList = new FilteredEdgeSet<BaseCompoundNode, BaseCompoundEdge>(edgeSet, new CiEdgeExistanceCriteria());
	}

	protected final void createOutEdgeSet(IDirectedEdgeSet<BaseCompoundNode, BaseCompoundEdge> edgeSet){
		this.edgeOutList = new FilteredEdgeSet<BaseCompoundNode, BaseCompoundEdge>(edgeSet, new CiEdgeExistanceCriteria());
	}

	protected final IFilteredEdgeSet<BaseCompoundNode, BaseCompoundEdge> getEdgeInList(){
		return this.edgeInList;
	}

	protected final IFilteredEdgeSet<BaseCompoundNode, BaseCompoundEdge> getEdgeOutList(){
		return this.edgeOutList;
	}

	/**
	 * This should be used to set the removal status variable only. No other actions#
	 * should be performed here. To perform an action on removal then use {@link #removalAction(boolean)}. 
	 * @param removed the removal status: true means the nodes is removed.
	 */
	protected abstract void setRemoved(boolean removed);
	
	public abstract BaseChildCompoundGraph getChildCompoundGraph();

	public final int getInDegree() {
		return this.getEdgeInList().size();
	}

	public final SortedSet<BaseCompoundEdge> getInEdgesFrom(BaseCompoundNode outNode) {
		return this.edgeInList.getEdgesWith(this, outNode);
	}

	public final Iterator<BaseCompoundEdge> getInEdgeIterator() {
		return this.edgeInList.iterator();
	}

	public final Iterator<BaseCompoundNode> getInNodeIterator() {
		return new ConnectedNodeIterator<BaseCompoundNode, BaseCompoundEdge>(this, this.edgeInList.iterator());
	}

	public final int getOutDegree() {
		return this.edgeOutList.size();
	}

	public final Iterator<BaseCompoundEdge> getOutEdgeIterator() {
		return this.edgeOutList.iterator();
	}

	public final SortedSet<BaseCompoundEdge> getOutEdgesTo(BaseCompoundNode inNode) {
		return this.edgeOutList.getEdgesWith(this, inNode);
	}

	public final Iterator<BaseCompoundNode> getOutNodeIterator() {
		return new ConnectedNodeIterator<BaseCompoundNode, BaseCompoundEdge>(this, this.edgeOutList.iterator());
	}

	public final boolean hasInEdgeFrom(BaseCompoundNode outNode) {
		return this.edgeInList.hasEdgesWith(this, outNode);
	}

	public final boolean hasOutEdgeTo(BaseCompoundNode inNode) {
		return this.edgeOutList.hasEdgesWith(this, inNode);
	}

	public final Iterator<BaseCompoundNode> connectedNodeIterator() {
		return new CombinedConnectedNodeIterator(this);
	}

	public final int getDegree() {
		return this.edgeInList.size() + this.edgeOutList.size();
	}

	public final Iterator<BaseCompoundEdge> edgeIterator() {
		return new CombinedEdgeIterator();
	}

	public final SortedSet<BaseCompoundEdge> getEdgesWith(BaseCompoundNode other) {
		if(other == null) throw new IllegalArgumentException("IOther cannot be null");
//		BaseCompoundNode other = (BaseCompoundNode)iOther;
		final SortedSet<BaseCompoundEdge> retVal = new TreeSet<BaseCompoundEdge>();
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

	public abstract BaseCompoundGraph getGraph();

	public abstract int getIndex();

	public final boolean isChild(BaseCompoundNode childNode) {
		boolean retVal = false;
		if(childNode != null){
			Iterator<BaseCompoundNode> childIter = this.childIterator();
			while(childIter.hasNext() && retVal == false){
				BaseCompoundNode possChild = childIter.next();
				if(possChild.equals(childNode)){
					retVal = true;
				}
			}
		}
		return retVal;
	}
	
	public final boolean hasEdgeWith(BaseCompoundNode other) {
		boolean retVal = false;
		if(other != null){ 
			retVal = this.edgeInList.hasEdgesWith(this, other);
			if(retVal == false){
				retVal = this.edgeOutList.hasEdgesWith(this, other);
			}
		}
		return retVal;
	}

	final void addInEdge(BaseCompoundEdge edge){
		this.edgeInList.add(edge);
	}
	
	final void addOutEdge(BaseCompoundEdge edge){
		this.edgeOutList.add(edge);
	}
	
	public final int compareTo(BaseCompoundNode o) {
		return this.getIndex() < o.getIndex() ? -1 : (this.getIndex() == o.getIndex() ? 0 : 1);
	}

	public abstract boolean isRemoved();

	public final void markRemoved(boolean removed){
		this.setRemoved(removed);
		this.removalAction(removed);
	}
	
	/**
	 * additional actions to be executed upon this node being
	 * marked as removed.
	 */
	protected abstract void removalAction(boolean removed);
	
	private class CombinedConnectedNodeIterator implements Iterator<BaseCompoundNode> {
		private final ConnectedNodeIterator<BaseCompoundNode, BaseCompoundEdge> inNodeIterator;
		private final ConnectedNodeIterator<BaseCompoundNode, BaseCompoundEdge> outNodeIterator;
		
		public CombinedConnectedNodeIterator(BaseCompoundNode initialNode){
			this.inNodeIterator = new ConnectedNodeIterator<BaseCompoundNode, BaseCompoundEdge>(initialNode, edgeInList.iterator());
			this.outNodeIterator = new ConnectedNodeIterator<BaseCompoundNode, BaseCompoundEdge>(initialNode, edgeOutList.iterator());
		}
		
		
		public boolean hasNext() {
			boolean retVal = this.inNodeIterator.hasNext();
			if(retVal == false){
				retVal = this.outNodeIterator.hasNext();
			}
			return retVal;
		}

		public BaseCompoundNode next() {
			BaseCompoundNode retVal = null;
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

	public final boolean isParent(BaseCompoundNode parentNode) {
		boolean retVal = false;
		if(parentNode != null){
			retVal = this.getParent().equals(parentNode);
		}
		return retVal;
	}

	public BaseCompoundNode getRoot() {
		return this.getGraph().getRootNode();
	}

	public final Iterator<BaseCompoundNode> ancestorIterator() {
		return new AncestorTreeIterator<BaseCompoundNode>(this);
	}

	public final Iterator<BaseCompoundNode> levelOrderIterator() {
		return new LevelOrderTreeIterator<BaseCompoundNode>(this);
	}

	public boolean isAncestor(BaseCompoundNode testNode) {
	    boolean retVal = false;
	    if(testNode != null) {
	        retVal = this.getGraph().getNodeTree().isAncestor(this, testNode);
	    }
	    return retVal;
	}
	
	public boolean isDescendent(BaseCompoundNode testNode) {
        boolean retVal = false;
        if(testNode != null) {
            retVal = this.getGraph().getNodeTree().isDescendant(this, testNode);
        }
        return retVal;
	}
	
	private class CombinedEdgeIterator implements Iterator<BaseCompoundEdge> {
		private final Iterator<BaseCompoundEdge> inEdgeIterator;
		private final Iterator<BaseCompoundEdge> outEdgeIterator;
		
		public CombinedEdgeIterator(){
			final SortedSet<BaseCompoundEdge> inEdgesCopy = new TreeSet<BaseCompoundEdge>(edgeInList);
			final SortedSet<BaseCompoundEdge> outEdgesCopy = new TreeSet<BaseCompoundEdge>(edgeOutList);
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

		public BaseCompoundEdge next() {
			BaseCompoundEdge retVal = null;
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
	
	private class CiEdgeExistanceCriteria implements IFilterCriteria<BaseCompoundEdge> {

		public boolean matched(BaseCompoundEdge testObj) {
			return !testObj.isRemoved();
		}
		
	}
}
