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
package uk.ac.ed.inf.graph.compound.newimpl;

import java.util.Iterator;

import uk.ac.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundChildEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundGraphCopyBuilder;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ICompoundGraphMoveBuilder;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.ICompoundNodeFactory;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ac.ed.inf.graph.util.IFilterCriteria;
import uk.ac.ed.inf.graph.util.impl.DirectedEdgeSet;
import uk.ac.ed.inf.graph.util.impl.FilteredEdgeSet;
import uk.ac.ed.inf.graph.util.impl.FilteredNodeSet;
import uk.ac.ed.inf.graph.util.impl.NodeSet;

public abstract class CommonChildCompoundGraph implements IChildCompoundGraph {
	private class CombinedElementIterator implements Iterator<ICompoundGraphElement> {
		private final Iterator<? extends ICompoundGraphElement> inNodeIterator;
		private final Iterator<? extends ICompoundGraphElement> outNodeIterator;
		
		public CombinedElementIterator(Iterator<ICompoundNode> nodeIter, Iterator<ICompoundEdge> edgeIter){
			this.inNodeIterator = edgeIter;
			this.outNodeIterator = nodeIter;
		}
		
		
		@Override
		public boolean hasNext() {
			boolean retVal = this.inNodeIterator.hasNext();
			if(retVal == false){
				retVal = this.outNodeIterator.hasNext();
			}
			return retVal;
		}

		@Override
		public ICompoundGraphElement next() {
			ICompoundGraphElement retVal = null;
			if(this.inNodeIterator.hasNext()){
				retVal = this.inNodeIterator.next();
			}
			else{
				retVal = this.outNodeIterator.next();
			}
			return retVal;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("This iterator does not support removal");
		}
		
	}

	
	// added debug checks to graph
//    private final Logger logger = Logger.getLogger(this.getClass());
	private final FilteredEdgeSet<ICompoundNode, ICompoundEdge> edgeSet;
	private final FilteredNodeSet<ICompoundNode, ICompoundEdge> nodeSet;
	private final CompoundGraphElementFactory elementFactory = new CompoundGraphElementFactory();
	
	protected CommonChildCompoundGraph(){
		this.nodeSet = new FilteredNodeSet<ICompoundNode, ICompoundEdge>(new NodeSet<ICompoundNode, ICompoundEdge>(), new IFilterCriteria<ICompoundNode>(){

			@Override
			public boolean matched(ICompoundNode testObj) {
				return !testObj.isRemoved();
			}
	
		});
		this.edgeSet = new FilteredEdgeSet<ICompoundNode, ICompoundEdge>(new DirectedEdgeSet<ICompoundNode, ICompoundEdge>(),
				new IFilterCriteria<ICompoundEdge>(){

					@Override
					public boolean matched(ICompoundEdge testObj) {
						return !testObj.isRemoved();
					}
			
		});
	}
	
	@Override
	public ICompoundGraph getSuperGraph() {
		return this.getRoot().getGraph();
	}

	@Override
	public boolean containsConnection(ICompoundNode thisNode, ICompoundNode thatNode) {
		boolean retVal = false;
		if(thisNode != null && thatNode != null){
			retVal = this.edgeSet.contains(thisNode, thatNode) || this.edgeSet.contains(thatNode, thisNode);
		}
		return retVal;
	}

	@Override
	public boolean containsDirectedEdge(ICompoundNode outNode, ICompoundNode inNode) {
		boolean retVal = false;
		if(inNode != null && outNode != null){
			retVal = this.edgeSet.contains(outNode, inNode);
		}
		return retVal;
	}

	@Override
	public boolean containsEdge(ICompoundEdge edge) {
		boolean retVal = false;
		if(edge != null){
			retVal = this.edgeSet.contains(edge.getIndex());
		}
		return retVal;
	}

	@Override
	public boolean containsEdge(int edgeIdx) {
		return this.edgeSet.contains(edgeIdx);
	}

	@Override
	public boolean containsNode(int nodeIdx) {
		return this.nodeSet.contains(nodeIdx);
	}

	@Override
	public boolean containsNode(ICompoundNode node) {
		boolean retVal = false;
		if(node != null){
			retVal = containsNode(node.getIndex());
		}
		return retVal;
	}

	@Override
	public Iterator<ICompoundEdge> edgeIterator() {
		return this.edgeSet.iterator();
	}

//	@Override
//	public final Iterator<ICompoundEdge> unfilteredEdgeIterator() {
//		return this.edgeSet.getUnfilteredEdgeSet().iterator();
//	}
//	
//	@Override
//	public final Iterator<ICompoundNode> unfilteredNodeIterator() {
//		return this.nodeSet.getUnfilteredNodeSet().iterator();
//	}

	@Override
	public final Iterator<ICompoundGraphElement> unfilteredElementIterator() {
		return new CombinedElementIterator(this.nodeSet.getUnfilteredNodeSet().iterator(), this.edgeSet.getUnfilteredEdgeSet().iterator());
	}

	@Override
	public Iterator<ICompoundGraphElement> elementIterator() {
		return new CombinedElementIterator(this.nodeSet.iterator(), this.edgeSet.iterator());
	}

	@Override
	public ICompoundEdge getEdge(int edgeIdx) {
		return this.edgeSet.get(edgeIdx);
	}

	@Override
	public ICompoundNode getNode(int nodeIdx) {
		return this.nodeSet.get(nodeIdx);
	}

	@Override
	public int numEdges() {
		return this.edgeSet.size();
	}

	@Override
	public int numNodes() {
		return this.nodeSet.size();
	}

	@Override
	public Iterator<ICompoundNode> nodeIterator() {
		return this.nodeSet.iterator();
	}

	@Override
	public int numElements() {
		return this.nodeSet.size() + this.edgeSet.size();
	}

	@Override
	public void addNode(ICompoundNode node){
		this.nodeSet.add(node);
	}
	
	@Override
	public void addEdge(ICompoundEdge edge){
		this.edgeSet.add(edge);
	}
	
	
	@Override
	public ICompoundChildEdgeFactory edgeFactory() {
		return new CompoundChildEdgeFactory(this.getRoot());
	}

	@Override
	public ICompoundGraphCopyBuilder newCopyBuilder(){
		return new CompoundGraphCopyBuilder(this, this.elementFactory);
	}
	
	@Override
	public ICompoundGraphMoveBuilder newMoveBuilder(){
		return new CompoundGraphMoveBuilder(this, this.elementFactory);
	}
	
	@Override
	public ICompoundNodeFactory nodeFactory() {
		ICompoundNodeFactory fact = new CompoundNodeFactory(this.getRoot());
		return fact;
	}

	protected abstract void notifyCopyOperationComplete(ISubCompoundGraph originalSubgraph,
			ISubCompoundGraph copiedSubgraph);

	protected abstract void notifyMoveOperationComplete(ISubCompoundGraph originalSubgraph,
			ISubCompoundGraph movedSubgraph);

}
