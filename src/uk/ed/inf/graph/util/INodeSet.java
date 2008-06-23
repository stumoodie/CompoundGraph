package uk.ed.inf.graph.util;

import java.util.Set;

import uk.ed.inf.graph.basic.IBasicEdge;
import uk.ed.inf.graph.basic.IBasicNode;

public interface INodeSet<
		N extends IBasicNode<N, ? extends IBasicEdge<N, E>>,
		E extends IBasicEdge<N, E>
> extends Set<N> {
	
	N get(int nodeIdx);
	
	boolean contains(int nodeIdx);
}
