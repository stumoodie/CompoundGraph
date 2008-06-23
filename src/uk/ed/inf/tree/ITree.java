package uk.ed.inf.tree;

import java.util.Iterator;

/**
 * A tree data structure. This class provides methods for manipulating the tree
 * or querying the tree structure and characteristics.
 * @author smoodie
 *
 * @param <T>
 */
public interface ITree<T extends ITreeNode<T>> {

	/**
	 * Get the root node of this tree. A tree wil at the very least have a root.
	 * @return The root node, cannot be null.
	 */
	T getRootNode();

	boolean containsNode(T testNode);

	boolean containsNode(int testIndex);

	T get(int testIndex);

	T getLowestCommonAncestor(T thisNode, T thatNode);
	
//	boolean hasLowestCommonAncestor(T thisNode, T thatNode);

	/**
	 * Tests if <code>testNode</code> is a descendent of <code>startNode</code>. This means traversing
	 * from the <code>startNode</code> until it finds the <code>testNode</code>.
	 * @param startNode the node to start from, can be null.
	 * @param testNode the node to be tested, can be null.
	 * @return <code>true</code> if <code>testNode</code> is an descendent of <code>startNode</code>,
	 *  <code>false</code> otherwise.
	 */
	boolean isDescendant(T startNode, T testNode);

	/**
	 * Tests if <code>testNode</code> is an anscestor of <code>startNode</code>. This means traversing
	 * from the <code>startNode</code> until it finds the <code>testNode</code>.
	 * @param startNode the node to start from, can be null.
	 * @param testNode the node to be tested, can be null.
	 * @return <code>true</code> if <code>testNode</code> is an ancestor of <code>startNode</code>,
	 *  <code>false</code> otherwise.
	 */
	boolean isAncestor(T startNode, T testNode);
	
	Iterator<T> levelOrderIterator();

//	/**
//	 * Obtain a visitor that will traverse the tree from the root node in level-order.
//	 * At each node the actions specified bu <code>visitorAction</code> will be performed.
//	 * @param visitorAction the action to be executed at each node visited.
//	 * @return a new instance of the tree visitor, which cannot be null.
//	 */
//	ITreeWalker<T> levelOrderTreeWalker(ITreeNodeAction<T> visitorAction);
	
	int size();
}