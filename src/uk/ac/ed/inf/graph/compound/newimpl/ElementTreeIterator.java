package uk.ac.ed.inf.graph.compound.newimpl;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.tree.ITree;

public class ElementTreeIterator<T extends ICompoundGraphElement> implements Iterator<T> {
	private final Iterator<ITree<ICompoundGraphElement>> iter;
//	private Iterator<ICompoundGraphElement> currIter;
	private final IElementTreeFilter filter;
	private final Deque<ICompoundGraphElement> stack;
//	private ICompoundGraphElement rootNode;
	
	
	public ElementTreeIterator(Iterator<ITree<ICompoundGraphElement>> iter, IElementTreeFilter filter){
		this.iter = iter;
		this.filter = filter;
		this.stack = new LinkedList<ICompoundGraphElement>();
		storeNextTopNode();
	}
	
	private void storeNextTopNode(){
		while(iter.hasNext() && stack.isEmpty()){
			ICompoundGraphElement element = iter.next().getRootNode();
			if(this.filter.matched(element)){
				this.stack.push(element);
			}
			else{
				readAhead(element);
			}
		}
	}
	
//	private void readAhead(){
//		while(this.currIter.hasNext() && this.stack.isEmpty()){
//			ICompoundGraphElement topElement = this.currIter.next();
//			if(this.filter.matched(topElement)){
//				this.stack.push(topElement);
//			}
//		}
//		if(this.stack.isEmpty() && this.iter.hasNext()){
//			this.currIter = iter.next().getRootNode().getChildCompoundGraph().unfilteredElementIterator();
//			readAhead();
//		}
//	}
	
	private void readAhead(ICompoundGraphElement rootNode){
		Iterator<ICompoundGraphElement> iter = rootNode.getChildCompoundGraph().unfilteredElementIterator();
		while(iter.hasNext()){
			ICompoundGraphElement element = iter.next();
			if(this.filter.matched(element)){
				this.stack.push(element);
			}
			else{
				readAhead(element);
			}
		}
		if(this.stack.isEmpty()){
			storeNextTopNode();
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
//		this.rootNode = retVal;
		readAhead(retVal);
		return (T)retVal;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("Remove not supported by this iterator");
	}

}
