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
package uk.ed.inf.graph.compound.listeners;

import java.util.Iterator;

import uk.ed.inf.graph.basic.listeners.GraphStructureChangeType;

/**
 * @author smoodie
 *
 */
public interface ISubModelChangeListenee {

	/**
	 * Add the property change listener.
	 * @param listener the listener to be added, which cannot be null.
	 * @throws IllegalArgumentException if <code>listener</code> is null.
	 */
	void addSubModelNodeChangeListener(ISubModelChangeListener listener);
	
	/**
	 * Remove the property change listener.
	 * @param listener the listener to be removed, which cannot be null.
	 * @throws IllegalArgumentException if <code>listener</code> is null.
	 */
	void removeSubModelNodeChangeListener(ISubModelChangeListener listener);
	
	/**
	 * Gets an iterator of listeners.
	 * @return The list of listeners which can be modified without affecting this instance.
	 */
	Iterator<ISubModelChangeListener> subModelNodeChangeListenerIterator();
	
	void notifyNodeStructureChange(GraphStructureChangeType type, Object changedNode);

	void notifyEdgeStructureChange(GraphStructureChangeType type, Object changedEdge);
}