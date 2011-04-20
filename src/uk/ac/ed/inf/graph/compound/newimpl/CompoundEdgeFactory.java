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
package uk.ac.ed.inf.graph.compound.newimpl;

import uk.ac.ed.inf.graph.compound.CompoundNodePair;
import uk.ac.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;

public class CompoundEdgeFactory extends CommonEdgeFactory implements ICompoundEdgeFactory {
	private final ICompoundGraph graph;
	
	public CompoundEdgeFactory(ICompoundGraph graph){
		super();
		this.graph = graph;
	}
	
	@Override
	public ICompoundGraphElement getParent() {
		ICompoundGraphElement lcmNode = null;
		if(this.getCurrentNodePair() != null){
			CompoundNodePair pair = this.getCurrentNodePair();
			lcmNode = this.getGraph().getElementTree().getLowestCommonAncestor(pair.getOutNode(), pair.getInNode());
		}
		return lcmNode;
	}
	
	@Override
	public ICompoundGraph getGraph(){
		return this.graph;
	}

	@Override
	protected boolean isParentLowestCommonAncestor(CompoundNodePair nodePair) {
		return nodePair != null;
	}

}
