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
package uk.ac.ed.inf.graph.state;

import uk.ac.ed.inf.bitstring.IBitString;


public interface IGraphState {

	/**
	 * Get the graph that this momento belong to.
	 * @return The graph, cannot be null.
	 */
	IRestorableGraph getGraph();

	/**
	 * Get the states of the elements as a bit string: [true => exists, false => removed]. Not that the
	 * bit string may be shorter than the number of nodes in the graph as the graph may have expanded
	 * since it was created. 
	 * @return The bit string representing the state of the elements in graph.
	 */
	IBitString getElementStates();
}
