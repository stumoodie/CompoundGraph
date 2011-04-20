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

import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;

public abstract class ElementBuilder implements IGraphObjectBuilder {
	
	private ICompoundGraphElement element;
//	private IChildCompoundGraph childGraph;
	
	public final ICompoundGraphElement getElement(){
		return element;
	}
	
//	public final IChildCompoundGraph getChildGraph(){
//		return childGraph;
//	}
	
	protected abstract ICompoundGraphElement createElement();
	
//	public abstract IChildCompoundGraph createChildGraph();

//	public abstract void buildElement();
	
//	public abstract void buildChildGraph();
	
//	protected void setElement(ICompoundGraphElement element){
//		this.element = element;
//	}
	
//	protected void setChildGraph(IChildCompoundGraph childGraph){
//		this.childGraph = childGraph;
//	}
	
	@Override
	public void create(){
		element = createElement();
//		childGraph = createChildGraph();
//		buildElement();
//		buildChildGraph();
	}
	
//	@Override
//	public void buildTree(){
//	}
}