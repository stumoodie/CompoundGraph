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

import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElementFactory;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.IElementAttribute;

public class CompoundGraphElementFactory implements ICompoundGraphElementFactory {
	private int index;
	private ICompoundGraphElement parent;
	private IElementAttribute attribute;
	
	public CompoundGraphElementFactory(){
	}
	
	@Override
	public void setParent(ICompoundGraphElement parent) {
		this.parent = parent;
	}

	@Override
	public void setIndex(int newIndex) {
		this.index = newIndex;
	}

	@Override
	public void setAttribute(IElementAttribute newAttribute) {
		this.attribute = newAttribute;
	}

	@Override
	public ICompoundEdge createEdge(ICompoundNode outNode, ICompoundNode inNode) {
		return new CompoundEdge(this.parent, this.index, this.attribute, outNode, inNode);
	}

	@Override
	public ICompoundNode createNode() {
		return new CompoundNode(this.parent, this.index, this.attribute);
	}

}
