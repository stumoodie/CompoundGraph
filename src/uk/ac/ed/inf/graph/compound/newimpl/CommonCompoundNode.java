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
import java.util.SortedSet;
import java.util.TreeSet;

import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElementVisitor;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.IElementAttribute;
import uk.ac.ed.inf.graph.util.IFilterCriteria;
import uk.ac.ed.inf.graph.util.IFilteredEdgeSet;
import uk.ac.ed.inf.graph.util.impl.ConnectedNodeIterator;
import uk.ac.ed.inf.graph.util.impl.DirectedEdgeSet;
import uk.ac.ed.inf.graph.util.impl.FilteredEdgeSet;

public abstract class CommonCompoundNode extends CompoundElement implements ICompoundNode {
	private static class EdgeExistanceCriteria implements IFilterCriteria<ICompoundEdge> {

		@Override
		public boolean matched(ICompoundEdge testObj) {
			return !testObj.isRemoved();
		}
		
	}

	private class CombinedConnectedNodeIterator implements Iterator<ICompoundNode> {
		private final ConnectedNodeIterator inNodeIterator;
		private final ConnectedNodeIterator outNodeIterator;
		
		public CombinedConnectedNodeIterator(CommonCompoundNode initialNode){
			this.inNodeIterator = new ConnectedNodeIterator(initialNode, edgeInList.iterator());
			this.outNodeIterator = new ConnectedNodeIterator(initialNode, edgeOutList.iterator());
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
		public ICompoundNode next() {
			ICompoundNode retVal = null;
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

	private static class CombinedEdgeIterator implements Iterator<ICompoundEdge> {
		private final Iterator<ICompoundEdge> inEdgeIterator;
		private final Iterator<ICompoundEdge> outEdgeIterator;
		
		public CombinedEdgeIterator(Iterator<ICompoundEdge> inEdgeIterator, Iterator<ICompoundEdge> outEdgeIterator){
//			final SortedSet<ICompoundEdge> inEdgesCopy = new TreeSet<ICompoundEdge>(edgeInList);
//			final SortedSet<ICompoundEdge> outEdgesCopy = new TreeSet<ICompoundEdge>(edgeOutList);
//			this.inEdgeIterator = inEdgesCopy.iterator();
//			this.outEdgeIterator = outEdgesCopy.iterator();
			this.inEdgeIterator = inEdgeIterator;
			this.outEdgeIterator = outEdgeIterator;
		}
		
		
		@Override
		public boolean hasNext() {
			boolean retVal = this.inEdgeIterator.hasNext();
			if(retVal == false){
				retVal = this.outEdgeIterator.hasNext();
			}
			return retVal;
		}

		@Override
		public ICompoundEdge next() {
			ICompoundEdge retVal = null;
			if(this.inEdgeIterator.hasNext()){
				retVal = this.inEdgeIterator.next();
			}
			else{
				retVal = this.outEdgeIterator.next();
			}
			return retVal;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("This iterator does not support removal");
		}
		
	}

	private final IFilteredEdgeSet<ICompoundNode, ICompoundEdge> edgeInList;
	private final IFilteredEdgeSet<ICompoundNode, ICompoundEdge> edgeOutList;

	protected CommonCompoundNode(int idx, IElementAttribute attribute){
		super(idx, attribute);
		this.edgeInList = new FilteredEdgeSet<ICompoundNode, ICompoundEdge>(new DirectedEdgeSet<ICompoundNode, ICompoundEdge>(), new EdgeExistanceCriteria());
		this.edgeOutList = new FilteredEdgeSet<ICompoundNode, ICompoundEdge>(new DirectedEdgeSet<ICompoundNode, ICompoundEdge>(), new EdgeExistanceCriteria());
	}
	
	@Override
	public Iterator<ICompoundNode> connectedNodeIterator() {
		return new CombinedConnectedNodeIterator(this);
	}

	@Override
	public Iterator<ICompoundEdge> edgeIterator() {
		return new CombinedEdgeIterator(this.edgeInList.iterator(), this.edgeOutList.iterator());
	}


	@Override
	public Iterator<ICompoundEdge> unfilteredEdgeIterator() {
		return new CombinedEdgeIterator(this.edgeInList.getUnfilteredEdgeSet().iterator(), this.edgeOutList.getUnfilteredEdgeSet().iterator());
	}

	@Override
	public int getDegree() {
		return this.edgeInList.size() + this.edgeOutList.size();
	}

	@Override
	public Iterator<ICompoundEdge> getEdgesWith(ICompoundNode other) {
		final SortedSet<ICompoundEdge> retVal = new TreeSet<ICompoundEdge>();
		if(this.edgeInList.hasEdgesWith(this, other)){
			Iterator<ICompoundEdge> iter = this.edgeInList.getEdgesWith(this, other); 
			while(iter.hasNext()){
				retVal.add(iter.next());
			}
		}
		if(this.edgeOutList.hasEdgesWith(this, other)){
			Iterator<ICompoundEdge> iter = this.edgeOutList.getEdgesWith(this, other);
			while(iter.hasNext()){
				retVal.add(iter.next());
			}
		}
		return retVal.iterator();
	}

	@Override
	public int getInDegree() {
		return this.edgeInList.size();
	}

	@Override
	public Iterator<ICompoundEdge> getInEdgeIterator() {
		return this.edgeInList.iterator();
	}

	@Override
	public Iterator<ICompoundEdge> getInEdgesFrom(ICompoundNode outNode) {
		return this.edgeInList.getEdgesWith(this, outNode);
	}

	@Override
	public Iterator<ICompoundNode> outEdgeIncidentNodesIterator() {
		return new ConnectedNodeIterator(this, this.edgeOutList.iterator());
	}

	@Override
	public int getOutDegree() {
		return this.edgeOutList.size();
	}

	@Override
	public Iterator<ICompoundEdge> getOutEdgeIterator() {
		return this.edgeOutList.iterator();
	}

	@Override
	public Iterator<ICompoundEdge> getOutEdgesTo(ICompoundNode inNode) {
		return this.edgeOutList.getEdgesWith(this, inNode);
	}

	@Override
	public Iterator<ICompoundNode> inEdgeIncidentNodesIterator() {
		return new ConnectedNodeIterator(this, this.edgeInList.iterator());
	}

	@Override
	public boolean hasEdgeWith(ICompoundNode other) {
		boolean retVal = false;
		if(other != null){ 
			retVal = this.edgeInList.hasEdgesWith(this, other);
			if(retVal == false){
				retVal = this.edgeOutList.hasEdgesWith(this, other);
			}
		}
		return retVal;
	}

	@Override
	public boolean hasInEdgeFrom(ICompoundNode outNode) {
		return this.edgeInList.hasEdgesWith(this, outNode);
	}

	@Override
	public boolean hasOutEdgeTo(ICompoundNode inNode) {
		return this.edgeOutList.hasEdgesWith(this, inNode);
	}

	@Override
	public boolean isEdge() {
		return false;
	}

	@Override
	public boolean isNode() {
		return true;
	}

	@Override
	public boolean containsEdge(ICompoundEdge edge) {
		boolean retVal = false;
		if(edge != null && edge.getGraph().equals(this.getGraph())){
			retVal = this.edgeInList.contains(edge) || this.edgeOutList.contains(edge);
		}
		return retVal;
	}

	@Override
	public boolean containsInEdge(ICompoundEdge edge) {
		boolean retVal = false;
		ICompoundGraph thisGraph = this.getGraph();
		if(edge != null && edge.getGraph().equals(thisGraph)){
			retVal = this.edgeInList.contains(edge);
		}
		return retVal;
	}

	@Override
	public boolean containsOutEdge(ICompoundEdge edge) {
		boolean retVal = false;
		if(edge != null && edge.getGraph().equals(this.getGraph())){
			retVal = this.edgeOutList.contains(edge);
		}
		return retVal;
	}

	@Override
	public void addOutEdge(ICompoundEdge compoundEdge) {
		this.edgeOutList.add(compoundEdge);
	}

	@Override
	public void addInEdge(ICompoundEdge compoundEdge) {
		this.edgeInList.add(compoundEdge);
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getGraph() == null) ? 0 : getGraph().hashCode());
		result = prime * result + getIndex();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof CommonCompoundNode)) {
			return false;
		}
		CommonCompoundNode other = (CommonCompoundNode) obj;
		if (getGraph() == null) {
			if (other.getGraph() != null) {
				return false;
			}
		} else if (!getGraph().equals(other.getGraph())) {
			return false;
		}
		if (getIndex() != other.getIndex()) {
			return false;
		}
		return true;
	}
	
	@Override
	public void visit(ICompoundGraphElementVisitor visitor){
		visitor.visitNode(this);
	}
}
