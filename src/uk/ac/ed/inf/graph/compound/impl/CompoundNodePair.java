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
package uk.ac.ed.inf.graph.compound.impl;

import uk.ac.ed.inf.graph.compound.base.BaseCompoundNode;
import uk.ac.ed.inf.graph.compound.base.BaseCompoundNodePair;

public class CompoundNodePair extends BaseCompoundNodePair {
	private final CompoundNode outNode;
	private final CompoundNode inNode;
	
	public CompoundNodePair(CompoundNode outNode, CompoundNode inNode) {
		this.outNode = outNode;
		this.inNode = inNode;
	}

	@Override
	public BaseCompoundNode getInNode() {
		return this.inNode;
	}

	@Override
	public BaseCompoundNode getOutNode() {
		return this.outNode;
	}

	public CompoundNodePair reversedNodes() {
		return new CompoundNodePair(this.inNode, this.outNode);
	}

}