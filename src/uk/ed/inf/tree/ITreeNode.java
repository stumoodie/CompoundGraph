/*
Copyright 2009, Court of the University of Edinburgh

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License. 
*/
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
	 * Gets the level of the tree.
	 * @return the level of this node in the tree.
	 */
	int getLevel();
	
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
	Iterator<T> childIterator();
	
	/**
	 * Iterator that traverses directly from this node up to the root node.
	 * @return a new iterator instance, which cannot be null.
	 */
	Iterator<T> ancestorIterator();
	
	/**
	 * Iterator that traverses down the tree in level-order, starting at this node.
	 * @return a new instance of the iterator, which cannot be null.
	 */
	Iterator<T> levelOrderIterator();

	/**
	 * Iterator that traverses down the tree from this node in pre-order, i.e., node, 1st child subtree, 2nd child subtree etc. 
	 * @return a new instance of the iterator, which cannot be null.
	 */
	Iterator<T> preOrderIterator();
}
