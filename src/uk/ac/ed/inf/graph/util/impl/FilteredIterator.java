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
import java.util.NoSuchElementException;

import uk.ac.ed.inf.graph.util.IFilterCriteria;

public final class FilteredIterator<F> implements Iterator<F> {
	
	private final Iterator<F> nodeListIterator;
	private final LookAheadManager lookAheadManager;
	private final IFilterCriteria<F> criteria;
	
	public FilteredIterator(Iterator<F> nodeListIterator, IFilterCriteria<F> criteria){
		if(nodeListIterator == null) throw new IllegalArgumentException("nodeListIterator cannot be null");
		this.nodeListIterator = nodeListIterator;
		this.lookAheadManager = new LookAheadManager();
		this.criteria = criteria;
	}
	
	public boolean hasNext() {
		this.lookAheadManager.readLookAhead();
		return this.lookAheadManager.isLookAheadSet();
	}

	public F next() {
		this.lookAheadManager.readLookAhead();
		F retVal = null;
		if(this.lookAheadManager.isLookAheadSet()){
			retVal = this.lookAheadManager.getLookAhead();
			this.lookAheadManager.consumeLookAhead();
		}
		else{
			throw new NoSuchElementException("Iterator is exhausted");
		}
		return retVal;
	}

	public void remove() {
		throw new UnsupportedOperationException("This iterator does not support removal");
	}
	
	private class LookAheadManager {
		private F lookAhead;
		
		public LookAheadManager(){
		}
		
		public F getLookAhead(){
			return this.lookAhead;
		}
		
		public boolean isLookAheadSet(){
			return this.lookAhead != null;
		}
		
		public void consumeLookAhead(){
			this.lookAhead = null;
		}

		// if lookahead is already set do nothing
		// only read a new one if it is consumed
		// if there is no new edge to be found then
		// the lookahead will not be set and so remain null.
		public void readLookAhead() {
			if(this.lookAhead == null){
				F nextNode = null;
				while(nodeListIterator.hasNext() && nextNode == null){
					nextNode = nodeListIterator.next();
					if(!criteria.matched(nextNode)){
						nextNode = null;
					}
				}
				this.lookAhead = nextNode;
			}
		}
	}
}
