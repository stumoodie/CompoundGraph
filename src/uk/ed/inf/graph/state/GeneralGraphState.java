package uk.ed.inf.graph.state;

import uk.ed.inf.bitstring.IBitString;
import uk.ed.inf.graph.basic.IBasicEdge;
import uk.ed.inf.graph.basic.IBasicGraph;
import uk.ed.inf.graph.basic.IBasicNode;

public class GeneralGraphState<
		N extends IBasicNode<N, ? extends IBasicEdge<N, E>>,
		E extends IBasicEdge<N, E>
> implements IGraphState<N, E> {
	
	private final IBasicGraph<N, E> graph;
	private final IBitString liveNodeStore;
	private final IBitString liveEdgeStore;
	
	public GeneralGraphState(IBasicGraph<N, E> graphToSave, IBitString nodeStates, IBitString edgeStates){
		this.graph = graphToSave;
		this.liveNodeStore = nodeStates;
		this.liveEdgeStore = edgeStates;
	}
	
	public IBasicGraph<N, E> getGraph() {
		return this.graph;
	}

	public IBitString getNodeStates(){
		return this.liveNodeStore;
	}
	
	public IBitString getEdgeStates(){
		return this.liveEdgeStore;
	}
}
