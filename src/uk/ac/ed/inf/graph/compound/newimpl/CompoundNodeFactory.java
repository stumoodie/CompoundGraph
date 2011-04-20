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

import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.ICompoundNodeFactory;
import uk.ac.ed.inf.graph.compound.IElementAttribute;
import uk.ac.ed.inf.graph.compound.IElementAttributeFactory;
import uk.ac.ed.inf.graph.compound.IGraphStructureChangeAction;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraphFactory;

public class CompoundNodeFactory implements ICompoundNodeFactory {
	private final ICompoundGraphElement parent;
	private IElementAttributeFactory attributeFactory; 

	public CompoundNodeFactory(ICompoundGraphElement parent){
		this.parent = parent;
	}
	
	private void notifyNodeCreated(ICompoundNode retVal){
		final ISubCompoundGraphFactory subgraphFact = this.getGraph().subgraphFactory();
		subgraphFact.addElement(retVal);
		ICompoundGraph graph = this.getGraph();
		graph.notifyGraphStructureChange(new IGraphStructureChangeAction(){

			@Override
			public GraphStructureChangeType getChangeType() {
				return GraphStructureChangeType.ELEMENT_ADDED;
			}

			@Override
			public ISubCompoundGraph originalSubgraph() {
				return null;
			}

			@Override
			public ISubCompoundGraph changedSubgraph() {
				return subgraphFact.createSubgraph();
			}
		});

	}
	
	@Override
	public ICompoundNode createNode() {
		attributeFactory.setDestinationAttribute(this.parent.getAttribute());
		int nodeIndex = this.getGraph().getIndexCounter().nextIndex();
		IElementAttribute newAttribute = this.attributeFactory.createAttribute();
		CompoundNode retVal = new CompoundNode(parent, nodeIndex, newAttribute);
		parent.getChildCompoundGraph().addNode(retVal);
		notifyNodeCreated(retVal);
		return retVal;
	}

	@Override
	public ICompoundGraph getGraph() {
		return this.parent.getGraph();
	}

	@Override
	public ICompoundGraphElement getParentNode() {
		return this.parent;
	}

	@Override
	public boolean canCreateNode() {
		boolean retVal = false;
		if(this.attributeFactory != null){
			this.attributeFactory.setDestinationAttribute(this.parent.getAttribute());
			retVal = this.attributeFactory.canCreateAttribute(); 
		}
		return retVal;
	}

	@Override
	public void setAttributeFactory(IElementAttributeFactory attribute) {
		this.attributeFactory = attribute;
	}

	@Override
	public IElementAttributeFactory getAttributeFactory() {
		return this.attributeFactory;
	}

}
