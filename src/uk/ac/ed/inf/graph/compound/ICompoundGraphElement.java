package uk.ac.ed.inf.graph.compound;

import uk.ac.ed.inf.graph.state.IRestorableGraphElement;
import uk.ac.ed.inf.tree.ITreeNode;


public interface ICompoundGraphElement extends IRestorableGraphElement, Comparable<ICompoundGraphElement>, ITreeNode<ICompoundGraphElement> {
	public static final int ROOT_LEVEL = 0;

	IElementAttribute getAttribute();
	
	/**
	 * Get subgraph of this compound node. Note that this node is the root-node of the
	 * sub-Cigraph.
	 * @return
	 */
	IChildCompoundGraph getChildCompoundGraph();
	
	boolean isDescendent(ICompoundGraphElement testNode);

    boolean isAncestor(ICompoundGraphElement testNode);

	/**
	 * Get the graph that owns this node.  
	 * @return The graph instance which cannot be null.
	 */
	ICompoundGraph getGraph();
	
	boolean isEdge();
	
	boolean isNode();
	
	void visit(ICompoundGraphElementVisitor visitor);
}
