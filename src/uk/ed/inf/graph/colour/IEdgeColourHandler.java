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
package uk.ed.inf.graph.colour;

import uk.ed.inf.graph.basic.IBasicEdge;
import uk.ed.inf.graph.basic.IBasicNode;

/**
 * Implementers of this interface will provide a handler class for an object that provides the colour
 * to a edge. The colour being one or more properties that are associated with the graph edge. 
 * The handler facilitates the storage of the colour and can be copied and perform the copying of the
 * colour object itself. The handler is required because a colour can be of any type and each edge in a graph
 * can potentially have a different colour type.  Implementers of this interface provides a mechanism handle
 * diverse colour object types. Another important issue handles here is the copy of a edge's colour when the edge
 * is copied. Depending of the usage of the graph or edge, the colour value may or may not be copied. The decision
 * to copy can be application dependent and so the supplier of the implementation of this interface can use the
 * <code>copyObject(newNode)</code> method to control this behaviour. The <code>newNode</code> parameter provides
 * the method with information about the edge to be copied to and as a consequence the graph it belongs to and
 * it;s topology. Any of these may be important in deciding if or how to copy the colour value.    
 * @author smoodie
 *
 * @param <N> the graph node, which must implement the interface <code>IBasicNode</code>.
 * @param <E> the graph edge, which must implement the interface <code>IBasicEdge</code>.
 */
public interface IEdgeColourHandler<
	N extends IBasicNode<N, ? extends IBasicEdge<N, ?>>,
	E extends IBasicEdge<N, E>
> {
	
	/**
	 * Set the colour value.
	 * @param colour The colour value to set.
	 */
	void setColour(Object colour);
	
	/**
	 * Get the colour value.
	 * @return the colour instance.
	 */
	Object getColour();
	
	/**
	 * Create a copy of this colour handler. This should not copy the object value, which should be left as 
	 * null or the default value.
	 * @return a new instance of the copy handler.
	 */
	IEdgeColourHandler<N, E> createCopy();
	
	/**
	 * Copy the colour object. The new edge to copied to is provided to enable the copy method
	 * to decide how to copy the object based on the newNode. For example if the newNode belongs to
	 * another Graph then the a deep copy of the object may be performed.
	 * @param newNode The new edge the colour will be copied to.
	 * @return The copied object.
	 */
	Object copyColour(E newEdge);
	
	/**
	 * Gets the edge that ones this colour handler.
	 * @return the owning edge. Can be null.
	 */
	E getEdge();
	
	/**
	 * Sets the edge that will own this handler and the associated colour value.
	 * @param edge The edge that will own this instance. Can be null.
	 */
	void setEdge(E edge);
}
