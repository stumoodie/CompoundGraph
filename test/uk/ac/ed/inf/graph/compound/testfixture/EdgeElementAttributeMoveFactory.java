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
package uk.ac.ed.inf.graph.compound.testfixture;

import uk.ac.ed.inf.graph.compound.IElementAttribute;
import uk.ac.ed.inf.graph.compound.IElementAttributeFactory;

public class EdgeElementAttributeMoveFactory implements	IElementAttributeFactory {
	private final EdgeElementAttribute attribToMove;
	private IElementAttribute destinationAttribute;
	private boolean canCreateFlag = true;
	private IElementAttribute outAttribute;
	private IElementAttribute inAttribute;
	
	
	public EdgeElementAttributeMoveFactory(EdgeElementAttribute attribToMove){
		this.attribToMove = attribToMove;
	}
	
	@Override
	public boolean canCreateAttribute() {
		return this.attribToMove != null && destinationAttribute != null && canCreateFlag;
	}

	@Override
	public EdgeElementAttribute createAttribute() {
		return this.attribToMove;
	}

	public IElementAttribute getElementToMove() {
		return this.attribToMove;
	}

	@Override
	public void setDestinationAttribute(IElementAttribute attribute) {
		this.destinationAttribute = attribute;
	}

	@Override
	public IElementAttribute getDestinationAttribute() {
		return destinationAttribute;
	}


	public boolean canCreate() {
		return canCreateFlag;
	}

	public void setCanCreateFlag(boolean canCreateFlag) {
		this.canCreateFlag = canCreateFlag;
	}


	@Override
	public void setOutAttribute(IElementAttribute attribute) {
		this.outAttribute = attribute;
	}

	@Override
	public IElementAttribute getOutAttribute() {
		return this.outAttribute;
	}

	@Override
	public void setInAttribute(IElementAttribute attribute) {
		this.inAttribute = attribute;
	}

	@Override
	public IElementAttribute getInAttribute() {
		return this.inAttribute;
	}

}
