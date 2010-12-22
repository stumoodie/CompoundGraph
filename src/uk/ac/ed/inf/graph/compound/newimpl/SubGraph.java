package uk.ac.ed.inf.graph.compound.newimpl;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import uk.ac.ed.inf.graph.compound.CompoundNodePair;
import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElementVisitor;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.ISubGraph;
import uk.ac.ed.inf.graph.util.IFilterCriteria;
import uk.ac.ed.inf.graph.util.SubgraphAlgorithms;

public class SubGraph implements ISubGraph {
	private final SortedSet<ICompoundGraphElement> elements;
	private final ICompoundGraph graph;
	
	public SubGraph(ICompoundGraph graph){
		this.elements = new TreeSet<ICompoundGraphElement>(new Comparator<ICompoundGraphElement>() {
			@Override
			public int compare(ICompoundGraphElement o1, ICompoundGraphElement o2) {
				int retVal = o1.getIndex() < o2.getIndex() ? -1 : (o1.getIndex() > o2.getIndex() ? 1 : 0); 
				return retVal;
			}
		});
		this.graph = graph;
	}
	
	
	@Override
	public ICompoundGraph getSuperGraph() {
		return this.graph;
	}

	private ICompoundGraphElement getCompoundGraphElement(int idx){
		ICompoundGraphElement retVal = null;
		int lastIdx = Integer.MIN_VALUE;
		Iterator<ICompoundGraphElement> iter = this.elements.iterator();
		while(iter.hasNext() && retVal == null && lastIdx < idx){
			ICompoundGraphElement element = iter.next();
			lastIdx = element.getIndex();
			if(lastIdx == idx){
				retVal = element;
			}
		}
		return retVal;
	}
	
	@Override
	public ICompoundNode getNode(int nodeIdx) {
		ICompoundGraphElement retVal = this.getCompoundGraphElement(nodeIdx);
		if(retVal != null && !retVal.isNode()){
			retVal = null;
		}
		return (ICompoundNode)retVal;
	}

	@Override
	public ICompoundEdge getEdge(int edgeIdx) {
		ICompoundGraphElement retVal = this.getCompoundGraphElement(edgeIdx);
		if(retVal != null && !retVal.isEdge()){
			retVal = null;
		}
		return (ICompoundEdge)retVal;
	}

	@Override
	public ICompoundGraphElement getElement(int idx) {
		return this.getCompoundGraphElement(idx);
	}

	@Override
	public boolean isInducedSubgraph() {
		SubgraphAlgorithms alg = new SubgraphAlgorithms(this);
		return alg.isInducedSubgraph();
	}

	@Override
	public boolean isConsistentSnapShot() {
		Iterator<ICompoundGraphElement> iter = this.elements.iterator();
		boolean retVal = true;
		while(iter.hasNext() && retVal){
			ICompoundGraphElement element = iter.next();
			retVal = !element.isRemoved();
		}
		return retVal;
	}

	@Override
	public int getNumNodes() {
		int counter = 0;
		for(ICompoundGraphElement el : this.elements){
			if(el.isNode()) counter++;
		}

		return counter;
	}

	@Override
	public int getNumEdges() {
		int counter = 0;
		for(ICompoundGraphElement el : this.elements){
			if(el.isEdge()) counter++;
		}

		return counter;
	}

	@Override
	public int numElements() {
		return this.elements.size();
	}

	@Override
	public boolean containsElement(ICompoundGraphElement element) {
		return element != null && element.getGraph().equals(this.graph) && getCompoundGraphElement(element.getIndex()) != null;
	}

	@Override
	public boolean containsElement(int idx) {
		return getCompoundGraphElement(idx) != null;
	}

	@Override
	public boolean containsNode(int nodeIdx) {
		return getNode(nodeIdx) != null;
	}

	@Override
	public boolean containsNode(ICompoundNode node) {
		return node != null && node.getGraph().equals(this.graph) && getNode(node.getIndex()) != null;
	}

