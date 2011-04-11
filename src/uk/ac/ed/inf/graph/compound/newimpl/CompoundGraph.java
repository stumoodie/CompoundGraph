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
package uk.ac.ed.inf.graph.compound.newimpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.ICompoundNodeFactory;
import uk.ac.ed.inf.graph.compound.IElementAttribute;
import uk.ac.ed.inf.graph.compound.IGraphRestoreStateAction;
import uk.ac.ed.inf.graph.compound.IGraphStructureChangeAction;
import uk.ac.ed.inf.graph.compound.IGraphStructureChangeListener;
import uk.ac.ed.inf.graph.compound.IRootCompoundNode;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraphFactory;
import uk.ac.ed.inf.graph.compound.ISubgraphRemovalBuilder;
import uk.ac.ed.inf.graph.state.IGraphState;
import uk.ac.ed.inf.graph.state.IGraphStateHandler;
import uk.ac.ed.inf.graph.state.IRestorableGraph;
import uk.ac.ed.inf.graph.state.IRestorableGraphElement;
import uk.ac.ed.inf.graph.util.IndexCounter;
import uk.ac.ed.inf.graph.util.impl.EdgeTreeIterator;
import uk.ac.ed.inf.graph.util.impl.NodeTreeIterator;
import uk.ac.ed.inf.tree.ITree;

public class CompoundGraph implements ICompoundGraph, IRestorableGraph {
	private final static int ROOT_NODE_IDX = 0;

	// added debug checks to graph
//    private final Logger logger = Logger.getLogger(this.getClass());
	private final IRootCompoundNode rootNode;
	private final IGraphStateHandler stateHandler;
	private final IndexCounter indexCounter;
	private final List<IGraphStructureChangeListener> graphStructureListeners;

//	private ICompoundGraphServices services;

	public CompoundGraph(IElementAttribute rootAttribute){
		this.stateHandler = new CompoundGraphStateHandler(this);
		this.rootNode = new RootCompoundNode(this, ROOT_NODE_IDX, rootAttribute);
		this.graphStructureListeners = new LinkedList<IGraphStructureChangeListener>();
		this.indexCounter = new IndexCounter();
	}
	
	@Override
	public IndexCounter getIndexCounter(){
		return this.indexCounter;
	}
	
	
	@Override
	public IRootCompoundNode getRoot() {
		return this.rootNode;
	}

	@Override
	public ITree<ICompoundGraphElement> getElementTree(){
		return this.getRoot().getChildCompoundGraph().getElementTree();
	}
	
	@Override
	public boolean containsConnection(ICompoundNode thisNode, ICompoundNode thatNode) {
		boolean retVal = false;
		if(thisNode != null && thatNode != null){
			ICompoundGraphElement node = this.getElementTree().getLowestCommonAncestor(thisNode, thatNode);
			if(node != null){
				retVal = node.getChildCompoundGraph().containsConnection(thisNode, thatNode);
			}
		}
		return retVal;
	}

	@Override
	public boolean containsDirectedEdge(ICompoundNode outNode, ICompoundNode inNode) {
		boolean retVal = false;
		if(inNode != null && outNode != null){
			ICompoundGraphElement node = this.getElementTree().getLowestCommonAncestor(inNode, outNode);
			if(node != null){
				retVal = node.getChildCompoundGraph().containsDirectedEdge(outNode, inNode);
			}
		}
		return retVal;
	}

	@Override
	public boolean containsEdge(ICompoundEdge edge) {
		return edge != null && !edge.isRemoved() && edge.getGraph().equals(this);
	}

	@Override
	public boolean containsEdge(int edgeIdx) {
		boolean retVal = false;
		Iterator<ICompoundGraphElement> iter = this.getElementTree().levelOrderIterator();
		while(iter.hasNext() && !retVal){
			ICompoundGraphElement node = iter.next();
			retVal = node.isEdge() && (node.getIndex() == edgeIdx);
		}
		return retVal;
	}

	@Override
	public boolean containsNode(int nodeIdx) {
		return this.getElementTree().containsNode(nodeIdx);
	}

	@Override
	public boolean containsNode(ICompoundNode node) {
		return node != null && !node.isRemoved() && node.getGraph().equals(this);
	}

