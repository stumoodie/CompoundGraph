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

import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.ICompoundNodePair;

public class ConnectedNodeIterator implements Iterator<ICompoundNode> {
	
	private final Iterator<? extends ICompoundEdge> edgeIterator;
	private final ICompoundNode currNode;

	public ConnectedNodeIterator(ICompoundNode currNode, Iterator<? extends ICompoundEdge> edgeIterator) {
		this.edgeIterator = edgeIterator;
		this.currNode = currNode;
	}

	public boolean hasNext() {
		return edgeIterator.hasNext();
	}

	public ICompoundNode next() {
		ICompoundEdge edge = this.edgeIterator.next();
		ICompoundNodePair nodePair = edge.getConnectedNodes();
		ICompoundNode retVal = nodePair.getOtherNode(this.currNode);
		return retVal;
	}

	public void remove() {
		throw new UnsupportedOperationException(
				"Removal not supported by this iterator");
	}

}
