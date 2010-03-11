package uk.ed.inf.graph.directed.impl;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import uk.ed.inf.graph.directed.IDirectedNode;
import uk.ed.inf.graph.util.IFilterCriteria;
import uk.ed.inf.graph.util.IFilteredEdgeSet;
import uk.ed.inf.graph.util.impl.ConnectedNodeIterator;
import uk.ed.inf.graph.util.impl.DirectedEdgeSet;
import uk.ed.inf.graph.util.impl.FilteredEdgeSet;

public class DirectedNode implements IDirectedNode<DirectedNode, DirectedEdge> {
	private final DirectedGraph graph;
	private final int index;
	private final IFilteredEdgeSet<DirectedNode, DirectedEdge> edgeInList;
	private final IFilteredEdgeSet<DirectedNode, DirectedEdge> edgeOutList;
	private boolean isRemoved;
	
	public DirectedNode(DirectedGraph graph, int index){
		this.graph = graph;
		this.index = index;
		this.isRemoved = false;
		this.edgeInList = new FilteredEdgeSet<DirectedNode, DirectedEdge>(new DirectedEdgeSet<DirectedNode, DirectedEdge>(), new IFilterCriteria<DirectedEdge>(){

			public boolean matched(DirectedEdge testObj) {
				return !testObj.isRemoved();
			}
		});
		this.edgeOutList = new FilteredEdgeSet<DirectedNode, DirectedEdge>(new DirectedEdgeSet<DirectedNode, DirectedEdge>(), new IFilterCriteria<DirectedEdge>(){

			public boolean matched(DirectedEdge testObj) {
				return !testObj.isRemoved();
			}
		});
	}

	public int getInDegree() {
		return this.edgeInList.size();
	}

	public Iterator<DirectedEdge> getInEdgeIterator() {
		return this.edgeInList.iterator();
	}

	public SortedSet<DirectedEdge> getInEdgesFrom(DirectedNode outNode) {
		return this.edgeInList.getEdgesWith(this, outNode);
	}

	public Iterator<DirectedNode> getInNodeIterator() {
		return new ConnectedNodeIterator<DirectedNode, DirectedEdge>(this, this.edgeInList.iterator());
	}

	public int getOutDegree() {
		return this.edgeOutList.size();
	}

	public Iterator<DirectedEdge> getOutEdgeIterator() {
		return this.edgeOutList.iterator();
	}

	public SortedSet<DirectedEdge> getOutEdgesTo(DirectedNode inNode) {
		return this.edgeOutList.getEdgesWith(this, inNode);
	}

	public Iterator<DirectedNode> getOutNodeIterator() {
		return new ConnectedNodeIterator<DirectedNode, DirectedEdge>(this, this.edgeOutList.iterator());
	}

	public boolean hasInEdgeFrom(DirectedNode outNode) {
		return this.edgeInList.hasEdgesWith(this, outNode);
	}

	public boolean hasOutEdgeTo(DirectedNode inNode) {
		return this.edgeOutList.hasEdgesWith(this, inNode);
	}

	public int compareTo(DirectedNode o) {
		return this.getIndex() < o.getIndex() ? -1 : (this.getIndex() == o.getIndex() ? 0 : 1);
	}

	public Iterator<DirectedNode> connectedNodeIterator() {
		return new CombinedConnectedNodeIterator(this);
	}

	public Iterator<DirectedEdge> edgeIterator() {
		return new CombinedEdgeIterator();
	}

	public int getDegree() {
		return this.edgeInList.size() + this.edgeOutList.size();
	}

	public SortedSet<DirectedEdge> getEdgesWith(DirectedNode other) {
		if(other == null) throw new IllegalArgumentException("IOther cannot be null");
//		BaseCompoundNode other = (BaseCompoundNode)iOther;
		final SortedSet<DirectedEdge> retVal = new TreeSet<DirectedEdge>();
		if(this.edgeInList.hasEdgesWith(this, other)){
			retVal.addAll(this.edgeInList.getEdgesWith(this, other));
		}
		if(this.edgeOutList.hasEdgesWith(this, other)){
			retVal.addAll(this.edgeOutList.getEdgesWith(this, other));
		}
		if(retVal.isEmpty()){
			throw new IllegalArgumentException("edge must be contained by this node");
		}
		return retVal;
	}

	public DirectedGraph getGraph() {
		return this.graph;
	}

	public int getIndex() {
		return this.index;
	}

	public boolean hasEdgeWith(DirectedNode other) {
		boolean retVal = false;
		if(other != null){ 
			retVal = this.edgeInList.hasEdgesWith(this, other);
			if(retVal == false){
				retVal = this.edgeOutList.hasEdgesWith(this, other);
			}
		}
		return retVal;
	}

	public boolean isRemoved() {
		return this.isRemoved;
	}

	private class CombinedConnectedNodeIterator implements Iterator<DirectedNode> {
		private final ConnectedNodeIterator<DirectedNode, DirectedEdge> inNodeIterator;
		private final ConnectedNodeIterator<DirectedNode, DirectedEdge> outNodeIterator;
		
		public CombinedConnectedNodeIterator(DirectedNode initialNode){
			this.inNodeIterator = new ConnectedNodeIterator<DirectedNode, DirectedEdge>(initialNode, edgeInList.iterator());
			this.outNodeIterator = new ConnectedNodeIterator<DirectedNode, DirectedEdge>(initialNode, edgeOutList.iterator());
		}
		
		
		public boolean hasNext() {
			boolean retVal = this.inNodeIterator.hasNext();
			if(retVal == false){
				retVal = this.outNodeIterator.hasNext();
			}
			return retVal;
		}

		public DirectedNode next() {
			DirectedNode retVal = null;
			if(this.inNodeIterator.hasNext()){
				retVal = this.inNodeIterator.next();
			}
			else{
				retVal = this.outNodeIterator.next();
			}
			return retVal;
		}

		public void remove() {
			throw new UnsupportedOperationException("This iterator does not support removal");
		}
		
	}
	
	private class CombinedEdgeIterator implements Iterator<DirectedEdge> {
		private final Iterator<DirectedEdge> inEdgeIterator;
		private final Iterator<DirectedEdge> outEdgeIterator;
		
		public CombinedEdgeIterator(){
			final SortedSet<DirectedEdge> inEdgesCopy = new TreeSet<DirectedEdge>(edgeInList);
			final SortedSet<DirectedEdge> outEdgesCopy = new TreeSet<DirectedEdge>(edgeOutList);
			this.inEdgeIterator = inEdgesCopy.iterator();
			this.outEdgeIterator = outEdgesCopy.iterator();
		}
		
		
		public boolean hasNext() {
			boolean retVal = this.inEdgeIterator.hasNext();
			if(retVal == false){
				retVal = this.outEdgeIterator.hasNext();
			}
			return retVal;
		}

		public DirectedEdge next() {
			DirectedEdge retVal = null;
			if(this.inEdgeIterator.hasNext()){
				retVal = this.inEdgeIterator.next();
			}
			else{
				retVal = this.outEdgeIterator.next();
			}
			return retVal;
		}

		public void remove() {
			throw new UnsupportedOperationException("This iterator does not support removal");
		}
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((graph == null) ? 0 : graph.hashCode());
		result = prime * result + index;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof DirectedNode))
			return false;
		DirectedNode other = (DirectedNode) obj;
		if (graph == null) {
			if (other.graph != null)
				return false;
		} else if (!graph.equals(other.graph))
			return false;
		if (index != other.index)
			return false;
		return true;
	}
}
