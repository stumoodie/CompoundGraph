package uk.ed.inf.graph.basic;


public interface IBasicSubgraph<
		N extends IBasicNode<N, ? extends IBasicEdge<N, E>>,
		E extends IBasicEdge<N, E>
> extends IBasicGraph<N, E> {

	IBasicGraph<N, E> getSuperGraph();
	
	boolean isInducedSubgraph();
	
	boolean isConsistentSnapShot();
}
