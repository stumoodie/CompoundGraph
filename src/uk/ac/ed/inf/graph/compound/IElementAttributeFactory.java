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
package uk.ac.ed.inf.graph.compound;

public interface IElementAttributeFactory {

	/**
	 * Tests if the factory is in a state to successfully create an attribute.
	 *   
	 * @return true if it is, false otherwise.
	 */
	boolean canCreateAttribute();
	
	/**
	 * Sets the destination attribute, which is the attribute of the <code>CompoundGraphElement</code> that will be 
	 * the parent of any newly created element attribute associated with the child <code>CompoundGraphElement</code>. 
	 * 
	 * @param attribute the destination attribute
	 */
	void setDestinationAttribute(IElementAttribute attribute);

	/**
	 * Get the destination attribute.
	 * @return
	 */
	IElementAttribute getDestinationAttribute();
	
	/**
	 * Create the new attribute.
	 * @return the new attribute instance.
	 * @throws IllegalStateException if <code>canCreateAttribute() == false</code>. 
	 */
	IElementAttribute createAttribute();
	
	void setOutAttribute(IElementAttribute attribute);
	
	IElementAttribute getOutAttribute();

	void setInAttribute(IElementAttribute attribute);
	
	IElementAttribute getInAttribute();
}