	@Override
	public Iterator<ICompoundEdge> edgeIterator() {
		return new EdgeTreeIterator(this.getRoot());
	}

	@Override
	public Iterator<ICompoundGraphElement> elementIterator() {
		return this.getElementTree().levelOrderIterator();
	}

	@Override
	public ICompoundEdge getEdge(int edgeIdx) {
		ICompoundGraphElement retVal = this.getElementTree().get(edgeIdx);
		if(retVal != null && !(retVal instanceof ICompoundEdge)) throw new IllegalArgumentException("edgeIdx does not refer to an edge contained in this graph");
		return (ICompoundEdge)retVal;
	}

	@Override
	public ICompoundNode getNode(int nodeIdx) {
		ICompoundGraphElement retVal = this.getElementTree().get(nodeIdx);
		if(retVal != null && !(retVal instanceof ICompoundNode)) throw new IllegalArgumentException("nodeIdx does not refer to a node contained in this graph");
		return (ICompoundNode)retVal;
	}

	@Override
	public int numEdges() {
		Iterator<ICompoundEdge> iter = this.edgeIterator();
		int cntr = 0;
		while(iter.hasNext()){
			iter.next();
			cntr++;
		}
		return cntr;
	}

	@Override
	public int numNodes() {
		Iterator<ICompoundNode> iter = this.nodeIterator();
		int cntr = 0;
		while(iter.hasNext()){
			iter.next();
			cntr++;
		}
		return cntr;
	}

	@Override
	public Iterator<ICompoundNode> nodeIterator() {
		return new NodeTreeIterator(this.getRoot());
	}

	@Override
	public int numElements() {
		return this.getElementTree().size();
	}

	@Override
	public ICompoundEdgeFactory edgeFactory() {
		return new CompoundEdgeFactory(this);
	}

	@Override
	public ICompoundNodeFactory nodeFactory() {
		return this.getRoot().getChildCompoundGraph().nodeFactory();
	}

	@Override
	public ISubCompoundGraphFactory subgraphFactory() {
		return new SubCompoundGraphFactory(this);
	}

	@Override
	public IGraphState getCurrentState() {
		return stateHandler.createGraphState();
	}

	@Override
	public void restoreState(IGraphState previousState) {
		this.stateHandler.restoreState(previousState);
	}

	@Override
	public Iterator<IRestorableGraphElement> restorableElementIterator() {
		final Iterator<ICompoundGraphElement> iter = new UnfilteredElementLevelOrderIterator(rootNode);
		return new Iterator<IRestorableGraphElement>(){

			@Override
			public boolean hasNext() {
				return iter.hasNext();
			}

			@Override
			public IRestorableGraphElement next() {
				return iter.next();
			}

			@Override
			public void remove() {
				iter.remove();
			}
			
		};
	}

	@Override
	public ISubgraphRemovalBuilder newSubgraphRemovalBuilder() {
		return new CompoundSubgraphRemovalBuilder(this);
	}
	
	@Override
	public String toString(){
		StringBuilder buf = new StringBuilder(this.getClass().getSimpleName());
		buf.append("(");
		buf.append("numNodes=");
		buf.append(numNodes());
		buf.append(",numEdges=");
		buf.append(numEdges());
		buf.append(")");
		return buf.toString();
	}

	@Override
	public void addGraphStructureChangeListener(IGraphStructureChangeListener listener) {
		this.graphStructureListeners.add(listener);
	}

	@Override
	public void removeGraphStructureChangeListener(IGraphStructureChangeListener listener) {
		this.graphStructureListeners.remove(listener);
	}

	@Override
	public List<IGraphStructureChangeListener> getGraphStructureChangeListeners() {
		return new ArrayList<IGraphStructureChangeListener>(this.graphStructureListeners);
	}

	@Override
	public void notifyGraphStructureChange(IGraphStructureChangeAction graphStructureChangeAction) {
		for(IGraphStructureChangeListener l : this.graphStructureListeners){
			l.graphStructureChange(graphStructureChangeAction);
		}
	}

	@Override
	public void notifyGraphRestoreChange(IGraphRestoreStateAction e) {
		for(IGraphStructureChangeListener l : this.graphStructureListeners){
			l.notifyRestoreCompleted(e);
		}
	}
}
