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

import uk.ed.inf.bitstring.IBitString;

public class GeneralGraphState implements IGraphState {
	
	private final IRestorableGraph graph;
	private final IBitString liveNodeStore;
	private final IBitString liveEdgeStore;
	
	public GeneralGraphState(IRestorableGraph graphToSave, IBitString nodeStates, IBitString edgeStates){
		this.graph = graphToSave;
		this.liveNodeStore = nodeStates;
		this.liveEdgeStore = edgeStates;
	}
	
	public IRestorableGraph getGraph() {
		return this.graph;
	}

	public IBitString getNodeStates(){
		return this.liveNodeStore;
	}
	
	public IBitString getEdgeStates(){
		return this.liveEdgeStore;
	}
}
