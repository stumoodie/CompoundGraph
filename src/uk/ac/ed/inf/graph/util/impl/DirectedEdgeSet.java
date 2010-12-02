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
package uk.ac.ed.inf.graph.util.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import uk.ac.ed.inf.graph.compound.CompoundNodePair;
import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.util.IDirectedEdgeSet;

public class DirectedEdgeSet <
		N extends ICompoundNode,
		E extends ICompoundEdge
> implements IDirectedEdgeSet<N, E> {
	
	private final SortedSet<E> edgeSet;

	public DirectedEdgeSet(){
		this.edgeSet = new TreeSet<E>();
	}
	
	@Override
	public Iterator<E> iterator() {
		return this.edgeSet.iterator();
	}

	@Override
	public Iterator<E> getEdgesWith(N thisNode, N other) {
		return this.findConnectingEdge(thisNode, other);
	}

	@Override
	public boolean hasEdgesWith(N thisNode, N other) {
		Iterator<E> edges = findConnectingEdge(thisNode, other);
		return edges.hasNext();
	}

	@Override
	public boolean contains(int edgeIdx){
		return this.get(edgeIdx) != null;
	}
	
	public boolean contains(E edge){
		return this.edgeSet.contains(edge);
	}
	
	@Override
	public boolean contains(N outNode, N inNode){
		boolean retVal = !findEdges(outNode, inNode).isEmpty();
		
		return retVal;
	}
	
	@Override
	public SortedSet<E> get(N outNode, N inNode){
		SortedSet<E> retVal = findEdges(outNode, inNode);
		if(retVal.isEmpty()){
			throw new IllegalArgumentException("Nodes do not have at least one edge as expected");
		}
		return retVal;
	}
	
	private SortedSet<E> findEdges(N outNode, N inNode){
		final SortedSet<E> retVal = new TreeSet<E>();
		for(E edge : this.edgeSet){
			CompoundNodePair ends = edge.getConnectedNodes();
			if(ends.hasDirectedEnds(outNode, inNode)){
				retVal.add(edge);
			}
		}
		return retVal;
	}
	
	@Override
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

	@Override
	public boolean add(E edge){
		return this.edgeSet.add(edge);
	}
	
	@Override
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

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return this.edgeSet.addAll(c);
	}

	@Override
	public void clear() {
		this.edgeSet.clear();
	}

	@Override
	public boolean contains(Object o) {
		return this.edgeSet.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return this.edgeSet.containsAll(c);
	}

	@Override
	public boolean isEmpty() {
		return this.edgeSet.isEmpty();
	}

	@Override
	public boolean remove(Object o) {
		return this.edgeSet.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return this.edgeSet.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return this.edgeSet.retainAll(c);
	}

	@Override
	public Object[] toArray() {
		return this.edgeSet.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return this.edgeSet.toArray(a);
	}
}
