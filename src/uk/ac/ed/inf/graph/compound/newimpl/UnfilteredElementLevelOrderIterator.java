package uk.ac.ed.inf.graph.compound.newimpl;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;

public class UnfilteredElementLevelOrderIterator implements	Iterator<ICompoundGraphElement> {
	private final Deque<ICompoundGraphElement> stack;

	public UnfilteredElementLevelOrderIterator(ICompoundGraphElement topNode){
		this.stack = new LinkedList<ICompoundGraphElement>();
		this.stack.push(topNode);
	}
	
	private void readAhead(ICompoundGraphElement rootNode){
		Iterator<ICompoundGraphElement> iter = rootNode.getChildCompoundGraph().unfilteredElementIterator();
		while(iter.hasNext()){
			ICompoundGraphElement element = iter.next();
				this.stack.push(element);
		}
	}
	
	@Override
	public boolean hasNext() {
		return !this.stack.isEmpty();
	}

	@Override
	public ICompoundGraphElement next() {
		ICompoundGraphElement retVal = this.stack.poll();
		readAhead(retVal);
		return retVal;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("Remove not supported by this iterator");
	}

}
