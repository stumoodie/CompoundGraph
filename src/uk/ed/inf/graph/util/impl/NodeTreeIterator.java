package uk.ed.inf.graph.util.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundNode;

public class NodeTreeIterator <
		N extends ICompoundNode<N, ? extends ICompoundEdge<N, ?>>,
		E extends ICompoundEdge<N, E>
> implements Iterator<N> {
	private final Iterator<? extends N> topNodeIter;
	private final Queue<N> lookAhead;

	public NodeTreeIterator(Iterator<? extends N> topNodeIterator){
		if(topNodeIterator == null) throw new IllegalArgumentException("iterator must exist");
		this.topNodeIter = topNodeIterator;
		this.lookAhead = new LinkedList<N>();
		readBranchFromTopNode();
	}
	
	public boolean hasNext() {
		return !this.lookAhead.isEmpty();
	}

	public N next() {
		N retVal = this.lookAhead.remove();
		if(this.lookAhead.isEmpty()){
			readBranchFromTopNode();
		}
		return retVal;
	}
	
	@SuppressWarnings("unchecked")
	private void readBranchFromTopNode(){
		boolean nodesAdded = false;
		while(this.topNodeIter.hasNext() && !nodesAdded){
			final N nextNode = this.topNodeIter.next();
			Iterator<N> levelOrderIter = nextNode.levelOrderIterator();
			while (levelOrderIter.hasNext()) {
				N node = levelOrderIter.next();
				this.lookAhead.offer(node);
				nodesAdded = true;
			}
		}
	}
	
	public void remove() {
		throw new UnsupportedOperationException("This Iterator does not support removal");
	}
}
