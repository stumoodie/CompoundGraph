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
import java.util.LinkedList;
import java.util.Queue;


public class LevelOrderTreeIterator <T extends ITreeNode<T>> implements Iterator<T> {
	private final Queue<T> queue;
	
	public LevelOrderTreeIterator(T rootNode) {
		if(rootNode == null) throw new IllegalArgumentException("root node cannot be null");
		
		this.queue = new LinkedList<T>();
		queue.add(rootNode);
	}
	
	@Override
	public boolean hasNext() {
		return !queue.isEmpty();
	}

	@Override
	public T next() {
		T retVal = queue.remove();
		readChildren(retVal);
		return retVal;
	}
	
	private void readChildren(T parent){
		Iterator<T> iter = parent.childIterator();
		while(iter.hasNext()){
			this.queue.offer(iter.next());
		}
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("removal not supported by this iterator");
	}

}
