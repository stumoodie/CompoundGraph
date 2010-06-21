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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import uk.ed.inf.graph.compound.ICompoundNode;

public class NodeTreeIterator implements Iterator<ICompoundNode> {
	private final Iterator<? extends ICompoundNode> topNodeIter;
	private final Queue<ICompoundNode> lookAhead;

	public NodeTreeIterator(Iterator<? extends ICompoundNode> topNodeIterator){
		if(topNodeIterator == null) throw new IllegalArgumentException("iterator must exist");
		this.topNodeIter = topNodeIterator;
		this.lookAhead = new LinkedList<ICompoundNode>();
		readBranchFromTopNode();
	}
	
	public boolean hasNext() {
		return !this.lookAhead.isEmpty();
	}

	public ICompoundNode next() {
		ICompoundNode retVal = this.lookAhead.remove();
		if(this.lookAhead.isEmpty()){
			readBranchFromTopNode();
		}
		return retVal;
	}
	
//	@SuppressWarnings("unchecked")
	private void readBranchFromTopNode(){
		boolean nodesAdded = false;
		while(this.topNodeIter.hasNext() && !nodesAdded){
			final ICompoundNode nextNode = this.topNodeIter.next();
			Iterator<ICompoundNode> levelOrderIter = nextNode.levelOrderIterator();
			while (levelOrderIter.hasNext()) {
				ICompoundNode node = levelOrderIter.next();
				this.lookAhead.offer(node);
				nodesAdded = true;
			}
		}
	}
	
	public void remove() {
		throw new UnsupportedOperationException("This Iterator does not support removal");
	}
}
