/*
Copyright 2009, Court of the University of Edinburgh

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License. 
*/
package uk.ed.inf.graph.compound.archetypal;

import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.base.BaseChildCompoundGraph;
import uk.ed.inf.graph.compound.base.BaseGraphCopyBuilder;
import uk.ed.inf.graph.compound.base.BaseGraphMoveBuilder;
import uk.ed.inf.graph.util.impl.DirectedEdgeSet;
import uk.ed.inf.graph.util.impl.NodeSet;

public abstract class ArchetypalChildCompoundGraph extends BaseChildCompoundGraph {
	private final ArchetypalCompoundNode root;
	
	protected ArchetypalChildCompoundGraph(ArchetypalCompoundNode root, BaseGraphCopyBuilder copyBuilder , BaseGraphMoveBuilder moveBuilder){
		super(copyBuilder , moveBuilder);
		if(root == null) throw new IllegalArgumentException("root cannot be null");
		
		this.root = root;
		createEdgeSet(new DirectedEdgeSet<ICompoundNode, ICompoundEdge>());
		createNodeSet(new NodeSet<ICompoundNode, ICompoundEdge>());
	}
	
	@Override
	public ArchetypalCompoundNode getRootNode() {
		return this.root;
	}
	
	@Override
	protected void addNewNode(ICompoundNode node) {
	    super.addNewNode(node);
	}

	@Override
	protected void addNewEdge(ICompoundEdge edge) {
	    super.addNewEdge(edge);
	}
}
