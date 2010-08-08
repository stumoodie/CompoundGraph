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
package uk.ac.ed.inf.graph.util.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundNode;

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
