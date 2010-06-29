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
package uk.ed.inf.graph.util.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.util.INodeEdgeFilterCriteria;

public class EdgeFromNodeIterator <N extends ICompoundNode, E extends ICompoundEdge> implements Iterator<E> {
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
				Iterator<? extends ICompoundEdge> edgeIter = nextNode.edgeIterator();
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

	private void markEdgeVisited(ICompoundEdge edge){
		this.visited.add(edge.getIndex());
	}
	
	private boolean isVisited(ICompoundEdge edge){
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
