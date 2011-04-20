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

import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.IElementAttribute;
import uk.ac.ed.inf.graph.compound.IRootCompoundNode;

public class RootCompoundNode extends CommonCompoundNode implements IRootCompoundNode {
	private final RootChildCompoundGraph childGraph;
	private final CompoundGraph graph;
	
	public RootCompoundNode(CompoundGraph graph, int index, IElementAttribute attribute){
		super(index, attribute);
		this.childGraph = new RootChildCompoundGraph(this);
		this.graph = graph;
	}
	
	@Override
	public RootChildCompoundGraph getChildCompoundGraph() {
		return this.childGraph;
	}

	@Override
	public int getLevel() {
		return ROOT_LEVEL;
	}

	@Override
	public ICompoundGraphElement getParent() {
		return this;
	}

	@Override
	public boolean isParent(ICompoundGraphElement parentNode) {
		return true;
	}

	@Override
	public CompoundGraph getGraph() {
		return this.graph;
	}

	@Override
	public ICompoundGraphElement getRoot() {
		return this;
	}

}
