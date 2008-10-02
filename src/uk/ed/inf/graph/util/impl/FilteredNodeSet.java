package uk.ed.inf.graph.util.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import uk.ed.inf.graph.basic.IBasicEdge;
import uk.ed.inf.graph.basic.IBasicNode;
import uk.ed.inf.graph.compound.base.BaseCompoundEdge;
import uk.ed.inf.graph.compound.base.BaseCompoundNode;
import uk.ed.inf.graph.util.IEdgeSet;
import uk.ed.inf.graph.util.IFilterCriteria;
import uk.ed.inf.graph.util.INodeSet;

public class FilteredNodeSet<
		N extends IBasicNode<N, ? extends IBasicEdge<N, ?>>,
		E extends IBasicEdge<N, E>
> implements INodeSet<N, E> {
	
	private final INodeSet<N, E> nodeSet;
	private final IFilterCriteria<N> criteria;
	
	public FilteredNodeSet(INodeSet<N, E> nodeSet, IFilterCriteria<N> criteria){
		this.nodeSet = nodeSet;
		this.criteria = criteria;
	}

	public boolean add(N o) {
		return this.nodeSet.add(o);
	}

	public boolean addAll(Collection<? extends N> c) {
		return this.nodeSet.addAll(c);
	}

	public void clear() {
		this.nodeSet.clear();
	}

	public boolean equals(Object o) {
		return nodeSet.equals(o);
	}

	public N get(int nodeIdx) {
		return nodeSet.get(nodeIdx);
	}

	public int hashCode() {
		return nodeSet.hashCode();
	}

	public boolean remove(Object o) {
		throw new UnsupportedOperationException("Removal not supported by this collection");
	}

	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException("Removal not supported by this collection");
	}

	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException("Modification that may lead to removals is not supported by this collection");
	}

	public String toString() {
		return nodeSet.toString();
	}

	@SuppressWarnings("unchecked")
	public boolean contains(Object o) {
		boolean retVal = false;
		if(this.nodeSet.contains(o)){
			if(o instanceof IBasicNode){
				N node = (N)o;
				if(this.criteria.matched(node)){
					retVal = true;
				}
			}
		}
		return retVal;
	}

	@SuppressWarnings("unchecked")
	public boolean containsAll(Collection<?> c) {
		boolean retVal = true;
		if(this.nodeSet.containsAll(c)){
			for(Object o : c){
				if(o instanceof IBasicNode){
					N node = (N)o;
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
		return this.nodeSet.isEmpty() || this.size() == 0;
	}

	public Iterator<N> iterator() {
		return new FilteredIterator<N>(this.nodeSet.iterator(), this.criteria);
	}

	public int size() {
		int count = 0;
		for(N node : this.nodeSet){
			if(this.criteria.matched(node)){
				count++;
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
	
	private List<N> createFilteredList(){
		final List<N> retVal = new ArrayList<N>();
		for(N node : this.nodeSet){
			if(this.criteria.matched(node)){
				retVal.add(node);
			}
		}
		return retVal;
	}

	public boolean contains(int nodeIdx) {
		boolean retVal = false;
		if(this.nodeSet.contains(nodeIdx)){
			N node = get(nodeIdx);
			if(this.criteria.matched(node)){
				retVal = true;
			}
		}
		return retVal;
	}

	public INodeSet<N, E> getUnfilteredNodeSet() {
		return this.nodeSet;
	}
}
