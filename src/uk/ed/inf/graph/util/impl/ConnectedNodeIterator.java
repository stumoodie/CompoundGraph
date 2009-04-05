/*
Copyright 2009, Court of the University of Edinburgh

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License. 
*/
package uk.ed.inf.graph.util.impl;

import java.util.Iterator;

import uk.ed.inf.graph.basic.IBasicEdge;
import uk.ed.inf.graph.basic.IBasicNode;
import uk.ed.inf.graph.basic.IBasicPair;

public class ConnectedNodeIterator<
		N extends IBasicNode<N, ? extends IBasicEdge<N, ?>>,
		E extends IBasicEdge<N, E>
	> implements Iterator<N> {
	
	private final Iterator<? extends E> edgeIterator;
	private final N currNode;

	public ConnectedNodeIterator(N currNode, Iterator<? extends E> edgeIterator) {
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
