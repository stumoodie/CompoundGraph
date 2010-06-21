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
/**
 * 
 */
package uk.ed.inf.graph.basic.listeners;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArrayList;

import uk.ed.inf.graph.basic.IBasicEdge;
import uk.ed.inf.graph.basic.IBasicNode;

/**
 * @author smoodie
 *
 */
public final class GraphStructureChangeListenee implements IGraphChangeListenee, ISuppressableChangeListenee {
	private final List<IGraphChangeListener> listeners;
	private boolean enabled = true;
	
	public GraphStructureChangeListenee(){
		this.listeners = new CopyOnWriteArrayList<IGraphChangeListener>();
	}

	public final void fireNodeChange(IGraphNodeChangeEvent evt){
		if(this.enabled){
			for(IGraphChangeListener listener : this.listeners){
				listener.nodeStructureChange(evt);
			}
		}
	}
	
	public final void fireEdgeChange(IGraphEdgeChangeEvent evt){
		if(this.enabled){
			for(IGraphChangeListener listener : this.listeners){
				listener.edgeStructureChange(evt);
			}
		}
	}
	
	public final void notifyNodeStructureChange(final GraphStructureChangeType type, IBasicNode changedNode){
		Set<IBasicNode> items = new TreeSet<IBasicNode>();
		items.add(changedNode);
		notifyNodeStructureChange(type, items);
	}
		
	public final void notifyNodeStructureChange(final GraphStructureChangeType type, final Set<IBasicNode> changedNodes){
		IGraphNodeChangeEvent event = new IGraphNodeChangeEvent(){

			public GraphStructureChangeType getChangeType() {
				return type;
			}

			public Iterator<IBasicNode> changedNodesIterator() {
				return changedNodes.iterator();
			}

			public Set<IBasicNode> getChangedNodes() {
				return new HashSet<IBasicNode>(changedNodes);
			}

			public int numChangedNodes() {
				return changedNodes.size();
			}
		};
		fireNodeChange(event);
	}

	public final void notifyEdgeStructureChange(final GraphStructureChangeType type, IBasicEdge changedEdge){
		Set<IBasicEdge> items = new TreeSet<IBasicEdge>();
		items.add(changedEdge);
		notifyEdgeStructureChange(type, items);
	}
		
	public final void notifyEdgeStructureChange(final GraphStructureChangeType type, final Set<IBasicEdge> changedEdge){
		IGraphEdgeChangeEvent event = new IGraphEdgeChangeEvent(){

			public GraphStructureChangeType getChangeType() {
				return type;
			}

			public Iterator<IBasicEdge> changedEdgeIterator() {
				return changedEdge.iterator();
			}

			public Set<IBasicEdge> getChangedEdges() {
				return new HashSet<IBasicEdge>(changedEdge);
			}

			public int numChangedEdges() {
				return changedEdge.size();
			}
		};
		fireEdgeChange(event);
	}

	/* (non-Javadoc)
	 * @see org.pathwayeditor.businessobjects.drawingprimitives.listeners.IModelChangeListenee#addModelNodeChangeListener(org.pathwayeditor.businessobjects.drawingprimitives.listeners.IModelNodeChangeListener)
	 */
	public void addGraphChangeListener(IGraphChangeListener listener) {
		this.listeners.add(listener);
	}

	/* (non-Javadoc)
	 * @see org.pathwayeditor.businessobjects.drawingprimitives.listeners.IModelChangeListenee#modelNodeChangeListenerIterator()
	 */
	public Iterator<IGraphChangeListener> modelChangeListenerIterator() {
		return this.listeners.iterator();
	}

	/* (non-Javadoc)
	 * @see org.pathwayeditor.businessobjects.drawingprimitives.listeners.IModelChangeListenee#removeModelNodeChangeListener(org.pathwayeditor.businessobjects.drawingprimitives.listeners.IModelNodeChangeListener)
	 */
	public void removeGraphChangeListener(IGraphChangeListener listener) {
		this.listeners.remove(listener);
	}

	/* (non-Javadoc)
	 * @see org.pathwayeditor.businessobjects.drawingprimitives.listeners.ISuppressableChangeListenee#areListenersEnabled()
	 */
	public boolean areListenersEnabled() {
		return this.enabled;
	}

	/* (non-Javadoc)
	 * @see org.pathwayeditor.businessobjects.drawingprimitives.listeners.ISuppressableChangeListenee#setListenersEnabled(boolean)
	 */
	public void setListenersEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
