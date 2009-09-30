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

import uk.ed.inf.graph.basic.listeners.GraphStructureChangeType;

/**
 * Reports an event that has changed the node structure of a SubModel.
 * @author smoodie
 *
 */
public interface ISubModelEdgeChangeEvent {
	/**
	 * Determines what the change to the submodel's content was. 
	 * @return the change type.
	 */
	GraphStructureChangeType getChangeType();
	
	/**
	 * Returns the item that the change occurred to. If the item
	 * was removed then this will return null 
	 * @return the edge that was changed, which will be null if it has been removed.
	 */
	Object getChangedItem();
	
	/**
	 * Gets the folder whose content has been changed by this event.
	 * @return the folder whose content was changed, which cannot be null
	 */
	Object getChangedModel();

}
