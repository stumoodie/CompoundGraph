package uk.ed.inf.graph.directed;

import java.util.Iterator;
import java.util.SortedSet;

import uk.ed.inf.graph.basic.IBasicNode;


public interface IDirectedNode<
		N extends IDirectedNode<N, ? extends IDirectedEdge<N, ?>>,
		E extends IDirectedEdge<N, E>
> extends IBasicNode<N, E> {
	int getInDegree();
	
	int getOutDegree();
	
	/**
	 * Has at least one edge coming into this one from outNode 
	 * @param outNode
	 * @return
	 */
	boolean hasInEdgeFrom(N outNode);
	
	SortedSet<E> getInEdgesFrom(N outNode);  
	
	/**
	 * Has at least one edge going out from this node to inNode.
	 * @param inNode
	 * @return
	 */
	boolean hasOutEdgeTo(N inNode);
	
	SortedSet<E> getOutEdgesTo(N inNode);  

	/**
	 * Gets all edges connecting this node
	 * @return
	 */
	Iterator<? extends E> getInEdgeIterator();
	
	/**
	 * Gets all edges going out from this node. 
	 * @return
	 */
	Iterator<? extends E> getOutEdgeIterator();

	Iterator<? extends N> getInNodeIterator();
	
	Iterator<? extends N> getOutNodeIterator();
}
