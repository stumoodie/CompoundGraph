package uk.ed.inf.graph.basic;

public interface IBasicNodeFactory<
		N extends IBasicNode<N, ? extends IBasicEdge<N, ?>>,
		E extends IBasicEdge<N, E>
> {

	N createNode();
	
}
