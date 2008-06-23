package uk.ed.inf.graph.util;

public interface IAdjacencyDataStructure {

	void addNode(int nodeIdx);

	boolean containsNode(int nodeIdx);

	void addEdge(int edgeIdx, int inNodeIdx, int outNodeIdx);

	boolean isConnected(int inNodeIdx, int outNodeIdx);

	int getEdge(int inNodeIdx, int outNodeIdx);

}