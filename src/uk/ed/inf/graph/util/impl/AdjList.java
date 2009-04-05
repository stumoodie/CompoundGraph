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
package uk.ed.inf.graph.util.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import uk.ed.inf.graph.util.IAdjacencyDataStructure;

public final class AdjList implements IAdjacencyDataStructure {
	private static int DEFAULT_CAPACITY = 30;
	private final ArrayList<List<AdjUnit>> adjList;
	private static final AdjUnitComparitor comparitor = new AdjUnitComparitor();
	
	public AdjList(){
		this(DEFAULT_CAPACITY);
	}
	
	public AdjList(int initialCapacity){
		this.adjList = new ArrayList<List<AdjUnit>>(initialCapacity);
		for(int i = 0; i < initialCapacity; i++){
			this.adjList.add(null);
		}
	}
	
	public void addNode(int nodeIdx){
		ensureCapacity(nodeIdx+1);
		this.adjList.set(nodeIdx, new ArrayList<AdjUnit>());
	}
	
	public boolean containsNode(int nodeIdx){
		return nodeIdx < this.adjList.size() && this.adjList.get(nodeIdx) != null;
	}
	
	public void addEdge(int edgeIdx, int thisNodeIdx, int thatNodeIdx){
		if(!containsNode(thisNodeIdx) || !containsNode(thatNodeIdx)) throw new IllegalArgumentException("nodes must already exist");
		List<AdjUnit> node1Adj = this.adjList.get(thisNodeIdx);
		node1Adj.add(new AdjUnit(thatNodeIdx, edgeIdx));
		List<AdjUnit> node2Adj = this.adjList.get(thatNodeIdx);
		node2Adj.add(new AdjUnit(thisNodeIdx, edgeIdx));
		// keep the lists sorted to make searching more efficient later.
		Collections.sort(node1Adj, comparitor);
		Collections.sort(node2Adj, comparitor);
	}
	
	public boolean isConnected(int thisNodeIdx, int thatNodeIdx){
		boolean retVal = false;
		if(containsNode(thisNodeIdx) && containsNode(thatNodeIdx)){
			List<AdjUnit> nodeAdj = this.adjList.get(thisNodeIdx);
			// note that this list should already be sorted whenever it is modified.
			retVal = (Collections.binarySearch(nodeAdj, thatNodeIdx, comparitor) >= 0);
		}
		return retVal;
	}
	
	public int getEdge(int thisNodeIdx, int thatNodeIdx){
		if(!containsNode(thisNodeIdx) || !containsNode(thatNodeIdx)) throw new IllegalArgumentException("nodes must already exist");
		List<AdjUnit> nodeAdj = this.adjList.get(thisNodeIdx);
		// note that this list should already be sorted whenever it is modified.
		int pos = Collections.binarySearch(this.adjList.get(thisNodeIdx), thatNodeIdx, comparitor);
		return nodeAdj.get(pos).getEdgeIdx();
	}
	
	void ensureCapacity(int capacity){
		if(capacity > this.adjList.size()){
			for(int i = this.adjList.size(); i < capacity; i++){
				this.adjList.add(null);
			}
		}
	}
	
	private static class AdjUnit {
		private final int otherNodeIdx;
		private final int edgeIdx;
		
		public AdjUnit(int nodeIdx, int edgeIdx){
			this.otherNodeIdx = nodeIdx;
			this.edgeIdx = edgeIdx;
		}

		public int getOtherNodeIdx() {
			return otherNodeIdx;
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
				idx = adjUnit1.getOtherNodeIdx();
			}
			else{
				idx = (Integer)o;
			}
			return idx;
		}
	}
}
