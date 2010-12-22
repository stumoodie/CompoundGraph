package uk.ac.ed.inf.graph.compound.newimpl;

import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.SortedSet;
import java.util.TreeSet;

import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;

public class DFSNodeFirstIterator implements Iterator<ICompoundGraphElement> {
	private final Deque<ICompoundGraphElement> stack;
	
	public DFSNodeFirstIterator(Iterator<ICompoundGraphElement> topElements){
		this.stack = new LinkedList<ICompoundGraphElement>();
		SortedSet<ICompoundGraphElement> cache = createSortedCache();
		while(topElements.hasNext()){
			cache.add(topElements.next());
		}
		for(ICompoundGraphElement topElement : cache){
			stack.push(topElement);
		}
	}
	
	@Override
	public boolean hasNext() {
		return !this.stack.isEmpty();
	}

	@Override
	public ICompoundGraphElement next() {
		ICompoundGraphElement retVal = this.stack.pop();
		populate(retVal);
		return retVal;
	}
	
	private SortedSet<ICompoundGraphElement> createSortedCache(){
		SortedSet<ICompoundGraphElement> cache = new TreeSet<ICompoundGraphElement>(new Comparator<ICompoundGraphElement>(){

			@Override
			public int compare(ICompoundGraphElement o1, ICompoundGraphElement o2) {
				int retVal = 0;
				if(o1.isNode() && o2.isEdge()){
					retVal = 1;
				}
				else if(o1.isEdge() && o2.isNode()){
					retVal = -1;
				}
				if(retVal == 0){
					retVal = o1.getIndex() < o2.getIndex() ? 1 : (o1.getIndex() > o2.getIndex() ? -1 : 0);
				}
				return retVal;
			}
			
		});
		return cache;
	}

	private void populate(ICompoundGraphElement parent) {
		// strategy is to sort the elements here do that the nodes in the child graph are DF traversed before the links 
		Iterator<ICompoundGraphElement> iter = parent.getChildCompoundGraph().elementIterator();
		SortedSet<ICompoundGraphElement> cache = createSortedCache();
		while(iter.hasNext()){
			cache.add(iter.next());
		}
		for(ICompoundGraphElement element : cache){
			this.stack.push(element);
		}
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("Removal is not supported by this interator");
	}

}
