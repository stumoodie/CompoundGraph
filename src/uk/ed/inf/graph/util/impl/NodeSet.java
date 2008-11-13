package uk.ed.inf.graph.util.impl;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import uk.ed.inf.graph.basic.IBasicEdge;
import uk.ed.inf.graph.basic.IBasicNode;
import uk.ed.inf.graph.util.INodeSet;

public final class NodeSet<
		N extends IBasicNode<N, ? extends IBasicEdge<N, ?>>,
		E extends IBasicEdge<N, E>
> implements INodeSet<N, E> {
	
	private final SortedSet<N> nodeList;

	public NodeSet(){
		this.nodeList = new TreeSet<N>();
	}

	public boolean add(N o) {
		return nodeList.add(o);
	}

	public boolean addAll(Collection<? extends N> c) {
		return nodeList.addAll(c);
	}

	public void clear() {
		nodeList.clear();
	}

	public Comparator<? super N> comparator() {
		return nodeList.comparator();
	}

	public boolean contains(int nodeIdx){
		return this.basicGet(nodeIdx) != null;
	}
	
	public boolean contains(Object o) {
		return nodeList.contains(o);
	}

	N basicGet(int nodeIdx){
		N retVal = null;
		for(N node : this.nodeList){
			if(node.getIndex() == nodeIdx){
				retVal = node;
				break;
			}
		}
		return retVal;
	}
	
	public N get(int nodeIdx){
		N retVal = basicGet(nodeIdx);
		if(retVal == null){
			throw new IllegalArgumentException("set does not contain a node at this position nodeIdx = " + nodeIdx);
		}
		return retVal;
	}
	
	public boolean containsAll(Collection<?> c) {
		return nodeList.containsAll(c);
	}

	@Override
	public boolean equals(Object o) {
		return nodeList.equals(o);
	}

	@Override
	public int hashCode() {
		return nodeList.hashCode();
	}

	public boolean isEmpty() {
		return nodeList.isEmpty();
	}

	public Iterator<N> iterator() {
		return nodeList.iterator();
	}

	public boolean remove(Object o) {
		return nodeList.remove(o);
	}

	public boolean removeAll(Collection<?> c) {
		return nodeList.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return nodeList.retainAll(c);
	}

	public int size() {
		return nodeList.size();
	}

	public Object[] toArray() {
		return nodeList.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return nodeList.toArray(a);
	}
	
	@Override
	public String toString(){
		return this.nodeList.toString();
	}
}
