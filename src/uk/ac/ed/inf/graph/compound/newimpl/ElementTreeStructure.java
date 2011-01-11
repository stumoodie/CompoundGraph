package uk.ac.ed.inf.graph.compound.newimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import uk.ac.ed.inf.graph.compound.CompoundNodePair;
import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.tree.GeneralTree;
import uk.ac.ed.inf.tree.ITree;

public class ElementTreeStructure {
	private final List<ITree<ICompoundGraphElement>> topElements;
	private final Set<Integer> storedElements;
	private final IElementTreeFilter DEFAULT_FILTER = new IElementTreeFilter(){
		@Override
		public boolean matched(ICompoundGraphElement element) {
			return storedElements.contains(element.getIndex());
		}
	};
	private final IElementTreeFilter NODE_FILTER = new IElementTreeFilter(){
		@Override
		public boolean matched(ICompoundGraphElement element) {
			return element.isNode() && storedElements.contains(element.getIndex());
		}
	};
	private final IElementTreeFilter EDGE_FILTER = new IElementTreeFilter(){
		@Override
		public boolean matched(ICompoundGraphElement element) {
			return element.isEdge() && storedElements.contains(element.getIndex());
		}
	};
	
	public ElementTreeStructure() {
		this.topElements = new LinkedList<ITree<ICompoundGraphElement>>();
		this.storedElements = new TreeSet<Integer>();
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
		ITree<ICompoundGraphElement> topTree = new GeneralTree<ICompoundGraphElement>(element); 
		this.topElements.add(topTree);
		// now store the elements that exist at this point
		// Any that are deleted here will be ignored.
		Iterator<ICompoundGraphElement> iter = topTree.levelOrderIterator();
		while(iter.hasNext()){
			ICompoundGraphElement el = iter.next();
			this.storedElements.add(el.getIndex());
		}
//		this.addElement(element);
	}
	
//	public void addElement(ICompoundGraphElement element){
//		this.storedElements.add(element.getIndex());
//	}
	
	private boolean containsElement(IElementTreeFilter filter, Integer ... elementIdxs){
		boolean retVal = false;
//		List<Integer> elementList = new LinkedList<Integer>(Arrays.asList(elementIdxs));
//		elementList.retainAll(storedElements);
//		Iterator<ITree<ICompoundGraphElement>> iter =  this.topElements.iterator();
//		while(iter.hasNext() && !retVal){
//			ITree<ICompoundGraphElement> topTree = iter.next();
//			Iterator<ICompoundGraphElement> treeIter = topTree.levelOrderIterator();
//			while(treeIter.hasNext()){
//				ICompoundGraphElement treeElement = treeIter.next();
//				if(filter.matched(treeElement) && elementList.contains(treeElement.getIndex())){
//					retVal = true;
//				}
//			}
//		}
		List<Integer> elementList = Arrays.asList(elementIdxs);
//		elementList.retainAll(storedElements);
		Iterator<ITree<ICompoundGraphElement>> iter =  this.topElements.iterator();
		while(iter.hasNext() && !retVal){
			ITree<ICompoundGraphElement> topTree = iter.next();
			Iterator<ICompoundGraphElement> treeIter = new UnfilteredElementLevelOrderIterator(topTree.getRootNode());
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
			Iterator<ICompoundGraphElement> elementIter = new UnfilteredElementLevelOrderIterator(tree.getRootNode());
			while(elementIter.hasNext() && retVal == null){
				ICompoundGraphElement el = elementIter.next();
				if((el.getIndex() == elementIdx)){
					retVal = el;
				}
			}
		}
		return retVal;
	}

	public ICompoundNode getNode(int nodeIdx) {
		ICompoundNode retVal = null;
		ICompoundGraphElement el = getElement(nodeIdx);
		if(el != null && el.isNode()){
			retVal = (ICompoundNode)el;
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
//		for(ITree<ICompoundGraphElement> el : this.topElements){
//			numElements += el.size();
//		}
		Iterator<ICompoundGraphElement> elIter = this.elementIterator();
		while(elIter.hasNext()){
			elIter.next();
			numElements++;
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
		ICompoundGraphElement el = getElement(edgeIdx);
		if(el != null && el.isEdge()){
			retVal = (ICompoundEdge)el;
		}
		return retVal;
	}
	
	public boolean isEmpty() {
		return this.topElements.isEmpty();
	}

	public boolean containsDirectedConnection(CompoundNodePair testPair) {
		boolean retVal = false;
		if(testPair != null){
			Iterator<ITree<ICompoundGraphElement>> elementTreeIter = this.topElements.iterator();
			while(elementTreeIter.hasNext() && !retVal){
				ITree<ICompoundGraphElement> elementTree = elementTreeIter.next();
				Iterator<ICompoundGraphElement> iter = new UnfilteredElementLevelOrderIterator(elementTree.getRootNode());
				while(iter.hasNext() && ! retVal){
					ICompoundGraphElement element = iter.next();
					if(element instanceof ICompoundEdge){
						ICompoundEdge edge = (ICompoundEdge)element;
						retVal = storedElements.contains(edge.getIndex()) && edge.hasDirectedEnds(testPair);
					}
				}
			}
		}
		return retVal;
	}
	
	public boolean containsConnection(CompoundNodePair testPair) {
		boolean retVal = false;
		if(testPair != null){
			Iterator<ITree<ICompoundGraphElement>> elementTreeIter = this.topElements.iterator();
			while(elementTreeIter.hasNext() && !retVal){
				ITree<ICompoundGraphElement> elementTree = elementTreeIter.next();
				Iterator<ICompoundGraphElement> iter = new UnfilteredElementLevelOrderIterator(elementTree.getRootNode());
				while(iter.hasNext() && ! retVal){
					ICompoundGraphElement element = iter.next();
					if(element instanceof ICompoundEdge){
						ICompoundEdge edge = (ICompoundEdge)element;
						retVal = storedElements.contains(edge.getIndex()) && edge.hasEnds(testPair);
					}
				}
			}
		}
		return retVal;
	}

	public Iterator<ICompoundGraphElement> edgeLastElementIterator() {
		return new DFSNodeFirstIterator(this.topElementIterator(), DEFAULT_FILTER);
	}
	
}
