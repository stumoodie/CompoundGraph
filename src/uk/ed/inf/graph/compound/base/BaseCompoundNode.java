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

import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.state.IRestorableGraphElement;
import uk.ed.inf.graph.util.IDirectedEdgeSet;
import uk.ed.inf.graph.util.IFilterCriteria;
import uk.ed.inf.graph.util.IFilteredEdgeSet;
import uk.ed.inf.graph.util.impl.ConnectedNodeIterator;
import uk.ed.inf.graph.util.impl.FilteredEdgeSet;
import uk.ed.inf.tree.AncestorTreeIterator;
import uk.ed.inf.tree.LevelOrderTreeIterator;
import uk.ed.inf.tree.PreOrderTreeIterator;

public abstract class BaseCompoundNode implements ICompoundNode, IRestorableGraphElement {
	public static final int ROOT_LEVEL = 0;
	private IFilteredEdgeSet<ICompoundNode, ICompoundEdge> edgeInList;
	private IFilteredEdgeSet<ICompoundNode, ICompoundEdge> edgeOutList;
	
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
	
	public final Iterator<ICompoundNode> childIterator() {
		return this.getChildCompoundGraph().nodeIterator();
	}

	/**
	 * The parent node cannot be null and should be the root node if the current node is the root
	 * node. This follows the standard conversion for tree data structures.
	 */
	@Override
	public abstract BaseCompoundNode getParent();
	
	protected final void createInEdgeSet(IDirectedEdgeSet<ICompoundNode, ICompoundEdge> edgeSet){
		this.edgeInList = new FilteredEdgeSet<ICompoundNode, ICompoundEdge>(edgeSet, new CiEdgeExistanceCriteria());
	}

	protected final void createOutEdgeSet(IDirectedEdgeSet<ICompoundNode, ICompoundEdge> edgeSet){
		this.edgeOutList = new FilteredEdgeSet<ICompoundNode, ICompoundEdge>(edgeSet, new CiEdgeExistanceCriteria());
	}

	protected final IFilteredEdgeSet<ICompoundNode, ICompoundEdge> getEdgeInList(){
		return this.edgeInList;
	}

	protected final IFilteredEdgeSet<ICompoundNode, ICompoundEdge> getEdgeOutList(){
		return this.edgeOutList;
	}

	/**
	 * This should be used to set the removal status variable only. No other actions#
	 * should be performed here. To perform an action on removal then use {@link #removalAction(boolean)}. 
	 * @param removed the removal status: true means the nodes is removed.
	 */
	protected abstract void setRemoved(boolean removed);
	
	@Override
	public abstract BaseChildCompoundGraph getChildCompoundGraph();

	@Override
	public final int getInDegree() {
		return this.getEdgeInList().size();
	}

	@Override
	public final SortedSet<ICompoundEdge> getInEdgesFrom(ICompoundNode outNode) {
		return this.edgeInList.getEdgesWith(this, outNode);
	}

	@Override
	public final Iterator<ICompoundEdge> getInEdgeIterator() {
		return this.edgeInList.iterator();
	}

	@Override
	public final Iterator<ICompoundNode> getInNodeIterator() {
		return new ConnectedNodeIterator(this, this.edgeInList.iterator());
	}

	@Override
	public final int getOutDegree() {
		return this.edgeOutList.size();
	}

	@Override
	public final Iterator<ICompoundEdge> getOutEdgeIterator() {
		return this.edgeOutList.iterator();
	}

	@Override
	public final SortedSet<ICompoundEdge> getOutEdgesTo(ICompoundNode inNode) {
		return this.edgeOutList.getEdgesWith(this, inNode);
	}

	@Override
	public final Iterator<ICompoundNode> getOutNodeIterator() {
		return new ConnectedNodeIterator(this, this.edgeOutList.iterator());
	}

	@Override
	public final boolean hasInEdgeFrom(ICompoundNode outNode) {
		return this.edgeInList.hasEdgesWith(this, outNode);
	}

	@Override
	public final boolean hasOutEdgeTo(ICompoundNode inNode) {
		return this.edgeOutList.hasEdgesWith(this, inNode);
	}

	@Override
	public final Iterator<ICompoundNode> connectedNodeIterator() {
		return new CombinedConnectedNodeIterator(this);
	}

	@Override
	public final int getDegree() {
		return this.edgeInList.size() + this.edgeOutList.size();
	}

	@Override
	public final Iterator<ICompoundEdge> edgeIterator() {
		return new CombinedEdgeIterator();
	}

	@Override
	public final SortedSet<ICompoundEdge> getEdgesWith(ICompoundNode other) {
		if(other == null) throw new IllegalArgumentException("IOther cannot be null");
//		BaseCompoundNode other = (BaseCompoundNode)iOther;
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
	public abstract BaseCompoundGraph getGraph();

	@Override
	public abstract int getIndex();

	@Override
	public final boolean isChild(ICompoundNode childNode) {
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
	public final boolean hasEdgeWith(ICompoundNode other) {
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
	
	@Override
	public int compareTo(ICompoundNode o) {
		return this.getIndex() < o.getIndex() ? -1 : (this.getIndex() == o.getIndex() ? 0 : 1);
	}

	@Override
	public abstract boolean isRemoved();

	@Override
	public final void markRemoved(boolean removed){
		this.setRemoved(removed);
		this.removalAction(removed);
	}
	
	/**
	 * additional actions to be executed upon this node being
	 * marked as removed.
	 */
	protected abstract void removalAction(boolean removed);
	
	private class CombinedConnectedNodeIterator implements Iterator<ICompoundNode> {
		private final ConnectedNodeIterator inNodeIterator;
		private final ConnectedNodeIterator outNodeIterator;
		
		public CombinedConnectedNodeIterator(BaseCompoundNode initialNode){
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

	@Override
	public final boolean isParent(ICompoundNode parentNode) {
		boolean retVal = false;
		if(parentNode != null){
			retVal = this.getParent().equals(parentNode);
		}
		return retVal;
	}

	@Override
	public BaseCompoundNode getRoot() {
		return this.getGraph().getRootNode();
	}

	@Override
	public final Iterator<ICompoundNode> ancestorIterator() {
		return new AncestorTreeIterator<ICompoundNode>(this);
	}

	@Override
	public final Iterator<ICompoundNode> levelOrderIterator() {
		return new LevelOrderTreeIterator<ICompoundNode>(this);
	}

	@Override
	public final Iterator<ICompoundNode> preOrderIterator() {
		return new PreOrderTreeIterator<ICompoundNode>(this);
	}

	@Override
	public boolean isAncestor(ICompoundNode testNode) {
	    boolean retVal = false;
	    if(testNode != null) {
	        retVal = this.getGraph().getNodeTree().isAncestor(this, testNode);
	    }
	    return retVal;
	}
	
	@Override
	public boolean isDescendent(ICompoundNode testNode) {
        boolean retVal = false;
        if(testNode != null) {
            retVal = this.getGraph().getNodeTree().isDescendant(this, testNode);
        }
        return retVal;
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
	
	private class CiEdgeExistanceCriteria implements IFilterCriteria<ICompoundEdge> {

		public boolean matched(ICompoundEdge testObj) {
			return !testObj.isRemoved();
		}
		
	}
}
