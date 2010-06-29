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

import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.base.BaseCompoundGraph;
import uk.ed.inf.graph.compound.base.BaseCompoundNode;
import uk.ed.inf.graph.compound.base.BaseGraphCopyBuilder;
import uk.ed.inf.graph.util.IndexCounter;
import uk.ed.inf.tree.GeneralTree;
import uk.ed.inf.tree.ITree;

public abstract class ArchetypalCompoundGraph extends BaseCompoundGraph {
	private final static int ROOT_NODE_IDX = 0;
	private final IndexCounter nodeCounter;
	private final GeneralTree<ICompoundGraphElement> nodeTree;
	
	protected ArchetypalCompoundGraph(BaseGraphCopyBuilder copyBuilder){
		super(copyBuilder);
		this.nodeCounter = new IndexCounter(ROOT_NODE_IDX);
		createNewRootNode(nodeCounter.getLastIndex());
		this.nodeTree = new GeneralTree<ICompoundGraphElement>(getRoot());
	}

	protected abstract void createNewRootNode(int indexValue);
	
	@Override
	protected abstract void createCopyOfRootNode(int newIndexValue, BaseCompoundNode otherRootNode);

	@Override
	protected final ITree<ICompoundGraphElement> getNodeTree(){
		return this.nodeTree;
	}
	
	protected final int getRootNodeIndex(){
		return ROOT_NODE_IDX;
	}
	
	protected ArchetypalCompoundGraph(BaseGraphCopyBuilder copyBuilder, ArchetypalCompoundGraph otherGraph){
		super(copyBuilder);
		this.nodeCounter = new IndexCounter(ROOT_NODE_IDX);
		createCopyOfRootNode(nodeCounter.getLastIndex(), otherGraph.getRoot());
		this.nodeTree = new GeneralTree<ICompoundGraphElement>(getRoot());
		this.performCopy(otherGraph);
	}
	
//	@Override
//	public abstract ArchetypalCompoundNode getRoot();

	@Override
	protected final IndexCounter getNodeCounter(){
		return this.nodeCounter;
	}
	
	
	@Override
	protected final IndexCounter getEdgeCounter(){
		return this.nodeCounter;
	}

}
