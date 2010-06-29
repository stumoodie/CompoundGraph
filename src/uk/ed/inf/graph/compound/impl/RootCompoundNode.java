package uk.ed.inf.graph.compound.impl;

import java.util.Iterator;
import java.util.SortedSet;

import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundGraph;
import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.IRootChildCompoundGraph;
import uk.ed.inf.graph.compound.IRootCompoundNode;
import uk.ed.inf.tree.GeneralTree;

public class RootCompoundNode implements IRootCompoundNode {
	private int index;
	private RootChildCompoundGraph childCompoundGraph;
	private CompoundGraph graph;
	
	public RootCompoundNode(CompoundGraph graph, int index){
		this.graph = graph;
		this.index = index;
		this.childCompoundGraph = new RootChildCompoundGraph(this); 
	}
	
	@Override
	public IRootChildCompoundGraph getChildCompoundGraph() {
		return this.childCompoundGraph;
	}

	@Override
	public Iterator<ICompoundNode> connectedNodeIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<ICompoundEdge> edgeIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getDegree() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SortedSet<ICompoundEdge> getEdgesWith(ICompoundNode other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getInDegree() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Iterator<ICompoundEdge> getInEdgeIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SortedSet<ICompoundEdge> getInEdgesFrom(ICompoundNode outNode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<ICompoundNode> getInNodeIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getOutDegree() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Iterator<ICompoundEdge> getOutEdgeIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SortedSet<ICompoundEdge> getOutEdgesTo(ICompoundNode inNode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<ICompoundNode> getOutNodeIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasEdgeWith(ICompoundNode other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasInEdgeFrom(ICompoundNode outNode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasOutEdgeTo(ICompoundNode inNode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CompoundGraph getGraph() {
		return this.graph;
	}

	@Override
	public boolean isAncestor(ICompoundGraphElement testNode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDescendent(ICompoundGraphElement testNode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLink() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isNode() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int compareTo(ICompoundGraphElement o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Iterator<ICompoundGraphElement> ancestorIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<ICompoundGraphElement> childIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ICompoundGraphElement getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICompoundGraphElement getRoot() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isChild(ICompoundGraphElement childNode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isParent(ICompoundGraphElement parentNode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<ICompoundGraphElement> levelOrderIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<ICompoundGraphElement> preOrderIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isRemoved() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void markRemoved(boolean setRemoved) {
		// TODO Auto-generated method stub

	}

}
