package uk.ed.inf.graph.util.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import uk.ed.inf.graph.util.IAdjacencyDataStructure;

public class DirectedAdjList implements IAdjacencyDataStructure {
	private static int DEFAULT_CAPACITY = 30;
	private final ArrayList<List<AdjUnit>> adjList;
	private static final AdjUnitComparitor comparitor = new AdjUnitComparitor();
	
	public DirectedAdjList(){
		this(DEFAULT_CAPACITY);
	}
	
	public DirectedAdjList(int initialCapacity){
		this.adjList = new ArrayList<List<AdjUnit>>(initialCapacity);
		for(int i = 0; i < initialCapacity; i++){
			this.adjList.add(null);
		}
	}
	
	/* (non-Javadoc)
	 * @see uk.ed.inf.graph.compound.impl.IAdjList#addNode(int)
	 */
	public void addNode(int nodeIdx){
		ensureCapacity(nodeIdx+1);
		this.adjList.set(nodeIdx, new ArrayList<AdjUnit>());
	}
	
	/* (non-Javadoc)
	 * @see uk.ed.inf.graph.compound.impl.IAdjList#containsNode(int)
	 */
	public boolean containsNode(int nodeIdx){
		return nodeIdx < this.adjList.size() && this.adjList.get(nodeIdx) != null;
	}
	
	/* (non-Javadoc)
	 * @see uk.ed.inf.graph.compound.impl.IAdjList#addEdge(int, int, int)
	 */
	public void addEdge(int edgeIdx, int inNodeIdx, int outNodeIdx){
		if(!containsNode(inNodeIdx) || !containsNode(outNodeIdx)) throw new IllegalArgumentException("nodes must already exist");
		List<AdjUnit> node1Adj = this.adjList.get(inNodeIdx);
		node1Adj.add(new AdjUnit(outNodeIdx, edgeIdx));
		// keep the lists sorted to make searching more efficient later.
		Collections.sort(node1Adj, comparitor);
	}
	
	/* (non-Javadoc)
	 * @see uk.ed.inf.graph.compound.impl.IAdjList#isConnected(int, int)
	 */
	public boolean isConnected(int inNodeIdx, int outNodeIdx){
		boolean retVal = false;
		if(containsNode(inNodeIdx) && containsNode(outNodeIdx)){
			List<AdjUnit> nodeAdj = this.adjList.get(inNodeIdx);
			// note that this list should already be sorted whenever it is modified.
			retVal = (Collections.binarySearch(nodeAdj, outNodeIdx, comparitor) >= 0);
		}
		return retVal;
	}
	
	/* (non-Javadoc)
	 * @see uk.ed.inf.graph.compound.impl.IAdjList#getEdge(int, int)
	 */
	public int getEdge(int inNodeIdx, int outNodeIdx){
		if(!containsNode(inNodeIdx) || !containsNode(outNodeIdx)) throw new IllegalArgumentException("nodes must already exist");
		List<AdjUnit> nodeAdj = this.adjList.get(inNodeIdx);
		// note that this list should already be sorted whenever it is modified.
		int pos = Collections.binarySearch(this.adjList.get(inNodeIdx), outNodeIdx, comparitor);
		return nodeAdj.get(pos).getEdgeIdx();
	}
	
	private void ensureCapacity(int capacity){
		if(capacity > this.adjList.size()){
			for(int i = this.adjList.size(); i < capacity; i++){
				this.adjList.add(null);
			}
		}
	}
	
	private static class AdjUnit {
		private final int outNodeIdx;
		private final int edgeIdx;
		
		public AdjUnit(int outNodeIdx, int edgeIdx){
			this.outNodeIdx = outNodeIdx;
			this.edgeIdx = edgeIdx;
		}

		public int getOutNodeIdx() {
			return outNodeIdx;
		}

		public int getEdgeIdx() {
			return edgeIdx;
		}
	}
	
	public static class AdjUnitComparitor implements Comparator<Object> {

		public int compare(Object o1, Object o2) {
			Integer idx1 = getNodeIdx(o1);
			Integer idx2 = getNodeIdx(o2);
			
			return idx1.compareTo(idx2);
		}
		
		private static Integer getNodeIdx(Object o){
			Integer idx = null;
			if(o instanceof AdjUnit){
				AdjUnit adjUnit1 = (AdjUnit)o;
				idx = adjUnit1.getOutNodeIdx();
			}
			else{
				idx = (Integer)o;
			}
			return idx;
		}
	}
}
