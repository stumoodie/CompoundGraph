package uk.ed.inf.tree;

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
	
	public boolean hasNext() {
		return !queue.isEmpty();
	}

	public T next() {
		T retVal = queue.remove();
		readChildren(retVal);
		return retVal;
	}
	
	@SuppressWarnings("unchecked")
	private void readChildren(T parent){
		Iterator<? extends ITreeNode> iter = parent.childIterator();
		while(iter.hasNext()){
			this.queue.offer((T)iter.next());
		}
	}

	public void remove() {
		throw new UnsupportedOperationException("removal not supported by this iterator");
	}

}
