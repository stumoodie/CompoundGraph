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

import uk.ed.inf.graph.basic.IBasicEdge;
import uk.ed.inf.graph.basic.IBasicGraph;
import uk.ed.inf.graph.basic.IBasicNode;

public interface IGraphStateHandler<N extends IRestorableGraphElement & IBasicNode<N, ? extends IBasicEdge<N, ?>>, E extends IRestorableGraphElement & IBasicEdge<N, E>> {

	IBasicGraph<N, E> getGraph();

	IGraphState<N, E> createGraphState();

	void restoreState(IGraphState<N, E> previousState);

}