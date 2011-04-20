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

import uk.ac.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.IElementAttribute;

public class CompoundNode extends CommonCompoundNode {
	private final ICompoundGraphElement parentElement;
	private final int level;
	private final IChildCompoundGraph childGraph;
	
	public CompoundNode(ICompoundGraphElement parentElement, int index, IElementAttribute attribute){
		super(index, attribute);
		this.parentElement = parentElement;
		this.level = calcTreeLevel();
		this.childGraph = new ChildCompoundGraph(this);
		attribute.setCurrentElement(this);
	}
	
	@Override
	public IChildCompoundGraph getChildCompoundGraph() {
		return this.childGraph;
	}

	private int calcTreeLevel(){
		ICompoundGraphElement p = this;
		int level = ROOT_LEVEL;
		while(p != p.getParent()){
			p = p.getParent();
			level++;
		}
		return level;
	}

	@Override
	public int getLevel() {
		return this.level;
	}

	@Override
	public ICompoundGraphElement getParent() {
		return this.parentElement;
	}

	@Override
	public boolean isParent(ICompoundGraphElement parentNode) {
		boolean retVal = false;
		if(parentNode != null){
			retVal = this.getParent().equals(parentNode);
		}
		return retVal;
	}

	@Override
	public ICompoundGraph getGraph() {
		return this.parentElement.getGraph();
	}

	@Override
	public ICompoundGraphElement getRoot() {
		return this.parentElement.getRoot();
	}
	
	@Override
	public String toString(){
		StringBuilder buf = new StringBuilder(this.getClass().getSimpleName());
		buf.append("(");
		buf.append("index=");
		buf.append(this.getIndex());
		buf.append(",parent=");
		buf.append(this.getParent().getIndex());
		buf.append(",attrib=");
		buf.append(this.getAttribute());
		buf.append(")");
		return buf.toString();
	}
}
