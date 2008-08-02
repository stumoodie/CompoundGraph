package uk.ed.inf.tree;

import java.util.Iterator;

/**
 * Defines the node in a tree. The tree is defined from the root node. Each node can have
 * any number of children. To obtain tree-wide information and to manipulate the tree use
 * the <code>GeneralTree</code> class.
 * @param T the class the implements the node, which must implement this interface.
 * @author smoodie
 *
 */
public interface ITreeNode<T extends ITreeNode<T>> {
	
	/**
	 * Get the index (unique identifier) of this node. The node must be unique within this tree
	 *  (as define by the root node). 
	 * @return the identifier.
	 */
	int getIndex();
	
	
	/**
	 * Get the root node for the tree that this node belongs to.
	 * @return The root node, cannot be null.
	 */
	T getRoot();
	
	
	/**
	 * Is the given node the parent node of this one?
	 * @param parentNode The node to test as a parent. Can be null.
	 * @return true if it is the parent, false otherwise.
	 */
	boolean isParent(T parentNode);
	
	/**
	 * Get the parent node of this node. If this is the root node then its
	 * parent is itself.
	 * @return The parent node, cannot be null.
	 */
	T getParent();
	
	/**
	 * Tests if <code>childNode</code> is a child of this node. 
	 * @param childNode The node to test, can be null.
	 * @return true if it is a child of this node, false otherwise.
	 */
	boolean isChild(T childNode);
	
	/**
	 * Iterator over the immediate children of this node.
	 * @return An iterator to this nodes children.
	 */
	Iterator<? extends T> childIterator();
	
	/**
	 * Iterator that traverses directly from this node up to the root node.
	 * @return a new iterator instance, which cannot be null.
	 */
	Iterator<? extends T> ancestorIterator();
	
	/**
	 * Iterator that traverses down the tree in level-order, starting at this node.
	 * @return a new instance of the iterator, which cannot be null.
	 */
	Iterator<? extends T> levelOrderIterator();
	
//	/**
//	 * Get a visitor that will traverse the tree in level-order. This will start at this node
//	 * and progress up each level of the tree after visiting each node in the previous level. When
//	 * each node is visited the action specified in the <code>visitorAction</code> interface will be
//	 * performed.
//	 * @param visitorActions an implementation of an action to be performed when a node is visited. 
//	 * @return the tree visitor, which will not be null.
//	 */
//	ITreeWalker<T> levelOrderTreeVisitor(ITreeNodeAction<T> visitorAction);
//	
//	/**
//	 * Get a visitor that will traverse from this node down to the root node. At each node the action
//	 * specified by <code>visitorAction</code> will be specified.
//	 * @param visitorAction an implementation of an action to be performed when a node is visited. 
//	 * @return the tree visitor, which will not be null.
//	 */
//	ITreeWalker<T> ancestorTreeVisitor(ITreeNodeAction<T> visitorAction);
}
