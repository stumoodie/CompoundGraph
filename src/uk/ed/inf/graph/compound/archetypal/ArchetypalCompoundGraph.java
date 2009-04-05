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

import uk.ed.inf.graph.compound.base.BaseCompoundGraph;
import uk.ed.inf.graph.compound.base.BaseCompoundNode;
import uk.ed.inf.graph.compound.base.BaseGraphCopyBuilder;
import uk.ed.inf.graph.util.IndexCounter;
import uk.ed.inf.tree.GeneralTree;
import uk.ed.inf.tree.ITree;

public abstract class ArchetypalCompoundGraph extends BaseCompoundGraph {
	private final static int ROOT_NODE_IDX = 0;
	private final static int INIT_EDGE_IDX = -1;
	private final IndexCounter nodeCounter;
	private final IndexCounter edgeCounter;
	private final GeneralTree<BaseCompoundNode> nodeTree;
	
	protected ArchetypalCompoundGraph(BaseGraphCopyBuilder copyBuilder){
		super(copyBuilder);
		this.nodeCounter = new IndexCounter(ROOT_NODE_IDX);
		this.edgeCounter = new IndexCounter(INIT_EDGE_IDX);
		createNewRootNode(nodeCounter.getLastIndex());
		this.nodeTree = new GeneralTree<BaseCompoundNode>(getRootNode());
	}

	protected abstract void createNewRootNode(int indexValue);
	
	@Override
	protected abstract void createCopyOfRootNode(int newIndexValue, BaseCompoundNode otherRootNode);

	@Override
	protected final ITree<BaseCompoundNode> getNodeTree(){
		return this.nodeTree;
	}
	
	protected final int getRootNodeIndex(){
		return ROOT_NODE_IDX;
	}
	
	protected ArchetypalCompoundGraph(BaseGraphCopyBuilder copyBuilder, ArchetypalCompoundGraph otherGraph){
		super(copyBuilder);
		this.nodeCounter = new IndexCounter(ROOT_NODE_IDX);
		this.edgeCounter = new IndexCounter(INIT_EDGE_IDX);
		createCopyOfRootNode(nodeCounter.getLastIndex(), otherGraph.getRootNode());
		this.nodeTree = new GeneralTree<BaseCompoundNode>(getRootNode());
		this.performCopy(otherGraph);
	}
	
	@Override
	public abstract ArchetypalCompoundNode getRootNode();

	@Override
	protected final IndexCounter getNodeCounter(){
		return this.nodeCounter;
	}
	
	
	@Override
	protected final IndexCounter getEdgeCounter(){
		return this.edgeCounter;
	}

}
