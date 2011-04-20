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
package uk.ac.ed.inf.graph.compound.testfixture;

import uk.ac.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundNodeFactory;

public class EdgeBuilder implements IEdgeBuilder {
	private ICompoundEdge edge;
	private IChildCompoundGraph childGraph;
	private ICompoundNodeFactory nodeFactory;
	private ICompoundEdgeFactory edgeFactory;
	private final IEdgeConstructor defaultConstructor;
	private IEdgeConstructor overridingConstructor;
	private String elementId;
	
	public EdgeBuilder(String elementId, IEdgeConstructor defaultConstructor){
		this.elementId = elementId;
		this.defaultConstructor = defaultConstructor;
		this.overridingConstructor = null;
	}
	
	public void setEdgeContructor(IEdgeConstructor newConstructor){
		this.overridingConstructor = newConstructor;
	}
	
	public IEdgeConstructor getEdgeContructor(){
		return this.overridingConstructor;
	}
	
	@Override
	public final void create() {
		if(overridingConstructor == null || (edge = overridingConstructor.createCompoundEdge()) == null){
			edge = defaultConstructor.createCompoundEdge();
		}
		;
		if(overridingConstructor == null || (childGraph = overridingConstructor.createCompoundChildGraph(edge)) == null){
			childGraph = defaultConstructor.createCompoundChildGraph(edge);
		}
		if(overridingConstructor == null || (nodeFactory = overridingConstructor.createNodeFactory(childGraph)) == null){
			nodeFactory = defaultConstructor.createNodeFactory(childGraph);
		}
		if(overridingConstructor == null || (edgeFactory = overridingConstructor.createEdgeFactory(childGraph)) == null){
			edgeFactory = defaultConstructor.createEdgeFactory(childGraph);
		}
	}


	@Override
	public ICompoundEdge getCompoundEdge() {
		return edge;
	}


	@Override
	public IChildCompoundGraph getChildGraph() {
		return childGraph;
	}


	@Override
	public ICompoundNodeFactory getNodeFactory() {
		return nodeFactory;
	}



	@Override
	public void buildGraphStructure() {
		if(overridingConstructor == null || !overridingConstructor.buildEdge(edge)){
			defaultConstructor.buildEdge(edge);
		}
		;
		if(overridingConstructor == null || !overridingConstructor.buildChildGraph(childGraph)){
			defaultConstructor.buildChildGraph(childGraph);
		}
		if(overridingConstructor == null || !overridingConstructor.buildNodeFactory(nodeFactory)){
			defaultConstructor.buildNodeFactory(nodeFactory);
		}
		if(overridingConstructor == null || !overridingConstructor.buildEdgeFactory(edgeFactory)){
			defaultConstructor.buildEdgeFactory(edgeFactory);
		}
	}



	@Override
	public String getElementId() {
		return this.elementId;
	}

	@Override
	public ICompoundEdgeFactory getEdgeFactory() {
		return this.edgeFactory;
	}

}
