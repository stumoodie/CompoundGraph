package uk.ed.inf.graph.util.impl;

import java.util.Iterator;

public class AppendingIterator<T> implements Iterator<T> {
	private Iterator<T> currIter;
	private Iterator<T> secondIter;
	
	public AppendingIterator(Iterator<T> firstIter, Iterator<T> secondIter){
		this.currIter = firstIter;
		this.secondIter = secondIter;
	}
	
	public boolean hasNext() {
		if(!this.currIter.hasNext()){
			if(this.secondIter != null){
				this.currIter = this.secondIter;
				this.secondIter = null;
			}
		}
		return this.currIter.hasNext();
	}

	public T next() {
		if(!this.currIter.hasNext()){
			if(this.secondIter != null){
				this.currIter = this.secondIter;
				this.secondIter = null;
			}
		}
		return this.currIter.next();
	}

	public void remove() {
		throw new UnsupportedOperationException("Removal not supported by this iterator");
	}

}
