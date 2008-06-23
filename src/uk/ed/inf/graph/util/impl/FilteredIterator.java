package uk.ed.inf.graph.util.impl;

import java.util.Iterator;
import java.util.NoSuchElementException;

import uk.ed.inf.graph.util.IFilterCriteria;

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
