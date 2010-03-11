package uk.ed.inf.graph.directed.impl;

import uk.ed.inf.graph.basic.IBasicPair;
import uk.ed.inf.graph.directed.IDirectedEdge;
import uk.ed.inf.graph.directed.IDirectedPair;

public class DirectedEdge implements IDirectedEdge<DirectedNode, DirectedEdge> {
	private final DirectedGraph graph;
	private final DirectedNode inNode;
	private final DirectedNode outNode;
	private final int index;
	private boolean isRemoved;
	
	public DirectedEdge(DirectedGraph graph, int index, DirectedNode outNode, DirectedNode inNode){
		this.graph = graph;
		this.index = index;
		this.outNode = outNode;
		this.inNode = inNode;
		this.isRemoved = false;
	}
	
	public IDirectedPair<DirectedNode, DirectedEdge> getConnectedNodes() {
		return new DirectedPair(this.outNode, this.inNode);
	}

	public boolean hasDirectedEnds(IDirectedPair<? super DirectedNode, ? super DirectedEdge> ends) {
		boolean retVal = false;
		if(ends != null){
			retVal = ends.hasDirectedEnds(this.outNode, this.inNode);
		}
		return retVal;
	}

	public DirectedGraph getGraph() {
		return this.graph;
	}

	public int getIndex() {
		return this.index;
	}

	public boolean hasEnds(IBasicPair<? super DirectedNode, ? super DirectedEdge> ends) {
		boolean retVal = false;
		if(ends != null){
			retVal = ends.hasEnds(this.outNode, this.inNode);
		}
		return retVal;
	}

	public boolean isRemoved() {
		return this.isRemoved;
	}

	public boolean isSelfEdge() {
		return this.inNode.equals(outNode);
	}

	public int compareTo(DirectedEdge o) {
		return this.getIndex() < o.getIndex() ? -1 : (this.getIndex() == o.getIndex() ? 0 : 1);
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
		if (!(obj instanceof DirectedEdge))
			return false;
		DirectedEdge other = (DirectedEdge) obj;
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
