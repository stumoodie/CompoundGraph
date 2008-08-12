package uk.ed.inf.graph.compound;

import uk.ed.inf.graph.directed.IDirectedNode;
import uk.ed.inf.tree.ITreeNode;


public interface ICompoundNode<
		N extends ICompoundNode<N, ? extends ICompoundEdge<N, ?>> & ITreeNode<N>,
		E extends ICompoundEdge<N, E>
> extends IDirectedNode<N, E>, ITreeNode<N> {
	/**
	 * Get subgraph of this compound node. Note that this node is the root-node of the
	 * sub-Cigraph.
	 * @return
	 */
	IChildCompoundGraph<N, E> getChildCompoundGraph();
}
