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

package uk.ac.ed.inf.tree;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class AncestorTreeIterator<T extends ITreeNode<T>> implements Iterator<T> {
	private T currNode;
	private boolean visitedRoot = false;
	private final T rootNode;
	
	public AncestorTreeIterator(T startNode){
		this.currNode = startNode.getParent();
		this.rootNode = startNode.getRoot();
	}
	
	@Override
	public boolean hasNext() {
		return !visitedRoot;
	}

	@Override
	public T next() {
		T retVal = this.currNode;
		this.currNode = currNode.getParent();
		if(visitedRoot){
			throw new NoSuchElementException("Iterator is exhausted");
		}
		if(visitedRoot == false && retVal.equals(rootNode)){
			visitedRoot = true;
		}
		return retVal;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("removal not supported by this iterator");
	}

}
