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
package uk.ed.inf.graph.compound.impl;

import java.util.Iterator;

import uk.ed.inf.graph.basic.listeners.INodeChangeListener;
import uk.ed.inf.graph.compound.archetypal.ArchetypalCompoundNode;
import uk.ed.inf.graph.compound.base.BaseCompoundEdge;
import uk.ed.inf.graph.compound.base.BaseCompoundNode;


public class CompoundNode extends ArchetypalCompoundNode {
	private ChildCompoundGraph childCompoundGraph;
	private boolean removed;
	
	// root node constructor
	CompoundNode(CompoundGraph superGraph, int index){
		super(superGraph, index);
	}
	
	CompoundNode(CompoundNode parent, int index){
		super(parent, index);
	}

	@Override
	protected void createChildCompoundGraph(ArchetypalCompoundNode rootNode){
		this.childCompoundGraph = new ChildCompoundGraph(this);
	}
	
	@Override
	public ChildCompoundGraph getChildCompoundGraph() {
		return this.childCompoundGraph;
	}

	@Override
	public String toString(){
		return this.getClass().getSimpleName() + "[index=" + this.getIndex() + "]";
	}

	@Override
	protected void removalAction(boolean removed) {
	}

	@Override
    protected void setRemoved(boolean removed) {
        this.removed = removed;
    }
    
    @Override
    public boolean isRemoved() {
        return this.removed;
    }

	public void addNodeChangeListener(
			INodeChangeListener<BaseCompoundNode, BaseCompoundEdge> listener) {
		// TODO Auto-generated method stub
		
	}

	public Iterator<INodeChangeListener<BaseCompoundNode, BaseCompoundEdge>> nodeChangeListenerIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeNodeChangeListener(
			INodeChangeListener<BaseCompoundNode, BaseCompoundEdge> listener) {
		// TODO Auto-generated method stub
		
	}
}
