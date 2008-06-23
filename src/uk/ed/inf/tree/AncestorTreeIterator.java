package uk.ed.inf.tree;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class AncestorTreeIterator<T extends ITreeNode<T>> implements Iterator<T> {
	private T currNode;
	private boolean visitedRoot = false;
	private final T rootNode;
	
	public AncestorTreeIterator(T startNode){
		this.currNode = startNode;
		this.rootNode = startNode.getRoot();
	}
	
	public boolean hasNext() {
		return !visitedRoot;
	}

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

	public void remove() {
		throw new UnsupportedOperationException("removal not supported by this iterator");
	}

}
