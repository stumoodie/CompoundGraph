/*
  Licensed to the Court of the University of Edinburgh (UofE) under one
  or more contributor license agreements.  See the NOTICE file
  distributed with this work for additional information
  regarding copyright ownership.  The UofE licenses this file
  to you under the Apache License, Version 2.0 (the
  "License"); you may not use this file except in compliance
  with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing,
  software distributed under the License is distributed on an
  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
  KIND, either express or implied.  See the License for the
  specific language governing permissions and limitations
  under the License.
*/

package uk.ac.ed.inf.graph.util.impl;

import java.util.Iterator;

import uk.ac.ed.inf.graph.compound.CompoundNodePair;
import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundNode;

public class ConnectedNodeIterator implements Iterator<ICompoundNode> {
	
	private final Iterator<? extends ICompoundEdge> edgeIterator;
	private final ICompoundNode currNode;

	public ConnectedNodeIterator(ICompoundNode currNode, Iterator<? extends ICompoundEdge> edgeIterator) {
		this.edgeIterator = edgeIterator;
		this.currNode = currNode;
	}

	@Override
	public boolean hasNext() {
		return edgeIterator.hasNext();
	}

	@Override
	public ICompoundNode next() {
		ICompoundEdge edge = this.edgeIterator.next();
		CompoundNodePair nodePair = edge.getConnectedNodes();
		ICompoundNode retVal = nodePair.getOtherNode(this.currNode);
		return retVal;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException(
				"Removal not supported by this iterator");
	}

}
