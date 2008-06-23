package uk.ed.inf.graph.undirected;

import uk.ed.inf.graph.basic.IBasicEdge;
import uk.ed.inf.graph.basic.IBasicNode;
import uk.ed.inf.graph.basic.IBasicPair;

public interface IUndirectedPair<
		N extends IBasicNode<N, ? extends IBasicEdge<N, E>>,
		E extends IBasicEdge<N, E>
> extends IBasicPair<N, E> {


	N getOneNode();
	
	N getTwoNode();
	
}
