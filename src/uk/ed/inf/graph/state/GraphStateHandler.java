package uk.ed.inf.graph.state;

import java.util.Iterator;

import uk.ed.inf.bitstring.BitStringBuffer;
import uk.ed.inf.bitstring.IBitString;
import uk.ed.inf.graph.basic.IBasicEdge;
import uk.ed.inf.graph.basic.IBasicGraph;
import uk.ed.inf.graph.basic.IBasicNode;
import uk.ed.inf.graph.util.impl.EdgeFromNodeIterator;

public class GraphStateHandler<
		N extends IRestorableGraphElement & IBasicNode<N, ? extends IBasicEdge<N, ?>> ,
		E extends IRestorableGraphElement & IBasicEdge<N, E> 
> implements IGraphStateHandler<N, E> {
	
	private final IBasicGraph<N, E> graph;
	private IBitString nodeStatus;
	private IBitString edgeStatus;
	
	public GraphStateHandler(IBasicGraph<N, E> graph){
		this.graph = graph;
	}

	/* (non-Javadoc)
	 * @see uk.ed.inf.graph.state.IGraphStateHandler#getGraph()
	 */
	public IBasicGraph<N, E> getGraph(){
		return this.graph;
	}
	
	/* (non-Javadoc)
	 * @see uk.ed.inf.graph.state.IGraphStateHandler#createGraphState()
	 */
	public IGraphState<N, E> createGraphState(){
		recordNodes();
		recordEdges();
		IGraphState<N, E> state = new GeneralGraphState<N, E>(this.graph, nodeStatus, edgeStatus);
		return state;
	}
	
	private void recordNodes(){
		BitStringBuffer nodeStatus = new BitStringBuffer();
		Iterator<? extends N> iter = this.graph.nodeIterator();
		while(iter.hasNext()){
			N node = iter.next();
			if(!node.isRemoved()){
				nodeStatus.set(node.getIndex(), true);
			}
			else{
				nodeStatus.set(node.getIndex(), true);
			}
		}
		this.nodeStatus = nodeStatus.toBitString();
	}

	private void recordEdges(){
		BitStringBuffer edgeStatus = new BitStringBuffer();
		Iterator<? extends E> edgeIter = this.graph.edgeIterator();
		while(edgeIter.hasNext()){
			E edge = edgeIter.next();
			if(edge.isRemoved()){
				edgeStatus.set(edge.getIndex(), false);
			}
			else{
				edgeStatus.set(edge.getIndex(), true);
			}
		}
		this.edgeStatus = edgeStatus.toBitString();
	}
	
	/* (non-Javadoc)
	 * @see uk.ed.inf.graph.state.IGraphStateHandler#restoreState(uk.ed.inf.graph.state.IGraphState)
	 */
	public void restoreState(IGraphState<N, E> previousState) throws IllegalArgumentException{
		// mark all nodes and edges as removed
		// then mark those in bit list as restored.
		if ( previousState.getGraph() != this.getGraph())
			throw new IllegalArgumentException ( "The state must belong to the same Graph.") ;
		this.nodeStatus = previousState.getNodeStates();
		restoreNodes();
		this.edgeStatus = previousState.getEdgeStates();
		restoreEdges();
	}
	
	
	private void restoreNodes(){
		Iterator<? extends N> iter = this.graph.nodeIterator();
		while(iter.hasNext()){
			N node = iter.next();
			int nodeIdx = node.getIndex();
			if(nodeIdx < this.nodeStatus.length()){
				boolean nodeState = this.nodeStatus.get(nodeIdx);
				node.markRemoved(!nodeState);
			}
			else{
				node.markRemoved(true);
			}
		}
	}
	
	private void restoreEdges(){
		Iterator<? extends E> edgeIter = new EdgeFromNodeIterator<N, E>(this.graph.nodeIterator());
		while(edgeIter.hasNext()){
			E edge = edgeIter.next();
			int edgeIdx = edge.getIndex();
			if(edgeIdx < edgeStatus.length()){
				boolean edgeState = this.edgeStatus.get(edgeIdx);
				edge.markRemoved(!edgeState);
			}
			else{
				edge.markRemoved(true);
			}
		}
	}
}
