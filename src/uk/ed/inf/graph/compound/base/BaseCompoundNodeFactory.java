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
package uk.ed.inf.graph.compound.base;

import uk.ed.inf.graph.compound.ICompoundNodeFactory;

public abstract class BaseCompoundNodeFactory implements ICompoundNodeFactory<BaseCompoundNode, BaseCompoundEdge> {
	
	public BaseCompoundNodeFactory(){
	}
	
	public BaseCompoundNode createNode() {
		int cntr = this.getGraph().getNodeCounter().nextIndex();
		BaseCompoundNode retVal = newNode(this.getParentNode(), cntr);
//		this.getParentNode().getChildCompoundGraph().addNewNode(retVal);
		getGraph().registerNewNode(retVal);
		return retVal;
	}
	
	/**
	 * Creates the new node. The node must be correctly created and owned by the 
	 * ChildCompoundGraph of the parent node.
	 * @param parent the parent node the child graph of which this node will be added. 
	 * @param nodeIndex the new index to use when creating the node.
	 * @return the newly created node which must be valid and correctly formed.
	 */
	protected abstract BaseCompoundNode newNode(BaseCompoundNode parent, int nodeIndex);

	public abstract BaseCompoundGraph getGraph();
	
	public abstract BaseCompoundNode getParentNode();
}
