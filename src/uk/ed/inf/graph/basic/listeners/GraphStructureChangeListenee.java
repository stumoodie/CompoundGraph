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
import java.util.concurrent.CopyOnWriteArrayList;

import uk.ed.inf.graph.basic.IBasicEdge;
import uk.ed.inf.graph.basic.IBasicNode;

/**
 * @author smoodie
 *
 */
public final class GraphStructureChangeListenee<
		N extends IBasicNode<N, ? extends IBasicEdge<N, ?>>,
		E extends IBasicEdge<N, E>
> implements IGraphChangeListenee<N, E>, ISuppressableChangeListenee {
	private final List<IGraphChangeListener<N, E>> listeners;
	private boolean enabled = true;
	
	public GraphStructureChangeListenee(){
		this.listeners = new CopyOnWriteArrayList<IGraphChangeListener<N, E>>();
	}

	public final void fireNodeChange(IGraphNodeChangeEvent<N, E> evt){
		if(this.enabled){
			for(IGraphChangeListener<N, E> listener : this.listeners){
				listener.nodeStructureChange(evt);
			}
		}
	}
	
	public final void fireEdgeChange(IGraphEdgeChangeEvent<N, E> evt){
		if(this.enabled){
			for(IGraphChangeListener<N, E> listener : this.listeners){
				listener.edgeStructureChange(evt);
			}
		}
	}
	
	public final void notifyNodeStructureChange(final GraphStructureChangeType type, final Set<N> changedNodes){
		IGraphNodeChangeEvent<N, E> event = new IGraphNodeChangeEvent<N, E>(){

			public GraphStructureChangeType getChangeType() {
				return type;
			}

			public Iterator<N> changedNodesIterator() {
				return changedNodes.iterator();
			}

			public Set<N> getChangedNodes() {
				return new HashSet<N>(changedNodes);
			}

			public int numChangedNodes() {
				return changedNodes.size();
			}
		};
		fireNodeChange(event);
	}

	public final void notifyEdgeStructureChange(final GraphStructureChangeType type, final Set<E> changedEdge){
		IGraphEdgeChangeEvent<N, E> event = new IGraphEdgeChangeEvent<N, E>(){

			public GraphStructureChangeType getChangeType() {
				return type;
			}

			public Iterator<E> changedEdgeIterator() {
				return changedEdge.iterator();
			}

			public Set<E> getChangedEdges() {
				return new HashSet<E>(changedEdge);
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
	public void addGraphChangeListener(IGraphChangeListener<N, E> listener) {
		this.listeners.add(listener);
	}

	/* (non-Javadoc)
	 * @see org.pathwayeditor.businessobjects.drawingprimitives.listeners.IModelChangeListenee#modelNodeChangeListenerIterator()
	 */
	public Iterator<IGraphChangeListener<N, E>> modelChangeListenerIterator() {
		return this.listeners.iterator();
	}

	/* (non-Javadoc)
	 * @see org.pathwayeditor.businessobjects.drawingprimitives.listeners.IModelChangeListenee#removeModelNodeChangeListener(org.pathwayeditor.businessobjects.drawingprimitives.listeners.IModelNodeChangeListener)
	 */
	public void removeGraphChangeListener(IGraphChangeListener<N, E> listener) {
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
