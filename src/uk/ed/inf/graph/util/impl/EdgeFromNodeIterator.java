package uk.ed.inf.graph.util.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import uk.ed.inf.graph.basic.IBasicEdge;
import uk.ed.inf.graph.basic.IBasicNode;
import uk.ed.inf.graph.util.INodeEdgeFilterCriteria;

public class EdgeFromNodeIterator <
		N extends IBasicNode<N, ? extends IBasicEdge<N, ?>>,
		E extends IBasicEdge<N, E>
> implements Iterator<E> {
	private final Iterator<? extends N> nodeIter;
	private final Set<Integer> visited;
	private final Queue<E> lookAhead;
	private final INodeEdgeFilterCriteria<? super N, ? super E> criteria;

	public EdgeFromNodeIterator(Iterator<? extends N> nodeListIterator){
		if(nodeListIterator == null) throw new IllegalArgumentException("collection must exist");
		this.nodeIter = nodeListIterator;
		this.lookAhead = new LinkedList<E>();
		this.visited = new HashSet<Integer>();
		this.criteria = new DefaultNodeEdgeFilter();
		readUntilNewEdgeFound();
	}
	
	public EdgeFromNodeIterator(Iterator<? extends N> nodeListIterator, INodeEdgeFilterCriteria<? super N, ? super E> criteria){
		if(nodeListIterator == null) throw new IllegalArgumentException("collection must exist");
		this.nodeIter = nodeListIterator;
		this.lookAhead = new LinkedList<E>();
		this.visited = new HashSet<Integer>();
		this.criteria = criteria;
		readUntilNewEdgeFound();
	}
	
	public boolean hasNext() {
		return !this.lookAhead.isEmpty();
	}

	public E next() {
		E retVal = this.lookAhead.remove();
		if(this.lookAhead.isEmpty()){
			readUntilNewEdgeFound();
		}
		return retVal;
	}
	
	@SuppressWarnings("unchecked")
	private void readUntilNewEdgeFound(){
		boolean nodesAdded = false;
		while(this.nodeIter.hasNext() && !nodesAdded){
			final N nextNode = this.nodeIter.next();
			if(criteria.matchedNode(nextNode)){
				Iterator<? extends IBasicEdge<N, ?>> edgeIter = nextNode.edgeIterator();
				while (edgeIter.hasNext()) {
					E edge = (E) edgeIter.next();
					if (criteria.matchedEdge(edge) && !isVisited(edge)) {
						this.lookAhead.offer(edge);
						nodesAdded = true;
						markEdgeVisited(edge);
					}
				}
			}
		}
	}
	
	public void remove() {
		throw new UnsupportedOperationException("This Iterator does not support removal");
	}

	private void markEdgeVisited(IBasicEdge<? extends N, ? extends E> edge){
		this.visited.add(edge.getIndex());
	}
	
	private boolean isVisited(IBasicEdge<? extends N, ? extends E> edge){
		return this.visited.contains(edge.getIndex());
	}
	
	private class DefaultNodeEdgeFilter implements INodeEdgeFilterCriteria<N, E> {

		public boolean matchedEdge(E testEdge) {
			return true;
		}

		public boolean matchedNode(N testNode) {
			return true;
		}
		
	}
}
