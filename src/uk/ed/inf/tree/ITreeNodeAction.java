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
