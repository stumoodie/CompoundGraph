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
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import uk.ac.ed.inf.bitstring.BitStringBuffer;
import uk.ac.ed.inf.bitstring.IBitString;
import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.IGraphRestoreStateAction;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraphFactory;
import uk.ac.ed.inf.graph.state.GeneralGraphState;
import uk.ac.ed.inf.graph.state.IGraphState;
import uk.ac.ed.inf.graph.state.IGraphStateHandler;
import uk.ac.ed.inf.graph.state.IRestorableGraph;
import uk.ac.ed.inf.graph.state.IRestorableGraphElement;

public class CompoundGraphStateHandler implements IGraphStateHandler {
	private final Logger logger = Logger.getLogger(this.getClass());
	private final ICompoundGraph graph;
	private IBitString elementStatus;
	
	public CompoundGraphStateHandler(ICompoundGraph graph){
		this.graph = graph;
	}
	
	@Override
	public IGraphState createGraphState() {
		recordNodes();
		IGraphState state = new GeneralGraphState(this.graph, elementStatus);
		return state;
	}

	private void recordNodes(){
		BitStringBuffer elementStatus = new BitStringBuffer();
		Iterator<IRestorableGraphElement> iter = this.graph.restorableElementIterator();
		while(iter.hasNext()){
			IRestorableGraphElement node = iter.next();
			if(!node.isRemoved()){
				elementStatus.set(node.getIndex(), true);
			}
			else{
				elementStatus.set(node.getIndex(), false);
			}
		}
		this.elementStatus = elementStatus.toBitString();
	}

	@Override
	public IRestorableGraph getGraph() {
		return this.graph;
	}

	@Override
	public void restoreState(IGraphState previousState) {
		if ( previousState.getGraph() != this.getGraph()){
			throw new IllegalArgumentException ( "The state must belong to the same Graph.") ;
		}
		// then mark those in bit list as restored.
		this.elementStatus = previousState.getElementStates();
		ISubCompoundGraphFactory removalSubgraphFactory = this.graph.subgraphFactory();
		ISubCompoundGraphFactory restoreSubgraphFactory = this.graph.subgraphFactory();
		Set<ICompoundGraphElement> removalSet = new TreeSet<ICompoundGraphElement>();
		restoreElements(removalSet, removalSubgraphFactory, restoreSubgraphFactory);
		final ISubCompoundGraph removalSubCompoundGraph = removalSubgraphFactory.createSubgraph();
		final ISubCompoundGraph restoreSubgraph = restoreSubgraphFactory.createSubgraph();
		for(ICompoundGraphElement e : removalSet){
			e.markRemoved(true);
		}
		this.graph.notifyGraphRestoreChange(new IGraphRestoreStateAction() {
			@Override
			public ICompoundGraph getSource() {
				return graph;
			}
			
			@Override
			public ISubCompoundGraph getRestoredElements() {
				return restoreSubgraph;
			}
			
			@Override
			public ISubCompoundGraph getRemovedElements() {
				return removalSubCompoundGraph;
			}
		});
	}

	private void restoreElements(Set<ICompoundGraphElement> removalSet, ISubCompoundGraphFactory removalRestoreSubgraphFactory, ISubCompoundGraphFactory restoreSubgraphFactory){
		Iterator<IRestorableGraphElement> iter = this.graph.restorableElementIterator();
		while(iter.hasNext()){
			ICompoundGraphElement element = (ICompoundGraphElement)iter.next();
			int nodeIdx = element.getIndex();
			boolean removalStatus = true;
			if(nodeIdx < this.elementStatus.length()){
				boolean nodeState = this.elementStatus.get(nodeIdx);
				removalStatus = !nodeState;
			}
			boolean originalRemovalStatus = element.isRemoved();
			if(originalRemovalStatus != removalStatus){
				if(removalStatus){
					removalRestoreSubgraphFactory.addElement((ICompoundGraphElement)element);
					removalSet.add(element);
//					element.markRemoved(removalStatus);
					if(logger.isTraceEnabled()){
						logger.trace("Recording element is removed by restore=" + element);
					}
				}
				else{
					element.markRemoved(removalStatus);
					restoreSubgraphFactory.addElement((ICompoundGraphElement)element);
					if(logger.isTraceEnabled()){
						logger.trace("Recording element is added by restore=" + element);
					}
				}
			}
			else{
				element.markRemoved(removalStatus);
			}
		}
	}
}
