/*
  Licensed to the Court of the University of Edinburgh (UofE) under one
  or more contributor license agreements.  See the NOTICE file
  distributed with this work for additional information
  regarding copyright ownership.  The UofE licenses this file
  to you under the Apache License, Version 2.0 (the
  "License"); you may not use this file except in compliance
  with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing,
  software distributed under the License is distributed on an
  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
  KIND, either express or implied.  See the License for the
  specific language governing permissions and limitations
  under the License.
*/

package uk.ac.ed.inf.graph.util.impl;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.IRootCompoundNode;

public class NodeTreeIterator implements Iterator<ICompoundNode> {
	private final Iterator<? extends ICompoundGraphElement> topNodeIter;
	private final Queue<ICompoundNode> lookAhead;

	public NodeTreeIterator(Iterator<? extends ICompoundGraphElement> topNodeIterator){
		if(topNodeIterator == null) throw new IllegalArgumentException("iterator must exist");
		this.topNodeIter = topNodeIterator;
		this.lookAhead = new LinkedList<ICompoundNode>();
		readBranchFromTopNode();
	}
	
	public NodeTreeIterator(IRootCompoundNode rootNode){
		if(rootNode == null) throw new IllegalArgumentException("node must exist");
		List<ICompoundNode> tmpList = Arrays.asList(new ICompoundNode[]{ rootNode });
		this.topNodeIter = tmpList.iterator();
		this.lookAhead = new LinkedList<ICompoundNode>();
		readBranchFromTopNode();
	}
	
	@Override
	public boolean hasNext() {
		return !this.lookAhead.isEmpty();
	}

	@Override
	public ICompoundNode next() {
		ICompoundNode retVal = this.lookAhead.remove();
		if(this.lookAhead.isEmpty()){
			readBranchFromTopNode();
		}
		return retVal;
	}
	
	private void readBranchFromTopNode(){
		boolean nodesAdded = false;
		while(this.topNodeIter.hasNext() && !nodesAdded){
			final ICompoundGraphElement nextNode = this.topNodeIter.next();
			Iterator<ICompoundGraphElement> levelOrderIter = nextNode.levelOrderIterator();
			while (levelOrderIter.hasNext()) {
				ICompoundGraphElement element = levelOrderIter.next(); 
				if(element instanceof ICompoundNode){
					ICompoundNode node = (ICompoundNode)element;
					this.lookAhead.offer(node);
					nodesAdded = true;
				}
			}
		}
	}
	
	@Override
	public void remove() {
		throw new UnsupportedOperationException("This Iterator does not support removal");
	}
}
