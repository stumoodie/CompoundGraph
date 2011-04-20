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

import java.util.Iterator;

import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.IGraphStructureChangeAction;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraphFactory;
import uk.ac.ed.inf.graph.compound.ISubgraphRemovalBuilder;

public class CompoundSubgraphRemovalBuilder implements ISubgraphRemovalBuilder {
	private final ICompoundGraph owningGraph;
	private ISubCompoundGraph subCompoundGraph;
	private ISubCompoundGraph removalSubGraph;
	
	protected CompoundSubgraphRemovalBuilder(ICompoundGraph owningGraph){
		this.owningGraph = owningGraph;
	}
	
	
	@Override
	public boolean canRemoveSubgraph() {
		return subCompoundGraph != null && subCompoundGraph.getSuperGraph().equals(this.owningGraph)
		&& !subCompoundGraph.containsRoot() && subCompoundGraph.isConsistentSnapShot()
		&& subCompoundGraph.isInducedSubgraph();
	}


	@Override
	public ISubCompoundGraph getRemovalSubgraph() {
		return this.subCompoundGraph;
	}

	private void removeElement(ICompoundGraphElement element, ISubCompoundGraphFactory subCompoundGraphFactory){
		if(!element.isRemoved()){
			subCompoundGraphFactory.addElement(element);
		}
	}

	@Override
	public void setRemovalSubgraph(ISubCompoundGraph subgraph) {
		this.subCompoundGraph = subgraph;
	}

	@Override
	public ICompoundGraph getGraph(){
		return this.owningGraph;
	}


	@Override
	public void removeSubgraph() {
		ISubCompoundGraphFactory removalGraphFactory = this.owningGraph.subgraphFactory();
		Iterator<ICompoundGraphElement> elementIterator = this.subCompoundGraph.elementIterator();
		while(elementIterator.hasNext()){
			ICompoundGraphElement element = elementIterator.next();
			if(element instanceof ICompoundNode){
				ICompoundNode node = (ICompoundNode)element;
				Iterator<ICompoundEdge> iter = node.edgeIterator();
				while(iter.hasNext()){
					ICompoundEdge edge = iter.next();
					removeElement(edge, removalGraphFactory);
				}
			}
			removeElement(element, removalGraphFactory);
		}
		this.removalSubGraph = removalGraphFactory.createSubgraph();
		// once have subgraph - can now remove the nodes
		Iterator<ICompoundGraphElement> elementIter = removalSubGraph.elementIterator();
		while(elementIter.hasNext()){
			ICompoundGraphElement element = elementIter.next();
			element.markRemoved(true);
		}
		this.owningGraph.notifyGraphStructureChange(new IGraphStructureChangeAction(){

			@Override
			public GraphStructureChangeType getChangeType() {
				return GraphStructureChangeType.SUBGRAPH_REMOVED;
			}

			@Override
			public ISubCompoundGraph originalSubgraph() {
				return removalSubGraph;
			}

			@Override
			public ISubCompoundGraph changedSubgraph() {
				return null;
			}
		});
	}
	
}
