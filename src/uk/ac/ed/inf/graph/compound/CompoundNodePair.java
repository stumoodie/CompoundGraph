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
package uk.ac.ed.inf.graph.compound;


public class CompoundNodePair {
	private final ICompoundNode outNode;
	private final ICompoundNode inNode;
	
	public CompoundNodePair(ICompoundNode outNode, ICompoundNode inNode) {
		this.outNode = outNode;
		this.inNode = inNode;
	}
	
	public boolean containsNode(ICompoundNode node) {
		return this.outNode.equals(node) || this.inNode.equals(node);
	}

	public ICompoundNode getInNode() {
		return this.inNode;
	}

	public ICompoundNode getOtherNode(ICompoundNode node) {
		return this.inNode.equals(node) ? this.outNode : this.inNode;
	}

	public ICompoundNode getOutNode() {
		return this.outNode;
	}

	public boolean hasDirectedEnds(ICompoundNode outNode, ICompoundNode inNode) {
		return this.outNode.equals(outNode) && this.inNode.equals(inNode);
	}

	public boolean hasEnds(ICompoundNode endOne, ICompoundNode endTwo) {
		return this.hasDirectedEnds(endOne, endTwo) || this.hasDirectedEnds(endTwo, endOne);
	}

	public CompoundNodePair reversedNodes() {
		return new CompoundNodePair(inNode, outNode);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((inNode == null) ? 0 : inNode.hashCode());
		result = prime * result + ((outNode == null) ? 0 : outNode.hashCode());
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
		if (!(obj instanceof CompoundNodePair)) {
			return false;
		}
		CompoundNodePair other = (CompoundNodePair) obj;
		if (inNode == null) {
			if (other.inNode != null) {
				return false;
			}
		} else if (!inNode.equals(other.inNode)) {
			return false;
		}
		if (outNode == null) {
			if (other.outNode != null) {
				return false;
			}
		} else if (!outNode.equals(other.outNode)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
		builder.append("(inNode=");
		builder.append(inNode);
		builder.append(", outNode=");
		builder.append(outNode);
		builder.append(")");
		return builder.toString();
	}

	public boolean isSelfEdge() {
		return this.outNode.equals(inNode);
	}

	public ICompoundGraph getGraph() {
		return outNode.getGraph();
	}

}
