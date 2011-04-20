/*
  Licensed to the Court of the University of Edinburgh (UofE) under one
  or more contributor license agreements.  See the NOTICE file
  distributed with this work for additional information
  regarding copyright ownership.  The UofE licenses this file
  to you under the Apache License, Version 2.0 (the
  "License"); you may not use this file except in compliance
  with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing,
  software distributed under the License is distributed on an
  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
  KIND, either express or implied.  See the License for the
  specific language governing permissions and limitations
  under the License.
*/

package uk.ac.ed.inf.graph.util.impl;

import java.util.ArrayList;

import uk.ac.ed.inf.graph.util.IAdjacencyDataStructure;

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

	
	@Override
	public void addNode(int idx){
		int newCap = idx + 1;
		this.adjMat.ensureCapacity(newCap);
		if(idx >= this.adjMat.size()){
			for(int i = this.adjMat.size(); i <= idx; i++){
				this.adjMat.add(i, new ArrayList<Integer>(0));
			}
		}
	}
	
	@Override
	public void addEdge(int one, int two, int edgeIdx){
		ArrayList<Integer> row = this.adjMat.get(one);
		row.ensureCapacity(two+1);
		row.add(two, edgeIdx);
	}
	
	@Override
	public int getEdge(int one, int two){
		ArrayList<Integer> row = this.adjMat.get(one);
		row.ensureCapacity(two+1);
		return row.get(two);
	}
	
	@Override
	public boolean isConnected(int one, int two){
		ArrayList<Integer> row = this.adjMat.get(one);
		row.ensureCapacity(two+1);
		return row.get(two) != null;
	}


	@Override
	public boolean containsNode(int nodeIdx) {
		return nodeIdx < this.adjMat.size();
	}
}
