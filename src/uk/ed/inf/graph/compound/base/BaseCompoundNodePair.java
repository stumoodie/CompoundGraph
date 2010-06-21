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

import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.ICompoundNodePair;

public abstract class BaseCompoundNodePair implements ICompoundNodePair {

	@Override
	public boolean containsNode(ICompoundNode node) {
		return node.equals(getInNode()) || node.equals(getOutNode());
	}

	@Override
	public BaseCompoundNode getOtherNode(ICompoundNode node) {
		BaseCompoundNode retVal = getInNode();
		if (node.equals(getInNode())) {
			retVal = getOutNode();
		}
		return retVal;
	}

	@Override
	public abstract BaseCompoundNode getInNode();

	@Override
	public abstract BaseCompoundNode getOutNode();

	@Override
	public boolean hasEnds(ICompoundNode endOne, ICompoundNode endTwo) {
		boolean retVal = false;
		if (endOne != null && endTwo != null) {
			retVal = hasDirectedEnds(endOne, endTwo)
					|| hasDirectedEnds(endTwo, endOne);
		}
		return retVal;
	}

	@Override
	public boolean hasDirectedEnds(ICompoundNode outNode, ICompoundNode inNode) {
		boolean retVal = false;
		if (outNode != null && inNode != null) {
			retVal = this.getInNode().equals(inNode) && this.getOutNode().equals(outNode);
		}
		return retVal;
	}

}
