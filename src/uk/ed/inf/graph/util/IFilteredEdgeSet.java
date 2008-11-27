package uk.ed.inf.graph.util;

import uk.ed.inf.graph.basic.IBasicEdge;
import uk.ed.inf.graph.basic.IBasicNode;

public interface IFilteredEdgeSet<
		N extends IBasicNode<N, ? extends IBasicEdge<N, ?>>,
		E extends IBasicEdge<N, E>
    > extends IEdgeSet<N, E> {
	
	IEdgeSet<N, E> getUnfilteredEdgeSet();
}