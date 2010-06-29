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
package uk.ed.inf.graph.state;

import java.util.Iterator;



public interface IRestorableGraph {
	
	/**
	 * Get the current state of the graph as a momento.
	 * @return the current graph state, will not be null. 
	 */
	IGraphState getCurrentState();
	
	/**
	 * Restores the graph state to the state stored by the <code>previousState</code>
	 * object.
	 * @param previousState The previous state to be restored.
	 */
	void restoreState(IGraphState previousState);

	Iterator<IRestorableGraphElement> restorableElementIterator();
}
