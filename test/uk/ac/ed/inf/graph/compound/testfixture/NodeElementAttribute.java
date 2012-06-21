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
import uk.ac.ed.inf.graph.compound.IElementAttribute;
import uk.ac.ed.inf.graph.compound.IElementAttributeFactory;

public class NodeElementAttribute implements IElementAttribute {
	private final String name;
	private ICompoundGraphElement currentElement;
	private boolean canCreateFlag = true;
	
	public NodeElementAttribute(String name){
		this.name = name;
	}
	
	public NodeElementAttribute(NodeElementAttribute otherEl){
		this.name = otherEl.getName();
	}
	
	public String getName(){
		return this.name;
	}
	
	@Override
	public String toString(){
		StringBuilder buf = new StringBuilder(this.getClass().getSimpleName());
		buf.append("(");
		buf.append(this.name);
		buf.append(")");
		return buf.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NodeElementAttribute other = (NodeElementAttribute) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public ICompoundGraphElement getCurrentElement() {
		return this.currentElement;
	}

	@Override
	public void setCurrentElement(ICompoundGraphElement newOwner) {
		this.currentElement = newOwner;
	}

	@Override
	public IElementAttributeFactory elementAttributeCopyFactory() {
		NodeElementAttributeCopyFactory retVal = new NodeElementAttributeCopyFactory(this);
		retVal.setCanCreateFlag(canCreateFlag);
		return retVal;
	}

	@Override
	public IElementAttributeFactory elementAttributeMoveFactory() {
		NodeElementAttributeMoveFactory retVal = new NodeElementAttributeMoveFactory(this);
		retVal.setCanCreateFlag(canCreateFlag);
		return retVal;
	}

	public void setCanCreateFlag(boolean canCreate) {
		this.canCreateFlag = canCreate;
	}
}
