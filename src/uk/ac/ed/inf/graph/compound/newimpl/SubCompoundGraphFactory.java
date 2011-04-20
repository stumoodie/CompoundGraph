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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraphBuilder;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraphFactory;

public class SubCompoundGraphFactory implements ISubCompoundGraphFactory {
	private final ICompoundGraph graph;
	private final Set<ICompoundGraphElement> elements;
	private final ISubCompoundGraphBuilder builder;
	
	public SubCompoundGraphFactory(ICompoundGraph graph){
		this.graph = graph;
		this.builder = new SubCompoundGraphBuilder(this.graph);
		this.elements = new HashSet<ICompoundGraphElement>();
	}
	
	@Override
	public void addElement(ICompoundGraphElement element) {
		this.elements.add(element);
	}

	@Override
	public ISubCompoundGraph createInducedSubgraph() {
		builder.setElementList(this.elements);
		
		// because we are creating an induced graph we ignore any edges that are selected since if they are
		// not incident to the selected nodes they will result in a non-induced graph.
		builder.expandChildNodes();
		builder.removeNonIncidentEdges();
		builder.addIncidentEdges();
		builder.buildSubgraph();
		ISubCompoundGraph retVal = builder.getSubgraph();
		return retVal;
	}

	@Override
	public ISubCompoundGraph createPermissiveInducedSubgraph() {
		builder.setElementList(this.elements);
		builder.expandChildNodes();
		builder.addIncidentEdges();
		builder.buildSubgraph();
		ISubCompoundGraph retVal = builder.getSubgraph();
		return retVal;
	}

	@Override
	public ISubCompoundGraph createSubgraph() {
		builder.setElementList(this.elements);
		builder.expandChildNodes();
		builder.buildSubgraph();
		return builder.getSubgraph();
	}

	@Override
	public Iterator<ICompoundGraphElement> elementIterator() {
		return this.elements.iterator();
	}

	@Override
	public ICompoundGraph getGraph() {
		return this.graph;
	}


	@Override
	public int numElements() {
		return this.elements.size();
	}

}
