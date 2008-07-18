package uk.ed.inf.graph.util.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import uk.ed.inf.graph.basic.IBasicEdge;
import uk.ed.inf.graph.basic.IBasicNode;
import uk.ed.inf.graph.basic.IBasicPair;
import uk.ed.inf.graph.util.IEdgeSet;

public class EdgeSet <
		N extends IBasicNode<N, ? extends IBasicEdge<N, ?>>,
		E extends IBasicEdge<N, E>
> implements IEdgeSet<N, E> {
	
	private final SortedSet<E> edgeSet;

	public EdgeSet(){
		this.edgeSet = new TreeSet<E>();
	}
	
	public Iterator<E> iterator() {
		return this.edgeSet.iterator();
	}

	public SortedSet<E> getEdgesWith(N other) {
		return this.findConnectingEdge(other);
	}

	public boolean hasEdgesWith(N other) {
		Set<E> edges = findConnectingEdge(other);
		return !edges.isEmpty();
	}

	public boolean contains(int edgeIdx){
		return this.get(edgeIdx) != null;
	}
	
	public boolean contains(E edge){
		return this.edgeSet.contains(edge);
	}
	
	public boolean contains(N thisNode, N thatNode){
		boolean retVal = !findEdges(thisNode, thatNode).isEmpty();
		
		return retVal;
	}
	
	public SortedSet<E> get(N thisNode, N thatNode){
		SortedSet<E> retVal = findEdges(thisNode, thatNode);
		if(retVal.isEmpty()){
			throw new IllegalArgumentException("Nodes do not have at least one edge as expected");
		}
		return retVal;
	}
	
	private SortedSet<E> findEdges(N thisNode, N thatNode){
		final SortedSet<E> retVal = new TreeSet<E>();
		for(E edge : this.edgeSet){
			IBasicPair<N, E> ends = edge.getConnectedNodes();
			if(ends.hasEnds(thisNode, thatNode)){
				retVal.add(edge);
			}
		}
		return retVal;
	}
	
	public E get(int edgeIdx){
		E retVal = null;
		// assume is sorted
		for(E edge : this.edgeSet){
			if(edge.getIndex() == edgeIdx){
				retVal = edge;
				break;
			}
		}
		return retVal;

	}
	
	/**
	 * 
	 * @param edge
	 * @throws IllegalArgumentException if edge not in list
	 */
	public void remove(E edge) {
		if(!this.edgeSet.remove(edge)){
			throw new IllegalArgumentException("Deleted edge does not exist in this graph");
		}
	}

	public boolean add(E edge){
		return this.edgeSet.add(edge);
	}
	
	public int size(){
		return this.edgeSet.size();
	}
	
	private SortedSet<E> findConnectingEdge(N otherNode){
		final SortedSet<E> retVal = new TreeSet<E>();
		for(E edge : this.edgeSet){
			IBasicPair<N, E> pair = edge.getConnectedNodes();
			if(pair.containsNode(otherNode)){
				retVal.add(edge);
			}
		}
		return retVal;
	}

	public boolean addAll(Collection<? extends E> c) {
		return this.edgeSet.addAll(c);
	}

	public void clear() {
		this.clear();
	}

	public boolean contains(Object o) {
		return this.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return this.containsAll(c);
	}

	public boolean isEmpty() {
		return this.edgeSet.isEmpty();
	}

	public boolean remove(Object o) {
		return this.edgeSet.remove(o);
	}

	public boolean removeAll(Collection<?> c) {
		return this.edgeSet.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return this.edgeSet.retainAll(c);
	}

	public Object[] toArray() {
		return this.edgeSet.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return this.edgeSet.toArray(a);
	}
}
