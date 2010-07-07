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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.util.IEdgeSet;
import uk.ed.inf.graph.util.IFilterCriteria;
import uk.ed.inf.graph.util.IFilteredEdgeSet;

public class FilteredEdgeSet <
		N extends ICompoundNode,
		E extends ICompoundEdge
> implements IFilteredEdgeSet<N, E> {

	private final IEdgeSet<N, E> edgeSet;
	private final IFilterCriteria<E> criteria;
	
	public FilteredEdgeSet(IEdgeSet<N, E> edgeSet, IFilterCriteria<E> criteria){
		this.edgeSet = edgeSet;
		this.criteria = criteria;
	}

	public IEdgeSet<N, E> getUnfilteredEdgeSet(){
		return this.edgeSet;
	}
	
	public boolean add(E edge) {
		return this.edgeSet.add(edge);
	}

	public boolean addAll(Collection<? extends E> c) {
		return this.edgeSet.addAll(c);
	}

	public void clear() {
		this.edgeSet.clear();
	}

	@Override
	public boolean equals(Object obj) {
		return edgeSet.equals(obj);
	}

	@Override
	public int hashCode() {
		return edgeSet.hashCode();
	}

	@Override
	public String toString() {
		return edgeSet.toString();
	}

	@SuppressWarnings("unchecked")
	public boolean contains(Object o) {
		boolean retVal = false;
		if(o != null && this.edgeSet.contains(o)){
			if(o instanceof ICompoundEdge){
				E edge = (E)o;
//				retVal = !node.isRemoved();
				retVal = this.criteria.matched(edge);
			}
		}
		return retVal;
	}

	@SuppressWarnings("unchecked")
	public boolean containsAll(Collection<?> c) {
		boolean retVal = true;
		if(this.edgeSet.containsAll(c)){
			for(Object o : c){
				if(o instanceof ICompoundEdge){
					E node = (E)o;
					if(!this.criteria.matched(node)){
						retVal = false;
						break;
					}
				}
				else{
					retVal = false;
					break;
				}
			}
		}
		return retVal;
	}

	public boolean isEmpty() {
		return this.edgeSet.isEmpty() || this.size() == 0;
	}

	public Iterator<E> iterator() {
		return new FilteredIterator<E>(this.edgeSet.iterator(), this.criteria);
	}

	public boolean remove(Object o) {
		throw new UnsupportedOperationException("Removal not supported by this collection");
	}

	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException("Removal not supported by this collection");
	}

	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException("Modification that may require removal not supported by this collection");
	}

	public int size() {
		int count = 0;
		for(E edge : this.edgeSet){
			if(this.criteria.matched(edge)){
//				if(edge.isSelfEdge()){
//					count += 2;
//				}
//				else{
					count++;
//				}
			}
		}
		return count;
	}

	public Object[] toArray() {
		return createFilteredList().toArray();
	}

	public <T> T[] toArray(T[] a) {
		return createFilteredList().toArray(a);
	}
	
	private List<E> createFilteredList(){
		final List<E> retVal = new ArrayList<E>();
		for(E edge : this.edgeSet){
			if(this.criteria.matched(edge)){
				retVal.add(edge);
			}
		}
		return retVal;
	}

	public E get(int edgeIdx) {
		E retVal = this.edgeSet.get(edgeIdx);
		if(retVal == null || !this.criteria.matched(retVal)){
			throw new IllegalArgumentException("edge matching edgeIdx=" + edgeIdx + " cannot be found");
		}
		return retVal;
	}

	public Iterator<E> getEdgesWith(N thisNode, N otherNode) {
		Iterator<E> iter = this.edgeSet.getEdgesWith(thisNode, otherNode);
		List<E> retVal = new LinkedList<E>();
		while(iter.hasNext()){
			E edge = iter.next();
			if(!this.criteria.matched(edge)){
				iter.remove();
			}
			else{
				retVal.add(edge);
			}
		}
		if(retVal.isEmpty()){
			throw new IllegalArgumentException("No edges that matched the filter criteria contained this node");
		}
		return retVal.iterator();
	}

	public boolean hasEdgesWith(N thisNode, N otherNode) {
		boolean retVal = this.edgeSet.hasEdgesWith(thisNode, otherNode);
		if(retVal){
			// now check edge matches filter criteria
			int unmatchedCnt = 0;
			int edgeCntr = 0;
			Iterator<E> iter = this.edgeSet.getEdgesWith(thisNode, otherNode);
			while(iter.hasNext()){
				E edge = iter.next();
				edgeCntr++;
				if(!this.criteria.matched(edge)){
					unmatchedCnt++;
				}
			}
			retVal = (unmatchedCnt < edgeCntr); 
		}
		return retVal;
	}

	public boolean contains(N thisNode, N thatNode) {
		boolean retVal = this.edgeSet.contains(thisNode, thatNode);
		if(retVal == true){
			Set<E> edges = this.edgeSet.get(thisNode, thatNode);
			int unmatchedCnt = 0;
			Iterator<E> iter = edges.iterator();
			while(iter.hasNext()){
				E edge = iter.next();
				if(!this.criteria.matched(edge)){
					unmatchedCnt++;
				}
			}
			retVal = (unmatchedCnt < edges.size()); 
		}
		return retVal;
	}

	public SortedSet<E> get(N thisNode, N thatNode) {
		SortedSet<E> edges = this.edgeSet.get(thisNode, thatNode);
		Iterator<E> iter = edges.iterator();
		while(iter.hasNext()){
			E edge = iter.next();
			if(!this.criteria.matched(edge)){
				iter.remove();
			}
		}
		if(edges.isEmpty()){
			throw new IllegalArgumentException("Edges do not exist for these nodes as expected");
		}
		return edges;
	}

	public boolean contains(int edgeIdx) {
		boolean retVal = this.edgeSet.contains(edgeIdx);
		if(retVal){
			E edge = this.edgeSet.get(edgeIdx);
			retVal = this.criteria.matched(edge);
		}
		return retVal;
	}
}
