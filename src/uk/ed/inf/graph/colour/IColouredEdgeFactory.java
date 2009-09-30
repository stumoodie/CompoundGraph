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
import uk.ed.inf.graph.basic.IBasicEdgeFactory;
import uk.ed.inf.graph.basic.IBasicNode;


public interface IColouredEdgeFactory<
		N extends IBasicNode<N, ? extends IBasicEdge<N, ?>>,
		E extends IBasicEdge<N, E>
> extends IBasicEdgeFactory<N, E> {

	/**
	 * Get the graph to which this factory acts upon.
	 * @return The owning graph, cannot be null.
	 */
	IColouredGraph<N, E> getGraph();
	
	void setColourHandlerFactory(IEdgeColourHandlerFactory<N, E> handlerFactory);
	
	/**
	 * Create a new edge and add it to the graph.
	 * @return
	 */
	E createEdge();
	
}
