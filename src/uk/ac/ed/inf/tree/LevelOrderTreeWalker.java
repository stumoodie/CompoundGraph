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

class LevelOrderTreeWalker<T extends ITreeNode<T>> implements ITreeWalker<T> {
	private final T rootNode;
	private final ITreeNodeAction<T> visitor;
	
	public LevelOrderTreeWalker(T startNode, ITreeNodeAction<T> visitor){
		this.rootNode = startNode;
		this.visitor = visitor;
	}
	
	/* (non-Javadoc)
	 * @see uk.ac.ed.inf.tree.ITreeVisitor#getStarttNode()
	 */
	@Override
	public T getStartNode(){
		return this.rootNode;
	}
	
	/* (non-Javadoc)
	 * @see uk.ac.ed.inf.tree.ITreeVisitor#getVisitor()
	 */
	@Override
	public ITreeNodeAction<T> getAction(){
		return this.visitor;
	}
	
	/* (non-Javadoc)
	 * @see uk.ac.ed.inf.tree.ITreeVisitor#visitTree()
	 */
	@Override
	public void visitTree(){
		Iterator<T> iter = new LevelOrderTreeIterator<T>(this.rootNode);
		while(iter.hasNext() && visitor.canContinue()){
			T node = iter.next();
			visitor.visit(node);
		}
	}
}
