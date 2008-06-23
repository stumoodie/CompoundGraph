package uk.ed.inf.graph.util.impl;

import java.util.ArrayList;

import uk.ed.inf.graph.util.IAdjacencyDataStructure;

/**
 * Implementation of an adjacency matrix.
 * NOT TESTED, so not completed.
 * @author smoodie
 *
 */
public final class AdjMatrix implements IAdjacencyDataStructure {
	private final ArrayList<ArrayList<Integer>> adjMat;
	
	public AdjMatrix(){
		this.adjMat = new ArrayList<ArrayList<Integer>>();
	}

	
	public void addNode(int idx){
		int newCap = idx + 1;
		this.adjMat.ensureCapacity(newCap);
		if(idx >= this.adjMat.size()){
			for(int i = this.adjMat.size(); i <= idx; i++){
				this.adjMat.add(i, new ArrayList<Integer>(0));
			}
		}
	}
	
	public void addEdge(int one, int two, int edgeIdx){
		ArrayList<Integer> row = this.adjMat.get(one);
		row.ensureCapacity(two+1);
		row.add(two, edgeIdx);
	}
	
	public int getEdge(int one, int two){
		ArrayList<Integer> row = this.adjMat.get(one);
		row.ensureCapacity(two+1);
		return row.get(two);
	}
	
	public boolean isConnected(int one, int two){
		ArrayList<Integer> row = this.adjMat.get(one);
		row.ensureCapacity(two+1);
		return row.get(two) != null;
	}


	public boolean containsNode(int nodeIdx) {
		return nodeIdx < this.adjMat.size();
	}
}
