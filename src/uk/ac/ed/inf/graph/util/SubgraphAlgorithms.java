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
package uk.ac.ed.inf.graph.util;

import java.util.Iterator;

import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.ISubGraph;
import uk.ac.ed.inf.graph.compound.ISubgraphAlgorithms;


public final class SubgraphAlgorithms implements ISubgraphAlgorithms {
	private final ISubGraph basicSubgraph;
	
	public SubgraphAlgorithms(ISubGraph subgraph){
		this.basicSubgraph = subgraph;
	}
	
	/* (non-Javadoc)
	 * @see uk.ed.inf.graph.impl.ISubgraphAlgorithms#getSubgraph()
	 */
	@Override
	public ISubGraph getSubgraph(){
		return this.basicSubgraph;
	}
	
	/* (non-Javadoc)
	 * @see uk.ed.inf.graph.impl.ISubgraphAlgorithms#isInducedSubgraph()
	 */
	@Override
	public boolean isInducedSubgraph(){
		boolean retVal = true;
		Iterator<ICompoundNode> graphNodeIter = this.basicSubgraph.getSuperGraph().nodeIterator();
		while(graphNodeIter.hasNext() && retVal == true){
			ICompoundNode thisNode = graphNodeIter.next();
			if(this.basicSubgraph.containsNode(thisNode)){
				Iterator<ICompoundNode> thatNodeIter = thisNode.connectedNodeIterator();
				while(thatNodeIter.hasNext() && retVal == true){
					ICompoundNode thatNode = thatNodeIter.next();
					if(this.basicSubgraph.containsNode(thatNode)){
						// subgraph contains both nodes. To be induced it must contain
						// all edges that exist between them.
						Iterator<ICompoundEdge> edgeIter = thisNode.getEdgesWith(thatNode); 
						while(edgeIter.hasNext()){
							ICompoundEdge edge = edgeIter.next();
							if(this.basicSubgraph.containsEdge(edge) == false){
								retVal = false;
								break;
							}
						}
					}
				}
			}
		}
		// ignore this - this checked for dangling ends which we now ignore.
//		Iterator<ICompoundEdge> edgeIter = this.basicSubgraph.edgeIterator();
//		while(edgeIter.hasNext()){
//			ICompoundEdge edge = edgeIter.next();
//			CompoundNodePair pair = edge.getConnectedNodes();
//			if(!this.basicSubgraph.containsNode(pair.getOutNode()) || !this.basicSubgraph.containsNode(pair.getInNode())){
//				retVal = false;
//				break;
//			}
//		}
		return retVal;
	}

	
}
