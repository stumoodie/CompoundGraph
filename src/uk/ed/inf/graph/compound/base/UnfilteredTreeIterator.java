package uk.ed.inf.graph.compound.base;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import uk.ed.inf.graph.compound.ICompoundNode;

public class UnfilteredTreeIterator implements Iterator<BaseCompoundNode>{
	private final Queue<BaseCompoundNode> queue;
	
	public UnfilteredTreeIterator(BaseRootCompoundNode rootNode) {
		if(rootNode == null) throw new IllegalArgumentException("root node cannot be null");
		
		this.queue = new LinkedList<BaseCompoundNode>();
		queue.add(rootNode);
	}
	
	public boolean hasNext() {
		return !queue.isEmpty();
	}

	public BaseCompoundNode next() {
		BaseCompoundNode retVal = queue.remove();
		readChildren(retVal);
		return retVal;
	}
	
	private void readChildren(BaseCompoundNode parent){
		Iterator<ICompoundNode> iter = parent.getChildCompoundGraph().unfilteredNodeIterator();
		while(iter.hasNext()){
			this.queue.offer((BaseCompoundNode)iter.next());
		}
	}

	public void remove() {
		throw new UnsupportedOperationException("removal not supported by this iterator");
	}

}

