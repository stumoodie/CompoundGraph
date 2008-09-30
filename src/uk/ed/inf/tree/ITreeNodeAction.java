package uk.ed.inf.tree;

public interface ITreeNodeAction<T extends ITreeNode<T>> {

	/**
	 * Action to be performed at a given node.
	 * @param node The node that is to be acted upon.
	 * @throws IllegalStateException if <code>canContinue() == false</code>.
	 */
	void visit(T node);

	/**
	 * Tests whether the node walker should continue based on the outcome of the last action executed. 
	 * @return true if it should continue, false if no.
	 */
	boolean canContinue();
	
}
