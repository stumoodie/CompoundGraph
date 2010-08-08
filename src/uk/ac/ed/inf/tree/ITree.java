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
package uk.ac.ed.inf.tree;

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

	Iterator<T> preOrderIterator();

	/**
	 * Obtain a visitor that will traverse the tree from the root node in level-order.
	 * At each node the actions specified by <code>visitorAction</code> will be performed.
	 * @param visitorAction the action to be executed at each node visited.
	 * @return a new instance of the tree visitor, which cannot be null.
	 */
	ITreeWalker<T> levelOrderTreeWalker(ITreeNodeAction<T> visitorAction);
	
	int size();
}