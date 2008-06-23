package uk.ed.inf.graph.util;

import java.util.Iterator;

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
	public boolean isInducedSubgraph(){
		boolean retVal = true;
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
