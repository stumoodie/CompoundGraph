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
import uk.ac.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ac.ed.inf.tree.GeneralTree;
import uk.ac.ed.inf.tree.ITree;

public class ChildCompoundGraph extends CommonChildCompoundGraph {
	private ITree<ICompoundGraphElement> elementTree;

	public ChildCompoundGraph(ICompoundGraphElement rootElement) {
		super();
		this.elementTree = new GeneralTree<ICompoundGraphElement>(rootElement);
	}

	@Override
	protected void notifyCopyOperationComplete(ISubCompoundGraph originalSubgraph, ISubCompoundGraph copiedSubgraph) {

	}

	@Override
	protected void notifyMoveOperationComplete(ISubCompoundGraph originalSubgraph, ISubCompoundGraph movedSubgraph) {

	}

	@Override
	public ICompoundGraphElement getRoot() {
		return this.getElementTree().getRootNode();
	}

	@Override
	public ITree<ICompoundGraphElement> getElementTree() {
		return this.elementTree;
	}

}
