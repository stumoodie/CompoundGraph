/*
  Licensed to the Court of the University of Edinburgh (UofE) under one
  or more contributor license agreements.  See the NOTICE file
  distributed with this work for additional information
  regarding copyright ownership.  The UofE licenses this file
  to you under the Apache License, Version 2.0 (the
  "License"); you may not use this file except in compliance
  with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing,
  software distributed under the License is distributed on an
  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
  KIND, either express or implied.  See the License for the
  specific language governing permissions and limitations
  under the License.
*/

/**
 * 
 */
package uk.ac.ed.inf.graph.compound;

import java.util.Iterator;


/**
 * IModel is an interface which defines a complete model used to represent drawing
 * of a graphical notation. The model contains the compound graph representation of
 * the drawing together with the Notation Subsystem defining the graphical notation
 * used by this drawing, factories to create the attributes used to store the appearance
 * of the diagram and methods to access these attributes.    
 * 
 * @author Stuart Moodie
 *
 */
public interface IElementAttributeModel {

	/**
	 * Get a new shape attribute factory.
	 * @return the shape attribute factory, which cannot be null.
	 */
	<E extends IElementAttribute> IElementAttributeFactory elementAttributeFactory(Class<E> discriminator);
	
	int numElementAttributes(Class<? extends IElementAttribute> discriminator);
	
	/**
	 * Provides a new iterator for all the canvas attributes in the model - that
	 * have are not associated with removed graph elements.
	 * @return the iterator, which cannot be null.
	 */
	<E extends IElementAttribute> Iterator<E> elementAttributeIterator();
	
	/**
	 * Adds a new attribute to be stored by this model. This is typically done by a factory when creating
	 * a new attribute and this method is typically no used by client code.
	 * @param attribute the attribute to add, which should not be null.
	 */
	void addElementAttribute(IElementAttribute attribute);


	/**
	 * Get the compound graph that defines the compound graph structure of the
	 * diagram defined by this model. 
	 * @return the compound graph, which cannot be null.
	 */
	ICompoundGraph getGraph();

	IElementAttribute getRootAttribute();
	
}
