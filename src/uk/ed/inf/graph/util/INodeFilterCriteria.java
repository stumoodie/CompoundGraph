package uk.ed.inf.graph.util;

import uk.ed.inf.graph.basic.IBasicEdge;
import uk.ed.inf.graph.basic.IBasicNode;

public interface INodeFilterCriteria<
		N extends IBasicNode<N, ? extends IBasicEdge<N, E>>,
		E extends IBasicEdge<N, E>
>  extends IFilterCriteria<N> {

	boolean matched(N testNode);
	
}
