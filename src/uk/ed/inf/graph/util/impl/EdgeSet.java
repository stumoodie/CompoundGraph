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

import java.util.Collection;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import uk.ed.inf.graph.compound.CompoundNodePair;
import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.util.IEdgeSet;

public class EdgeSet <
		N extends ICompoundNode,
		E extends ICompoundEdge
> implements IEdgeSet<N, E> {
	
	private final SortedSet<E> edgeSet;

	public EdgeSet(){
		this.edgeSet = new TreeSet<E>();
	}
	
	public Iterator<E> iterator() {
		return this.edgeSet.iterator();
	}

	public Iterator<E> getEdgesWith(N thisNode, N other) {
		return this.findConnectingEdge(thisNode, other);
	}

	public boolean hasEdgesWith(N thisNode, N other) {
		Iterator<E> edges = findConnectingEdge(thisNode, other);
		return edges.hasNext();
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
			CompoundNodePair ends = edge.getConnectedNodes();
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
	
	private Iterator<E> findConnectingEdge(N thisNode, N otherNode){
		final SortedSet<E> retVal = new TreeSet<E>();
		for(E edge : this.edgeSet){
			CompoundNodePair pair = edge.getConnectedNodes();
			if(pair.containsNode(otherNode) && pair.getOtherNode(thisNode).equals(otherNode)){
				retVal.add(edge);
			}
		}
		return retVal.iterator();
	}

	public boolean addAll(Collection<? extends E> c) {
		return this.edgeSet.addAll(c);
	}

	public void clear() {
		this.edgeSet.clear();
	}

	public boolean contains(Object o) {
		return this.edgeSet.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return this.edgeSet.containsAll(c);
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
	
	@Override
	public String toString(){
		return this.edgeSet.toString();
	}

}
