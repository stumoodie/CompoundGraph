package uk.ed.inf.graph.compound.impl;

import java.util.Iterator;

import uk.ed.inf.graph.compound.ICompoundChildEdgeFactory;
import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundGraph;
import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.ICompoundNodeFactory;
import uk.ed.inf.graph.compound.CompoundNodePair;
import uk.ed.inf.graph.compound.IRootChildCompoundGraph;
import uk.ed.inf.graph.compound.IRootCompoundNode;
import uk.ed.inf.graph.compound.ISubCompoundGraph;

public class RootChildCompoundGraph implements IRootChildCompoundGraph {
	private RootCompoundNode rootNode;

	public RootChildCompoundGraph(RootCompoundNode rootCompoundNode) {
		this.rootNode = rootCompoundNode;
	}

	@Override
	public RootCompoundNode getRoot() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICompoundGraph getSuperGraph() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean containsConnection(ICompoundNode thisNode, ICompoundNode thatNode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsConnection(CompoundNodePair nodePair) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsDirectedEdge(ICompoundNode outNode, ICompoundNode inNode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsDirectedEdge(CompoundNodePair ends) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsEdge(CompoundNodePair ends) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsEdge(ICompoundEdge edge) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsEdge(int edgeIdx) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsNode(int nodeIdx) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsNode(ICompoundNode node) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<ICompoundEdge> edgeIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<ICompoundGraphElement> elementIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICompoundEdge getEdge(int edgeIdx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICompoundNode getNode(int nodeIdx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumEdges() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumNodes() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Iterator<ICompoundNode> nodeIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int numElements() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean canCopyHere(ISubCompoundGraph subgraph) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canMoveHere(ISubCompoundGraph subGraph) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void copyHere(ISubCompoundGraph subgraph) {
		// TODO Auto-generated method stub

	}

	@Override
	public ICompoundChildEdgeFactory edgeFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ISubCompoundGraph getCopiedComponents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ISubCompoundGraph getMovedComponents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void moveHere(ISubCompoundGraph subGraph) {
		// TODO Auto-generated method stub

	}

	@Override
	public ICompoundNodeFactory nodeFactory() {
		// TODO Auto-generated method stub
		return null;
	}

}
