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

import uk.ed.inf.graph.compound.ICompoundChildEdgeFactory;

public abstract class BaseChildCompoundEdgeFactory implements ICompoundChildEdgeFactory<BaseCompoundNode, BaseCompoundEdge> {
	
	public abstract BaseChildCompoundGraph getOwningChildGraph();

	/**
	 * Methods implementing the <code>isValidNodePair()</code> should call this method
	 * in addition to carrying out it's own checks.
	 * @param outNode
	 * @param inNode
	 * @return
	 */
	protected final boolean isValidBaseNodePair(BaseCompoundNode outNode, BaseCompoundNode inNode) {
		boolean retVal = false;
		if(outNode != null  && inNode != null){
			BaseCompoundNode parentNode = this.getOwningChildGraph().getRootNode();
			retVal = this.getOwningChildGraph().getSuperGraph().getLcaNode(outNode, inNode).equals(parentNode);
		}
		return retVal;
	}

	public abstract void setPair(BaseCompoundNode outNode, BaseCompoundNode inNode);

	protected abstract BaseCompoundNode getOutNode();
	
	protected abstract BaseCompoundNode getInNode();
	
	public abstract BaseCompoundNodePair getCurrentNodePair();
	
	public BaseCompoundEdge createEdge() {
		int idx = this.getGraph().getEdgeCounter().nextIndex();
		BaseCompoundEdge newCompoundEdge = newEdge(this.getOwningChildGraph(), idx, this.getOutNode(), this.getInNode());
//		this.getOwningChildGraph().addNewEdge(newCompoundEdge) ;
		return newCompoundEdge ;
	}

	protected abstract BaseCompoundEdge newEdge(BaseChildCompoundGraph owningChildGraph,
					int edgeIndex, BaseCompoundNode outNode, BaseCompoundNode inNode);
	
	public abstract BaseCompoundGraph getGraph();
	
	public abstract boolean canCreateEdge();

}
