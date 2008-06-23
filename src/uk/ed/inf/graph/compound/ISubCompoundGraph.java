package uk.ed.inf.graph.compound;

import java.util.Iterator;

import uk.ed.inf.graph.directed.IDirectedSubgraph;

public interface ISubCompoundGraph<
		N extends ICompoundNode<N, ? extends ICompoundEdge<N, ?>>,
		E extends ICompoundEdge<N, E>
> extends IDirectedSubgraph<N, E> {

	ICompoundGraph<N, E> getSuperGraph();
	
	N getNode(int nodeIdx);

	E getEdge(int edgeIdx);

	Iterator<N> nodeIterator();
	
	Iterator<E> edgeIterator();}
