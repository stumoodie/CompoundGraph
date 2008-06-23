package uk.ed.inf.graph.util;

import uk.ed.inf.graph.basic.IBasicEdge;
import uk.ed.inf.graph.basic.IBasicNode;

public interface INodeEdgeFilterCriteria<
		N extends IBasicNode<N, ? extends IBasicEdge<N, ?>>,
		E extends IBasicEdge<N, E>
> {

	boolean matchedNode(N testNode);
	
	boolean matchedEdge(E testEdge);
	
}
