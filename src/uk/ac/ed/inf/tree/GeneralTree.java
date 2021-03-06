/*
  Licensed to the Court of the University of Edinburgh (UofE) under one
  or more contributor license agreements.  See the NOTICE file
  distributed with this work for additional information
  regarding copyright ownership.  The UofE licenses this file
  to you under the Apache License, Version 2.0 (the
  "License"); you may not use this file except in compliance
  with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing,
  software distributed under the License is distributed on an
  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
  KIND, either express or implied.  See the License for the
  specific language governing permissions and limitations
  under the License.
*/

package uk.ac.ed.inf.tree;

import java.util.Iterator;


public final class GeneralTree<T extends ITreeNode<T>> implements ITree<T> {
	private final T rootNode;
	private final LCACalculator<T> lcaCalc;
	
	public GeneralTree(T rootNode){
		if(rootNode == null) throw new NullPointerException("root node cannot be null");
		
		this.rootNode = rootNode;
		this.lcaCalc = new LCACalculator<T>(rootNode);
	}
	
	/* (non-Javadoc)
	 * @see uk.ed.inf.graph.impl.ITree#getRootNode()
	 */
	@Override
	public T getRootNode(){
		return this.rootNode;
	}
	
	/* (non-Javadoc)
	 * @see uk.ed.inf.graph.impl.ITree#containsNode(T)
	 */
	@Override
	public boolean containsNode(T testNode){
		boolean retVal = false;
		if(testNode != null && this.rootNode.equals(testNode.getRoot())){
			retVal = this.containsNode(testNode.getIndex());
		}
		return retVal;
	}
	
	/* (non-Javadoc)
	 * @see uk.ed.inf.graph.impl.ITree#constainsNode(int)
	 */
	@Override
	public boolean containsNode(int testIndex){
		Iterator<T> iter = this.levelOrderIterator();
		boolean retVal = false;
		while(iter.hasNext() && retVal == false){
			T node = iter.next();
			if(node.getIndex() == testIndex){
				retVal = true;
			}
		}
		return retVal;
	}
	
	/* (non-Javadoc)
	 * @see uk.ed.inf.graph.impl.ITree#get(int)
	 */
	@Override
	public T get(int testIndex){
		Iterator<T> iter = this.levelOrderIterator();
		T retVal = null;
		while(iter.hasNext() && retVal == null){
			T node = iter.next();
			if(node.getIndex() == testIndex){
				retVal = node;
			}
		}
		return retVal;
	}
	
	@Override
	public T getLowestCommonAncestor(final T thisNode, final T thatNode){
		if(!thisNode.getRoot().equals(this.rootNode) || !thatNode.getRoot().equals(this.rootNode))
			throw new IllegalArgumentException("Both nodes must belong to the same this tree");
		
		this.lcaCalc.findLowestCommonAncestor(thisNode, thatNode);
		return this.lcaCalc.getLCANode();
	}
	
	@Override
	public Iterator<T> levelOrderIterator(){
		return new LevelOrderTreeIterator<T>(this.rootNode);
	}
	
	@Override
	public Iterator<T> preOrderIterator(){
		return new PreOrderTreeIterator<T>(this.rootNode);
	}
	
	/**
	 * Tests if <code>testNode</code> is a descendant of <code>startNode</code>. This means traversing
	 * from the <code>startNode</code> until it finds the <code>testNode</code>.
	 * @param startNode the node to start from, can be null.
	 * @param testNode the node to be tested, can be null.
	 * @return <code>true</code> if <code>testNode</code> is an descendant of <code>startNode</code>,
	 *  <code>false</code> otherwise.
	 */
	@Override
	public boolean isDescendant(T startNode, T testNode){
		boolean retVal = false;
		Iterator<T> iter = new LevelOrderTreeIterator<T>(startNode);
		iter.next(); // skip startNode - something cannot be a descendant of itself
		while(iter.hasNext() && retVal == false){
			T node = iter.next();
			if(node.equals(testNode)){
				retVal = true;
			}
		}
		return retVal;
	}
	
	@Override
	public int size(){
		Iterator<T> iter = this.levelOrderIterator();
		int cnt = 0;
		while(iter.hasNext()){
			iter.next();
			cnt++;
		}
		return cnt;
	}

	@Override
	public boolean isAncestor(T startNode, T testNode) {
		boolean retVal = false;
		Iterator<T> iter = new AncestorTreeIterator<T>(startNode);
		while(iter.hasNext() && retVal == false){
			T node = iter.next();
			if(node.equals(testNode)){
				retVal = true;
			}
		}
		return retVal;
	}

	@Override
	public ITreeWalker<T> levelOrderTreeWalker(ITreeNodeAction<T> visitorAction) {
		return new LevelOrderTreeWalker<T>(this.rootNode, visitorAction);
	}
}
