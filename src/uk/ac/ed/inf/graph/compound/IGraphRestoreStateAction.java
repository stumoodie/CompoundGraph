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


public interface IGraphRestoreStateAction {
	
	/**
	 * The compound graph upon which is the origin of this action.
	 * @return the source compound graph.
	 */
	ICompoundGraph getSource();
	
	/**
	 * This is the subgraph of elements that are removed from the graph by the restore operation.
	 * @return the subgraph of removed elements, which can be empty. 
	 */
	ISubCompoundGraph getRemovedElements();
	
	/**
	 * This is the subgraph of elements that are restored, i.e. added to the graph by the restore operation.
	 * @return the subgraph of restored elements, which can be empty. 
	 */
	ISubCompoundGraph getRestoredElements();
}
