package uk.ed.inf.graph.compound;

import uk.ed.inf.graph.directed.IDirectedGraph;


public interface ICompoundGraph<
		N extends ICompoundNode<N, ? extends ICompoundEdge<N, E>>,
		E extends ICompoundEdge<N, E>
> extends IDirectedGraph<N, E> {

//	/**
//	 * Gets the least common ancestor node. This is where links that span sub-graphs
//	 * should go. 
//	 * @param inNode
//	 * @param outNode
//	 * @return the lca node, which is guaranteed to be non-null
//	 */
//	ICiNode<N, E> getLcaNode(N inNode, N outNode);
	
	N getRoot();
}
