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

import uk.ed.inf.graph.directed.IDirectedPair;

public abstract class BaseCompoundNodePair implements IDirectedPair<BaseCompoundNode, BaseCompoundEdge> {

	public boolean containsNode(BaseCompoundNode node) {
		return node.equals(getInNode()) || node.equals(getOutNode());
	}

	public BaseCompoundNode getOneNode() {
		return getInNode();
	}

	public BaseCompoundNode getOtherNode(BaseCompoundNode node) {
		BaseCompoundNode retVal = getInNode();
		if (node.equals(getInNode())) {
			retVal = getOutNode();
		}
		return retVal;
	}

	public BaseCompoundNode getTwoNode() {
		return getOutNode();
	}

	public abstract BaseCompoundNode getInNode();

	public abstract BaseCompoundNode getOutNode();

	public boolean hasEnds(BaseCompoundNode endOne, BaseCompoundNode endTwo) {
		boolean retVal = false;
		if (endOne != null && endTwo != null) {
			retVal = hasDirectedEnds(endOne, endTwo)
					|| hasDirectedEnds(endTwo, endOne);
		}
		return retVal;
	}

	public boolean hasDirectedEnds(BaseCompoundNode outNode, BaseCompoundNode inNode) {
		boolean retVal = false;
		if (outNode != null && inNode != null) {
			retVal = this.getInNode().equals(inNode) && this.getOutNode().equals(outNode);
		}
		return retVal;
	}

}
