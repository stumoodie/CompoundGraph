package uk.ed.inf.graph.util.impl;

import java.util.Iterator;

import uk.ed.inf.graph.basic.IBasicEdge;
import uk.ed.inf.graph.basic.IBasicNode;
import uk.ed.inf.graph.basic.IBasicPair;

public class ConnectedNodeIterator<
		N extends IBasicNode<N, ? extends IBasicEdge<N, E>>,
		E extends IBasicEdge<N, E>
	> implements Iterator<N> {
	
	private final Iterator<E> edgeIterator;
	private final N currNode;

	public ConnectedNodeIterator(N currNode, Iterator<E> edgeIterator) {
		this.edgeIterator = edgeIterator;
		this.currNode = currNode;
	}

	public boolean hasNext() {
		return edgeIterator.hasNext();
	}

	public N next() {
		E edge = this.edgeIterator.next();
		IBasicPair<N, E> nodePair = edge.getConnectedNodes();
		N retVal = nodePair.getOtherNode(this.currNode);
		return retVal;
	}

	public void remove() {
		throw new UnsupportedOperationException(
				"Removal not supported by this iterator");
	}

}
