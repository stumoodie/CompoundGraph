package uk.ed.inf.graph.basic;


/**
 * An edge in a graph. By default this is undirected.
 * @author smoodie
 *
 * @param <N> The node class that must implement the INode class.
 * @param <E> The edge class that must implement this interface.
 */
public interface IBasicEdge<
	N extends IBasicNode<N, ? extends IBasicEdge<N, E>>,
	E extends IBasicEdge<N, E>
> extends Comparable<E> {

	/**
	 * Get the owning graph of this edge.
	 * @return the owning graph.
	 */
	IBasicGraph<N, E> getGraph();
	
	/**
	 * Get the index that uniquely identifies the edge within its owning graph.  
	 * @return The index, which is a While number (>-0).
	 */
	int getIndex();
	
	/**
	 * Get the ends of  
	 * @return
	 */
	IBasicPair<N, E> getConnectedNodes();
	
	/**
	 * Is this a self edge, i.e. both ends are the same node. 
	 * @return <code>true</code> if removed.
	 */
	boolean isSelfEdge();
	
	/**
	 * Tests if this edge has these ends. Ignores the directions of the ends.
	 * @param ends The ends to be tested.
	 * @return true if the edge contains the ends, false otherwise. 
	 */
	boolean hasEnds(IBasicPair<N, E> ends);
	
	/**
	 * Test if the edge has been removed from the graph.
	 * @return <code>true</code> if removed, false otherwise.
	 */
	boolean isRemoved();
}
