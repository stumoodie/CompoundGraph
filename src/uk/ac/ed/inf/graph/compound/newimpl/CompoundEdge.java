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

import uk.ac.ed.inf.graph.compound.CompoundNodePair;
import uk.ac.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElementVisitor;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.IElementAttribute;

public class CompoundEdge extends CompoundElement implements ICompoundEdge {
	private final ICompoundGraphElement parentElement;
	private final IChildCompoundGraph childGraph;
	private final CompoundNodePair nodePair;
	private final int level;

	public CompoundEdge(ICompoundGraphElement parent, int index, IElementAttribute attribute,
			ICompoundNode outNode, ICompoundNode inNode){
		super(index, attribute);
		this.parentElement = parent;
		this.childGraph = new ChildCompoundGraph(this);
		this.nodePair = new CompoundNodePair(outNode, inNode);
		this.level = calcTreeLevel();
		outNode.addOutEdge(this);
		inNode.addInEdge(this);
		attribute.setCurrentElement(this);
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
	public CompoundNodePair getConnectedNodes() {
		return new CompoundNodePair(this.nodePair.getOutNode(), this.nodePair.getInNode());
	}

	@Override
	public boolean hasDirectedEnds(ICompoundNode outNode, ICompoundNode inNode) {
		boolean retVal = false;
		
		if(outNode != null && inNode != null && outNode.getGraph().equals(inNode.getGraph())){
			retVal = this.hasDirectedEnds(new CompoundNodePair(outNode, inNode));
		}
		return retVal;
	}

	@Override
	public boolean hasDirectedEnds(CompoundNodePair ends) {
		return this.nodePair.equals(ends);
	}

	@Override
	public boolean hasEnds(ICompoundNode thisNode, ICompoundNode thatNode) {
		return this.nodePair.hasEnds(thisNode, thatNode);
	}

	@Override
	public boolean hasEnds(CompoundNodePair ends) {
		return this.nodePair.equals(ends) || this.nodePair.reversedNodes().equals(ends);
	}

	@Override
	public boolean isSelfEdge() {
		return this.nodePair.isSelfEdge();
	}

	@Override
	public IChildCompoundGraph getChildCompoundGraph() {
		return this.childGraph;
	}

	@Override
	public ICompoundGraph getGraph() {
		return this.parentElement.getGraph();
	}

	@Override
	public boolean isEdge() {
		return true;
	}

	@Override
	public boolean isNode() {
		return false;
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
	public ICompoundGraphElement getRoot() {
		return this.parentElement.getRoot();
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
	public void visit(ICompoundGraphElementVisitor visitor){
		visitor.visitEdge(this);
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.getGraph().hashCode();
		result = prime * result + getIndex();
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof CompoundEdge)) {
			return false;
		}
		CompoundEdge other = (CompoundEdge) obj;
		if(!this.getGraph().equals(other.getGraph())){
			return false;
		}
		if (getIndex() != other.getIndex()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString(){
		StringBuilder buf = new StringBuilder(this.getClass().getSimpleName());
		buf.append("(");
		buf.append("index=");
		buf.append(this.getIndex());
		buf.append(",parent=");
		buf.append(this.getParent().getIndex());
		buf.append(",pair=");
		buf.append(this.getConnectedNodes());
		buf.append(",attrib=");
		buf.append(this.getAttribute());
		buf.append(")");
		return buf.toString();
	}
}
