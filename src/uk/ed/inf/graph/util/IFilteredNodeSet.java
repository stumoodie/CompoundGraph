package uk.ed.inf.graph.util;

import uk.ed.inf.graph.basic.IBasicEdge;
import uk.ed.inf.graph.basic.IBasicNode;

public interface IFilteredNodeSet<
		N extends IBasicNode<N, ? extends IBasicEdge<N, ?>>,
		E extends IBasicEdge<N, E>
    > extends INodeSet<N, E> {
	
	INodeSet<N, E> getUnfilteredNodeSet();
}
