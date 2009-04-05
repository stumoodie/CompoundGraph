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
package uk.ed.inf.graph.util;

import java.util.Iterator;
import java.util.SortedSet;

import uk.ed.inf.graph.basic.IBasicEdge;
import uk.ed.inf.graph.basic.IBasicNode;
import uk.ed.inf.graph.basic.IBasicPair;
import uk.ed.inf.graph.basic.IBasicSubgraph;
import uk.ed.inf.graph.basic.ISubgraphAlgorithms;

public final class SubgraphAlgorithms<
		N extends IBasicNode<N, ? extends IBasicEdge<N, ?>>,
		E extends IBasicEdge<N, E>
> implements ISubgraphAlgorithms<N, E> {
	
	private final IBasicSubgraph<N, E> basicSubgraph;
	
	public SubgraphAlgorithms(IBasicSubgraph<N, E> subgraph){
		this.basicSubgraph = subgraph;
	}
	
	/* (non-Javadoc)
	 * @see uk.ed.inf.graph.impl.ISubgraphAlgorithms#getSubgraph()
	 */
	public IBasicSubgraph<N, E> getSubgraph(){
		return this.basicSubgraph;
	}
	
	/* (non-Javadoc)
	 * @see uk.ed.inf.graph.impl.ISubgraphAlgorithms#isInducedSubgraph()
	 */
	@SuppressWarnings("unchecked")
	public boolean isInducedSubgraph(){
		boolean retVal = true;
		Iterator<N> graphNodeIter = this.basicSubgraph.getSuperGraph().nodeIterator();
		while(graphNodeIter.hasNext() && retVal == true){
			N thisNode = graphNodeIter.next();
			if(this.basicSubgraph.containsNode(thisNode)){
				Iterator<N> thatNodeIter = thisNode.connectedNodeIterator();
				while(thatNodeIter.hasNext() && retVal == true){
					N thatNode = thatNodeIter.next();
					if(this.basicSubgraph.containsNode(thatNode)){
						// subgraph contains both nodes. To be induced it must contain
						// any edges that exist between them.
						for(E edge : (SortedSet<E>) thisNode.getEdgesWith(thatNode)){
							if(this.basicSubgraph.containsEdge(edge) == false){
								retVal = false;
								break;
							}
						}
					}
				}
			}
		}
		Iterator<E> edgeIter = this.basicSubgraph.edgeIterator();
		while(edgeIter.hasNext()){
			E edge = edgeIter.next();
			IBasicPair<N, E> pair = edge.getConnectedNodes();
			if(this.basicSubgraph.containsConnection(pair) == false){
				retVal = false;
				break;
			}
		}
		return retVal;
	}

	
}
