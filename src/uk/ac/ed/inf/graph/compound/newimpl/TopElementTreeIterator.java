package uk.ac.ed.inf.graph.compound.newimpl;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.tree.ITree;

public class TopElementTreeIterator<T extends ICompoundGraphElement> implements Iterator<T> {
	private final Iterator<ITree<ICompoundGraphElement>> iter;
	private final IElementTreeFilter filter;
	private final Deque<ICompoundGraphElement> stack;
	
	
	public TopElementTreeIterator(Iterator<ITree<ICompoundGraphElement>> iter, IElementTreeFilter filter){
		this.iter = iter;
		this.filter = filter;
		this.stack = new LinkedList<ICompoundGraphElement>();
		readAhead();
	}
	
	private void readAhead(){
		while(this.stack.isEmpty() && this.iter.hasNext()){
			ICompoundGraphElement topElement = this.iter.next().getRootNode();
			if(this.filter.matched(topElement)){
				this.stack.push(topElement);
			}
		}
	}
	
	@Override
	public boolean hasNext() {
		return !this.stack.isEmpty();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T next() {
		ICompoundGraphElement retVal = this.stack.poll();
		readAhead();
		return (T)retVal;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("Remove not supported by this iterator");
	}

}
