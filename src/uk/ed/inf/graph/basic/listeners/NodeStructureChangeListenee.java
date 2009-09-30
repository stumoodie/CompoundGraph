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

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import uk.ed.inf.graph.basic.IBasicEdge;
import uk.ed.inf.graph.basic.IBasicNode;

/**
 * @author smoodie
 *
 */
public final class NodeStructureChangeListenee<
N extends IBasicNode<N, ? extends IBasicEdge<N, ?>>,
		E extends IBasicEdge<N, E>
	> implements INodeChangeListenee<N, E>, ISuppressableChangeListenee {
	private final List<INodeChangeListener<N, E>> listeners;
	private final N node;
	private boolean enabled = true;
	
	public NodeStructureChangeListenee(N model){
		this.listeners = new CopyOnWriteArrayList<INodeChangeListener<N, E>>();
		this.node = model;
	}

	/**
	 * 
	 */
	public final List<INodeChangeListener<N, E>> getListeners() {
		return this.listeners;
	}

	public final void fireNodeChange(INodeChangeEvent<N, E> evt){
		if(enabled){
			for(INodeChangeListener<N, E> listener : this.getListeners()){
				listener.nodeStructureChange(evt);
			}
		}
	}
	
	public final void notifyNodeStructureChange(final GraphStructureChangeType type, final E changedNode){
		INodeChangeEvent<N, E> event = new INodeChangeEvent<N, E>(){

			public GraphStructureChangeType getChangeType() {
				return type;
			}

			public E getChangedEdge() {
				return changedNode;
			}

			public N getChangedNode() {
				return node;
			}
			
		};
		if(changedNode.getConnectedNodes().containsNode(node)){
			fireNodeChange(event);
		}
		else{
			throw new IllegalArgumentException("the changedEdge does not contain this node");
		}
	}

	/* (non-Javadoc)
	 * @see org.pathwayeditor.businessobjects.drawingprimitives.listeners.INodeChangeListenee#addNodeNodeChangeListener(org.pathwayeditor.businessobjects.drawingprimitives.listeners.INodeNodeChangeListener)
	 */
	public void addNodeChangeListener(INodeChangeListener<N, E> listener) {
		this.listeners.add(listener);
	}

	/* (non-Javadoc)
	 * @see org.pathwayeditor.businessobjects.drawingprimitives.listeners.INodeChangeListenee#modelNodeChangeListenerIterator()
	 */
	public Iterator<INodeChangeListener<N, E>> nodeChangeListenerIterator() {
		return this.listeners.iterator();
	}

	/* (non-Javadoc)
	 * @see org.pathwayeditor.businessobjects.drawingprimitives.listeners.INodeChangeListenee#removeNodeNodeChangeListener(org.pathwayeditor.businessobjects.drawingprimitives.listeners.INodeNodeChangeListener)
	 */
	public void removeNodeChangeListener(INodeChangeListener<N, E> listener) {
		this.listeners.remove(listener);
	}

	/* (non-Javadoc)
	 * @see org.pathwayeditor.businessobjects.drawingprimitives.listeners.ISuppressableChangeListenee#areListenersEnabled()
	 */
	public boolean areListenersEnabled() {
		return this.enabled ;
	}

	/* (non-Javadoc)
	 * @see org.pathwayeditor.businessobjects.drawingprimitives.listeners.ISuppressableChangeListenee#setListenersEnabled(boolean)
	 */
	public void setListenersEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
