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
package uk.ac.ed.inf.graph.compound.impl;

import uk.ac.ed.inf.graph.compound.base.BaseCompoundEdge;
import uk.ac.ed.inf.graph.compound.base.BaseCompoundNode;
import uk.ac.ed.inf.graph.compound.base.BaseSubCompoundGraph;
import uk.ac.ed.inf.graph.util.impl.DirectedEdgeSet;
import uk.ac.ed.inf.graph.util.impl.NodeSet;


public class SubCompoundGraph extends BaseSubCompoundGraph {
	private final CompoundGraph superGraph;
	
	SubCompoundGraph(CompoundGraph superGraph){
		super();
		this.superGraph = superGraph;
		createEdgeSet(new DirectedEdgeSet<BaseCompoundNode, BaseCompoundEdge>());
		createNodeSet(new NodeSet<BaseCompoundNode, BaseCompoundEdge>());
		createTopNodeSet(new NodeSet<BaseCompoundNode, BaseCompoundEdge>());
	}

	@Override
	public CompoundGraph getSuperGraph() {
		return this.superGraph;
	}

}