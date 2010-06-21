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
package uk.ed.inf.graph.compound.base;

import java.util.Iterator;

import uk.ed.inf.bitstring.BitStringBuffer;
import uk.ed.inf.bitstring.IBitString;
import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.state.GeneralGraphState;
import uk.ed.inf.graph.state.IGraphState;
import uk.ed.inf.graph.state.IGraphStateHandler;
import uk.ed.inf.graph.util.impl.EdgeFromNodeIterator;

public class BaseCompoundGraphStateHandler implements IGraphStateHandler {
	
	private final BaseCompoundGraph graph;
	private IBitString nodeStatus;
	private IBitString edgeStatus;
	
	public BaseCompoundGraphStateHandler(BaseCompoundGraph graph){
		this.graph = graph;
	}

	/* (non-Javadoc)
	 * @see uk.ed.inf.graph.state.IGraphStateHandler#getGraph()
	 */
	public BaseCompoundGraph getGraph(){
		return this.graph;
	}
	
	/* (non-Javadoc)
	 * @see uk.ed.inf.graph.state.IGraphStateHandler#createGraphState()
	 */
	public IGraphState createGraphState(){
		recordNodes();
		recordEdges();
		IGraphState state = new GeneralGraphState( this.graph, nodeStatus, edgeStatus);
		return state;
	}
	
	private void recordNodes(){
		BitStringBuffer nodeStatus = new BitStringBuffer();
		Iterator<BaseCompoundNode> iter = this.unfilteredNodeIterator();
		while(iter.hasNext()){
			BaseCompoundNode node = iter.next();
			if(!node.isRemoved()){
				nodeStatus.set(node.getIndex(), true);
			}
			else{
				nodeStatus.set(node.getIndex(), false);
			}
		}
		this.nodeStatus = nodeStatus.toBitString();
	}

	private Iterator<BaseCompoundNode> unfilteredNodeIterator(){
		return new UnfilteredTreeIterator(this.graph.getRootNode());
	}
	
	private Iterator<BaseCompoundEdge> unfilteredEdgeIterator(){
		return new EdgeFromNodeIterator<BaseCompoundNode, BaseCompoundEdge>(this.unfilteredNodeIterator());
	}
	
	private void recordEdges(){
		BitStringBuffer edgeStatus = new BitStringBuffer();
		Iterator<BaseCompoundEdge> edgeIter = this.unfilteredEdgeIterator();
		while(edgeIter.hasNext()){
			BaseCompoundEdge edge = edgeIter.next();
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
	public void restoreState(IGraphState previousState) throws IllegalArgumentException{
		if ( previousState.getGraph() != this.getGraph())
			throw new IllegalArgumentException ( "The state must belong to the same Graph.") ;
		// mark all nodes and edges as removed
		Iterator<BaseCompoundNode> nodeIterator = this.unfilteredNodeIterator() ;
		
		while ( nodeIterator.hasNext())
		{
			nodeIterator.next().markRemoved(true) ;
		}
		
		Iterator<BaseCompoundEdge> edgeIterator = this.unfilteredEdgeIterator() ;
		
		while ( edgeIterator.hasNext())
		{
			edgeIterator.next().markRemoved(true) ;
		}
		
		// then mark those in bit list as restored.

		this.nodeStatus = previousState.getNodeStates();
		restoreNodes();
		this.edgeStatus = previousState.getEdgeStates();
		restoreEdges();
	}
	
	
	private void restoreNodes(){
		Iterator<? extends BaseCompoundNode> iter = this.unfilteredNodeIterator();
		while(iter.hasNext()){
			BaseCompoundNode node = iter.next();
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
		Iterator<? extends BaseCompoundNode> iter = this.unfilteredNodeIterator();
		while(iter.hasNext()){
			Iterator<ICompoundEdge> edgeIter = iter.next().getChildCompoundGraph().unfilteredEdgeIterator();
			while(edgeIter.hasNext()){
				BaseCompoundEdge edge = (BaseCompoundEdge)edgeIter.next();
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
	


}