	@Override
	public boolean containsConnection(ICompoundNode thisNode, ICompoundNode thatNode) {
		// check if both in subgraph, then check for edge
		boolean retVal = false;
		if(thisNode != null && thatNode != null && thisNode.getGraph().equals(this.graph) && thatNode.getGraph().equals(this.graph)){
			Iterator<ICompoundEdge> edgeIter = thisNode.getEdgesWith(thatNode);
			while(edgeIter.hasNext() && !retVal){
				ICompoundEdge edge = edgeIter.next();
				retVal = this.elements.contains(edge);
			}
		}
		return retVal;
	}

	@Override
	public boolean containsConnection(CompoundNodePair pair) {
		boolean retVal = false;
		if(pair != null && pair.getGraph().equals(this.graph)){
			retVal = this.containsConnection(pair.getOutNode(), pair.getInNode());
		}
		return retVal;
	}

	@Override
	public boolean containsEdge(ICompoundEdge edge) {
		return edge != null && edge.getGraph().equals(this.graph) && this.getEdge(edge.getIndex()) != null;
	}

	@Override
	public boolean containsDirectedEdge(ICompoundNode outNode, ICompoundNode inNode) {
		// check if both in subgraph, then check for edge
		boolean retVal = false;
		if(outNode != null && inNode != null && outNode.getGraph().equals(this.graph) && inNode.getGraph().equals(this.graph)){
			Iterator<ICompoundEdge> edgeIter = outNode.getOutEdgesTo(inNode);
			while(edgeIter.hasNext() && !retVal){
				ICompoundEdge edge = edgeIter.next();
				retVal = this.elements.contains(edge);
			}
		}
		return retVal;
	}

	@Override
	public boolean containsDirectedEdge(CompoundNodePair pair) {
		boolean retVal = false;
		if(pair != null && pair.getGraph().equals(this.graph)){
			retVal = this.containsConnection(pair.getOutNode(), pair.getInNode());
		}
		return retVal;
	}

	@Override
	public boolean containsEdge(int edgeIdx) {
		return this.getEdge(edgeIdx) != null;
	}

	@Override
	public Iterator<ICompoundNode> nodeIterator() {
		return new FilteredCompoundGraphElementIterator<ICompoundNode>(this.elements.iterator(), new IFilterCriteria<ICompoundGraphElement>() {
			@Override
			public boolean matched(ICompoundGraphElement testObj) {
				return testObj.isNode();
			}
		});
	}

	@Override
	public Iterator<ICompoundEdge> edgeIterator() {
		return new FilteredCompoundGraphElementIterator<ICompoundEdge>(this.elements.iterator(), new IFilterCriteria<ICompoundGraphElement>() {
			@Override
			public boolean matched(ICompoundGraphElement testObj) {
				return testObj.isEdge();
			}
		});
	}

	@Override
	public Iterator<ICompoundGraphElement> elementIterator() {
		return this.elements.iterator();
	}

	@Override
	public boolean hasOrphanedEdges() {
		Set<ICompoundEdge> incidentEdges = new TreeSet<ICompoundEdge>();
		Set<ICompoundEdge> allEdgeList = new TreeSet<ICompoundEdge>();
		for(ICompoundGraphElement el : this.elements){
			if(el.isNode()){
				ICompoundNode node = (ICompoundNode)el;
				Iterator<ICompoundEdge> incidentEdgeIter = node.edgeIterator();
				while(incidentEdgeIter.hasNext()){
					ICompoundEdge edge = incidentEdgeIter.next(); 
					ICompoundNode otherNode = edge.getConnectedNodes().getOtherNode(node);
					// check that the other node is in this subgraph
					if(this.elements.contains(otherNode)){
						// it is so this is an incident edge
						incidentEdges.add(edge);
					}
				}
			}
			else{
				allEdgeList.add((ICompoundEdge)el);
			}
		}
		// get the difference between both sets.
		allEdgeList.removeAll(incidentEdges);
		// if there are any edges left over they must be orphaned
		return !allEdgeList.isEmpty();
	}


	@Override
	public void visitAll(ICompoundGraphElementVisitor visitor) {
		for(ICompoundGraphElement el : this.elements){
			el.visit(visitor);
		}
	}


	public void addElement(ICompoundGraphElement element) {
		this.elements.add(element);
	}

}
