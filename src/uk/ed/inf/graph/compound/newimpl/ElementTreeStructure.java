package uk.ed.inf.graph.compound.newimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.tree.GeneralTree;
import uk.ed.inf.tree.ITree;

public class ElementTreeStructure {
	private final IElementTreeFilter DEFAULT_FILTER = new IElementTreeFilter(){
		@Override
		public boolean matched(ICompoundGraphElement element) {
			return true;
		}
	};
	private final IElementTreeFilter NODE_FILTER = new IElementTreeFilter(){
		@Override
		public boolean matched(ICompoundGraphElement element) {
			return element.isNode();
		}
	};
	private final IElementTreeFilter EDGE_FILTER = new IElementTreeFilter(){
		@Override
		public boolean matched(ICompoundGraphElement element) {
			return element.isLink();
		}
	};
	private final List<ITree<ICompoundGraphElement>> topElements;
	
	public ElementTreeStructure() {
		this.topElements = new LinkedList<ITree<ICompoundGraphElement>>();
	}
	
	public boolean containsElement(ICompoundGraphElement ... elements){
		List<Integer> idxList = new ArrayList<Integer>(elements.length);
		for(ICompoundGraphElement element : elements){
			idxList.add(element.getIndex());
		}
		return containsElement(DEFAULT_FILTER, idxList.toArray(new Integer[0]));
	}
	
	public boolean containsElement(Integer ... elementIdxs){
		return containsElement(DEFAULT_FILTER, elementIdxs);
	}
	
	public boolean containsNodes(ICompoundNode ... nodes){
		List<Integer> idxList = new ArrayList<Integer>(nodes.length);
		for(ICompoundGraphElement element : nodes){
			idxList.add(element.getIndex());
		}
		return containsElement(NODE_FILTER, idxList.toArray(new Integer[0]));
	}
	
	public boolean containsNodes(Integer ... elementIdxs){
		return containsElement(NODE_FILTER, elementIdxs);
	}
	
	public boolean containsEdges(ICompoundEdge ... edges){
		List<Integer> idxList = new ArrayList<Integer>(edges.length);
		for(ICompoundGraphElement element : edges){
			idxList.add(element.getIndex());
		}
		return containsElement(EDGE_FILTER, idxList.toArray(new Integer[0]));
	}
	
	public boolean containsEdges(Integer ... elementIdxs){
		return containsElement(EDGE_FILTER, elementIdxs);
	}
	
	public void addTopElement(ICompoundGraphElement element){
		//precondition is that this not is not already contained
		this.topElements.add(new GeneralTree<ICompoundGraphElement>(element));
	}
	
	private boolean containsElement(IElementTreeFilter filter, Integer ... elementIdxs){
		boolean retVal = false;
		List<Integer> elementList = Arrays.asList(elementIdxs);
		Iterator<ITree<ICompoundGraphElement>> iter =  this.topElements.iterator();
		while(iter.hasNext() && !retVal){
			ITree<ICompoundGraphElement> topTree = iter.next();
			Iterator<ICompoundGraphElement> treeIter = topTree.levelOrderIterator();
			while(treeIter.hasNext()){
				ICompoundGraphElement treeElement = treeIter.next();
				if(filter.matched(treeElement) && elementList.contains(treeElement.getIndex())){
					retVal = true;
				}
			}
		}
		return retVal;

	}

	public Iterator<ICompoundGraphElement> elementIterator() {
		return new ElementTreeIterator<ICompoundGraphElement>(this.topElements.iterator(), DEFAULT_FILTER);
	}

	public Iterator<ICompoundEdge> edgeIterator() {
		return new ElementTreeIterator<ICompoundEdge>(this.topElements.iterator(), EDGE_FILTER);
	}

	public int numTopNodes() {
		Iterator<ICompoundNode> iter = this.topNodeIterator();
		int cnt = 0;
		while(iter.hasNext()){
			iter.next();
			cnt++;
		}
		return cnt;
	}

	public int numTopEdges() {
		Iterator<ICompoundEdge> iter = this.topEdgeIterator();
		int cnt = 0;
		while(iter.hasNext()){
			iter.next();
			cnt++;
		}
		return cnt;
	}

	public ICompoundGraphElement getElement(int elementIdx) {
		ICompoundGraphElement retVal = null;
		Iterator<ITree<ICompoundGraphElement>> iter = this.topElements.iterator();
		while(iter.hasNext() && retVal == null){
			ITree<ICompoundGraphElement> tree = iter.next();
			retVal = tree.get(elementIdx);
		}
		return retVal;
	}

	public ICompoundNode getNode(int nodeIdx) {
		ICompoundNode retVal = null;
		Iterator<ITree<ICompoundGraphElement>> iter = this.topElements.iterator();
		while(iter.hasNext() && retVal == null){
			ITree<ICompoundGraphElement> tree = iter.next();
			retVal = (ICompoundNode)tree.get(nodeIdx);
		}
		return retVal;
	}

	public int numEdges() {
		int cnt = 0;
		Iterator<ICompoundEdge> iter = this.edgeIterator();
		while(iter.hasNext()){
			iter.next();
			cnt++;
		}
		return cnt;
	}

	public int numNodes() {
		int cnt = 0;
		Iterator<ICompoundNode> iter = this.nodeIterator();
		while(iter.hasNext()){
			iter.next();
			cnt++;
		}
		return cnt;
	}

	public Iterator<ICompoundEdge> topEdgeIterator() {
		return new TopElementTreeIterator<ICompoundEdge>(this.topElements.iterator(), EDGE_FILTER);
	}

	public Iterator<ICompoundNode> nodeIterator() {
		return new ElementTreeIterator<ICompoundNode>(this.topElements.iterator(), NODE_FILTER);
	}

	public int numElements() {
		int numElements = 0;
		for(ITree<ICompoundGraphElement> el : this.topElements){
			numElements += el.size();
		}
		return numElements;
	}

	public int numTopElements() {
		return this.topElements.size();
	}

	public Iterator<ICompoundGraphElement> topElementIterator() {
		return new TopElementTreeIterator<ICompoundGraphElement>(this.topElements.iterator(), DEFAULT_FILTER);
	}

	public Iterator<ICompoundNode> topNodeIterator() {
		return new TopElementTreeIterator<ICompoundNode>(this.topElements.iterator(), NODE_FILTER);
	}

	public ICompoundEdge getEdge(int edgeIdx) {
		ICompoundEdge retVal = null;
		Iterator<ITree<ICompoundGraphElement>> iter = this.topElements.iterator();
		while(iter.hasNext() && retVal == null){
			ITree<ICompoundGraphElement> tree = iter.next();
			retVal = (ICompoundEdge)tree.get(edgeIdx);
		}
		return retVal;
	}
	
	public void addAll(Set<ICompoundGraphElement> topElements) {
		for(ICompoundGraphElement element : topElements){
			this.addTopElement(element);
		}
	}

	public boolean isEmpty() {
		return this.topElements.isEmpty();
	}
	
}
