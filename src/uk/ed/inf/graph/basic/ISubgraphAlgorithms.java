package uk.ed.inf.graph.basic;


public interface ISubgraphAlgorithms<
		N extends IBasicNode<N, ? extends IBasicEdge<N, ?>>,
		E extends IBasicEdge<N, E>
> {

	IBasicSubgraph<N, E> getSubgraph();

	boolean isInducedSubgraph();

}