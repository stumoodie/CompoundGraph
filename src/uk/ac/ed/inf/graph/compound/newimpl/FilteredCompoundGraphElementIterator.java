package uk.ac.ed.inf.graph.compound.newimpl;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.util.IFilterCriteria;

public class FilteredCompoundGraphElementIterator<C extends ICompoundGraphElement> implements Iterator<C> {
	private final Iterator<ICompoundGraphElement> elementIter;
	private final Deque<C> stack;
	private final IFilterCriteria<ICompoundGraphElement> matchCriteria;
	
	public FilteredCompoundGraphElementIterator(Iterator<ICompoundGraphElement> iter, IFilterCriteria<ICompoundGraphElement> matchCriteria){
		this.elementIter = iter;
		this.stack = new LinkedList<C>();
		this.matchCriteria = matchCriteria;
	}
	
	@Override
	public boolean hasNext() {
		findNextMatchingElement();
		return !this.stack.isEmpty();
	}

	@Override
	public C next() {
		return this.stack.pop();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("Remove not supported by this iterator");
	}
	
	@SuppressWarnings("unchecked")
	private void findNextMatchingElement(){
		while(this.elementIter.hasNext() && this.stack.isEmpty()){
			ICompoundGraphElement el = this.elementIter.next();
			if(matchCriteria.matched(el)){
				this.stack.push((C)el);
			}
		}
	}

}
